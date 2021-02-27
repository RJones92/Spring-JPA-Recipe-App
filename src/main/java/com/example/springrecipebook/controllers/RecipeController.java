package com.example.springrecipebook.controllers;

import com.example.springrecipebook.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class RecipeController {

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping("/show/{recipeId}")
    public String getRecipeById(Model model, @PathVariable Long recipeId) {
        log.info("Getting individual recipe page");

        model.addAttribute("recipe", recipeService.getRecipeById(recipeId));

        log.info("Added recipe data to the model.");
        log.info("Now sending recipe to Thymeleaf");
        return "recipe/show";
    }
}
