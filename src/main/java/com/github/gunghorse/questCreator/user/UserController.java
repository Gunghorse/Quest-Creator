package com.github.gunghorse.questCreator.user;

import com.github.gunghorse.questCreator.repositories.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

    @RequestMapping(value="/login", method = RequestMethod.GET)
    public void login(HttpServletRequest req){
        System.out.println("Login");
        //return userRepository.findByUsername(name);
    }

    /**
     * URL looks like:
     *      GET /user/logout
     *
     * Check if login and password correct and if such user is in system
     * @param request HttpServletRequest for calling logout
     * @return true if everything ok
     */
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logoutDo(HttpServletRequest request, HttpServletResponse response){
        HttpSession session= request.getSession(false);
        SecurityContextHolder.clearContext();
        session= request.getSession(false);
        if(session != null) {
            session.invalidate();
        }
        for(Cookie cookie : request.getCookies()) {
            cookie.setMaxAge(0);
        }

        return "logout";
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
