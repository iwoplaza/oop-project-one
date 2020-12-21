package agh.cs.project1.ui;

import agh.cs.project1.IEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class MapView extends JPanel
{
    private IEngine engine;
    private BufferedImage bufferImage = null;
    protected Graphics2D bg;

    protected final int gridCellSize;
    private final int canvasWidth;
    private final int canvasHeight;

    public MapView(IEngine engine, int width, int height)
    {
        this.engine = engine;

        setBackground(Color.blue);

        // Scaling the grid cell size according to the dimensions of the map.
        this.gridCellSize = Math.max(6, Math.min(600 / Math.max(width, height), 20));
        this.canvasWidth = gridCellSize * width;
        this.canvasHeight = gridCellSize * height;
        this.setPreferredSize(new Dimension(this.canvasWidth, this.canvasHeight));

        this.bufferImage = new BufferedImage(this.canvasWidth, this.canvasHeight, BufferedImage.TYPE_INT_ARGB);
        this.bg = bufferImage.createGraphics();
    }

    protected abstract void draw(Graphics2D g);

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);

        // Clearing the buffer
        bg.setColor(Color.white);
        bg.clearRect(0, 0, this.canvasWidth, this.canvasHeight);

        this.draw(bg);

        // Flipping the image upside-down
        AffineTransform at = new AffineTransform();
        at.concatenate(AffineTransform.getScaleInstance(1, -1));
        at.concatenate(AffineTransform.getTranslateInstance(0, -this.canvasHeight));
        ((Graphics2D) g).transform(at);

        g.drawImage(bufferImage, 0, 0, this);
    }

}
