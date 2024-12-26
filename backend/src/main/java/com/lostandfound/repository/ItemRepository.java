package com.lostandfound.repository; // This interface defines the repository for interacting with the Item collection in MongoDB.

import com.lostandfound.model.Item; // Importing the Item model class to be used in the repository.
import org.springframework.data.mongodb.repository.MongoRepository; // Importing the MongoRepository to inherit CRUD operations.

public interface ItemRepository extends MongoRepository<Item, String>, CustomItemRepository {
    // Inherits basic CRUD operations from MongoRepository for the Item entity
    // (using 'String' as the ID type)
    // and extends CustomItemRepository to include custom search methods.

    // Default methods from MongoRepository and CustomItemRepository can be used
    // without needing to implement them.
}
