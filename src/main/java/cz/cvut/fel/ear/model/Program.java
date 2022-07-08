package cz.cvut.fel.ear.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "Program.findById", query = "SELECT p FROM Program p WHERE p.id = :id")
})
public class Program extends AbstractEntity{
    
    @Basic(optional = false)
    @Column(nullable = false)
    private String name;
    
    @OneToMany(mappedBy = "program")
    private List<Student> students;
    
    @OneToMany(cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "program_id")
    private List<Semester> semesters;

    public Program() {}
    
    public void addSemester(Semester semester){
        if(semesters == null){
            this.semesters = new ArrayList<>();
        }
        semesters.add(semester);
    }
   
    public void removeSemester(Semester semester){
        semesters.remove(semester);
    }
    
    public void addStudent(Student student){
        if(students == null){
            this.students = new ArrayList<>();
        }
        students.add(student);
    }
    
    public void removeStudent(Student student){
        semesters.remove(student);
    }
    
    public void setStudents(List<Student> students) {
        this.students = students;
    }
    
    public void setSemesters(List<Semester> semesters) {
        this.semesters = semesters;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public List<Student> getStudents() {
        return students;
    }

    public List<Semester> getSemesters() {
        return semesters;
    }
    
}
