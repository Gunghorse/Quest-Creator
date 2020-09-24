package com.github.gunghorse.questCreator.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServices {

    private final UserRepository userRepository;

    public UserServices(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User login(UserDTO userDTO) {
        String password = encoder().encode(userDTO.getPassword());
        User user = userRepository.findByEmailIgnoreCase(userDTO.getEmail());
        System.out.println(user.getPassword() == password);
        return user;
    }

    public User registerNewUserAccount(UserDTO accountDto) {
        /*if (emailExist(accountDto.getEmail())) {
            throw new EmailExistsException(
                    "There is an account with that email adress:" + accountDto.getEmail());
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