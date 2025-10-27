package com.example.demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Category;
import com.example.demo.repositories.CategoryRepository;

@Service
public class CategoryService {
     @Autowired
    private CategoryRepository categoryRepository;

    public Category createCategory(Category category) {
        if(category.getName()==null){
            throw new RuntimeException("Name is required");
        }
        return categoryRepository.save(category);
    }
}
