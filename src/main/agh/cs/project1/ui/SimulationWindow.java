package agh.cs.project1.ui;

import agh.cs.project1.IEngine;
import agh.cs.project1.map.element.Animal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class SimulationWindow extends JFrame implements ActionListener, IAnimalSelectedObserver, IWorldTickObserver
{
    private static final int HEADER_HEIGHT = 40;
    private static final int FOOTER_HEIGHT = 40;

    private JPanel panel;

    private JPanel header;
    private List<EnginePanel> enginePanels;
    private JPanel footer;
    private OptionsPanel optionsPanel;

    private JButton pauseAllButton;
    private JButton resumeAllButton;

    public SimulationWindow(IEngine[] engines)
    {
        super("Animal Simulation");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        Container content = this.getContentPane();

        List<IEngine> engines1 = Arrays.asList(engines);
        this.enginePanels = engines1.stream().map(e -> new EnginePanel(e, this, this)).collect(Collectors.toList());

        this.createPanel();
        content.add(this.panel, BorderLayout.CENTER);

        this.createHeader();
        content.add(this.header, BorderLayout.PAGE_START);

        this.createFooter();
        content.add(this.footer, BorderLayout.PAGE_END);

        this.createOptionsPanel();
        content.add(this.optionsPanel, BorderLayout.LINE_START);

        this.pack();
    }

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

    private void createOptionsPanel()
    {
        this.optionsPanel = new OptionsPanel();
    }

    private void createPanel()
    {
        this.panel = new JPanel();
        this.panel.setLayout(new BoxLayout(this.panel, BoxLayout.X_AXIS));
        int border = 10;
        this.panel.setBorder(BorderFactory.createEmptyBorder(border, border, border, border));

        this.panel.setBackground(Color.LIGHT_GRAY);

        for (int i = 0; i < enginePanels.size(); ++i)
        {
            this.panel.add(enginePanels.get(i));
            if (i < enginePanels.size() - 1)
            {
                this.panel.add(Box.createRigidArea(new Dimension(border, 0)));
            }
        }
    }

    private void resumeAllMaps()
    {
        this.enginePanels.forEach(v -> v.setRunning(true));
    }

    private void pauseAllMaps()
    {
        this.enginePanels.forEach(v -> v.setRunning(false));
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

    @Override
    public void onAnimalSelected(Animal animal)
    {
        this.optionsPanel.trackAnimal(animal);
    }

    @Override
    public void onWorldTick(IEngine engine)
    {
        this.optionsPanel.updateStatistics();
    }
}
