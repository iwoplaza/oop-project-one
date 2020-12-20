package agh.cs.project1.ui;

import agh.cs.project1.util.MapDirection;
import agh.cs.project1.util.TransformHelper;
import agh.cs.project1.util.Vector2d;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnimalView extends JPanel
{
    MapDirection direction;

    public AnimalView(MapDirection direction)
    {
        super();

        this.direction = direction;

        this.setLayout(null);
        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.BLACK);

        int originX = this.getWidth() / 2;
        int originY = this.getHeight() / 2;
        int width = (int) (this.getWidth() * 0.6);
        int height = (int) (this.getHeight() * 0.6);

        Vector2d[] points = new Vector2d[] {
                new Vector2d(-width / 2, -height / 2),
                new Vector2d(width * 2 / 3, 0),
                new Vector2d(-width / 2, height / 2),
        };

        List<Vector2d> transformedPoints = Stream.of(points)
                .map(v -> TransformHelper.rotate(v, -this.direction.toRadians()))
                .collect(Collectors.toList());

        int[] xPositions = new int[transformedPoints.size()];
        int[] yPositions = new int[transformedPoints.size()];

        for (int i = 0; i < transformedPoints.size(); ++i)
        {
            Vector2d point = transformedPoints.get(i);
            xPositions[i] = point.x + originX;
            yPositions[i] = point.y + originY;
        }

        g2d.fillPolygon(xPositions, yPositions, points.length);
    }
}
