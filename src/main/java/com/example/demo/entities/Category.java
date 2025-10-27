package com.example.demo.entities;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.persistence.Id;
import lombok.Data;

@Document(collection ="categories")
@Data
public class Category {
    @Id
    private ObjectId id;
    private String name;
    private String description;

    public String getId() {
        return id != null ? id.toHexString() : null;
    }
}
