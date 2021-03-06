package agh.cs.project1;

import agh.cs.project1.map.FoldingJungleMap;
import agh.cs.project1.ui.SimulationWindow;
import agh.cs.project1.util.RandomHelper;
import agh.cs.project1.util.Vector2d;
import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.IOException;

public class Main
{
    private static final String PARAMETERS_FILEPATH = "parameters.json";

    private static AppParameters loadParameters() throws IOException
    {
        try (JsonReader reader = new JsonReader(new FileReader(PARAMETERS_FILEPATH)))
        {
            Gson gson = new Gson();
            return gson.fromJson(reader, AppParameters.class);
        }
        catch (IOException e)
        {
            throw e;
        }
    }

    public static void main(String[] args)
    {
        try
        {
            AppParameters params = loadParameters();

            Vector2d[] positions = RandomHelper.findSpotsInCenter(params.amountOfStartAnimals, new Vector2d(0, 0), new Vector2d(params.width - 1, params.height - 1));

            IEngine firstEngine = new SimulationEngine(new FoldingJungleMap(params.width, params.height, params.jungleRatio, params.plantEnergy), params, positions);
            IEngine secondEngine = new SimulationEngine(new FoldingJungleMap(params.width, params.height, params.jungleRatio, params.plantEnergy), params, positions);

            (new SimulationWindow(new IEngine[] {
                    firstEngine, secondEngine
            })).setVisible(true);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
