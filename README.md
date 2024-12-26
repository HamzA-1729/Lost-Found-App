Lost and Found App
Overview
The Lost and Found App is a web application that allows users to report lost and found items, search for items, and view detailed information about the reported items. The app is built using React for the frontend, Spring Boot for the backend, and MongoDB as the database to store item data.

Key Features:
Report Lost Items: Users can report lost items by providing details such as name, description, location, category, and an optional image.
Report Found Items: Users can report found items with similar details to help others reclaim their lost belongings.
Search Items: Users can search for lost or found items by name, description, or location.
Filter by Category: Items can be filtered by category (e.g., pets, vehicles, electronics).
Tech Stack
Frontend: React.js
Backend: Spring Boot
Database: MongoDB
Image Upload: Local file system (uploads directory)
Requirements
Backend (Spring Boot)
Java 17 (or higher)
Maven or Gradle for dependency management
MongoDB (Local or Cloud-based)
Frontend (React)
Node.js (version 16 or higher)
npm or yarn for package management
Setup and Installation

1. Clone the Repository
   bash
   Copy code
   git clone https://github.com/yourusername/lost-and-found-app.git
   cd lost-and-found-app
2. Backend Setup (Spring Boot)
   Step 1: Configure MongoDB
   Make sure MongoDB is running locally or set up a cloud MongoDB instance. Update the application.properties file to configure the MongoDB URI:

properties
Copy code

# src/main/resources/application.properties

spring.data.mongodb.uri=mongodb://localhost:27017/lostandfounddb
server.port=8080
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
Step 2: Run the Spring Boot Application
Navigate to the backend directory and build/run the Spring Boot application.

bash
Copy code
cd backend
./mvnw spring-boot:run
The backend server will run on http://localhost:8080.

3. Frontend Setup (React)
   Step 1: Install Dependencies
   Navigate to the frontend directory and install the required dependencies.

bash
Copy code
cd frontend
npm install
Step 2: Run the React Development Server
bash
Copy code
npm start
The frontend will run on http://localhost:3000.

4. CORS Configuration
   Ensure that the frontend React app running on localhost:3000 can communicate with the backend API running on localhost:8080. This is handled by the WebConfig class in the backend, which allows CORS requests from localhost:3000.

java
Copy code
// WebConfig.java (backend)
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // Allow CORS requests from the frontend (React app running on localhost:3000)
        registry.addMapping("/api/**").allowedOrigins("http://localhost:3000");
    }

}
API Endpoints

1. Report Lost Item
   URL: /api/items/report-lost
   Method: POST
   Description: Allows users to report a lost item.
   Parameters:
   name: Name of the item
   description: Description of the item
   location: Location where the item was lost
   category: Category of the item (e.g., pets, vehicles)
   color: Color of the item (optional)
   petType: Type of pet (if category is pets)
   petAge: Age of the pet (if category is pets)
   vehicleType: Type of vehicle (if category is vehicles)
   vehicleMake: Vehicle make (if category is vehicles)
   vehicleModel: Vehicle model (if category is vehicles)
   vehicleNumber: Vehicle number (if category is vehicles)
   image: Image of the lost item (optional)
2. Report Found Item
   URL: /api/items/report-found
   Method: POST
   Description: Allows users to report a found item.
   Parameters: Same as the report-lost endpoint.
3. Search Items
   URL: /api/items/search
   Method: GET
   Description: Allows users to search for lost or found items based on the searchText (name, description, location) and category.
   Parameters:
   searchText: Text to search for in the item’s name, description, or location (optional)
   category: Category of the item (optional)
   Example Search Request:
   bash
   Copy code
   GET /api/items/search?searchText=dog&category=pets
   File Upload
   The app supports uploading images for the lost and found items. The images are stored in a local uploads directory. The image file is saved with a unique name to avoid conflicts and is referenced via a URL that can be accessed by the frontend.

Folder Structure
bash
Copy code
/lost-and-found-app
/backend # Spring Boot backend
/src
/main
/java
/com
/lostandfound
/controller # API controllers
/model # MongoDB data models (Item, etc.)
/repository # Database interfaces and implementations
/service # Business logic and services
/config # Application configuration
/resources
application.properties # Configuration file for Spring Boot
/frontend # React frontend
/src
/components # Reusable React components (Header, Footer, etc.)
/pages # Page components (HomePage, ReportPage, etc.)
/styles # CSS/SCSS files
App.js # Main React component
index.js # React entry point
.gitignore # Git ignore file
README.md # Project documentation (this file)
package.json # Node.js package manager configuration
pom.xml # Maven configuration for Spring Boot
Contributing
Feel free to fork the repository and submit pull requests if you have improvements or fixes to contribute.

License
This project is licensed under the MIT License - see the LICENSE file for details.
