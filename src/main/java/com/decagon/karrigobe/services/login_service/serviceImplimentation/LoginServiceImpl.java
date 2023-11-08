package com.decagon.karrigobe.services.login_service.serviceImplimentation;

import com.decagon.karrigobe.entities.model.UserEntity;
import com.decagon.karrigobe.exceptions.InvalidPasswordException;
import com.decagon.karrigobe.exceptions.UserNotFoundException;
import com.decagon.karrigobe.payload.request.LoginRequest;
import com.decagon.karrigobe.payload.response.AuthResponse;
import com.decagon.karrigobe.repositories.UserRepository;
import com.decagon.karrigobe.security.JWTGenerator;
import com.decagon.karrigobe.services.login_service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class LoginServiceImpl implements LoginService {
    private final UserRepository userRepo;
    private final PasswordEncoder encoder;
    private final AuthenticationManager authenticationManager;
    private final JWTGenerator jwtGenerator;

    @Override
    public AuthResponse login(LoginRequest loginRequest) {
        UserEntity user = userRepo.findUserEntityByEmail(loginRequest.getEmail())
                .orElseThrow(()-> new UserNotFoundException("Invalid Email address."));

        if (!encoder.matches(loginRequest.getPassword(), user.getPassword())){
            throw new InvalidPasswordException("Invalid password!");
        }

        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = jwtGenerator.generateToken(authentication, 30L);
        String freshToken = jwtGenerator.generateToken(authentication, 1440L);

        return new AuthResponse(token, freshToken);
    }

    @Override
    public void logout() {
        SecurityContextHolder.clearContext();
    }
}
