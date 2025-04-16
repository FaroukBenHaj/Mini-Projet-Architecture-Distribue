package tn.esprit.etudiant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import tn.esprit.etudiant.repository.EtudiantRepository;

@SpringBootApplication
@EnableDiscoveryClient
public class EtudiantApplication {

    public static void main(String[] args) {
        SpringApplication.run(EtudiantApplication.class, args);
    }
    @Autowired
    private EtudiantRepository etudiantRepository;

//    @Bean
//    ApplicationRunner init(){
//        return (args -> {
//            etudiantRepository.save(new Etudiant("safa","ben mustapha", 11408705, "Esprit"));
//            etudiantRepository.findAll();
//        });
//    }

}
