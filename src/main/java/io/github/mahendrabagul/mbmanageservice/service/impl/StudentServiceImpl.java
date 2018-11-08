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

package io.github.mahendrabagul.mbmanageservice.service.impl;

import io.github.mahendrabagul.mbmanageservice.objects.model.Student;
import io.github.mahendrabagul.mbmanageservice.repository.StudentRepository;
import io.github.mahendrabagul.mbmanageservice.service.StudentService;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl implements StudentService {

  private StudentRepository studentRepository;

  @Autowired
  public StudentServiceImpl(
      StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  @Override
  public boolean existsByRollNumber(String rollNumber) {
    return studentRepository.existsByRollNumber(rollNumber);
  }

  private String generateRollNumber(
      Student student) {
    String rollNumber = null;
    boolean exists = true;
    do {
      rollNumber = generateUniqueRollNumber(student);
      exists = existsByRollNumber(rollNumber);
    } while (exists);

    return rollNumber;
  }

  private String generateUniqueRollNumber(Student student) {
    Calendar now = Calendar.getInstance();
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(now.get(Calendar.YEAR)).append(student.getDegree().toUpperCase())
        .append(randomNumber());
    return stringBuffer.toString();
  }

  private int randomNumber() {
    Random rand = new Random();
    return rand.nextInt(100) + 1;
  }

  @Override
  public Student save(Student student) {
    student.setRollNumber(generateRollNumber(student));
    return studentRepository.save(student);
  }

  @Override
  public List<Student> findAll() {
    return studentRepository.findAll();
  }

  @Override
  public Optional<Student> findById(String studentId) {
    return studentRepository.findById(studentId);
  }

  @Override
  public Page<Student> findAll(Pageable pageable) {
    return studentRepository.findAll(pageable);
  }

  @Override
  public void deleteById(String studentId) {
    studentRepository.deleteById(studentId);
  }
}