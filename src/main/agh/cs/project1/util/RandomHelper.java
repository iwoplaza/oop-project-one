package agh.cs.project1.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;

public class RandomHelper
{
    private static int RANDOM_GUESS_LIMIT = 1000;
    private static final Random rand = new Random();

    public static <E> E chooseRandom(List<E> list)
    {
        if (list.isEmpty())
            return null;

        int index = rand.nextInt(list.size());
        return list.get(index);
    }

    public static Vector2d findRandomPositionWhere(Vector2d min, Vector2d max, Predicate<Vector2d> predicate)
    {
        int width = max.x - min.x + 1;
        int height = max.y - min.y + 1;

        int iteration = 0;
        do
        {
            Vector2d vec = new Vector2d(
                    min.x + rand.nextInt(width),
                    min.y + rand.nextInt(height)
            );
            iteration++;

            if (predicate.test(vec))
                return vec;
        }
        while (iteration < RANDOM_GUESS_LIMIT);

        // Not found guessing the position, checking the whole search space.

        for (int x = min.x; x <= max.x; ++x)
        {
            for (int y = min.y; y <= max.y; ++y)
            {
                Vector2d vec = new Vector2d(x, y);

                if (predicate.test(vec))
                    return vec;
            }
        }

        // No space found that meets the requirements.
        return null;
    }

    public static Vector2d findRandomPositionWithinSmallSpaceWhere(Vector2d min, Vector2d max, Predicate<Vector2d> predicate)
    {
        List<Vector2d> searchSpace = new ArrayList<>();

        for (int i = min.x; i <= max.x; ++i)
        {
            for (int j = min.y; j <= max.y; ++j)
            {
                searchSpace.add(new Vector2d(i, j));
            }
        }

        while (!searchSpace.isEmpty())
        {
            // Selecting a random vector from the search space.
            int index = rand.nextInt(searchSpace.size());
            Vector2d vec = searchSpace.get(index);
            if (predicate.test(vec))
                return vec;

            // Removing the unfit vector from the search-space.
            searchSpace.remove(index);
        }

        // No space found that meets the requirements.
        return null;
    }

    public static void addNeighbourRing(List<Vector2d> list, Vector2d origin, int width, int height)
    {
        Vector2d min = new Vector2d(origin.x - (int) Math.floor(width / 2.0),    origin.y - (int) Math.floor(height / 2.0));
        Vector2d max = new Vector2d(origin.x + (int) Math.ceil(width / 2.0) - 1, origin.y + (int) Math.ceil(height / 2.0) - 1);

        // Adding horizontal lines.
        for (int x = min.x; x <= max.x; ++x)
        {
            list.add(new Vector2d(x, min.y));
            if (min.y != max.y)
            {
                list.add(new Vector2d(x, max.y));
            }
        }

        // Adding vertical lines.
        for (int y = min.y + 1; y < max.y; ++y)
        {
            list.add(new Vector2d(min.x, y));
            if (min.x != max.x)
            {
                list.add(new Vector2d(max.x, y));
            }
        }
    }

    public static Vector2d[] findSpotsInCenter(int n, Vector2d min, Vector2d max)
    {
        List<Vector2d> searchSpace = new ArrayList<>();
        Vector2d[] foundSpots = new Vector2d[n];

        int width = max.x - min.x + 1;
        int height = max.y - min.y + 1;
        Vector2d origin = new Vector2d(min.x + width / 2, min.y + height / 2);

        int currentXRing = 2 - width % 2;
        int currentYRing = 2 - height % 2;
        addNeighbourRing(searchSpace, origin, currentXRing, currentYRing);

        int iterations = 0;
        while(n > 0 && iterations < RANDOM_GUESS_LIMIT)
        {
            int index = rand.nextInt(searchSpace.size());
            Vector2d vec = searchSpace.get(index);

            if (vec.follows(min) && vec.precedes(max))
            {
                n--;
                foundSpots[n] = vec;
                searchSpace.remove(index);

                if (searchSpace.isEmpty())
                {
                    currentXRing += 2;
                    currentYRing += 2;
                    addNeighbourRing(searchSpace, origin, currentXRing, currentYRing);
                }
            }
            iterations++;
        }

        return foundSpots;
    }
}
