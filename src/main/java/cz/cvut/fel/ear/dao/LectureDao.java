/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.ear.dao;

import cz.cvut.fel.ear.model.Lecture;
import cz.cvut.fel.ear.model.Subject;
import java.util.List;
import java.util.Objects;
import org.springframework.stereotype.Repository;

@Repository
public class LectureDao extends BaseDao<Lecture>{
    
    public LectureDao() {
        super(Lecture.class);
    }
    
    public List<Lecture> findAll(Subject subject){
        Objects.requireNonNull(subject);
        return em.createNamedQuery("Lecture.findBySubject", Lecture.class)
                .setParameter("id", subject.getId()).getResultList();
    }
    
}
