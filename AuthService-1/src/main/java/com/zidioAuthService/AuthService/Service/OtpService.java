package com.zidioAuthService.AuthService.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.stereotype.Service;

@Service
public class OtpService {

    private final Map<String, String> otpStorage = new HashMap<>();

    public void sendOtp(String email) {
        String otp = String.valueOf(new Random().nextInt(999999));
        otpStorage.put(email, otp);
        
        System.out.println("OTP for " + email + " is: " + otp);
    }

    public boolean validateOtp(String email, String otp) {
        return otpStorage.containsKey(email) && otpStorage.get(email).equals(otp);
    }
}