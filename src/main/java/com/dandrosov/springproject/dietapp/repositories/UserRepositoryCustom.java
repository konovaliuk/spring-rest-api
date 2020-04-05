package com.dandrosov.springproject.dietapp.repositories;

import com.dandrosov.springproject.dietapp.entities.User;

import java.util.List;

public interface UserRepositoryCustom{
    User findUserByUsername(String username);
    double calculateConsumptedCalories(String username);
    List<User> findByUserName(String username);
    User findByEmailEquals(String email);
    List<User> findAll();
    long count();
    User save(User user);
    void deleteById(long id);
    String getFullName(User user);
    User findById(long id);
}
