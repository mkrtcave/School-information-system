package cz.cvut.fel.ear.rest;

import cz.cvut.fel.ear.exception.NotFoundException;
import cz.cvut.fel.ear.model.Program;
import cz.cvut.fel.ear.model.Semester;
import cz.cvut.fel.ear.model.Student;
import cz.cvut.fel.ear.model.User;
import cz.cvut.fel.ear.rest.util.RestUtils;
import cz.cvut.fel.ear.service.ProgramService;
import cz.cvut.fel.ear.service.SemesterService;
import cz.cvut.fel.ear.service.StudentService;
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
@RequestMapping("/programs")
public class ProgramController {
    
    private static final Logger LOG = LoggerFactory.getLogger(ProgramController.class);
    
    private final ProgramService programService;
    
    private final SemesterService semesterService;
    
    private final StudentService studentService;
    
    private final UserService userService;
    
    @Autowired 
    public ProgramController(ProgramService programService, 
            SemesterService semesterService, StudentService studentService,
            UserService userService){
        this.programService = programService;
        this.semesterService = semesterService;
        this.studentService = studentService;
        this.userService = userService;
    }
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Program> getPrograms(){
        return programService.findAll();
    }
    
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Program getById(@PathVariable Integer id){
        final Program program = programService.findById(id);
        if(program == null){
            throw NotFoundException.create("Program", id);
        }
        return program;
    }
    
    @GetMapping(value = "/{id}/semesters", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Semester> getSemestersFromProgram(@PathVariable Integer id){
        return semesterService.findAllSemestersFromProgram(getById(id));
    }
    
    @GetMapping(value = "/{id}/students", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Student> getStudentsFromProgram(@PathVariable Integer id){
        return studentService.findAllStudentsFromProgram(getById(id));
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createProgram(@RequestBody Program program){
        programService.persist(program);
        LOG.debug("Created program {}.", program);
        final HttpHeaders headers = RestUtils
                .createLocationHeaderFromCurrentUri("/{id}", program.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/{id}/semesters", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addSemester(@PathVariable Integer id, @RequestBody Semester semester){
        final Program program = getById(id);
        programService.addSemester(program, semester);
        LOG.debug("Semester {} added into program {}.", semester, program);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{programId}/semesters/{semesterId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeSemester(@PathVariable Integer programId, 
                               @PathVariable Integer semesterId){
        final Program program = getById(programId);
        final Semester semesterToRemove = semesterService.getById(semesterId);
        if(semesterToRemove == null){
            throw NotFoundException.create("Semester", semesterId);
        }
        programService.removeSemester(program, semesterToRemove);
        LOG.debug("Semester {} removed from program {}.", semesterToRemove, program);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/{id}/students", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addStudent(@PathVariable Integer id, @RequestBody HashMap<String, String> form){
        final Program program = getById(id);
        final User user = userService.getByUserName(form.get("userName"));
        if(user == null){
            throw NotFoundException.create("Student", form.get("userName"));
        }
        programService.addStudent(program, studentService.getById(user.getId()));
        LOG.debug("Student {} added into program {}.", studentService.getById(user.getId()), program);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{programId}/students/{studentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeStudent(@PathVariable Integer programId, 
                              @PathVariable Integer studentId){
        final Program program = getById(programId);
        final Student studentToRemove = studentService.getById(studentId);
        if(studentToRemove == null){
            throw NotFoundException.create("Student", studentId);
        }
        programService.removeStudent(program, studentToRemove);
        LOG.debug("Student {} removed from program {}.", studentToRemove, program);
    }
    
}
