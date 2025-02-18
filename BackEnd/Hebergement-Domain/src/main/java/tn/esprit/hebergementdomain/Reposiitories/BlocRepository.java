package tn.esprit.hebergementdomain.Reposiitories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.hebergementdomain.Entities.Bloc;

@Repository
public interface BlocRepository extends JpaRepository<Bloc, Long> {

}
