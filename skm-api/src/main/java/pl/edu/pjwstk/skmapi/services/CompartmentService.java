package pl.edu.pjwstk.skmapi.services;


import pl.edu.pjwstk.skmapi.exception.EmptyFieldException;
import pl.edu.pjwstk.skmapi.model.Compartment;
import pl.edu.pjwstk.skmapi.repository.CompartmentRepository;

public class CompartmentService extends CrudService<Compartment> {
    public CompartmentService(CompartmentRepository repository) {
        super(repository);
    }

    @Override
    public Compartment createOrUpdate(Compartment updateEntity) throws EmptyFieldException {
        if (updateEntity.getLimit()==0||updateEntity.getSkm().getId()==null){
            EmptyFieldException emptyFieldException = new EmptyFieldException();
            throw emptyFieldException;
        }else {
            return repository.save(updateEntity);
        }
    }
}
