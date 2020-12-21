package agh.cs.project1.ui;

import agh.cs.project1.map.IMapElement;
import agh.cs.project1.map.IRemoveObserver;
import agh.cs.project1.map.IWorldMap;
import agh.cs.project1.map.element.Animal;
import agh.cs.project1.map.element.IReproduceObserver;

import java.util.LinkedList;
import java.util.List;

public class AnimalTracker implements IRemoveObserver, IReproduceObserver
{

    private Animal trackedAnimal;
    private List<Animal> descendants;
    private int childrenCount = 0;
    private int diedOn = -1;

    public AnimalTracker(Animal trackedAnimal)
    {
        this.trackedAnimal = trackedAnimal;
        this.descendants = new LinkedList<>();

        IWorldMap map = this.trackedAnimal.getMap();
        map.addRemoveObserver(this);
        this.trackedAnimal.addReproduceObserver(this);
    }

    public void dispose()
    {
        IWorldMap map = this.trackedAnimal.getMap();
        map.removeRemoveObserver(this);
        this.trackedAnimal.removeReproduceObserver(this);
        descendants.forEach(d -> d.removeReproduceObserver(this));
    }

    public int getChildrenCount()
    {
        return this.childrenCount;
    }

    public int getDescendantsCount()
    {
        return this.descendants.size();
    }

    public String getDayDiedOnAsString()
    {
        return this.diedOn == -1 ? "-" : String.format("%d", this.diedOn);
    }

    @Override
    public void onRemoved(IMapElement element)
    {
        if (element == this.trackedAnimal)
        {
            this.diedOn = element.getMap().getDay();
        }
    }

    @Override
    public void onReproduced(Animal animal, Animal with, Animal child)
    {
        if (animal == this.trackedAnimal)
        {
            this.childrenCount++;
        }

        descendants.add(child);
        child.addReproduceObserver(this);
    }
}
