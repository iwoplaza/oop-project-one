package agh.cs.project1.map;

import agh.cs.project1.util.Vector2d;

public interface IMapElement
{

    Vector2d getPosition();

    IWorldMap getMap();

}
