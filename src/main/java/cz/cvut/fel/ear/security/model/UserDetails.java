package cz.cvut.fel.ear.security.model;

import cz.cvut.fel.ear.model.User;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;


public class UserDetails implements org.springframework.security.core.userdetails.UserDetails{
    
    private User user;

    private final Set<GrantedAuthority> authorities;
    
    public UserDetails(User user) {
        Objects.requireNonNull(user);
        this.user = user;
        this.authorities = new HashSet<>();
        addUserRole();
    }
    
    public UserDetails(User user, Collection<GrantedAuthority> authorities) {
        Objects.requireNonNull(user);
        Objects.requireNonNull(authorities);
        this.user = user;
        this.authorities = new HashSet<>();
        addUserRole();
        this.authorities.addAll(authorities);
    }
    
    private void addUserRole() {
        authorities.add(new SimpleGrantedAuthority(user.getRole().toString()));
    }
    
    public User getUser() {
        return user;
    }
    
    public void eraseCredentials() {
        user.erasePassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.unmodifiableCollection(authorities);
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
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
    
}
