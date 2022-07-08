package cz.cvut.fel.ear.dao.enviroment;

import cz.cvut.fel.ear.model.Exam;
import cz.cvut.fel.ear.model.Parallel;
import cz.cvut.fel.ear.model.Program;
import cz.cvut.fel.ear.model.Semester;
import cz.cvut.fel.ear.model.Student;
import cz.cvut.fel.ear.model.Subject;
import cz.cvut.fel.ear.model.Teacher;
import cz.cvut.fel.ear.model.TeachingSemester;
import cz.cvut.fel.ear.model.User;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Random;



public class Generator {
    
    private static final Random RAND = new Random();
    
    public static int randomInt(){
        return RAND.nextInt();
    }
    
    public static boolean randomBoolean(){
        return RAND.nextBoolean();
    }
    
    public static Student generateStudent(){
        final Student student = new Student();
        student.setFirstName("FirstName" + randomInt());
        student.setLastName("LastName" + randomInt());
        student.setUserName("UserName" + randomInt());
        student.setPassword("12345");
        student.setYear(randomInt());
        student.setProgram(generateProgram());  
        student.setSemester(generateSemester());
        return student;
    }
    
    public static Program generateProgram(){
        final Program program = new Program();
        program.setName("SIT " + randomInt());
        return program;
    }
    
    public static Semester generateSemester(){
        final Semester semester = new Semester();
        semester.setCode("B1R" + randomInt());
        semester.setYear(1);
        semester.setTeachingSemester(TeachingSemester.WINTER);
        return semester;
    }
    
    public static Teacher generateTeacher(){
        final Teacher teacher = new Teacher();
        teacher.setFirstName("FirstName" + randomInt());
        teacher.setLastName("LastName" + randomInt());
        teacher.setUserName("UserName" + randomInt());
        teacher.setPassword("12345");
        return teacher;
    }
        
    
    public static Subject generateSubject(){
        final Subject subject = new Subject();
//        final Teacher teacher = generateTeacher();
        subject.setCode("B3D5G" + randomInt());
        subject.setTitle("EAR" + randomInt());
        subject.setTeachingSemester(TeachingSemester.WINTER);
        subject.setInfo("test");
        subject.setNumberOfCredits(10);
        return subject;
    }
    
    public static Parallel generateParallel(){
        Parallel par = new Parallel();
        par.setPlace("A" + randomInt());
        par.setCapacity(20);
        par.setCodeOfParallel("EAR " + randomInt());
        par.setTime(LocalDateTime.MIN);
        return par;
    }
    
    public static Exam generateExam(){
        Exam exam = new Exam();
        exam.setCapacity(20);
        exam.setNotes("EAR Exam" + randomInt());
        exam.setPlace("B" + randomInt());
        LocalDateTime date = LocalDateTime.of(2025, Month.MARCH, 5, 14, 22);
        exam.setDate(date);
        return exam;
    }
    
    public static Student generateUser(){
        final Student user = new Student();
        user.setFirstName("FirstName" + randomInt());
        user.setLastName("LastName" + randomInt());
        user.setUserName("UserName" + randomInt());
        user.setPassword("12345");
        user.setYear(randomInt());
        return user;
    }
    
    
      
}
