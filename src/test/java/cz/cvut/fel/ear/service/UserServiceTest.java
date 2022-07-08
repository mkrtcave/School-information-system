package cz.cvut.fel.ear.service;

import cz.cvut.fel.ear.dao.UserDao;
import cz.cvut.fel.ear.dao.enviroment.Generator;
import cz.cvut.fel.ear.model.User;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import static org.mockito.Mockito.verify;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {
    
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @Mock
    private UserDao userDaoMock;
    
    
    private UserService sut;
    
    @BeforeEach
    public void setUp(){
        this.sut = new UserService(userDaoMock, passwordEncoder);
    }
    
    @Test
    public void persistEncodesUserPassword() {
        final User user = Generator.generateUser();
        final String password = user.getPassword();
        sut.persist(user);
        
        final ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(userDaoMock).persist(captor.capture());
        assertTrue(passwordEncoder.matches(password, captor.getValue().getPassword()));
    }
}
