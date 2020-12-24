package pl.edu.pjwstk.skmapi.serviceTests;

import liquibase.pro.packaged.C;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.pjwstk.skmapi.exception.idNotFoundException;
import pl.edu.pjwstk.skmapi.model.Compartment;
import pl.edu.pjwstk.skmapi.repository.CompartmentRepository;
import pl.edu.pjwstk.skmapi.services.CompartmentService;

import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CompartmentServiceTest {
    @MockBean
    CompartmentRepository compartmentRepository;

    CompartmentService compartmentService;

    @Before
    public void setup() {
        compartmentService = new CompartmentService(compartmentRepository);
    }


    @Test
    public void CreateOrUpdateWithoutIdAdded() {
        Compartment compartment = new Compartment();
        Mockito.when(compartmentRepository.save(compartment)).thenReturn(compartment);
        Assert.assertEquals(compartment,compartmentService.createOrUpdate(compartment));
        Mockito.verify(compartmentRepository).save(compartment);
    }


    @Test
    public void CreateOrUpdateWithIdThrowsException() {
        Compartment compartment =  new Compartment();
        compartment.setId(1L);
        Mockito.when(compartmentRepository.findById(1L)).thenReturn(Optional.empty());
        Assert.assertThrows(idNotFoundException.class, () -> {
            compartmentService.createOrUpdate(compartment);
        });
    }

    @Test
    public void CreateOrUpdateWithIdAddedCorrectly() {
        Compartment compartment =  new Compartment();
        compartment.setId(1L);
        Mockito.when(compartmentRepository.findById(1L)).thenReturn(Optional.of(compartment));
        Mockito.when(compartmentRepository.save(compartment)).thenReturn(compartment);
        Assert.assertEquals(compartment,compartmentService.createOrUpdate(compartment));
    }

    @Test
    public void getAllTest() {
        compartmentService.getAll();
        Mockito.verify(compartmentRepository).findAll();
    }

    @Test
    public void getByIdCorrectlyTest() {
        Compartment compartment = new Compartment();
        compartment.setId(1L);
        Mockito.when(compartmentRepository.findById(1l)).thenReturn(Optional.of(compartment));
        Assert.assertEquals(Optional.of(compartment),compartmentRepository.findById(1l));
        Mockito.verify(compartmentRepository).findById(1L);
    }

    @Test
    public void getByIdIncorrectlyTest() {
        Mockito.when(compartmentRepository.findById(1l)).thenReturn(Optional.empty());
        Assert.assertThrows(idNotFoundException.class, () -> {
            compartmentService.getById(1l);
        });
        Mockito.verify(compartmentRepository).findById(1L);
    }

    @Test
    public void deleteCorrectlyTest() {
        Compartment compartment = new Compartment();
        compartment.setId(1L);
        Mockito.when(compartmentRepository.findById(1l)).thenReturn(Optional.of(compartment));
        compartmentService.delete(1l);
        Mockito.verify(compartmentRepository).findById(1L);
        Mockito.verify(compartmentRepository).delete(compartment);
    }

    @Test
    public void deleteInCorrectlyTest() {
        Mockito.when(compartmentRepository.findById(1l)).thenReturn(Optional.empty());
        Assert.assertThrows(idNotFoundException.class, () -> {
            compartmentService.delete(1l);
        });
        Mockito.verify(compartmentRepository).findById(1L);
    }


}
