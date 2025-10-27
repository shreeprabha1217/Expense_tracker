package com.example.demo.controllers;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.entities.CategoryReport;
import com.example.demo.services.ReportService;

@RestController
@RequestMapping("/api/reports")
public class CategoryReportController {
    @Autowired
    private ReportService reportService;

    @GetMapping("/{userId}/category-summary")
    public ResponseEntity<List<CategoryReport>> getCategorySummary(@PathVariable String userId) {
        ObjectId objectId = new ObjectId(userId);
        List<CategoryReport> summary = reportService.getCategorySummary(objectId);
        return ResponseEntity.ok(summary);
    }
}
