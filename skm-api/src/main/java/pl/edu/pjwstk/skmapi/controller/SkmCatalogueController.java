package pl.edu.pjwstk.skmapi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.skmapi.model.Simulation;
import pl.edu.pjwstk.skmapi.model.Skm;

import java.util.List;

@RestController
public class SkmCatalogueController {
    private Simulation simulation;


    public SkmCatalogueController(@Value("${envX}") final int x, @Value("${envY}") final int y, @Value("${envZ}") final int z) {
        simulation = new Simulation(x, y, z);
    }

    @GetMapping("/")
    public List<Skm> simulation() {
        return this.simulation.getSkms();
    }

    @GetMapping("/move")
    public void move() {
        simulation.move();
    }

    @GetMapping("/skm")
    public List<Object> skm() {
        return simulation.getSkmsIDs();
    }

    @GetMapping("/skm/{id}")
    public Skm skmID(@PathVariable int id) {
        return simulation.getSkms().get(id);
    }

    @GetMapping("/skm/{id}/compartment")
    public List<Object> skmConpartment(@PathVariable int id) {
        return simulation.getCompartments(id);
    }

}


