package pl.edu.pjwstk.skmapi.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.skmapi.exception.EmptyFieldException;
import pl.edu.pjwstk.skmapi.exception.idNotFoundException;
import pl.edu.pjwstk.skmapi.repository.UserRepository;
import pl.edu.pjwstk.skmapi.model.User;

@Service
public class UserService extends CrudService<User> implements UserDetailsService  {
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository) {
        super(repository);
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

    @Override
    public User createOrUpdate(User updateEntity) throws EmptyFieldException {
        return null;
    }

    public void removeAuthority(GrantedAuthority authority,Long id) throws UsernameNotFoundException{
        User toBeDeleted= repository.findById(id).orElseThrow(() -> new idNotFoundException("not a valid id"));
        toBeDeleted.removeAuthority(authority);
        repository.save(toBeDeleted);
    }

    public void addAuthority(GrantedAuthority authority,Long id) throws UsernameNotFoundException{
        User toBeDeleted= repository.findById(id).orElseThrow(() -> new idNotFoundException("not a valid id"));
        toBeDeleted.addAuthority(authority);
        repository.save(toBeDeleted);
    }
}
