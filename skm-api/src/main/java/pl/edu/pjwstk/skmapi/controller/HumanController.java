package pl.edu.pjwstk.skmapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.skmapi.model.Human;
import pl.edu.pjwstk.skmapi.services.CrudService;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

@RestController
@RequestMapping("/passengers")
public class HumanController extends CrudController<Human> {
    protected HumanController(CrudService<Human> service) {
        super(service);
    }

    @Override
    public Function<Human, Map<String, Object>> transformToDTO() {
        return passenger -> {
            var payload = new LinkedHashMap<String, Object>();
            payload.put("id", passenger.getId());
            payload.put("name", passenger.getName());
            payload.put("surname", passenger.getSurname());
            payload.put("endpoint", passenger.getEndpoint());
            payload.put("compartment_id", passenger.getCompartment().getId());

            return payload;
        };
    }
}
