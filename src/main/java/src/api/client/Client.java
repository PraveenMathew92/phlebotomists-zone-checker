package src.api.client;

import org.json.JSONException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Client {
    private static final String URI = "https://3qbqr98twd.execute-api.us-west-2.amazonaws.com/test/clinicianstatus/";

    public PointGeometryResponse getResponse(int id) throws URISyntaxException, IOException, InterruptedException, JSONException, APIClientFailedException {
        java.net.URI uri = new URI(URI + id);
        HttpRequest request = HttpRequest.newBuilder(uri)
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());
        boolean statusOK = response.statusCode() == 200;
        if(!statusOK) {
            throw new APIClientFailedException();
        }
        String responseBodyJson = response.body();
        PointGeometryResponse pointGeometryResponse = new PointGeometryResponse(responseBodyJson);
        return pointGeometryResponse;
    }
}
