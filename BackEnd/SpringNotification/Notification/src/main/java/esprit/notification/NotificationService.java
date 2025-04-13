import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private NotificationRepository notificationRepository;

    public void sendNotification(String email, String subject, String message) {
        // Save notification to DB
        Notification notification = new Notification();
        notification.setEmail(email);
        notification.setSubject(subject);
        notification.setMessage(message);
        notificationRepository.save(notification);

        // Send email
        sendEmail(email, subject, message);
    }

    private void sendEmail(String email, String subject, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("your_email@gmail.com");  // Use your email
        mailMessage.setTo(email);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }
}
