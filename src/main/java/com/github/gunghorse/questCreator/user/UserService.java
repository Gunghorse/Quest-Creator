package com.github.gunghorse.questCreator.user;

import com.github.gunghorse.questCreator.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.github.gunghorse.questCreator.exceptions.EmailOrUsernameAlreadyExistsException;

public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User registerNewUserAccount(UserDTO userDto) {
        if (emailExist(userDto.getEmail()) || usernameExist(userDto.getUsername())) {
            throw new EmailOrUsernameAlreadyExistsException(
                    "There already exists an account with that email address: " + userDto.getEmail() +
                            " or username: " + userDto.getUsername());
        }
        User user = new User();
        user.setUsername(userDto.getUsername());

        String pass = encoder().encode(userDto.getPassword());
        user.setPassword(pass);

        user.setEmail(userDto.getEmail());
        return userRepository.save(user);
    }

    private boolean emailExist(String email) {
        return userRepository.findByEmailIgnoreCase(email) != null;
    }

    private boolean usernameExist(String username) {
        return userRepository.findByUsername(username) != null;
    }

    private PasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

}