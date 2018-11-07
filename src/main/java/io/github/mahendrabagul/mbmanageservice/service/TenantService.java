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

import io.github.mahendrabagul.mbmanageservice.model.Tenant;
import java.util.List;
import java.util.Optional;

public interface TenantService {

  Tenant save(Tenant tenant);

  Optional<Tenant> findByTenantName(String tenantName);

  Optional<Tenant> findById(String id);

  List<Tenant> findAll();

  void deleteById(String tenantId);
}

