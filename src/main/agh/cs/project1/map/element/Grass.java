package agh.cs.project1.map.element;

import agh.cs.project1.util.Vector2d;
import agh.cs.project1.map.IWorldMap;

public class Grass extends AbstractWorldMapElement
{
    public Grass(IWorldMap map, Vector2d position)
    {
        super(map, position);
    }

    @Override
    public String toString()
    {
        return "*";
    }
}
