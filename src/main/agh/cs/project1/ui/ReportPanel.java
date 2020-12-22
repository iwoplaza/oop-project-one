package agh.cs.project1.ui;

import agh.cs.project1.IEngine;
import agh.cs.project1.statistics.Report;
import agh.cs.project1.statistics.StatisticsGatherer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

public class ReportPanel extends JPanel implements ActionListener
{

    private static final File REPORT_FILE = new File("report.txt");
    private final IEngine engine;
    private final JButton setupReportButton;
    private final JLabel reportCountdownLabel;

    private int daysToTrack = 0;
    private StatisticsGatherer statisticsGatherer;
    private Report report;

    public ReportPanel(IEngine engine)
    {
        this.engine = engine;

        this.setLayout(new GridLayout(1, 0, 10, 0));
        this.setEnabled(false);

        this.setupReportButton = new JButton("Setup report");
        this.setupReportButton.addActionListener(this);
        this.add(setupReportButton);

        this.reportCountdownLabel = new JLabel("");
        this.add(this.reportCountdownLabel);
    }

    public void setRunning(boolean running)
    {
        this.setupReportButton.setEnabled(!running);
    }

    public void onWorldTick()
    {
        if (this.report == null)
            return;

        this.daysToTrack--;

        this.reportCountdownLabel.setText(String.format("Days left to track: %d", this.daysToTrack));

        this.report.addDay(this.engine.getMap().getDay(), this.statisticsGatherer.getStatistics());

        if (this.daysToTrack <= 0)
        {
            try
            {
                this.report.writeToFile(REPORT_FILE);
            }
            catch (IOException e)
            {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null,
                        "An error occured while saving the report",
                        "Couldn't save report",
                        JOptionPane.ERROR_MESSAGE);
            }
            finally
            {
                this.cancelReport();
            }
        }
    }

    private void setupReport(int daysToTrack)
    {
        this.daysToTrack = daysToTrack;
        this.report = new Report();
        this.statisticsGatherer = new StatisticsGatherer(this.engine);
        this.setupReportButton.setText("Cancel report");
        this.reportCountdownLabel.setText(String.format("Days left to track: %d", this.daysToTrack));
    }

    private void cancelReport()
    {
        this.report = null;
        this.statisticsGatherer = null;
        this.setupReportButton.setText("Setup report");
        this.reportCountdownLabel.setText("");
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.setupReportButton)
        {
            if (this.report != null)
            {
                this.cancelReport();
                return;
            }

            String inputValue = JOptionPane.showInputDialog(
                    null,
                    "How many days would you like to track?",
                    "Setup a report",
                    JOptionPane.PLAIN_MESSAGE);

            if (inputValue == null)
            {
                // Cancel
                return;
            }

            int intValue = 1;
            boolean invalidInput = false;
            try
            {
                intValue = Integer.parseInt(inputValue);
                if (intValue <= 0)
                {
                    invalidInput = true;
                }
            }
            catch (NumberFormatException ex)
            {
                invalidInput = true;
            }

            if (invalidInput)
            {
                JOptionPane.showMessageDialog(null,
                        "The amount of days has to be a positive number (>0)!",
                        "Invalid report duration.",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            this.setupReport(intValue);
        }
    }
}
