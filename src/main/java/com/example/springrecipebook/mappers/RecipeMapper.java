package com.example.springrecipebook.mappers;

import com.example.springrecipebook.commands.RecipeCommand;
import com.example.springrecipebook.model.Recipe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RecipeMapper {

    RecipeCommand recipeToRecipeCommand(Recipe recipe);

    @Mapping(target = "image", ignore = true)
    Recipe recipeCommandToRecipe(RecipeCommand recipeCommand);
}
