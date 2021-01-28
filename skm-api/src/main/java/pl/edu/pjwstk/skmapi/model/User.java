package pl.edu.pjwstk.skmapi.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.edu.pjwstk.skmapi.services.DbEntity;

import javax.persistence.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Collectors;

@Entity
@Table(name = "users")
public class User implements UserDetails, DbEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String authority;

    public User() {}

    public User(String username, String password, String authority) {
        this.username = username;
        this.password = password;
        this.authority = authority;
    }
    //trzy metody z dołu nie są moim pomysłem,  tylko je zainicjowałem, ponieważ mi się  spodobał
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Arrays.stream(this.authority.split(",")).map(String::trim).filter(authority -> !authority.equals("")).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public void addAuthority(GrantedAuthority authority) {
        String trimmedAuthority = authority.getAuthority().trim();
        String currentAuthority = this.authority == null ? "" : (this.authority + ",");
        this.authority = currentAuthority + trimmedAuthority;
    }

    public void removeAuthority(GrantedAuthority authority) {
        String auth = authority.getAuthority().trim();
        String newAuthority = this.authority.replace(auth, "").replace(",,", "").trim();
        this.authority = newAuthority;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }
}
