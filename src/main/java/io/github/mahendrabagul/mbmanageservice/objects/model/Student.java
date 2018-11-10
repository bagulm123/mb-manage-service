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

package io.github.mahendrabagul.mbmanageservice.objects.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "students")
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Student extends AuditModel {

  @Id
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  @Column(name = "student_id")
  private String studentId;

  @Column(name = "roll_number", unique = true)
  private String rollNumber;

  @Column(name = "degree")
  private String degree;

  @Column(name = "admission_year")
  private String admissionYear;

  @Column(name = "city")
  private String city;

  @Column(name = "fullName")
  private String fullName;

  @OneToOne
  @JoinColumn(name = "created_by")
  private User createdBy;

  @OneToOne
  @JoinColumn(name = "updated_by")
  private User updatedBy;

  @OneToOne
  @JoinColumn(name = "tenant_id")
  private Tenant tenant;
}