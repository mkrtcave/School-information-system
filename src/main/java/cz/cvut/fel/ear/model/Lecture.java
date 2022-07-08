/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.ear.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
        @NamedQuery(name = "Lecture.findBySubject", query = "SELECT l FROM Lecture l WHERE l.subject.id = :id")
})
public class Lecture extends AbstractEntity{
    
    @Basic(optional = false)
    @Column(nullable = false)
    private LocalDateTime time;
    
    @Basic(optional = false)
    @Column(nullable = false)
    private String place;
    
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer capacity;
    
    @ManyToMany
    private List<Teacher> teachers;
    
    @ManyToOne
    private Subject subject;
    
    public Lecture(){}
    
    public void addTeacher(Teacher teacher){
        if(teachers == null){
            teachers = new ArrayList<>();
        }
        teachers.add(teacher);
    }
    
    public void removeTeacher(Teacher teacher){
        if(teachers != null){
            teachers.remove(teacher);
        }
    }
    
    public void setTime(LocalDateTime time) {
        this.time = time;
    }
    
    public void setPlace(String place) {
        this.place = place;
    }
    
    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }
    
    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public String getPlace() {
        return place;
    }

    public Integer getCapacity() {
        return capacity;
    }
    
    public LocalDateTime getTime() {
        return time;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }
    
}
