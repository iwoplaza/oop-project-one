package agh.cs.project1.map;

@FunctionalInterface
public interface IRemoveObserver
{
    /**
     * Called when an object is removed from the world.
     * @param element
     */
    void onRemoved(IMapElement element);
}
