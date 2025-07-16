package in.venkat.foodiesapi.controller;

import in.venkat.foodiesapi.entity.UserEntity;
import in.venkat.foodiesapi.exception.BadRequestException;
import in.venkat.foodiesapi.exception.ResourceNotFoundException;
import in.venkat.foodiesapi.io.UserRequest;
import in.venkat.foodiesapi.io.UserResponse;
import in.venkat.foodiesapi.repository.UserRepository;
import in.venkat.foodiesapi.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class UserController {


    private final UserService userService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public UserResponse register(@RequestBody UserRequest request){
        return userService.registerUser(request);
    }

    @PostMapping("/forgot/password")
    public ResponseEntity<String> forgotPassword(@RequestBody Map<String, String> request) {
        String email = request.get("email");
        if (email == null || email.isBlank()) {
            throw new BadRequestException("Email must not be empty");
        }

        boolean success = userService.sendOtp(email);
        if (!success) {
            throw new ResourceNotFoundException("User not found with email: " + email);
        }

        return ResponseEntity.ok("OTP sent to email");
    }

    @PostMapping("/verify/otp")
    public ResponseEntity<String> verifyOtp(@RequestBody Map<String, String> request) {
        boolean verified = userService.verifyOtpAndResetPassword(
                request.get("email"),
                request.get("otp"),
                request.get("newPassword")
        );

        return verified
                ? ResponseEntity.ok("Password reset successful")
                : ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid OTP or email");
    }



}
