package com.lostandfound.repository;

import com.lostandfound.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ItemRepository extends MongoRepository<Item, String>, CustomItemRepository {
    // Default methods can still be used if needed
}
