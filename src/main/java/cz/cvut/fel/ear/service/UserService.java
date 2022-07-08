package cz.cvut.fel.ear.service;

import cz.cvut.fel.ear.dao.UserDao;
import cz.cvut.fel.ear.model.User;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
    
    private final UserDao dao;
    
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public UserService(UserDao dao, PasswordEncoder passwordEncoder){
        this.dao = dao;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Transactional
    public User getById(Integer id){
        return dao.find(id);
    }
    
    public User getByUserName(String username){
        return dao.findByUsername(username);
    }
    
    @Transactional
    public void persist(User user) {
        user.encodePassword(passwordEncoder);
        dao.persist(user);
    }
    
    @Transactional(readOnly = true)
    public boolean exists(String username){
        return dao.findByUsername(username) != null;
    }
}
