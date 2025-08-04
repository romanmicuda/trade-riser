package com.app.trade_riser.user.web.bodies;

import java.util.List;
import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JwtResponse {
  private String token;
  private String type = "Bearer";
  private UUID id;
  private String username;
  private String email;
  private List<String> roles;

  public JwtResponse(String accessToken, UUID id, String username, String email, List<String> roles) {
    this.token = accessToken;
    this.id = id;
    this.username = username;
    this.email = email;
    this.roles = roles;
  }
}
