package com.lostandfound.service;

import com.lostandfound.model.Item;
import com.lostandfound.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private ItemRepository repository;

    public List<Item> searchItems(String searchText, String category) {
        return repository.customSearch(searchText, category);
    }
}
