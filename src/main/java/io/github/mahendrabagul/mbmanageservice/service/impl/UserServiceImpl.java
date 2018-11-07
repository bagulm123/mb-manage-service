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

import io.github.mahendrabagul.mbmanageservice.model.User;
import io.github.mahendrabagul.mbmanageservice.repository.UserRepository;
import io.github.mahendrabagul.mbmanageservice.service.UserService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

  private UserRepository userRepository;

  @Autowired
  public UserServiceImpl(
      UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public boolean existsByUserName(String userName) {
    return userRepository.existsByUserName(userName);
  }

  @Override
  public boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @Override
  public void save(User user) {
    userRepository.save(user);
  }

  @Override
  public List<User> findAll() {
    return userRepository.findAll();
  }

  @Override
  public Optional<User> findById(String userId) {
    return userRepository.findById(userId);
  }
}
