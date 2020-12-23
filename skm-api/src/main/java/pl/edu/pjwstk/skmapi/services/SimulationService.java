package pl.edu.pjwstk.skmapi.services;

import org.springframework.stereotype.Service;
import pl.edu.pjwstk.skmapi.model.Compartment;
import pl.edu.pjwstk.skmapi.model.Skm;
import pl.edu.pjwstk.skmapi.repository.CompartmentRepository;
import pl.edu.pjwstk.skmapi.repository.HumanRepository;
import pl.edu.pjwstk.skmapi.repository.SkmRepository;

import java.util.List;
import java.util.Random;

@Service
public class SimulationService {
    private final SkmRepository skmRepository;
    private final CompartmentRepository compartmentRepository;
    private final HumanRepository humanRepository;


    public SimulationService(SkmRepository skmRepository, CompartmentRepository compartmentRepository, HumanRepository humanRepository) {
        this.skmRepository = skmRepository;
        this.compartmentRepository = compartmentRepository;
        this.humanRepository = humanRepository;
    }


    public void move() {
        Random random = new Random();
        List<Skm> skms = skmRepository.findAll();

        for (Skm skm : skms) {
            skm.moveSkm(skmRepository, skm.getId());
            for (int i = 2; i < random.nextInt(7) + 2; i++) {
                if (skm.getCapacity() > skm.countPeople()) {
//                    skm.getFirstFreeCompartment().addPassenger(Stations.randomNextStation(skm));
                }
            }
            for (Compartment compartment:skm.getCompartments()) {
                compartment.getHumans().forEach(o-> System.out.println(o.toString()));

            }
//            skm.getCompartments().forEach(o-> System.out.println(o.toString()));
        }

    }
}
