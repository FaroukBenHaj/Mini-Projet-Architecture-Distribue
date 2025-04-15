package tn.esprit.hebergementdomain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.Bean;
import tn.esprit.hebergementdomain.Entities.Bloc;
import tn.esprit.hebergementdomain.Reposiitories.BlocRepository;

@SpringBootApplication
@EnableDiscoveryClient


public class HebergementDomainApplication {

	public static void main(String[] args) {
		SpringApplication.run(HebergementDomainApplication.class, args);
	}

	@Autowired
	BlocRepository blocRepository;
	@Bean
	public Bloc addBloc_2 ()
	{
		return blocRepository.save( new Bloc("Esprit" , 10 ));
	}
}
