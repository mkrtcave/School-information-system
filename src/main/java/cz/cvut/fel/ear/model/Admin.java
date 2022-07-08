package cz.cvut.fel.ear.model;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity(name = "admin")
public class Admin extends User{
    
    public Admin() {
        setRole(Role.ADMIN);
    }
}
