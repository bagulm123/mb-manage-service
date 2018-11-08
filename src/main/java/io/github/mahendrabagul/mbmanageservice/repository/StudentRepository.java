
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

package io.github.mahendrabagul.mbmanageservice.repository;

import io.github.mahendrabagul.mbmanageservice.objects.model.Student;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, String> {

  Optional<Student> findByRollNumber(String rollNumber);

  Boolean existsByRollNumber(String rollNumber);

  Page<Student> findByTenant_TenantId(Pageable pageable, String tenantId);

  @Query(value = "select distinct student from Student student where student.tenant.tenantId=:tenantId and lower(student.fullName) like %:fullName%",
      countQuery = "select count(distinct student) from Student student  where student.tenant.tenantId=:tenantId and lower(student.fullName) like %:fullName%")
  Page<Student> findByTenantIdAndFullName(Pageable pageable, @Param("tenantId") String tenantId,
      @Param("fullName") String fullName);

}