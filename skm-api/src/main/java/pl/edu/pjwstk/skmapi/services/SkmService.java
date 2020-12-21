package pl.edu.pjwstk.skmapi.services;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.skmapi.model.Skm;
import pl.edu.pjwstk.skmapi.repository.SkmRepository;

@Service
public class SkmService extends CrudService<Skm> {


    public SkmService(SkmRepository repository) {
        super(repository);
    }

    @Override
    public Skm createOrUpdate(Skm updateEntity) {
        return updateEntity;
    }
}
