package agh.cs.project1.map;

import agh.cs.project1.util.Vector2d;

public class FoldingWorldMap extends AbstractWorldMap
{
    private int width;
    private int height;
    private Vector2d minBoundary;
    private Vector2d maxBoundary;

    public FoldingWorldMap(int width, int height)
    {
        this.width = width;
        this.height = height;
        this.minBoundary = new Vector2d(0, 0);
        this.maxBoundary = new Vector2d(width - 1, height - 1);
    }

    public int getWidth()
    {
        return width;
    }

    public int getHeight()
    {
        return height;
    }

    @Override
    protected Vector2d getMinVisualBoundary()
    {
        return minBoundary;
    }

    @Override
    protected Vector2d getMaxVisualBoundary()
    {
        return maxBoundary;
    }

    @Override
    public boolean canMoveTo(Vector2d position)
    {
        return super.canMoveTo(position);
    }

    @Override
    public Vector2d mapCoordinates(Vector2d in)
    {
        // Wrapping the coordinates.
        return new Vector2d(Math.floorMod(in.x, width), Math.floorMod(in.y, height));
    }
}
