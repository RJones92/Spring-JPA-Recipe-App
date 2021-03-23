package com.example.springrecipebook.services;

import com.example.springrecipebook.Repositories.UnitOfMeasureRepository;
import com.example.springrecipebook.commands.UnitOfMeasureCommand;
import com.example.springrecipebook.mappers.UnitOfMeasureMapperImpl;
import com.example.springrecipebook.model.UnitOfMeasure;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UomServiceImplTest {

    @Mock
    UnitOfMeasureRepository uomRepository;

    UomService uomService;

    @BeforeEach
    void setUp() {
        uomService = new UomServiceImpl(uomRepository, new UnitOfMeasureMapperImpl());
    }

    @Test
    void testGetAllUnitOfMeasures() {
        //GIVEN
        Long id1 = 1L;
        Long id2 = 2L;

        UnitOfMeasure uom1 = new UnitOfMeasure();
        uom1.setId(id1);
        UnitOfMeasure uom2 = new UnitOfMeasure();
        uom2.setId(id2);

        Iterable<UnitOfMeasure> uoms = new HashSet<>(Arrays.asList(uom1, uom2));

        when(uomRepository.findAll()).thenReturn(uoms);

        //WHEN
        Set<UnitOfMeasureCommand> result = uomService.getAllUnitOfMeasures();

        //THEN
        assertEquals(2, result.size());
        verify(uomRepository, times(1)).findAll();
    }
}