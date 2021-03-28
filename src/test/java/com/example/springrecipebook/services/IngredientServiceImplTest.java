package com.example.springrecipebook.services;

import com.example.springrecipebook.Repositories.RecipeRepository;
import com.example.springrecipebook.Repositories.UnitOfMeasureRepository;
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
    public static final Long RECIPE_ID = 1L;
    public static final Long INGREDIENT_ID = 2L;

    IngredientServiceImpl ingredientService;

    @Mock
    RecipeRepository recipeRepository;
    @Mock
    UnitOfMeasureRepository uomRepository;

    @BeforeEach
    void setUp() {
        ingredientService = new IngredientServiceImpl(recipeRepository, new IngredientMapperImpl(), uomRepository);
    }

    @Test
    void getCommandByIngredientIdAndRecipeId() {
        //GIVEN
        Recipe recipe = setUpRecipeWithThreeIngredients();

        Optional<Recipe> recipeOptional = Optional.of(recipe);

        when(recipeRepository.findById(RECIPE_ID)).thenReturn(recipeOptional);

        //WHEN
        IngredientCommand ingredientCommand = ingredientService.getCommandByIngredientIdAndRecipeId(INGREDIENT_ID, RECIPE_ID);

        //THEN
        assertEquals(INGREDIENT_ID, ingredientCommand.getId());
        assertEquals(RECIPE_ID, ingredientCommand.getRecipeId());
        verify(recipeRepository, times(1)).findById(anyLong());
    }

    @Test
    void testSaveRecipeCommand() throws Exception {
        //GIVEN
        IngredientCommand command = new IngredientCommand();
        command.setId(INGREDIENT_ID);
        command.setRecipeId(RECIPE_ID);

        Recipe savedRecipe = setUpRecipeWithThreeIngredients();

        when(recipeRepository.findById(anyLong())).thenReturn(Optional.of(new Recipe()));
        when(recipeRepository.save(any())).thenReturn(savedRecipe);

        //WHEN
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(command);

        //THEN
        assertEquals(INGREDIENT_ID, savedCommand.getId());
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any());

    }

    @Test
    void testDeleteIngredient() throws Exception {
        //GIVEN
        Recipe recipe = setUpRecipeWithThreeIngredients();
        when(recipeRepository.findById(RECIPE_ID)).thenReturn(Optional.of(recipe));

        //WHEN
        ingredientService.deleteIngredient(RECIPE_ID, INGREDIENT_ID);

        //THEN
        verify(recipeRepository, times(1)).findById(anyLong());
        verify(recipeRepository, times(1)).save(any(Recipe.class));
    }

    private Recipe setUpRecipeWithThreeIngredients() {
        Recipe recipe = new Recipe();
        recipe.setId(RECIPE_ID);

        Ingredient ingredient1 = new Ingredient();
        ingredient1.setId(1L);

        Ingredient ingredient2 = new Ingredient();
        ingredient2.setId(INGREDIENT_ID);

        Ingredient ingredient3 = new Ingredient();
        ingredient3.setId(3L);

        recipe.addIngredient(ingredient1);
        recipe.addIngredient(ingredient2);
        recipe.addIngredient(ingredient3);

        return recipe;
    }
}