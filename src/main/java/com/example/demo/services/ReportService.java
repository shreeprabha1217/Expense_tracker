package com.example.demo.services;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.GroupOperation;
import org.springframework.data.mongodb.core.aggregation.MatchOperation;
import org.springframework.data.mongodb.core.aggregation.ProjectionOperation;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.stereotype.Service;

import com.example.demo.entities.CategoryReport;

@Service
public class ReportService {
 @Autowired
    private MongoTemplate mongoTemplate;

    public List<CategoryReport> getCategorySummary(ObjectId userId) {
        MatchOperation matchUser = Aggregation.match(Criteria.where("userId").is(userId));

        GroupOperation groupByCategory = Aggregation.group("category")
                .sum("amount").as("totalAmount");

        ProjectionOperation project = Aggregation.project()
                .and("_id").as("category")
                .and("totalAmount").as("totalAmount");

        Aggregation aggregation = Aggregation.newAggregation(matchUser, groupByCategory, project);

        return mongoTemplate.aggregate(aggregation, "expenses", CategoryReport.class).getMappedResults();
    }
    
}
