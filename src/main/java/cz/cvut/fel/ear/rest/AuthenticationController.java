package cz.cvut.fel.ear.rest;

import cz.cvut.fel.ear.model.Student;
import cz.cvut.fel.ear.model.Teacher;
import cz.cvut.fel.ear.model.User;
import cz.cvut.fel.ear.rest.util.RestUtils;
import cz.cvut.fel.ear.service.LoginService;
import cz.cvut.fel.ear.service.StudentService;
import cz.cvut.fel.ear.service.TeacherService;
import cz.cvut.fel.ear.service.UserService;
import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
    
    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationController.class);
    
    private final UserService userService;
    
    private final LoginService loginService;
    
    private final TeacherService teacherService;
    
    private final StudentService studentService;
    
    @Autowired
    public AuthenticationController(UserService userService, 
            LoginService loginService, TeacherService teacherService,
            StudentService studentService) {
        this.userService = userService;
        this.loginService = loginService;
        this.teacherService = teacherService;
        this.studentService = studentService;
    }
    
    @PostMapping(value = "/login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> loginUser(@RequestBody HashMap<String, String> request){
        loginService.loginUser(request.get("userName"), request.get("password"));
        LOG.trace("User {} successfully logged in", request.get("userName"));
        return new ResponseEntity<>(null, HttpStatus.OK);
    }
    
    @PostMapping(value = "/register-student", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> registerStudent(@RequestBody Student student) {
        studentService.persist(student);
        LOG.debug("User {} successfully registered.", student);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }
    
    @PostMapping(value = "/register-teacher", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> registerTeacher(@RequestBody Teacher teacher) {
        teacherService.persist(teacher);
        LOG.debug("User {} successfully registered.", teacher);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }

}
