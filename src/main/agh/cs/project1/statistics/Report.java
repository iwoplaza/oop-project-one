package agh.cs.project1.statistics;

import java.io.*;
import java.util.LinkedList;
import java.util.List;

public class Report
{
    private List<ReportDay> reportDays = new LinkedList<>();

    public void addDay(int dayNumber, SimulationStatistics statistics)
    {
        this.reportDays.add(new ReportDay(dayNumber, statistics));
    }

    public static class ReportDay
    {
        private int dayNumber;
        private SimulationStatistics statistics;

        public ReportDay(int dayNumber, SimulationStatistics statistics)
        {
            this.dayNumber = dayNumber;
            this.statistics = (SimulationStatistics) statistics.clone();
        }

        public void writeToWriter(Writer writer) throws IOException
        {
            writer.append(String.format("DAY #%d\n", this.dayNumber));
            writer.append(String.format("  Animal count:        %s\n", this.statistics.getAnimalCount()));
            writer.append(String.format("  Plant count:         %s\n", this.statistics.getPlantCount()));
            writer.append(String.format("  Dominant gene:       %s\n", this.statistics.getDominantGene()));
            writer.append(String.format("  Average energy:      %s\n", this.statistics.getAverageEnergy()));
            writer.append(String.format("  Average child count: %s\n", this.statistics.getAverageChildCount()));
            writer.append("\n");
        }
    }

    public void writeToFile(File file) throws IOException
    {
        file.createNewFile();

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file)))
        {
            for (ReportDay reportDay : this.reportDays)
            {
                reportDay.writeToWriter(writer);
            }
        }
    }
}
