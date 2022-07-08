package cz.cvut.fel.ear.rest;

import cz.cvut.fel.ear.exception.NotFoundException;
import cz.cvut.fel.ear.model.Exam;
import cz.cvut.fel.ear.model.Grade;
import cz.cvut.fel.ear.model.Lecture;
import cz.cvut.fel.ear.model.Parallel;
import cz.cvut.fel.ear.model.Schedule;
import cz.cvut.fel.ear.model.Semester;
import cz.cvut.fel.ear.model.Subject;
import cz.cvut.fel.ear.model.Teacher;
import cz.cvut.fel.ear.rest.util.RestUtils;
import cz.cvut.fel.ear.service.ExamService;
import cz.cvut.fel.ear.service.LectureService;
import cz.cvut.fel.ear.service.ParallelService;
import cz.cvut.fel.ear.service.ScheduleService;
import cz.cvut.fel.ear.service.StudentService;
import cz.cvut.fel.ear.service.SubjectService;
import cz.cvut.fel.ear.service.TeacherService;
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
@RequestMapping("/subjects")
public class SubjectController {
    
    private static final Logger LOG = LoggerFactory.getLogger(SubjectController.class);
    
    private final SubjectService subjectService;
    
    private final TeacherService teacherService;
    
    private final ParallelService parallelService;
    
    private final LectureService lectureService;
    
    private final ExamService examService;
    
    private final ScheduleService scheduleService;
    
    private final StudentService studentService;
    
    @Autowired
    public SubjectController(SubjectService subjectService, 
                TeacherService teacherService, ParallelService parallelService,
                LectureService lectureService, ExamService examService,
                ScheduleService scheduleService, StudentService studentService){
        this.subjectService = subjectService;
        this.teacherService = teacherService;
        this.parallelService = parallelService;
        this.lectureService = lectureService;
        this.examService = examService;
        this.scheduleService = scheduleService;
        this.studentService = studentService;
    }
    
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Subject> getSubjects(){
        return subjectService.getAll();
    }
    
    @GetMapping(value = "/{id}/parallels", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Parallel> getParallelsBySubject(@PathVariable Integer id){
        return parallelService.findAllParallelsBySubject(getById(id));
    }
    
    @GetMapping(value = "/{id}/available-parallels", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Parallel> getAvailableParallelsBySubject(@PathVariable Integer id){
        return parallelService.findAllFreeParallelsBySubject(getById(id));
    }
    
    @GetMapping(value = "/{id}/exams", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Exam> getExamsBySubject(@PathVariable Integer id){
        return examService.findAllExamsBySubject(getById(id));
    }
    
    @GetMapping(value = "/{id}/available-exams", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Exam> getAvailableExamsBySubject(@PathVariable Integer id){
        return examService.findAllAvailableExamsBySubject(getById(id));
    }
    
    @GetMapping(value = "/{id}/lectures", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Lecture> getLecturesBySubject(@PathVariable Integer id){
        return lectureService.findAllLecturesBySubject(getById(id));
    }
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createSubject(@RequestBody Subject subject){
        subjectService.persist(subject);
        LOG.debug("Created subject {}.", subject);
        final HttpHeaders headers = RestUtils.
                createLocationHeaderFromCurrentUri("/{id}", subject.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
    
    @GetMapping(value= "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Subject getById(@PathVariable Integer id){
        final Subject subject = subjectService.getById(id);
        if(subject == null){
            throw NotFoundException.create("Subject", id);
        }
        return subject;
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping(value = "/{id}/teachers", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addTeacher(@PathVariable Integer id, @RequestBody Teacher teacher){
        final Subject subject = getById(id);
        subjectService.addTeacher(subject, teacher);
        LOG.debug("Teacher {} added into subject {}.", teacher, subject);
    }
    
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "/{subjectId}/teachers/{teacherId}", 
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeTeacherFromSubject(@PathVariable Integer subjectId,
                                         @RequestBody Integer teacherId) {
        final Subject subject = getById(subjectId);
        final Teacher teacherToRemove = teacherService.getById(teacherId);
        if(teacherToRemove == null){
            throw NotFoundException.create("Teacher", teacherId);
        }
        subjectService.removeTeacher(subject, teacherToRemove);
        LOG.debug("Teacher {} removed from subject {}.", teacherToRemove, subject);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @PostMapping(value = "/{id}/parallels", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addParallel(@PathVariable Integer id, @RequestBody Parallel parallel){
        final Subject subject = getById(id);
        subjectService.addParallel(subject, parallel);
        LOG.debug("Parallel {} added into subject {}.", parallel, subject);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @DeleteMapping(value = "/{subjectId}/parallels/{parallelId}", 
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeParallel(@PathVariable Integer subjectId, 
            @PathVariable Integer parallelId){
        final Subject subject = getById(subjectId);
        final Parallel parallel = parallelService.getById(parallelId);
        if(parallel == null){
            throw NotFoundException.create("Parallel", parallelId);
        }
        subjectService.removeParallel(subject, parallel);
        LOG.debug("Parallel {} removed from subject {}.", parallel, subject);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @PostMapping(value = "/{id}/lecture", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void addLecture(@PathVariable Integer id, @RequestBody Lecture lecture){
        final Subject subject = getById(id);
        subjectService.addLecture(subject, lecture);
        LOG.debug("Lecture {} added into subject {}.", lecture, subject);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @DeleteMapping(value = "/{subjectId}/lectures/{lectureId}", 
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeLecture(@PathVariable Integer subjectId,
                              @PathVariable Integer lectureId){
        final Subject subject = getById(subjectId);
        final Lecture lecture = lectureService.getById(subjectId);
        if(lectureService == null){
            throw NotFoundException.create("Lecture", lectureId);
        }
        subjectService.removeLecture(subject, lecture);
        LOG.debug("Lecture {} removed from subject {}.", lecture, subject);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @PostMapping(value = "/{id}/exams", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addExam(@PathVariable Integer id, @RequestBody Exam exam){
        final Subject subject = getById(id);
        subjectService.addExam(subject, exam);
        LOG.debug("Exam {} added into subject {}.", exam, subject);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @DeleteMapping(value = "/{subjectId}/exams/{examId}", 
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeExam(@PathVariable Integer subjectId, @PathVariable Integer examId){
        final Subject subject = getById(subjectId);
        final Exam exam = examService.getById(examId);
        if(exam == null){
           throw NotFoundException.create("Exam", examId);
        }
        subjectService.removeExam(subject, exam);
        LOG.debug("Exam {} removed from subject {}.", exam, subject);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'TEACHER')")
    @PostMapping(value = "/{subjectId}/students/{studentId}", 
            consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addGradeOfSubject(@PathVariable Integer subjectId,
                                  @PathVariable Integer studentId,
                                  @PathVariable Grade grade){
        final Subject subject = getById(subjectId);
        final Schedule schedule = scheduleService.getByOwner(studentService.getById(studentId));
        if(schedule == null){
           throw NotFoundException.create("Student", studentId);
        }
        scheduleService.addGrade(schedule, subject, grade);
        LOG.debug("Grade{} added to subject {}.", grade, subject);
    }

}
