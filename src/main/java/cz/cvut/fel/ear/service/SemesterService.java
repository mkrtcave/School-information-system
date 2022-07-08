package cz.cvut.fel.ear.service;

import cz.cvut.fel.ear.dao.SemesterDao;
import cz.cvut.fel.ear.model.Program;
import cz.cvut.fel.ear.model.Semester;
import cz.cvut.fel.ear.model.Student;
import cz.cvut.fel.ear.model.Subject;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SemesterService {
    
    private SemesterDao dao;
    
    @Autowired
    public SemesterService(SemesterDao dao){
        this.dao = dao;
    }
    
    @Transactional
    public List<Semester> findAllSemestersFromProgram(Program program){
        Objects.requireNonNull(program);
        return dao.findAll(program);
    }
    
    @Transactional
    public Semester getById(Integer id){
        return dao.find(id);
    }
    
    @Transactional
    public void persist(Semester semester){
        Objects.requireNonNull(semester);
        dao.persist(semester);
    }
    
    @Transactional
    public void addSubject(Semester semester, Subject subject){
        Objects.requireNonNull(semester);
        Objects.requireNonNull(subject);
        semester.addSubject(subject);
        dao.update(semester);
    }
    
    @Transactional
    public void removeSubject(Semester semester, Subject subject){
        Objects.requireNonNull(semester);
        Objects.requireNonNull(subject);
        semester.removeSubject(subject);
        dao.update(semester);
    }
    
    @Transactional
    public void addStudent(Semester semester, Student student){
        Objects.requireNonNull(semester);
        Objects.requireNonNull(student);
        semester.addStudent(student);
        dao.update(semester);
    }
    
    @Transactional
    public void removeStudent(Semester semester, Student student){
        Objects.requireNonNull(semester);
        Objects.requireNonNull(student);
        semester.removeStudent(student);
        dao.update(semester);
    }
}
