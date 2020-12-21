package agh.cs.project1.map;

import agh.cs.project1.map.element.Animal;
import agh.cs.project1.IPositionChangeObserver;
import agh.cs.project1.util.Vector2d;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver
{
    protected Map<Vector2d, SortedSet<Animal>> animalMap = new HashMap<>();

    public SortedSet<Animal> getAnimalsAt(Vector2d position)
    {
        return animalMap.getOrDefault(position, Collections.emptySortedSet());
    }

    public Collection<Vector2d> getAnimalOccupations()
    {
        return animalMap.keySet();
    }

    protected abstract Vector2d getMinVisualBoundary();

    protected abstract Vector2d getMaxVisualBoundary();

    public Collection<Animal> getAnimals()
    {
        return animalMap.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
    }

    protected void performPrecopulationActions() {}

    @Override
    public void performActions()
    {
        // Removing dead animals from the map.
        for (SortedSet<Animal> set : this.animalMap.values())
        {
            set.removeIf(Animal::isDead);
        }

        Collection<Animal> animals = getAnimals();

        // Performing animal actions
        for (Animal animal : animals)
        {
            animal.performActions();
        }

        this.performPrecopulationActions();

        // Reproduce
        Collection<Vector2d> animalOccupations = this.getAnimalOccupations();

    }

    @Override
    public void place(Animal animal)
    {
        this.animalMap.computeIfAbsent(animal.getPosition(), k -> new TreeSet<>(Animal::animalEnergyComparator)).add(animal);
        animal.addObserver(this);
    }

    @Override
    public void removeObject(Object element)
    {
        animalMap.entrySet().removeIf(entry -> entry.getValue() == element);
    }

    @Override
    public boolean canMoveTo(Vector2d position)
    {
        return true;
    }

    @Override
    public void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition)
    {
        this.animalMap.getOrDefault(oldPosition, Collections.emptySortedSet()).remove(animal);
        this.animalMap.computeIfAbsent(newPosition, k -> new TreeSet<>(Animal::animalEnergyComparator)).add(animal);
    }

    @Override
    public boolean isOccupied(Vector2d position)
    {
        return this.objectAt(position) != null;
    }

    @Override
    public Object objectAt(Vector2d position)
    {
        return this.getAnimalsAt(position).last();
    }
}
