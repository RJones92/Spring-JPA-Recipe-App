package com.example.springrecipebook.services;

import com.example.springrecipebook.Repositories.UnitOfMeasureRepository;
import com.example.springrecipebook.commands.UnitOfMeasureCommand;
import com.example.springrecipebook.mappers.UnitOfMeasureMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
public class UomServiceImpl implements UomService {
    private final UnitOfMeasureRepository unitOfMeasureRepository;
    private final UnitOfMeasureMapper unitOfMeasureMapper;

    public UomServiceImpl(UnitOfMeasureRepository unitOfMeasureRepository, UnitOfMeasureMapper unitOfMeasureMapper) {
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.unitOfMeasureMapper = unitOfMeasureMapper;
    }

    @Override
    public Set<UnitOfMeasureCommand> getAllUnitOfMeasures() {

        return StreamSupport.stream(unitOfMeasureRepository.findAll().spliterator(), false)
                .map(unitOfMeasureMapper::unitOfMeasureToUnitOfMeasureCommand)
                .collect(Collectors.toSet());
    }
}
