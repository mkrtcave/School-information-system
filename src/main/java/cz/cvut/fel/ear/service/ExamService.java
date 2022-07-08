package cz.cvut.fel.ear.service;

import cz.cvut.fel.ear.dao.ExamDao;
import cz.cvut.fel.ear.model.Exam;
import cz.cvut.fel.ear.model.Student;
import cz.cvut.fel.ear.model.Subject;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExamService {
    
    private ExamDao dao;
    
    @Autowired
    public ExamService(ExamDao dao){
        this.dao = dao;
    }
    
    @Transactional
    public Exam getById(Integer id){
        return dao.find(id);
    }
    
    @Transactional
    public void persist(Exam exam){
        Objects.requireNonNull(exam);
        dao.persist(exam);
    }
    
    @Transactional
    public List<Exam> findAllExamsBySubject(Subject subject){
        Objects.requireNonNull(subject);
        return dao.findAll(subject);
    }
    
    @Transactional
    public List<Exam> findAllAvailableExamsBySubject(Subject subject){
        Objects.requireNonNull(subject);
        return dao.findAvailableExams(subject);
    }
    
//    @Transactional
//    public List<Exam> findAllExamsByStudent(Student student){
//        Objects.requireNonNull(student);
//        return dao.findAvailableExams(student);
//    }
    
    
    @Transactional
    public void addStudent(Exam exam, Student student){
        Objects.requireNonNull(student);
        exam.addExaminee(student);
        dao.update(exam);
    }
    
    @Transactional
    public void removeStudent(Exam exam, Student student){
        Objects.requireNonNull(student);
        exam.removeExaminee(student);
        dao.update(exam);
    }
}
