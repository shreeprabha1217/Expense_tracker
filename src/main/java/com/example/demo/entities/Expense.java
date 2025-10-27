package com.example.demo.entities;

import java.time.LocalDate;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;


import jakarta.persistence.Id;
import lombok.Data;

@Document(collection = "expenses")
@Data
public class Expense {
    
    @Id
    private ObjectId id;

    private String purpose;
    private Double amount;
    private String item;
    private LocalDate date;
    private String category;
    private String userId; 

    public String getId() {
        return id != null ? id.toHexString() : null;
    }
}
