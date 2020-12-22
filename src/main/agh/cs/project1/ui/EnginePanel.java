package agh.cs.project1.ui;

import agh.cs.project1.IEngine;
import agh.cs.project1.map.FoldingJungleMap;
import agh.cs.project1.statistics.SimulationStatistics;
import agh.cs.project1.statistics.StatisticsGatherer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

public class EnginePanel extends JPanel implements ActionListener, Runnable
{

    private IEngine engine;
    private MapView mapView;
    private StatisticsGatherer statisticsGatherer;

    private final int updateInterval = 100;
    private boolean running = false;

    private StatisticsPanel.Item animalCountStat = new StatisticsPanel.Item("Animal count", "0");
    private StatisticsPanel.Item plantCountStat = new StatisticsPanel.Item("Plant count", "");
    private StatisticsPanel.Item dominantGeneStat = new StatisticsPanel.Item("Dominant gene", "");
    private StatisticsPanel.Item averageEnergyStat = new StatisticsPanel.Item("Average energy", "");
    private StatisticsPanel.Item averageLifespanStat = new StatisticsPanel.Item("Average lifespan", "");
    private StatisticsPanel.Item averageChildCountStat = new StatisticsPanel.Item("Average child count", "");

    private JLabel dayLabel;
    private ReportPanel reportPanel;
    private JButton pauseButton;

    IWorldTickObserver worldTickObserver;

    public EnginePanel(IEngine engine, IWorldTickObserver worldTickObserver, IAnimalSelectedObserver animalSelectedObserver)
    {
        this.engine = engine;
        this.worldTickObserver = worldTickObserver;
        this.statisticsGatherer = new StatisticsGatherer(engine);

        this.setLayout(new BorderLayout());
        this.mapView = createMapView(engine, animalSelectedObserver);
        this.add(this.mapView, BorderLayout.CENTER);

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
        header.setLayout(new GridLayout(0, 2));
        this.add(header, BorderLayout.PAGE_START);

        this.dayLabel = new JLabel("Day: 0");
        this.dayLabel.setFont(new Font("Sans-Serif", Font.PLAIN, 22));
        header.setBorder(BorderFactory.createEmptyBorder(0, 20, 0, 0));
        header.add(this.dayLabel);

        StatisticsPanel statistics = new StatisticsPanel(Arrays.asList(
                animalCountStat,
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

        this.reportPanel = new ReportPanel(this.engine);
        footer.add(this.reportPanel);
    }

    private MapView createMapView(IEngine engine, IAnimalSelectedObserver animalSelectedObserver)
    {
        if (engine.getMap() instanceof FoldingJungleMap)
        {
            return new FoldingJungleMapView(engine, (FoldingJungleMap) engine.getMap(), animalSelectedObserver);
        }
        else
        {
            throw new IllegalArgumentException("Engines have to have a map of type FoldingJungleMap. Others are not supported");
        }
    }

    public void setRunning(boolean value)
    {
        this.running = value;
        this.reportPanel.setRunning(value);
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

                this.worldTickObserver.onWorldTick(this.engine);
                this.reportPanel.onWorldTick();

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

        this.dayLabel.setText(String.format("Day: %d", this.engine.getMap().getDay()));

        // Updating statistics
        this.updateStatistics();
    }

    private void updateStatistics()
    {
        SimulationStatistics statistics = this.statisticsGatherer.getStatistics();
        this.animalCountStat.setValue(statistics.getAnimalCount());
        this.plantCountStat.setValue(statistics.getPlantCount());
        this.dominantGeneStat.setValue(statistics.getDominantGene());
        this.averageEnergyStat.setValue(statistics.getAverageEnergy());
        this.averageLifespanStat.setValue(statistics.getAverageLifespan());
        this.averageChildCountStat.setValue(statistics.getAverageChildCount());
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
