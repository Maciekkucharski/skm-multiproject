package pl.edu.pjwstk.skmapi.services;


import org.springframework.stereotype.Service;
import pl.edu.pjwstk.skmapi.exception.idNotFoundException;
import pl.edu.pjwstk.skmapi.model.Skm;
import pl.edu.pjwstk.skmapi.repository.CompartmentRepository;
import pl.edu.pjwstk.skmapi.repository.SkmRepository;

import java.util.Optional;

import static pl.edu.pjwstk.skmapi.util.Utils.fallbackIfNull;


@Service
public class SkmService extends CrudService<Skm> {
//    private final CompartmentRepository compartmentRepository;
    public SkmService(SkmRepository skmRepository, CompartmentRepository compartmentRepository) {
        super(skmRepository);
//        this.compartmentRepository = compartmentRepository;
    }


    @Override
    public Skm createOrUpdate(Skm updateEntity) {
        if (updateEntity.getId() == null) {
            return repository.save(updateEntity);
        }

        Optional<Skm> skmInDb = repository.findById(updateEntity.getId());

        if (skmInDb.isPresent()) {
            var dbEntity = skmInDb.get();

            dbEntity.setToGdynia(fallbackIfNull(updateEntity.getToGdynia(), dbEntity.getToGdynia()));
            dbEntity.setPauseCount(fallbackIfNull(updateEntity.getPauseCount(),dbEntity.getPauseCount()));
            dbEntity.setStation(fallbackIfNull(updateEntity.getStation(),dbEntity.getStation()));
            dbEntity.setCompartments(fallbackIfNull(updateEntity.getCompartments(),dbEntity.getCompartments()));


            return repository.save(dbEntity);
        } else {
            throw new idNotFoundException("nie znaleziono skm'ki");
        }
    }
}
