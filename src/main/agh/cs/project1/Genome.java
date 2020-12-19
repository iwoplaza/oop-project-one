package agh.cs.project1;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

public class Genome
{
    private static final int POSSIBLE_GENES = 8;
    private static final Random rand = new Random();

    private int capacity;
    private int[] geneAmounts;

    public Genome(Genome other)
    {
        this.capacity = other.capacity;
        this.geneAmounts = Arrays.copyOf(other.geneAmounts, other.geneAmounts.length);
    }

    private Genome(int capacity)
    {
        this.capacity = capacity;
        this.geneAmounts = new int[POSSIBLE_GENES];

        for (int i = 0; i < POSSIBLE_GENES; ++i)
        {
            this.geneAmounts[i] = 0;
        }
    }

    /**
     * Returns true if the genome is filled with genes up to it's capacity.
     */
    public boolean isComplete()
    {
        int count = 0;
        for (int amount : geneAmounts)
        {
            count += amount;
        }

        return count == capacity;
    }

    @Override
    public String toString()
    {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < POSSIBLE_GENES; ++i)
        {
            for (int j = 0; j < geneAmounts[i]; ++j)
            {
                builder.append(i);
            }
        }

        return builder.toString();
    }

    @Override
    public boolean equals(Object other)
    {
        if (!(other instanceof Genome))
            return false;

        Genome otherGenome = (Genome) other;
        return Arrays.equals(this.geneAmounts, otherGenome.geneAmounts);
    }

    public int pickRandomGene()
    {
        int index = rand.nextInt(this.capacity);

        for (int i = 0; i < this.geneAmounts.length; ++i)
        {
            int amount = this.geneAmounts[i];
            if (index < amount)
            {
                return i;
            }
            index -= amount;
        }

        throw new IllegalStateException("The genome is incomplete.");
    }

    public int[] asSortedArray()
    {
        int[] sortedArray = new int[capacity];

        int offset = 0;
        for (int gene = 0; gene < POSSIBLE_GENES; ++gene)
        {
            for (int i = 0; i < this.geneAmounts[gene]; ++i)
            {
                sortedArray[offset++] = gene;
            }
        }

        return sortedArray;
    }

    public static Genome fromSortedArray(int[] sortedArray)
    {
        Genome genome = new Genome(sortedArray.length);

        for (int i = 0; i < sortedArray.length; ++i)
        {
            genome.geneAmounts[sortedArray[i]]++;
        }

        return genome;
    }

    public static Genome fromString(String input)
    {
        Genome genome = new Genome(input.length());

        for (int i = 0; i < input.length(); ++i)
        {
            char c = input.charAt(i);
            int gene = c - '0';
            genome.geneAmounts[gene]++;
        }

        return genome;
    }

    public static Genome createRandomized(int capacity)
    {
        Genome genome = new Genome(capacity);

        for (int i = 0; i < POSSIBLE_GENES; ++i)
        {
            genome.geneAmounts[i] = 1;
        }

        for (int i = 0; i < capacity - POSSIBLE_GENES; ++i)
        {
            int gene = rand.nextInt(POSSIBLE_GENES);
            genome.geneAmounts[gene]++;
        }

        return genome;
    }

    /**
     * Combines the parents' genomes, breaking them into 3 slices.
     * @param singleParent This parent provides the middle slice of their genome.
     * @param doubleParent This parent provides the two edge slices of their genome.
     * @param firstSlice Where to slice the genome.
     * @param secondSlice Where to slice the genome.
     * @return The combined genome.
     */
    public static Genome combine(Genome singleParent, Genome doubleParent, int firstSlice, int secondSlice)
    {
        if (singleParent.capacity != doubleParent.capacity)
        {
            throw new IllegalArgumentException("Tried to combine two genomes of different capacities.");
        }

        int[] singleParentArray = singleParent.asSortedArray();
        int[] doubleParentArray = doubleParent.asSortedArray();

        int[] combinedArray = IntStream.concat(
                Arrays.stream(doubleParentArray).limit(firstSlice),
                IntStream.concat(
                    Arrays.stream(singleParentArray).limit(secondSlice).skip(firstSlice),
                    Arrays.stream(doubleParentArray).skip(secondSlice)
                )
        ).toArray();

        return Genome.fromSortedArray(combinedArray);
    }
}
