package pl.edu.pjwstk.skmapi.serviceTests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.pjwstk.skmapi.exception.idNotFoundException;
import pl.edu.pjwstk.skmapi.model.User;
import pl.edu.pjwstk.skmapi.repository.UserRepository;
import pl.edu.pjwstk.skmapi.services.UserService;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    UserService service;
    @MockBean
    UserRepository repository;
    @Before
    public void setup(){
        service=new UserService(repository);
    }

    @Test
    public void CreateOrUpdateWithoutIdAdded() {
        User user = new User();
        user.setPassword("test");
        Mockito.when(repository.save(user)).thenReturn(user);
        Assert.assertEquals(user,service.createOrUpdate(user));
        Mockito.verify(repository).save(user);
    }

    @Test(expected = idNotFoundException.class)
    public void CreateOrUpdateWithIdThrowsException() {
        User user =  new User();
        user.setPassword("test");
        user.setId(1L);
        Mockito.when(repository.findById(1L)).thenReturn(Optional.empty());
        service.createOrUpdate(user);
    }

    @Test
    public void CreateOrUpdateWithIdAddedCorrectly() {
        User user =  new User();
        user.setPassword("test");
        user.setId(1L);
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(user));
        Mockito.when(repository.save(user)).thenReturn(user);
        Assert.assertEquals(user,service.createOrUpdate(user));
    }

    @Test
    public void getByIdCorrectlyTest() {
        User user = new User();
        user.setId(1L);
        Mockito.when(repository.findById(1l)).thenReturn(Optional.of(user));
        Assert.assertEquals(Optional.of(user), repository.findById(1l));
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
        User user = new User();
        user.setId(1L);
        Mockito.when(repository.findById(1l)).thenReturn(Optional.of(user));
        service.delete(1l);
        Mockito.verify(repository).findById(1L);
        Mockito.verify(repository).delete(user);
    }

    @Test(expected = idNotFoundException.class)
    public void deleteInCorrectlyTest() {
        Mockito.when(repository.findById(1l)).thenReturn(Optional.empty());
        service.delete(1l);
        Mockito.verify(repository).findById(1L);
    }
    @Test
    public void loadUserByUsernameReturnsNull() {
        Mockito.when(repository.findAll()).thenReturn(List.of());
        assertEquals(null,service.loadUserByUsername(""));
    }

    @Test(expected = UsernameNotFoundException.class)
    public void removeAuthorityThrowsException() {
        Mockito.when(repository.findById(1l)).thenThrow(new UsernameNotFoundException(""));
        service.removeAuthority(()->"ROLE_ADMIN",1l);
    }

    @Test
    public void removeAuthorityWorks() {
        User user = new User();
        user.setAuthority("ROLE_ADMIN");
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(user));
        service.removeAuthority(()->"ROLE_ADMIN",1l);
        assertEquals("",user.getAuthority());
    }

    @Test(expected = UsernameNotFoundException.class)
    public void addAuthorityThrowsException() {
        Mockito.when(repository.findById(1l)).thenThrow(new UsernameNotFoundException(""));
        service.addAuthority(()->"ROLE_ADMIN",1l);
    }

    @Test
    public void addAuthorityWorks() {
        User user = new User();
        Mockito.when(repository.findById(1L)).thenReturn(Optional.of(user));
        service.addAuthority(()->"ROLE_ADMIN",1l);
        assertEquals("ROLE_ADMIN",user.getAuthority());
    }
}
