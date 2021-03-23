package com.example.springrecipebook.services;

import com.example.springrecipebook.Repositories.RecipeRepository;
import com.example.springrecipebook.commands.IngredientCommand;
import com.example.springrecipebook.mappers.IngredientMapperImpl;
import com.example.springrecipebook.model.Ingredient;
import com.example.springrecipebook.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class IngredientServiceImplTest {

    IngredientServiceImpl ingredientService;

    @Mock
    RecipeRepository recipeRepository;

    @BeforeEach
    void setUp() {
        ingredientService = new IngredientServiceImpl(recipeRepository, new IngredientMapperImpl());
    }

    @Test
    void getCommandByIngredientIdAndRecipeId() {
        //GIVEN
        Long recipeId = 1L;
        Long ingredientId = 2L;

        Recipe recipe = new Recipe();
        recipe.setId(recipeId);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(ingredientId);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(2L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(recipeId)).thenReturn(recipeOptional);

        //WHEN
        IngredientCommand ingredientCommand = ingredientService.getCommandByIngredientIdAndRecipeId(ingredientId, recipeId);

        //THEN
        assertEquals(ingredientId, ingredientCommand.getId());
        assertEquals(recipeId, ingredientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());
    }
}