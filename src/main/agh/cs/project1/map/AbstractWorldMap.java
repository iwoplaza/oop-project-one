package agh.cs.project1.map;

import agh.cs.project1.map.element.IPositionChangeObserver;
import agh.cs.project1.map.element.Animal;
import agh.cs.project1.util.Vector2d;

import java.util.*;
import java.util.stream.Collectors;

public abstract class AbstractWorldMap implements IWorldMap, IPositionChangeObserver
{
    protected Map<Vector2d, List<Animal>> animalMap = new HashMap<>();
    private int currentDay = 0;

    private List<ISpawnObserver> spawnObservers = new ArrayList<>();
    private List<IRemoveObserver> removeObservers = new ArrayList<>();

    public List<Animal> getAnimalsAt(Vector2d position)
    {
        return this.animalMap.getOrDefault(position, Collections.emptyList());
    }

    protected abstract Vector2d getMinVisualBoundary();

    protected abstract Vector2d getMaxVisualBoundary();

    @Override
    public Collection<Animal> getAnimals()
    {
        return this.animalMap.values().stream().flatMap(Collection::stream).collect(Collectors.toList());
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

    @Override
    public void removeSpawnObserver(ISpawnObserver observer)
    {
        this.spawnObservers.remove(observer);
    }

    @Override
    public void removeRemoveObserver(IRemoveObserver observer)
    {
        this.removeObservers.remove(observer);
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

    private void performReproduction()
    {
        List<Animal> newborn = new ArrayList<>();
        for (Map.Entry<Vector2d, List<Animal>> entry : this.animalMap.entrySet())
        {
            List<Animal> animalList = entry.getValue();
            if (animalList.isEmpty())
            {
                continue;
            }

            animalList.sort(Animal::animalEnergyComparator);
            List<Animal> highestEnergyAnimals = animalList.stream().limit(2)
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
    public void performActions()
    {
        // Next day
        this.currentDay++;

        // Removing dead animals from the map.
        for (List<Animal> animalList : this.animalMap.values())
        {
            List<Animal> deadAnimals = animalList.stream().filter(Animal::isDead).collect(Collectors.toList());
            animalList.removeIf(Animal::isDead);

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
        this.performReproduction();
    }

    @Override
    public void place(Animal animal)
    {
        this.animalMap.computeIfAbsent(animal.getPosition(), k -> new LinkedList<>()).add(animal);
        animal.addPositionObserver(this);

        this.notifySpawned(animal);
    }

    @Override
    public boolean canMoveTo(Vector2d position)
    {
        return true;
    }

    @Override
    public void positionChanged(Animal animal, Vector2d oldPosition)
    {
        List<Animal> oldList = this.animalMap.get(oldPosition);
        oldList.remove(animal);

        if (oldList.isEmpty())
        {
            this.animalMap.remove(oldPosition);
        }

        this.animalMap.computeIfAbsent(animal.getPosition(), k -> new LinkedList<>()).add(animal);
    }

    @Override
    public boolean isOccupied(Vector2d position)
    {
        return this.animalMap.containsKey(position);
    }

    @Override
    public int getDay()
    {
        return currentDay;
    }
}
