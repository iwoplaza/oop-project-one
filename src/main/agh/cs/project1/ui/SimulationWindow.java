package agh.cs.project1.ui;

import agh.cs.project1.map.FoldingJungleMap;
import agh.cs.project1.IEngine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SimulationWindow extends JFrame implements ActionListener
{
    private static final int HEADER_HEIGHT = 40;
    private static final int FOOTER_HEIGHT = 40;

    private List<IEngine> engines;

    private Container content;
    private JPanel panel;

    private JPanel header;
    private List<MapView> mapViews;
    private JPanel footer;

    private JButton pauseAllButton;
    private JButton resumeAllButton;

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

    private FoldingJungleMapView createMapView(IEngine engine)
    {
        if (engine.getMap() instanceof FoldingJungleMap)
        {
            FoldingJungleMapView view = new FoldingJungleMapView(engine, (FoldingJungleMap) engine.getMap());

            Thread thread = new Thread(view);
            thread.start();

            return view;
        }
        else
        {
            throw new IllegalArgumentException("Engines have to have a map of type FoldingJungleMap. Others are not supported");
        }
    }

    private void createFooter()
    {
        this.footer = new JPanel();
        this.footer.setBackground(Color.WHITE);
        this.footer.setPreferredSize(new Dimension(this.panel.getWidth(), FOOTER_HEIGHT));

        this.pauseAllButton = new JButton("Pause All");
        this.pauseAllButton.addActionListener(this);
        this.footer.add(this.pauseAllButton);

        this.resumeAllButton = new JButton("Resume All");
        this.resumeAllButton.addActionListener(this);
        this.footer.add(this.resumeAllButton);
    }

    private void createPanel()
    {
        this.panel = new JPanel();
        this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.X_AXIS));
        int border = 10;
        this.panel.setBorder(BorderFactory.createEmptyBorder(border, border, border, border));

        int mapViewWidths = mapViews.stream().mapToInt(JComponent::getWidth).reduce(0, Integer::sum);
        int mapViewMaxHeight = mapViews.stream().mapToInt(JComponent::getHeight).max().orElse(100);

        this.panel.setPreferredSize(new Dimension(mapViewWidths + (mapViews.size() + 1) * border, mapViewMaxHeight + border * 2));
        this.panel.setBackground(Color.LIGHT_GRAY);

        for (int i = 0; i < mapViews.size(); ++i)
        {
            this.panel.add(mapViews.get(i));
            if (i < mapViews.size() - 1)
            {
                this.panel.add(Box.createRigidArea(new Dimension(border, 0)));
            }
        }
    }

    public SimulationWindow(IEngine[] engines)
    {
        super("Animal Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);

        this.content = this.getContentPane();

        this.engines = Arrays.asList(engines);
        this.mapViews = this.engines.stream().map(this::createMapView).collect(Collectors.toList());

        this.createPanel();
        this.content.add(this.panel, BorderLayout.CENTER);

        this.createHeader();
        this.content.add(this.header, BorderLayout.PAGE_START);

        this.createFooter();
        this.content.add(this.footer, BorderLayout.PAGE_END);

        this.pack();
    }

    private void refreshView()
    {
    }

    private void resumeAllMaps()
    {
        this.mapViews.forEach(v -> v.setRunning(true));
    }

    private void pauseAllMaps()
    {
        this.mapViews.forEach(v -> v.setRunning(false));
        this.refreshView();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.pauseAllButton)
        {
            this.pauseAllMaps();
        }
        else if(e.getSource() == this.resumeAllButton)
        {
            this.resumeAllMaps();
        }
    }
}
