package com.example.demo.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.Expense;
import com.example.demo.services.ExpenseService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/expenses")
@Slf4j
public class ExpenseController {
@Autowired
private ExpenseService expenseService;
@PostMapping("/create")
public ResponseEntity<Expense> addExpense(@RequestBody Expense expense) {
    log.debug("Inside expense controller");
    Expense savedExpense = expenseService.addExpense(expense);
    return ResponseEntity.status(HttpStatus.CREATED).body(savedExpense);
}
@GetMapping("/get")
public ResponseEntity<List<Expense>> getExpenses(){
    List<Expense> expenses=expenseService.getExpense();
        return ResponseEntity.status(HttpStatus.OK).body(expenses);
}

@GetMapping("/{id}")
public ResponseEntity<Expense> getExpenseById(@PathVariable String id) {
    Optional<Expense> expense = expenseService.getExpenseById(id);
    return expense.map(ResponseEntity::ok)  // 200 OK if present
                  .orElse(ResponseEntity.notFound().build()); // 404 if not found
}

@DeleteMapping("/{id}")
public ResponseEntity<Void> deleteExpense(@PathVariable String id) {
    try {
        expenseService.deleteExpense(id);
        return ResponseEntity.noContent().build(); // 204 No Content
    } catch (IllegalArgumentException e) {
        return ResponseEntity.badRequest().build(); // 400 Bad Request for invalid ObjectId
    }
}



    
}
