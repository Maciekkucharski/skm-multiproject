package pl.edu.pjwstk.skmapi.controllerTests;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import pl.edu.pjwstk.skmapi.controller.SimulationController;
import pl.edu.pjwstk.skmapi.services.SimulationService;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SimulationController.class)
public class SimulationControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SimulationService service;

    @Test
    public void moveBadRequest() throws Exception {
        doThrow(new RuntimeException()).when(service).move();
        mockMvc.perform(MockMvcRequestBuilders.post("/move")).andExpect(status().isBadRequest());
    }

    @Test
    public void moveIsOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/move")).andExpect(status().isOk());
    }

}
