import React, { useState } from "react";
import { useNavigate } from "react-router-dom"; // Import useNavigate for redirection
import "../styles/ReportItemForm.css";

const ReportFoundItemPage = () => {
  const [itemName, setItemName] = useState("");
  const [itemDescription, setItemDescription] = useState("");
  const [itemLocation, setItemLocation] = useState("");
  const [statusMessage, setStatusMessage] = useState("");
  const [isSuccess, setIsSuccess] = useState(false);
  const navigate = useNavigate(); // Hook to manage redirection

  const handleSubmit = async (e) => {
    e.preventDefault();

    const formData = new FormData();
    formData.append("name", itemName);
    formData.append("description", itemDescription);
    formData.append("location", itemLocation);

    try {
      const response = await fetch(
        "http://localhost:8080/api/items/report-lost",
        {
          method: "POST",
          body: formData,
        }
      );

      if (response.status === 201) {
        setStatusMessage("Lost item reported successfully!");
        setIsSuccess(true); // Set success flag to true
        // After a delay, redirect to the home page
        setTimeout(() => {
          navigate("/"); // Redirect to home page
        }, 2000); // 2-second delay before redirection
      } else {
        const message = await response.text();
        setStatusMessage(message);
      }
    } catch (error) {
      console.error("Error reporting lost item:", error);
      setStatusMessage("Error reporting lost item.");
    }
  };

  return (
    <div className="report-item-form">
      <h2>Report a Lost Item</h2>
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
        <button type="submit" className="submit-button">
          Report Lost Item
        </button>
      </form>
    </div>
  );
};

export default ReportFoundItemPage;
