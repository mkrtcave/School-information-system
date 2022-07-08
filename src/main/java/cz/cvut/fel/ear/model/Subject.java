package cz.cvut.fel.ear.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
public class Subject extends AbstractEntity{
    
    @Basic(optional = false)
    @Column(nullable = false, unique = true)
    private String code;
    
    @Basic(optional = false)
    @Column(nullable = false)
    private Integer credits;
    
    @Basic(optional = false)
    @Column(nullable = false)
    private String title;
    
    @Basic(optional = false)
    @Column(nullable = false)
    private String info;
    
    @Enumerated(EnumType.STRING)
    private TeachingSemester teachingSemester;
    
    @Basic(optional = false)
    @ManyToMany(mappedBy = "subjects")
    private List<Teacher> teachers;

    @ManyToOne
    private Schedule schedule;

    @ManyToMany
    private List<Student> students;
    
    @OneToMany(cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "subject_id")
    private List<Exam> exams; 
    
    @OneToMany(cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "subject_id")
    private List<Parallel> parallels;
    
    @OneToOne(cascade = CascadeType.MERGE, orphanRemoval = true)
    @JoinColumn(name = "subject_id")
    private List<Lecture> lectures;

    public Subject() {}

    public Subject(Teacher teacher){
        addTeacher(teacher);
    }
    
    public void addTeacher(Teacher teacher){
        if(teachers == null){
            teachers = new ArrayList<>();
        }
        teachers.add(teacher);
    }
    
    public void addParallel(Parallel parallel){
        if(parallels == null){
            parallels = new ArrayList<>();
        }
        parallels.add(parallel);
    }
    
    public void removeParallel(Parallel parallel){
        parallels.remove(parallel);
    }   
    
    public void removeTeacher(Teacher teacher){
        teachers.remove(teacher);
    }
    
    public void addExam(Exam exam){
        if(exams == null){
            exams = new ArrayList<>();
        }
        exams.add(exam);
    }
    
    public void removeExam(Exam exam){
        exams.remove(exam);
    }
    
    public void setCode(String code) {
        this.code = code;
    }

    public void setNumberOfCredits(int credits) {
        this.credits = credits;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setTeachingSemester(TeachingSemester teachingSemester) {
        this.teachingSemester = teachingSemester;
    }

    public void setTeachers(List<Teacher> teachers) {
        this.teachers = teachers;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }
    
    public String getCode() {
        return code;
    }

    public Integer getCredits() {
        return credits;
    }

    public String getTitle() {
        return title;
    }

    public String getInfo() {
        return info;
    }

    public TeachingSemester getTeachingSemester() {
        return teachingSemester;
    }

    public List<Teacher> getTeachers() {
        return teachers;
    }

    public Schedule getSchedule() {
        return schedule;
    }
    
    public List<Student> getStudents() {
        return students;
    }

    public void setStudents(List<Student> students) {
        this.students = students;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }
    
    public void addLecture(Lecture lecture) {
        lectures.add(lecture);
    }

    public void removeLecture(Lecture lecture) {
        lectures.remove(lecture);
    }
    
    public List<Parallel> getParallels() {
        return parallels;
    }

    public void setParalels(List<Parallel> paralels) {
        this.parallels = paralels;
    }

    @Override
    public String toString() {
        return "Subject{" + "code=" + code + ", numberOfCredits=" 
                + credits + ", title=" + title 
                + ", info=" + info + ", teachingSemester=" + teachingSemester 
                + ", teachers=" + teachers + '}';
    }
    
}
