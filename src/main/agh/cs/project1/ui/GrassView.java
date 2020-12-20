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

        xPositions = new int[] { this.getWidth() / 2, this.getWidth(),      this.getWidth() / 2, 0 };
        yPositions = new int[] { 0                  , this.getHeight() / 2, this.getHeight(),    this.getHeight() / 2 };

        g2d.fillPolygon(xPositions, yPositions, 4);
    }
}
