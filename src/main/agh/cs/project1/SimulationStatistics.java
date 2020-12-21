package agh.cs.project1;

import agh.cs.project1.map.*;
import agh.cs.project1.map.element.Animal;
import agh.cs.project1.map.element.Grass;

public class SimulationStatistics implements ISpawnObserver, IRemoveObserver
{
    private final IWorldMap map;
    private int animalCount = 0;
    private int plantCount = 0;

    public SimulationStatistics(IEngine engine)
    {
        this.map = engine.getMap();
        this.animalCount = engine.getMap().getAnimals().size();
        if (this.map instanceof IGrassyWorldMap)
        {
            this.plantCount = ((IGrassyWorldMap) this.map).getGrassCount();
        }

        this.map.addSpawnObserver(this);
        this.map.addRemoveObserver(this);
    }

    public int getAnimalCount()
    {
        return this.animalCount;
    }

    public int getActualAnimalCount()
    {
        return this.map.getAnimals().size();
    }

    public int getPlantCount()
    {
        return this.plantCount;
    }

    @Override
    public void onSpawned(IMapElement element)
    {
        if (element instanceof Animal)
        {
            this.animalCount++;
        }
        else if (element instanceof Grass)
        {
            this.plantCount++;
        }
    }

    @Override
    public void onRemoved(IMapElement element)
    {
        if (element instanceof Animal)
        {
            this.animalCount--;
        }
        else if (element instanceof Grass)
        {
            this.plantCount--;
        }
    }
}
