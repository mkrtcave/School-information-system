package cz.cvut.fel.ear.service;

import cz.cvut.fel.ear.dao.ExamDao;
import cz.cvut.fel.ear.dao.LectureDao;
import cz.cvut.fel.ear.dao.ParallelDao;
import cz.cvut.fel.ear.dao.SubjectDao;
import cz.cvut.fel.ear.model.Exam;
import cz.cvut.fel.ear.model.Lecture;
import cz.cvut.fel.ear.model.Parallel;
import cz.cvut.fel.ear.model.Semester;
import cz.cvut.fel.ear.model.Subject;
import cz.cvut.fel.ear.model.Teacher;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
public class SubjectService {
    
    private final SubjectDao dao;
    
    private final ParallelDao parallelDao;
    
    private final ExamDao examDao;
    
    private final LectureDao lectureDao;
    
    @Autowired
    public SubjectService(SubjectDao dao, ParallelDao parallelDao, ExamDao examDao, 
            LectureDao lectureDao){
        this.dao = dao;
        this.parallelDao = parallelDao;
        this.examDao = examDao;
        this.lectureDao = lectureDao;
    }
    
    @Transactional
    public List<Subject> getAll(){
       return dao.findAll();
    }
    
    @Transactional
    public Subject getById(Integer id){
        return dao.find(id);
    }
    
    @Transactional
    public void addLecture(Subject subject, Lecture lecture){
        Objects.requireNonNull(subject);
        Objects.requireNonNull(lecture);
        lectureDao.persist(lecture);
        subject.addLecture(lecture);
        dao.update(subject);
    }
    
    @Transactional
    public void removeLecture(Subject subject, Lecture lecture){
        Objects.requireNonNull(subject);
        Objects.requireNonNull(lecture);
        subject.removeLecture(lecture);
        lectureDao.remove(lecture);
        dao.update(subject);
    }
    
    @Transactional
    public void addExam(Subject subject, Exam exam){
        Objects.requireNonNull(subject);
        Objects.requireNonNull(exam);
        examDao.persist(exam);
        subject.addExam(exam);
        dao.update(subject);
    }
    
    @Transactional
    public void removeExam(Subject subject, Exam exam){
        Objects.requireNonNull(subject);
        Objects.requireNonNull(exam);
        subject.removeExam(exam);
        examDao.remove(exam);
        dao.update(subject);
    }
    
    @Transactional
    public void addTeacher(Subject subject, Teacher teacher){
        Objects.requireNonNull(subject);
        Objects.requireNonNull(teacher);
        subject.addTeacher(teacher);
        dao.update(subject);
    }
    
    @Transactional
    public void removeTeacher(Subject subject, Teacher teacher){
        Objects.requireNonNull(subject);
        Objects.requireNonNull(teacher);
        subject.removeTeacher(teacher);
        dao.update(subject);
    }
    
    @Transactional
    public void addParallel(Subject subject, Parallel parallel){
        Objects.requireNonNull(subject);
        Objects.requireNonNull(parallel);
        parallelDao.persist(parallel);
        subject.addParallel(parallel);
        dao.update(subject);
    }
    
    @Transactional
    public void removeParallel(Subject subject, Parallel parallel){
        Objects.requireNonNull(subject);
        Objects.requireNonNull(parallel);
        subject.removeParallel(parallel);
        parallelDao.remove(parallel);
        dao.update(subject);
    }
    
    @Transactional
    public void persist(Subject subject){
        Objects.requireNonNull(subject);
        dao.persist(subject);
    }

}
