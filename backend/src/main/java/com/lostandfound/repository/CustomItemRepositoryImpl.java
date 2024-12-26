package com.lostandfound.repository; // This class implements the custom repository methods for searching items in the MongoDB database.

import com.lostandfound.model.Item; // Importing the Item model class to work with Item objects.
import org.springframework.beans.factory.annotation.Autowired; // Importing the Autowired annotation to inject dependencies.
import org.springframework.data.mongodb.core.MongoTemplate; // Importing MongoTemplate to interact with MongoDB.
import org.springframework.data.mongodb.core.query.Criteria; // Importing Criteria for defining query conditions.
import org.springframework.data.mongodb.core.query.Query; // Importing Query to construct MongoDB queries.
import org.springframework.stereotype.Repository; // Importing the Repository annotation to mark this class as a repository bean.

import java.util.List; // Importing List to work with a collection of items.

@Repository // Marks this class as a repository bean in the Spring context.
public class CustomItemRepositoryImpl implements CustomItemRepository { // Implements the CustomItemRepository
                                                                        // interface.

    @Autowired // Injecting the MongoTemplate bean to interact with MongoDB.
    private MongoTemplate mongoTemplate;

    @Override // Implements the customSearch method from CustomItemRepository interface.
    public List<Item> customSearch(String searchText, String category) {
        Query query = new Query(); // Creates a new Query object to build the MongoDB query.

        // Add criteria for search text (name, description, location)
        if (searchText != null && !searchText.isEmpty()) { // Checks if the searchText is not null or empty.
            // Creates a Criteria object to search in the 'name', 'description', or
            // 'location' fields, case-insensitively.
            Criteria searchCriteria = new Criteria().orOperator(
                    Criteria.where("name").regex(searchText, "i"), // Search for 'name' field, case-insensitive match.
                    Criteria.where("description").regex(searchText, "i"), // Search for 'description' field,
                                                                          // case-insensitive match.
                    Criteria.where("location").regex(searchText, "i") // Search for 'location' field, case-insensitive
                                                                      // match.
            );
            query.addCriteria(searchCriteria); // Adds the search criteria to the query.
        }

        // Add criteria for category
        if (category != null && !category.isEmpty()) { // Checks if the category is not null or empty.
            query.addCriteria(Criteria.where("category").is(category)); // Adds the category filter to the query.
        }

        return mongoTemplate.find(query, Item.class); // Executes the query and returns a list of Item objects.
    }
}
