package pl.edu.pjwstk.skmapi.services;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import pl.edu.pjwstk.skmapi.exception.idNotFoundException;
import pl.edu.pjwstk.skmapi.model.User;
import pl.edu.pjwstk.skmapi.repository.UserRepository;

import java.util.Optional;

import static pl.edu.pjwstk.skmapi.util.Utils.fallbackIfNull;

@Service
public class UserService extends CrudService<User> implements UserDetailsService  {
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository) {
        super(repository);
        passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();


    }

    @Override
    public UserDetails loadUserByUsername(String username)  {
        return repository.findAll().stream()
                .filter(u -> u.getUsername().equals(username))
                .findAny()
                .orElse(null);
    }

    public PasswordEncoder getPasswordEncoder() {
        return passwordEncoder;
    }

    @Override
    public User createOrUpdate(User updateEntity) {
        updateEntity.setPassword(passwordEncoder.encode(updateEntity.getPassword()));
        if (updateEntity.getId() == null) {
            return repository.save(updateEntity);
        }

        Optional<User> userInDb = repository.findById(updateEntity.getId());

        if (userInDb.isPresent()) {
            var dbEntity = userInDb.get();

            dbEntity.setPassword(fallbackIfNull(updateEntity.getPassword(), dbEntity.getPassword()));
            dbEntity.setUsername(fallbackIfNull(updateEntity.getUsername(), dbEntity.getUsername()));
            dbEntity.setAuthority(fallbackIfNull(updateEntity.getAuthority(), dbEntity.getAuthority()));


            return repository.save(dbEntity);
        } else {
            throw new idNotFoundException("nie znaleziono skm'ki");
        }
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
