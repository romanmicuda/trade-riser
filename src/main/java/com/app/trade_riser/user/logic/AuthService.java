package com.app.trade_riser.user.logic;

import java.util.Set;

import org.springframework.stereotype.Service;

import com.app.trade_riser.exception.IllegalOperationException;
import com.app.trade_riser.user.data.User;
import com.app.trade_riser.user.web.ResetPasswordRequest;
import com.app.trade_riser.user.web.VerifyTokenRequest;
import com.app.trade_riser.user.web.bodies.JwtResponse;

public interface AuthService {
    JwtResponse signin(String username, String password);
    User signup(String username, String email, String password, Set<String> strRoles) throws IllegalOperationException ;
    boolean verifyToken(VerifyTokenRequest token);
    void resetPassword(ResetPasswordRequest request) throws IllegalOperationException;
    boolean verifyResetCode(String email, String code);
    void updatePassword(String email, String code, String newPassword) throws IllegalOperationException;
}

