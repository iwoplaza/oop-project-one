package agh.cs.project1.ui;

import agh.cs.project1.IEngine;
import agh.cs.project1.SimulationStatistics;
import agh.cs.project1.map.FoldingJungleMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class EnginePanel extends JPanel implements ActionListener, Runnable
{

    private IEngine engine;
    private SimulationStatistics statistics;

    private final int updateInterval = 100;
    private boolean running = false;

    private StatisticsPanel.Item animalCountStat = new StatisticsPanel.Item("Animal count", "0");
    private StatisticsPanel.Item actualAnimalCountStat = new StatisticsPanel.Item("Actual animal count", "0");
    private StatisticsPanel.Item plantCountStat = new StatisticsPanel.Item("Plant count", "");
    private StatisticsPanel.Item dominantGeneStat = new StatisticsPanel.Item("Dominant gene", "");
    private StatisticsPanel.Item averageEnergyStat = new StatisticsPanel.Item("Average energy", "");
    private StatisticsPanel.Item averageLifespanStat = new StatisticsPanel.Item("Average lifespan", "");
    private StatisticsPanel.Item averageChildCountStat = new StatisticsPanel.Item("Average child count", "");
    private JButton pauseButton;

    public EnginePanel(IEngine engine)
    {
        this.engine = engine;
        this.statistics = new SimulationStatistics(engine);

        this.setLayout(new BorderLayout());
        MapView mapView = createMapView(engine);
        this.add(mapView, BorderLayout.CENTER);

        this.constructUI();
        this.updateStatistics();

        // Thread
        Thread thread = new Thread(this);
        thread.start();
    }

    private void constructUI()
    {
        // Header
        JPanel header = new JPanel();
        header.setLayout(new GridLayout(0, 1));
        this.add(header, BorderLayout.PAGE_START);

        StatisticsPanel statistics = new StatisticsPanel(Arrays.asList(
                animalCountStat,
                actualAnimalCountStat,
                plantCountStat,
                dominantGeneStat,
                averageEnergyStat,
                averageLifespanStat,
                averageChildCountStat
        ));
        header.add(statistics);

        // Footer
        JPanel footer = new JPanel();
        this.add(footer, BorderLayout.PAGE_END);

        this.pauseButton = new JButton(this.isRunning() ? "Pause" : "Resume");
        this.pauseButton.addActionListener(this);
        footer.add(this.pauseButton);
    }

    private MapView createMapView(IEngine engine)
    {
        if (engine.getMap() instanceof FoldingJungleMap)
        {
            return new FoldingJungleMapView(engine, (FoldingJungleMap) engine.getMap());
        }
        else
        {
            throw new IllegalArgumentException("Engines have to have a map of type FoldingJungleMap. Others are not supported");
        }
    }

    public void setRunning(boolean value)
    {
        this.running = value;
        this.pauseButton.setText(value ? "Pause" : "Resume");
    }

    public boolean isRunning()
    {
        return this.running;
    }

    @Override
    public void run()
    {
        while (true)
        {
            if (running)
            {
                // Updating the state of the "game"
                this.update();

                // Repainting the map
                this.repaint();
            }

            try
            {
                Thread.sleep(this.updateInterval);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
                System.out.println("Map view interrupted...");
            }
        }
    }

    protected void update()
    {
        this.engine.runStep();

        // Updating statistics
        this.updateStatistics();
    }

    private void updateStatistics()
    {
        this.animalCountStat.setValue(String.format("%d", this.statistics.getAnimalCount()));
        this.actualAnimalCountStat.setValue(String.format("%d", this.statistics.getActualAnimalCount()));
        this.plantCountStat.setValue(String.format("%d", this.statistics.getPlantCount()));
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.pauseButton)
        {
            this.setRunning(!this.isRunning());
        }
    }
}
