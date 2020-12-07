package agh.cs.project1;

import java.util.Random;

public enum MapDirection
{
    NORTH,
    NORTH_EAST,
    EAST,
    SOUTH_EAST,
    SOUTH,
    SOUTH_WEST,
    WEST,
    NORTH_WEST;

    public MapDirection rotatedBy(int offset)
    {
        MapDirection[] values = MapDirection.values();
        return values[(this.ordinal() + offset) % values.length];
    }

    public Vector2d toUnitVector()
    {
        switch (this)
        {
            case NORTH:
                return new Vector2d(0, 1);
            case NORTH_EAST:
                return new Vector2d(1, 1);
            case EAST:
                return new Vector2d(1, 0);
            case SOUTH_EAST:
                return new Vector2d(1, -1);
            case SOUTH:
                return new Vector2d(0, -1);
            case SOUTH_WEST:
                return new Vector2d(-1, -1);
            case WEST:
                return new Vector2d(-1, 0);
            case NORTH_WEST:
                return new Vector2d(-1, 1);
        }

        return new Vector2d(0, 0);
    }

    @Override
    public String toString()
    {
        switch (this)
        {
            case NORTH:
                return "Północ";
            case NORTH_EAST:
                return "Północny-Wschód";
            case EAST:
                return "Wschód";
            case SOUTH_EAST:
                return "Południowy-Wschód";
            case SOUTH:
                return "Południe";
            case SOUTH_WEST:
                return "Południowy-Zachód";
            case WEST:
                return "Zachód";
            case NORTH_WEST:
                return "Północny-Zachód";
        }
        return null;
    }

    public static MapDirection generateRandom()
    {
        Random rand = new Random();
        MapDirection[] values = MapDirection.values();
        return values[rand.nextInt(values.length)];
    }
}
