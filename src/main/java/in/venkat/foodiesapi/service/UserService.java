package in.venkat.foodiesapi.service;

import in.venkat.foodiesapi.entity.UserEntity;
import in.venkat.foodiesapi.io.UserRequest;
import in.venkat.foodiesapi.io.UserResponse;

public interface UserService {
    UserResponse  registerUser(UserRequest  request);

    String findByUserId();
    UserEntity getUserByEmail(String email);


    boolean sendOtp(String email);
    boolean verifyOtpAndResetPassword(String email, String otp, String newPassword);



}
