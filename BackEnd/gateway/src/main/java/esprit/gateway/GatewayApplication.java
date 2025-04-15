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
				.route("Payement",
						r -> r.path("/Payement/**")
								.uri("lb://PAYEMENT"))
				.route("Etudiant",
						r -> r.path("/Etudiant/**")
								.uri("lb://ETUDIANT"))
				.route("Reservation",
						r -> r.path("/reservation/**")
								.uri("lb://RESERVATION"))
				.route("Hebergement-Domain",
						r -> r.path("/Hebergement-Domain/**")
								.uri("lb://HEBERGEMENT-DOMAIN"))
				.route("universite-foyer", r -> r.path("/Universite-Foyer/**")
						.uri("lb://MICROSERVICENADA"))
				.build();

	}

}
