package com.decagon.karrigobe.services.verification_services.serviceImplementation;

import com.decagon.karrigobe.entities.model.UserEntity;
import com.decagon.karrigobe.exceptions.EmailNotSentException;
import com.decagon.karrigobe.exceptions.UserNotFoundException;
import com.decagon.karrigobe.repositories.UserRepository;
import com.decagon.karrigobe.security.JWTGenerator;
import com.decagon.karrigobe.services.email_service.EmailServices;
import com.decagon.karrigobe.services.verification_services.VerificationServices;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@RequiredArgsConstructor
@Service
public class VerificationServiceImpl implements VerificationServices {
    private final JWTGenerator jwtGenerator;
    private final UserRepository userRepo;
    private final EmailServices emailServices;

    @Override
    public String verifyUserEmail(String token) {
        if (!jwtGenerator.validateToken(token)) {
            return "Token has expired";
        }

        String email = jwtGenerator.getEmailFromJWT(token);
        UserEntity user = userRepo.findUserEntityByEmail(email)
                .orElseThrow(()-> new UserNotFoundException("User not found"));

        user.setIsVerified(true);

        userRepo.save(user);

        return "Email verified successfully. Now you can login to your account";
    }

    @Override
    public void re_sendVerificationEmail() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        String verificationToken = jwtGenerator.generateSignupToken(email, 5L);
        String url = "http://192.168.0.44:8085/api/v1/registration/email_verification?token="+verificationToken;

        String subject = "Email Verification";
        String senderName = "KarriGo";
        String mailContent = "<p>Please, follow the link below to complete your registration. \nThis link <strong> expires in 5 minute</strong>.</p>"+
                "<a href=\"" +url+ "\">Verify your email to activate your account</a>"+
                "<p> Thank you <br> Users Registration Portal Service";
        new Thread(() -> {
            try {
                emailServices.sendSimpleMessage(email, subject, mailContent, senderName);
            } catch (MessagingException | UnsupportedEncodingException e) {
                throw new EmailNotSentException("Email not sent.");
            }
        }).start();
    }
}
