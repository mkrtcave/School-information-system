package cz.cvut.fel.ear.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity(name = "Teacher")   
public class Teacher extends User{
    
    @ManyToMany
    private List<Subject> subjects;

    public Teacher() {
        setRole(Role.TEACHER);
    }
    
    public void addSubject(Subject subject){
        if(subjects == null){
            subjects = new ArrayList<>();
        }
        subjects.add(subject);
    }
    
    public void removeSubject(Subject subject){
        subjects.remove(subject);
    }


    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }
    
}
