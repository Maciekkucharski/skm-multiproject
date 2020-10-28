package pl.edu.pjwstk.skmclient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.edu.pjwstk.skmclient.model.Skm;

import java.util.Arrays;
import java.util.List;

@RestController
public class SkmCatalogueControllerReader {
    private final String URI = "http://skmapi:11111/";


    @GetMapping("/")
    public List<Skm> getCatalogue(){
        RestTemplate template =new RestTemplate();
        Skm[] catalogue =template.getForObject(URI,Skm[].class);
        return Arrays.asList(catalogue);
    }

}
