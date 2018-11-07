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

import io.github.mahendrabagul.mbmanageservice.model.Role;
import io.github.mahendrabagul.mbmanageservice.model.RoleName;
import java.util.List;
import java.util.Optional;

public interface RoleService {

  void save(Role role);

  List<Role> findAll();

  Optional<Role> findByName(RoleName roleName);
}
