package cz.cvut.fel.ear.dao;

import cz.cvut.fel.ear.model.Program;
import cz.cvut.fel.ear.model.Semester;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Repository;


@Repository
public class SemesterDao extends BaseDao<Semester> {
    
    public SemesterDao() {
        super(Semester.class);
    }
    
    public List<Semester> findAll(Program program){
        Objects.requireNonNull(program);
        return em.createNamedQuery("Semester.findAllByProgram", Semester.class)
                .setParameter("id", program.getId()).getResultList();
    }
    
    
}
