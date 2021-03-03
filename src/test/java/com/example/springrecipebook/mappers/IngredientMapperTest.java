package com.example.springrecipebook.mappers;

import com.example.springrecipebook.commands.IngredientCommand;
import com.example.springrecipebook.model.Ingredient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class IngredientMapperTest {
    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "description";

    IngredientMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(IngredientMapper.class);
    }
    
    @Test
    void testEmptyIngredientCommand() {
        assertNotNull(mapper.ingredientCommandToIngredient(new IngredientCommand()));
    }

    @Test
    void testEmptyIngredient() {
        assertNotNull(mapper.ingredientToIngredientCommand(new Ingredient()));
    }

    @Test
    void testNullObject_ingredientToIngredientCommand() {
        assertNull(mapper.ingredientToIngredientCommand(null));
    }

    @Test
    void testNullObject_ingredientCommandToIngredient() {
        assertNull(mapper.ingredientCommandToIngredient(null));
    }

    @Test
    void ingredientToIngredientCommand() {
        Ingredient ingredient = new Ingredient();
        ingredient.setDescription(DESCRIPTION);
        ingredient.setId(ID_VALUE);

        IngredientCommand ingredientCommand = mapper.ingredientToIngredientCommand(ingredient);

        assertThat(ingredientCommand.getId(), equalTo(ID_VALUE));
        assertThat(ingredientCommand.getDescription(), equalTo(DESCRIPTION));
    }

    @Test
    void ingredientCommandToIngredient() {
        IngredientCommand ingredientCommand = new IngredientCommand();
        ingredientCommand.setDescription(DESCRIPTION);
        ingredientCommand.setId(ID_VALUE);

        Ingredient ingredient = mapper.ingredientCommandToIngredient(ingredientCommand);

        assertThat(ingredient.getId(), equalTo(ID_VALUE));
        assertThat(ingredient.getDescription(), equalTo(DESCRIPTION));
        assertNull(ingredient.getRecipe());
    }
}