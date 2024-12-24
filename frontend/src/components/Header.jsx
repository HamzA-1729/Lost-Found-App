import React, { useState } from "react";
import { Link } from "react-router-dom"; // Use Link for client-side navigation
import "../styles/Header.css"; // Assuming the header styles are in this CSS file

const Header = () => {
  const [menuOpen, setMenuOpen] = useState(false);

  const toggleMenu = () => {
    setMenuOpen(!menuOpen);
  };

  return (
    <header className={`header ${menuOpen ? "active" : ""}`}>
      <div className="logo">
        <h1>Lost & Found</h1>
      </div>
      <nav>
        <div className="menu-icon" onClick={toggleMenu}>
          &#9776; {/* This is the hamburger icon */}
        </div>
        <ul className={`menu ${menuOpen ? "open" : ""}`}>
          <li>
            <Link to="/" onClick={() => setMenuOpen(false)}>
              Home
            </Link>
          </li>
          <li>
            <Link to="/report-lost" onClick={() => setMenuOpen(false)}>
              Report Lost Item
            </Link>
          </li>
          <li>
            <Link to="/report-found" onClick={() => setMenuOpen(false)}>
              Report Found Item
            </Link>
          </li>
          <li>
            <Link to="/search" onClick={() => setMenuOpen(false)}>
              Search Items
            </Link>
          </li>
        </ul>
      </nav>
    </header>
  );
};

export default Header;
