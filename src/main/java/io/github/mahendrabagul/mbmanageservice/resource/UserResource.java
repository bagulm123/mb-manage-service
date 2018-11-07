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

import io.github.mahendrabagul.mbmanageservice.exception.ResourceNotFoundException;
import io.github.mahendrabagul.mbmanageservice.model.User;
import io.github.mahendrabagul.mbmanageservice.service.UserService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserResource {

  private UserService userService;

  @Autowired
  public UserResource(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/{userId}")
  public User findUserById(@PathVariable String userId) throws ResourceNotFoundException {
    Optional<User> userOptional = userService.findById(userId);
    if (!userOptional.isPresent()) {
      throw new ResourceNotFoundException("userId-" + userId);
    }
    return userOptional.get();
  }

  @GetMapping("/")
  public List<User> findUsers() {
    return userService.findAll();
  }

}
