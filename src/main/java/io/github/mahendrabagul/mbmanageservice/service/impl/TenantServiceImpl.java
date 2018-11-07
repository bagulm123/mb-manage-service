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

import io.github.mahendrabagul.mbmanageservice.objects.model.Tenant;
import io.github.mahendrabagul.mbmanageservice.repository.TenantRepository;
import io.github.mahendrabagul.mbmanageservice.service.TenantService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TenantServiceImpl implements TenantService {

  private TenantRepository tenantRepository;

  @Autowired
  public TenantServiceImpl(
      TenantRepository tenantRepository) {
    this.tenantRepository = tenantRepository;
  }

  @Override
  public Tenant save(Tenant tenant) {
    return tenantRepository.save(tenant);
  }

  @Override
  public Optional<Tenant> findByTenantName(String tenantName) {
    return tenantRepository.findByTenantName(tenantName);
  }

  @Override
  public Optional<Tenant> findById(String tenantId) {
    return tenantRepository.findById(tenantId);
  }

  @Override
  public List<Tenant> findAll() {
    return tenantRepository.findAll();
  }

  @Override
  public void deleteById(String tenantId) {
    tenantRepository.deleteById(tenantId);
  }

}
