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

    private Container content;
    private JPanel panel;

    private JPanel header;
    private MapView mapView;
    private JPanel footer;

    private void createHeader()
    {
        this.header = new JPanel();

        JLabel label = new JLabel("Animal Simulation");
        label.setBounds(10, 10, 200, HEADER_HEIGHT);
        label.setFont(new Font("Arial", (Font.BOLD), 16));
        label.setForeground(Color.BLACK);
        this.header.add(label);

        this.header.setPreferredSize(new Dimension(this.panel.getWidth(), HEADER_HEIGHT));
    }

    private void createMapView()
    {
        if (this.engine.getMap() instanceof FoldingJungleMap)
        {
            this.mapView = new FoldingJungleMapView((FoldingJungleMap) this.engine.getMap());
        }
    }

    private void createFooter()
    {
        this.footer = new JPanel();
        this.footer.setBackground(Color.WHITE);
        this.footer.setPreferredSize(new Dimension(this.panel.getWidth(), FOOTER_HEIGHT));

        JButton button = new JButton("Next step");
        button.addActionListener(this);
        this.footer.add(button);
    }

    private void createPanel()
    {
        this.panel = new JPanel();
        this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.PAGE_AXIS));
        int border = 10;
        this.panel.setBorder(BorderFactory.createEmptyBorder(border, border, border, border));
        this.panel.setPreferredSize(new Dimension(mapView.getWidth() + border * 2, mapView.getHeight() + border * 2));
        this.panel.setBackground(Color.LIGHT_GRAY);
        this.panel.add(this.mapView);
    }

    public SimulationWindow(IEngine engine)
    {
        super("Animal Simulation");
        this.engine = engine;

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.createMapView();

        setResizable(true);

        this.content = this.getContentPane();

        this.createPanel();
        this.content.add(this.panel, BorderLayout.CENTER);

        this.createHeader();
        this.content.add(this.header, BorderLayout.PAGE_START);

        this.createFooter();
        this.content.add(this.footer, BorderLayout.PAGE_END);

        this.pack();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {

    }
}
