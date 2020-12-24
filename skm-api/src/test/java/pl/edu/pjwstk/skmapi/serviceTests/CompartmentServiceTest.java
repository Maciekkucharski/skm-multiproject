package pl.edu.pjwstk.skmapi.serviceTests;

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
    CompartmentRepository repository;

    CompartmentService service;

    @Before
    public void setup() {
        service = new CompartmentService(repository);
    }


    @Test
    public void CreateOrUpdateWithoutIdAdded() {
        Compartment compartment = new Compartment();
        Mockito.when(repository.save(compartment)).thenReturn(compartment);
        Assert.assertEquals(compartment, service.createOrUpdate(compartment));
        Mockito.verify(repository).save(compartment);
    }


    @Test
    public void CreateOrUpdateWithIdThrowsException() {
        Compartment compartment =  new Compartment();
        compartment.setId(1L);
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());
        Assert.assertThrows(idNotFoundException.class, () -> {
            service.createOrUpdate(compartment);
        });
    }

    @Test
    public void CreateOrUpdateWithIdAddedCorrectly() {
        Compartment compartment =  new Compartment();
        compartment.setId(1L);
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(compartment));
        Mockito.when(repository.save(compartment)).thenReturn(compartment);
        Assert.assertEquals(compartment, service.createOrUpdate(compartment));
    }

    @Test
    public void getAllTest() {
        service.getAll();
        Mockito.verify(repository).findAll();
    }

    @Test
    public void getByIdCorrectlyTest() {
        Compartment compartment = new Compartment();
        compartment.setId(1L);
        Mockito.when(repository.findById(1l)).thenReturn(Optional.of(compartment));
        Assert.assertEquals(Optional.of(compartment), repository.findById(1l));
        Mockito.verify(repository).findById(1L);
    }

    @Test
    public void getByIdIncorrectlyTest() {
        Mockito.when(repository.findById(1l)).thenReturn(Optional.empty());
        Assert.assertThrows(idNotFoundException.class, () -> {
            service.getById(1l);
        });
        Mockito.verify(repository).findById(1L);
    }

    @Test
    public void deleteCorrectlyTest() {
        Compartment compartment = new Compartment();
        compartment.setId(1L);
        Mockito.when(repository.findById(1l)).thenReturn(Optional.of(compartment));
        service.delete(1l);
        Mockito.verify(repository).findById(1L);
        Mockito.verify(repository).delete(compartment);
    }

    @Test
    public void deleteInCorrectlyTest() {
        Mockito.when(repository.findById(1l)).thenReturn(Optional.empty());
        Assert.assertThrows(idNotFoundException.class, () -> {
            service.delete(1l);
        });
        Mockito.verify(repository).findById(1L);
    }


}
