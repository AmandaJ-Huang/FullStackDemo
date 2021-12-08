package controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import com.zipcode.FullStackDemo.entity.Student;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import repository.StudentRepo;

@RestController
@RequestMapping("/students")
public class StudentController {
    
    private final Logger log = LoggerFactory.getLogger(StudentController.class);
    private final StudentRepo studentRepo;

    public StudentController(StudentRepo studentRepo) {
        this.studentRepo = studentRepo;
    }

    @GetMapping
    public List<Student> getStudents() {
        return studentRepo.findAll();
    }

    @GetMapping("/(id)")
    public Student getStudent(@PathVariable Long id) {
        return studentRepo.findById(id).orElseThrow(RuntimeException::new);
    }

    @PostMapping
    public ResponseEntity<Student> createStudent(@RequestBody Student student) throws URISyntaxException {
        log.info("Request to create student");
        Student savedStudent = studentRepo.save(student);
        return ResponseEntity.created(new URI("/students" 
                    + savedStudent.getId())).body(savedStudent);
    }

}