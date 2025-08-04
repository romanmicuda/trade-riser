package com.app.trade_riser.user.web;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.trade_riser.exception.IllegalOperationException;
import com.app.trade_riser.user.data.User;
import com.app.trade_riser.user.logic.AuthService;
import com.app.trade_riser.user.web.bodies.JwtResponse;
import com.app.trade_riser.user.web.bodies.LoginRequest;
import com.app.trade_riser.user.web.bodies.RegisterResponse;
import com.app.trade_riser.user.web.bodies.SignupRequest;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
  private final AuthService authService;

  @Autowired
  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/signin")
  public ResponseEntity<JwtResponse> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
  JwtResponse jwtResponse = authService.signin(loginRequest.getUsername(), loginRequest.getPassword());
  return ResponseEntity.ok(jwtResponse);
  }

  @PostMapping("/signup")
  public ResponseEntity<RegisterResponse> registerUser(@Valid @RequestBody SignupRequest signUpRequest) throws IllegalOperationException {
   User user = authService.signup(signUpRequest.getUsername(), signUpRequest.getEmail(), signUpRequest.getPassword(), signUpRequest.getRole());
   return ResponseEntity.ok(new RegisterResponse(user));
  }

  @PostMapping("/verify")
  public ResponseEntity<Void> verifyToken(@RequestBody VerifyTokenRequest token) {
    boolean isValid = authService.verifyToken(token);
    if (isValid) {
      return ResponseEntity.ok().build();
    } else {
      return ResponseEntity.status(401).build();
    }
  }
}
