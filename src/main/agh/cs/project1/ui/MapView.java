package agh.cs.project1.ui;

import agh.cs.project1.IEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class MapView extends JPanel
{
    protected static final int GRID_CELL_SIZE = 16;

    private IEngine engine;
    private BufferedImage bufferImage = null;
    protected Graphics2D bg;

    private int canvasWidth;
    private int canvasHeight;

    public MapView(IEngine engine, int width, int height)
    {
        this.engine = engine;

        setBackground(Color.blue);

        this.canvasWidth = GRID_CELL_SIZE * width;
        this.canvasHeight = GRID_CELL_SIZE * height;
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
