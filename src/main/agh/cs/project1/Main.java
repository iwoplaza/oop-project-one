package agh.cs.project1;

import agh.cs.project1.ui.SimulationWindow;
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

            Vector2d[] positions = new Vector2d[] {
                    new Vector2d(0, 0),
                    new Vector2d(1, 2),
            };
            FoldingWorldMap map = new FoldingWorldMap(params.width, params.height);
            IEngine engine = new SimulationEngine(map, positions);

            (new SimulationWindow(engine)).setVisible(true);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
