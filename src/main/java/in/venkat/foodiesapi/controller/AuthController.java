package in.venkat.foodiesapi.controller;


import in.venkat.foodiesapi.entity.UserEntity;
import in.venkat.foodiesapi.io.AuthenticationRequest;
import in.venkat.foodiesapi.io.AuthenticationResponse;
import in.venkat.foodiesapi.service.AppUserDetailService;
import in.venkat.foodiesapi.service.UserService;
import in.venkat.foodiesapi.util.JwtUtil;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private  final JwtUtil jwtUtil;



    @PostMapping("/login")
    public AuthenticationResponse login(@RequestBody AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        UserEntity user = userService.getUserByEmail(request.getEmail()); // 👈 New method in AppUserDetailService
        String jwtToken = jwtUtil.generateToken(user);

        return new AuthenticationResponse(request.getEmail(), jwtToken);
    }




    //Testing purpose

//    @PostMapping("/login")
//    public ResponseEntity<?> login(@RequestBody AuthenticationRequest request) {
//        try {
//            authenticationManager.authenticate(
//                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
//            );
//
//            final UserDetails userDetails = userDetailService.loadUserByUsername(request.getEmail());
//            final String jwtToken = jwtUtil.generateToken(userDetails);
//
//            return ResponseEntity.ok(new AuthenticationResponse(request.getEmail(), jwtToken));
//
//        } catch (Exception e) {
//            // Optional: print the error for debugging
//            System.out.println("Login failed for user: " + request.getEmail());
//            e.printStackTrace();
//
//            return ResponseEntity
//                    .status(HttpStatus.UNAUTHORIZED)
//                    .body("Invalid email or password");
//        }
//    }




}
