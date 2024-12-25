package com.lostandfound.controller;

import com.lostandfound.model.Item;
import com.lostandfound.repository.ItemRepository;
import com.lostandfound.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
            @RequestParam("location") String location,
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {

        Item item = new Item();
        item.setName(name);
        item.setDescription(description);
        item.setLocation(location);
        item.setStatus("LOST");

        // Handle image upload if provided
        if (image != null) {
            String imageUrl = saveImage(image);
            item.setImageUrl(imageUrl);
        }

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
        if (image != null && !image.isEmpty()) {
            String imageUrl = saveImage(image);
            item.setImageUrl(imageUrl);
        }

        itemRepository.save(item);

        return new ResponseEntity<>("Found item reported successfully!", HttpStatus.CREATED);
    }

    // Save the image and return the URL
    private String saveImage(MultipartFile image) throws IOException {
        String originalFileName = StringUtils.cleanPath(image.getOriginalFilename());

        // Generate a unique file name
        String uniqueFileName = System.currentTimeMillis() + "_" + originalFileName;

        // Define the upload directory
        Path uploadDir = Paths.get("uploads");

        // Ensure the directory exists
        if (!Files.exists(uploadDir)) {
            Files.createDirectories(uploadDir);
        }

        // Construct the file path
        Path filePath = uploadDir.resolve(uniqueFileName);

        // Save the file
        Files.copy(image.getInputStream(), filePath);

        return "/uploads/" + uniqueFileName;
    }

    // Updated search endpoint
    @GetMapping("/search")
    public List<Item> searchItems(
            @RequestParam(required = false) String searchText,
            @RequestParam(required = false) String category) {

        return itemService.searchItems(searchText, category);
    }
}
