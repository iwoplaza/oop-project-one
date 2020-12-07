package agh.cs.project1;

import java.util.ArrayList;
import java.util.List;

public class Animal extends AbstractWorldMapElement
{
    private final int GENOME_CAPACITY = 32;
    private MapDirection orientation;
    private Genome genome = Genome.createRandomized(GENOME_CAPACITY);

    private final List<IPositionChangeObserver> observers = new ArrayList<>();

    public Animal(IWorldMap map)
    {
        this(map, new Vector2d(2, 2));
    }

    public Animal(IWorldMap map, Vector2d initialPosition)
    {
        super(map, initialPosition);
        this.orientation = MapDirection.generateRandom();
    }

    @Override
    public String toString()
    {
        switch(this.orientation) {
            case NORTH:
                return "^";
            case EAST:
                return ">";
            case SOUTH:
                return "v";
            case WEST:
                return "<";
        }
        return "?";
    }

    public MapDirection getOrientation()
    {
        return this.orientation;
    }

    public void move()
    {
        this.orientation = this.orientation.rotatedBy(this.genome.pickRandomGene());
        this.moveBy(this.orientation.toUnitVector());
    }

    public void addObserver(IPositionChangeObserver observer)
    {
        this.observers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer)
    {
        this.observers.remove(observer);
    }

    private void moveBy(Vector2d offset)
    {
        Vector2d newPosition = this.position.add(offset);
        if (this.map.canMoveTo(newPosition))
        {
            Vector2d oldPosition = new Vector2d(this.position.x, this.position.y);
            this.position = newPosition;
            this.positionChanged(oldPosition);
        }
    }

    private void positionChanged(Vector2d oldPosition)
    {
        this.observers.forEach(observer -> observer.positionChanged(oldPosition, getPosition()));
    }
}
