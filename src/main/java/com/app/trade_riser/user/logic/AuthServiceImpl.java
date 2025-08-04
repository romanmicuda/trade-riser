package com.app.trade_riser.user.logic;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.app.trade_riser.exception.IllegalOperationException;
import com.app.trade_riser.security.jwt.JwtUtils;
import com.app.trade_riser.security.services.UserDetailsImpl;
import com.app.trade_riser.user.data.ERole;
import com.app.trade_riser.user.data.Role;
import com.app.trade_riser.user.data.RoleRepository;
import com.app.trade_riser.user.data.User;
import com.app.trade_riser.user.data.UserRepository;
import com.app.trade_riser.user.web.VerifyTokenRequest;
import com.app.trade_riser.user.web.bodies.JwtResponse;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository,
            RoleRepository roleRepository, PasswordEncoder encoder, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public JwtResponse signin(String username, String password) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        return new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
    }

    @Override
    public User signup(String username, String email, String password, Set<String> role) throws IllegalOperationException {
    if (userRepository.existsByUsername(username)) {
        throw new IllegalOperationException("Error: Username is already taken!");
    }

    if (userRepository.existsByEmail(email)) {
      throw new IllegalOperationException("Error: Email is already in use!");
    }
    User user = new User(username, email, encoder.encode(password));

    Set<String> strRoles = role;
    Set<Role> roles = new HashSet<>();

    if (strRoles == null) {
      Role userRole = roleRepository.findByName(ERole.ROLE_USER)
          .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
      roles.add(userRole);
    } else {
      strRoles.forEach(item -> {
        switch (item) {
        case "admin":
          Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(adminRole);

          break;
        default:
          Role userRole = roleRepository.findByName(ERole.ROLE_USER)
              .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
          roles.add(userRole);
        }
      });
    }
    user.setRoles(roles);
    return userRepository.save(user);
  }

    @Override
    public boolean verifyToken(VerifyTokenRequest token) {
        try {
            if (token == null || token.getToken() == null || token.getToken().isEmpty()) {
                return false;
            }
            String jwt = token.getToken().startsWith("Bearer ") ? token.getToken().substring(7) : token.getToken();
            return jwtUtils.validateJwtToken(jwt);
        } catch (Exception e) {
            return false;
        }
    }
}
