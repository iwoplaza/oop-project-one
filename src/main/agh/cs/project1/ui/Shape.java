package agh.cs.project1.ui;

import agh.cs.project1.util.TransformHelper;
import agh.cs.project1.util.Vector2d;

import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Shape
{
    private final int[] xPositions;
    private final int[] yPositions;

    public Shape(List<Vector2d> points)
    {
        this.xPositions = new int[points.size()];
        this.yPositions = new int[points.size()];

        for (int i = 0; i < points.size(); ++i)
        {
            Vector2d point = points.get(i);
            xPositions[i] = point.x;
            yPositions[i] = point.y;
        }
    }

    public Shape(List<Vector2d> points, double rotationInRadians)
    {
        List<Vector2d> transformedPoints = points.stream()
                .map(v -> TransformHelper.rotate(v, rotationInRadians))
                .collect(Collectors.toList());

        this.xPositions = new int[transformedPoints.size()];
        this.yPositions = new int[transformedPoints.size()];

        for (int i = 0; i < transformedPoints.size(); ++i)
        {
            Vector2d point = transformedPoints.get(i);
            xPositions[i] = point.x;
            yPositions[i] = point.y;
        }
    }

    public void fill(Graphics2D g)
    {
        g.fillPolygon(xPositions, yPositions, xPositions.length);
    }

    public void stroke(Graphics2D g)
    {
        g.drawPolygon(xPositions, yPositions, xPositions.length);
    }
}
