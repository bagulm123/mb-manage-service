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

package io.github.mahendrabagul.mbmanageservice.objects.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.Data;

@Data
public class LoginForm {

  @NotBlank
  @Size(min = 3, max = 60)
  private String userName;

  @NotBlank
  @Size(min = 3, max = 60)
  private String tenantName;

  @NotBlank
  @Size(min = 6, max = 40)
  private String password;

}