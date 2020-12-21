package agh.cs.project1.ui;

import agh.cs.project1.IEngine;
import agh.cs.project1.map.FoldingJungleMap;
import agh.cs.project1.map.element.Animal;
import agh.cs.project1.map.element.Grass;
import agh.cs.project1.ui.draw.AnimalRenderer;
import agh.cs.project1.ui.draw.GrassRenderer;
import agh.cs.project1.util.Vector2d;

import java.awt.*;

public class FoldingJungleMapView extends MapView
{
    private static final Color JUNGLE_COLOR = Color.getHSBColor(0.3f, 0.4f, 0.8f);
    private static final Color PLAINS_COLOR = Color.getHSBColor(0.1f, 0.3f, 0.9f);

    private final FoldingJungleMap map;

    private final AnimalRenderer animalRenderer;
    private final GrassRenderer grassRenderer;

    public FoldingJungleMapView(IEngine engine, FoldingJungleMap map)
    {
        super(engine, map.getWidth(), map.getHeight());
        this.map = map;

        this.animalRenderer = new AnimalRenderer(this.gridCellSize);
        this.grassRenderer = new GrassRenderer(this.gridCellSize);

        this.setLayout(null);
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
}
