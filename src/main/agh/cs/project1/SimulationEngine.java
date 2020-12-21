package agh.cs.project1;

import agh.cs.project1.map.IWorldMap;
import agh.cs.project1.map.element.Animal;
import agh.cs.project1.util.Vector2d;

import java.util.*;

public class SimulationEngine implements IEngine
{

    private AppParameters parameters;
    private IWorldMap map;

    public SimulationEngine(IWorldMap map, AppParameters parameters, Vector2d[] initialPositions)
    {
        this.parameters = parameters;
        this.map = map;

        for (Vector2d pos : initialPositions)
        {
            Animal animal = new Animal(map, pos, parameters.startEnergy);
            map.place(animal);
        }
    }

    @Override
    public boolean runStep()
    {
        // Adding new grass tufts, reproduction etc.
        this.map.performActions();

        return true;
    }

    @Override
    public IWorldMap getMap()
    {
        return map;
    }
}
