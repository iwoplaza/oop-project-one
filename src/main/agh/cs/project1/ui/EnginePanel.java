package agh.cs.project1.ui;

import agh.cs.project1.IEngine;
import agh.cs.project1.map.FoldingJungleMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnginePanel extends JPanel implements ActionListener
{

    private MapView mapView;
    private JButton pauseButton;

    public EnginePanel(IEngine engine)
    {
        this.mapView = createMapView(engine);

        this.setLayout(new BorderLayout());
        this.add(this.mapView, BorderLayout.CENTER);

        JPanel footer = new JPanel();
        this.add(footer, BorderLayout.PAGE_END);

        this.pauseButton = new JButton(this.mapView.isRunning() ? "Pause" : "Resume");
        this.pauseButton.addActionListener(this);
        footer.add(this.pauseButton);
    }

    private MapView createMapView(IEngine engine)
    {
        if (engine.getMap() instanceof FoldingJungleMap)
        {
            MapView view = new FoldingJungleMapView(engine, (FoldingJungleMap) engine.getMap());

            Thread thread = new Thread(view);
            thread.start();

            return view;
        }
        else
        {
            throw new IllegalArgumentException("Engines have to have a map of type FoldingJungleMap. Others are not supported");
        }
    }

    public void setRunning(boolean value)
    {
        this.mapView.setRunning(value);
        this.pauseButton.setText(value ? "Pause" : "Resume");
    }

    public boolean isRunning()
    {
        return this.mapView.isRunning();
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
