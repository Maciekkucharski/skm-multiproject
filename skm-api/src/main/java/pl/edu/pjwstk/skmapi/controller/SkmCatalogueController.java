package pl.edu.pjwstk.skmapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.skmapi.model.Compartment;
import pl.edu.pjwstk.skmapi.model.Simulation;
import pl.edu.pjwstk.skmapi.model.Skm;
import pl.edu.pjwstk.skmapi.services.CrudService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

@RestController
public class SkmCatalogueController extends CrudController<Skm> {
    private Simulation simulation;

    @Autowired
    public SkmCatalogueController(CrudService<Skm> service,@Value("4") final int x, @Value("4") final int y, @Value("4") final int z) {
        super(service);
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

    @Override
    public Function<Skm, Map<String, Object>> transformToDTO() {
        return skm -> {
            var payload = new LinkedHashMap<String, Object>();
            payload.put("id", skm.getId());
            payload.put("capability", skm.getCapability());
            payload.put("station", skm.getStation());
            payload.put("compartments", skm.getCompartments().stream().map(Compartment::getId));
            payload.put("direction", skm.getToGdynia());
            payload.put("pauseCount", skm.getPauseCount());

            return payload;
        };
    }
}


