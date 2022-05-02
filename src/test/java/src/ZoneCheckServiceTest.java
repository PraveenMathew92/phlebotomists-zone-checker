package src;

import org.json.JSONException;
import org.junit.jupiter.api.Test;
import src.api.client.APIClientFailedException;
import src.api.client.Client;
import src.api.client.PointGeometryResponse;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;

import static org.mockito.Mockito.*;

class ZoneCheckServiceTest {
    @Test
    void shouldAlertWhenPhlebotomistIsOutsideTheZone() throws JSONException, URISyntaxException, IOException, InterruptedException, APIClientFailedException {
        EmailService emailService = mock(EmailService.class);
        Client client = mock(Client.class);
        Point targetPoint = new Point(0, 0);
        int phlebotomistId = 2;
        List<Point> coordinatePoints = List.of(new Point(1, 1), new Point(1, 2), new Point(2, 2), new Point(2, 1), new Point(1, 1));

        when(client.getResponse(phlebotomistId)).thenReturn(new PointGeometryResponse(targetPoint, coordinatePoints));

        ZoneCheckService service = new ZoneCheckService(client, emailService, new PointInPolygonChecker());
        service.check(phlebotomistId);

        verify(emailService).sendMail(phlebotomistId);
    }

    @Test
    void shouldNotAlertWhenPhlebotomistIsOutsideTheZone() throws JSONException, URISyntaxException, IOException, InterruptedException, APIClientFailedException {
        EmailService emailService = mock(EmailService.class);
        Client client = mock(Client.class);
        Point targetPoint = new Point(1.5, 1.5);
        int phlebotomistId = 2;
        List<Point> coordinatePoints = List.of(new Point(1, 1), new Point(1, 2), new Point(2, 2), new Point(2, 1), new Point(1, 1));

        when(client.getResponse(phlebotomistId)).thenReturn(new PointGeometryResponse(targetPoint, coordinatePoints));

        ZoneCheckService service = new ZoneCheckService(client, emailService, new PointInPolygonChecker());
        service.check(phlebotomistId);

        verify(emailService, never()).sendMail(phlebotomistId);
    }

    @Test
    void shouldAlertWhenClientAPIFails() throws JSONException, URISyntaxException, IOException, InterruptedException, APIClientFailedException {
        EmailService emailService = mock(EmailService.class);
        Client client = mock(Client.class);
        Point targetPoint = new Point(1.5, 1.5);
        int phlebotomistId = 2;
        List<Point> coordinatePoints = List.of(new Point(1, 1), new Point(1, 2), new Point(2, 2), new Point(2, 1), new Point(1, 1));

        when(client.getResponse(phlebotomistId)).thenThrow(APIClientFailedException.class);

        ZoneCheckService service = new ZoneCheckService(client, emailService, new PointInPolygonChecker());
        service.check(phlebotomistId);

        verify(emailService, never()).sendMail(phlebotomistId);
    }
}