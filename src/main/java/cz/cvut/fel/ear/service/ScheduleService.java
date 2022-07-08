package cz.cvut.fel.ear.service;

import cz.cvut.fel.ear.dao.ParallelDao;
import cz.cvut.fel.ear.dao.ScheduleDao;
import cz.cvut.fel.ear.exception.OvercrowdedException;
import cz.cvut.fel.ear.model.Exam;
import cz.cvut.fel.ear.model.Grade;
import cz.cvut.fel.ear.model.Lecture;
import cz.cvut.fel.ear.model.Parallel;
import cz.cvut.fel.ear.model.Schedule;
import cz.cvut.fel.ear.model.Student;
import cz.cvut.fel.ear.model.Subject;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ScheduleService {
    
    private ScheduleDao dao;
    
    @Autowired
    public ScheduleService(ScheduleDao dao){
        this.dao = dao;
    }
    
    @Transactional
    public Schedule getById(Integer id){
        return dao.find(id);
    }
    
    @Transactional
    public Schedule getByOwner(Student student){
        return dao.findByOwner(student);
    }
    
    @Transactional
    public void addLecture(Schedule schedule, Lecture lecture){
        Objects.requireNonNull(schedule);
        Objects.requireNonNull(lecture);
        schedule.addLecture(lecture);
        dao.update(schedule);
    }
    
    @Transactional
    public void removeLecture(Schedule schedule, Lecture lecture){
        Objects.requireNonNull(schedule);
        Objects.requireNonNull(lecture);
        schedule.removeLecture(lecture);
        dao.update(schedule);
    }
    
    @Transactional
    public void addParallel(Schedule schedule, Parallel parallel){
        Objects.requireNonNull(schedule);
        Objects.requireNonNull(parallel);
        if(parallel.getCurrentNumberOfStudent() >= parallel.getCapacity()){
            throw OvercrowdedException.create(parallel);
        }
        schedule.addParallel(parallel);
        dao.update(schedule);
    }
    
    @Transactional
    public void removeParallel(Schedule schedule, Parallel parallel){
        Objects.requireNonNull(schedule);
        Objects.requireNonNull(parallel);
        schedule.removeParallel(parallel);
        dao.update(schedule);
    }
    
    @Transactional
    public void addExam(Schedule schedule, Exam exam){
        Objects.requireNonNull(schedule);
        Objects.requireNonNull(exam);
        if(exam.getCurrentNumberOfStudent() >= exam.getCapacity()){
            throw OvercrowdedException.create(exam);
        }
        schedule.addExam(exam);
        dao.update(schedule);
    }
    
    @Transactional
    public void removeExam(Schedule schedule, Exam exam){
        Objects.requireNonNull(schedule);
        Objects.requireNonNull(exam);
        schedule.removeExam(exam);
        dao.update(schedule);
    }
    
    @Transactional
    public void addGrade(Schedule schedule, Subject subject, Grade grade){
        Objects.requireNonNull(schedule);
        Objects.requireNonNull(subject);
        Objects.requireNonNull(grade);
        schedule.addGrade(subject, grade);
        dao.update(schedule);
    }
    
    @Transactional
    public void persist(Schedule schedule){
        Objects.requireNonNull(schedule);
        dao.persist(schedule);
    }
}
