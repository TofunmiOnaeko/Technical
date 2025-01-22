package application.repository;

import application.model.Interaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InteractionRepository extends CrudRepository<Interaction, String> {
}
