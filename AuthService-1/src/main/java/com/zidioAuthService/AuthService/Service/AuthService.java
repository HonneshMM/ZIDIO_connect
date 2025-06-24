package com.zidioAuthService.AuthService.Service;
import org.springframework.stereotype.Service;

import com.zidioAuthService.AuthService.Entities.Role;
import com.zidioAuthService.AuthService.Entities.User;
import com.zidioAuthService.AuthService.Exception.CustomException;
import com.zidioAuthService.AuthService.Repository.UserRepository;
import com.zidioAuthService.AuthService.Util.JwtUtil;
import com.zidioAuthService.AuthService.dto.LoginRequest;
import com.zidioAuthService.AuthService.dto.RegisterRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepo;

    @Autowired(required=true)
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private EmailService emailService;

    private final Map<String, String> otpStore = new HashMap<>();

    public String register(RegisterRequest request) {
        Optional<User> existingUser = userRepo.findByEmail(request.getEmail());
        if (existingUser.isPresent()) {
            throw new CustomException("Email already registered");
        }

        User newUser = new User();
        newUser.setName(request.getName());
        newUser.setEmail(request.getEmail());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setRole(request.getRole());
        newUser.setEmailVerified(true);

        userRepo.save(newUser);

        // Generate OTP and send via email
        String otp = generateOTP();
        otpStore.put(request.getEmail(), otp);

        try {
            emailService.sendOtpEmail(request.getEmail(), otp);
        } catch (Exception e) {
            throw new CustomException("Failed to send OTP: " + e.getMessage());
        }

        return "User registered successfully. OTP sent to email.";
    }

    public String verifyOtp(String email, String otp) {
        String storedOtp = otpStore.get(email);

        if (storedOtp == null) {
            throw new CustomException("OTP expired or not generated.");
        }

        if (!storedOtp.equals(otp)) {
            throw new CustomException("Invalid OTP");
        }

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new CustomException("User not found"));

        user.setEmailVerified(true);
        userRepo.save(user);
        otpStore.remove(email);

        return "OTP verified successfully.";
    }

    public String login(LoginRequest request) {
        User user = userRepo.findByEmail(request.getEmail())
                .orElseThrow(() -> new CustomException("User not found"));
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new CustomException("Incorrect password");
        }

        if (!user.isEmailVerified()) {
            throw new CustomException("Email not verified. Please verify using OTP.");
        }

        if (user.getRole() == Role.ADMIN) {
        	return jwtUtil.generateToken(user);
            
        } else if (user.getRole() == Role.STUDENT || user.getRole() == Role.RECRUITER) {
            return jwtUtil.generateToken(user);
        } else {
            throw new CustomException("Unauthorized role");
        }
    }

    private String generateOTP() {
        int otp = 100000 + new Random().nextInt(900000);
        return String.valueOf(otp);
    }
}
