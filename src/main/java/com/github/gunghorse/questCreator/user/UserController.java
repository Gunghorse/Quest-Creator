package com.github.gunghorse.questCreator.user;

import com.github.gunghorse.questCreator.repositories.UserRepository;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("${v1API}/user")
public class UserController {

    private UserRepository userRepository;
    private UserService userService;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userService = new UserService(this.userRepository);
    }

    /**
     * URL looks like:
     *      GET /user/
     *
     * @return Info about current user
     */
    @RequestMapping(value="/info", method = RequestMethod.GET)
    public User getMyInfo(HttpServletRequest req){
        String name = req.getRemoteUser();
        return userRepository.findByUsername(name);
    }

    /**
     * URL looks like:
     *      GET /user/logout
     *
     * Check if login and password correct and if such user is in system
     * @param req HttpServletRequest for calling logout
     * @return true if everything ok
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public boolean logout(HttpServletRequest req){
        try {
            req.logout();
            return true;
        } catch (ServletException e) {
            return false;
        }
    }

    /**
     * URL looks like:
     *      POST /user/registration
     *
     * Check if such user is not present in system and then add him to database
     * @param userDto send in POST body object of User
     * @return true if registration is successful
     */
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registerUserAccount(@RequestBody UserDTO userDto) {
        userService.registerNewUserAccount(userDto);
        return "registered";
    }
}
