package agh.cs.project1.ui;

import javax.swing.*;
import java.awt.*;

public class GrassView extends JPanel
{
    public GrassView()
    {
        super();

        this.setLayout(null);
        this.setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.setColor(Color.GREEN);

        int[] xPositions;
        int[] yPositions;

        xPositions = new int[] { 0, this.getWidth() / 2, this.getWidth() };
        yPositions = new int[] { this.getHeight(), 0, this.getHeight() };

        g2d.fillPolygon(xPositions, yPositions, 3);
    }
}
