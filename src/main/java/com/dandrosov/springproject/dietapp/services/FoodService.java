package com.dandrosov.springproject.dietapp.services;

import com.dandrosov.springproject.dietapp.dto.FoodDTO;
import com.dandrosov.springproject.dietapp.dto.MealDTO;
import com.dandrosov.springproject.dietapp.dto.UserDTO;
import com.dandrosov.springproject.dietapp.dto.converters.FoodToFoodDTO;
import com.dandrosov.springproject.dietapp.dto.converters.MealToMealDTO;
import com.dandrosov.springproject.dietapp.entities.Food;
import com.dandrosov.springproject.dietapp.entities.Meal;
import com.dandrosov.springproject.dietapp.entities.User;
import com.dandrosov.springproject.dietapp.repositories.FoodRepository;
import com.dandrosov.springproject.dietapp.repositories.MealRepository;
import com.dandrosov.springproject.dietapp.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;
    private final UserRepository userRepository;
    private final MealRepository foodEatenRepository;
    private final FoodToFoodDTO foodDTOConverter;
    private final MealToMealDTO foodEatenDTOConverter;


    public final List<MealDTO> findEatenOnDate(long id, Date date){
        var user = userRepository.findById(id);
        return foodEatenRepository.getMealsByUserAndDate(user, date)
                .stream()
                .map(foodEatenDTOConverter::convert)
                .collect(toList());
    }

    public final List<MealDTO> findEatenRecently(long id, Date currentDate){
        var user = userRepository.findById(id);
        var calendar = new GregorianCalendar();
        calendar.setTime(currentDate);
        calendar.add(Calendar.DATE, -7);
        var weekAgo = new Date(calendar.getTime().getTime());
        return foodEatenRepository.findByUserEatenWithinRange(user,
                currentDate, weekAgo)
                .stream()
                .map(foodEatenDTOConverter::convert)
                .collect(toList());
    }

    public final List<FoodDTO> searchFoods(String searchString) {
        return foodRepository.findByNameLike(searchString)
                .stream()
                .map(foodDTOConverter::convert)
                .collect(toList());
    }

    public final MealDTO findMealById(long id){
        var meal = foodEatenRepository.findById(id);
        return foodEatenDTOConverter.convert(meal);
    }

    public final MealDTO addMeal(long userId,
                                long foodId,
                                int quantity,
                                Date date) {
        boolean duplicate = findEatenOnDate(userId, date)
                .stream()
                .anyMatch((var foodAlreadyEaten) -> foodAlreadyEaten
                        .getFoodId() == foodId );
        if (!duplicate) {
            var user = userRepository.findById(userId);
            var food = foodRepository.findById(foodId);
            var calories = food.getCalories()*quantity;
            var foodEaten = new Meal(food, user, quantity, date, calories);
            foodEatenRepository.save(foodEaten);
            return foodEatenDTOConverter.convert(foodEaten);
        }
        return null;
    }

    public final MealDTO updateMeal(
            long mealId,
            int servingQty
    ) {
        var meal = foodEatenRepository.findById(mealId);
        meal.setQuantity(servingQty);
        var calories = meal.getNutrient().getCalories()*servingQty;
        meal.setCalories(calories);
        foodEatenRepository.save(meal);
        return foodEatenDTOConverter.convert(meal);
    }

    public final void deleteMeal(long mealId) {
        var foodEaten = foodEatenRepository.findById(mealId);
        foodEatenRepository.delete(foodEaten);
    }

    public final FoodDTO getFoodById(long foodId) {
        final Food food = foodRepository.findById(foodId);
        return foodDTOConverter.convert(food);
    }

    public final String updateFood(
            FoodDTO foodDTO,
            UserDTO userDTO
    ) {
        String resultMessage = "";
        // Halt if this update would create two foods with duplicate names owned by the same user.
        List<Food> foodsWithSameName = foodRepository.findByNameEquals(foodDTO.getName());
        boolean noConflictsFound = foodsWithSameName
                .stream()
                .allMatch( (Food food) -> foodDTO.getId() == food.getId() ); // Should be only one item in this stream anyway
        if (noConflictsFound) {
            // If this is already a user-owned food, then simply update it.  Otherwise, if it's a global food then create a
            // user-owned copy for this user.
            User user = userRepository.findById(userDTO.getId());
            Food food = foodRepository.findById(foodDTO.getId());
            var dateFirstEaten = new Date();
            final List<Meal> foodsEatenSortedByDate = foodEatenRepository.findByUserEqualsAndNutrientEqualsOrderByDateAsc(user, food);
            dateFirstEaten = (foodsEatenSortedByDate != null && !foodsEatenSortedByDate.isEmpty())
                        ? foodsEatenSortedByDate.get(0).getDate() : new Date(System.currentTimeMillis());
            food.setName(foodDTO.getName());
            food.setDescription(foodDTO.getDescription());
            food.setCalories(foodDTO.getCalories());
            foodRepository.save(food);
            resultMessage = "Success!";
            } else {
                resultMessage = "Error:  You already have another customized food with this name.";
            }
        return resultMessage;
    }

    public final String createFood(FoodDTO foodDTO) {
        String resultMessage = "";

        // Halt if this update would create two foods with duplicate names owned by the same user.
        List<Food> foodsWithSameName = foodRepository.findByNameEquals(foodDTO.getName());

        if (foodsWithSameName.isEmpty()) {
            final Food food = new Food();
            food.setName(foodDTO.getName());
            food.setCalories(foodDTO.getCalories());
            food.setDescription(foodDTO.getDescription());
            foodRepository.save(food);
            resultMessage = "Success!";
        } else {
            resultMessage = "Error:  You already have another customized food with this name.";
        }
        return resultMessage;
    }

}
