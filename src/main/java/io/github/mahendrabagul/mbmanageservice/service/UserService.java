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

import io.github.mahendrabagul.mbmanageservice.objects.model.User;
import java.util.List;
import java.util.Optional;

public interface UserService {

  boolean existsByUserName(String userName);

  boolean existsByEmail(String email);

  void save(User user);

  List<User> findAll();

  Optional<User> findById(String userId);

  Optional<User> findByUserName(String userName);
}
