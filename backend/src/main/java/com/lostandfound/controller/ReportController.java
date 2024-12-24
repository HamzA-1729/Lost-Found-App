package com.lostandfound.controller;

import com.lostandfound.model.Item;
import com.lostandfound.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ReportController {

    @Autowired
    private ItemRepository itemRepository;

    // Report Lost Item
    @PostMapping("/report-lost")
    public ResponseEntity<String> reportLostItem(@RequestBody Item item) {
        // Check if the item is already reported as found
        Optional<Item> existingItem = itemRepository.findByNameAndStatus(item.getName(), "found");

        if (existingItem.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Item is already reported as found.");
        }

        // Set the status of the item as 'lost'
        item.setStatus("lost");
        itemRepository.save(item);
        return ResponseEntity.status(HttpStatus.CREATED).body("Lost item reported successfully!");
    }

    // Report Found Item
    @PostMapping("/report-found")
    public ResponseEntity<String> reportFoundItem(@RequestBody Item item) {
        // Check if the item is already reported as lost
        Optional<Item> existingItem = itemRepository.findByNameAndStatus(item.getName(), "lost");

        if (existingItem.isPresent()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Item is already reported as lost.");
        }

        // Set the status of the item as 'found'
        item.setStatus("found");
        itemRepository.save(item);
        return ResponseEntity.status(HttpStatus.CREATED).body("Found item reported successfully!");
    }

    // Search for Lost or Found Items by Name
    @GetMapping("/search")
    public ResponseEntity<List<Item>> searchItems(@RequestParam String query) {
        List<Item> foundItems = itemRepository.findByNameContaining(query);
        return ResponseEntity.ok(foundItems);
    }

}
