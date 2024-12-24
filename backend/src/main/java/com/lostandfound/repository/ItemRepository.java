package com.lostandfound.repository;

import com.lostandfound.model.Item;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ItemRepository extends MongoRepository<Item, String> {
    List<Item> findByNameContainingIgnoreCase(String name);

    List<Item> findByLocationContainingIgnoreCase(String location);

    List<Item> findByNameContainingIgnoreCaseAndLocationContainingIgnoreCase(String name, String location);
}
