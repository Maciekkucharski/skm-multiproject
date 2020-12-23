package pl.edu.pjwstk.skmapi.services;

import org.springframework.stereotype.Service;
import pl.edu.pjwstk.skmapi.exception.EmptyFieldException;
import pl.edu.pjwstk.skmapi.model.Human;
import pl.edu.pjwstk.skmapi.repository.HumanRepository;

@Service
public class HumanService extends CrudService<Human> {
    public HumanService(HumanRepository repository) {
        super(repository);
    }

    @Override
    public Human createOrUpdate(Human updateEntity) throws EmptyFieldException {
        return null;
    }
}
