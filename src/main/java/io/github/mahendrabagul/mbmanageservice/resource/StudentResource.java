/*
 * ******************************************************
 *  * Copyright (C) 2018-2019 Mahendra Bagul <bagulm123@gmail.com>
 *  *
 *  * This file is part of MB Manage Service.
 *  *
 *  * MB Manage Service can not be copied and/or distributed without the express
 *  * permission of Mahendra Bagul
 *  ******************************************************
 */

package io.github.mahendrabagul.mbmanageservice.resource;

import io.github.mahendrabagul.mbmanageservice.exception.ResourceNotFoundException;
import io.github.mahendrabagul.mbmanageservice.objects.model.Student;
import io.github.mahendrabagul.mbmanageservice.service.StudentService;
import io.github.mahendrabagul.mbmanageservice.util.PaginationUtil;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/students")
@Slf4j
public class StudentResource {

  public static final String URI = "/students";
  private StudentService studentService;

  @Autowired
  public StudentResource(StudentService studentService) {
    this.studentService = studentService;
  }

  @GetMapping("/{studentId}")
  public Student findStudentById(@PathVariable String studentId) throws ResourceNotFoundException {
    Optional<Student> studentOptional = studentService.findById(studentId);
    if (!studentOptional.isPresent()) {
      throw new ResourceNotFoundException("studentId-" + studentId);
    }
    return studentOptional.get();
  }

  @GetMapping("/list")
  public List<Student> findStudents() {
    return studentService.findAll();
  }

  @GetMapping
  public ResponseEntity<List<Student>> getAllStudents(Pageable pageable,
      @RequestParam String tenantId, @RequestParam String searchKeyWord) {
    log.debug(
        "REST request to get a page of Students : {} by tenantId : {} and optional searchString : {}",
        pageable, tenantId, searchKeyWord);
    Page<Student> page = studentService.findByTenant(pageable, tenantId, searchKeyWord);
    HttpHeaders headers = PaginationUtil
        .generatePaginationHttpHeaders(page, String.format(URI));
    return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
  }


  @DeleteMapping("/{studentId}")
  public void deleteStudent(@PathVariable String studentId) {
    studentService.deleteById(studentId);
  }

  @PutMapping("/{studentId}")
  public ResponseEntity<Object> updateStudent(@RequestBody Student student,
      @PathVariable String studentId) {
    Optional<Student> studentOptional = studentService.findById(studentId);
    if (!studentOptional.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    student.setStudentId(studentId);
    studentService.save(student);
    return ResponseEntity.noContent().build();
  }

  @PostMapping
  public ResponseEntity<Student> createStudent(@RequestBody Student student)
      throws URISyntaxException {
    Student savedStudent = studentService.save(student);
    return ResponseEntity.created(new URI(URI + "/" + savedStudent.getStudentId()))
        .body(savedStudent);
  }
}
