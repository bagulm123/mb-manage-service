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

import io.github.mahendrabagul.mbmanageservice.model.Student;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StudentService {

  boolean existsByRoleNumber(String roleNumber);

  Student save(Student student);

  List<Student> findAll();

  Optional<Student> findById(String studentId);

  Page<Student> findAll(Pageable pageable);

  void deleteById(String studentId);
}
