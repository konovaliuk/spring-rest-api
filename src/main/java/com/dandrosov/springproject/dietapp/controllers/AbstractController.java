package com.dandrosov.springproject.dietapp.controllers;

import com.dandrosov.springproject.dietapp.dto.UserDTO;
import com.dandrosov.springproject.dietapp.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
public class AbstractController {
    final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private static final Logger LOGGER = Logger.getLogger(AbstractController.class);

    UserService userService;

    AbstractController(UserService userService){
        this.userService = userService;
    }

    /**
     * Used by child class controllers to obtain the currently authenticated user from Spring Security.
     */

    final UserDTO currentAuthenticatedUser(final HttpServletRequest request) {
        return userService.findByEmail((String) request.getAttribute("email"));
    }

    final java.sql.Date stringToSqlDate(final String dateString) {
        java.sql.Date date;
        try {
            date = java.sql.Date.valueOf(dateString);
        } catch (final IllegalArgumentException e) {
            e.printStackTrace();
            date = new java.sql.Date(new Date().getTime());
        }
        return date;
    }

    final java.sql.Date todaySqlDateForUser(final UserDTO user) {
        return new java.sql.Date(new Date().getTime());
    }

}
