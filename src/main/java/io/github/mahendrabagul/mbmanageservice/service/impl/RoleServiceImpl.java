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

import io.github.mahendrabagul.mbmanageservice.objects.model.Role;
import io.github.mahendrabagul.mbmanageservice.objects.model.RoleName;
import io.github.mahendrabagul.mbmanageservice.repository.RoleRepository;
import io.github.mahendrabagul.mbmanageservice.service.RoleService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

  private RoleRepository roleRepository;

  @Autowired
  public RoleServiceImpl(
      RoleRepository roleRepository) {
    this.roleRepository = roleRepository;
  }

  @Override
  public void save(Role role) {
    roleRepository.save(role);
  }

  @Override
  @Cacheable("roles")
  public List<Role> findAll() {
    return roleRepository.findAll();
  }

  @Override
  public Optional<Role> findByName(RoleName roleName) {
    return roleRepository.findByName(roleName);
  }
}
