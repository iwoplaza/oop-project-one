package agh.cs.project1;

import java.util.Collection;

public class GenomeStatistics
{
    private int[] geneAmounts;

    public GenomeStatistics()
    {
        this.geneAmounts = new int[Genome.POSSIBLE_GENES];
    }

    public void addAll(Collection<Genome> genomes)
    {
        genomes.forEach(g -> {
            for (int i = 0; i < Genome.POSSIBLE_GENES; ++i)
            {
                geneAmounts[i] += g.getAmountOf(i);
            }
        });
    }

    /**
     * Returns the gene that occurs the most often within all genomes.
     */
    public int getDominantGene()
    {
        int dominantGene = 0;

        for (int i = 1; i < Genome.POSSIBLE_GENES; ++i)
        {
            if (this.geneAmounts[i] > this.geneAmounts[dominantGene])
            {
                dominantGene = i;
            }
        }

        return dominantGene;
    }
}
