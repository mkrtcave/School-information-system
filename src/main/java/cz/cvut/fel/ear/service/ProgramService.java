package cz.cvut.fel.ear.service;

import cz.cvut.fel.ear.dao.ProgramDao;
import cz.cvut.fel.ear.dao.SemesterDao;
import cz.cvut.fel.ear.dao.StudentDao;
import cz.cvut.fel.ear.exception.AlreadyContainsException;
import cz.cvut.fel.ear.model.Program;
import cz.cvut.fel.ear.model.Semester;
import cz.cvut.fel.ear.model.Student;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProgramService {
    
    private ProgramDao dao;
    
    private SemesterDao semesterDao;
    
    private StudentDao studentDao;
    
    @Autowired
    public ProgramService(ProgramDao dao, SemesterDao semesterDao,  
            StudentDao studentDao){
        this.dao = dao;
        this.semesterDao = semesterDao;
        this.studentDao = studentDao;
    }
    
    @Transactional
    public void persist(Program program){
        Objects.requireNonNull(program);
        dao.persist(program);
    }
    
    
    @Transactional
    public List<Program> findAll(){
        return dao.findAll();
    }
    
    @Transactional
    public Program findById(Integer id){
        return dao.find(id);
    }
    
    @Transactional
    public void addSemester(Program program, Semester semester){
        Objects.requireNonNull(program);
        Objects.requireNonNull(semester);
        semesterDao.persist(semester);
        program.addSemester(semester);
        dao.update(program);
    }
    
    @Transactional
    public void removeSemester(Program program, Semester semester){
        Objects.requireNonNull(program);
        Objects.requireNonNull(semester);
        program.removeSemester(semester);
        semesterDao.remove(semester);
        dao.update(program);
    }
    
    @Transactional
    public void addStudent(Program program, Student student){
        Objects.requireNonNull(program);
        Objects.requireNonNull(student);
        if(program.getStudents().contains(student)){
            throw AlreadyContainsException.create(program, student);
        }
        student.setProgram(program);
        studentDao.update(student);
        program.addStudent(student);
        dao.update(program);
    }
    
    @Transactional
    public void removeStudent(Program program, Student student){
        Objects.requireNonNull(program);
        Objects.requireNonNull(student);
        studentDao.update(student);
        program.removeStudent(student);
        dao.update(program);
    }
}
