package com.dandrosov.springproject.dietapp.dto.converters;

import com.dandrosov.springproject.dietapp.dto.MealDTO;
import com.dandrosov.springproject.dietapp.entities.Meal;
import org.modelmapper.Converters.Converter;
import org.springframework.stereotype.Component;

@Component(value = "mealToMealDTO")
public class MealToMealDTO implements Converter<Meal, MealDTO> {

    @Override
    public MealDTO convert(Meal meal) {
        MealDTO mealDTO  = null;
        if (meal!= null){
            mealDTO = new MealDTO(
                    meal.getId(),
                    meal.getUser().getId(),
                    meal.getNutrient().getId(),
                    meal.getQuantity(),
                    meal.getCalories(),
                    meal.getDate());
            }
        return mealDTO;
        }

//    double getCalories(Meal meal){
//        return meal.getQuantity()*meal.getNutrient().getCalories();
//    }
}
