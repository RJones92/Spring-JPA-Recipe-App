package com.example.springrecipebook.services;

import com.example.springrecipebook.commands.UnitOfMeasureCommand;

import java.util.Set;

public interface UomService {

    Set<UnitOfMeasureCommand> getAllUnitOfMeasures();
}
