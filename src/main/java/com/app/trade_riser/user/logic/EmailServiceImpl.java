package com.app.trade_riser.user.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceImpl.class);
    private final JavaMailSender mailSender;

    @Value("${app.mail.from}")
    private String fromEmail;

    public EmailServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void sendPasswordResetEmail(String toEmail, String resetCode) {
        try {
            logger.info("Attempting to send password reset email to: {}", toEmail);
            
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            
            helper.setFrom(fromEmail);
            helper.setTo(toEmail);
            helper.setSubject("Trade Riser - Password Reset Code");
            
            String htmlContent = createPasswordResetEmailContent(resetCode);
            helper.setText(htmlContent, true);
            
            logger.info("Sending email from: {} to: {}", fromEmail, toEmail);
            mailSender.send(message);
            logger.info("Password reset email sent successfully to: {}", toEmail);
            
        } catch (MessagingException e) {
            logger.error("MessagingException while sending email to {}: {}", toEmail, e.getMessage(), e);
            throw new RuntimeException("Failed to send password reset email", e);
        } catch (Exception e) {
            logger.error("Unexpected error while sending email to {}: {}", toEmail, e.getMessage(), e);
            throw new RuntimeException("Failed to send password reset email", e);
        }
    }

    private String createPasswordResetEmailContent(String resetCode) {
        return """
            <!DOCTYPE html>
            <html>
            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Password Reset - Trade Riser</title>
                <style>
                    body { font-family: Arial, sans-serif; line-height: 1.6; color: #333; }
                    .container { max-width: 600px; margin: 0 auto; padding: 20px; }
                    .header { background-color: #2c3e50; color: white; padding: 20px; text-align: center; border-radius: 5px 5px 0 0; }
                    .content { background-color: #f8f9fa; padding: 30px; border-radius: 0 0 5px 5px; }
                    .reset-code { font-size: 32px; font-weight: bold; color: #e74c3c; text-align: center; 
                                  background-color: #fff; padding: 20px; border-radius: 5px; margin: 20px 0; 
                                  border: 2px dashed #e74c3c; letter-spacing: 8px; }
                    .warning { background-color: #fff3cd; border: 1px solid #ffeaa7; padding: 15px; border-radius: 5px; margin: 20px 0; }
                    .footer { text-align: center; margin-top: 30px; color: #7f8c8d; font-size: 14px; }
                </style>
            </head>
            <body>
                <div class="container">
                    <div class="header">
                        <h1>Trade Riser</h1>
                        <h2>Password Reset Request</h2>
                    </div>
                    <div class="content">
                        <p>Hello,</p>
                        <p>We received a request to reset your password for your Trade Riser account. To complete the password reset process, please use the following 6-digit verification code:</p>
                        
                        <div class="reset-code">%s</div>
                        
                        <div class="warning">
                            <strong>⚠️ Important Security Information:</strong>
                            <ul>
                                <li>This code will expire in 15 minutes</li>
                                <li>Only use this code if you requested a password reset</li>
                                <li>Never share this code with anyone</li>
                                <li>If you didn't request this reset, please ignore this email</li>
                            </ul>
                        </div>
                        
                        <p>To reset your password:</p>
                        <ol>
                            <li>Go to the password reset page in the Trade Riser app</li>
                            <li>Enter your email address</li>
                            <li>Enter the 6-digit code shown above</li>
                            <li>Create your new password</li>
                        </ol>
                        
                        <p>If you have any questions or concerns, please contact our support team.</p>
                        
                        <p>Best regards,<br>
                        The Trade Riser Team</p>
                    </div>
                    <div class="footer">
                        <p>This is an automated message. Please do not reply to this email.</p>
                        <p>&copy; 2024 Trade Riser. All rights reserved.</p>
                    </div>
                </div>
            </body>
            </html>
            """.formatted(resetCode);
    }
}
