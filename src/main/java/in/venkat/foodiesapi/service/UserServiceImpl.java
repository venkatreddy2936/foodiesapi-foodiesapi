package in.venkat.foodiesapi.service;

import in.venkat.foodiesapi.entity.UserEntity;
import in.venkat.foodiesapi.exception.ResourceNotFoundException;
import in.venkat.foodiesapi.io.UserRequest;
import in.venkat.foodiesapi.io.UserResponse;
import in.venkat.foodiesapi.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

@Service
@AllArgsConstructor
public  class UserServiceImpl  implements UserService{

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private  final AuthenticationFacade authenticationFacade;



    private final JavaMailSender mailSender;
    private final Map<String, String> otpStore = new HashMap<>();


    @Override
    public UserResponse registerUser(UserRequest request) {
        UserEntity newUser=convertToEntity(request);
         newUser=userRepository.save(newUser);
         return convertToResponse(newUser);
    }

    @Override
    public String findByUserId() {
        String loggedInUserEmail =authenticationFacade.getAuthentication().getName();
     UserEntity loggedInUser=userRepository.findByEmail(loggedInUserEmail).orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + loggedInUserEmail));
     return loggedInUser.getId();
    }
//    ********************************************************************



    @Override
    public boolean sendOtp(String email) {
        Optional<UserEntity> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) return false;

        String otp = String.format("%06d", new Random().nextInt(999999));
        otpStore.put(email, otp);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP for Password Reset");
        message.setText("Your OTP is: " + otp);
        mailSender.send(message);
        return true;
    }

    @Override
    public boolean verifyOtpAndResetPassword(String email, String otp, String newPassword) {
        String storedOtp = otpStore.get(email);
        if (storedOtp == null || !storedOtp.equals(otp)) return false;

        Optional<UserEntity> userOpt = userRepository.findByEmail(email);
        if (userOpt.isEmpty()) return false;

        UserEntity user = userOpt.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        otpStore.remove(email);
        return true;
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found with email: " + email)
                );
    }


    private  UserEntity convertToEntity(UserRequest request){
        String role = "USER";  // default role
        if (request.getRole() != null && request.getRole().equalsIgnoreCase("ADMIN")) {
            role = "ADMIN";
        }
        return UserEntity.builder()
                .email(request.getEmail())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(role)
                 .build();
    }
    private  UserResponse convertToResponse(UserEntity registeredUser){
        return UserResponse.builder()
                .email(registeredUser.getEmail())
                .id(registeredUser.getId())
                .name(registeredUser.getName())
                .role(registeredUser.getRole())
                .build();
    }
}
