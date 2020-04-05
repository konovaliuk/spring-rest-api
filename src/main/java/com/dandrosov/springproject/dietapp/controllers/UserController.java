package com.dandrosov.springproject.dietapp.controllers;

import com.dandrosov.springproject.dietapp.dto.UserDTO;
import com.dandrosov.springproject.dietapp.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

/**
 *
 *  REST service for users.
 *
 */

@RestController
@RequestMapping("/api")
public class UserController{

    private static final Logger LOGGER = Logger.getLogger(UserController.class);
    private UserService userService;
    private AuthenticationManager authenticationManager;

    public UserController(UserService userService,
                          AuthenticationManager authenticationManager){
        this.userService = userService;
        this.authenticationManager = authenticationManager;
    }

    final UserDTO currentAuthenticatedUser(Principal principal) {
        return userService.findByUsername(principal.getName());
    }

    @GetMapping(path="/", produces = "application/json")
    public String init()
    {
        return "hello";
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @GetMapping(value = "/info", produces = "application/json")
    public UserDTO getUserInfo(Principal principal) {
        return currentAuthenticatedUser(principal);
    }

    public void autologin(String username, String password){
        var userDetails = userService.findByUsername(username);
        var usernamePasswordAuthenticationToken
                = new UsernamePasswordAuthenticationToken(userDetails, password);

        authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
            LOGGER.debug(String.format("Auto login %s successfully!", username));
        }
    }

    @RequestMapping(value = "/registration", method = RequestMethod.GET)
    public String registration(Model model) {
        model.addAttribute("userForm", new UserDTO());

        return "registration";
    }

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/registration", method = RequestMethod.POST, consumes = "application/json")
    public String createUser(
            @ModelAttribute("userForm") UserDTO userForm,
            BindingResult bindingResult, Model model,
            String password) {
        userService.createUser(userForm, password);
        return "redirect:/login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String login(Model model, String error, String logout) {
        if (error != null)
            model.addAttribute("error", "Your username and password is invalid.");

        if (logout != null)
            model.addAttribute("message", "You have been logged out successfully.");

        return "login";
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler(Exception exc) {
        LOGGER.error(exc.getMessage(), exc);
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }

}