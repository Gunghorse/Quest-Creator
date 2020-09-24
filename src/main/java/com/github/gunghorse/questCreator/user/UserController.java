package com.github.gunghorse.questCreator.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@RestController
@RequestMapping("${v1API}/user")
public class UserController {

    private UserRepository userRepository;
    private UserServices userServices;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
        this.userServices = new UserServices(this.userRepository);
    }

    /**
     * URL looks like:
     *      GET /user/
     *
     * @return Info about you;
     */
    @RequestMapping(value="/info", method = RequestMethod.GET)
    public User getMyInfo(HttpServletRequest req){
        String name = req.getRemoteUser();
        User me = userRepository.findByUsername(name);
        return me;
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
     *      POST /user/register
     *
     * Check if such user is not present in system and then added him
     * @param userDto send in POST body object of User
     * @return true if registration is successful
     */
    @RequestMapping(value = "/registration", method = RequestMethod.POST)
    public String registerUserAccount(@RequestBody UserDTO userDto) {
        userServices.registerNewUserAccount(userDto);
        return "registered";
    }
}
