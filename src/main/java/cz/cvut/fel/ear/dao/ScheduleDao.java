    package cz.cvut.fel.ear.dao;

import cz.cvut.fel.ear.model.Schedule;
import cz.cvut.fel.ear.model.Student;
import java.util.Objects;
import org.springframework.stereotype.Repository;

@Repository
public class ScheduleDao extends BaseDao<Schedule>{
    
    public ScheduleDao() {
        super(Schedule.class);
    }
    
    public Schedule findByOwner(Student student){
        Objects.requireNonNull(student);
        return em.createNamedQuery("Semester.findAllByProgram", Schedule.class)
                .setParameter("id", student.getId()).getSingleResult();
    }
    
}
