package esprit.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {
    @Autowired
    NotificationRespository notificationRespository;
public List<Notification> findAll() {
    return NotificationRespository.findAll();
}
}
