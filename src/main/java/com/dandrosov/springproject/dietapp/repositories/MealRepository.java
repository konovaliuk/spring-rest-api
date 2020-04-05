package com.dandrosov.springproject.dietapp.repositories;

import com.dandrosov.springproject.dietapp.entities.Food;
import com.dandrosov.springproject.dietapp.entities.Meal;
import com.dandrosov.springproject.dietapp.entities.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

public interface MealRepository extends CrudRepository<Meal, Long> {

    List<Meal> getMealsByUserAndDate(User user, Date date);

    List<Meal> getDistinctByUser(User user);

    List<Meal> findByUserEqualsAndNutrientEqualsOrderByDateAsc(User user, Food nutrient);

    @Query(
            "SELECT DISTINCT food FROM Food food, Meal meal "
                    + "WHERE food = meal.nutrient "
                    + "AND meal.user = :user "
                    + "AND meal.date BETWEEN :startDate AND :endDate "
                    + "ORDER BY food.name ASC")
    List<Meal> findByUserEatenWithinRange(
            @Param("user") User user,
            @Param("startDate") Date startDate,
            @Param("endDate") Date endDate
    );

    Meal findById(long id);

    //List<Meal> findByUserEqualsAndNutrientEqualsOrderByDateAsc(User user, Food food);
}
