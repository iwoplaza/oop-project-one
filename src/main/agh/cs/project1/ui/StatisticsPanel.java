package agh.cs.project1.ui;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class StatisticsPanel extends JPanel
{

    public StatisticsPanel(List<Item> items)
    {
        this.setLayout(new GridLayout(0, 2, 10, 0));
        this.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        for (Item item : items)
        {
            item.addTo(this);
        }
    }

    public static class Item
    {
        private JLabel titleLabel;
        private JLabel valueLabel;

        public Item(String title, String value)
        {
            this.titleLabel = new JLabel(title);
            this.valueLabel = new JLabel(value);
        }

        public void addTo(JComponent component)
        {
            component.add(this.titleLabel);
            component.add(this.valueLabel);
        }

        public void setValue(String value)
        {
            this.valueLabel.setText(value);
        }
    }

}
