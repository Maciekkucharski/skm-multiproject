package pl.edu.pjwstk.skmapi.serviceTests;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import pl.edu.pjwstk.skmapi.model.User;
import pl.edu.pjwstk.skmapi.repository.UserRepository;
import pl.edu.pjwstk.skmapi.services.UserService;

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
        Mockito.when(repository.save(user)).thenReturn(user);
        Assert.assertEquals(user,service.createOrUpdate(user));
        Mockito.verify(repository).save(user);
    }

}
