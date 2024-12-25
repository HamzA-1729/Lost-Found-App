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

    public List<Item> searchItems(String name, String location, String category) {
        if (name != null && location != null) {
            return repository.findByNameContainingIgnoreCaseAndLocationContainingIgnoreCase(name, location);
        } else if (name != null) {
            return repository.findByNameContainingIgnoreCase(name);
        } else if (location != null) {
            return repository.findByLocationContainingIgnoreCase(location);
        } else {
            return repository.findAll();
        }
    }
}
