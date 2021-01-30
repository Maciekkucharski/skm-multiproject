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
import pl.edu.pjwstk.skmapi.model.Skm;
import pl.edu.pjwstk.skmapi.repository.SkmRepository;
import pl.edu.pjwstk.skmapi.services.SkmService;

import java.util.Optional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SkmServiceTest {
    SkmService service;
    @MockBean
    SkmRepository repository;
    @Before
    public void setup(){
        service=new SkmService(repository);
    }

    @Test
    public void CreateOrUpdateWithoutIdAdded() {
        Skm skm = new Skm();
        Mockito.when(repository.save(skm)).thenReturn(skm);
        Assert.assertEquals(skm,service.createOrUpdate(skm));
        Mockito.verify(repository).save(skm);
    }

    @Test(expected = idNotFoundException.class)
    public void CreateOrUpdateWithIdThrowsException() {
        Skm skm =  new Skm();
        skm.setId(1L);
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());
        service.createOrUpdate(skm);
    }

    @Test
    public void CreateOrUpdateWithIdAddedCorrectly() {
        Skm skm =  new Skm();
        skm.setId(1L);
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(skm));
        Mockito.when(repository.save(skm)).thenReturn(skm);
        Assert.assertEquals(skm,service.createOrUpdate(skm));
    }

    @Test
    public void getAllTest() {
        service.getAll();
        Mockito.verify(repository).findAll();
    }

    @Test
    public void getByIdCorrectlyTest() {
        Skm skm = new Skm();
        skm.setId(1L);
        Mockito.when(repository.findById(1l)).thenReturn(Optional.of(skm));
        Assert.assertEquals(Optional.of(skm), repository.findById(1l));
        Mockito.verify(repository).findById(1L);
    }

    @Test(expected = idNotFoundException.class)
    public void getByIdIncorrectlyTest() {
        Mockito.when(repository.findById(1l)).thenReturn(Optional.empty());
        service.getById(1l);
        Mockito.verify(repository).findById(1L);
    }

    @Test
    public void deleteCorrectlyTest() {
        Skm skm = new Skm();
        skm.setId(1L);
        Mockito.when(repository.findById(1l)).thenReturn(Optional.of(skm));
        service.delete(1l);
        Mockito.verify(repository).findById(1L);
        Mockito.verify(repository).delete(skm);
    }

    @Test(expected = idNotFoundException.class)
    public void deleteInCorrectlyTest() {
        Mockito.when(repository.findById(1l)).thenReturn(Optional.empty());
        service.delete(1l);
        Mockito.verify(repository).findById(1L);
    }

}
