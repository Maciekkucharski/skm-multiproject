package pl.edu.pjwstk.skmapi.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.edu.pjwstk.skmapi.model.User;
import pl.edu.pjwstk.skmapi.services.CrudService;
import pl.edu.pjwstk.skmapi.services.UserService;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.Function;

@RestController
@RequestMapping("/user")
public class UserController extends CrudController<User> {
    private final UserService userService;
    protected UserController(CrudService<User> service, UserService userService) {
        super(service);
        this.userService = userService;
    }


    @Override
    public Function<User, Map<String, Object>> transformToDTO() {
        return user -> {
            var payload = new LinkedHashMap<String, Object>();
            payload.put("id", user.getId());
            payload.put("username", user.getUsername());
            payload.put("authorities", user.getAuthorities());
            return payload;
        };
    }


    @DeleteMapping("/authority")
    public ResponseEntity<Void> deleteAuthority(@RequestBody User user) {
        try {
            userService.removeAuthority(() -> user.getAuthority(), user.getId());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/authority")
    public ResponseEntity<Void> addAuthority(@RequestBody User user) {
        try {
            userService.addAuthority(()->user.getAuthority(),user.getId());
            return new ResponseEntity<>(HttpStatus.ACCEPTED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
