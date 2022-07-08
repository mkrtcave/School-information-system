    package cz.cvut.fel.ear.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity(name = "Student")
public class Student extends User {

    @Basic(optional = false)
    @Column(nullable = false)
    private Integer year;
    
    @OneToOne
    @JoinColumn(name = "shedule_id")
    private Schedule schedule;
    
    @ManyToOne
    @JoinColumn(name = "program_id")
    private Program program;

    @OneToOne
    @JoinColumn(name = "semester_id")
    private Semester semester;

    @ManyToMany(mappedBy = "students")
    private List<Subject> subjects;
    
    @ManyToMany
    private List<Exam> exams;

    public Student(){
        setRole(Role.STUDENT);
    }
    
    public Student(Program program){    
        this.program = program;
        this.semester = program.getSemesters().get(0);
        this.subjects = semester.getSubjects();
    }
    
    public void addParalelToSchedule(Parallel parallel){
        if(schedule == null){
            schedule = new Schedule(this);
        }
        schedule.addParallel(parallel);
    }
    
    public void removeParallelFromSchedule(Parallel parallel){
        if(schedule != null){
            schedule.removeParallel(parallel);
        }
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
    
    public List<Exam> getExamsOfSubject(Subject subject){
        return subject.getExams();
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public void setProgram(Program program) {
        this.program = program;
        this.semester = program.getSemesters().get(0);
    }

    public void setSemester(Semester semester) {
        this.semester = semester;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public Program getProgram() {
        return program;
    }

    public Semester getSemester() {
        return semester;
    }

    @Override
    public String toString() {
        return "Student{firstname = " + getFirstName() +", secondname =" 
                + getSecondName() +  ", username =" + getUserName() 
                + ", program=" + program + ", semester=" + semester +'}';
    }
}