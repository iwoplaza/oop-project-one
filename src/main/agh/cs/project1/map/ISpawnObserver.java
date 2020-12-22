package agh.cs.project1.map;

@FunctionalInterface
public interface ISpawnObserver
{
    /**
     * Called when an object is placed into the world.
     * @param element
     */
    void onSpawned(IMapElement element);
}
