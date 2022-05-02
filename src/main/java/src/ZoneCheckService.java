package src;

import org.json.JSONException;
import src.api.client.Client;
import src.api.client.PointGeometryResponse;

import javax.mail.internet.AddressException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ZoneCheckService {

    private final Client client;
    private final EmailService emailService;
    private final PointInPolygonChecker checker;
    public static final int NUMBER_OF_PHLEBOTOMIST = 7;

    public ZoneCheckService(Client client, EmailService emailService, PointInPolygonChecker checker) {

        this.client = client;
        this.emailService = emailService;
        this.checker = checker;
    }

    public void check(int phlebotomistId) {
        System.out.println("Checking for phlebotomist" + phlebotomistId);

        PointGeometryResponse response = null;
        try {
            response = client.getResponse(phlebotomistId);
        } catch (Exception e) {
            System.out.println("API error when checking for phlebotomist" + phlebotomistId);
            emailService.sendMail(phlebotomistId);
            return;
        }
        Point location = response.getLocation();
        List<Point> coordinates = response.getCoordinates();
        Point[] coordinatesArray = coordinates.toArray(new Point[0]);
        boolean isWithinZone = checker.check(location, coordinatesArray);
        if(!isWithinZone)
            emailService.sendMail(phlebotomistId);
    }

    public static void main(String[] args) throws AddressException, JSONException, URISyntaxException, IOException, InterruptedException {
        Client client = new Client();
        EmailService emailService = new EmailService();
        PointInPolygonChecker checker = new PointInPolygonChecker();
        ZoneCheckService service = new ZoneCheckService(client, emailService, checker);

        System.out.println("START THE SERVICE");

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(4);
        executorService.scheduleWithFixedDelay(() -> {
            for(int phlebotomistId = 1; phlebotomistId <= NUMBER_OF_PHLEBOTOMIST; phlebotomistId++)
                service.check(phlebotomistId);
        }, 0, 270000, TimeUnit.MILLISECONDS);
    }
}
