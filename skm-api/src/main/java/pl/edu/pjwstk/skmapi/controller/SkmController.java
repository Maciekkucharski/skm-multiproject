package pl.edu.pjwstk.skmapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.skmapi.model.Compartment;
import pl.edu.pjwstk.skmapi.model.Skm;
import pl.edu.pjwstk.skmapi.repository.CompartmentRepository;
import pl.edu.pjwstk.skmapi.repository.SkmRepository;
import pl.edu.pjwstk.skmapi.services.CrudService;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

@RestController
@RequestMapping("/skm")
public class SkmController extends CrudController<Skm> {
    private final CompartmentRepository compartmentRepository;
    private final SkmRepository skmRepository;

    @Autowired
    public SkmController(CrudService<Skm> service, CompartmentRepository compartmentRepository, SkmRepository skmRepository) {
        super(service);
        this.compartmentRepository = compartmentRepository;
        this.skmRepository = skmRepository;
    }




    @Override
    public Function<Skm, Map<String, Object>> transformToDTO() {
        return skm -> {
            var payload = new LinkedHashMap<String, Object>();
            payload.put("id", skm.getId());
            payload.put("station", skm.getStation());
            payload.put("compartments", skm.getCompartments().stream().map(Compartment::getId));
            payload.put("direction", skm.getToGdynia());
            payload.put("pauseCount", skm.getPauseCount());

            return payload;
        };
    }
}


