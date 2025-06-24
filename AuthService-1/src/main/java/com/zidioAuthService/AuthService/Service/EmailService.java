package com.zidioAuthService.AuthService.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.*;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender javaMailSender;

    public void sendOtpEmail(String to, String otp) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();

        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setTo(to);
        helper.setSubject("ZIDIOConnect - OTP Verification");
        helper.setText("<h2>Your OTP is: " + otp + "</h2><p>Please enter this to verify your email.</p>", true);

        javaMailSender.send(message);
    }
}
