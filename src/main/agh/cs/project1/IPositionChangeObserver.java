package agh.cs.project1;

import agh.cs.project1.util.Vector2d;

public interface IPositionChangeObserver
{
    void positionChanged(Vector2d oldPosition, Vector2d newPosition);
}
