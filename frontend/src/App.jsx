import React from "react";
import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Header from "./components/Header";
import Footer from "./components/Footer";
import HomePage from "./pages/HomePage";
import ReportLostItemPage from "./pages/ReportLostItemPage";
import ReportFoundItemPage from "./pages/ReportFoundItemPage";
import SearchItemsPage from "./pages/SearchItemsPage";
import "./styles/App.css";

function App() {
  return (
    <Router>
      <Header />
      <div className="main-content">
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/report-lost" element={<ReportLostItemPage />} />
          <Route path="/report-found" element={<ReportFoundItemPage />} />
          <Route path="/search" element={<SearchItemsPage />} />
        </Routes>
      </div>
      <Footer />
    </Router>
  );
}

export default App;
