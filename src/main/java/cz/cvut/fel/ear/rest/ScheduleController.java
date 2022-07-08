package cz.cvut.fel.ear.rest;

import cz.cvut.fel.ear.exception.NotFoundException;
import cz.cvut.fel.ear.model.Exam;
import cz.cvut.fel.ear.model.Lecture;
import cz.cvut.fel.ear.model.Parallel;
import cz.cvut.fel.ear.model.Schedule;
import cz.cvut.fel.ear.model.Student;
import cz.cvut.fel.ear.rest.util.RestUtils;
import cz.cvut.fel.ear.service.ExamService;
import cz.cvut.fel.ear.service.LectureService;
import cz.cvut.fel.ear.service.ParallelService;
import cz.cvut.fel.ear.service.ScheduleService;
import cz.cvut.fel.ear.service.StudentService;
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
@RequestMapping("/schedules")
public class ScheduleController {
    
    private static final Logger LOG = LoggerFactory.getLogger(ProgramController.class);
    
    private final ScheduleService scheduleService;
    
    private final StudentService studentService;
    
    private final LectureService lectureService;
    
    private final ExamService examService;
    
    private final ParallelService parallelService;
    
    @Autowired
    public ScheduleController(ScheduleService scheduleService,
            StudentService studentService, LectureService lectureService,
            ExamService examService, ParallelService parallelService){
        this.scheduleService = scheduleService;
        this.studentService = studentService;
        this.lectureService = lectureService;
        this.examService = examService;
        this.parallelService = parallelService;
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    @PostMapping(value = "/{studentId}/schedule", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createSchedule(@PathVariable Integer studentId) {
        Schedule schedule = new Schedule(); 
        schedule.setOwner(studentService.getById(studentId));
        scheduleService.persist(schedule);
        LOG.debug("Created schedule {}.", schedule);
        final HttpHeaders headers = RestUtils.createLocationHeaderFromCurrentUri("/{id}", schedule.getId());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
    
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Schedule getById(@PathVariable Integer id) {
        final Schedule schedule = scheduleService.getById(id);
        if (schedule == null) {
            throw NotFoundException.create("Schedule", id);
        }
        return schedule;
    }
    
    @GetMapping(value = "/{id}/exams", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Exam> getExamsBySchedule(@PathVariable Integer id){
        final Schedule schedule = getById(id);
        return schedule.getExams();
    }
    
    @GetMapping(value = "/{id}/parallels", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Parallel> getParallelsBySchedule(@PathVariable Integer id){
        final Schedule schedule = getById(id);
        return schedule.getParallels();
    }
    
    @GetMapping(value = "/{id}/lectures", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Lecture> getLecturesBySchedule(@PathVariable Integer id){
        final Schedule schedule = getById(id);
        return schedule.getLectures();
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    @PostMapping(value = "/{id}/lectures", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addLecture(@PathVariable Integer id, @RequestBody Lecture lecture){
        final Schedule schedule = getById(id);
        scheduleService.addLecture(schedule, lecture);
        LOG.debug("Lecture {} added into schedule {}.", lecture, schedule);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    @PostMapping(value = "/{id}/exams", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addExam(@PathVariable Integer id, @RequestBody Exam exam){
        final Schedule schedule = getById(id);
        scheduleService.addExam(schedule, exam);
        LOG.debug("Exam {} added into schedule {}.", exam, schedule);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    @PostMapping(value = "/{id}/parallels", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addParallel(@PathVariable Integer id, @RequestBody Parallel parallel){
        final Schedule schedule = getById(id);
        scheduleService.addParallel(schedule, parallel);
        LOG.debug("Parallel {} added into schedule {}.", parallel, schedule);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    @DeleteMapping(value = "/{scheduleId}/lectures/{lectureId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeLecture(@PathVariable Integer scheduleId, @PathVariable Integer lectureId){
        final Schedule schedule = getById(scheduleId);
        final Lecture lecture = lectureService.getById(lectureId);
        if(lecture == null){
            throw NotFoundException.create("Lecture", lectureId);
        }
        scheduleService.removeLecture(schedule, lecture);
        LOG.debug("Lecture {} removed from schedule {}.", lecture, schedule);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    @DeleteMapping(value = "/{scheduleId}/exams/{examId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeExam(@PathVariable Integer scheduleId, @PathVariable Integer examId){
        final Schedule schedule = getById(scheduleId);
        final Exam exam = examService.getById(examId);
        if(exam == null){
            throw NotFoundException.create("Exam", examId);
        }
        scheduleService.removeExam(schedule, exam);
        LOG.debug("Exam {} removed from schedule {}.", exam, schedule);
    }
    
    @PreAuthorize("hasAnyRole('ADMIN', 'STUDENT')")
    @DeleteMapping(value = "/{scheduleId}/parallels/{parallelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeParallel(@PathVariable Integer scheduleId, @PathVariable Integer parallelId){
        final Schedule schedule = getById(scheduleId);
        final Parallel parallel = parallelService.getById(parallelId);
        if(parallel == null){
            throw NotFoundException.create("Parallel", parallelId);
        }
        scheduleService.removeParallel(schedule, parallel);
        LOG.debug("Parallel {} removed from schedule {}.", parallel, schedule);
    }
    
}
