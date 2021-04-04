package com.example.springrecipebook.mappers;

import com.example.springrecipebook.commands.RecipeCommand;
import com.example.springrecipebook.model.Recipe;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RecipeMapper {

    RecipeCommand recipeToRecipeCommand(Recipe recipe);

    Recipe recipeCommandToRecipe(RecipeCommand recipeCommand);
}
