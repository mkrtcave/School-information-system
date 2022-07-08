package cz.cvut.fel.ear.dao;

import cz.cvut.fel.ear.model.Parallel;
import cz.cvut.fel.ear.model.Subject;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Repository;

@Repository
public class ParallelDao extends BaseDao<Parallel>{
    
    public ParallelDao() {
        super(Parallel.class);
    }
    
    public List<Parallel> findAll(Subject subject){
        Objects.requireNonNull(subject);
        return em.createNamedQuery("Parallel.findBySubject", Parallel.class)
                .setParameter("id", subject.getId()).getResultList();
    }
    
    public List<Parallel> findFreeParallels(Subject subject){
        LocalDateTime date = LocalDateTime.now();
        Objects.requireNonNull(subject);
        return em.createQuery("SELECT p FROM Parallel p WHERE p.subject.id = " 
            + subject.getId() + " AND size(p.students) < p.capacity AND p.time > " + date,
                Parallel.class).getResultList();
    }
}
