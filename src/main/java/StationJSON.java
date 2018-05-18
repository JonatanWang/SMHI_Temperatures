import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Extract data x: date, y: value, from station number 159880.
 */
public class StationJSON {

    // Url for the metobs API
    private String metObsAPI = "https://opendata-download-metobs.smhi.se/api";


    /**
     * Print all available points.
     *
     * @return The list of points.
     * @throws IOException
     * @throws JSONException
     */
    public ArrayList<DispPoint> getXYData() throws IOException, JSONException {

        ArrayList<DispPoint> dispPoints = new ArrayList<>();
        JSONObject parameterObject = readJsonFromUrl(metObsAPI
                + "/version/1.0/parameter/1/station/159880/period/latest-months/data.json");
        JSONArray parametersArray = parameterObject.getJSONArray("value");

        Long date = 0L;
        for (int i = 0; i < parametersArray.length(); i++) {

            JSONObject parameter = parametersArray.getJSONObject(i);
            date = parameter.getLong("date");
            double y = parameter.getDouble("value");
            long x = (System.currentTimeMillis() - date) / (1000 * 60 * 60);
            DispPoint point = new DispPoint();
            point.setX(x);
            point.setY(y);
            dispPoints.add(point);
            System.out.println(x + ": " + y);
        }

        return dispPoints;
    }

    private JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        String text = readStringFromUrl(url);
        return new JSONObject(text);
    }


    private String readStringFromUrl(String url) throws IOException {

        InputStream inputStream = new URL(url).openStream();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, Charset.forName("UTF-8")));
            StringBuilder stringBuilder = new StringBuilder();
            int cp;
            while ((cp = reader.read()) != -1) {
                stringBuilder.append((char) cp);
            }
            return stringBuilder.toString();
        } finally {
            inputStream.close();
        }
    }

    // Methods to get Json objects
    public JSONObject getParameterJsonObject () throws IOException, JSONException{

        return readJsonFromUrl(metObsAPI + "/version/latest.json");
    }

    public static void main(String... args) {
        try {
            StationJSON openDataMetobsReader = new StationJSON();
            openDataMetobsReader.getXYData();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }
}
