package com.lostandfound.model; // This class represents an item in the system, which can be either lost or found, and stores its details.

import org.springframework.data.annotation.Id; // Importing the Id annotation to designate the ID field in the MongoDB collection.
import org.springframework.data.mongodb.core.mapping.Document; // Importing the Document annotation to define the MongoDB collection.
import java.time.LocalDateTime; // Importing LocalDateTime to handle the date and time of the item report.

@Document(collection = "items") // Marks this class as a MongoDB document, storing items in the "items"
                                // collection.
public class Item { // Defines the Item class, representing an item in the system.

    @Id // Marks the field as the identifier (primary key) for the MongoDB document.
    private String id; // Field to store the unique identifier of the item.
    private String name; // Field to store the name of the item.
    private String description; // Field to store a description of the item.
    private String location; // Field to store the location where the item was found or lost.
    private String status; // Status: "LOST" or "FOUND", indicating the current status of the item.
    private String imageUrl; // URL of the uploaded image, if any, associated with the item.
    private String category; // General category of the item (e.g., "pets", "vehicles", "electronics").
    private String color; // General color field for all categories of items.
    private LocalDateTime dateTime; // Date and time when the item was reported as lost or found.

    // Fields specific to Pets
    private String petType; // Type of pet (e.g., Dog, Cat), applicable when the category is "pets".
    private String petAge; // Age of the pet, applicable when the category is "pets".

    // Fields specific to Vehicles
    private String vehicleType; // Type of vehicle (e.g., Car, Bike, Motorcycle), applicable when the category
                                // is "vehicles".
    private String vehicleMake; // Make of the vehicle, applicable when the category is "vehicles".
    private String vehicleModel; // Model of the vehicle, applicable when the category is "vehicles".
    private String vehicleNumber; // Vehicle registration number, applicable when the category is "vehicles".

    // Getters and Setters for all fields

    public String getId() { // Getter for the item's ID.
        return id;
    }

    public void setId(String id) { // Setter for the item's ID.
        this.id = id;
    }

    public String getName() { // Getter for the item's name.
        return name;
    }

    public void setName(String name) { // Setter for the item's name.
        this.name = name;
    }

    public String getDescription() { // Getter for the item's description.
        return description;
    }

    public void setDescription(String description) { // Setter for the item's description.
        this.description = description;
    }

    public String getLocation() { // Getter for the item's location.
        return location;
    }

    public void setLocation(String location) { // Setter for the item's location.
        this.location = location;
    }

    public String getStatus() { // Getter for the item's status.
        return status;
    }

    public void setStatus(String status) { // Setter for the item's status.
        this.status = status;
    }

    public String getImageUrl() { // Getter for the item's image URL.
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) { // Setter for the item's image URL.
        this.imageUrl = imageUrl;
    }

    public String getCategory() { // Getter for the item's category.
        return category;
    }

    public void setCategory(String category) { // Setter for the item's category.
        this.category = category;
    }

    public String getColor() { // Getter for the item's color.
        return color;
    }

    public void setColor(String color) { // Setter for the item's color.
        this.color = color;
    }

    public LocalDateTime getDateTime() { // Getter for the item's date and time of report.
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) { // Setter for the item's date and time of report.
        this.dateTime = dateTime;
    }

    // Pet-related fields
    public String getPetType() { // Getter for the pet type.
        return petType;
    }

    public void setPetType(String petType) { // Setter for the pet type.
        this.petType = petType;
    }

    public String getPetAge() { // Getter for the pet age.
        return petAge;
    }

    public void setPetAge(String petAge) { // Setter for the pet age.
        this.petAge = petAge;
    }

    // Vehicle-related fields
    public String getVehicleType() { // Getter for the vehicle type.
        return vehicleType;
    }

    public void setVehicleType(String vehicleType) { // Setter for the vehicle type.
        this.vehicleType = vehicleType;
    }

    public String getVehicleMake() { // Getter for the vehicle make.
        return vehicleMake;
    }

    public void setVehicleMake(String vehicleMake) { // Setter for the vehicle make.
        this.vehicleMake = vehicleMake;
    }

    public String getVehicleModel() { // Getter for the vehicle model.
        return vehicleModel;
    }

    public void setVehicleModel(String vehicleModel) { // Setter for the vehicle model.
        this.vehicleModel = vehicleModel;
    }

    public String getVehicleNumber() { // Getter for the vehicle number.
        return vehicleNumber;
    }

    public void setVehicleNumber(String vehicleNumber) { // Setter for the vehicle number.
        this.vehicleNumber = vehicleNumber;
    }
}
