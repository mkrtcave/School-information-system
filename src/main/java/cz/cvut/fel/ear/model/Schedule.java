package cz.cvut.fel.ear.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "Schedule.findByOwner", query = "SELECT s FROM Schedule s WHERE s.owner.id = :id")
})
public class Schedule extends AbstractEntity{
    
    @OneToOne(mappedBy = "schedule", optional = false)
    private Student owner;

    @OneToMany
    private List<Parallel> parallels;
    
    @OneToMany
    private List<Lecture> lectures;
    
    @OneToMany
    private List<Exam> exams;
    
    HashMap<Subject, Grade> grades = new HashMap<Subject, Grade>();
            
    public Schedule() {}
    
    public Schedule(Student student){
        this.owner = student;
    }
    
    public void addGrade(Subject subject, Grade grade){
        grades.put(subject, grade);
    }
    
    public void addParallel(Parallel parallel){
        if(parallels == null){
            parallels = new ArrayList<>();
        }
        
        if(!parallels.contains(parallel)){
            parallels.add(parallel);
        }
    }
    
    public void removeParallel(Parallel parallel){
        parallels.remove(parallel);
    }
    
    public void addExam(Exam exam){
        if(exams == null){
            exams = new ArrayList<>();
        }
        exams.add(exam);
    }
    
    public void removeExam(Exam exam){
        if(exams != null){
            exams.remove(exam);
        }
    }
    
    public void addLecture(Lecture lecture){
        if(lectures == null){
            lectures = new ArrayList<>();
        }
        lectures.add(lecture);
    }
    
    public void removeLecture(Lecture lecture){
        if(lectures != null){
            lectures.remove(lecture);
        }
    }
    
    public Student getStudent() {
        return owner;
    }

    public void setOwner(Student student) {
        this.owner = student;
    }

    public List<Parallel> getParallels() {
        return parallels;
    }

    public void setParalels(List<Parallel> parallels) {
        this.parallels = parallels;
    }

    public Student getOwner() {
        return owner;
    }

    public List<Lecture> getLectures() {
        return lectures;
    }

    public List<Exam> getExams() {
        return exams;
    }
    
}
