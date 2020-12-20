package agh.cs.project1.ui;

import agh.cs.project1.map.IWorldMap;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

public abstract class MapView extends JPanel implements Runnable
{
    protected static final int GRID_CELL_SIZE = 16;

    private boolean running = false;
    private BufferedImage bufferImage = null;
    protected Graphics2D bg;

    public MapView(int width, int height)
    {
        setBackground(Color.blue);

        this.setSize(new Dimension(
                GRID_CELL_SIZE * width,
                GRID_CELL_SIZE * height
        ));

        Dimension dim = getSize();
        bufferImage = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_ARGB);
        this.bg = bufferImage.createGraphics();
    }

    @Override
    public void run()
    {

    }

    protected abstract void draw(Graphics2D g);

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);

        // Clearing the buffer
        bg.setColor(Color.white);
        bg.clearRect(0, 0, this.getWidth(), this.getHeight());

        this.draw(bg);

        AffineTransform at = new AffineTransform();
        at.concatenate(AffineTransform.getScaleInstance(1, -1));
        at.concatenate(AffineTransform.getTranslateInstance(0, -bufferImage.getHeight()));
        ((Graphics2D) g).transform(at);
        g.drawImage(bufferImage, 0, 0, this);
    }

}
