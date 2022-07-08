package cz.cvut.fel.ear.dao;

import com.sun.jmx.snmp.Timestamp;
import cz.cvut.fel.ear.model.Exam;
import cz.cvut.fel.ear.model.Student;
import cz.cvut.fel.ear.model.Subject;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TemporalType;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
public class ExamDao extends BaseDao<Exam>{
      
    public ExamDao() {
        super(Exam.class);
    }
    
    public List<Exam> findAll(Subject subject){
        Objects.requireNonNull(subject);
        return em.createNamedQuery("Exam.findBySubject", Exam.class)
                .setParameter("id", subject.getId()).getResultList();
    }
    
    public List<Exam> findAvailableExams(Subject subject){
        Objects.requireNonNull(subject);
        return em.createQuery("SELECT e FROM Exam e WHERE e.subject.id = " 
          + subject.getId() + " AND size(e.examinees) < e.capacity AND e.date > CURRENT_TIMESTAMP",
            Exam.class).getResultList();
    }
    
    public List<Exam> findExamsByStudent(Student student){
        Objects.requireNonNull(student);
        return em.createNamedQuery("Exam.findByStudent", Exam.class)
                .setParameter("id", student.getId()).getResultList();
    }
}
