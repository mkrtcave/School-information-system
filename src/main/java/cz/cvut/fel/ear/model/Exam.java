package cz.cvut.fel.ear.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
        @NamedQuery(name = "Exam.findBySubject", query = "SELECT e FROM Exam e WHERE e.subject.id = :id")
})
public class Exam extends AbstractEntity{
    
    @Basic(optional = false)
    @Column(nullable = false)
    private LocalDateTime date;
    
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer capacity;
    
    @Basic(optional = false)
    @Column(nullable = false)
    private String place;
    
    @Basic(optional = false)
    @Column(nullable = false)
    private String notes;
    
    @ManyToOne(optional = false)
    private Teacher examiner;
    
    @ManyToOne
    private Subject subject;
    
    public Exam(){}

    @ManyToMany
    private List<Student> examinees;

    public LocalDateTime getDate() {
        return date;
    }
    
    public void addExaminee(Student student){
        if(examinees == null){
            examinees = new ArrayList<>();
        }
        examinees.add(student);
    }
    
    public void removeExaminee(Student student){
        if(examinees != null){
            examinees.remove(student);
        }
    }

    public void setDate(LocalDateTime dateOfExam) {
        this.date = dateOfExam;
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public Teacher getExaminer() {
        return examiner;
    }

    public void setExaminer(Teacher examiner) {
        this.examiner = examiner;
    }

    public List<Student> getExaminees() {
        return examinees;
    }
    
    public Integer getCurrentNumberOfStudent(){
        return examinees.size();
    }

    public void setExaminees(List<Student> examinees) {
        this.examinees = examinees;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Exam{ date= "
                + date + ", capacity=" + capacity 
                + ", place=" + place + ", examiner=" + examiner 
                + ", notes=" + notes + '}';
    }

}
