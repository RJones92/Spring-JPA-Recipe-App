package com.example.springrecipebook.mappers;

import com.example.springrecipebook.commands.UnitOfMeasureCommand;
import com.example.springrecipebook.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

class UnitOfMeasureMapperTest {
    public static final Long ID_VALUE = 1L;
    public static final String UNIT_NAME = "teaspoon";

    UnitOfMeasureMapper mapper;

    @BeforeEach
    void setUp() {
        mapper = Mappers.getMapper(UnitOfMeasureMapper.class);
    }

    @Test
    void testEmptyUnitOfMeasureCommand() {
        assertNotNull(mapper.unitOfMeasureCommandToUnitOfMeasure(new UnitOfMeasureCommand()));
    }

    @Test
    void testEmptyUnitOfMeasure() {
        assertNotNull(mapper.unitOfMeasureToUnitOfMeasureCommand(new UnitOfMeasure()));
    }

    @Test
    void testNullObject_unitOfMeasureToUnitOfMeasureCommand() {
        assertNull(mapper.unitOfMeasureToUnitOfMeasureCommand(null));
    }

    @Test
    void testNullObject_unitOfMeasureCommandToUnitOfMeasure() {
        assertNull(mapper.unitOfMeasureCommandToUnitOfMeasure(null));
    }

    @Test
    void unitOfMeasureToUnitOfMeasureCommand() {
        UnitOfMeasure unitOfMeasure = new UnitOfMeasure();
        unitOfMeasure.setUnitName(UNIT_NAME);
        unitOfMeasure.setId(ID_VALUE);

        UnitOfMeasureCommand unitOfMeasureCommand = mapper.unitOfMeasureToUnitOfMeasureCommand(unitOfMeasure);

        assertThat(unitOfMeasureCommand.getId(), equalTo(ID_VALUE));
        assertThat(unitOfMeasureCommand.getUnitName(), equalTo(UNIT_NAME));
    }

    @Test
    void unitOfMeasureCommandToUnitOfMeasure() {
        UnitOfMeasureCommand unitOfMeasureCommand = new UnitOfMeasureCommand();
        unitOfMeasureCommand.setUnitName(UNIT_NAME);
        unitOfMeasureCommand.setId(ID_VALUE);

        UnitOfMeasure unitOfMeasure = mapper.unitOfMeasureCommandToUnitOfMeasure(unitOfMeasureCommand);

        assertThat(unitOfMeasure.getId(), equalTo(ID_VALUE));
        assertThat(unitOfMeasure.getUnitName(), equalTo(UNIT_NAME));
    }
}