package com.example.springrecipebook.services;

import com.example.springrecipebook.Repositories.RecipeRepository;
import com.example.springrecipebook.Repositories.UnitOfMeasureRepository;
import com.example.springrecipebook.commands.IngredientCommand;
import com.example.springrecipebook.mappers.IngredientMapper;
import com.example.springrecipebook.model.Ingredient;
import com.example.springrecipebook.model.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Slf4j
public class IngredientServiceImpl implements IngredientService {

    private final RecipeRepository recipeRepository;
    private final IngredientMapper ingredientMapper;
    private final UnitOfMeasureRepository uomRepository;

    public IngredientServiceImpl(RecipeRepository recipeRepository, IngredientMapper ingredientMapper,
                                 UnitOfMeasureRepository uomRepository) {
        this.recipeRepository = recipeRepository;
        this.ingredientMapper = ingredientMapper;
        this.uomRepository = uomRepository;
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

    @Transactional
    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand ingredientCommand) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(ingredientCommand.getRecipeId());

        if (recipeOptional.isEmpty()) {
            log.debug("Recipe Id not found. Id: {}", ingredientCommand.getRecipeId());
            //TODO handle error if not found
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();
            Optional<Ingredient> ingredientToUpdate = findIngredientFromRecipe(recipe, ingredientCommand);

            if (ingredientToUpdate.isPresent()) {
                updateExistingIngredientInRecipe(ingredientToUpdate.get(), ingredientCommand);
            } else {
                addNewIngredientToRecipe(ingredientCommand, recipe);
            }

            Recipe savedRecipe = recipeRepository.save(recipe);
            //TODO check for fail

            Ingredient savedIngredient = findIngredientFromRecipe(savedRecipe, ingredientCommand).get();

            return ingredientMapper.ingredientToIngredientCommand(savedIngredient);
        }
    }

    private Optional<Ingredient> findIngredientFromRecipe(Recipe recipeToSearch, IngredientCommand ingredientToFind) {
        Optional<Ingredient> foundIngredient = recipeToSearch.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientToFind.getId()))
                .findFirst();

        if (foundIngredient.isEmpty()) {
            foundIngredient = findIngredientFromRecipeUsingFieldsOtherThanId(recipeToSearch, ingredientToFind);
        }

        return foundIngredient;
    }

    private Optional<Ingredient> findIngredientFromRecipeUsingFieldsOtherThanId(Recipe recipeToSearch, IngredientCommand ingredientToFind) {
        return recipeToSearch.getIngredients().stream()
                .filter(ingredient -> ingredient.getDescription().equals(ingredientToFind.getDescription()))
                .filter(ingredient -> ingredient.getAmount().equals(ingredientToFind.getAmount()))
                .filter(ingredient -> ingredient.getUom().getId().equals(ingredientToFind.getUom().getId()))
                .findFirst();
    }

    private void updateExistingIngredientInRecipe(Ingredient ingredientToUpdate, IngredientCommand ingredientToTakeFieldsFrom) {
        ingredientToUpdate.setDescription(ingredientToTakeFieldsFrom.getDescription());
        ingredientToUpdate.setAmount(ingredientToTakeFieldsFrom.getAmount());
        ingredientToUpdate.setUom(uomRepository
                .findById(ingredientToTakeFieldsFrom.getUom().getId())
                .orElseThrow(() -> new RuntimeException("UOM not found.")));
    }

    private void addNewIngredientToRecipe(IngredientCommand newIngredient, Recipe recipe) {
        Ingredient ingredient = ingredientMapper.ingredientCommandToIngredient(newIngredient);
        ingredient.setRecipe(recipe);
        recipe.addIngredient(ingredient);
    }
}
