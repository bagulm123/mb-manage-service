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
import io.github.mahendrabagul.mbmanageservice.objects.model.Tenant;
import io.github.mahendrabagul.mbmanageservice.service.TenantService;
import java.net.URI;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/api/v1/tenants")
public class TenantResource {

  private TenantService tenantService;

  @Autowired
  public TenantResource(TenantService tenantService) {
    this.tenantService = tenantService;
  }

  @GetMapping("/{tenantId}")
  public Tenant findTenantById(@PathVariable String tenantId) throws ResourceNotFoundException {
    Optional<Tenant> tenantOptional = tenantService.findById(tenantId);
    if (!tenantOptional.isPresent()) {
      throw new ResourceNotFoundException("tenantId-" + tenantId);
    }
    return tenantOptional.get();
  }

  @GetMapping("/")
  public List<Tenant> findTenants() {
    return tenantService.findAll();
  }

  @DeleteMapping("/{tenantId}")
//  @PreAuthorize("hasRole('ADMIN')")
  public void deleteTenant(@PathVariable String tenantId) {
    tenantService.deleteById(tenantId);
  }

  @PutMapping("/{tenantId}")
//  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Object> updateTenant(@RequestBody Tenant tenant,
      @PathVariable String tenantId) {
    Optional<Tenant> tenantOptional = tenantService.findById(tenantId);
    if (!tenantOptional.isPresent()) {
      return ResponseEntity.notFound().build();
    }
    tenant.setTenantId(tenantId);
    tenantService.save(tenant);
    return ResponseEntity.noContent().build();
  }

  @PostMapping("/")
//  @PreAuthorize("hasRole('ADMIN')")
  public ResponseEntity<Object> createTenant(@RequestBody Tenant tenant) {
    Tenant savedTenant = tenantService.save(tenant);
    URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{tenantId}")
        .buildAndExpand(savedTenant.getTenantId()).toUri();
    return ResponseEntity.created(location).build();
  }
}
