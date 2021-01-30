package pl.edu.pjwstk.skmapi.controllerTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.edu.pjwstk.skmapi.controller.HumanController;
import pl.edu.pjwstk.skmapi.exception.idNotFoundException;
import pl.edu.pjwstk.skmapi.model.Compartment;
import pl.edu.pjwstk.skmapi.model.Human;
import pl.edu.pjwstk.skmapi.model.Stations;
import pl.edu.pjwstk.skmapi.services.HumanService;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@WithMockUser(authorities = {"ROLE_ADMIN"})
@RunWith(SpringRunner.class)
@WebMvcTest(HumanController.class)
public class HumanControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    HumanController controller;
    @MockBean
    HumanService service;

    @Test
    public void getAllTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/passengers").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        Mockito.verify(service).getAll();
    }

    @Test
    public void getByIdAccepted() throws Exception {
        Human human = new Human();
        human.setId(1L);
        human.setEndpoint(Stations.GD_OLI);
        human.setCompartment(new Compartment());
        Mockito.when(service.getById(1L)).thenReturn(human);
        mockMvc.perform(MockMvcRequestBuilders.get("/passengers/1")).andExpect(status().isOk());
    }

    @Test
    public void getByBadRequest() throws Exception {
        Mockito.when(service.getById(1L)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/passengers/1")).andExpect(status().isBadRequest());
    }

    @Test
    public void deleteBadRequest() throws Exception {
        doThrow(new idNotFoundException("")).when(service).delete(any(Long.class));
        mockMvc.perform(MockMvcRequestBuilders.delete("/passengers/1"))
                .andExpect(status().isBadRequest());
        Mockito.verify(service).delete(1L);
    }
    @Test
    public void deleteIsAccepted() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/passengers/1"))
                .andExpect(status().isAccepted());
        Mockito.verify(service).delete(1L);
    }


}
