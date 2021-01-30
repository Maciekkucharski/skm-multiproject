package pl.edu.pjwstk.skmapi.controllerTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.edu.pjwstk.skmapi.controller.RegisterController;
import pl.edu.pjwstk.skmapi.model.User;
import pl.edu.pjwstk.skmapi.repository.UserRepository;
import pl.edu.pjwstk.skmapi.services.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WithMockUser(authorities = {"ROLE_ADMIN"})
@RunWith(SpringRunner.class)
@WebMvcTest(RegisterController.class)
public class RegisterControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    RegisterController controller;

    @MockBean
    UserService service;
    @MockBean
    UserRepository repository;
    @MockBean
    AuthenticationManager authenticationManager;

    @Test
    public void registerReturnsIsOk() throws Exception {
        User  user =new User();
        mockMvc.perform(MockMvcRequestBuilders.post("/register").contentType(MediaType.APPLICATION_JSON).content("""
                           {
                               "username":"",
                               "password":""
                           }
                        """)).andExpect(status().isOk());
        controller.register(user);
        Mockito.verify(service).createOrUpdate(user);
        assertEquals("ROLE_USER",user.getAuthority());
    }

    @Test
    public void registerReturnsIsConflict() throws Exception {
        User  user =new User();
        user.setUsername("test");
        when(service.loadUserByUsername(user.getUsername())).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.post("/register").contentType(MediaType.APPLICATION_JSON).content("""
                           {
                               "username":"test",
                               "password":""
                           }
                        """)).andExpect(status().isConflict());
    }


}
