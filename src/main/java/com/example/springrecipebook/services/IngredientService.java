package com.example.springrecipebook.services;

import com.example.springrecipebook.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand getCommandByIngredientIdAndRecipeId(Long ingredientId, Long recipeId);

    IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand);
}
