package pl.edu.pjwstk.skmapi.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.skmapi.repository.UserRepository;
import pl.edu.pjwstk.skmapi.model.User;

@Service
public class UserService implements UserDetailsService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String encodedPassword = passwordEncoder.encode("piw0");
        repository.save( new User("user", encodedPassword, "ROLE_USER"));
        repository.save( new User("privileged", encodedPassword, "ROLE_PRIVILEGED"));
        repository.save( new User("admin", encodedPassword, "ROLE_ADMIN"));

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return repository.findAll().stream()
                .filter(u -> u.getUsername().equals(username))
                .findAny()
                .orElse(null);
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }
}
