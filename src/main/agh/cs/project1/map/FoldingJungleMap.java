package agh.cs.project1.map;

import agh.cs.project1.map.element.Animal;
import agh.cs.project1.map.element.Grass;
import agh.cs.project1.util.RandomHelper;
import agh.cs.project1.util.Vector2d;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Predicate;

public class FoldingJungleMap extends FoldingWorldMap
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

        grassFieldsMap.put(vec, new Grass(this, vec));
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

    @Override
    public void removeObject(Object element)
    {
        super.removeObject(element);

        this.grassFieldsMap.remove(element);
    }

    public boolean removeGrassAt(Vector2d position)
    {
        return grassFieldsMap.remove(position) != null;
    }

    @Override
    public Object objectAt(Vector2d position)
    {
        Object obj = super.objectAt(position);
        if (obj != null)
            return obj;

        if (grassFieldsMap.containsKey(position))
        {
            return grassFieldsMap.get(position);
        }

        return null;
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
}
