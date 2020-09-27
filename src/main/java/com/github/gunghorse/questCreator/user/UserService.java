package com.github.gunghorse.questCreator.user;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerNewUserAccount(UserDTO accountDto) {
        /*if (emailExist(accountDto.getEmail())) {
            throw new EmailExistsException(
                    "There is an account with that email address:" + accountDto.getEmail());
        }*/
        User user = new User();
        user.setUsername(accountDto.getUsername());

        String pass = encoder().encode(accountDto.getPassword());
        user.setPassword(pass);

        user.setEmail(accountDto.getEmail());
        return userRepository.save(user);
    }

    private boolean emailExist(String email) {
        return userRepository.findByEmailIgnoreCase(email) != null;
    }

    private PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}