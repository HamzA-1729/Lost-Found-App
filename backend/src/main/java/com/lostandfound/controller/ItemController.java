package com.lostandfound.controller; // This controller handles the endpoints for reporting lost and found items, image upload, and searching items.

import com.lostandfound.model.Item; // Importing the Item model to interact with item data.
import com.lostandfound.repository.ItemRepository; // Importing the ItemRepository to interact with the database.
import com.lostandfound.service.ItemService; // Importing the ItemService to handle the business logic for items.
import org.springframework.beans.factory.annotation.Autowired; // Importing the Autowired annotation for dependency injection.
import org.springframework.http.HttpStatus; // Importing the HttpStatus class for setting HTTP response statuses.
import org.springframework.http.ResponseEntity; // Importing the ResponseEntity class to return HTTP responses.
import org.springframework.util.StringUtils; // Importing StringUtils for file name handling.
import org.springframework.web.bind.annotation.*; // Importing Spring's web annotation to handle HTTP requests.
import org.springframework.web.multipart.MultipartFile; // Importing MultipartFile for handling file uploads.

import java.io.IOException; // Importing IOException to handle file input/output exceptions.
import java.nio.file.Files; // Importing Files to handle file system operations.
import java.nio.file.Path; // Importing Path to work with file paths.
import java.nio.file.Paths; // Importing Paths for creating file paths.
import java.time.LocalDateTime; // Importing LocalDateTime for handling date and time.
import java.util.List; // Importing List to handle collections of items.

@RestController // Marks this class as a REST controller, meaning it will handle HTTP requests
                // and return data.
@RequestMapping("/api/items") // Defines the base URL path for all the endpoints in this controller.
public class ItemController { // Defines the ItemController class to handle HTTP requests for items.

    @Autowired // Injects the ItemRepository bean into this controller.
    private ItemRepository itemRepository;

    @Autowired // Injects the ItemService bean into this controller.
    private ItemService itemService;

    // Report a lost item
    @PostMapping("/report-lost") // Handles HTTP POST requests for reporting a lost item.
    public ResponseEntity<String> reportLostItem( // Method that handles reporting a lost item and returns a response.
            @RequestParam("name") String name, // Captures the name of the lost item.
            @RequestParam("description") String description, // Captures the description of the lost item.
            @RequestParam("location") String location, // Captures the location where the item was lost.
            @RequestParam("category") String category, // Captures the category of the item.
            @RequestParam(value = "color", required = false) String color, // Captures the color of the item (optional).
            @RequestParam(value = "petType", required = false) String petType, // Captures the pet type if category is
                                                                               // "pets" (optional).
            @RequestParam(value = "petAge", required = false) String petAge, // Captures the pet age if category is
                                                                             // "pets" (optional).
            @RequestParam(value = "vehicleType", required = false) String vehicleType, // Captures the vehicle type if
                                                                                       // category is "vehicles"
                                                                                       // (optional).
            @RequestParam(value = "vehicleMake", required = false) String vehicleMake, // Captures the vehicle make if
                                                                                       // category is "vehicles"
                                                                                       // (optional).
            @RequestParam(value = "vehicleModel", required = false) String vehicleModel, // Captures the vehicle model
                                                                                         // if category is "vehicles"
                                                                                         // (optional).
            @RequestParam(value = "vehicleNumber", required = false) String vehicleNumber, // Captures the vehicle
                                                                                           // number if category is
                                                                                           // "vehicles" (optional).
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException { // Handles the
                                                                                                       // uploaded image
                                                                                                       // of the lost
                                                                                                       // item
                                                                                                       // (optional).

        Item item = new Item(); // Creates a new Item object to store the lost item details.
        item.setName(name); // Sets the name of the lost item.
        item.setDescription(description); // Sets the description of the lost item.
        item.setLocation(location); // Sets the location where the item was lost.
        item.setStatus("LOST"); // Sets the status of the item as "LOST".
        item.setCategory(category); // Sets the category of the item.
        item.setColor(color); // Sets the color of the item (optional).
        item.setDateTime(LocalDateTime.now()); // Sets the current date and time as the time when the item was reported
                                               // lost.

        // Handle image upload if provided
        if (image != null) { // Checks if an image was uploaded.
            String imageUrl = saveImage(image); // Calls the saveImage method to save the uploaded image and get its
                                                // URL.
            item.setImageUrl(imageUrl); // Sets the image URL of the item.
        }

        // If category is pets, set pet-specific fields
        if ("pets".equalsIgnoreCase(category)) { // Checks if the category is "pets".
            item.setPetType(petType); // Sets the pet type if the item is a pet.
            item.setPetAge(petAge); // Sets the pet age if the item is a pet.
        }

        // If category is vehicles, set vehicle-specific fields
        if ("vehicles".equalsIgnoreCase(category)) { // Checks if the category is "vehicles".
            item.setVehicleType(vehicleType); // Sets the vehicle type if the item is a vehicle.
            item.setVehicleMake(vehicleMake); // Sets the vehicle make if the item is a vehicle.
            item.setVehicleModel(vehicleModel); // Sets the vehicle model if the item is a vehicle.
            item.setVehicleNumber(vehicleNumber); // Sets the vehicle number if the item is a vehicle.
        }

        itemRepository.save(item); // Saves the item to the database.

        return new ResponseEntity<>("Lost item reported successfully!", HttpStatus.CREATED); // Returns a success
                                                                                             // response with HTTP
                                                                                             // status 201.
    }

    // Report a found item
    @PostMapping("/report-found") // Handles HTTP POST requests for reporting a found item.
    public ResponseEntity<String> reportFoundItem( // Method that handles reporting a found item and returns a response.
            @RequestParam("name") String name, // Captures the name of the found item.
            @RequestParam("description") String description, // Captures the description of the found item.
            @RequestParam("location") String location, // Captures the location where the item was found.
            @RequestParam("category") String category, // Captures the category of the item.
            @RequestParam(value = "color", required = false) String color, // Captures the color of the item (optional).
            @RequestParam(value = "petType", required = false) String petType, // Captures the pet type if category is
                                                                               // "pets" (optional).
            @RequestParam(value = "petAge", required = false) String petAge, // Captures the pet age if category is
                                                                             // "pets" (optional).
            @RequestParam(value = "vehicleType", required = false) String vehicleType, // Captures the vehicle type if
                                                                                       // category is "vehicles"
                                                                                       // (optional).
            @RequestParam(value = "vehicleMake", required = false) String vehicleMake, // Captures the vehicle make if
                                                                                       // category is "vehicles"
                                                                                       // (optional).
            @RequestParam(value = "vehicleModel", required = false) String vehicleModel, // Captures the vehicle model
                                                                                         // if category is "vehicles"
                                                                                         // (optional).
            @RequestParam(value = "vehicleNumber", required = false) String vehicleNumber, // Captures the vehicle
                                                                                           // number if category is
                                                                                           // "vehicles" (optional).
            @RequestParam(value = "image", required = false) MultipartFile image) throws IOException { // Handles the
                                                                                                       // uploaded image
                                                                                                       // of the found
                                                                                                       // item
                                                                                                       // (optional).

        Item item = new Item(); // Creates a new Item object to store the found item details.
        item.setName(name); // Sets the name of the found item.
        item.setDescription(description); // Sets the description of the found item.
        item.setLocation(location); // Sets the location where the item was found.
        item.setStatus("FOUND"); // Sets the status of the item as "FOUND".
        item.setCategory(category); // Sets the category of the item.
        item.setColor(color); // Sets the color of the item (optional).
        item.setDateTime(LocalDateTime.now()); // Sets the current date and time as the time when the item was reported
                                               // found.

        // Handle image upload if provided
        if (image != null) { // Checks if an image was uploaded.
            String imageUrl = saveImage(image); // Calls the saveImage method to save the uploaded image and get its
                                                // URL.
            item.setImageUrl(imageUrl); // Sets the image URL of the item.
        }

        // If category is pets, set pet-specific fields
        if ("pets".equalsIgnoreCase(category)) { // Checks if the category is "pets".
            item.setPetType(petType); // Sets the pet type if the item is a pet.
            item.setPetAge(petAge); // Sets the pet age if the item is a pet.
        }

        // If category is vehicles, set vehicle-specific fields
        if ("vehicles".equalsIgnoreCase(category)) { // Checks if the category is "vehicles".
            item.setVehicleType(vehicleType); // Sets the vehicle type if the item is a vehicle.
            item.setVehicleMake(vehicleMake); // Sets the vehicle make if the item is a vehicle.
            item.setVehicleModel(vehicleModel); // Sets the vehicle model if the item is a vehicle.
            item.setVehicleNumber(vehicleNumber); // Sets the vehicle number if the item is a vehicle.
        }

        itemRepository.save(item); // Saves the item to the database.

        return new ResponseEntity<>("Found item reported successfully!", HttpStatus.CREATED); // Returns a success
                                                                                              // response with HTTP
                                                                                              // status 201.
    }

    // Save the image and return the URL
    private String saveImage(MultipartFile image) throws IOException { // Method that handles saving the uploaded image
                                                                       // and returning its URL.
        String originalFileName = StringUtils.cleanPath(image.getOriginalFilename()); // Gets the original filename of
                                                                                      // the image and cleans the path.

        // Generate a unique file name
        String uniqueFileName = System.currentTimeMillis() + "_" + originalFileName; // Generates a unique filename
                                                                                     // using the current time and the
                                                                                     // original filename.

        // Define the upload directory
        Path uploadDir = Paths.get("uploads"); // Defines the path to the "uploads" directory.

        // Ensure the directory exists
        if (!Files.exists(uploadDir)) { // Checks if the directory does not exist.
            Files.createDirectories(uploadDir); // Creates the directory if it does not exist.
        }

        // Construct the file path
        Path filePath = uploadDir.resolve(uniqueFileName); // Resolves the file path by combining the upload directory
                                                           // with the unique filename.

        // Save the file
        Files.copy(image.getInputStream(), filePath); // Copies the image file to the specified path.

        return "/uploads/" + uniqueFileName; // Returns the URL path of the uploaded image.
    }

    // Updated search endpoint
    @GetMapping("/search") // Handles HTTP GET requests for searching items.
    public List<Item> searchItems( // Method that handles searching items based on query parameters.
            @RequestParam(required = false) String searchText, // Captures the search text (optional).
            @RequestParam(required = false) String category) { // Captures the category (optional).

        return itemService.searchItems(searchText, category); // Calls the searchItems method from the ItemService to
                                                              // retrieve the search results.
    }
}
