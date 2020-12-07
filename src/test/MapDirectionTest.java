import agh.cs.project1.MapDirection;
import org.junit.Assert;
import org.junit.Test;

public class MapDirectionTest
{

    private static class StepChain
    {
        private int step;
        private MapDirection[] chain;

        public StepChain(int step, MapDirection[] chain)
        {
            this.step = step;
            this.chain = chain;
        }
    }

    private static final StepChain[] stepChains = new StepChain[] {
            new StepChain(1, new MapDirection[] {
                    MapDirection.NORTH,
                    MapDirection.NORTH_EAST,
                    MapDirection.EAST,
                    MapDirection.SOUTH_EAST,
                    MapDirection.SOUTH,
                    MapDirection.SOUTH_WEST,
                    MapDirection.WEST,
                    MapDirection.NORTH_WEST,
            }),
            new StepChain(2, new MapDirection[] {
                    MapDirection.NORTH,
                    MapDirection.EAST,
                    MapDirection.SOUTH,
                    MapDirection.WEST,
            }),
            new StepChain(3, new MapDirection[] {
                    MapDirection.NORTH,
                    MapDirection.SOUTH_EAST,
                    MapDirection.WEST,
                    MapDirection.NORTH_EAST,
                    MapDirection.SOUTH,
                    MapDirection.NORTH_WEST,
                    MapDirection.EAST,
                    MapDirection.SOUTH_WEST,
            }),
            new StepChain(4, new MapDirection[] {
                    MapDirection.NORTH,
                    MapDirection.SOUTH,
            }),
    };

    @Test
    public void rotatingByIncrements()
    {
        for (StepChain stepChain : stepChains)
        {
            for (int i = 0; i < stepChain.chain.length; ++i)
            {
                MapDirection current = stepChain.chain[i];
                MapDirection next = stepChain.chain[(i + 1) % stepChain.chain.length];
                Assert.assertEquals(current.rotatedBy(stepChain.step), next);
            }
        }
    }
}
