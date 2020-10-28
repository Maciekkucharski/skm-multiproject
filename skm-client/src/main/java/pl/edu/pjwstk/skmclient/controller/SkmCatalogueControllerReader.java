package pl.edu.pjwstk.skmclient.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import pl.edu.pjwstk.skmclient.model.Skm;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class SkmCatalogueControllerReader {
    private Skm[] catalogue;
    private final String URI = "http://skmapi:11111";
    private RestTemplate template = new RestTemplate();


    @GetMapping("/")
    public List<Skm> getCatalogue(){
        catalogue = template.getForObject(URI,Skm[].class);
        return Arrays.asList(catalogue);
    }

    @GetMapping("/skm")
    public List<Object> getSkmIDs(){
        List<Object> IDs = new ArrayList<>();
        for (int i=0;i<catalogue.length;i++){
            IDs.add(catalogue[i].ID);
        }
        return IDs;
    }

    @GetMapping("/skm/{id}")
    public Skm getSpecificSkm(@PathVariable int id){
        return catalogue[id];
    }

}
