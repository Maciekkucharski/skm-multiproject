package pl.edu.pjwstk.skmapi.controller;

import pl.edu.pjwstk.skmapi.services.CrudService;
import pl.edu.pjwstk.skmapi.services.DbEntity;

import java.util.Map;
import java.util.function.Function;


public abstract class CrudController<T extends DbEntity> {
//    @Autowired
//    private final CrudService<T> service;

    protected CrudController(CrudService<T> service) {
//        this.service = service;
    }


//    @GetMapping()
//    public ResponseEntity<List<Map<String, Object>>> getAll(@RequestParam(name = "size", defaultValue = "4") int size, @RequestParam(name = "page", defaultValue = "0") int page, @RequestParam(name = "sort", defaultValue = "asc,id") String sort) {
//        try {
//            String[] sortType = sort.split(",");
//            List<T> all = service.getAll(page, size, sortType);
//            List<Map<String, Object>> payload = all.stream()
//                    .map(obj -> transformToDTO().apply(obj))
//                    .collect(Collectors.toList());
//            Map<String, Object> data = new HashMap<>();
//            data.put("Size", size);
//            data.put("Number", page);
//            data.put("Direction", sortType[0]);
//            data.put("Sorting key", sortType[1]);
//            payload.add(data);
//            return new ResponseEntity<>(payload, HttpStatus.OK);
//        } catch (Exception e) {
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    public abstract Function<T, Map<String, Object>> transformToDTO();
}
