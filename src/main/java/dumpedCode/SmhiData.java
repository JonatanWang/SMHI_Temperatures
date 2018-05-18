package dumpedCode;

import dumpedCode.JSONParser;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Class to get the needed Json data format
 */
public class SmhiData {

    JSONParser parser = new JSONParser();

    // Get data from JsonParser, in which format???? What to show? Which stations data?
    // x-axel: time, y-axel: temperature?
    // TODO...
    public ArrayList<JSONObject> load() {
        ArrayList<JSONObject> res = new ArrayList<>();
        try {
            for (int i = 0; i < parser.getStationJsonObject().length(); i ++) {
                res.add(parser.getStationJsonObject());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return res;
    }

    /**
    public ArrayList<DispPoint> getDisplayPoints() {
        ArrayList<DispPoint> points = null;
        DispPoint p = null;
        for (int i = 0; i < this.load().size(); i ++) {
            p.setX(this.load().get(i).getInt("x")); // if there is a key with value "x"
            p.setY(this.load().get(i).getInt("y")); // if there is a key with value "y"
            points.add(p);
        }
        return points;
    }
     */
}
