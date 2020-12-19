package agh.cs.project1.ui;

import agh.cs.project1.MapDirection;

import javax.swing.*;
import java.awt.*;

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

        int[] xPositions;
        int[] yPositions;

        switch (this.direction)
        {
            case NORTH:
                xPositions = new int[] { 0, this.getWidth() / 2, this.getWidth() };
                yPositions = new int[] { this.getHeight(), 0, this.getHeight() };
                break;
            case EAST:
                xPositions = new int[] { 0, this.getWidth(), 0 };
                yPositions = new int[] { 0, this.getHeight() / 2, this.getHeight() };
                break;
            case SOUTH:
                xPositions = new int[] { 0, this.getWidth() / 2, this.getWidth() };
                yPositions = new int[] { 0, this.getHeight(), 0 };
                break;
            default:
                xPositions = new int[] { this.getWidth(), 0, this.getWidth() };
                yPositions = new int[] { 0, this.getHeight() / 2, this.getHeight() };
                break;
        }

        g2d.fillPolygon(xPositions, yPositions, 3);
    }
}
