package com.dandrosov.springproject.dietapp.controllers;

import com.dandrosov.springproject.dietapp.dto.UserDTO;
import com.dandrosov.springproject.dietapp.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping("api/auth")
public class AuthController extends AbstractController {

    private static final Logger LOGGER = Logger.getLogger(AuthController.class);
    AuthController(UserService userService) {
        super(userService);
    }

    @PostMapping("/userpass")
    public HttpServletResponse doLogin(
            @RequestBody final Map<String, Object> payload,
            final HttpServletResponse response){
        if (payload.get("username") == null || !(payload.get("username") instanceof String)
                || payload.get("password") == null || !(payload.get("password") instanceof String)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        // Check credentials
        final String username = (String) payload.get("username");
        final String password = (String) payload.get("password");
        final UserDTO userDTO = userService.findByEmail(username);
        if (userDTO == null || !userService.verifyPassword(userDTO, password)) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
        return response;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler(Exception exc) {
        LOGGER.error(exc.getMessage(), exc);
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
