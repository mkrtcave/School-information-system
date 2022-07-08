package cz.cvut.fel.ear.service;

import cz.cvut.fel.ear.dao.StudentDao;
import cz.cvut.fel.ear.model.Exam;
import cz.cvut.fel.ear.model.Program;
import cz.cvut.fel.ear.model.Semester;
import cz.cvut.fel.ear.model.Student;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class StudentService {
    
    private final StudentDao dao;
    
    private final PasswordEncoder passwordEncoder;
    
    @Autowired
    public StudentService(StudentDao dao, PasswordEncoder passwordEncoder){
        this.dao = dao;
        this.passwordEncoder = passwordEncoder;
    }
    
    @Transactional
    public void persist(Student student){
        Objects.requireNonNull(student);
        student.encodePassword(passwordEncoder);
        dao.persist(student);
    }
    
    @Transactional
    public List<Student> findAll(){
        return dao.findAll();
    }
    
    @Transactional
    public Student getById(Integer id){
        return dao.find(id);
    }
    
    @Transactional
    public List<Student> findAllStudentsFromSemester(Semester semester){
        Objects.requireNonNull(semester);
        return dao.findAllFromSemester(semester);
    }
    
    @Transactional
    public List<Student> findAllStudentsFromProgram(Program program){
        Objects.requireNonNull(program);
        return dao.findAllFromProgram(program);
    }
    
    @Transactional
    public void addExam(Student student, Exam exam){
        Objects.requireNonNull(student);
        Objects.requireNonNull(exam);
        student.addExam(exam);
        dao.update(student);
    }
    
    @Transactional
    public void removeExam(Student student, Exam exam){
        Objects.requireNonNull(student);
        Objects.requireNonNull(exam);
        student.removeExam(exam);
        dao.update(student);
    }
    
    @Transactional
    public void addParallel(Student student, Exam exam){
        Objects.requireNonNull(student);
        Objects.requireNonNull(exam);
//        student.addParallel(exam);
        dao.update(student);
    }
    
}
