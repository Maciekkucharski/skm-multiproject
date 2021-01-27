package pl.edu.pjwstk.skmapi.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.skmapi.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService implements UserDetailsService {
//    private final List<User> users;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//        this.users = new ArrayList<>();
//        GrantedAuthority adminAuthority = () -> "ROLE_ADMIN";
//        List<GrantedAuthority> authorities = List.of(adminAuthority);
//        var encodedPassword = passwordEncoder.encode("admin1");
       //users.addAll(repository.findAll());
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
