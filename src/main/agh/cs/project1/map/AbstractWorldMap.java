package agh.cs.project1.map;

import agh.cs.project1.map.element.Animal;
import agh.cs.project1.IPositionChangeObserver;
import agh.cs.project1.util.Vector2d;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver
{
    protected Map<Vector2d, Animal> animals = new LinkedHashMap<>();

    public Animal getAnimalAt(Vector2d position)
    {
        return animals.get(position);
    }

    protected abstract Vector2d getMinVisualBoundary();

    protected abstract Vector2d getMaxVisualBoundary();

    @Override
    public void place(Animal animal)
    {
        if (!this.canMoveTo(animal.getPosition()))
        {
            throw new IllegalArgumentException("Couldn't place animal at " + animal.getPosition() + ".");
        }

        this.animals.put(animal.getPosition(), animal);
        animal.addObserver(this);
    }

    @Override
    public void removeObject(Object element)
    {
        animals.entrySet().removeIf(entry -> entry.getValue() == element);
    }

    @Override
    public boolean canMoveTo(Vector2d position)
    {
        return getAnimalAt(position) == null;
    }

    @Override
    public void positionChanged(Vector2d oldPosition, Vector2d newPosition)
    {
        Animal animal = this.animals.remove(oldPosition);
        this.animals.put(newPosition, animal);
    }

    @Override
    public boolean isOccupied(Vector2d position)
    {
        return this.objectAt(position) != null;
    }

    @Override
    public Object objectAt(Vector2d position)
    {
        return this.getAnimalAt(position);
    }
}
