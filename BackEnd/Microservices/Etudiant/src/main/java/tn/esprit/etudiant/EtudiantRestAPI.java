package tn.esprit.etudiant;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EtudiantRestAPI {
    public String title="Hello Etudiant";

    @RequestMapping("/hello")
    public String sayHello(){
        return title;
    }

}
