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

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestRestResource {

  @GetMapping("/api/test/clerk")
  @PreAuthorize("hasRole('CLERK') or hasRole('ADMIN')")
  public String userAccess() {
    return ">>> User Contents!";
  }

  @GetMapping("/api/test/senior")
  @PreAuthorize("hasRole('SENIOR') or hasRole('ADMIN')")
  public String projectManagementAccess() {
    return ">>> Board Management Project";
  }

  @GetMapping("/api/test/admin")
  @PreAuthorize("hasRole('ADMIN')")
  public String adminAccess() {
    return ">>> Admin Contents";
  }
}