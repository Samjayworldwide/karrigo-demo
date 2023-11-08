package com.decagon.karrigobe.controllers.driver_controller;

import com.decagon.karrigobe.payload.request.LoginRequest;
import com.decagon.karrigobe.payload.request.UserRequest;
import com.decagon.karrigobe.payload.response.ApiResponse;
import com.decagon.karrigobe.payload.response.AuthResponse;
import com.decagon.karrigobe.services.login_service.LoginService;
import com.decagon.karrigobe.services.signup_login_service.SignupAndLoginServices;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/drivers")
public class DriverSignupAndLoginController {
    private final SignupAndLoginServices signupAndLoginServices;
    private final LoginService loginService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<String>> registration(@Valid @RequestBody UserRequest userRequest, HttpServletRequest request){
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse<>(signupAndLoginServices.register(userRequest, request)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest loginRequest){
        return ResponseEntity.ok(new ApiResponse<>("Login successfully", loginService.login(loginRequest)));
    }

    @GetMapping("/logout")
    private ResponseEntity<ApiResponse<String>> logout(){
        loginService.logout();
        return ResponseEntity.ok(new ApiResponse<>("Logout Successfully"));
    }
}
