package cz.cvut.fel.ear.service;

import cz.cvut.fel.ear.dao.TeacherDao;
import cz.cvut.fel.ear.model.Subject;
import cz.cvut.fel.ear.model.Teacher;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TeacherService {
    
    private TeacherDao dao;
    
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public TeacherService(TeacherDao dao, PasswordEncoder passwordEncoder){
        this.dao = dao;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Transactional
    public Teacher getById(Integer id){
        return dao.find(id);
    }
    
    @Transactional
    public void persist(Teacher teacher){
        Objects.requireNonNull(teacher);
        teacher.encodePassword(passwordEncoder);
        dao.persist(teacher);
    }
    
    @Transactional
    public void addSubject(Teacher teacher, Subject subject){
        Objects.requireNonNull(teacher);
        Objects.requireNonNull(subject);
        teacher.addSubject(subject);
        dao.persist(teacher);
    }
    
    @Transactional
    public void removeSubject(Teacher teacher, Subject subject){
        Objects.requireNonNull(teacher);
        Objects.requireNonNull(subject);
        teacher.removeSubject(subject);
        dao.persist(teacher);
    }
}
