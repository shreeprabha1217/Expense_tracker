package com.example.demo.events;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import com.example.demo.entities.Expense;

@Service
public class ExpenseProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    @Autowired
    public ExpenseProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendExpense(Expense expense) {
        kafkaTemplate.send("expense-topic", expense);
        System.out.println("✅ Expense event published: " + expense);
    }
}
