package pl.edu.pjwstk.skmapi.services;


import org.springframework.stereotype.Service;
import pl.edu.pjwstk.skmapi.model.Compartment;
import pl.edu.pjwstk.skmapi.repository.CompartmentRepository;

import java.util.Optional;

import static pl.edu.pjwstk.skmapi.util.Utils.fallbackIfNull;
@Service
public class CompartmentService extends CrudService<Compartment> {
    public CompartmentService(CompartmentRepository repository) {
        super(repository);
    }


    @Override
    public Compartment createOrUpdate(Compartment updateEntity) {

        if (updateEntity.getId() == null) {
            return repository.save(updateEntity);
        }

        Optional<Compartment> compartmentInDb = repository.findById(updateEntity.getId());

        if (compartmentInDb.isPresent()) {
            var dbEntity = compartmentInDb.get();
            dbEntity.setLimit(fallbackIfNull(updateEntity.getLimit(), dbEntity.getLimit()));
            dbEntity.setSkm(fallbackIfNull(updateEntity.getSkm(), updateEntity.getSkm()));

            return repository.save(dbEntity);
        } else {
            throw new idNotFoundException("nie znaleziono skm'ki");
        }
    }
}
