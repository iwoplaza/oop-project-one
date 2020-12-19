package agh.cs.project1.ui;

import agh.cs.project1.Animal;
import agh.cs.project1.FoldingWorldMap;
import agh.cs.project1.Vector2d;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class FoldingWorldMapView extends MapView
{
    private static final int GRID_CELL_SIZE = 16;

    private FoldingWorldMap map;

    private List<AnimalView> animalViews = new ArrayList<>();

    private JPanel createGridCell()
    {
        JPanel gridCell = new JPanel();

        gridCell.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        return gridCell;
    }

    public FoldingWorldMapView(FoldingWorldMap map)
    {
        this.map = map;

        this.setLayout(null);
        this.setSize(new Dimension(
                GRID_CELL_SIZE * map.getWidth(),
                GRID_CELL_SIZE * map.getHeight()
        ));

        int width = this.map.getWidth();
        int height = this.map.getHeight();

        for (int i = 0; i < width; ++i)
        {
            for (int j = 0; j < height; ++j)
            {
                JPanel gridCell = createGridCell();
                gridCell.setBounds(i * GRID_CELL_SIZE, j * GRID_CELL_SIZE, GRID_CELL_SIZE, GRID_CELL_SIZE);
                this.add(gridCell);
            }
        }

        redraw();
    }

    @Override
    public void redraw()
    {
        for (AnimalView v : this.animalViews)
        {
            this.remove(v);
        }

        this.animalViews.clear();

        int mapWidth = this.map.getWidth();
        int mapHeight = this.map.getHeight();

        for (int x = 0; x < mapWidth; ++x)
        {
            for (int y = 0; y < mapHeight; ++y)
            {
                Vector2d pos = new Vector2d(x, y);
                if (this.map.isOccupied(pos))
                {
                    Object obj = this.map.objectAt(pos);
                    if (obj instanceof Animal)
                    {
                        Animal animal = (Animal) obj;
                        AnimalView animalView = new AnimalView(animal.getOrientation());

                        int margin = (int) (GRID_CELL_SIZE * 0.3);

                        animalView.setBounds(
                                x * GRID_CELL_SIZE + margin,
                                (mapHeight - y - 1) * GRID_CELL_SIZE + margin,
                                GRID_CELL_SIZE - margin * 2,
                                GRID_CELL_SIZE - margin * 2
                        );
                        this.animalViews.add(animalView);
                        this.add(animalView);

                        this.setComponentZOrder(animalView, 0);
                    }
                }
            }
        }

        this.repaint();
    }
}
