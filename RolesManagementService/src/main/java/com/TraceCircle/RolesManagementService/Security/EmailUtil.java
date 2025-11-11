package com.TraceCircle.RolesManagementService.Security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class EmailUtil {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    public void sendEmail(String to, String subject, String text) {
       
    	try 
    	{
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setFrom(from);
            msg.setTo(to);
            msg.setSubject(subject);
            msg.setText(text);

            mailSender.send(msg);
            
            log.info("Email successfully sent to {}", to);

        } 
    	catch (Exception e) 
    	{
            log.error("Failed to send email to {}: {}", to, e.getMessage());
        }
    }

    public void sendOtpEmail(String to, String otp) {
        
    	sendEmail(to, "Password Reset OTP", 
            "Your OTP is: " + otp + "\n\nValid for 5 minutes.\n- TraceCircle Team");
    }

    public void sendPasswordResetConfirmation(String to) {
       
    	sendEmail(to, "Password Updated Successfully",
            "Your password has been changed.\nIf not you"
            + ", contact support.\n- TraceCircle Team");
    }
}
