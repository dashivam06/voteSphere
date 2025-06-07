<%@ page import="com.voteSphere.model.AuthUser" %>
<%@ include file="../loader-animation.jsp" %>

<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List"%>

<link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600&display=swap" rel="stylesheet">

<aside
        class="bg-white w-64 h-full shadow-md fixed left-0 top-0 transform transition-transform duration-300 ease-in-out z-30 lg:translate-x-0 font-['Poppins'] flex flex-col"
        id="sidebar"
>
  <div class="p-4 py-7 border-b flex items-center justify-between">
    <div class="flex items-center">
      <svg
              xmlns="http://www.w3.org/2000/svg"
              class="h-8 w-8 text-primary-600"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
      >
        <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z"
        />
      </svg>
      <span class="ml-2 text-xl font-bold text-primary-600">VoteSphere</span>
    </div>
    <button
            class="lg:hidden text-gray-500 hover:text-gray-700 focus:outline-none"
            id="closeSidebar"
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
                d="M6 18L18 6M6 6l12 12"
        />
      </svg>
    </button>
  </div>

  <div class="py-3 overflow-y-auto flex-grow">
    <ul>
      <li class="mb-1">
        <a
                href="/dashboard"
                class="flex items-center px-4 py-3 text-gray-600 hover:bg-primary-50 hover:text-gray-900 transition-colors duration-200"
        >
          <svg
                  xmlns="http://www.w3.org/2000/svg"
                  class="h-5 w-5 mr-3"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
          >
            <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6"
            />
          </svg>
          Dashboard
        </a>
      </li>
      <li class="mb-1">
        <a
                href="/profile"
                class="flex items-center px-4 py-3 text-gray-600 hover:bg-primary-50 hover:text-gray-900 transition-colors duration-200"
        >
          <svg
                  xmlns="http://www.w3.org/2000/svg"
                  class="h-5 w-5 mr-3"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
          >
            <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"
            />
          </svg>
          My Profile
        </a>
      </li>
      <li class="mb-1">
        <a
                href="/election"
                class="flex items-center px-4 py-3 text-gray-600 hover:bg-primary-50 hover:text-gray-900 transition-colors duration-200"
        >
          <svg
                  xmlns="http://www.w3.org/2000/svg"
                  class="h-5 w-5 mr-3"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
          >
            <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2"
            />
          </svg>
          Elections
        </a>
      </li>
      <li class="mb-1">
        <a
                href="/donate"
                class="flex items-center px-4 py-3 text-gray-600 hover:bg-primary-50 hover:text-gray-900 transition-colors duration-200"
        >
          <svg
                  xmlns="http://www.w3.org/2000/svg"
                  class="h-5 w-5 mr-3"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
          >
            <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M12 8c-1.657 0-3 .895-3 2s1.343 2 3 2 3 .895 3 2-1.343 2-3 2m0-8c1.11 0 2.08.402 2.599 1M12 8V7m0 1v8m0 0v1m0-1c-1.11 0-2.08-.402-2.599-1M21 12a9 9 0 11-18 0 9 9 0 0118 0z"
            />
          </svg>
          Donate
        </a>
      </li>
    </ul>
  </div>

  <div class="mt-auto p-4">
    <a
            href="/logout"
            class="flex items-center  w-full px-4 py-3 text-white bg-red-500 hover:bg-red-600 transition-colors duration-200 rounded-lg"
    >
      <svg
              xmlns="http://www.w3.org/2000/svg"
              class="h-5 w-5 mr-3"
              fill="none"
              viewBox="0 0 24 24"
              stroke="currentColor"
      >
        <path
                stroke-linecap="round"
                stroke-linejoin="round"
                stroke-width="2"
                d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"
        />
      </svg>
      Logout
    </a>
  </div>
</aside>

<script>
  // Toggle sidebar on mobile
  const sidebar = document.getElementById("sidebar");
  const closeSidebar = document.getElementById("closeSidebar");

  if (closeSidebar) {
    closeSidebar.addEventListener("click", () => {
      sidebar.classList.add("-translate-x-full");
    });
  }
</script>