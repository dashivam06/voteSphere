<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>VoteSphere - Elections</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/global.css" />
    <script src="https://cdn.tailwindcss.com"></script>
    <link
            href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap"
            rel="stylesheet"
    />
    <!-- Tailwind CDN link -->
    <link href="https://cdn.jsdelivr.net/npm/tailwindcss@2.2.19/dist/tailwind.min.css" rel="stylesheet">

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

            <!-- Filters and Search -->
            <div class="bg-white rounded-lg shadow-sm p-4 mb-6">
                <div class="flex flex-wrap gap-4 items-center justify-between">
                    <div class="flex flex-wrap gap-2 items-center">
                        <span class="text-sm text-gray-500">Filter by:</span>
                        <div class="flex items-center space-x-2">
                            <div class="inline-flex rounded-md shadow-sm" role="group">
                                <button type="button" data-filter="all" class="filter-btn px-4 py-2 text-sm font-medium rounded-l-lg border border-gray-300 bg-primary-600 text-white">
                                    All
                                </button>
                                <button type="button" data-filter="active" class="filter-btn px-4 py-2 text-sm font-medium border-t border-b border-gray-300 hover:bg-gray-50">
                                    Active
                                </button>
                                <button type="button" data-filter="upcoming" class="filter-btn px-4 py-2 text-sm font-medium border-t border-b border-gray-300 hover:bg-gray-50">
                                    Upcoming
                                </button>
                                <button type="button" data-filter="past" class="filter-btn px-4 py-2 text-sm font-medium rounded-r-lg border border-gray-300 hover:bg-gray-50">
                                    Past
                                </button>
                            </div>
                        </div>
                    </div>
                    <div class="relative">
                        <div class="absolute inset-y-0 left-0 flex items-center pl-3 pointer-events-none">
                            <svg class="w-4 h-4 text-gray-500" aria-hidden="true" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 20 20">
                                <path stroke="currentColor" stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="m19 19-4-4m0-7A7 7 0 1 1 1 8a7 7 0 0 1 14 0Z"/>
                            </svg>
                        </div>
                        <input type="text" id="search-input" class="block w-full p-2 pl-10 text-sm text-gray-900 border border-gray-300 rounded-lg focus:ring-primary-500 focus:border-primary-500" placeholder="Search elections...">
                    </div>
                </div>
            </div>

            <!-- Active Elections Section -->
            <section id="active-section" class="mb-8">
                <h2 class="text-xl font-bold text-gray-800 mb-4">Active Elections</h2>

                <c:choose>
                    <c:when test="${not empty activeElections}">
                        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                            <c:forEach items="${activeElections}" var="election">
                                <div class="election-card bg-white rounded-lg shadow-sm overflow-hidden transition-transform hover:shadow-md" data-status="active" data-name="${fn:toLowerCase(election.name)}">
                                    <!-- Election Image -->
                                    <div class="h-40 bg-gray-200 relative">
                                        <img src="${not empty election.coverImage ? election.coverImage : 'https://placehold.co/800x400'}"
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

                                        <!-- Action Area -->
                                        <div class="flex justify-end">
                                            <c:choose>
                                                <c:when test="${hasUserVotedMap[election.electionId]}">
                                                    <!-- Already Voted Message -->
                                                    <div class="flex items-center bg-blue-100 text-blue-700 px-3 py-2 rounded-md text-sm">
                                                        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 mr-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                                  d="M13 16h-1v-4h-1m2-4h.01M12 20a8 8 0 100-16 8 8 0 000 16z" />
                                                        </svg>
                                                        You have already voted
                                                    </div>
                                                </c:when>
                                                <c:otherwise>
                                                    <!-- Cast Vote Button -->
                                                    <a href="${pageContext.request.contextPath}/cast-vote/${election.electionId}"
                                                       class="bg-primary-600 text-white px-4 py-2 rounded-md text-sm hover:bg-primary-700 transition-colors">
                                                        Cast Vote
                                                    </a>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>

                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="no-results bg-white rounded-lg shadow-sm p-8 text-center">
                            <svg xmlns="http://www.w3.org/2000/svg" class="h-12 w-12 mx-auto text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.172 16.172a4 4 0 015.656 0M9 10h.01M15 10h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                            </svg>
                            <h3 class="mt-4 text-lg font-medium text-gray-900">No active elections</h3>
                            <p class="mt-1 text-sm text-gray-500">There are currently no elections in progress.</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </section>


            <!-- Upcoming Elections Section -->
            <section id="upcoming-section" class="mb-8">
                <h2 class="text-xl font-bold text-gray-800 mb-4">Upcoming Elections</h2>

                <c:choose>
                    <c:when test="${not empty upcomingElections}">
                        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                            <c:forEach items="${upcomingElections}" var="election">
                                <div class="election-card bg-white rounded-lg shadow-sm overflow-hidden transition-transform hover:shadow-md" data-status="upcoming" data-name="${fn:toLowerCase(election.name)}">
                                    <!-- Election Image -->
                                    <div class="h-40 bg-gray-200 relative">
                                        <img src="${not empty election.coverImage ? election.coverImage : 'https://placehold.co/800x400'}"
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
                                            <a href="${pageContext.request.contextPath}/election/view/${election.electionId}"
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
                        <div class="no-results bg-white rounded-lg shadow-sm p-8 text-center">
                            <svg xmlns="http://www.w3.org/2000/svg" class="h-12 w-12 mx-auto text-gray-400" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.172 16.172a4 4 0 015.656 0M9 10h.01M15 10h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                            </svg>
                            <h3 class="mt-4 text-lg font-medium text-gray-900">No upcoming elections</h3>
                            <p class="mt-1 text-sm text-gray-500">Check back later for scheduled elections.</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </section>

            <!-- Past Elections Section -->
            <section id="past-section">
                <h2 class="text-xl font-bold text-gray-800 mb-4">Past Elections</h2>

                <c:choose>
                    <c:when test="${not empty pastElections}">
                        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                            <c:forEach items="${pastElections}" var="election">
                                <div class="election-card bg-white rounded-lg shadow-sm overflow-hidden transition-transform hover:shadow-md" data-status="past" data-name="${fn:toLowerCase(election.name)}">
                                    <!-- Election Image -->
                                    <div class="h-40 bg-gray-200 relative">
                                        <img src="${not empty election.coverImage ? election.coverImage : 'https://placehold.co/800x400'}"
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

                                            <a href="javascript:void(0)"
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
                        <div class="no-results bg-white rounded-lg shadow-sm p-8 text-center">
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

    // Filter and Search functionality
    document.addEventListener("DOMContentLoaded", function () {
        // Initialize filter buttons
        const filterButtons = document.querySelectorAll('.filter-btn');
        const searchInput = document.getElementById('search-input');

        // Set default filter to 'all'
        setActiveFilter('all');

        // Add click event listeners to filter buttons
        filterButtons.forEach(button => {
            button.addEventListener('click', function() {
                const filterValue = this.getAttribute('data-filter');
                setActiveFilter(filterValue);
                filterElections(filterValue, searchInput.value);
            });
        });

        // Add input event listener to search input
        searchInput.addEventListener('input', function() {
            const activeFilter = document.querySelector('.filter-btn.bg-primary-600').getAttribute('data-filter');
            filterElections(activeFilter, this.value);
        });

        // Function to set active filter button style
        function setActiveFilter(filterValue) {
            filterButtons.forEach(button => {
                if (button.getAttribute('data-filter') === filterValue) {
                    button.classList.add('bg-primary-600', 'text-white');
                    button.classList.remove('hover:bg-gray-50', 'text-gray-700');
                } else {
                    button.classList.remove('bg-primary-600', 'text-white');
                    button.classList.add('hover:bg-gray-50', 'text-gray-700');
                }
            });
        }

        // Function to filter elections based on status and search term
        function filterElections(status, searchTerm) {
            const electionCards = document.querySelectorAll('.election-card');
            const noResultsElements = document.querySelectorAll('.no-results');
            const sections = {
                'active': document.getElementById('active-section'),
                'upcoming': document.getElementById('upcoming-section'),
                'past': document.getElementById('past-section')
            };

            let hasVisibleCards = false;

            // First, handle section visibility based on filter
            if (status === 'all') {
                // Show all sections
                Object.values(sections).forEach(section => {
                    section.style.display = 'block';
                });
            } else {
                // Hide all sections except the selected one
                Object.keys(sections).forEach(key => {
                    sections[key].style.display = key === status ? 'block' : 'none';
                });
            }

            // Then filter cards based on search term
            electionCards.forEach(card => {
                const cardStatus = card.getAttribute('data-status');
                const cardName = card.getAttribute('data-name');
                const shouldShow =
                    (status === 'all' || cardStatus === status) &&
                    (searchTerm === '' || cardName.includes(searchTerm.toLowerCase()));

                card.style.display = shouldShow ? 'block' : 'none';

                if (shouldShow) {
                    hasVisibleCards = true;
                }
            });

            // Show/hide no results messages
            noResultsElements.forEach(el => {
                el.style.display = hasVisibleCards ? 'none' : 'block';
            });
        }
    });
</script>
</body>
</html>