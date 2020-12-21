package agh.cs.project1.map;

import agh.cs.project1.map.element.Animal;
import agh.cs.project1.map.element.Grass;
import agh.cs.project1.util.RandomHelper;
import agh.cs.project1.util.Vector2d;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class FoldingJungleMap extends FoldingWorldMap implements IGrassyWorldMap
{
    private Map<Vector2d, Grass> grassFieldsMap = new HashMap<>();

    private Vector2d jungleMin,
                     jungleMax;

    private int plantEnergy;

    public FoldingJungleMap(int width, int height, float jungleRatio, int plantEnergy)
    {
        super(width, height);

        int jungleWidth = (int) Math.ceil(width * jungleRatio);
        int jungleHeight = (int) Math.ceil(height * jungleRatio);
        this.jungleMin = new Vector2d((width - jungleWidth) / 2, (height - jungleHeight) / 2);
        this.jungleMax = new Vector2d((width + jungleWidth) / 2 - 1, (height + jungleHeight) / 2 - 1);
        this.plantEnergy = plantEnergy;
    }

    private void placeGrassTuftAtRandom(Vector2d min, Vector2d max, Predicate<Vector2d> requirements)
    {
        // Finding an empty spot.
        Vector2d vec = RandomHelper.findRandomPositionWhere(
                min,
                max,
                v -> requirements.test(v) && !grassFieldsMap.containsKey(v) && getAnimalsAt(v).isEmpty());

        if (vec == null)
        {
            // No grass left to place.
            return;
        }

        Grass grass = new Grass(this, vec);
        grassFieldsMap.put(vec, grass);

        // Notifying about the grass tuft being spawned.
        this.notifySpawned(grass);
    }

    private void placeGrassTuftOutsideJungle()
    {
        this.placeGrassTuftAtRandom(getMinVisualBoundary(), getMaxVisualBoundary(), vec -> !isInJungle(vec));
    }

    private void placeGrassTuftInJungle()
    {
        this.placeGrassTuftAtRandom(jungleMin, jungleMax, vec -> true);
    }

    public Collection<Grass> getGrassTufts()
    {
        return this.grassFieldsMap.values();
    }

    public boolean removeGrassAt(Vector2d position)
    {
        Grass removedGrass = grassFieldsMap.remove(position);
        if (removedGrass != null)
        {
            this.notifyRemoved(removedGrass);
            return true;
        }
        return false;
    }

    @Override
    public boolean isOccupied(Vector2d position)
    {
        boolean occupied = super.isOccupied(position);
        if (occupied)
            return true;

        return grassFieldsMap.containsKey(position);
    }

    @Override
    protected void performPrecopulationActions()
    {
        Collection<Animal> animals = getAnimals();

        // Eating grass
        for (Animal animal : animals)
        {
            if (this.removeGrassAt(animal.getPosition()))
            {
                animal.gainEnergy(this.plantEnergy);
            }
        }
    }

    @Override
    public void performActions()
    {
        super.performActions();

        this.placeGrassTuftOutsideJungle();
        this.placeGrassTuftInJungle();
    }

    public boolean isInJungle(Vector2d vec)
    {
        return vec.follows(jungleMin) && vec.precedes(jungleMax);
    }

    public Vector2d getJungleMin()
    {
        return jungleMin;
    }

    public Vector2d getJungleMax()
    {
        return jungleMax;
    }

    @Override
    public int getGrassCount()
    {
        return this.grassFieldsMap.size();
    }
}
