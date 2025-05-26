<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>VoteSphere - Elections</title>
    <link rel="stylesheet" href="../styles/global.css" />
    <script src="https://cdn.tailwindcss.com"></script>
    <link
      href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap"
      rel="stylesheet"
    />
    <script>
      tailwind.config = {
        theme: {
          extend: {
            colors: {
              primary: {
                50: "#f0f9ff",
                100: "#e0f2fe",
                200: "#bae6fd",
                300: "#7dd3fc",
                400: "#38bdf8",
                500: "#0ea5e9",
                600: "#0284c7",
                700: "#0369a1",
                800: "#075985",
                900: "#0c4a6e",
              },
            },
            fontFamily: {
              sans: ["Inter", "sans-serif"],
            },
          },
        },
      };
    </script>
  </head>
  <body class="font-sans bg-gray-100 flex h-screen overflow-hidden">
    <!-- Include sidebar -->
        		<%@ include file="sidebar.jsp" %>

    <!-- Main Content -->
    <div
      class="flex-grow flex flex-col ml-0 lg:ml-64 transition-all duration-300 ease-in-out"
    >
      <!-- Navbar -->
        		<%@ include file="navbar.jsp" %>

      <!-- Content Area -->
      <div class="flex-1 overflow-y-auto p-4 md:p-8 bg-gray-100">
        <div class="max-w-7xl mx-auto">
          <!-- Page Header -->
          <div class="mb-6">
            <h1 class="text-2xl font-bold text-gray-800">Elections</h1>
            <p class="text-gray-600">View all upcoming and active elections</p>
          </div>

          <!-- Filters and Search -->
          <div class="bg-white rounded-lg shadow-sm p-4 mb-6">
            <div class="flex flex-wrap gap-4 items-center justify-between">
              <div class="flex flex-wrap gap-2 items-center">
                <span class="text-sm text-gray-500">Filter by:</span>
                <select
                  class="border border-gray-300 rounded-md text-sm px-3 py-1.5 focus:ring-primary-500 focus:border-primary-500"
                >
                  <option value="active">Active</option>
                  <option value="upcoming">Upcoming</option>
                  <option value="past">Past</option>
                </select>
              </div>
              <div class="relative">
                <input
                  type="text"
                  placeholder="Search elections..."
                  class="border border-gray-300 rounded-md pl-10 pr-4 py-2 w-full md:w-64 focus:ring-primary-500 focus:border-primary-500"
                />
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  class="h-5 w-5 text-gray-400 absolute left-3 top-2.5"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"
                  />
                </svg>
              </div>
            </div>
          </div>

         <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
         <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

        <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

        <!-- Active Elections Section -->
        <section class="mb-8">
            <h2 class="text-xl font-bold text-gray-800 mb-4">Active Elections</h2>

            <c:choose>
                <c:when test="${not empty activeElections}">
                    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                        <c:forEach items="${activeElections}" var="election">
                            <div class="bg-white rounded-lg shadow-sm overflow-hidden transition-transform hover:shadow-md">
                                <!-- Election Image -->
                                <div class="h-40 bg-gray-200 relative">
                                    <img src="/uploads/${not empty election.coverImage ? election.coverImage : 'https://placehold.co/800x400'}"
                                         alt="${election.name}"
                                         class="w-full h-full object-cover"
                                         onerror="this.onerror=null;this.src='https://placehold.co/800x400?text=Election'">
                                    <div class="absolute top-3 right-3">
                                        <span class="px-3 py-1 bg-green-500 text-white text-xs font-semibold rounded-full">
                                            ${election.type}
                                        </span>
                                    </div>
                                </div>

                                <!-- Election Details -->
                                <div class="p-4">
                                    <h3 class="font-semibold text-lg mb-1">${election.name}</h3>

                                    <div class="grid grid-cols-2 gap-4 mb-4">
                                        <div>
                                            <p class="text-xs text-gray-500">Date</p>
                                            <p class="text-sm font-medium">
                                                <fmt:formatDate value="${election.date}" pattern="MMM d, yyyy" />
                                            </p>
                                        </div>
                                        <div>
                                            <p class="text-xs text-gray-500">Time</p>
                                            <p class="text-sm font-medium">
                                                <fmt:formatDate value="${election.startTime}" pattern="hh:mm a" /> -
                                                <fmt:formatDate value="${election.endTime}" pattern="hh:mm a" />
                                            </p>
                                        </div>
                                    </div>

                                    <!-- Action Button -->
                                    <div class="flex justify-end">
                                        <a href="${pageContext.request.contextPath}/cast-vote/${election.electionId}"
                                           class="bg-primary-600 text-white px-4 py-2 rounded-md text-sm hover:bg-primary-700 transition-colors">
                                            Cast Vote
                                        </a>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </div>
                </c:when>
                <c:otherwise>
                    <div class="bg-white rounded-lg shadow-sm p-8 text-center">
                        <svg xmlns="http://www.w3.org/2000/svg" class="h-12 w-12 mx-auto text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.172 16.172a4 4 0 015.656 0M9 10h.01M15 10h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                        </svg>
                        <h3 class="mt-4 text-lg font-medium text-gray-900">No active elections</h3>
                        <p class="mt-1 text-sm text-gray-500">There are currently no elections in progress.</p>
                    </div>
                </c:otherwise>
            </c:choose>
        </section>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!-- Upcoming Elections Section -->
<section class="mb-8">
    <h2 class="text-xl font-bold text-gray-800 mb-4">Upcoming Elections</h2>

    <c:choose>
        <c:when test="${not empty upcomingElections}">
            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                <c:forEach items="${upcomingElections}" var="election">
                    <div class="bg-white rounded-lg shadow-sm overflow-hidden transition-transform hover:shadow-md">
                        <!-- Election Image -->
                        <div class="h-40 bg-gray-200 relative">
                            <img src="/uploads/${not empty election.coverImage ? election.coverImage : 'https://placehold.co/800x400'}"
                                 alt="${election.name}"
                                 class="w-full h-full object-cover"
                                 onerror="this.onerror=null;this.src='https://placehold.co/800x400?text=Election'">
                            <div class="absolute top-3 right-3">
                                <span class="px-3 py-1 bg-yellow-500 text-white text-xs font-semibold rounded-full">
                                    Upcoming
                                </span>
                            </div>
                        </div>

                        <!-- Election Details -->
                        <div class="p-4">
                            <h3 class="font-semibold text-lg mb-1">${election.name}</h3>
                            <p class="text-gray-600 text-sm mb-3">
                                <c:choose>
                                    <c:when test="${not empty election.type}">
                                        ${election.type}
                                    </c:when>
                                    <c:otherwise>
                                        National
                                    </c:otherwise>
                                </c:choose>
                            </p>

                            <div class="grid grid-cols-2 gap-4 mb-4">
                                <div>
                                    <p class="text-xs text-gray-500">Date</p>
                                    <p class="text-sm font-medium">
                                        <fmt:formatDate value="${election.date}" pattern="MMM d, yyyy" />
                                    </p>
                                </div>
                                <div>
                                    <p class="text-xs text-gray-500">Time</p>
                                    <p class="text-sm font-medium">
                                        <fmt:formatDate value="${election.startTime}" pattern="hh:mm a" /> -
                                        <fmt:formatDate value="${election.endTime}" pattern="hh:mm a" />
                                    </p>
                                </div>
                            </div>

                            <!-- Days remaining and Action -->
                            <div class="flex justify-between items-center">
                                <div>
                                    <p class="text-xs text-gray-500">
                                        <c:set var="now" value="<%= new java.util.Date() %>" />
                                        <c:set var="daysUntil" value="${(election.date.time - now.time) / (1000 * 60 * 60 * 24)}" />
                                        Starts in <fmt:formatNumber value="${daysUntil}" maxFractionDigits="0" /> days
                                    </p>
                                </div>
                                <a href="${pageContext.request.contextPath}/elections/${election.electionId}"
                                   class="border border-primary-600 text-primary-600 px-4 py-2 rounded-md text-sm hover:bg-primary-50 transition-colors">
                                    View Details
                                </a>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
        </c:when>
        <c:otherwise>
            <div class="bg-white rounded-lg shadow-sm p-8 text-center">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-12 w-12 mx-auto text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.172 16.172a4 4 0 015.656 0M9 10h.01M15 10h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
                <h3 class="mt-4 text-lg font-medium text-gray-900">No upcoming elections</h3>
                <p class="mt-1 text-sm text-gray-500">Check back later for scheduled elections.</p>
            </div>
        </c:otherwise>
    </c:choose>
</section><%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
          <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

          <!-- Past Elections Section -->
          <section>
              <h2 class="text-xl font-bold text-gray-800 mb-4">Past Elections</h2>

              <c:choose>
                  <c:when test="${not empty pastElections}">
                      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                          <c:forEach items="${pastElections}" var="election">
                              <div class="bg-white rounded-lg shadow-sm overflow-hidden transition-transform hover:shadow-md">
                                  <!-- Election Image -->
                                  <div class="h-40 bg-gray-200 relative">
                                      <img src="/uploads/${not empty election.coverImage ? election.coverImage : 'https://placehold.co/800x400'}"
                                           alt="${election.name}"
                                           class="w-full h-full object-cover"
                                           onerror="this.onerror=null;this.src='https://placehold.co/800x400?text=Election'">
                                      <div class="absolute top-3 right-3">
                                          <span class="px-3 py-1 bg-gray-500 text-white text-xs font-semibold rounded-full">
                                              Completed
                                          </span>
                                      </div>
                                  </div>

                                  <!-- Election Details -->
                                  <div class="p-4">
                                      <h3 class="font-semibold text-lg mb-1">${election.name}</h3>
                                      <p class="text-gray-600 text-sm mb-3">
                                          <c:choose>
                                              <c:when test="${not empty election.type}">
                                                  ${election.type}
                                              </c:when>
                                              <c:otherwise>
                                                  <c:choose>
                                                      <c:when test="${fn:containsIgnoreCase(election.name, 'local')}">
                                                          City of Springfield
                                                      </c:when>
                                                      <c:when test="${fn:containsIgnoreCase(election.name, 'state')}">
                                                          Illinois State
                                                      </c:when>
                                                      <c:otherwise>
                                                          National
                                                      </c:otherwise>
                                                  </c:choose>
                                              </c:otherwise>
                                          </c:choose>
                                      </p>

                                      <div class="grid grid-cols-2 gap-4 mb-4">
                                          <div>
                                              <p class="text-xs text-gray-500">Date</p>
                                              <p class="text-sm font-medium">
                                                  <fmt:formatDate value="${election.date}" pattern="MMM d, yyyy" />
                                              </p>
                                          </div>
                                          <div>
                                              <p class="text-xs text-gray-500">Time</p>
                                              <p class="text-sm font-medium">
                                                  <fmt:formatDate value="${election.startTime}" pattern="hh:mm a" /> -
                                                  <fmt:formatDate value="${election.endTime}" pattern="hh:mm a" />
                                              </p>
                                          </div>
                                      </div>

                                      <!-- Voting Status and Action -->
                                      <div class="flex justify-between items-center">

                                          <a href="${pageContext.request.contextPath}/elections/${election.electionId}/results"
                                             class="border border-gray-300 text-gray-600 px-4 py-2 rounded-md text-sm hover:bg-gray-50 transition-colors">
                                              See Results
                                          </a>
                                      </div>
                                  </div>
                              </div>
                          </c:forEach>
                      </div>
                  </c:when>
                  <c:otherwise>
                      <div class="bg-white rounded-lg shadow-sm p-8 text-center">
                          <svg xmlns="http://www.w3.org/2000/svg" class="h-12 w-12 mx-auto text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.172 16.172a4 4 0 015.656 0M9 10h.01M15 10h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                          </svg>
                          <h3 class="mt-4 text-lg font-medium text-gray-900">No past elections</h3>
                          <p class="mt-1 text-sm text-gray-500">There are no completed elections to display.</p>
                      </div>
                  </c:otherwise>
              </c:choose>
          </section>
        </div>
      </div>
    </div>

    <script>
      // Load sidebar
      fetch("sidebar.html")
        .then((response) => response.text())
        .then((data) => {
          document.getElementById("sidebar-container").innerHTML = data;
        });

      // Load navbar
      fetch("navbar.html")
        .then((response) => response.text())
        .then((data) => {
          document.getElementById("navbar-container").innerHTML = data;
        });

      // Filter functionality
      document.addEventListener("DOMContentLoaded", function () {
        const filterSelect = document.querySelector("select");

        if (filterSelect) {
          filterSelect.addEventListener("change", function (e) {
            // Filter implementation would go here
            console.log("Filtering by:", e.target.value);

            // This would typically involve showing/hiding election cards
            // or fetching new data from a backend
          });
        }

        // Search implementation
        const searchInput = document.querySelector('input[type="text"]');

        if (searchInput) {
          searchInput.addEventListener("input", function (e) {
            // Search implementation would go here
            console.log("Searching for:", e.target.value);
          });
        }
      });
    </script>
  </body>
</html>
