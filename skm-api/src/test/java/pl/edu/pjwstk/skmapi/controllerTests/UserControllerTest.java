package pl.edu.pjwstk.skmapi.controllerTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.edu.pjwstk.skmapi.controller.UserController;
import pl.edu.pjwstk.skmapi.exception.idNotFoundException;
import pl.edu.pjwstk.skmapi.model.User;
import pl.edu.pjwstk.skmapi.services.UserService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser(authorities = {"ROLE_ADMIN"})
@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    UserController controller;

    @MockBean
    UserService service;

    @MockBean
    AuthenticationManager authenticationManager;

    @Test
    public void getAllTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/user").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        Mockito.verify(service).getAll();
    }

    @Test
    public void getByIdAccepted() throws Exception {
        User user = new User();
        user.setUsername("test");
        user.setId(1L);
        user.setPassword("password");
        user.setAuthority("");
        Mockito.when(service.getById(1L)).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.get("/user/1")).andExpect(status().isOk());
    }

    @Test
    public void getByBadRequest() throws Exception {
        Mockito.when(service.getById(1L)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/user/1")).andExpect(status().isBadRequest());
    }

    @Test
    public void updateIsOk() throws Exception {
        User user = new User();
        user.setUsername("test");
        user.setId(1L);
        user.setPassword("password");
        user.setAuthority("");
        Mockito.when(service.createOrUpdate(any(User.class))).thenReturn(user);
        mockMvc.perform(MockMvcRequestBuilders.put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                           {
                               "username":"",
                               "password":""
                           }
                        """))
                .andExpect(status().isAccepted());

    }

    @Test
    public void updateBadRequest() throws Exception {
        Mockito.when(service.createOrUpdate(any(User.class))).thenThrow(new idNotFoundException(""));
        mockMvc.perform(MockMvcRequestBuilders.put("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                           {
                               "id":"1",
                               "password":"test"
                           }
                        """))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                           {
                               "id":"1",
                               "password":"test"
                           }
                        """))
                .andExpect(status().isAccepted());
        Mockito.verify(service).createOrUpdate(any(User.class));
    }

    @Test
    public void addBadRequest() throws Exception {
        Mockito.when(service.createOrUpdate(any())).thenThrow(new IllegalArgumentException());
        mockMvc.perform(MockMvcRequestBuilders.post("/user")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                           {
                               "id":"1",
                               "password":"test"
                           }
                        """))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void deleteBadRequest() throws Exception {
        doThrow(new idNotFoundException("")).when(service).delete(any(Long.class));
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/1"))
                .andExpect(status().isBadRequest());
        Mockito.verify(service).delete(1L);
    }


    @Test
    public void deleteAuthorityIsOK() throws Exception {
        User user = new User();
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/authority")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                           {
                               "id":"1",
                               "password":"test",
                               "authority":"ROLE_USER"
                           }
                        """))
                .andExpect(status().isAccepted());
        Mockito.verify(service).removeAuthority(any(GrantedAuthority.class), any(Long.class));
    }

    @Test
    public void deleteIsBadRequest() throws Exception {
        doThrow(new RuntimeException("")).when(service).removeAuthority(any(GrantedAuthority.class), any(Long.class));
        mockMvc.perform(MockMvcRequestBuilders.delete("/user/1"))
                .andExpect(status().isAccepted());
        Mockito.verify(service).delete(1L);
    }

    @Test
    public void addAuthorityIsOK() throws Exception {
        User user = new User();
        mockMvc.perform(MockMvcRequestBuilders.post("/user/authority")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                           {
                               "id":"1",
                               "authority":"ROLE_USER"
                           }
                        """))
                .andExpect(status().isAccepted());
        Mockito.verify(service).addAuthority(any(GrantedAuthority.class), any(Long.class));
    }

    @Test
    public void addIsBadRequest() throws Exception {
        doThrow(new RuntimeException("")).when(service).addAuthority(any(GrantedAuthority.class), any(Long.class));
        mockMvc.perform(MockMvcRequestBuilders.post("/user/authority")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                           {
                               "id":"1",
                               "authority":"ROLE_USER"
                           }
                        """))
                .andExpect(status().isBadRequest());
    }


}
