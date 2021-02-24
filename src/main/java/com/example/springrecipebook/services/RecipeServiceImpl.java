package com.example.springrecipebook.services;

import com.example.springrecipebook.Repositories.RecipeRepository;
import com.example.springrecipebook.model.Recipe;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Service
public class RecipeServiceImpl implements RecipeService {
    private final RecipeRepository recipeRepository;

    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    public Set<Recipe> getRecipes() {
        log.debug("Getting recipes. In the Recipe service implementation.");
        Set<Recipe> recipes = new HashSet<>();

        recipeRepository.findAll().iterator().forEachRemaining(recipes::add);
        return recipes;
    }

    @Override
    public Recipe getRecipeById(Long id){
        log.debug("Getting recipe for Id {}. In the Recipe service implementation.", id);
        Optional<Recipe> recipeOptional = recipeRepository.findById(id);

        if (recipeOptional.isEmpty()) {
            throw new RuntimeException("No recipe found with id:" + id);
        };

        return recipeOptional.get();

    }

}
