package com.lostandfound.service; // This service class handles the business logic for managing Items, including searching.

import com.lostandfound.model.Item; // Importing the Item model to be used in the service class.
import com.lostandfound.repository.ItemRepository; // Importing the ItemRepository to interact with the Item data in MongoDB.
import org.springframework.beans.factory.annotation.Autowired; // Importing the Autowired annotation for dependency injection.
import org.springframework.stereotype.Service; // Importing the Service annotation to mark this class as a service component.

import java.util.List; // Importing List to handle collections of Item objects.

@Service // Marks the class as a Spring service component to be managed by the Spring
         // container.
public class ItemService {

    @Autowired // Automatically injects the ItemRepository dependency into this class.
    private ItemRepository repository;

    // Method to search for items based on search text and category.
    public List<Item> searchItems(String searchText, String category) {
        // Calls the customSearch method from the repository to find matching items.
        return repository.customSearch(searchText, category);
    }
}
