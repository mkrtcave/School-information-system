package cz.cvut.fel.ear.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({
        @NamedQuery(name = "Parallel.findBySubject", query = "SELECT p FROM Parallel p WHERE p.subject.id = :id")
})
public class Parallel extends AbstractEntity{
    
    @Basic(optional = false)
    @Column(nullable = false)
    private LocalDateTime time;
    
    @Basic(optional = false)
    @Column(nullable = false)
    private String place;
    
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer capacity;
    
    @Basic(optional = false)
    @Column(nullable = false)
    private String codeOfParallel;
    
    @ManyToOne
    private Subject subject;
    
    @ManyToMany
    @JoinTable(name="parallel_student",
        joinColumns=
            @JoinColumn(name="parallel_id"),
        inverseJoinColumns=
            @JoinColumn(name="student_id"))
    private List<Student> students;
    
    public Parallel(){}
    
    public void addStudent(Student student){
        if(students == null){
            students = new ArrayList<>();
        }
        students.add(student);
    }
    
    public void removeStudent(Student student){
        students.remove(student);
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }
    
    public Integer getCurrentNumberOfStudent(){
        return students.size();
    }

    public Integer getCapacity() {
        return capacity;
    }

    public void setCapacity(Integer capacity) {
        this.capacity = capacity;
    }

    public String getCodeOfParallel() {
        return codeOfParallel;
    }

    public void setCodeOfParallel(String codeOfParallel) {
        this.codeOfParallel = codeOfParallel;
    }

    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }
}
