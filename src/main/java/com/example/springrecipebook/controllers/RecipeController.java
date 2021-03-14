package com.example.springrecipebook.controllers;

import com.example.springrecipebook.commands.RecipeCommand;
import com.example.springrecipebook.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class RecipeController {
    private static final String SHOW_RECIPE_PATH = "/show/";
    private static final String ADD_NEW_RECIPE_PATH = "/new";

    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @RequestMapping(SHOW_RECIPE_PATH + "/{recipeId}")
    public String getRecipeById(Model model, @PathVariable Long recipeId) {
        log.info("Getting individual recipe page");

        model.addAttribute("recipe", recipeService.getRecipeById(recipeId));

        log.info("Added recipe data to the model.");
        log.info("Now sending recipe to Thymeleaf");
        return "recipe/show";
    }

    @RequestMapping(ADD_NEW_RECIPE_PATH)
    public String addNewRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "recipe/recipeForm";
    }

    @PostMapping
    @RequestMapping("/")
    public String postRecipe(@ModelAttribute RecipeCommand recipeCommand) {
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand);

        return "redirect:/recipe" + SHOW_RECIPE_PATH + savedCommand.getId();
    }

}
