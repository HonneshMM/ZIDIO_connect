package com.zidioAuthService.AuthService.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.zidioAuthService.AuthService.Service.AuthService;
import com.zidioAuthService.AuthService.dto.LoginRequest;
import com.zidioAuthService.AuthService.dto.RegisterRequest;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    //Register User
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody RegisterRequest request) {
        String response = authService.register(request);
        return ResponseEntity.ok(response);
    }

    //Verify OTP for Email Activation
    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestParam("email") String email,
                                            @RequestParam("otp") String otp) {
        String response = authService.verifyOtp(email, otp);
        return ResponseEntity.ok(response);
    }

    //Login (Returns JWT Token)
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        String jwt = authService.login(request);
        if(ResponseEntity.status(200) != null) {
        return ResponseEntity.ok(jwt);
        }
        return (ResponseEntity<String>) ResponseEntity.notFound();
    }

    //Test Secured Route
    @GetMapping("/secure")
    public ResponseEntity<String> secureTest() {
        return ResponseEntity.ok("Accessed Secured Endpoint With Valid JWT!");
    }
}
