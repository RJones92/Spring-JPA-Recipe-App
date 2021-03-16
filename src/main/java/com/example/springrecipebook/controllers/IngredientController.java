package com.example.springrecipebook.controllers;

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

    public IngredientController(RecipeService recipeService){
        this.recipeService = recipeService;
    }

    @GetMapping
    @RequestMapping("/ingredients")
    public String getIngredientsByRecipeId(Model model, @PathVariable String recipeId) {
        log.debug("Getting ingredients list for recipe id: {}", recipeId);

        model.addAttribute("recipe", recipeService.getCommandById(Long.valueOf(recipeId)));

        return "recipe/ingredient/list";
    }

}
