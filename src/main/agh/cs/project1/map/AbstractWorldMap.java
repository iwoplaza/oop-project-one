package agh.cs.project1.map;

import agh.cs.project1.IPositionChangeObserver;
import agh.cs.project1.IRemoveObserver;
import agh.cs.project1.ISpawnObserver;
import agh.cs.project1.map.element.Animal;
import agh.cs.project1.util.Vector2d;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver
{
    protected Map<Vector2d, SortedSet<Animal>> animalMap = new HashMap<>();
    protected List<ISpawnObserver> spawnObservers = new ArrayList<>();
    protected List<IRemoveObserver> removeObservers = new ArrayList<>();

    public SortedSet<Animal> getAnimalsAt(Vector2d position)
    {
        return this.animalMap.getOrDefault(position, Collections.emptySortedSet());
    }

    protected abstract Vector2d getMinVisualBoundary();

    protected abstract Vector2d getMaxVisualBoundary();

    @Override
    public Collection<Animal> getAnimals()
    {
        return this.animalMap.values().stream().flatMap(Collection::stream).collect(Collectors.toSet());
    }

    @Override
    public void addSpawnObserver(ISpawnObserver observer)
    {
        this.spawnObservers.add(observer);
    }

    @Override
    public void addRemoveObserver(IRemoveObserver observer)
    {
        this.removeObservers.add(observer);
    }

    protected void notifySpawned(IMapElement element)
    {
        this.spawnObservers.forEach(o -> o.onSpawned(element));
    }

    protected void notifyRemoved(IMapElement element)
    {
        this.removeObservers.forEach(o -> o.onRemoved(element));
    }

    protected void performPrecopulationActions() {}

    @Override
    public void performActions()
    {
        // Removing dead animals from the map.
        for (SortedSet<Animal> set : this.animalMap.values())
        {
            List<Animal> deadAnimals = set.stream().filter(Animal::isDead).collect(Collectors.toList());
            set.removeIf(Animal::isDead);

            // Notify about the animal's removal.
            deadAnimals.forEach(this::notifyRemoved);
        }

        Collection<Animal> animals = getAnimals();

        // Performing animal actions
        for (Animal animal : animals)
        {
            animal.performActions();
        }

        this.performPrecopulationActions();

        // Reproduce
        List<Animal> newborn = new ArrayList<>();
        for (Map.Entry<Vector2d, SortedSet<Animal>> entry : this.animalMap.entrySet())
        {
            SortedSet<Animal> animalSet = entry.getValue();
            if (animalSet.isEmpty())
            {
                continue;
            }

            List<Animal> highestEnergyAnimals = animalSet.stream().limit(2)
                    .collect(Collectors.toList());

            if (highestEnergyAnimals.size() < 2)
            {
                // Not enough animals in one place to reproduce
                continue;
            }

            Animal firstParent = highestEnergyAnimals.get(0);
            Animal secondParent = highestEnergyAnimals.get(1);
            if (firstParent.canReproduce() && secondParent.canReproduce())
            {
                Animal child = highestEnergyAnimals.get(0).reproduceWith(highestEnergyAnimals.get(1));
                newborn.add(child);
            }
        }

        newborn.forEach(this::place);
    }

    @Override
    public void place(Animal animal)
    {
        this.animalMap.computeIfAbsent(animal.getPosition(), k -> new TreeSet<>(Animal::animalEnergyComparator)).add(animal);
        animal.addObserver(this);

        this.notifySpawned(animal);
    }

    @Override
    public void removeObject(IMapElement element)
    {
        if (element instanceof Animal)
        {
            boolean removed = animalMap.values().stream().anyMatch(set -> set.remove(element));

            if (removed)
            {
                this.notifyRemoved(element);
            }
        }
    }

    @Override
    public boolean canMoveTo(Vector2d position)
    {
        return true;
    }

    @Override
    public void positionChanged(Animal animal, Vector2d oldPosition, Vector2d newPosition)
    {
        SortedSet<Animal> oldSet = this.animalMap.get(oldPosition);
        oldSet.remove(animal);

        if (oldSet.isEmpty())
        {
            this.animalMap.remove(oldPosition);
        }

        this.animalMap.computeIfAbsent(newPosition, k -> new TreeSet<>(Animal::animalEnergyComparator)).add(animal);
    }

    @Override
    public boolean isOccupied(Vector2d position)
    {
        return this.objectAt(position) != null;
    }

    @Override
    public IMapElement objectAt(Vector2d position)
    {
        return this.getAnimalsAt(position).first();
    }
}
