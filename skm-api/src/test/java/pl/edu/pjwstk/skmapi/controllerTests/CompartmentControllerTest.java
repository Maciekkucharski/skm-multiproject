package pl.edu.pjwstk.skmapi.controllerTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.edu.pjwstk.skmapi.controller.CompartmentController;
import pl.edu.pjwstk.skmapi.exception.idNotFoundException;
import pl.edu.pjwstk.skmapi.model.Compartment;
import pl.edu.pjwstk.skmapi.model.Skm;
import pl.edu.pjwstk.skmapi.repository.CompartmentRepository;
import pl.edu.pjwstk.skmapi.services.CompartmentService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CompartmentController.class)
public class CompartmentControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    CompartmentController controller;
    @MockBean
    CompartmentService service;

    @Test
    public void getAllTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/compartment").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        Mockito.verify(service).getAll();
    }

    @Test
    public void getByIdAccepted() throws Exception {
        Compartment compartment = new Compartment();
        compartment.setLimit(19);
        compartment.setId(1L);
        compartment.setSkm(new Skm());
        compartment.setHumans(List.of());
        Mockito.when(service.getById(1L)).thenReturn(compartment);
        mockMvc.perform(MockMvcRequestBuilders.get("/compartment/1")).andExpect(status().isOk());
    }

    @Test
    public void getByBadRequest() throws Exception {
        Mockito.when(service.getById(1L)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/compartment/1")).andExpect(status().isBadRequest());
    }

    @Test
    public void updateIsOk() throws Exception {
        Compartment compartment = new Compartment();
        compartment.setLimit(19);
        compartment.setId(1L);
        compartment.setSkm(new Skm());
        compartment.setHumans(List.of());
        Mockito.when(service.createOrUpdate(any(Compartment.class))).thenReturn(compartment);
        mockMvc.perform(MockMvcRequestBuilders.put("/compartment")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"1\", \"limit\":\"1\"}"))
                .andExpect(status().isAccepted());

    }

    @Test
    public void updateBadRequest() throws Exception {
        Mockito.when(service.createOrUpdate(any(Compartment.class))).thenThrow(new idNotFoundException(""));
        mockMvc.perform(MockMvcRequestBuilders.put("/compartment")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"limit\":\"1\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/compartment")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"1\",\"limit\":\"1\"}"))
                .andExpect(status().isAccepted());
        Mockito.verify(service).createOrUpdate(any(Compartment.class));
    }
    @Test
    public void addBadRequest() throws Exception {
        Mockito.when(service.createOrUpdate(any())).thenThrow(new IllegalArgumentException());
        mockMvc.perform(MockMvcRequestBuilders.post("/compartment")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"1\",\"limit\":\"1\"}"))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void deleteBadRequest() throws Exception {
        doThrow(new idNotFoundException("")).when(service).delete(any(Long.class));
        mockMvc.perform(MockMvcRequestBuilders.delete("/compartment/1"))
                .andExpect(status().isBadRequest());
        Mockito.verify(service).delete(1L);
    }
    @Test
    public void deleteIsAccepted() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/compartment/1"))
                .andExpect(status().isAccepted());
        Mockito.verify(service).delete(1L);
    }



}
