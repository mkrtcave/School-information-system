package cz.cvut.fel.ear.dao;

import cz.cvut.fel.ear.model.Program;
import cz.cvut.fel.ear.model.Semester;
import cz.cvut.fel.ear.model.Student;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Repository;

@Repository
public class StudentDao extends BaseDao<Student> {
    
    public StudentDao() {
        super(Student.class);
    }
    
    public List<Student> findAllFromProgram(Program program){
        Objects.requireNonNull(program);
        return em.createQuery("SELECT s FROM Student s WHERE s.program.id = " 
                + program.getId(), Student.class).getResultList();
    }
    
    public List<Student> findAllFromSemester(Semester semester){
        Objects.requireNonNull(semester);
        return em.createQuery("SELECT s FROM Student s WHERE s.semester.id = " 
                + semester.getId(), Student.class).getResultList();
    }
}
