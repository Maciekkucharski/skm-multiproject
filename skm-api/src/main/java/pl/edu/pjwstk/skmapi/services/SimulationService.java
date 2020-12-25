package pl.edu.pjwstk.skmapi.services;

import org.springframework.stereotype.Service;
import pl.edu.pjwstk.skmapi.model.Compartment;
import pl.edu.pjwstk.skmapi.model.Human;
import pl.edu.pjwstk.skmapi.model.Skm;
import pl.edu.pjwstk.skmapi.model.Stations;
import pl.edu.pjwstk.skmapi.repository.CompartmentRepository;
import pl.edu.pjwstk.skmapi.repository.HumanRepository;
import pl.edu.pjwstk.skmapi.repository.SkmRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class SimulationService {
    private final SkmRepository skmRepository;
    private final HumanRepository humanRepository;


    public SimulationService(SkmRepository skmRepository, HumanRepository humanRepository) {
        this.skmRepository = skmRepository;
        this.humanRepository = humanRepository;
    }

    public void removePassengers(Skm skm) {
        List<Human> passengersToDelete = new ArrayList<>();
        for (Compartment compartment : skm.getCompartments()) {
            for (Human passenger : compartment.getHumans()) {
                if (passenger.getEndpoint().equals(skm.getStation())) {
                    passengersToDelete.add(passenger);
                }
            }
        }
        humanRepository.deleteInBatch(passengersToDelete);
    }


    public void move() {
        Random random = new Random();
        List<Skm> skms = skmRepository.findAll();
        for (Skm skm : skms) {
            skm.moveSkm(skmRepository, skm.getId());
            removePassengers(skm);
            for (int i = 2; i < random.nextInt(7) + 2; i++) {
                if (skm.getCapacity() > skm.countPeople()) {
                    if (skm.getPauseCount()==0){
                        humanRepository.save(skm.getFirstFreeCompartment().addPassenger(Stations.randomNextStation(skm)));
                    }
                }
            }
        }
    }
}
