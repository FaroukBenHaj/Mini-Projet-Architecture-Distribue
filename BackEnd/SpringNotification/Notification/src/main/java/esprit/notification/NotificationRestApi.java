import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationRestApi {

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/send-alert")
    public String sendAlert(@RequestBody NotificationRequest request) {
        // Call service to send the email and store notification
        notificationService.sendNotification(request.getEmail(), request.getSubject(), request.getMessage());
        return "Notification sent successfully!";
    }
}
