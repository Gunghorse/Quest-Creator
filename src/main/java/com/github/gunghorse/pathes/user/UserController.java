package com.github.gunghorse.pathes.user;

import com.github.gunghorse.pathes.sessions.Session;
import com.github.gunghorse.pathes.sessions.SessionRepository;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping(value="", method = RequestMethod.GET)
    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public boolean login(@RequestBody User user){
        return userRepository.findByLoginAndPassword(user.getLogin(), user.getPassword()) != null;
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public boolean register(@RequestBody User user){
        if(userRepository.findByLogin(user.getLogin()) != null){
            userRepository.save(user);
            return true;
        }
        return false;
    }

}
