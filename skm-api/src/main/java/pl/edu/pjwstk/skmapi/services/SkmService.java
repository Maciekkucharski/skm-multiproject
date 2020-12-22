package pl.edu.pjwstk.skmapi.services;


import org.springframework.stereotype.Service;
import pl.edu.pjwstk.skmapi.exception.EmptyFieldException;
import pl.edu.pjwstk.skmapi.model.Skm;
import pl.edu.pjwstk.skmapi.repository.CompartmentRepository;
import pl.edu.pjwstk.skmapi.repository.SkmRepository;


@Service
public class SkmService extends CrudService<Skm> {
    private final CompartmentRepository compartmentRepository;
    public SkmService(SkmRepository skmRepository, CompartmentRepository compartmentRepository) {
        super(skmRepository);
        this.compartmentRepository = compartmentRepository;
    }


    @Override
    public Skm createOrUpdate(Skm updateEntity) throws EmptyFieldException {
        if (updateEntity.getToGdynia()==null||updateEntity.getStation()==null){
            EmptyFieldException emptyFieldException = new EmptyFieldException();
            throw emptyFieldException;
        }else {
            return repository.save(updateEntity);
        }
    }
}
