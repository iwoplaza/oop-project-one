import agh.cs.project1.util.Vector2d;
import org.junit.Assert;
import org.junit.Test;

public class Vector2dTest
{
    @Test
    public void equals()
    {
        Vector2d primary = new Vector2d(1, 2);
        Assert.assertEquals(primary, primary);
        Assert.assertEquals(primary, new Vector2d(1, 2));
        Assert.assertNotEquals(primary, new Vector2d(1, 3));
        Assert.assertNotEquals(primary, null);
        Assert.assertNotEquals(primary, "Hello world");
    }

    @Test
    public void toStringTest()
    {
        Vector2d primary = new Vector2d(1, 2);
        Assert.assertEquals(primary.toString(), "(1,2)");
        Assert.assertNotEquals(primary.toString(), "(1, 2)");
        Assert.assertNotEquals(primary.toString(), "(1,3)");
    }

    @Test
    public void precedes()
    {
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(3, 4);

        Assert.assertTrue(v1.precedes(v1));
        Assert.assertTrue(v1.precedes(v2));
        Assert.assertFalse(v2.precedes(v1));
    }

    @Test
    public void follows()
    {
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(3, 4);

        Assert.assertTrue(v2.follows(v2));
        Assert.assertTrue(v2.follows(v1));
        Assert.assertFalse(v1.follows(v2));
    }

    @Test
    public void upperRight()
    {
        Vector2d v1 = new Vector2d(5, 2);
        Vector2d v2 = new Vector2d(3, 4);

        Assert.assertEquals(v1.upperRight(v2), new Vector2d(5, 4));
    }

    @Test
    public void lowerLeft()
    {
        Vector2d v1 = new Vector2d(5, 2);
        Vector2d v2 = new Vector2d(3, 4);

        Assert.assertEquals(v1.lowerLeft(v2), new Vector2d(3, 2));
    }

    @Test
    public void add()
    {
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(3, 4);

        Assert.assertEquals(v1.add(v2), new Vector2d(4, 6));
    }

    @Test
    public void subtract()
    {
        Vector2d v1 = new Vector2d(1, 2);
        Vector2d v2 = new Vector2d(3, 4);

        Assert.assertEquals(v1.subtract(v2), new Vector2d(-2, -2));
    }

    @Test
    public void opposite()
    {
        Vector2d v1 = new Vector2d(1, 2);

        Assert.assertEquals(v1.opposite(), new Vector2d(-1, -2));
    }
}
