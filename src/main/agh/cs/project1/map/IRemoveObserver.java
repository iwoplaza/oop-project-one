package agh.cs.project1.map;

import agh.cs.project1.map.IMapElement;

@FunctionalInterface
public interface IRemoveObserver
{
    /**
     * Called when an object is removed from the world.
     * @param element
     */
    void onRemoved(IMapElement element);
}
