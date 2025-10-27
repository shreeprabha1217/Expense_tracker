package com.example.demo.repositories;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.entities.Category;

@Repository
public interface CategoryRepository extends MongoRepository<Category, ObjectId>{
    Optional<Category> findByName(String name);

     // Case-insensitive search by name
     Optional<Category> findByNameIgnoreCase(String name);
    
} 
    

