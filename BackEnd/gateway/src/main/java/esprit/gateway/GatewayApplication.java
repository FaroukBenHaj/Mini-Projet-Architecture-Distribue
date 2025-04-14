package esprit.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}
	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
		return builder.routes()
				.route("Notification",
				r->r.path("/Notification/**")
						.uri("lb://Notification"))
				.route("Payement",
						r -> r.path("/Payement/**")
								.uri("lb://Payement"))
				.route("Etudiant",
						r -> r.path("/Etudiant/**")
								.uri("lb://Etudiant"))
				.route("Reservation",
						r -> r.path("/Reservation/**")
								.uri("lb://Reservation"))
				.route("Hebergement_Domain",
						r -> r.path("/Hebergement/**")
								.uri("http://localhost:8050"))
				.build();

	}

}
