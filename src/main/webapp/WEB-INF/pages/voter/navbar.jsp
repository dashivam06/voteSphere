<%@ include file="../loader-animation.jsp" %>
<%@ page import="com.voteSphere.model.AuthUser" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List"%>

<style>
  /* Navbar styles */
  .navbar {
    background-color: white;
    padding: 1rem 1.5rem;
    box-shadow: 0 1px 3px 0 rgba(0, 0, 0, 0.1), 0 1px 2px 0 rgba(0, 0, 0, 0.06);
    display: flex;
    justify-content: space-between;
    align-items: center;
    position: relative;
    z-index: 20;
  }

  .navbar-left {
    display: flex;
    align-items: center;
  }

  .navbar-toggle {
    color: #6b7280;
    background: none;
    border: none;
    cursor: pointer;
    padding: 0.5rem;
    margin-right: 1rem;
  }

  .navbar-toggle:hover {
    color: #374151;
  }

  .navbar-toggle-icon {
    height: 1.5rem;
    width: 1.5rem;
  }

  .navbar-title {
    font-size: 1.25rem;
    font-weight: 700;
    color: #1f2937;
    display: none;
  }

  @media (min-width: 768px) {
    .navbar-title {
      display: block;
    }
  }

  .navbar-right {
    display: flex;
    align-items: center;
    gap: 1rem;
  }

  .profile-button {
    display: flex;
    align-items: center;
    color: #4b5563;
    background: none;
    border: none;
    cursor: pointer;
    gap: 0.5rem;
    padding: 0.25rem;
  }

  .profile-image {
    height: 2.8rem;
    width: 2.8rem;
    border-radius: 50%;
    object-fit: cover;
  }

  .profile-info {
    display: flex;
    flex-direction: column;
    align-items: flex-start;
    margin-left: 0.5rem;
    display: none;
  }

  @media (min-width: 768px) {
    .profile-info {
      display: flex;
    }
  }

  .profile-name {
    font-size: 1rem;
    font-weight: 500;
    color: #1f2937;
  }

  .profile-id {
    font-size: 0.875rem;
    font-weight: 500;
    color: #6b7280;
  }

  /* Dropdown styles */
  .dropdown {
    position: absolute;
    right: 0;
    top: 100%;
    margin-top: 0.5rem;
    width: 12rem;
    background-color: white;
    border-radius: 0.5rem;
    box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
    z-index: 10;
    display: none;
  }

  .dropdown.show {
    display: block;
  }

  .dropdown-item {
    display: block;
    padding: 0.5rem 1rem;
    font-size: 0.875rem;
    color: #374151;
    text-decoration: none;
  }

  .dropdown-item:hover {
    background-color: #f3f4f6;
  }

  .dropdown-divider {
    border-top: 1px solid #e5e7eb;
    margin: 0.25rem 0;
  }

  /* Utility classes */
  .hidden {
    display: none;
  }

  .focus-outline-none:focus {
    outline: none;
  }

  /* Animation */
  .transition-all {
    transition-property: all;
    transition-timing-function: cubic-bezier(0.4, 0, 0.2, 1);
    transition-duration: 150ms;
  }

  .duration-200 {
    transition-duration: 200ms;
  }

  /* Colors */
  .bg-white {
    background-color: white;
  }

  .text-gray-700 {
    color: #374151;
  }

  .hover-bg-gray-100:hover {
    background-color: #f3f4f6;
  }
</style>

<nav class="navbar">
  <div class="navbar-left">

    <h1 class="navbar-title">Voter Portal</h1>
  </div>

  <div class="navbar-right">
    <div class="relative">
      <button id="profileButton" class="profile-button focus-outline-none">
        <img src="${authenticated_user.profileImageFromUser}" alt="Profile" class="profile-image" />
        <%
          Object userObj = session.getAttribute("authenticated_user");
          if (userObj == null) {
          } else {
            AuthUser authenticated_user = (AuthUser) userObj;
          }
        %>
        <div class="profile-info">
          <span class="profile-name">${authenticated_user.fullName}</span>
          <span class="profile-id">Voter ID: ${authenticated_user.voterId}</span>
        </div>
      </button>

      <!-- Profile dropdown -->
      <div id="profileDropdown" class="dropdown">
        <a href="/profile" class="dropdown-item">My Profile</a>
        <a href="#" class="dropdown-item">Settings</a>
        <div class="dropdown-divider"></div>
        <a href="/logout" class="dropdown-item">Logout</a>
      </div>
    </div>
  </div>
</nav>

<script>
  // Toggle sidebar
  const sidebarToggle = document.getElementById("sidebarToggle");
  const sidebar = document.getElementById("sidebar");

  if (sidebarToggle && sidebar) {
    sidebarToggle.addEventListener("click", () => {
      sidebar.classList.toggle("-translate-x-full");
    });
  }

  // Toggle profile dropdown
  const profileButton = document.getElementById("profileButton");
  const profileDropdown = document.getElementById("profileDropdown");

  if (profileButton && profileDropdown) {
    profileButton.addEventListener("click", (e) => {
      e.stopPropagation();
      profileDropdown.classList.toggle("show");
    });
  }

  // Close dropdown when clicking outside
  document.addEventListener("click", (event) => {
    if (profileDropdown && !profileButton.contains(event.target) && !profileDropdown.contains(event.target)) {
      profileDropdown.classList.remove("show");
    }
  });
</script>