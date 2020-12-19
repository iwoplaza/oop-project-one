package agh.cs.project1;

import agh.cs.project1.map.IWorldMap;
import agh.cs.project1.map.element.Animal;
import agh.cs.project1.util.Vector2d;

import java.util.*;

public class SimulationEngine implements IEngine
{

    private IWorldMap map;
    private List<Animal> animals = new ArrayList<>();

    public SimulationEngine(IWorldMap map, AppParameters parameters, Vector2d[] initialPositions)
    {
        this.map = map;

        for (Vector2d pos : initialPositions)
        {
            Animal animal = new Animal(map, pos, parameters.startEnergy);
            map.place(animal);
            animals.add(animal);
        }
    }

    @Override
    public void run()
    {
        while (true)
        {
            System.out.println(this.map);

            if (!runStep()) {
                break;
            }
        }
    }

    @Override
    public boolean runStep()
    {
        // Removing dead animals from the map.
        animals.forEach(a -> {
            if (a.isDead())
            {
                this.map.removeObject(a);
            }
        });
        animals.removeIf(a -> a.getEnergy() <= 0);

        // Performing animal actions
        for (Animal animal : animals)
        {
            animal.performActions();
        }

        // Eating grass
        // ...

        // Reproduction
        // ...

        // Adding new grass tufts
        this.map.performActions();

        return true;
    }

    @Override
    public IWorldMap getMap()
    {
        return map;
    }
}
