package agh.cs.project1.ui.draw;

import agh.cs.project1.map.element.Grass;

import java.awt.*;

public class GrassRenderer
{
    private int[] xPositions;
    private int[] yPositions;
    private int tileSize;

    public GrassRenderer(int tileSize)
    {
        super();

        this.tileSize = tileSize;
        this.xPositions = new int[] { tileSize / 2, tileSize,     tileSize / 2, 0 };
        this.yPositions = new int[] { 0           , tileSize / 2, tileSize,     tileSize / 2 };
    }

    public void draw(Graphics2D g, Grass grass)
    {
        g.setColor(Color.GREEN);

        int originX = grass.getPosition().x * this.tileSize;
        int originY = grass.getPosition().y * this.tileSize;
        g.translate(originX, originY);
        g.fillPolygon(this.xPositions, this.yPositions, 4);
        g.translate(-originX, -originY);
    }
}
