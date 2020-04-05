package com.dandrosov.springproject.dietapp.dto.converters;

import com.dandrosov.springproject.dietapp.dto.FoodDTO;
import com.dandrosov.springproject.dietapp.entities.Food;
import org.modelmapper.Converters;
import org.springframework.stereotype.Component;

@Component(value = "foodToFoodDTO")
public class FoodToFoodDTO implements Converters.Converter<Food, FoodDTO> {

    @Override
    public FoodDTO convert(Food food) {
        FoodDTO foodDTO  = null;
        if (food != null){
            foodDTO = new FoodDTO(
                    food.getId(),
                    food.getName(),
                    (int) food.getCalories(),
                    food.getDescription());
        }
        return foodDTO;
    }
}
