<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<fmt:setLocale value="ne_NP"/>
<fmt:setLocale value="en_US"/>
<%@ include file="../loader-animation.jsp"%>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>VoteSphere - Donation Management</title>
    
    <link rel="stylesheet" href="../styles/global.css"/>
    <link rel="icon" src="/resources/favicon.ico" type="image/x-icon"/>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet"/>
    <script src="https://cdn.tailwindcss.com"></script>
    
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
                            900: "#0c4a6e"
                        }
                    },
                    fontFamily: {
                        sans: ["Inter", "sans-serif"]
                    }
                }
            }
        };
    </script>
</head>

<body class="font-sans bg-gray-100 flex h-screen overflow-hidden">
    <!-- Include sidebar -->
    <%@ include file="sidebar.jsp"%>

    <!-- Main Content -->
    <div class="flex-1 ml-64  ">
        <%@ include file="navbar.jsp" %>

        <div class="bg-white rounded-lg shadow-md p-6">
            <div class="flex justify-between items-center mb-6">
                <h1 class="text-2xl font-bold text-gray-800">Donation Management</h1>
                <div class="flex space-x-4">
                    <div class="relative">
                        <form id="searchForm" action="${pageContext.request.contextPath}/admin/donation/search" method="post">
                            <input type="text" 
                                   name="searchInput" 
                                   id="searchInput" 
                                   value="${searchInput}" 
                                   placeholder="Search donations..."
                                   class="pl-10 pr-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500"/>
                            <svg class="absolute left-3 top-2.5 h-5 w-5 text-gray-400"
                                 xmlns="http://www.w3.org/2000/svg" 
                                 fill="none"
                                 viewBox="0 0 24 24" 
                                 stroke="currentColor">
                                <path stroke-linecap="round" 
                                      stroke-linejoin="round"
                                      stroke-width="2" 
                                      d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"/>
                            </svg>
                        </form>
                    </div>
                </div>
            </div>

            <!-- Donation Statistics -->
            <div class="grid grid-cols-1 md:grid-cols-4 gap-6 mb-6">
                <div class="bg-white p-6 rounded-lg border border-gray-200">
                    <h3 class="text-lg font-semibold text-gray-800 mb-2">Total Donations</h3>
                    <p class="text-3xl font-bold text-primary-600">
                        <fmt:formatNumber value="${totalDonations}" type="currency" currencySymbol="रू "/>
                    </p>
                </div>
                <div class="bg-white p-6 rounded-lg border border-gray-200">
                    <h3 class="text-lg font-semibold text-gray-800 mb-2">This Month</h3>
                    <p class="text-3xl font-bold text-green-600">
                        <fmt:formatNumber value="${monthlyDonations}" type="currency" currencySymbol="रू "/>
                    </p>
                </div>
                <div class="bg-white p-6 rounded-lg border border-gray-200">
                    <h3 class="text-lg font-semibold text-gray-800 mb-2">Average Donation</h3>
                    <p class="text-3xl font-bold text-blue-600">
                        <fmt:formatNumber value="${averageDonation}" type="currency" currencySymbol="रू "/>
                    </p>
                </div>
                <div class="bg-white p-6 rounded-lg border border-gray-200">
                    <h3 class="text-lg font-semibold text-gray-800 mb-2">Total Donors</h3>
                    <p class="text-3xl font-bold text-yellow-600">${totalDonors}</p>
                </div>
            </div>

            <!-- Donations Table -->
            <div class="  max-h-[80vh] overflow-y-auto">
                <table class="min-w-full divide-y">
                    <thead class="bg-gray-50 sticky top-0 z-10">
                        <tr>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Donor</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Amount</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Date</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
                            <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
                        </tr>
                    </thead>
                    <tbody class="bg-white divide-y divide-gray-200  overflow-y-auto" >
                        <c:choose>
                            <c:when test="${not empty donations}">
                                <c:forEach var="donation" items="${donations}">
                                    <tr>
                                        <td class="px-6 py-4 whitespace-nowrap">
                                            <div class="flex items-center">
                                                <div class="h-10 w-10 flex-shrink-0">
                                                    <img class="h-10 w-10 rounded-full"
                                                         src="${not empty donation.profileImage ? donation.profileImage : 'default-profile.png'}"
                                                         alt="${donation.userFullName}"/>
                                                </div>
                                                <div class="ml-4">
                                                    <div class="text-sm font-medium text-gray-900">${donation.userFullName}</div>
                                                    <div class="text-sm text-gray-500">${donation.userEmail}</div>
                                                </div>
                                            </div>
                                        </td>
                                        <td class="px-6 py-4 whitespace-nowrap">
                                            <div class="text-sm text-gray-900">
                                                <fmt:formatNumber value="${donation.amount}" type="currency" currencySymbol="Rs "/>
                                            </div>
                                        </td>
                                        <td class="px-6 py-4 whitespace-nowrap">
                                            <div class="text-sm text-gray-900">
                                                <fmt:formatDate value="${donation.donationTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                            </div>
                                        </td>
                                        <td class="px-6 py-4 whitespace-nowrap">
                                            <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full
                                                ${donation.status eq 'COMPLETED' ? 'bg-green-100 text-green-800' :
                                                donation.status eq 'PENDING' ? 'bg-yellow-100 text-yellow-800' :
                                                'bg-red-100 text-red-800'}">
                                                ${donation.status}
                                            </span>
                                        </td>
                                        <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                                            <div class="flex space-x-3">
                                                <c:if test="${donation.status eq 'COMPLETED'}">
                                                    <form action="${pageContext.request.contextPath}/admin/donation/refund/${donation.donationId}"
                                                          method="post"
                                                          class="inline"
                                                          onsubmit="return confirm('Are you sure you want to refund this donation?');">
                                                        <button type="submit" class="text-red-600 hover:text-red-900">Refund</button>
                                                    </form>
                                                </c:if>
                                            </div>
                                        </td>
                                    </tr>
                                </c:forEach>
                            </c:when>
                            <c:otherwise>
                                <tr>
                                    <td colspan="5" class="px-6 py-4 text-center text-sm text-gray-500">
                                        No donations found.
                                    </td>
                                </tr>
                            </c:otherwise>
                        </c:choose>
                    </tbody>
                </table>
            </div>

            <!-- Pagination Controls -->
            <div class="mt-6 flex items-center justify-between border-t border-gray-200 bg-white px-4 py-3 sm:px-6">
                <div class="flex flex-1 justify-between sm:hidden">
                    <a href="?page=${currentPage - 1}"
                       class="relative inline-flex items-center rounded-md border border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-700 hover:bg-gray-50 ${currentPage == 1 ? 'opacity-50 cursor-not-allowed' : ''}">Previous</a>
                    <a href="?page=${currentPage + 1}"
                       class="relative ml-3 inline-flex items-center rounded-md border border-gray-300 bg-white px-4 py-2 text-sm font-medium text-gray-700 hover:bg-gray-50 ${currentPage == totalPages ? 'opacity-50 cursor-not-allowed' : ''}">Next</a>
                </div>
                <div class="hidden sm:flex sm:flex-1 sm:items-center sm:justify-between">
                    <div>
                        <p class="text-sm text-gray-700">
                            Showing <span class="font-medium">${(currentPage-1)*itemsPerPage + 1}</span> to
                            <span class="font-medium">${Math.min(currentPage*itemsPerPage, totalItems)}</span> of
                            <span class="font-medium">${totalItems}</span> results
                        </p>
                    </div>
                    <div>
                        <nav class="isolate inline-flex -space-x-px rounded-md shadow-sm" aria-label="Pagination">
                            <a href="?page=${currentPage - 1}"
                               class="relative inline-flex items-center rounded-l-md px-2 py-2 text-gray-400 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0 ${currentPage == 1 ? 'opacity-50 cursor-not-allowed' : ''}">
                                <span class="sr-only">Previous</span>
                                <svg class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
                                    <path fill-rule="evenodd"
                                          d="M12.79 5.23a.75.75 0 01-.02 1.06L8.832 10l3.938 3.71a.75.75 0 11-1.04 1.08l-4.5-4.25a.75.75 0 010-1.08l4.5-4.25a.75.75 0 011.06.02z"
                                          clip-rule="evenodd"/>
                                </svg>
                            </a>
                            <span class="relative inline-flex items-center px-4 py-2 text-sm font-semibold text-gray-900 ring-1 ring-inset ring-gray-300 focus:outline-offset-0">
                                Page ${currentPage} of ${totalPages}
                            </span>
                            <a href="?page=${currentPage + 1}"
                               class="relative inline-flex items-center rounded-r-md px-2 py-2 text-gray-400 ring-1 ring-inset ring-gray-300 hover:bg-gray-50 focus:z-20 focus:outline-offset-0 ${currentPage == totalPages ? 'opacity-50 cursor-not-allowed' : ''}">
                                <span class="sr-only">Next</span>
                                <svg class="h-5 w-5" viewBox="0 0 20 20" fill="currentColor" aria-hidden="true">
                                    <path fill-rule="evenodd"
                                          d="M7.21 14.77a.75.75 0 01.02-1.06L11.168 10 7.23 6.29a.75.75 0 111.04-1.08l4.5 4.25a.75.75 0 010 1.08l-4.5 4.25a.75.75 0 01-1.06-.02z"
                                          clip-rule="evenodd"/>
                                </svg>
                            </a>
                        </nav>
                    </div>
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

            if (searchInput.value) {
                searchInput.focus();
                searchInput.selectionStart = searchInput.selectionEnd = searchInput.value.length;
            }

            searchInput.addEventListener('input', function() {
                clearTimeout(searchTimeout);
                clearTimeout(emptyTimer);

                if (this.value.trim() === '') {
                    if (!isEmptyState) {
                        isEmptyState = true;
                        emptyTimer = setTimeout(() => {
                            searchForm.submit();
                        }, 5000);
                    }
                } else {
                    isEmptyState = false;
                    searchTimeout = setTimeout(() => {
                        searchForm.submit();
                    }, 500);
                }
            });

            searchInput.addEventListener('blur', function() {
                if (this.value.trim() === '' && isEmptyState) {
                    setTimeout(() => {
                        this.focus();
                    }, 10);
                }
            });
        });
    </script>
</body>
</html>