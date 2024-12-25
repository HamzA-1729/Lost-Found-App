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
            @RequestParam("category") String category,
            @RequestParam("color") String color,
            @RequestParam("date") String date, // New date field
            @RequestParam("time") String time, // New time field
            @RequestParam(value = "petType", required = false) String petType,
            @RequestParam(value = "petDescription", required = false) String petDescription,
            @RequestParam(value = "vehicleType", required = false) String vehicleType,
            @RequestParam(value = "vehicleMake", required = false) String vehicleMake,
            @RequestParam(value = "vehicleModel", required = false) String vehicleModel,
            @RequestParam(value = "vehicleNumber", required = false) String vehicleNumber,
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {

        Item item = new Item();
        item.setName(name);
        item.setDescription(description);
        item.setLocation(location);
        item.setStatus("LOST");
        item.setCategory(category);
        item.setColor(color);
        item.setDate(date); // Set the date
        item.setTime(time); // Set the time

        // Set category-specific fields
        if (category.equals("Pets")) {
            item.setPetType(petType);
            item.setPetDescription(petDescription);
        } else if (category.equals("Vehicles")) {
            item.setVehicleType(vehicleType);
            item.setVehicleMake(vehicleMake);
            item.setVehicleModel(vehicleModel);
            item.setVehicleNumber(vehicleNumber);
        }

        // Handle image upload if provided
        if (image != null && !image.isEmpty()) {
            String imageUrl = saveImage(image);
            item.setImageUrl(imageUrl);
        }

        itemRepository.save(item);

        return new ResponseEntity<>("Lost item reported successfully!", HttpStatus.CREATED);
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

    // Search for items (by name, location, category)
    @GetMapping("/search")
    public ResponseEntity<List<Item>> searchItems(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String category) {
        List<Item> items = itemService.searchItems(name, location, category);
        return ResponseEntity.ok(items);
    }
}
