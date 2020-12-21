package agh.cs.project1.ui;

import agh.cs.project1.IEngine;
import agh.cs.project1.map.FoldingJungleMap;
import agh.cs.project1.map.element.Animal;
import agh.cs.project1.map.element.Grass;
import agh.cs.project1.ui.draw.AnimalRenderer;
import agh.cs.project1.ui.draw.GrassRenderer;
import agh.cs.project1.util.Vector2d;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

public class FoldingJungleMapView extends MapView implements MouseListener
{
    private static final Color JUNGLE_COLOR = Color.getHSBColor(0.3f, 0.4f, 0.8f);
    private static final Color PLAINS_COLOR = Color.getHSBColor(0.1f, 0.3f, 0.9f);

    private final FoldingJungleMap map;

    private final AnimalRenderer animalRenderer;
    private final GrassRenderer grassRenderer;
    private IAnimalSelectedObserver animalSelectedObserver;

    public FoldingJungleMapView(IEngine engine, FoldingJungleMap map, IAnimalSelectedObserver animalSelectedObserver)
    {
        super(map.getWidth(), map.getHeight());
        this.animalSelectedObserver = animalSelectedObserver;
        this.map = map;

        this.animalRenderer = new AnimalRenderer(this.gridCellSize);
        this.grassRenderer = new GrassRenderer(this.gridCellSize);

        this.setLayout(null);
        this.addMouseListener(this);
    }

    public void drawBackground(Graphics2D g)
    {
        g.setColor(PLAINS_COLOR);
        g.fillRect(0, 0, this.getWidth(), this.getHeight());

        g.setColor(JUNGLE_COLOR);
        Vector2d jungleMin = this.map.getJungleMin();
        Vector2d jungleMax = this.map.getJungleMax();
        g.fillRect(
                jungleMin.x * this.gridCellSize,
                jungleMin.y * this.gridCellSize,
                (jungleMax.x - jungleMin.x + 1) * this.gridCellSize,
                (jungleMax.y - jungleMin.y + 1) * this.gridCellSize);
    }

    @Override
    protected void draw(Graphics2D g)
    {
        this.drawBackground(g);

        for (Grass grass : this.map.getGrassTufts())
        {
            this.grassRenderer.draw(g, grass);
        }

        for (Animal animal : this.map.getAnimals())
        {
            this.animalRenderer.draw(g, animal);
        }
    }

    @Override
    public void mouseClicked(MouseEvent e)
    {
        int tileX = Math.max(0, Math.min(e.getX() / this.gridCellSize,                       this.map.getWidth() - 1));
        int tileY = Math.max(0, Math.min((this.canvasHeight - e.getY()) / this.gridCellSize, this.map.getHeight() - 1));
        List<Animal> animals = this.map.getAnimalsAt(new Vector2d(tileX, tileY));
        if (animals.size() > 0)
        {
            this.animalSelectedObserver.onAnimalSelected(animals.get(0));
        }
        else
        {
            this.animalSelectedObserver.onAnimalSelected(null);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {}

    @Override
    public void mouseExited(MouseEvent e) {}
}
