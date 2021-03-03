package com.example.springrecipebook.mappers;

import com.example.springrecipebook.commands.UnitOfMeasureCommand;
import com.example.springrecipebook.model.UnitOfMeasure;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UnitOfMeasureMapper {

    UnitOfMeasureCommand unitOfMeasureToUnitOfMeasureCommand(UnitOfMeasure unitOfMeasure);

    UnitOfMeasure unitOfMeasureCommandToUnitOfMeasure(UnitOfMeasureCommand unitOfMeasureCommand);
}
