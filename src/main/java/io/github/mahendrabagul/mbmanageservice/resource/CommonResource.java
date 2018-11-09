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

import io.github.mahendrabagul.mbmanageservice.objects.model.Role;
import io.github.mahendrabagul.mbmanageservice.objects.model.RoleName;
import io.github.mahendrabagul.mbmanageservice.objects.model.Student;
import io.github.mahendrabagul.mbmanageservice.objects.model.Tenant;
import io.github.mahendrabagul.mbmanageservice.objects.model.User;
import io.github.mahendrabagul.mbmanageservice.service.RoleService;
import io.github.mahendrabagul.mbmanageservice.service.StudentService;
import io.github.mahendrabagul.mbmanageservice.service.TenantService;
import io.github.mahendrabagul.mbmanageservice.service.UserService;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
@RequestMapping("/api/v1/commons")
public class CommonResource {

  private TenantService tenantService;
  private UserService userService;
  private StudentService studentService;
  private RoleService roleService;

  @Autowired
  public CommonResource(TenantService tenantService,
      UserService userService,
      StudentService studentService,
      RoleService roleService) {
    this.tenantService = tenantService;
    this.userService = userService;
    this.studentService = studentService;
    this.roleService = roleService;
  }

  @GetMapping("/getAllCommonData")
  public ResponseEntity<Map<String, Object>> getAllCommonData() {
    log.debug("REST request to get getAllCommonData");
    Map<String, Object> map = new HashMap<>();
    map.put("years", Arrays.asList("FIRST", "SECOND"));
    map.put("degrees", Arrays.asList("BTECH", "BE", "BCS", "MCS", "MTECH", "ME", "PHD"));
    List<Tenant> tenants = tenantService.findAll();
    List<String> tenantNames = new ArrayList<>();
    tenants.forEach(tenant -> {
      tenantNames.add(tenant.getTenantName());
    });
    map.put("tenantNames", tenantNames);
    return new ResponseEntity<>(map, HttpStatus.OK);
  }

  @RequestMapping("/populate")
  public String populate() {
    callME();
    return "SUCCESS";
  }

  private void callME() {
    //Tenant Creation
    Tenant sggs = new Tenant();
    sggs.setTenantName("Shri Guru Gobind Singhji Institute of Engineering and Technology");
    sggs.setCity("Nanded");
    sggs.setType("Engineering College");
    Tenant savedSggs = tenantService.save(sggs);

    Tenant pict = new Tenant();
    pict.setTenantName("Pune Institute of Computer Technology");
    pict.setCity("Pune");
    pict.setType("Engineering College");
    Tenant savedPict = tenantService.save(pict);

    //Roles Creation
    Role admin = roleService.findByName(RoleName.ROLE_ADMIN).get();
    Role clerk = roleService.findByName(RoleName.ROLE_CLERK).get();
    Role senior = roleService.findByName(RoleName.ROLE_SENIOR).get();

    //User Creation
    User pakale = new User();
    pakale.setFullName("Deepak Sonawane");
    pakale.setEmail("dsonawane@sggs.ac.in");
    pakale.setUserName("dsonawane");
    pakale.setPassword("sonawane@123");
    pakale.setTenant(savedSggs);
    pakale.setRoles(new HashSet<>(Arrays.asList(clerk, senior)));
    userService.save(pakale);

    User navedeti = new User();
    navedeti.setFullName("Suresh Pagar");
    navedeti.setEmail("spagar@sggs.ac.in");
    navedeti.setUserName("spagar");
    navedeti.setPassword("pagar@123");
    navedeti.setTenant(savedSggs);
    navedeti.setRoles(new HashSet<>(Arrays.asList(clerk)));
    userService.save(navedeti);

    User vaidya = new User();
    vaidya.setFullName("Suraj Tambe");
    vaidya.setEmail("stambe@pict.ac.in");
    vaidya.setUserName("stambe");
    vaidya.setPassword("tambe@123");
    vaidya.setTenant(savedPict);
    vaidya.setRoles(new HashSet<>(Arrays.asList(clerk, senior)));
    userService.save(vaidya);

    User shetty = new User();
    shetty.setFullName("Nitin Pawar");
    shetty.setEmail("npawar@pict.ac.in");
    shetty.setUserName("npawar");
    shetty.setPassword("pawar@123");
    shetty.setTenant(savedPict);
    shetty.setRoles(new HashSet<>(Arrays.asList(clerk)));
    userService.save(shetty);

    List<String> cities = Arrays
        .asList("Pune", "Nashik", "Mumbai", "Aurangabad", "Jalna", "Ahmed Nagar", "Latur", "Beed",
            "Parbhani", "Navi Mumbai", "Solapur", "Dhule");
    List<String> degrees = Arrays.asList("BTECH", "BE", "BCS", "MCS", "MTECH", "ME", "PHD");
    List<String> years = Arrays.asList("FIRST", "SECOND");

    // pict Student Creation
    for (int i = 0; i < 50; i++) {
      Student student = new Student();
      student.setTenant(savedPict);

      Collections.shuffle(cities);
      student.setCity(cities.get(0));

      Collections.shuffle(degrees);
      student.setDegree(degrees.get(0));

      Collections.shuffle(years);
      student.setAdmissionYear(years.get(0));

      student.setFullName("PICT Student's full Name " + (i + 1));
      if (i % 2 == 0) {
        student.setCreatedBy(vaidya);
        student.setModifiedBy(vaidya);
      } else {
        student.setCreatedBy(shetty);
        student.setModifiedBy(shetty);
      }
      studentService.save(student);
    }

    //Sggs Student Creation
    for (int i = 0; i < 50; i++) {
      Student student = new Student();
      student.setTenant(savedSggs);

      Collections.shuffle(cities);
      student.setCity(cities.get(0));

      Collections.shuffle(degrees);
      student.setDegree(degrees.get(0));

      Collections.shuffle(years);
      student.setAdmissionYear(years.get(0));

      student.setFullName("Sggs Student's full Name " + (i + 1));
      if (i % 2 == 0) {
        student.setCreatedBy(pakale);
        student.setModifiedBy(pakale);
      } else {
        student.setCreatedBy(navedeti);
        student.setModifiedBy(navedeti);
      }
      studentService.save(student);
    }
  }
}
