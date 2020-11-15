package com.example.springrecipebook.controllers;

import com.example.springrecipebook.Repositories.CategoryRepository;
import com.example.springrecipebook.Repositories.UnitOfMeasureRepository;
import com.example.springrecipebook.model.Category;
import com.example.springrecipebook.model.UnitOfMeasure;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
public class IndexController {

    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @RequestMapping({"", "/", "/index"})
    public String getIndexPage(Model model){

        Optional<Category> categoryOptional = categoryRepository.findByDescription("American");
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByUnitName("Litre");

        if (categoryOptional.isPresent()) {
            System.out.println("Cat ID is: " + categoryOptional.get().getId());
        }
        if (unitOfMeasureOptional.isPresent()) {
            System.out.println("UOM ID is: " + unitOfMeasureOptional.get().getId());
        }

        return "index";
    }
}
