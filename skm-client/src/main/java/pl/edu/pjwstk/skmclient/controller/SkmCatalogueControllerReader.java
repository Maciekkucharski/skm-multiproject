package pl.edu.pjwstk.skmclient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.edu.pjwstk.skmclient.model.Skm;

import java.util.Arrays;
import java.util.List;

@RestController
public class SkmCatalogueControllerReader {
    private final String URI = "http://skmapi:11111";
    private RestTemplate template = new RestTemplate();


    @GetMapping("/")
    public List<Skm> getCatalogue(){
        Skm[] catalogue = template.getForObject(URI,Skm[].class);
        return Arrays.asList(catalogue);
    }

//    @GetMapping("/")
//    public String status() {
//        List<Integer> result = Arrays.asList(template.getForObject(URI, Integer[].class));
//
//        return result.toString();
//    }



}
