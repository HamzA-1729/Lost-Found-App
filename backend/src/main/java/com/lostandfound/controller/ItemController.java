package com.lostandfound.controller;

import com.lostandfound.model.Item;
import com.lostandfound.repository.ItemRepository;
import com.lostandfound.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private ItemService itemService;

    // Report a lost item
    @PostMapping("/report-lost")
    public ResponseEntity<String> reportLostItem(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("location") String location) {

        Item item = new Item();
        item.setName(name);
        item.setDescription(description);
        item.setLocation(location);
        item.setStatus("LOST");

        itemRepository.save(item);

        return new ResponseEntity<>("Lost item reported successfully!", HttpStatus.CREATED);
    }

    // Report a found item
    @PostMapping("/report-found")
    public ResponseEntity<String> reportFoundItem(
            @RequestParam("name") String name,
            @RequestParam("description") String description,
            @RequestParam("location") String location,
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {

        Item item = new Item();
        item.setName(name);
        item.setDescription(description);
        item.setLocation(location);
        item.setStatus("FOUND");

        // Handle image upload if provided
        if (image != null) {
            String imageUrl = saveImage(image);
            item.setImageUrl(imageUrl);
        }

        itemRepository.save(item);

        return new ResponseEntity<>("Found item reported successfully!", HttpStatus.CREATED);
    }

    // Save the image and return the URL
    private String saveImage(MultipartFile image) throws IOException {
        String fileName = StringUtils.cleanPath(image.getOriginalFilename());
        Path path = Paths.get("uploads/" + fileName);
        Files.copy(image.getInputStream(), path);
        return "/uploads/" + fileName; // Returning the URL path
    }

    // Search for items (by name, description, or location)
    @GetMapping("/search")
    public ResponseEntity<List<Item>> searchItems(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location) {
        List<Item> items = itemService.searchItems(name, location);
        return ResponseEntity.ok(items);
    }

}
