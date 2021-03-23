package com.example.springrecipebook.controllers;

import com.example.springrecipebook.services.IngredientService;
import com.example.springrecipebook.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/recipe/{recipeId}")
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService){
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
    }

    @GetMapping
    @RequestMapping("/ingredients")
    public String getIngredientsByRecipeId(Model model, @PathVariable String recipeId) {
        log.debug("Getting ingredients list for recipe id: {}", recipeId);

        model.addAttribute("recipe", recipeService.getCommandById(Long.valueOf(recipeId)));

        return "recipe/ingredient/list";
    }

    @GetMapping
    @RequestMapping("/ingredient/{ingredientId}/show")
    public String showIngredient(Model model, @PathVariable String recipeId, @PathVariable String ingredientId) {
        model.addAttribute("ingredient",
                ingredientService.getCommandByIngredientIdAndRecipeId(
                        Long.valueOf(ingredientId),
                        Long.valueOf(recipeId)));

        return "recipe/ingredient/show";
    }

}
