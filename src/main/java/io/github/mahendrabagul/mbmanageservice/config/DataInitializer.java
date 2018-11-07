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

package io.github.mahendrabagul.mbmanageservice.config;

import static io.github.mahendrabagul.mbmanageservice.objects.model.RoleName.ROLE_ADMIN;
import static io.github.mahendrabagul.mbmanageservice.objects.model.RoleName.ROLE_CLERK;
import static io.github.mahendrabagul.mbmanageservice.objects.model.RoleName.ROLE_SENIOR;

import io.github.mahendrabagul.mbmanageservice.objects.model.Role;
import io.github.mahendrabagul.mbmanageservice.objects.model.RoleName;
import io.github.mahendrabagul.mbmanageservice.service.RoleService;
import java.util.Arrays;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Slf4j
@ConditionalOnProperty(name = "app.db-init", havingValue = "true")
public class DataInitializer implements CommandLineRunner {

  private RoleService roleService;

  @Autowired
  public DataInitializer(RoleService roleService) {
    this.roleService = roleService;
  }

  @Override
  @Transactional
  public void run(String... strings) {

    List<Role> roles = roleService.findAll();

    if (roles.isEmpty()) {
      List<RoleName> roleNames = Arrays.asList(ROLE_ADMIN, ROLE_SENIOR, ROLE_CLERK);
      roleNames.forEach(roleName -> {
        Role role = new Role();
        role.setName(roleName);
        roleService.save(role);
      });
    } else {
      log.info("roles are already populated. No need to populate again.");
    }
  }

}
