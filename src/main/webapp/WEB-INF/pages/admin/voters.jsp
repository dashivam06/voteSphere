<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>VoteSphere - Voter Management</title>
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
					<h1 class="text-2xl font-bold text-gray-800">Voter Management</h1>
					<div class="flex space-x-4">
						<div class="relative">
							<form id="searchForm" action="${pageContext.request.contextPath}/admin/voter/search/" method="post" >
                            							<input type="text" name="searchInput" id="searchInput" value="${searchInput}" placeholder="Search voters..."
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

						<!-- <button
                class="bg-primary-600 text-white px-4 py-2 rounded-lg hover:bg-primary-700 transition-colors duration-200"
              >
                Create Voter
              </button> -->
					</div>
				</div>

                      <!-- Voter Stats -->
                      <div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-8">
                          <div class="bg-blue-50 rounded-lg p-4 border border-blue-100">
                              <h3 class="font-semibold text-blue-800">Total Registered Voters</h3>
                              <p class="text-3xl font-bold text-blue-800">
                                  <c:out value="${totalVoters}"/>
                              </p>
                          </div>
                          <div class="bg-green-50 rounded-lg p-4 border border-green-100">
                              <h3 class="font-semibold text-green-800">Active Voters</h3>
                              <p class="text-3xl font-bold text-green-800">
                                  <c:out value="${activeVoters}"/>
                              </p>
                          </div>
                          <div class="bg-red-50 rounded-lg p-4 border border-red-100">
                              <h3 class="font-semibold text-red-800">Pending Verification</h3>
                              <p class="text-3xl font-bold text-red-800">
                                  <c:out value="${pendingVoters}"/>
                              </p>
                          </div>
                      </div><!-- Voters Table -->
                            <div class="overflow-x-auto">
                                <table class="min-w-full divide-y divide-gray-200"><thead class="bg-gray-50">
                                                                                       <tr>
                                                                                           <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Voter</th>
                                                                                           <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Voter ID</th>
                                                                                           <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Phone</th>
                                                                                           <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Address</th>
                                                                                           <th class="px-6 py-3 text-center text-xs font-medium text-gray-500 uppercase tracking-wider">Gender</th>
                                                                                           <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Registered</th>
                                                                                           <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
                                                                                       </tr>
                                                                                   </thead>
                                                                                   <tbody class="bg-white divide-y divide-gray-200">
                                                                                       <c:choose>
                                                                                           <c:when test="${not empty voters}">
                                                                                               <c:forEach var="voter" items="${voters}">
                                                                                                   <tr>
                                                                                                       <!-- Voter Info -->
                                                                                                       <td class="px-6 py-4 whitespace-nowrap flex items-center space-x-4">
                                                                                                           <img src="/images/${voter.profileImage}" alt="Profile" class="h-10 w-10 rounded-full">
                                                                                                           <div>
                                                                                                               <div class="text-sm font-medium text-gray-900">${voter.firstName} ${voter.lastName}</div>
                                                                                                               <div class="text-xs text-gray-500">${voter.email}</div>
                                                                                                           </div>
                                                                                                       </td>

                                                                                                       <!-- Voter ID -->
                                                                                                       <td class="px-6 py-4 text-sm text-gray-700 whitespace-nowrap">VS-${voter.voterId}</td>

                                                                                                       <!-- Phone Number -->
                                                                                                       <td class="px-6 py-4 text-sm text-gray-700 whitespace-nowrap">${voter.phoneNumber}</td>

                                                                                                       <!-- Address -->
                                                                                                       <td class="px-6 py-4 text-sm text-gray-700 whitespace-nowrap">
                                                                                                           ${voter.permanentAddress}<br>
                                                                                                           <span class="text-xs text-gray-400">Temp: ${voter.temporaryAddress}</span>
                                                                                                       </td>

                                                                                                       <!-- Gender -->
                                                                                                       <td class="px-6 py-4 text-center whitespace-nowrap">
                                                                                                           <span class="inline-flex items-center gap-1 px-2.5 py-1.5 text-xs font-medium rounded-full
                                                                                                               ${fn:toLowerCase(voter.gender) eq 'male' ? 'bg-blue-50 text-blue-700' :
                                                                                                                 fn:toLowerCase(voter.gender) eq 'female' ? 'bg-pink-50 text-pink-700' :
                                                                                                                 'bg-gray-100 text-gray-700'}">
                                                                                                               <c:choose>
                                                                                                                   <c:when test="${fn:toLowerCase(voter.gender) eq 'male'}">
                                                                                                                       <svg xmlns="http://www.w3.org/2000/svg" class="h-3 w-3" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                                                                                                           <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M14 10l7-7m0 0v6m0-6h-6m2 10a5 5 0 11-10 0 5 5 0 0110 0z" />
                                                                                                                       </svg> Male
                                                                                                                   </c:when>
                                                                                                                   <c:when test="${fn:toLowerCase(voter.gender) eq 'female'}">
                                                                                                                       <svg xmlns="http://www.w3.org/2000/svg" class="h-3 w-3" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                                                                                                           <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 14a5 5 0 100-10 5 5 0 000 10zm0 0v6m0-3h3m-3 0h-3" />
                                                                                                                       </svg> Female
                                                                                                                   </c:when>
                                                                                                                   <c:otherwise>
                                                                                                                       <c:out value="${voter.gender}" />
                                                                                                                   </c:otherwise>
                                                                                                               </c:choose>
                                                                                                           </span>
                                                                                                       </td>


                                                                                                       <!-- Registration Date -->
                                                                                                       <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-700">
                                                                                                           <fmt:formatDate value="${voter.createdAt}" pattern="dd MMM yyyy" />
                                                                                                       </td>

                                                                                                       <!-- Actions -->
                                                                                                       <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-700">
                                                                                                           <div class="flex gap-2">
                                                                                                                                                               <a href="${pageContext.request.contextPath}/admin/voters/view/${voter.userId}" class="text-primary-600 hover:text-primary-900 tooltip">View<span class="tooltip-text">View Voter</span></a>

                                                    <a href="${pageContext.request.contextPath}/admin/voters/edit/${voter.userId}" class="text-yellow-600 hover:text-yellow-900 tooltip">Edit<span class="tooltip-text">Edit Voter</span></a>

                                                                                                               <a href="deleteVoter?voterId=${voter.voterId}" class="text-red-600 hover:underline tooltip" onclick="return confirm('Are you sure you want to delete this voter?');">
                                                                                                                   Delete
                                                                                                                   <span class="tooltip-text">Delete Voter</span>
                                                                                                               </a>
                                                                                                           </div>
                                                                                                       </td>
                                                                                                   </tr>
                                                                                               </c:forEach>
                                                                                           </c:when>
                                                                                           <c:otherwise>
                                                                                               <tr>
                                                                                                   <td colspan="7" class="px-6 py-4 text-center text-gray-500">
                                                                                                       No voters found.
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
                                                           </body>
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
                                                                               // Start 5 second countdown before refresh
                                                                               emptyTimer = setTimeout(() => {
                                                                                   searchForm.submit();
                                                                               }, 5000); // 5 second delay for empty field
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

                                                       </html>
