package com.example.springrecipebook.Repositories;

import com.example.springrecipebook.model.Recipe;
import org.springframework.data.repository.CrudRepository;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {

}
