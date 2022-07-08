package cz.cvut.fel.ear.dao;

import cz.cvut.fel.ear.model.Teacher;
import org.springframework.stereotype.Repository;

@Repository
public class TeacherDao extends BaseDao<Teacher> {
    
    public TeacherDao() {
        super(Teacher.class);
    }
    
}
