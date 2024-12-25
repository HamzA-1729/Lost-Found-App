import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import "../styles/ReportItemForm.css";

const ReportFoundItemPage = () => {
  const [itemName, setItemName] = useState("");
  const [itemDescription, setItemDescription] = useState("");
  const [itemLocation, setItemLocation] = useState("");
  const [itemImage, setItemImage] = useState(null);
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

        setTimeout(() => {
          navigate("/");
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
