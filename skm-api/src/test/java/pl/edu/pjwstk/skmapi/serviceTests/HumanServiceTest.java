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
import pl.edu.pjwstk.skmapi.model.Human;
import pl.edu.pjwstk.skmapi.repository.HumanRepository;
import pl.edu.pjwstk.skmapi.services.HumanService;

import java.util.Optional;

@SpringBootTest
@RunWith(SpringRunner.class)
public class HumanServiceTest {

    HumanService service;
    @MockBean
    HumanRepository repository;
    @Before
    public void setup(){
        service=new HumanService(repository);
    }

    @Test
    public void getAllTest() {
        service.getAll();
        Mockito.verify(repository).findAll();
    }

    @Test
    public void getByIdCorrectlyTest() {
        Human human = new Human();
        human.setId(1L);
        Mockito.when(repository.findById(1l)).thenReturn(Optional.of(human));
        Assert.assertEquals(Optional.of(human), repository.findById(1l));
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
    //
    @Test
    public void deleteCorrectlyTest() {
        Human human = new Human();
        human.setId(1L);
        Mockito.when(repository.findById(1l)).thenReturn(Optional.of(human));
        service.delete(1l);
        Mockito.verify(repository).findById(1L);
        Mockito.verify(repository).delete(human);
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
