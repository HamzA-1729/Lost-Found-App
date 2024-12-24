import React, { useState } from "react";
import { useNavigate } from "react-router-dom"; // Import useNavigate for redirection
import "../styles/ReportItemForm.css";

const ReportFoundItemPage = () => {
  const [itemName, setItemName] = useState("");
  const [itemDescription, setItemDescription] = useState("");
  const [itemLocation, setItemLocation] = useState("");
  const [itemImage, setItemImage] = useState(null);
  const [statusMessage, setStatusMessage] = useState("");
  const [isSuccess, setIsSuccess] = useState(false);
  const navigate = useNavigate(); // Hook to manage redirection

  const handleImageChange = (e) => {
    setItemImage(e.target.files[0]);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    // Create form data object
    const formData = new FormData();
    formData.append("name", itemName);
    formData.append("description", itemDescription);
    formData.append("location", itemLocation);
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
        setIsSuccess(true); // Set success flag to true
        // Clear the form after successful submission
        setItemName("");
        setItemDescription("");
        setItemLocation("");
        setItemImage(null);

        // After a delay, redirect to the home page
        setTimeout(() => {
          navigate("/"); // Redirect to home page
        }, 2000); // 2-second delay before redirection
      } else {
        const message = await response.text();
        setStatusMessage(message);
      }
    } catch (error) {
      console.error("Error reporting found item:", error);
      setStatusMessage("Error reporting found item.");
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
