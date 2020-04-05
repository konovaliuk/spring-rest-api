package com.dandrosov.springproject.dietapp.controllers;

import com.dandrosov.springproject.dietapp.dto.FoodDTO;
import com.dandrosov.springproject.dietapp.dto.MealDTO;
import com.dandrosov.springproject.dietapp.dto.converters.FoodToFoodDTO;
import com.dandrosov.springproject.dietapp.dto.converters.MealToMealDTO;
import com.dandrosov.springproject.dietapp.dto.converters.UserToUserDTO;
import com.dandrosov.springproject.dietapp.services.FoodService;
import com.dandrosov.springproject.dietapp.services.UserService;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/meal")
public class MealController{

    private static final Logger LOGGER = Logger.getLogger(MealController.class);

    private static final long DAY_IN_MS = 1000 * 60 * 60 * 24;


    private FoodService mealService;
    private UserService userService;
    private UserToUserDTO userToUserDTO;
    private MealToMealDTO mealToMealDTO;
    private FoodToFoodDTO foodToFoodDTO;

    MealController(FoodService mealService,
                   UserService userService,
                   UserToUserDTO userToUserDTO,
                   MealToMealDTO mealToMealDTO,
                   FoodToFoodDTO foodToFoodDTO) {
        this.mealService = mealService;
        this.userService = userService;
        this.userToUserDTO = userToUserDTO;
        this.mealToMealDTO = mealToMealDTO;
        this.foodToFoodDTO = foodToFoodDTO;
    }

    /**
     * search Meals for the current user by date and time ranges.
     * @param principal  - the current logged in user
     * @return - @see MealsDTO with the current page, total pages and the list of meals
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET)
    public List<MealDTO> searchRecentMeals(
            Principal principal) {
        var today = new Date();
        var user = userService.findByUsername(principal.getName());

        return mealService.findEatenRecently(user.getId(), today);
    }

    /**
     *
     * saves a list of meals - they be either new or existing
     *
     * @param principal - the current logged in user
     * @param meals - the list of meals to save
     * @return - an updated version of the saved meals
     */
    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.POST)
    public MealDTO saveMeal(Principal principal, @RequestBody FoodDTO food, int qty) {
        var user = userService.findByUsername(principal.getName());
        var date = new Date();

        return mealService.addMeal(user.getId(), food.getId(), qty, date);
    }

    /**
     *
     * error handler for backend errors - a 400 status code will be sent back, and the body
     * of the message contains the exception text.
     *
     * @param exc - the exception caught
     */

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> errorHandler(Exception exc) {
        LOGGER.error(exc.getMessage(), exc);
        return new ResponseEntity<>(exc.getMessage(), HttpStatus.BAD_REQUEST);
    }


}
