package cz.cvut.fel.ear.model;

import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity(name = "system_user")
@Inheritance(strategy = InheritanceType.JOINED)
@NamedQueries({
        @NamedQuery(name = "User.findByUsername", query = "SELECT s FROM system_user s WHERE s.userName = :userName")
})
public class User extends AbstractEntity{
    
    @Basic(optional = false)
    @Column(nullable = false)
    private String firstName;
    
    @Basic(optional = false)
    @Column(nullable = false)
    private String lastName;
    
    @Basic(optional = false)
    @Column(nullable = false, unique = true)
    private String userName;
    
    @Basic(optional = false)
    @Column(nullable = false)
    private String password;
    
    @Enumerated(EnumType.STRING)
    private Role role;
    
    public User() {}

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String secondName) {
        this.lastName = secondName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public void encodePassword(PasswordEncoder encoder){
        this.password = encoder.encode(password);
    }
    
    public void erasePassword(){
        this.password = null;
    }
    
    public String getFirstName() {
        return firstName;
    }

    public String getSecondName() {
        return lastName;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getLastName() {
        return lastName;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
    
    @Override
    public String toString() {
        return "User{" + "firstName=" + firstName 
                + ", secondName=" + lastName 
                + ", userName=" + userName + '}';
    }
}
