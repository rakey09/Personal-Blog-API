package com.rakey.Personal_Blog.controller;

import com.rakey.Personal_Blog.config.UserDetailService;
import com.rakey.Personal_Blog.config.jwt.JwtService;
import com.rakey.Personal_Blog.model.LoginForm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor

public class LoginController {

    private final AuthenticationManager authenticationManager;

    private final JwtService jwtService;

    private final UserDetailService myUserDetailService;

    @PostMapping("/authenticate")
    public String authenticateAndGetToken(@RequestBody LoginForm loginForm) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginForm.username(), loginForm.password()
        ));
        if (authentication.isAuthenticated()) {
            return jwtService.generateToken(myUserDetailService.loadUserByUsername(loginForm.username()));
        } else {
            throw new UsernameNotFoundException("Invalid credentials");
        }
    }
}
