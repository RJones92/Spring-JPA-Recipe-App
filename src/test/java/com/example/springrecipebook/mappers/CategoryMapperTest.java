package com.example.springrecipebook.mappers;

import com.example.springrecipebook.commands.CategoryCommand;
import com.example.springrecipebook.model.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class CategoryMapperTest {
    public static final Long ID_VALUE = 1L;
    public static final String DESCRIPTION = "description";

    CategoryMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(CategoryMapper.class);
    }

    @Test
    void testEmptyCategoryCommand() {
        assertNotNull(mapper.categoryCommandToCategory(new CategoryCommand()));
    }

    @Test
    void testEmptyCategory() {
        assertNotNull(mapper.categoryToCategoryCommand(new Category()));
    }

    @Test
    void testNullObject_categoryToCategoryCommand() {
        assertNull(mapper.categoryToCategoryCommand(null));
    }

    @Test
    void testNullObject_categoryCommandToCategory() {
        assertNull(mapper.categoryCommandToCategory(null));
    }

    @Test
    void categoryToCategoryCommand() {
        Category category = new Category();
        category.setDescription(DESCRIPTION);
        category.setId(ID_VALUE);

        CategoryCommand categoryCommand = mapper.categoryToCategoryCommand(category);

        assertThat(categoryCommand.getId(), equalTo(ID_VALUE));
        assertThat(categoryCommand.getDescription(), equalTo(DESCRIPTION));
    }

    @Test
    void categoryCommandToCategory() {
        CategoryCommand categoryCommand = new CategoryCommand();
        categoryCommand.setDescription(DESCRIPTION);
        categoryCommand.setId(ID_VALUE);

        Category category = mapper.categoryCommandToCategory(categoryCommand);

        assertThat(category.getId(), equalTo(ID_VALUE));
        assertThat(category.getDescription(), equalTo(DESCRIPTION));
        assertNull(category.getRecipes());
    }
}