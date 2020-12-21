package agh.cs.project1.map;

import agh.cs.project1.IRemoveObserver;
import agh.cs.project1.ISpawnObserver;
import agh.cs.project1.map.element.Animal;
import agh.cs.project1.util.Vector2d;

import java.util.Collection;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2d and MoveDirection classes are defined.
 *
 */
public interface IWorldMap
{
    void addSpawnObserver(ISpawnObserver observer);

    void addRemoveObserver(IRemoveObserver observer);

    /**
     * Indicate if any object can move to the given position.
     *
     * @param position
     *            The position checked for the movement possibility.
     * @return True if the object can move to that position.
     */
    boolean canMoveTo(Vector2d position);

    /**
     * Place a animal on the map.
     *
     * @param animal
     *            The animal to place on the map.
     * @return True if the animal was placed. The animal cannot be placed if the map is already occupied.
     */
    void place(Animal animal) throws IllegalArgumentException;

    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position
     *            Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2d position);

    /**
     * Performs all that needs to be done during one day of the simulation.
     */
    void performActions();

    Collection<Animal> getAnimals();

    /**
     * This allows the map to transform the movement of an animal, and put it in an appropriate spot.
     * @param in
     * @return
     */
    default Vector2d mapCoordinates(Vector2d in)
    {
        return in;
    }
}