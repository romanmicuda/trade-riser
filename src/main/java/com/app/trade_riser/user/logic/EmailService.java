package com.app.trade_riser.user.logic;

public interface EmailService {
    void sendPasswordResetEmail(String toEmail, String resetCode);
}
