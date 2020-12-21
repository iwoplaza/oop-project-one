package agh.cs.project1.map.element;

import agh.cs.project1.map.IMapElement;
import agh.cs.project1.map.IWorldMap;
import agh.cs.project1.util.Vector2d;

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
