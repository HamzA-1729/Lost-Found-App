package com.lostandfound.repository; // This interface defines custom repository methods for searching items in the system.

import com.lostandfound.model.Item; // Importing the Item model class to work with Item objects.
import java.util.List; // Importing the List class to handle a collection of items.

public interface CustomItemRepository { // Defines a custom repository interface for handling item search operations.
    List<Item> customSearch(String searchText, String category); // Method signature for searching items by searchText
                                                                 // and category.
}
