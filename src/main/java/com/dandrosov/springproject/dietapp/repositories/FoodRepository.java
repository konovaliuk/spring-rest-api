package com.dandrosov.springproject.dietapp.repositories;

import com.dandrosov.springproject.dietapp.entities.Food;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface FoodRepository extends CrudRepository<Food, Long> {

    List<Food> findByNameLike(String name);

    List<Food> findByDescriptionLike(String description);

    List<Food> findAll();

    Food findById(long id);

    List<Food> findByNameEquals(String name);
}
