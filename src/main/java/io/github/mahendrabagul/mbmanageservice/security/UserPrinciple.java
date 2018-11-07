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

package io.github.mahendrabagul.mbmanageservice.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.mahendrabagul.mbmanageservice.model.User;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UserPrinciple implements UserDetails {

  private static final long serialVersionUID = 1L;

  private String id;

  @Getter
  private String name;

  private String username;

  @Getter
  private String tenantName;

  @Getter
  private String email;

  @JsonIgnore
  private String password;

  private Collection<? extends GrantedAuthority> authorities;

  public UserPrinciple(String id, String name,
      String username, String email, String password,
      Collection<? extends GrantedAuthority> authorities, String tenantName) {
    this.id = id;
    this.name = name;
    this.username = username;
    this.email = email;
    this.password = password;
    this.authorities = authorities;
    this.tenantName = tenantName;
  }

  public static UserPrinciple build(User user) {
    List<GrantedAuthority> authorities = user.getRoles().stream().map(role ->
        new SimpleGrantedAuthority(role.getName().name())
    ).collect(Collectors.toList());
    return new UserPrinciple(
        user.getUserId(),
        user.getFullName(),
        user.getUserName(),
        user.getEmail(),
        user.getPassword(),
        authorities,
        user.getTenant().getTenantName()
    );
  }


  @Override
  public String getUsername() {
    return username;
  }

  @Override
  public String getPassword() {
    return password;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return authorities;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    UserPrinciple user = (UserPrinciple) o;
    return Objects.equals(id, user.id);
  }
}