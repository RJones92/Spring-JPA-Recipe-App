package com.example.springrecipebook.mappers;

import com.example.springrecipebook.commands.CategoryCommand;
import com.example.springrecipebook.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    CategoryCommand categoryToCategoryCommand(Category category);

    @Mapping(target = "recipes", ignore = true)
    Category categoryCommandToCategory(CategoryCommand categoryCommand);
}
