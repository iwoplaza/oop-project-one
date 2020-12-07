package agh.cs.project1;

import java.util.Random;

public class Genome
{
    private static final int POSSIBLE_GENES = 8;
    private static final Random rand = new Random();

    private int capacity;
    private int[] geneAmounts;

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

    /**
     * Removes genes from [begin, end) in the sorted list representation of the genome.
     * This will most likely make the genome incomplete.
     * @param begin
     * @param end
     */
    public void clearSection(int begin, int end)
    {
        int i = 0;
        for (; i < this.geneAmounts.length; ++i)
        {
            int amount = this.geneAmounts[i];
            if (begin < amount)
            {
                this.geneAmounts[i] = begin;

                // If sections ends inside the current gene.
                if (end <= amount)
                {
                    this.geneAmounts[i] += amount - end;
                    return;
                }
                begin -= amount;
                end -= amount;
                break;
            }
            begin -= amount;
            end -= amount;
        }

        i++;
        for (; i < this.geneAmounts.length; ++i)
        {
            int amount = this.geneAmounts[i];
            if (end < amount)
            {
                this.geneAmounts[i] = amount - end;
                break;
            }
            else
            {
                this.geneAmounts[i] = 0;
            }
            end -= amount;
        }
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

    public static Genome combine(Genome a, Genome b)
    {
        if (a.capacity != b.capacity)
        {
            throw new IllegalArgumentException("Tried to combine two genomes of different capacities.");
        }

        Genome genome = new Genome(a.capacity);

        for (int i = 0; i < a.capacity - POSSIBLE_GENES; ++i)
        {
            int gene = rand.nextInt(POSSIBLE_GENES);
            genome.geneAmounts[gene]++;
        }

        return genome;
    }
}
