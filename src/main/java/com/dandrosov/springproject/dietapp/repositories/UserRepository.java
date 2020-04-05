package com.dandrosov.springproject.dietapp.repositories;

import com.dandrosov.springproject.dietapp.entities.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    @Query(
            "SELECT SUM(meal.calories)"
            +"FROM Meal meal "
            +"WHERE meal.user.userName = :username "
            +"AND meal.date = current_date ")
    double calculateConsumptedCalories(@Param("username") String username);

    @Query("SELECT u FROM User u WHERE u.userName = :username")
    List<User> findByUserName(@Param("username") String username);

    User findByEmailEquals(String email);

    User findById(long id);

    @NotNull List<User> findAll();

    long count();

    void deleteById(long id);

    User save(User user);
}
