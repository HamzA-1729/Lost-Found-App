import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/ReportItemForm.css";

const ReportFoundItemPage = () => {
  const [itemName, setItemName] = useState("");
  const [itemDescription, setItemDescription] = useState("");
  const [itemLocation, setItemLocation] = useState("");
  const [itemImage, setItemImage] = useState(null);
  const [category, setCategory] = useState(""); // Category selection
  const [color, setColor] = useState(""); // General color picker
  const [date, setDate] = useState(""); // Date selection
  const [time, setTime] = useState(""); // Time selection
  const [petType, setPetType] = useState("");
  const [petDescription, setPetDescription] = useState("");
  const [petLocation, setPetLocation] = useState("");
  const [petContactInfo, setPetContactInfo] = useState("");
  const [vehicleType, setVehicleType] = useState("");
  const [vehicleMake, setVehicleMake] = useState("");
  const [vehicleModel, setVehicleModel] = useState("");
  const [vehicleNumber, setVehicleNumber] = useState("");
  const [statusMessage, setStatusMessage] = useState("");
  const [isSuccess, setIsSuccess] = useState(false);
  const navigate = useNavigate();

  const handleImageChange = (e) => {
    setItemImage(e.target.files[0]);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const formData = new FormData();
    formData.append("name", itemName);
    formData.append("description", itemDescription);
    formData.append("location", itemLocation);
    formData.append("category", category);
    formData.append("color", color);
    formData.append("date", date);
    formData.append("time", time);

    // Handle category-specific fields
    if (category === "Pets") {
      formData.append("petType", petType);
      formData.append("petDescription", petDescription);
      formData.append("petLocation", petLocation);
      formData.append("petContactInfo", petContactInfo);
    } else if (category === "Vehicles") {
      formData.append("vehicleType", vehicleType);
      formData.append("vehicleMake", vehicleMake);
      formData.append("vehicleModel", vehicleModel);
      formData.append("vehicleNumber", vehicleNumber);
    }

    // Handle image upload if provided
    if (itemImage) {
      formData.append("image", itemImage);
    }

    try {
      const response = await fetch(
        "http://localhost:8080/api/items/report-found",
        {
          method: "POST",
          body: formData,
        }
      );

      if (response.status === 201) {
        setStatusMessage("Found item reported successfully!");
        setIsSuccess(true);

        // Reset form
        setItemName("");
        setItemDescription("");
        setItemLocation("");
        setItemImage(null);
        setCategory("");
        setColor("");
        setDate("");
        setTime("");
        setPetType("");
        setPetDescription("");
        setPetLocation("");
        setPetContactInfo("");
        setVehicleType("");
        setVehicleMake("");
        setVehicleModel("");
        setVehicleNumber("");

        setTimeout(() => {
          navigate("/"); // Redirect to home page
        }, 2000);
      } else {
        const errorMessage = await response.text();
        setStatusMessage(errorMessage);
        setIsSuccess(false);
      }
    } catch (error) {
      console.error("Error reporting found item:", error);
      setStatusMessage("Error reporting found item.");
      setIsSuccess(false);
    }
  };

  return (
    <div className="report-item-form">
      <h2>Report a Found Item</h2>
      {statusMessage && (
        <div
          className={
            isSuccess ? "status-message success" : "status-message error"
          }
        >
          {statusMessage}
        </div>
      )}
      <form onSubmit={handleSubmit} encType="multipart/form-data">
        <label>
          Item Name:
          <input
            type="text"
            value={itemName}
            onChange={(e) => setItemName(e.target.value)}
            required
          />
        </label>
        <label>
          Item Description:
          <textarea
            value={itemDescription}
            onChange={(e) => setItemDescription(e.target.value)}
            required
          />
        </label>
        <label>
          Location:
          <input
            type="text"
            value={itemLocation}
            onChange={(e) => setItemLocation(e.target.value)}
            required
          />
        </label>
        <label>
          Date:
          <input
            type="date"
            value={date}
            onChange={(e) => setDate(e.target.value)}
            required
          />
        </label>
        <label>
          Time:
          <input
            type="time"
            value={time}
            onChange={(e) => setTime(e.target.value)}
            required
          />
        </label>
        <label>
          Category:
          <select
            value={category}
            onChange={(e) => setCategory(e.target.value)}
            required
          >
            <option value="">Select Category</option>
            <option value="Personal Items">Personal Items</option>
            <option value="Electronics">Electronics</option>
            <option value="Clothing">Clothing</option>
            <option value="Jewelry & Accessories">Jewelry & Accessories</option>
            <option value="Documents & Cards">Documents & Cards</option>
            <option value="Bags & Luggage">Bags & Luggage</option>
            <option value="Pets">Pets</option>
            <option value="Vehicles">Vehicles</option>
            <option value="Miscellaneous">Miscellaneous</option>
          </select>
        </label>
        <label>
          Color:
          <input
            type="color"
            value={color}
            onChange={(e) => setColor(e.target.value)}
            required
          />
        </label>

        {/* Category-specific fields */}
        {category === "Pets" && (
          <>
            <label>
              Pet Type:
              <input
                type="text"
                value={petType}
                onChange={(e) => setPetType(e.target.value)}
              />
            </label>
            <label>
              Pet Description:
              <textarea
                value={petDescription}
                onChange={(e) => setPetDescription(e.target.value)}
              />
            </label>
            <label>
              Pet Location:
              <input
                type="text"
                value={petLocation}
                onChange={(e) => setPetLocation(e.target.value)}
              />
            </label>
            <label>
              Pet Contact Info:
              <input
                type="text"
                value={petContactInfo}
                onChange={(e) => setPetContactInfo(e.target.value)}
              />
            </label>
          </>
        )}

        {category === "Vehicles" && (
          <>
            <label>
              Vehicle Type:
              <input
                type="text"
                value={vehicleType}
                onChange={(e) => setVehicleType(e.target.value)}
              />
            </label>
            <label>
              Vehicle Make:
              <input
                type="text"
                value={vehicleMake}
                onChange={(e) => setVehicleMake(e.target.value)}
              />
            </label>
            <label>
              Vehicle Model:
              <input
                type="text"
                value={vehicleModel}
                onChange={(e) => setVehicleModel(e.target.value)}
              />
            </label>
            <label>
              Vehicle Number:
              <input
                type="text"
                value={vehicleNumber}
                onChange={(e) => setVehicleNumber(e.target.value)}
              />
            </label>
          </>
        )}

        <label>
          Upload Picture (Optional):
          <input type="file" accept="image/*" onChange={handleImageChange} />
        </label>

        <button type="submit" className="submit-button">
          Report Item
        </button>
      </form>
    </div>
  );
};

export default ReportFoundItemPage;
