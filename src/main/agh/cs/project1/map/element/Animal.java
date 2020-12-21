package agh.cs.project1.map.element;

import agh.cs.project1.Genome;
import agh.cs.project1.IPositionChangeObserver;
import agh.cs.project1.util.MapDirection;
import agh.cs.project1.util.Vector2d;
import agh.cs.project1.map.IWorldMap;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Animal extends AbstractWorldMapElement
{
    private final int GENOME_CAPACITY = 32;

    private UUID uuid;
    private MapDirection orientation;
    private Genome genome = Genome.createRandomized(GENOME_CAPACITY);
    private int reproduceEnergy;
    private int energy = 0;

    private final List<IPositionChangeObserver> positionObservers = new ArrayList<>();

    public Animal(IWorldMap map, Vector2d initialPosition, int initialEnergy, int reproduceEnergy)
    {
        super(map, initialPosition);
        this.uuid = UUID.randomUUID();
        this.orientation = MapDirection.generateRandom();
        this.reproduceEnergy = reproduceEnergy;
        this.energy = initialEnergy;
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

    public int getEnergy()
    {
        return this.energy;
    }

    public boolean isDead()
    {
        return this.energy <= 0;
    }

    public boolean canReproduce()
    {
        return this.energy >= this.reproduceEnergy / 2;
    }

    public MapDirection getOrientation()
    {
        return this.orientation;
    }

    public void performActions()
    {
        this.orientation = this.orientation.rotatedBy(this.genome.pickRandomGene());
        this.moveBy(this.orientation.toUnitVector());

        // Decreasing the animal's energy after performing an action.
        this.energy--;
    }

    public void reproduceWith(Animal otherParent)
    {
        // TODO Implement this
    }

    public void gainEnergy(int energy)
    {
        if (energy < 0)
        {
            throw new IllegalArgumentException("The energy gained must be positive");
        }

        this.energy += energy;
    }

    public void addObserver(IPositionChangeObserver observer)
    {
        this.positionObservers.add(observer);
    }

    public void removeObserver(IPositionChangeObserver observer)
    {
        this.positionObservers.remove(observer);
    }

    private void moveBy(Vector2d offset)
    {
        Vector2d newPosition = this.position.add(offset);

        if (this.map.canMoveTo(newPosition))
        {
            Vector2d oldPosition = new Vector2d(this.position.x, this.position.y);
            newPosition = this.map.mapCoordinates(newPosition);
            this.position = newPosition;
            this.positionChanged(oldPosition);
        }
    }

    private void positionChanged(Vector2d oldPosition)
    {
        this.positionObservers.forEach(observer -> observer.positionChanged(this, oldPosition, getPosition()));
    }

    public static int animalEnergyComparator(Animal a, Animal b)
    {
        int energyCompare = Integer.compare(a.energy, b.energy);
        if (energyCompare != 0)
            return energyCompare;

        return a.uuid.compareTo(b.uuid);
    }
}
