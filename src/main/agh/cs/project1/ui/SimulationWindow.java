package agh.cs.project1.ui;

import agh.cs.project1.map.FoldingJungleMap;
import agh.cs.project1.IEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulationWindow extends JFrame implements ActionListener
{
    private static final int HEADER_HEIGHT = 40;
    private static final int FOOTER_HEIGHT = 40;

    private IEngine engine;

    private JPanel panel;

    private JPanel header;
    private MapView mapView;
    private JPanel footer;

    private void createHeader()
    {
        this.header = new JPanel();

        JLabel label = new JLabel("Animal Simulation");
        label.setBounds(10, 10, 200, 25);
        label.setFont(new Font("Arial", (Font.BOLD), 16));
        label.setForeground(Color.BLACK);
        this.header.add(label);

        this.header.setBounds(0, 0, getWidth(), HEADER_HEIGHT);
    }

    private void createMapView()
    {
        if (this.engine.getMap() instanceof FoldingJungleMap)
        {
            this.mapView = new FoldingJungleMapView((FoldingJungleMap) this.engine.getMap());
        }
        this.mapView.setLocation(0, HEADER_HEIGHT);
    }

    private void createFooter()
    {
        this.footer = new JPanel();
        this.footer.setBackground(Color.PINK);
        this.footer.setBounds(0, HEADER_HEIGHT + mapView.getHeight(), getWidth(), FOOTER_HEIGHT);

        JButton button = new JButton("Next step");
        button.addActionListener(this);
        this.footer.add(button);
    }

    private void createPanel()
    {
        this.panel = new JPanel();
        this.panel.setLayout(null);
        this.panel.setBackground(Color.RED);
        this.add(this.panel);

        this.createHeader();
        this.panel.add(this.header);

        this.panel.add(this.mapView);

        this.createFooter();
        this.panel.add(this.footer);
    }

    public SimulationWindow(IEngine engine)
    {
        super("Animal Simulation");
        this.engine = engine;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.createMapView();

        setSize(new Dimension(
                mapView.getWidth(),
                HEADER_HEIGHT + mapView.getHeight() + FOOTER_HEIGHT + 28));
        setResizable(false);

        this.createPanel();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        this.engine.runStep();
        this.mapView.redraw();
        this.repaint();
    }
}
