package agh.cs.project1.statistics;

public class SimulationStatistics
{
    protected int animalCount = 0;
    protected int plantCount = 0;
    protected int dominantGene = -1;
    protected float averageEnergy = 0;
    protected float averageLifespan = 0;
    protected float averageChildCount = 0;

    public String getAnimalCount()
    {
        return String.format("%d", this.animalCount);
    }

    public String getPlantCount()
    {
        return String.format("%d", this.plantCount);
    }

    public String getAverageLifespan()
    {
        return String.format("%.2f", this.averageLifespan);
    }

    public String getDominantGene()
    {
        return dominantGene == -1 ? "-" : String.format("%d", dominantGene);
    }

    public String getAverageEnergy()
    {
        return String.format("%.2f", this.averageEnergy);
    }

    public String getAverageChildCount()
    {
        return String.format("%.2f", this.averageChildCount);
    }

    @Override
    public Object clone()
    {
        SimulationStatistics clone = new SimulationStatistics();

        clone.animalCount = this.animalCount;
        clone.plantCount = this.plantCount;
        clone.dominantGene = this.dominantGene;
        clone.averageEnergy = this.averageEnergy;
        clone.averageLifespan = this.averageLifespan;
        clone.averageChildCount = this.averageChildCount;

        return clone;
    }
}
