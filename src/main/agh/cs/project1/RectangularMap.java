package agh.cs.project1;

public class RectangularMap extends AbstractWorldMap
{
    private int width;
    private int height;
    private Vector2d minBoundary;
    private Vector2d maxBoundary;

    public RectangularMap(int width, int height)
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
        return position.follows(this.minBoundary) &&
               position.precedes(this.maxBoundary) &&
               super.canMoveTo(position);
    }
}
