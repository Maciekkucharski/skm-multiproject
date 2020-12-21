package pl.edu.pjwstk.skmapi.services;


import pl.edu.pjwstk.skmapi.model.Compartment;
import pl.edu.pjwstk.skmapi.repository.CompartmentRepository;

public class CompartmentService extends CrudService<Compartment> {
    public CompartmentService(CompartmentRepository repository) {
        super(repository);
    }

    @Override
    public Compartment createOrUpdate(Compartment updateEntity) {
        return updateEntity;
    }
}
