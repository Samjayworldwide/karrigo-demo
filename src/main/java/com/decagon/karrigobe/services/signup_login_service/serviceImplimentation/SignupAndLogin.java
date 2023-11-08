package com.decagon.karrigobe.services.signup_login_service.serviceImplimentation;

import com.decagon.karrigobe.entities.enums.Gender;
import com.decagon.karrigobe.entities.enums.Roles;
import com.decagon.karrigobe.entities.model.UserEntity;
import com.decagon.karrigobe.exceptions.DuplicateEmailException;
import com.decagon.karrigobe.exceptions.PasswordMismatchException;
import com.decagon.karrigobe.payload.request.UserRequest;
import com.decagon.karrigobe.repositories.UserRepository;
import com.decagon.karrigobe.services.signup_login_service.SignupAndLoginServices;
import com.decagon.karrigobe.utils.events.RegistrationCompleteEvent;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class SignupAndLogin implements SignupAndLoginServices {
    private final UserRepository userRepos;
    private final PasswordEncoder encoder;
    private final ApplicationEventPublisher publisher;


    @Override
    public String register(UserRequest userRequest, HttpServletRequest request) {
        if (userRepos.existsByEmail(userRequest.getEmail())){
            throw new DuplicateEmailException("Email already exist!");
        }

        if (!userRequest.getPassword().equals(userRequest.getConfirmPassword())){
            throw new PasswordMismatchException("Password mismatch.");
        }

        String path = request.getContextPath();

        UserEntity user = UserEntity.builder()
                .firstName(userRequest.getFirstName())
                .lastName(userRequest.getLastName())
                .email(userRequest.getEmail())
                .password(encoder.encode(userRequest.getPassword()))
                .confirmPassword(userRequest.getConfirmPassword())
                .phoneNumber(userRequest.getPhoneNumber())
                .address(userRequest.getAddress())
                .gender(Gender.valueOf(userRequest.getGenders()))
                .roles(
                        path.contains("drivers") ? Roles.DRIVER :
                        path.contains("users") ? Roles.USER : Roles.ADMIN
                )
                .isVerified(false)
                .build();
        userRepos.save(user);

        publisher.publishEvent(new RegistrationCompleteEvent(user, applicationUrl(request)));

        return "Verification link sent to Email. Check email and verify your account.";
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://"+request.getServerName()+":"+request.getServerPort()+request.getContextPath();
    }
}
