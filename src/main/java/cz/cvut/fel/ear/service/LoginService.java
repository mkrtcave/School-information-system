package cz.cvut.fel.ear.service;

import cz.cvut.fel.ear.security.DefaultAuthenticationProvider;
import cz.cvut.fel.ear.security.SecurityUtils;
import cz.cvut.fel.ear.security.model.UserDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService {
    
    private DefaultAuthenticationProvider provider;
    private final UserDetailsService userDetailsService;
    
    @Autowired
    public LoginService(DefaultAuthenticationProvider provider, UserDetailsService userDetailsService) {
        this.provider = provider;
        this.userDetailsService = userDetailsService;
    }
    
    @Transactional
    public void loginUser (String username, String password){
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, password);
        provider.authenticate(authentication);
        final UserDetails userDetails = (UserDetails) userDetailsService.loadUserByUsername(username);
        SecurityUtils.setCurrentUser(userDetails);
    }


}
