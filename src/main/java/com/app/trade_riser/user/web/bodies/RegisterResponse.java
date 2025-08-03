package com.app.trade_riser.user.web.bodies;

import java.util.Set;

import com.app.trade_riser.user.data.Role;
import com.app.trade_riser.user.data.User;

import lombok.Data;

@Data
public class RegisterResponse {

  private String username;
  private String email;
  private Set<Role> roles;

  public RegisterResponse(User user){
    this.username = user.getUsername();
    this.email = user.getEmail();
    this.roles = user.getRoles();
  }
}
