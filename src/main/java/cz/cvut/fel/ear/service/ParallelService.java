package cz.cvut.fel.ear.service;

import cz.cvut.fel.ear.dao.ParallelDao;
import cz.cvut.fel.ear.model.Parallel;
import cz.cvut.fel.ear.model.Student;
import cz.cvut.fel.ear.model.Subject;
import java.util.List;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParallelService {
    
    private ParallelDao dao;
    
    @Autowired
    public ParallelService(ParallelDao dao){
        this.dao = dao;
    }
    
    @Transactional
    public Parallel getById(Integer id){
        return dao.find(id);
    } 
    
    @Transactional
    public List<Parallel> findAllParallelsBySubject(Subject subject){
        Objects.requireNonNull(subject);
        return dao.findAll(subject);
    }
    
    @Transactional
    public List<Parallel> findAllFreeParallelsBySubject(Subject subject){
        Objects.requireNonNull(subject);
        return dao.findFreeParallels(subject);
    }
    
    @Transactional
    public void addStudent(Parallel parallel, Student student){
        Objects.requireNonNull(parallel);
        Objects.requireNonNull(student);
        parallel.addStudent(student);
        dao.update(parallel);
    }
    
    @Transactional
    public void removeStudent(Parallel parallel, Student student){
        Objects.requireNonNull(parallel);
        Objects.requireNonNull(student);
        parallel.removeStudent(student);
        dao.update(parallel);
    }
    
    @Transactional
    public void persist(Parallel parallel){
        Objects.requireNonNull(parallel);
        dao.persist(parallel);
    }
}
