import agh.cs.project1.map.AbstractWorldMap;
import agh.cs.project1.map.FoldingJungleMap;
import agh.cs.project1.map.element.Animal;
import agh.cs.project1.util.Vector2d;
import org.junit.Test;

import java.util.SortedSet;

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
}
