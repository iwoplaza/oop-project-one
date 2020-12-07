package agh.cs.project1;

public class AbstractWorldMapElement implements IMapElement
{
    protected IWorldMap map;
    protected Vector2d position;

    public AbstractWorldMapElement(IWorldMap map, Vector2d position)
    {
        this.map = map;
        this.position = position;
    }

    @Override
    public Vector2d getPosition()
    {
        return this.position;
    }
}
