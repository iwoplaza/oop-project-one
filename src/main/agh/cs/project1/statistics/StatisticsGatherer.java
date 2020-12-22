package agh.cs.project1.statistics;

import agh.cs.project1.IEngine;
import agh.cs.project1.map.*;
import agh.cs.project1.map.element.Animal;
import agh.cs.project1.map.element.Grass;

import java.util.stream.Collectors;

public class StatisticsGatherer implements ISpawnObserver, IRemoveObserver
{
    private final IWorldMap map;
    private SimulationStatistics statistics = new SimulationStatistics();
    private int amountOfDead = 0;

    public StatisticsGatherer(IEngine engine)
    {
        this.map = engine.getMap();
        this.statistics.animalCount = engine.getMap().getAnimals().size();
        if (this.map instanceof IGrassyWorldMap)
        {
            this.statistics.plantCount = ((IGrassyWorldMap) this.map).getGrassCount();
        }

        this.map.addSpawnObserver(this);
        this.map.addRemoveObserver(this);
    }

    public SimulationStatistics getStatistics()
    {
        this.statistics.averageChildCount = (float) this.map.getAnimals().stream()
                .mapToDouble(Animal::getChildCount)
                .average()
                .orElse(0);

        this.statistics.averageEnergy = (float) this.map.getAnimals().stream()
                .mapToDouble(Animal::getEnergy)
                .average()
                .orElse(0);

        GenomeStatistics genomeStatistics = new GenomeStatistics();
        genomeStatistics.addAll(this.map.getAnimals().stream().map(Animal::getGenome).collect(Collectors.toList()));
        this.statistics.dominantGene = genomeStatistics.getDominantGene();

        return this.statistics;
    }

    @Override
    public void onSpawned(IMapElement element)
    {
        if (element instanceof Animal)
        {
            this.statistics.animalCount++;
        }
        else if (element instanceof Grass)
        {
            this.statistics.plantCount++;
        }
    }

    @Override
    public void onRemoved(IMapElement element)
    {
        if (element instanceof Animal)
        {
            Animal animal = (Animal) element;
            this.statistics.animalCount--;

            // Adjusting the average lifespan
            float aggregateLifespans = this.statistics.averageLifespan * this.amountOfDead;
            this.amountOfDead++;
            this.statistics.averageLifespan = (aggregateLifespans + animal.getAge()) / this.amountOfDead;
        }
        else if (element instanceof Grass)
        {
            this.statistics.plantCount--;
        }
    }
}
