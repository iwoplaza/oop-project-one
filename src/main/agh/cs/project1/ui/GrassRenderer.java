package agh.cs.project1.ui;

import agh.cs.project1.map.element.Grass;
import java.awt.*;

public class GrassRenderer
{
    int[] xPositions;
    int[] yPositions;

    public GrassRenderer(int tileSize)
    {
        super();

        this.xPositions = new int[] { tileSize / 2, tileSize,     tileSize / 2, 0 };
        this.yPositions = new int[] { 0           , tileSize / 2, tileSize,     tileSize / 2 };
    }

    public void draw(Graphics2D g, Grass grass)
    {
        g.setColor(Color.GREEN);

        g.fillPolygon(this.xPositions, this.yPositions, 4);
    }
}
