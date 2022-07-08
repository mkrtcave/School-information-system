package cz.cvut.fel.ear;

import cz.cvut.fel.ear.dao.StudentDao;
import javax.persistence.EntityManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SchoolProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(SchoolProjectApplication.class, args);
    }
}
