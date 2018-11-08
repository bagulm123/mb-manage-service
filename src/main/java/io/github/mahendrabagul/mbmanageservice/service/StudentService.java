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

package io.github.mahendrabagul.mbmanageservice.service;

import io.github.mahendrabagul.mbmanageservice.objects.model.Student;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {

  boolean existsByRollNumber(String rollNumber);

  Student save(Student student);

  List<Student> findAll();

  Optional<Student> findById(String studentId);

  Page<Student> findByTenant(Pageable pageable, String tenantId, String searchKeyWord);

  void deleteById(String studentId);
}
