package src.api.client;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Client {
    private static final String URI = "https://3qbqr98twd.execute-api.us-west-2.amazonaws.com/test/clinicianstatus/";
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void getResponse(int id) throws URISyntaxException, IOException, InterruptedException, JSONException {
        java.net.URI uri = new URI(URI + id);
        HttpRequest request = HttpRequest.newBuilder(uri)
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());
        String responseBodyJson = response.body();
        PointGeometryResponse pointGeometryResponse = new PointGeometryResponse(responseBodyJson);
    }

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException, JSONException {
        getResponse(1);
        getResponse(2);
        getResponse(3);
        getResponse(4);
        getResponse(5);
        getResponse(6);
        getResponse(7);
    }
}
