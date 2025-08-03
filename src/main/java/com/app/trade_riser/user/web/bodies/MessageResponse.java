package com.app.trade_riser.user.web.bodies;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MessageResponse {
  private String message;

  public MessageResponse(String message) {
    this.message = message;
  }
}
