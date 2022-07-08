package cz.cvut.fel.ear.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@NamedQueries({
        @NamedQuery(name = "Semester.findAllByProgram", query = "SELECT s FROM Semester s WHERE s.program.id = :id")
})
public class Semester extends AbstractEntity{
    
    @Basic(optional = false)
    @Column(nullable = false, unique = true)
    private String code;
    
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer year;
    
    @ManyToOne
    private Program program;
    
    @OneToMany(mappedBy="semester")
    private List<Student> students;

    @OneToMany
    @JoinTable(name = "semester_subject", 
        joinColumns =
            @JoinColumn(name = "semester_id"),
        inverseJoinColumns =
            @JoinColumn(name = "subject_id"))
    private List<Subject> subjects;
    
    @Enumerated(EnumType.STRING)
    private TeachingSemester teachingSemester;
    
    public Semester() {}
    
    public Semester(Program program){
        this.program = program;
    }
    
    public void addSubject(Subject subject){
        if(subjects == null){
            subjects = new ArrayList<>();
        }
        subjects.add(subject);
    }
    
    public void removeSubject(Subject subject){
        if(subject != null){
            subjects.remove(subject);
        }
    }
    
    public void addStudent(Student student){
        if(students == null){
            students = new ArrayList<>();
        }
        students.add(student);
    }
    
    public void removeStudent(Student student){
        students.remove(student);
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public void setProgram(Program program) {
        this.program = program;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public void setTeachingSemester(TeachingSemester teachingSemester) {
        this.teachingSemester = teachingSemester;
    }

    public String getCode() {
        return code;
    }

    public Integer getYear() {
        return year;
    }

    public Program getProgram() {
        return program;
    }

    public List<Student> getStudents() {
        return students;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public TeachingSemester getTeachingSemester() {
        return teachingSemester;
    }
    
}
