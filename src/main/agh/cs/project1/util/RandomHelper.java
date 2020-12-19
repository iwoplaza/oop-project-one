package agh.cs.project1.util;

import java.util.Random;
import java.util.function.Predicate;

public class RandomHelper
{
    private static int RANDOM_GUESS_LIMIT = 1000;
    private static final Random rand = new Random();

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
}
