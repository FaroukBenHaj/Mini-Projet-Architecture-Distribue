package esprit.notification;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class NotificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(CandidatApplication.class, args);
	}
     @Autowired
	NotificationRespository notificationRespository;
	@Bean
	public Notification addNotification() {
		return notificationRespository.save(new Notification("message",11-11-2025,"maissa@gmail.com"));

	};
}
