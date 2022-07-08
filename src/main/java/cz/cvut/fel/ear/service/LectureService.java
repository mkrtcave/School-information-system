package cz.cvut.fel.ear.service;

import cz.cvut.fel.ear.dao.LectureDao;
import cz.cvut.fel.ear.model.Lecture;
import cz.cvut.fel.ear.model.Subject;
import cz.cvut.fel.ear.model.Teacher;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LectureService {
    
    private LectureDao dao;
    
    @Autowired
    public LectureService(LectureDao dao){
        this.dao = dao;
    }
    
    @Transactional
    public Lecture getById(Integer id){
        return dao.find(id);
    }
    
    @Transactional
    public List<Lecture> findAllLecturesBySubject(Subject subject){
        Objects.requireNonNull(subject);
        return dao.findAll(subject);
    }
    
    @Transactional
    public void persist(Lecture lecture){
        Objects.requireNonNull(lecture);
        dao.persist(lecture);
    }
    
    public void addTeacher(Lecture lecture, Teacher teacher){
        Objects.requireNonNull(lecture);
        Objects.requireNonNull(teacher);
        lecture.addTeacher(teacher);
        dao.persist(lecture);
    }
    
    public void removeTeacher(Lecture lecture, Teacher teacher){
        Objects.requireNonNull(lecture);
        Objects.requireNonNull(teacher);
        lecture.removeTeacher(teacher);
        dao.persist(lecture);
    }
}
