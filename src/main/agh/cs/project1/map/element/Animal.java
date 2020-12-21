package agh.cs.project1.map.element;

import agh.cs.project1.Genome;
import agh.cs.project1.IPositionChangeObserver;
import agh.cs.project1.map.IWorldMap;
import agh.cs.project1.util.MapDirection;
import agh.cs.project1.util.RandomHelper;
import agh.cs.project1.util.Vector2d;
import sun.plugin.dom.exception.InvalidStateException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class Animal extends AbstractWorldMapElement
{
    private static final int GENOME_CAPACITY = 32;
    private static final Random rand = new Random();

    private int uniqueId;
    private MapDirection orientation;
    private Genome genome;
    private final int moveEnergy;
    private final int reproduceEnergy;
    private int energy = 0;

    private final List<IPositionChangeObserver> positionObservers = new ArrayList<>();

    public Animal(IWorldMap map, Vector2d initialPosition, int initialEnergy, int moveEnergy, int reproduceEnergy, Genome genome)
    {
        super(map, initialPosition);
        this.orientation = MapDirection.generateRandom();
        this.moveEnergy = moveEnergy;
        this.reproduceEnergy = reproduceEnergy;
        this.energy = initialEnergy;
        this.genome = genome;
    }

    public Animal(IWorldMap map, Vector2d initialPosition, int initialEnergy, int moveEnergy, int reproduceEnergy)
    {
        this(map, initialPosition, initialEnergy, moveEnergy, reproduceEnergy, Genome.createRandomized(GENOME_CAPACITY));
    }

    @Override
    public String toString()
    {
        return String.format("Animal [ID: %d]", this.uniqueId);
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
        this.energy -= this.moveEnergy;
    }

    public Animal reproduceWith(Animal otherParent)
    {
        // Finding initial position of the child
        Vector2d emptySpot = RandomHelper.findRandomPositionWithinSmallSpaceWhere(
                new Vector2d(position.x - 1, position.y - 1),
                new Vector2d(position.x + 1, position.y + 1),
                v -> !v.equals(position) && !this.map.isOccupied(position));

        if (emptySpot == null)
        {
            emptySpot = RandomHelper.findRandomPositionWithinSmallSpaceWhere(
                    new Vector2d(position.x - 1, position.y - 1),
                    new Vector2d(position.x + 1, position.y + 1),
                    v -> !v.equals(position));
        }

        int initialEnergy = this.takeReproductionEnergy() + otherParent.takeReproductionEnergy();

        // Choosing the genome slices randomly in such a way, that
        // Each of the 3 slices has at least one element inside.
        int firstSlice = rand.nextInt(GENOME_CAPACITY - 1);
        int secondSlice;
        do {
           secondSlice = rand.nextInt(GENOME_CAPACITY - 1);
        }
        while(secondSlice == firstSlice);

        if (firstSlice > secondSlice)
        {
            // Swapping the order.
            int tmp = firstSlice;
            firstSlice = secondSlice;
            secondSlice = tmp;
        }

        Genome mergedGenome = Genome.combine(this.genome, otherParent.genome, firstSlice, secondSlice);

        // Making sure the genome is fairly distributed.
        mergedGenome.redistribute();

        return new Animal(this.map, emptySpot, initialEnergy, this.moveEnergy, this.reproduceEnergy, mergedGenome);
    }

    public int takeReproductionEnergy()
    {
        int taken = this.energy / 4;
        this.takeEnergy(taken);
        return taken;
    }

    public void takeEnergy(int energy)
    {
        if (energy < 0)
        {
            throw new IllegalArgumentException("The energy taken must be positive");
        }

        this.energy -= energy;
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
        newPosition = this.map.mapCoordinates(newPosition);

        if (this.map.canMoveTo(newPosition))
        {
            Vector2d oldPosition = new Vector2d(this.position.x, this.position.y);
            this.position = newPosition;
            this.positionChanged(oldPosition);
        }
    }

    private void positionChanged(Vector2d oldPosition)
    {
        this.positionObservers.forEach(observer -> observer.positionChanged(this, oldPosition));
    }

    public static int animalEnergyComparator(Animal a, Animal b)
    {
        return -Integer.compare(a.energy, b.energy);
    }
}
