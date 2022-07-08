package cz.cvut.fel.ear.dao;

import cz.cvut.fel.ear.model.Subject;
import org.springframework.stereotype.Repository;

@Repository
public class SubjectDao extends BaseDao<Subject>{
    
    public SubjectDao() {
        super(Subject.class);
    }
    
}
