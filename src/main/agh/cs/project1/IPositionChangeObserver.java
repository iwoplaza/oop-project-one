package agh.cs.project1;

import agh.cs.project1.map.element.Animal;
import agh.cs.project1.util.Vector2d;

@FunctionalInterface
public interface IPositionChangeObserver
{
    void positionChanged(Animal animal, Vector2d oldPosition);
}
