package com.example.demo.services;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Category;
import com.example.demo.entities.Expense;
import com.example.demo.events.ExpenseProducer;
import com.example.demo.repositories.CategoryRepository;
import com.example.demo.repositories.ExpenseRepository;
import com.example.demo.repositories.UserRepository;


@Service
public class ExpenseService {
    
  @Autowired
  private ExpenseRepository expenseRepository;

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private CategoryRepository categoryRepository;

  @Autowired
  private ExpenseProducer expenseProducer;

  public Expense addExpense(Expense expense) {
        if(expense.getPurpose()==null){
            throw new RuntimeException("Purpose cannot be empty");
        }
        if(expense.getAmount()==null){
            throw new RuntimeException("Amount cannot be empty");
        } else if(expense.getAmount()<0){
            throw new RuntimeException("Amount should be greater than zero");
        }
        if(expense.getItem()==null){
            throw new RuntimeException("Item cannot be empty");
        }
        if(expense.getDate()==null){
            throw new RuntimeException("Date cannot be empty");
        } else if(expense.getDate().isAfter(LocalDate.now())){
            throw new RuntimeException("Expense date cannot be future date");
        }
        if(expense.getUserId()==null){
            throw new RuntimeException("UserID cannot be empty");
        }
        if(expense.getUserId()!=null){
              ObjectId userObjId = new ObjectId(expense.getUserId());
    if (!userRepository.existsById(userObjId)) {
        throw new IllegalArgumentException("Invalid userId: user does not exist");
    }
        }
        if (expense.getCategory() != null) {
            Optional<Category> cateString = categoryRepository.findByNameIgnoreCase(expense.getCategory());
        
            if (cateString.isPresent()) {
                // Normalize to the stored category name (avoid case mismatch)
                expense.setCategory(cateString.get().getName());
            } else {
                throw new RuntimeException("Category does not exist");
            }
        }
        Expense saved= expenseRepository.save(expense);
        expenseProducer.sendExpense(saved);
        return saved;
    }

  public List<Expense> getExpense(){
    return expenseRepository.findAll();
  }
  public Optional<Expense> getExpenseById(String id) {
    ObjectId objId;
    try {
        objId = new ObjectId(id);
    } catch (IllegalArgumentException e) {
        // invalid id format
        return Optional.empty();
    }
    return expenseRepository.findById(objId);
}

public void deleteExpense(String id) {
    ObjectId objId;
    try {
        objId = new ObjectId(id);
    } catch (IllegalArgumentException e) {
        throw new IllegalArgumentException("Invalid expense id");
    }
    expenseRepository.deleteById(objId);
}
}
