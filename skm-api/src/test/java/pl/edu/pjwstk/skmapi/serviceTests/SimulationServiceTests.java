package pl.edu.pjwstk.skmapi.serviceTests;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.pjwstk.skmapi.model.Compartment;
import pl.edu.pjwstk.skmapi.model.Human;
import pl.edu.pjwstk.skmapi.model.Skm;
import pl.edu.pjwstk.skmapi.model.Stations;
import pl.edu.pjwstk.skmapi.repository.HumanRepository;
import pl.edu.pjwstk.skmapi.repository.SkmRepository;
import pl.edu.pjwstk.skmapi.services.SimulationService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@RunWith(SpringRunner.class)
public class SimulationServiceTests {
    SimulationService service;
    @MockBean
    SkmRepository skmRepository;
    @MockBean
    HumanRepository humanRepository;


    @Before
    public void setup(){
        service = new SimulationService(skmRepository,humanRepository);
    }

    @Test
    public void removePassengersTest(){
        Skm skm = new Skm();
        skm.setToGdynia(false);
        skm.setStation(Stations.GD_ZAB);

        Compartment compartment = new Compartment();
        compartment.setSkm(skm);
        compartment.setId(0L);

        Human human1 = new Human(Stations.GD_ZAB,compartment);
        Human human2 = new Human(Stations.GD_GL,compartment);

        compartment.setHumans(List.of(human1,human2));

        skm.setCompartments(List.of(compartment));

        service.removePassengers(skm);

        Mockito.verify(humanRepository).deleteInBatch(List.of(human1));
    }

    @Test
    public void moveTest(){
        Skm skm = new Skm();
        skm.setToGdynia(false);
        skm.setStation(Stations.GD_OLI);
        skm.setPauseCount(0);
        skm.setId(1L);

        Compartment compartment = new Compartment();
        compartment.setSkm(skm);
        compartment.setId(0L);
        compartment.setLimit(10);

        Human human1 = new Human(Stations.GD_ZAB,compartment);
        human1.setId(1L);
        Human human2 = new Human(Stations.GD_GL,compartment);
        human2.setId(2L);

        List<Human> newPassengers = new ArrayList<>();
        newPassengers.add(human1);
        newPassengers.add(human2);

        compartment.setHumans(newPassengers);

        skm.setCompartments(List.of(compartment));

        Mockito.when(skmRepository.findAll()).thenReturn(List.of(skm));
        Mockito.when(skmRepository.findById(1L)).thenReturn(Optional.of(skm));


        service.move();

        Mockito.verify(humanRepository, Mockito.atMost(8)).save(any(Human.class));
        Mockito.verify(humanRepository).deleteInBatch(List.of());
        Mockito.verify(humanRepository, Mockito.atMost(8)).save(any(Human.class));

    }
}
