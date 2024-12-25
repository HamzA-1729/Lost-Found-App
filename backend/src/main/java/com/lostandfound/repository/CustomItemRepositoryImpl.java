package com.lostandfound.repository;

import com.lostandfound.model.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CustomItemRepositoryImpl implements CustomItemRepository {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Override
    public List<Item> customSearch(String searchText, String category) {
        Query query = new Query();

        // Add criteria for search text (name, description, location)
        if (searchText != null && !searchText.isEmpty()) {
            Criteria searchCriteria = new Criteria().orOperator(
                    Criteria.where("name").regex(searchText, "i"), // Case-insensitive
                    Criteria.where("description").regex(searchText, "i"),
                    Criteria.where("location").regex(searchText, "i"));
            query.addCriteria(searchCriteria);
        }

        // Add criteria for category
        if (category != null && !category.isEmpty()) {
            query.addCriteria(Criteria.where("category").is(category));
        }

        return mongoTemplate.find(query, Item.class);
    }
}
