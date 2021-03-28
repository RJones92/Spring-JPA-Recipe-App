package com.example.springrecipebook.controllers;

import com.example.springrecipebook.commands.IngredientCommand;
import com.example.springrecipebook.commands.RecipeCommand;
import com.example.springrecipebook.services.IngredientService;
import com.example.springrecipebook.services.RecipeService;
import com.example.springrecipebook.services.UomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashSet;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
class IngredientControllerTest {

    @Mock
    RecipeService recipeService;
    @Mock
    IngredientService ingredientService;
    @Mock
    UomService uomService;
    @InjectMocks
    IngredientController ingredientController;

    MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(ingredientController).build();
    }

    @Test
    void testGetIngredientsByRecipeId() throws Exception {
        Long recipeId = 1L;
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(recipeId);

        when(recipeService.getRecipeCommandById(recipeId)).thenReturn(recipeCommand);

        mockMvc.perform(get("/recipe/"+ recipeId + "/ingredients"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/list"))
                .andExpect(model().attributeExists("recipe"));

        verify(recipeService, times(1)).getRecipeCommandById(anyLong());
    }

    @Test
    void testShowIngredient() throws Exception {
        IngredientCommand ingredientCommand = new IngredientCommand();

        when(ingredientService.getCommandByIngredientIdAndRecipeId(anyLong(), anyLong())).thenReturn(ingredientCommand);

        mockMvc.perform(get("/recipe/1/ingredient/2/show"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/show"))
                .andExpect(model().attributeExists("ingredient"));
    }

    @Test
    void testUpdateIngredient() throws Exception {
        when(ingredientService.getCommandByIngredientIdAndRecipeId(anyLong(), anyLong())).thenReturn(new IngredientCommand());

        mockMvc.perform(get("/recipe/1/ingredient/2/update"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientForm"))
                .andExpect(model().attributeExists("ingredient", "uomList"));
    }

    @Test
    void testPostIngredient() throws Exception {
        Long ingredientId = 1L;
        Long recipeId = 2L;

        IngredientCommand command = new IngredientCommand();
        command.setId(ingredientId);
        command.setRecipeId(recipeId);

        when(ingredientService.saveIngredientCommand(any())).thenReturn(command);

        mockMvc.perform(post("/recipe/" + recipeId + "/ingredient")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
        )
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/" + recipeId + "/ingredient/" + ingredientId + "/show"));
    }

    @Test
    void testAddingNewRecipeIngredient() throws Exception {
        Long recipeId = 1L;
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(recipeId);

        when(recipeService.getRecipeCommandById(anyLong())).thenReturn(recipeCommand);
        when(uomService.getAllUnitOfMeasures()).thenReturn(new HashSet<>());

        mockMvc.perform(get("/recipe/" + recipeId + "/ingredient/new"))
                .andExpect(status().isOk())
                .andExpect(view().name("recipe/ingredient/ingredientForm"))
                .andExpect(model().attributeExists("ingredient", "uomList"));

        verify(recipeService, times(1)).getRecipeCommandById(anyLong());
        verify(uomService, times(1)).getAllUnitOfMeasures();
    }

    @Test
    void testDeleteIngredient() throws Exception {
        Long recipeId = 1L;
        Long ingredientId = 3L;

        mockMvc.perform(get("/recipe/" + recipeId + "/ingredient/" + ingredientId + "/delete"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/recipe/" + recipeId + "/ingredients"));

        verify(ingredientService, times(1)).deleteIngredient(anyLong(), anyLong());
    }
}