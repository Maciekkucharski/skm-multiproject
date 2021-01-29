package pl.edu.pjwstk.skmapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.edu.pjwstk.skmapi.exception.EmptyFieldException;
import pl.edu.pjwstk.skmapi.model.User;
import pl.edu.pjwstk.skmapi.services.UserService;

@RestController
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity register(@RequestBody User user) throws EmptyFieldException {
        try {
            if (userService.loadUserByUsername(user.getUsername()) != null) {
                return new ResponseEntity(HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        user.setAuthority("ROLE_USER");
        userService.createOrUpdate(user);
        return new ResponseEntity(HttpStatus.OK);
    }

}
