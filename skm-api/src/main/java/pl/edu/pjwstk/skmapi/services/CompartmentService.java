package pl.edu.pjwstk.skmapi.services;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.edu.pjwstk.skmapi.model.Compartment;

public class CompartmentService extends CrudService<Compartment> {
    public CompartmentService(JpaRepository<Compartment, Long> repository) {
        super(repository);
    }

    @Override
    public Compartment createOrUpdate(Compartment updateEntity) {
        return updateEntity;
    }
}
