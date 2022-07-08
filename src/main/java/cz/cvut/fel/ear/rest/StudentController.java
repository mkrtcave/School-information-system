package cz.cvut.fel.ear.rest;

import cz.cvut.fel.ear.exception.NotFoundException;
import cz.cvut.fel.ear.model.Exam;
import cz.cvut.fel.ear.model.Schedule;
import cz.cvut.fel.ear.model.Student;
import cz.cvut.fel.ear.model.Subject;
import cz.cvut.fel.ear.model.User;
import cz.cvut.fel.ear.rest.util.RestUtils;
import cz.cvut.fel.ear.service.ScheduleService;
import cz.cvut.fel.ear.service.StudentService;
import cz.cvut.fel.ear.service.UserService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.security.RolesAllowed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student")
public class StudentController {
    
    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);
    
    private final StudentService studentService;
    
    private final ScheduleService scheduleService;
    
    private final UserService userService;
    
    @Autowired
    public StudentController(StudentService studentService, 
            ScheduleService scheduleService, UserService userService){
        this.studentService = studentService;
        this.scheduleService = scheduleService;
        this.userService = userService;
    }
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Student> getStudents(){
        return studentService.findAll();
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STUDENT', 'ROLE_TEACHER')")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Student getById(@PathVariable Integer id) {
        final Student student = studentService.getById(id);
        if (student == null) {
            throw NotFoundException.create("Student", id);
        }
        return student;
    }
    
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_STUDENT')")
    @PostMapping(value = "/{id}/create-schedule", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Schedule> createSchedule(@PathVariable Integer id, @RequestBody Schedule schedule) {
        schedule.setOwner(getById(id));
        scheduleService.persist(schedule);
        LOG.debug("Created schedule {}.", schedule);
        return new ResponseEntity<>(schedule, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}/schedule", produces = MediaType.APPLICATION_JSON_VALUE)
    public Schedule getScheduleByOwner(@PathVariable Integer id){
        return scheduleService.getByOwner(getById(id));
    }
    
    @GetMapping(value = "/{id}/exams", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Exam> getExamsByOwner(@PathVariable Integer id){
        final Student student =  getById(id);
        return student.getSchedule().getExams();
    }

}
