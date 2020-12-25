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
import pl.edu.pjwstk.skmapi.controller.SkmController;
import pl.edu.pjwstk.skmapi.exception.idNotFoundException;
import pl.edu.pjwstk.skmapi.model.Skm;
import pl.edu.pjwstk.skmapi.model.Stations;
import pl.edu.pjwstk.skmapi.services.SkmService;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SkmController.class)
public class SkmControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    SkmController controller;

    @MockBean
    SkmService service;
    @Test
    public void getAllTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/skm").contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
        Mockito.verify(service).getAll();
    }

    @Test
    public void getByIdAccepted() throws Exception {
        Skm skm = new Skm();
        skm.setStation(Stations.GD_ZAB);
        skm.setId(1L);
        skm.setCompartments(new ArrayList<>());
        skm.setPauseCount(1);
        skm.setToGdynia(true);
        Mockito.when(service.getById(1L)).thenReturn(skm);
        mockMvc.perform(MockMvcRequestBuilders.get("/skm/1")).andExpect(status().isOk());
    }

    @Test
    public void getByBadRequest() throws Exception {
        Mockito.when(service.getById(1L)).thenReturn(null);
        mockMvc.perform(MockMvcRequestBuilders.get("/skm/1")).andExpect(status().isBadRequest());
    }

    @Test
    public void updateIsOk() throws Exception {
        Skm skm = new Skm();
        skm.setStation(Stations.GD_ZAB);
        skm.setId(1L);
        skm.setCompartments(new ArrayList<>());
        skm.setPauseCount(1);
        skm.setToGdynia(true);
        Mockito.when(service.createOrUpdate(any(Skm.class))).thenReturn(skm);
        mockMvc.perform(MockMvcRequestBuilders.put("/skm")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"1\", \"pauseCount\":\"1\"}"))
                .andExpect(status().isAccepted());

    }

    @Test
    public void updateBadRequest() throws Exception {
        Mockito.when(service.createOrUpdate(any(Skm.class))).thenThrow(new idNotFoundException(""));
        mockMvc.perform(MockMvcRequestBuilders.put("/skm")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"pauseCount\":\"1\"}"))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void addTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/skm")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"1\",\"pauseCount\":\"1\"}"))
                .andExpect(status().isAccepted());
        Mockito.verify(service).createOrUpdate(any(Skm.class));
    }
    @Test
    public void addBadRequest() throws Exception {
        Mockito.when(service.createOrUpdate(any())).thenThrow(new IllegalArgumentException());
        mockMvc.perform(MockMvcRequestBuilders.post("/skm")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":\"1\",\"pauseCount\":\"1\"}"))
                .andExpect(status().isBadRequest());
    }
    @Test
    public void deleteBadRequest() throws Exception {
        doThrow(new idNotFoundException("")).when(service).delete(any(Long.class));
        mockMvc.perform(MockMvcRequestBuilders.delete("/skm/1"))
                .andExpect(status().isBadRequest());
        Mockito.verify(service).delete(1L);
    }
    @Test
    public void deleteIsAccepted() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/skm/1"))
                .andExpect(status().isAccepted());
        Mockito.verify(service).delete(1L);
    }

}
