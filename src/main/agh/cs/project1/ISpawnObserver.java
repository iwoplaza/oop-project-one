package agh.cs.project1;

import agh.cs.project1.map.IMapElement;

@FunctionalInterface
public interface ISpawnObserver
{
    /**
     * Called when an object is placed into the world.
     * @param element
     */
    void onSpawned(IMapElement element);
}
