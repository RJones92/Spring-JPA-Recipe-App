package com.example.springrecipebook.mappers;

import com.example.springrecipebook.commands.IngredientCommand;
import com.example.springrecipebook.model.Ingredient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IngredientMapper {

    @Mapping(target = "recipeId", source = "ingredient.recipe.id")
    IngredientCommand ingredientToIngredientCommand(Ingredient ingredient);

    @Mapping(target = "recipe", ignore = true)
    Ingredient ingredientCommandToIngredient(IngredientCommand ingredientCommand);
}
