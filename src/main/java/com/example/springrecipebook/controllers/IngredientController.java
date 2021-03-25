package com.example.springrecipebook.controllers;

import com.example.springrecipebook.commands.IngredientCommand;
import com.example.springrecipebook.services.IngredientService;
import com.example.springrecipebook.services.RecipeService;
import com.example.springrecipebook.services.UomService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/recipe/{recipeId}")
public class IngredientController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UomService uomService;

    public IngredientController(RecipeService recipeService, IngredientService ingredientService,
                                UomService uomService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.uomService = uomService;
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

    @GetMapping
    @RequestMapping("/ingredient/{ingredientId}/update")
    public String updateRecipeIngredient(Model model, @PathVariable String recipeId, @PathVariable String ingredientId) {
        model.addAttribute("ingredient",
                ingredientService.getCommandByIngredientIdAndRecipeId(
                        Long.valueOf(ingredientId),
                        Long.valueOf(recipeId)));

        model.addAttribute("uomList", uomService.getAllUnitOfMeasures());

        return "recipe/ingredient/ingredientForm";
    }

    @PostMapping
    @RequestMapping("/ingredient")
    public String postRecipeIngredient(@ModelAttribute IngredientCommand ingredientCommand) {
        IngredientCommand savedCommand = ingredientService.saveIngredientCommand(ingredientCommand);

        log.debug("Saved ingredient id: {}", savedCommand.getId());
        log.debug("Saved Recipe Id: {}", savedCommand.getRecipeId());
        return "redirect:/recipe/" + savedCommand.getRecipeId() + "/ingredient/" + savedCommand.getId() + "/show";
    }

}
