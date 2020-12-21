package agh.cs.project1.ui;

import agh.cs.project1.AnimalTracker;
import agh.cs.project1.map.element.Animal;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class OptionsPanel extends JPanel
{

    private StatisticsPanel.Item childrenCount = new StatisticsPanel.Item("Children", "-");
    private StatisticsPanel.Item descendantsCount = new StatisticsPanel.Item("Descendants", "-");
    private StatisticsPanel.Item diedOnDay = new StatisticsPanel.Item("Died on (day)", "-");
    private AnimalTracker animalTracker = null;

    public OptionsPanel()
    {
        this.setLayout(new GridLayout(0, 1, 10, 20));
        this.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));

        JPanel selectedStatsContainer = new JPanel();
        StatisticsPanel selectedStatsPanel = new StatisticsPanel(Arrays.asList(
                childrenCount,
                descendantsCount,
                diedOnDay
        ));
        selectedStatsContainer.add(selectedStatsPanel);

        this.add(selectedStatsContainer);
    }

    public void trackAnimal(Animal animal)
    {
        if (this.animalTracker != null)
        {
            this.animalTracker.dispose();
        }

        this.animalTracker = animal != null ? new AnimalTracker(animal) : null;

        this.updateStatistics();
    }

    public void updateStatistics()
    {
        if (this.animalTracker != null)
        {
            this.childrenCount.setValue(String.format("%d", this.animalTracker.getChildrenCount()));
            this.descendantsCount.setValue(String.format("%d", this.animalTracker.getDescendantsCount()));
            this.diedOnDay.setValue(this.animalTracker.getDayDiedOnAsString());
        }
        else
        {
            this.childrenCount.setValue("-");
            this.descendantsCount.setValue("-");
            this.diedOnDay.setValue("-");
        }
    }


}
