import React, { useState, useEffect } from "react";
import "../styles/SearchItemsPage.css";

const SearchItemsPage = () => {
  const [filters, setFilters] = useState({
    searchText: "",
    category: "",
  });
  const [results, setResults] = useState([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");
  const [categories, setCategories] = useState([
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
    if (filters.searchText) {
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
                  <strong>Category:</strong> {item.category || "Uncategorized"}
                </p>
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
