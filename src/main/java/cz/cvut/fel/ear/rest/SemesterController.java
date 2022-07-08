/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.ear.rest;

import cz.cvut.fel.ear.exception.NotFoundException;
import cz.cvut.fel.ear.model.Program;
import cz.cvut.fel.ear.model.Semester;
import cz.cvut.fel.ear.model.Student;
import cz.cvut.fel.ear.model.Subject;
import cz.cvut.fel.ear.model.User;
import cz.cvut.fel.ear.rest.util.RestUtils;
import cz.cvut.fel.ear.service.SemesterService;
import cz.cvut.fel.ear.service.StudentService;
import cz.cvut.fel.ear.service.SubjectService;
import cz.cvut.fel.ear.service.UserService;
import java.util.HashMap;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/semesters")
public class SemesterController {
    
    private static final Logger LOG = LoggerFactory.getLogger(SemesterController.class);

    private final SemesterService semesterService;
    
    private final SubjectService subjectService;
    
    private final StudentService studentService;
    
    private final UserService userService;
    
    @Autowired
    public SemesterController(SemesterService semesterService,
            SubjectService subjectService, StudentService studentService,
            UserService userService){
        this.semesterService = semesterService;
        this.subjectService = subjectService;
        this.studentService = studentService;
        this.userService = userService;
    }
    
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Semester getById(@PathVariable Integer id){
        final Semester semester = semesterService.getById(id);
        if(semester == null){
            throw NotFoundException.create("Semester", semester);
        }
        return semester;
    }
    
    @GetMapping(value = "{id}/students", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Student> getStudentFromSemestr(@PathVariable Integer id){
        return studentService.findAllStudentsFromSemester(getById(id));
    } 
    
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @PostMapping(value = "/{id}/subjects", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addSubject(@PathVariable Integer id, @RequestBody Subject subject){
        final Semester semester = getById(id);
        semesterService.addSubject(semester, subject);
        LOG.debug("Subject {} added into semester {}.", subject, semester);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @DeleteMapping(value = "/{semesterId}/subjects/{subjectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeSubject(@PathVariable Integer semesterId, @PathVariable Integer subjectId){
        final Semester semester = getById(semesterId);
        final Subject subject = subjectService.getById(subjectId);
        if(subject == null){
            throw NotFoundException.create("Subject", subjectId);
        }
        semesterService.removeSubject(semester, subject);
        LOG.debug("Subject {} removed from semester {}.", subject, semester);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @PostMapping(value = "/{id}/students", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addStudent(@PathVariable Integer id, @RequestBody HashMap<String, String> form){
        final Semester semester = getById(id);
        final User user = userService.getByUserName(form.get("userName"));
        if(user == null){
            throw NotFoundException.create("Student", form.get("userName"));
        }
        semesterService.addStudent(semester, studentService.getById(user.getId()));
        LOG.debug("Student {} added into semester {}.", studentService.getById(user.getId()), semester);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @DeleteMapping(value = "/{semesterId}/students/{studentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeStudent(@PathVariable Integer semesterId, @PathVariable Integer studentId){
        final Semester semester = getById(semesterId);
        final Student student = studentService.getById(studentId);
        if(student == null){
            throw NotFoundException.create("Subject", studentId);
        }
        semesterService.removeStudent(semester, student);
        LOG.debug("Student {} removed from semester {}.", student, semester);
    }
}
