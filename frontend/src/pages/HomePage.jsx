import React from "react";
import "../styles/HomePage.css";

const HomePage = () => {
  return (
    <div className="home-page">
      <h2>Welcome to the Lost and Found App</h2>
      <p>Helping you find lost items and report found items.</p>
      <div className="cta-buttons">
        <a href="/report-lost" className="cta-button">
          Report Lost Item
        </a>
        <a href="/report-found" className="cta-button">
          Report Found Item
        </a>
      </div>
    </div>
  );
};

export default HomePage;
