package com.example.springrecipebook.controllers;

import com.example.springrecipebook.commands.RecipeCommand;
import com.example.springrecipebook.services.RecipeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/recipe")
public class RecipeController {
    private static final String SHOW_RECIPE_PATH = "/show";
    private static final String ADD_NEW_RECIPE_PATH = "/new";
    private static final String UPDATE_RECIPE_PATH = "/update";
    private static final String DELETE_RECIPE_PATH = "/delete";


    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @RequestMapping("/{recipeId}" + SHOW_RECIPE_PATH)
    public String getRecipeById(Model model, @PathVariable String recipeId) {
        model.addAttribute("recipe", recipeService.getRecipeById(Long.valueOf(recipeId)));
        return "recipe/show";
    }

    @GetMapping
    @RequestMapping(ADD_NEW_RECIPE_PATH)
    public String addNewRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());
        return "recipe/recipeForm";
    }

    @GetMapping
    @RequestMapping("/{recipeId}" + UPDATE_RECIPE_PATH)
    public String updateRecipe(Model model, @PathVariable String recipeId) {
        model.addAttribute("recipe", recipeService.getCommandById(Long.valueOf(recipeId)));
        return "recipe/recipeForm";
    }

    @PostMapping
    @RequestMapping("/")
    public String postRecipe(@ModelAttribute RecipeCommand recipeCommand) {
        RecipeCommand savedCommand = recipeService.saveRecipeCommand(recipeCommand);
        return "redirect:/recipe/" + savedCommand.getId() + SHOW_RECIPE_PATH;
    }

    @GetMapping
    @RequestMapping("/{recipeId}" + DELETE_RECIPE_PATH)
    public String deleteRecipe(Model model, @PathVariable String recipeId) {
        recipeService.deleteRecipeById(Long.valueOf(recipeId));
        return "redirect:/index";
    }

}
