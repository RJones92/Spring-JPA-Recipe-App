package com.example.springrecipebook.services;

import com.example.springrecipebook.Repositories.RecipeRepository;
import com.example.springrecipebook.commands.IngredientCommand;
import com.example.springrecipebook.mappers.IngredientMapper;
import com.example.springrecipebook.model.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientMapper ingredientMapper;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientMapper ingredientMapper) {
        this.recipeRepository = recipeRepository;
        this.ingredientMapper = ingredientMapper;
    }

    @Override
    public IngredientCommand getCommandByIngredientIdAndRecipeId(Long ingredientId, Long recipeId) {
        Optional<Recipe> recipeOptional = recipeRepository.findById(recipeId);

        Recipe recipe = recipeOptional.orElseThrow(() -> {
            log.debug("Recipe Id not found. Id: {}", recipeId);
            return null;
            //TODO handle error
        });

        Optional<IngredientCommand> ingredientCommandOptional = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredientMapper::ingredientToIngredientCommand)
                .findFirst();

        return ingredientCommandOptional.orElseThrow(() -> {
            log.debug("Ingredient Id not found. Id: {}", ingredientId);
            return null;
            //TODO handle error
            }
        );

    }

}
