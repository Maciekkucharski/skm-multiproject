package pl.edu.pjwstk.skmapi.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.skmapi.model.Compartment;
import pl.edu.pjwstk.skmapi.model.Human;
import pl.edu.pjwstk.skmapi.services.CompartmentService;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

@RestController
@RequestMapping("/compartment")
public class CompartmentController extends CrudController<Compartment> {


    public CompartmentController(CompartmentService service) {
        super(service);
    }

    public Function<Compartment, Map<String, Object>> transformToDTO() {
        return compartment -> {
            var payload = new LinkedHashMap<String, Object>();
            payload.put("id", compartment.getId());
            payload.put("limitation", compartment.getLimit());
            payload.put("skm_id", compartment.getSkm().getId());
            payload.put("Humans", compartment.getHumans().stream().map(Human::getId));

            return payload;
        };
    }


}
