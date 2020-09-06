package com.github.gunghorse.pathes.user;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * URL looks like:
     *      GET /user/
     *
     * @return List of all users
     */
    @RequestMapping(value="", method = RequestMethod.GET)
    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }

    /**
     * URL looks like:
     *      /user/login
     *
     * Check if login and password correct and if such user is in system
     * @param user send in POST body object of User
     * @return true if user correct wright his login and password
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public boolean login(@RequestBody User user){
        return userRepository.findByLoginAndPassword(user.getLogin(), user.getPassword()) != null;
    }

    /**
     * URL looks like:
     *      /user/register
     *
     * Check if such user is not present in system and then added him
     * @param user send in POST body object of User
     * @return true if registration is successful
     */
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public boolean register(@RequestBody User user){
        if(userRepository.findByLogin(user.getLogin()) != null){
            userRepository.save(user);
            return true;
        }
        return false;
    }

}
