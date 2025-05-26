<%@ page import="com.voteSphere.model.AuthUser" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List"%>

<nav class="bg-white shadow-sm px-6 py-4 flex justify-between items-center">
  <div class="flex items-center">
    <button
      id="sidebarToggle"
      class="lg:hidden text-gray-500 hover:text-gray-700 focus:outline-none mr-4"
    >
      <svg
        xmlns="http://www.w3.org/2000/svg"
        class="h-6 w-6"
        fill="none"
        viewBox="0 0 24 24"
        stroke="currentColor"
      >
        <path
          stroke-linecap="round"
          stroke-linejoin="round"
          stroke-width="2"
          d="M4 6h16M4 12h16M4 18h16"
        />
      </svg>
    </button>
    <h1 class="text-xl font-bold text-gray-800 hidden md:block">
      Voter Portal
    </h1>
  </div>

  <div class="flex items-center space-x-4">
    <div class="relative">
      <button
        class="flex items-center text-gray-600 focus:outline-none gap-2"
        id="profileButton"
      >
        <img
          src="/uploads/${authenticated_user.profileImageFromUser}"
          alt="Profile"
          class="h-12 aspect-square rounded-full"
        />
        <%
            Object userObj = session.getAttribute("authenticated_user");
            if (userObj == null) {
            } else {
                AuthUser authenticated_user = (AuthUser) userObj;
            }
        %>

        <div class="flex flex-col items-start">
          <span class="hidden md:block ml-2 text-base font-medium"
            >${authenticated_user.fullName}</span
          >
          <span class="hidden md:block ml-2 text-sm text-gray-500 font-medium"
            >Voter ID: ${authenticated_user.voterId}</span
          >
        </div>
      </button>
      <!-- Profile dropdown -->
      <div
        class="absolute right-0 mt-2 w-48 bg-white rounded-lg shadow-lg z-10 hidden"
        id="profileDropdown"
      >
        <a
          href="profile.jsp"
          class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
        >
          My Profile
        </a>
        <a
          href="#"
          class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
        >
          Settings
        </a>
        <div class="border-t border-gray-100"></div>
        <a
          href="../index.html"
          class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100"
        >
          Logout
        </a>
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

  // Toggle notifications dropdown
  const notificationButton = document.getElementById("notificationButton");
  const notificationDropdown = document.getElementById("notificationDropdown");

  if (notificationButton && notificationDropdown) {
    notificationButton.addEventListener("click", () => {
      notificationDropdown.classList.toggle("hidden");
      if (profileDropdown) {
        profileDropdown.classList.add("hidden");
      }
    });
  }

  // Toggle profile dropdown
  const profileButton = document.getElementById("profileButton");
  const profileDropdown = document.getElementById("profileDropdown");

  if (profileButton && profileDropdown) {
    profileButton.addEventListener("click", () => {
      profileDropdown.classList.toggle("hidden");
      if (notificationDropdown) {
        notificationDropdown.classList.add("hidden");
      }
    });
  }

  // Close dropdowns when clicking outside
  document.addEventListener("click", (event) => {
    if (
      notificationButton &&
      notificationDropdown &&
      !notificationButton.contains(event.target) &&
      !notificationDropdown.contains(event.target)
    ) {
      notificationDropdown.classList.add("hidden");
    }

    if (
      profileButton &&
      profileDropdown &&
      !profileButton.contains(event.target) &&
      !profileDropdown.contains(event.target)
    ) {
      profileDropdown.classList.add("hidden");
    }
  });
</script>
