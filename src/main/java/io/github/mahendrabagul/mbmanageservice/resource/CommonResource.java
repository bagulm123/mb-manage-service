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

import io.github.mahendrabagul.mbmanageservice.objects.model.Tenant;
import io.github.mahendrabagul.mbmanageservice.service.TenantService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@Slf4j
@RequestMapping("/api/v1/commons")
public class CommonResource {

  private TenantService tenantService;

  @Autowired
  public CommonResource(TenantService tenantService) {
    this.tenantService = tenantService;
  }

  @GetMapping("/allCommonData")
  public ResponseEntity<Map<String, Object>> getAllCommonData() {
    log.debug("REST request to get getAllCommonData");
    List<String> degrees = new ArrayList<>();
    degrees.add("B.TECH");
    degrees.add("B.E.");
    degrees.add("BCS");
    degrees.add("MCS");
    degrees.add("M.TECH");
    degrees.add("M.E.");
    degrees.add("PHD");
    List<String> years = new ArrayList<>();
    years.add("First");
    years.add("Second");
    Map<String, Object> map = new HashMap<>();
    map.put("years", years);
    map.put("degrees", degrees);
    List<Tenant> tenants = tenantService.findAll();
    List<String> tenantNames = new ArrayList<>();
    tenants.forEach(tenant -> {
      tenantNames.add(tenant.getTenantName());
    });
    map.put("tenantNames", tenantNames);
    return new ResponseEntity<>(map, HttpStatus.OK);
  }

}
