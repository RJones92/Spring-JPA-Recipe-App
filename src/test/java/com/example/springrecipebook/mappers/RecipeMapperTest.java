package com.example.springrecipebook.mappers;

import com.example.springrecipebook.commands.CategoryCommand;
import com.example.springrecipebook.commands.IngredientCommand;
import com.example.springrecipebook.commands.NotesCommand;
import com.example.springrecipebook.commands.RecipeCommand;
import com.example.springrecipebook.enums.Difficulty;
import com.example.springrecipebook.model.Recipe;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class RecipeMapperTest {
    public static final String DESCRIPTION = "description";
    public static final Long RECIPE_ID = 1L;
    public static final Integer COOK_TIME = Integer.valueOf("5");
    public static final Integer PREP_TIME = Integer.valueOf("7");
    public static final String DIRECTIONS = "Directions";
    public static final Difficulty DIFFICULTY = Difficulty.EASY;
    public static final Integer SERVINGS = Integer.valueOf("3");
    public static final String SOURCE = "Source";
    public static final String URL = "Some URL";
    public static final Long CATEGORY_ID_1 = 1L;
    public static final Long CATEGORY_ID_2 = 2L;
    public static final Long INGREDIENT_ID_1 = 3L;
    public static final Long INGREDIENT_ID_2 = 4L;
    public static final Long NOTES_ID = 9L;

    RecipeMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(RecipeMapper.class);
    }

    @Test
    void testEmptyRecipeCommand() throws Exception {
        assertNotNull(mapper.recipeCommandToRecipe(new RecipeCommand()));
    }

    @Test
    void testEmptyRecipe() {
        assertNotNull(mapper.recipeToRecipeCommand(new Recipe()));
    }

    @Test
    void testNullObject_recipeToRecipeCommand() {
        assertNull(mapper.recipeToRecipeCommand(null));
    }

    @Test
    void testNullObject_recipeCommandToRecipe() {
        assertNull(mapper.recipeCommandToRecipe(null));
    }

    @Test
    void recipeToRecipeCommand() {
        Recipe recipe = new Recipe();
        recipe.setDescription(DESCRIPTION);
        recipe.setId(RECIPE_ID);

        RecipeCommand recipeCommand = mapper.recipeToRecipeCommand(recipe);

        assertThat(recipeCommand.getId(), equalTo(RECIPE_ID));
        assertThat(recipeCommand.getDescription(), equalTo(DESCRIPTION));
        assertThat(recipeCommand.getIngredients(), hasSize(0));
    }

    @Test
    void recipeCommandToRecipe() {

        RecipeCommand recipeCommand = createRecipeCommand();

        Recipe recipe = mapper.recipeCommandToRecipe(recipeCommand);

        assertThat(recipe.getId(), equalTo(RECIPE_ID));
        assertThat(recipe.getDescription(), equalTo(DESCRIPTION));
        assertThat(recipe.getIngredients(), hasSize(2));
        assertThat(recipe.getCategories(), hasSize(2));
        assertNull(recipe.getImage());
    }

    private RecipeCommand createRecipeCommand() {
        RecipeCommand recipeCommand = new RecipeCommand();
        recipeCommand.setId(RECIPE_ID);
        recipeCommand.setDescription(DESCRIPTION);
        recipeCommand.setPrepTime(PREP_TIME);
        recipeCommand.setCookTime(COOK_TIME);
        recipeCommand.setServings(SERVINGS);
        recipeCommand.setSource(SOURCE);
        recipeCommand.setUrl(URL);
        recipeCommand.setDifficulty(DIFFICULTY);
        recipeCommand.setDirections(DIRECTIONS);

        NotesCommand notesCommand = new NotesCommand();
        notesCommand.setId(NOTES_ID);

        IngredientCommand ingredientCommand1 = new IngredientCommand();
        ingredientCommand1.setId(INGREDIENT_ID_1);

        IngredientCommand ingredientCommand2 = new IngredientCommand();
        ingredientCommand2.setId(INGREDIENT_ID_2);

        CategoryCommand categoryCommand1 = new CategoryCommand();
        categoryCommand1.setId(CATEGORY_ID_1);

        CategoryCommand categoryCommand2 = new CategoryCommand();
        categoryCommand2.setId(CATEGORY_ID_2);

        recipeCommand.setNotes(notesCommand);
        recipeCommand.getIngredients().addAll(Arrays.asList(
                ingredientCommand1,
                ingredientCommand2));
        recipeCommand.getIngredients().add(ingredientCommand2);
        recipeCommand.getCategories().addAll(Arrays.asList(
                categoryCommand1,
                categoryCommand2));

        return recipeCommand;
    }
}