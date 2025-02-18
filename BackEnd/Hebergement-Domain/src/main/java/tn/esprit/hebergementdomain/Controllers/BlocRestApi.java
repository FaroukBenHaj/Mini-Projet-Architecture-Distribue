package tn.esprit.hebergementdomain.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tn.esprit.hebergementdomain.Entities.Bloc;
import tn.esprit.hebergementdomain.Services.BlocService;

import java.util.List;

@RestController
@RequestMapping("/Bloc")
public class BlocRestApi {
    @Autowired
    private BlocService blocService;

    @GetMapping("/show_list")
    public List<Bloc> getAllBloc() {
        List<Bloc> blocs = blocService.getAllBloc();
        return blocs;
    }

    @PostMapping("/add_Bloc")
    public Bloc addBloc(@RequestBody Bloc bloc) {
        return blocService.addBloc(bloc);
    }

}
