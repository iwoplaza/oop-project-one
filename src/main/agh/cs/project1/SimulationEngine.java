package agh.cs.project1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class SimulationEngine implements IEngine
{

    private IWorldMap map;
    private List<Animal> animals = new ArrayList<>();

    public SimulationEngine(IWorldMap map, Vector2d[] initialPositions)
    {
        this.map = map;

        for (Vector2d pos : initialPositions)
        {
            Animal animal = new Animal(map, pos);
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
        return true;
    }

    @Override
    public IWorldMap getMap()
    {
        return map;
    }
}
