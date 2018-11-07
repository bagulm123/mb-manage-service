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

import static io.github.mahendrabagul.mbmanageservice.util.Constants.AUTHORIZATION;
import static io.github.mahendrabagul.mbmanageservice.util.Constants.TOKEN_PREFIX;

import io.github.mahendrabagul.mbmanageservice.service.impl.UserDetailsServiceImpl;
import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;


@Slf4j
public class JwtAuthTokenFilter extends OncePerRequestFilter {

  private JwtProvider tokenProvider;

  private UserDetailsServiceImpl userDetailsService;


  @Autowired
  public JwtAuthTokenFilter(
      JwtProvider tokenProvider,
      UserDetailsServiceImpl userDetailsService) {
    this.tokenProvider = tokenProvider;
    this.userDetailsService = userDetailsService;
  }

  @Override
  protected void doFilterInternal(HttpServletRequest request,
      HttpServletResponse response,
      FilterChain filterChain)
      throws ServletException, IOException {
    try {

      String jwt = getJwt(request);
      if (jwt != null && tokenProvider.validateJwtToken(jwt)) {
        String username = tokenProvider.getUserNameFromJwtToken(jwt);

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        UsernamePasswordAuthenticationToken authentication
            = new UsernamePasswordAuthenticationToken(userDetails, null,
            userDetails.getAuthorities());
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authentication);
      }
    } catch (Exception e) {
      logger.error("Can NOT set user authentication -> Message: {}", e);
    }

    filterChain.doFilter(request, response);
  }

  private String getJwt(HttpServletRequest request) {
    String authHeader = request.getHeader(AUTHORIZATION);

    if (authHeader != null && authHeader.startsWith(TOKEN_PREFIX)) {
      return authHeader.replace(TOKEN_PREFIX, "");
    }

    return null;
  }
}