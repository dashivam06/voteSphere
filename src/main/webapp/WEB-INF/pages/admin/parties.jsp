<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.List"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>VoteSphere - Party Management</title>
<link rel="stylesheet" href="../styles/global.css" />
<script src="https://cdn.tailwindcss.com"></script>
<link
	href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap"
	rel="stylesheet" />
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
<style>
/* Tooltip styles */
.tooltip {
	position: relative;
}

.tooltip:hover .tooltip-text {
	visibility: visible;
	opacity: 1;
}

.tooltip-text {
	visibility: hidden;
	opacity: 0;
	width: max-content;
	max-width: 200px;
	background-color: #333;
	color: #fff;
	text-align: center;
	border-radius: 6px;
	padding: 5px 10px;
	position: absolute;
	z-index: 1;
	bottom: 125%;
	left: 50%;
	transform: translateX(-50%);
	transition: opacity 0.3s;
}

.tooltip-text::after {
	content: "";
	position: absolute;
	top: 100%;
	left: 50%;
	margin-left: -5px;
	border-width: 5px;
	border-style: solid;
	border-color: #333 transparent transparent transparent;
}
</style>
</head>
<body class="font-sans bg-gray-100 flex h-screen overflow-hidden">

    <!-- Include sidebar -->
    <%@ include file="sidebar.jsp" %>



	<!-- Main Content -->
	<div
		class="flex-1 flex flex-col ml-0 lg:ml-64 transition-all duration-300 ease-in-out">
		<!-- Include navbar -->
		<%@ include file="navbar.jsp" %>

		<!-- Content Area -->
		<div class="p-8">
			<div class="bg-white rounded-lg shadow-md p-6">
				<div class="flex justify-between items-center mb-6">
					<h1 class="text-2xl font-bold text-gray-800">Party Management</h1>
					<div class="flex space-x-4">
						<div class="relative">

						<form id="searchForm" action="${pageContext.request.contextPath}/admin/party/search/" method="post" >
							<input type="text" name="searchInput" id="searchInput" value="${searchInput}" placeholder="Search parties..."
								class="pl-10 pr-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500" />
							<svg class="absolute left-3 top-2.5 h-5 w-5 text-gray-400"
								xmlns="http://www.w3.org/2000/svg" fill="none"
								viewBox="0 0 24 24" stroke="currentColor">

								</form>

                  <path stroke-linecap="round" stroke-linejoin="round"
									stroke-width="2"
									d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
                </svg>
						</div>
						<a href="/admin/party/add">
						<button
							class="bg-primary-600 text-white px-4 py-2 rounded-lg hover:bg-primary-700 transition-colors duration-200">
							Add Party</button>
							</a>
					</div>

				</div>

				<!-- Parties Table -->
				<div class="overflow-x-auto">
					<table class="min-w-full divide-y divide-gray-200">
						<thead class="bg-gray-50">
							<tr>
								<th
									class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
									Party</th>
								<th
									class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
									Leader Name</th>
								<th
									class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
									Founder Name</th>

								<th
									class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
									Actions</th>
							</tr>
						</thead>
						<tbody class="bg-white divide-y divide-gray-200">
                            <c:choose>
                                <c:when test="${not empty parties}">
                                    <!-- Iterate through parties list -->
                                    <c:forEach var="party" items="${parties}">
                                        <tr>
                                            <td class="px-6 py-4 whitespace-nowrap">
                                                <div class="flex items-center">
                                                    <div class="h-10 w-10 flex-shrink-0">
                                                        <img class="h-10 w-10 rounded object-cover"
                                                            src="${pageContext.request.contextPath}/images/${not empty party.symbolImage ? party.symbolImage : 'https://placehold.co/100x100'}"
                                                            alt="${party.name} logo" />
                                                    </div>
                                                    <div class="ml-4">
                                                        <div class="text-sm font-medium text-gray-900">
                                                            ${party.name}
                                                        </div>
                                                    </div>
                                                </div>
                                            </td>
                                            <td class="px-6 py-4 whitespace-nowrap">
                                                <div class="text-sm text-gray-900">${party.leaderName}</div>
                                            </td>
                                            <td class="px-6 py-4 whitespace-nowrap">
                                                <div class="text-sm text-gray-900">${party.founderName}</div>
                                            </td>
                                            <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                                                <div class="flex space-x-3">
                                                    <a href="${pageContext.request.contextPath}/admin/party/view/${party.partyId}"
                                                       class="text-primary-600 hover:text-primary-900">
                                                        View
                                                    </a>
                                                    <a href="${pageContext.request.contextPath}/admin/party/update/${party.partyId}"
                                                       class="text-yellow-600 hover:text-yellow-900">
                                                        Edit
                                                    </a>
                                                    <form action="${pageContext.request.contextPath}/admin/party/delete/${party.partyId}"
                                                          method="POST"
                                                          class="inline"
                                                          onsubmit="return confirm('Are you sure you want to delete this party?');">
                                                        <button type="submit" class="text-red-600 hover:text-red-900">
                                                            Delete
                                                        </button>
                                                    </form>
                                                </div>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:when>
                                <c:otherwise>
                                    <!-- Show message when no parties exist -->
                                    <tr>
                                        <td colspan="4" class="px-6 pb-3 pt-5 text-center text-sm text-gray-500">
                                            No parties found. <a href="${pageContext.request.contextPath}/admin/party/add"
                                                               class="text-primary-600 hover:text-primary-900">Add a new party</a> to get started.
                                        </td>
                                    </tr>
                                </c:otherwise>
                            </c:choose>
                        </tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const searchInput = document.getElementById('searchInput');
            const searchForm = document.getElementById('searchForm');
            let searchTimeout;
            let isEmptyState = false;
            let emptyTimer;

            // Restore focus if there's a search query
            if (searchInput.value) {
                searchInput.focus();
                searchInput.selectionStart = searchInput.selectionEnd = searchInput.value.length;
            }

            searchInput.addEventListener('input', function() {
                clearTimeout(searchTimeout);
                clearTimeout(emptyTimer);

                if (this.value.trim() === '') {
                    // Field just became empty
                    if (!isEmptyState) {
                        isEmptyState = true;
                        // Start 4 second countdown before refresh
                        emptyTimer = setTimeout(() => {
                            searchForm.submit();
                        }, 4000); // 4 second delay for empty field
                    }
                } else {
                    // Field has content
                    isEmptyState = false;
                    // Normal search delay
                    searchTimeout = setTimeout(() => {
                        searchForm.submit();
                    }, 500); // 0.5 second delay for typing
                }
            });

            // Maintain focus when field is empty
            searchInput.addEventListener('blur', function() {
                if (this.value.trim() === '' && isEmptyState) {
                    // Only maintain focus if we're in the empty waiting period
                    setTimeout(() => {
                        this.focus();
                    }, 10); // Small delay to ensure blur completes
                }
            });
        });
        </script>
</body>
</html>
