package agh.cs.project1;

import agh.cs.project1.map.IWorldMap;

public interface IEngine
{
    boolean runStep();
    IWorldMap getMap();
}