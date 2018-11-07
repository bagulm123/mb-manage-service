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
import io.github.mahendrabagul.mbmanageservice.objects.model.Tenant;
import io.github.mahendrabagul.mbmanageservice.objects.model.User;
import io.github.mahendrabagul.mbmanageservice.objects.request.LoginForm;
import io.github.mahendrabagul.mbmanageservice.objects.request.SignUpForm;
import io.github.mahendrabagul.mbmanageservice.objects.response.AuthToken;
import io.github.mahendrabagul.mbmanageservice.security.JwtProvider;
import io.github.mahendrabagul.mbmanageservice.service.RoleService;
import io.github.mahendrabagul.mbmanageservice.service.TenantService;
import io.github.mahendrabagul.mbmanageservice.service.UserService;
import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationResource {

  private AuthenticationManager authenticationManager;

  private RoleService roleService;

  private PasswordEncoder encoder;

  private JwtProvider jwtProvider;
  private UserService userService;
  private TenantService tenantService;

  @Autowired
  public AuthenticationResource(
      AuthenticationManager authenticationManager,
      RoleService roleService,
      PasswordEncoder encoder,
      JwtProvider jwtProvider,
      UserService userService,
      TenantService tenantService) {
    this.authenticationManager = authenticationManager;
    this.roleService = roleService;
    this.encoder = encoder;
    this.jwtProvider = jwtProvider;
    this.userService = userService;
    this.tenantService = tenantService;
  }

  @PostMapping("/signin")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginForm loginRequest) {

    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(
            loginRequest.getUserName(),
            loginRequest.getPassword()
        )
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    return ResponseEntity.ok(new AuthToken(jwtProvider.generateJwtToken(authentication)));
  }

  @PostMapping("/signup")
  public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpForm signUpRequest) {
    if (userService.existsByUserName(signUpRequest.getUserName())) {
      return new ResponseEntity<>("Fail -> Username is already taken!",
          HttpStatus.BAD_REQUEST);
    }

    if (userService.existsByEmail(signUpRequest.getEmail())) {
      return new ResponseEntity<>("Fail -> Email is already in use!",
          HttpStatus.BAD_REQUEST);
    }

    // Creating user's account
    User user = new User(signUpRequest.getName(), signUpRequest.getUserName(),
        signUpRequest.getEmail(), encoder.encode(signUpRequest.getPassword()));

    String tenantName = signUpRequest.getTenantName();
    Tenant byTenantName = tenantService.findByTenantName(tenantName)
        .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
    user.setTenant(byTenantName);

    Set<String> strRoles = signUpRequest.getRole();
    Set<Role> roles = new HashSet<>();

    strRoles.forEach(role -> {
      switch (role) {
        case "admin":
          Role adminRole = roleService.findByName(RoleName.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
          roles.add(adminRole);

          break;
        case "senior":
          Role pmRole = roleService.findByName(RoleName.ROLE_SENIOR)
              .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
          roles.add(pmRole);

          break;
        default:
          Role userRole = roleService.findByName(RoleName.ROLE_CLERK)
              .orElseThrow(() -> new RuntimeException("Fail! -> Cause: User Role not find."));
          roles.add(userRole);
      }
    });

    user.setRoles(roles);
    userService.save(user);

    return ResponseEntity.ok().body("User registered successfully!");
  }

  /**
   * No Need to use it now
   */
  @PostMapping(value = "/confirmToken")
  public ResponseEntity<?> register(@RequestBody AuthToken token)
      throws AuthenticationException {

    return ResponseEntity.ok(!jwtProvider.validateJwtToken(token.getToken()));
  }
}