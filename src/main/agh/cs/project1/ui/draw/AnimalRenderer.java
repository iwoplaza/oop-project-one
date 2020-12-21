package agh.cs.project1.ui.draw;

import agh.cs.project1.map.element.Animal;
import agh.cs.project1.util.MapDirection;
import agh.cs.project1.util.Vector2d;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalRenderer
{
    private static final int ANIMAL_SIZE = 12;
    private static final int MAX_DETECTABLE_ENERGY = 40;

    private int tileSize;
    private Shape[] shapes = new Shape[MapDirection.values().length];

    public AnimalRenderer(int tileSize)
    {
        this.tileSize = tileSize;

        List<Vector2d> points = new ArrayList<>();
        points.add(new Vector2d(-ANIMAL_SIZE / 2, -ANIMAL_SIZE / 2));
        points.add(new Vector2d(ANIMAL_SIZE * 2 / 3, 0));
        points.add(new Vector2d(-ANIMAL_SIZE / 2, ANIMAL_SIZE / 2));

        for (MapDirection d : MapDirection.values())
        {
            shapes[d.ordinal()] = new Shape(points, d.toRadians());
        }
    }

    public void draw(Graphics2D g, Animal animal)
    {
        int originX = animal.getPosition().x * this.tileSize + this.tileSize / 2;
        int originY = animal.getPosition().y * this.tileSize + this.tileSize / 2;

        g.translate(originX, originY);

        float energyNormalized = Math.min((float) animal.getEnergy() / MAX_DETECTABLE_ENERGY, 1);
        g.setColor(Color.getHSBColor(energyNormalized * 0.1f, 0.7f, energyNormalized * 0.8f));
        this.shapes[animal.getOrientation().ordinal()].fill(g);

        g.translate(-originX, -originY);
    }
}
