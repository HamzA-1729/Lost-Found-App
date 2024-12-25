package com.lostandfound.repository;

import com.lostandfound.model.Item;
import java.util.List;

public interface CustomItemRepository {
    List<Item> customSearch(String searchText, String category);
}
