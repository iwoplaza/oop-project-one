package agh.cs.project1.util;

public class TransformHelper
{
    /**
     * Gives the in vector rotated by 'radians' radians.
     * @param in The vector to be rotated
     * @param radians How much will the resulting vector be rotated by
     */
    public static Vector2d rotate(Vector2d in, double radians)
    {
        double ix = Math.cos(radians);
        double iy = Math.sin(radians);

        double jx = -Math.sin(radians);
        double jy = Math.cos(radians);

        return new Vector2d(
                (int) (ix * in.x + jx * in.y),
                (int) (iy * in.x + jy * in.y)
        );
    }
}
