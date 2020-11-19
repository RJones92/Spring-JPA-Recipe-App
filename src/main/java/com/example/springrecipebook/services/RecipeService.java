package com.example.springrecipebook.services;

import com.example.springrecipebook.model.Recipe;

import java.util.Set;

public interface RecipeService {

    Set<Recipe> getRecipes();
}
