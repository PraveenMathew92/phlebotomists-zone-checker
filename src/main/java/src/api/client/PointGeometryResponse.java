package src.api.client;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import src.Point;

import java.util.ArrayList;
import java.util.List;

public class PointGeometryResponse {
    private List<Point> coordinates;

    public List<Point> getCoordinates() {
        return coordinates;
    }

    public Point getLocation() {
        return location;
    }

    private Point location;

    public PointGeometryResponse(Point location, List<Point> coordinates) {
        this.coordinates = coordinates;
        this.location = location;
    }

    public PointGeometryResponse(String jsonString) throws JSONException {
        JSONObject json = new JSONObject(jsonString);
        JSONArray features = json.getJSONArray("features");

        JSONArray locationArray = features.getJSONObject(0)
                .getJSONObject("geometry")
                .getJSONArray("coordinates");
        location = new Point(locationArray.getDouble(0), locationArray.getDouble(1));

        coordinates = new ArrayList<>();
        int n = features.length();
        JSONArray coordinatesArrays = features.getJSONObject(1)
                .getJSONObject("geometry")
                .getJSONArray("coordinates")
                .getJSONArray(0);
        for(int i = 0; i < coordinatesArrays.length(); i++) {
            JSONArray coordinatePointsArray = coordinatesArrays.getJSONArray(i);
            coordinates.add(new Point(coordinatePointsArray.getDouble(0), coordinatePointsArray.getDouble(1)));
        }
    }
}
