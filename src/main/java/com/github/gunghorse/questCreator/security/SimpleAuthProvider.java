package com.github.gunghorse.questCreator.security;

import com.github.gunghorse.questCreator.user.User;
import com.github.gunghorse.questCreator.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.util.Collections;

@Component
public class SimpleAuthProvider implements AuthenticationProvider {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    UserRepository userRepository;

    @Override
    public Authentication authenticate(Authentication auth)
            throws AuthenticationException {
        if (!auth.getName().isEmpty() && !auth.getCredentials().toString().isEmpty()) {
            User user;
            String password = auth.getCredentials().toString();
            if (isValidEmailAddress(auth.getName()))
                user = userRepository.findByEmailIgnoreCase(auth.getName());
            else user = userRepository.findByUsername(auth.getName());

            if (user != null && passwordEncoder.matches(password, user.getPassword()))
                return new UsernamePasswordAuthenticationToken
                        (user.getUsername(), password, Collections.emptyList());
        }
        throw new BadCredentialsException("External system authentication failed");
    }

    public static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    @Override
    public boolean supports(Class<?> auth) {
        return UsernamePasswordAuthenticationToken.class.isAssignableFrom(auth);
    }
}

