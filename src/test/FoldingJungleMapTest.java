import agh.cs.project1.map.FoldingJungleMap;
import agh.cs.project1.map.element.Animal;
import agh.cs.project1.map.element.Grass;
import agh.cs.project1.util.Vector2d;
import org.junit.Test;

import java.util.SortedSet;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

public class FoldingJungleMapTest
{
    @Test
    public void testAnimalSorting()
    {
        FoldingJungleMap map = new FoldingJungleMap(20, 40, 0.5f, 5);
        Animal animalAlpha = new Animal(map, new Vector2d(1, 2), 20, 1, 20);
        Animal animalBeta = new Animal(map, new Vector2d(1, 2), 15, 1, 15);
        map.place(animalAlpha);
        map.place(animalBeta);

        SortedSet<Animal> animals = map.getAnimalsAt(new Vector2d(1, 2));
        assertEquals(animalAlpha, animals.first());
        assertEquals(animalBeta, animals.last());
    }

    @Test
    public void spawnAndRemoveNotify()
    {
        AtomicInteger animalCount = new AtomicInteger();
        AtomicInteger plantCount = new AtomicInteger();

        FoldingJungleMap map = new FoldingJungleMap(20, 40, 0.5f, 5);
        map.addSpawnObserver(element -> {
            if (element instanceof Animal)
            {
                animalCount.getAndIncrement();
            }
            else if (element instanceof Grass)
            {
                plantCount.getAndIncrement();
            }
        });
        map.addRemoveObserver(element -> {
            if (element instanceof Animal)
            {
                animalCount.getAndDecrement();
            }
            else if (element instanceof Grass)
            {
                plantCount.getAndDecrement();
            }
        });

        Animal animalAlpha = new Animal(map, new Vector2d(1, 2), 20, 1, 20);
        Animal animalBeta = new Animal(map, new Vector2d(1, 2), 15, 1, 15);
        assertEquals(0, animalCount.get());
        assertEquals(0, plantCount.get());
        map.place(animalAlpha);
        assertEquals(1, animalCount.get());
        assertEquals(0, plantCount.get());
        map.place(animalBeta);
        assertEquals(2, animalCount.get());
        assertEquals(0, plantCount.get());

        map.performActions();
        assertEquals(2, animalCount.get());
        assertEquals(2, plantCount.get());

        // Hurting the animal.
        animalAlpha.takeEnergy(100);

        map.performActions();
        assertEquals(1, animalCount.get());
        assertEquals(4, plantCount.get());

        // Hurting the animal.
        animalBeta.takeEnergy(100);

        map.performActions();
        assertEquals(0, animalCount.get());
        assertEquals(6, plantCount.get());
    }
}
