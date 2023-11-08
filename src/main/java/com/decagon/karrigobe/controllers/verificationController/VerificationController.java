package com.decagon.karrigobe.controllers.verificationController;

import com.decagon.karrigobe.payload.response.ApiResponse;
import com.decagon.karrigobe.services.verification_services.VerificationServices;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/registration")
public class VerificationController {
    private final VerificationServices verificationServices;

    @GetMapping("/email_verification")
    public ResponseEntity<ApiResponse<String>> verifyEmail(@RequestParam("token") String token){
        return ResponseEntity.ok( new ApiResponse<>(verificationServices.verifyUserEmail(token)));
    }

    @GetMapping("/re_verification")
    public ResponseEntity<ApiResponse<String>> emailReverification(){
        verificationServices.re_sendVerificationEmail();
        return ResponseEntity.ok(new ApiResponse<>("Email verification sent.\nCheck your email and click on the link to verify."));
    }
}
