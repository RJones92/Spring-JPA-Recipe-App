package com.example.springrecipebook.services;

import com.example.springrecipebook.Repositories.RecipeRepository;
import com.example.springrecipebook.commands.RecipeCommand;
import com.example.springrecipebook.mappers.RecipeMapper;
import com.example.springrecipebook.model.Recipe;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

@SpringBootTest
public class RecipeServiceImplIT {
    public static final String NEW_DESCRIPTION = "New description";

    @Autowired
    RecipeService recipeService;

    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    RecipeMapper recipeMapper;

    @Transactional
    @Test
    public void testSaveOfDescription() throws Exception {
        //given
        Recipe testRecipe = recipeRepository.findAll().iterator().next();
        RecipeCommand testRecipeCommand = recipeMapper.recipeToRecipeCommand(testRecipe);

        //when
        testRecipeCommand.setDescription(NEW_DESCRIPTION);
        RecipeCommand savedRecipeCommand = recipeService.saveRecipeCommand(testRecipeCommand);

        //then
        assertThat(savedRecipeCommand.getDescription(), equalTo(NEW_DESCRIPTION));
        assertThat(savedRecipeCommand.getId(), equalTo(testRecipe.getId()));
        assertThat(savedRecipeCommand.getCategories(), hasSize(testRecipe.getCategories().size()));
        assertThat(savedRecipeCommand.getIngredients(), hasSize(testRecipe.getIngredients().size()));
    }
}
