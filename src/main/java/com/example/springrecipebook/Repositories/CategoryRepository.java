package com.example.springrecipebook.Repositories;

import com.example.springrecipebook.model.Category;
import org.springframework.data.repository.CrudRepository;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
