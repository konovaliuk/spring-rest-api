package com.dandrosov.springproject.dietapp.repositories.impl;

import com.dandrosov.springproject.dietapp.entities.User;
import com.dandrosov.springproject.dietapp.repositories.UserRepository;
import com.dandrosov.springproject.dietapp.repositories.UserRepositoryCustom;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    private UserRepository userRepositoryCrud;

    UserRepositoryCustomImpl(UserRepository userRepositoryCrud){
        this.userRepositoryCrud = userRepositoryCrud;
    }

    @Override
    public User findUserByUsername(String username) {
        var users = userRepositoryCrud.findByUserName(username);
        return users.size()==1 ? users.get(0) : null;
    }

    @Override
    public double calculateConsumptedCalories(String username) {
        return userRepositoryCrud.calculateConsumptedCalories(username);
    }

    @Override
    public List<User> findByUserName(String username) {
        return userRepositoryCrud.findByUserName(username);
    }

    @Override
    public User findByEmailEquals(String email) {
        return userRepositoryCrud.findByEmailEquals(email);
    }

    @Override
    public List<User> findAll() {
        return userRepositoryCrud.findAll();
    }

    @Override
    public long count(){
        return userRepositoryCrud.count();
    }

    @Override
    public User save(User user){
        return userRepositoryCrud.save(user);
    }

    @Override
    public void deleteById(long id) {
        userRepositoryCrud.deleteById(id);
    }

    @Override
    public String getFullName(User user) {
        return user.getLastName().isBlank() ? user.getFirstName() : user.getFirstName() + " " + user.getLastName();
    }

    @Override
    public User findById(long id) {
        return userRepositoryCrud.findById(id);
    }
}
