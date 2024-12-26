import React, { useState, useEffect } from "react";
import "../styles/SearchItemsPage.css";

// Helper function to map hex color to a human-readable color name (you can extend this as needed)
const hexToColorName = (hex) => {
  const colorNames = {
    "#FFFFFF": "White",
    "#000000": "Black",
    "#FF0000": "Red",
    "#00FF00": "Green",
    "#0000FF": "Blue",
    "#FFFF00": "Yellow",
    "#FF00FF": "Magenta",
    "#00FFFF": "Cyan",
    "#808080": "Gray",
  };

  return colorNames[hex.toUpperCase()] || hex; // Default to hex if not found in map
};

const SearchItemsPage = () => {
  const [filters, setFilters] = useState({
    searchText: "",
    category: "",
  });
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [categories] = useState([
    "Personal Items",
    "Electronics",
    "Clothing",
    "Jewelry & Accessories",
    "Documents & Cards",
    "Bags & Luggage",
    "Pets",
    "Vehicles",
    "Miscellaneous",
  ]);

  useEffect(() => {
    if (filters.searchText || filters.category) {
      handleSearch();
    }
  }, [filters]);

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFilters((prev) => ({ ...prev, [name]: value }));
  };

  const handleSearch = async () => {
    setLoading(true);
    setError(""); // Reset any previous error

    try {
      const queryParams = new URLSearchParams(filters).toString();
      const response = await fetch(
        `http://localhost:8080/api/items/search?${queryParams}`
      );

      if (!response.ok) {
        throw new Error("Failed to fetch results");
      }

      const data = await response.json();
      setResults(data);
    } catch (err) {
      setError("Error fetching search results. Please try again.");
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="search-items-page">
      <h2>Search for Lost or Found Items</h2>
      <form className="search-form">
        <input
          type="text"
          name="searchText"
          placeholder="Search by name, description, or location"
          value={filters.searchText}
          onChange={handleInputChange}
        />
        <select
          name="category"
          value={filters.category}
          onChange={handleInputChange}
        >
          <option value="">Select Category</option>
          {categories.map((category) => (
            <option key={category} value={category}>
              {category}
            </option>
          ))}
        </select>
      </form>

      {loading && <p>Loading...</p>}
      {error && <p className="error-message">{error}</p>}

      <div className="search-results">
        {results.length > 0 ? (
          <div className="results-grid">
            {results.map((item) => (
              <div key={item.id} className="result-card">
                <h3>{item.name}</h3>
                <p>{item.description}</p>
                <p>
                  <strong>Location:</strong> {item.location}
                </p>
                <p>
                  <strong>Status:</strong> {item.status}
                </p>
                <p>
                  <strong>Category:</strong> {item.category || "Uncategorized"}
                </p>
                <p>
                  <strong>Color:</strong>{" "}
                  {item.color ? hexToColorName(item.color) : "N/A"}
                </p>
                <p>
                  <strong>Reported On:</strong>{" "}
                  {new Date(item.dateTime).toLocaleString()}
                </p>

                {/* Display Pet-specific details */}
                {item.category === "Pets" && (
                  <>
                    <p>
                      <strong>Pet Type:</strong> {item.petType}
                    </p>
                    <p>
                      <strong>Pet Age:</strong> {item.petAge}
                    </p>
                  </>
                )}

                {/* Display Vehicle-specific details */}
                {item.category === "Vehicles" && (
                  <>
                    <p>
                      <strong>Vehicle Type:</strong> {item.vehicleType}
                    </p>
                    <p>
                      <strong>Make:</strong> {item.vehicleMake}
                    </p>
                    <p>
                      <strong>Model:</strong> {item.vehicleModel}
                    </p>
                    <p>
                      <strong>Vehicle Number:</strong> {item.vehicleNumber}
                    </p>
                  </>
                )}

                {/* Display image if available */}
                {item.imageUrl && (
                  <div>
                    <img
                      src={item.imageUrl}
                      alt={item.name}
                      className="item-image"
                      style={{ maxWidth: "200px", maxHeight: "200px" }}
                    />
                  </div>
                )}
              </div>
            ))}
          </div>
        ) : (
          !loading && <p>No results found.</p>
        )}
      </div>
    </div>
  );
};

export default SearchItemsPage;
