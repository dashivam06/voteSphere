<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<fmt:setLocale value="ne_NP"/>
<fmt:setLocale value="en_US"/>

<div class="preloader" id="preloader">
    <%@ include file="../loader-animation.jsp" %>
</div>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        document.getElementById('preloader').style.display = 'none';
    });
</script>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <title>VoteSphere - Donation Management</title>
    
    <link rel="icon" src="${pageContext.request.contextPath}/resources/favicon.ico" type="image/x-icon" />
    <link href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap" rel="stylesheet"/>
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
                        sans: ["Poppins", "sans-serif"]
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
    <div class="flex-1 ml-64 pb-8  ">
    <%@ include file="../navbar.jsp" %>

        <div class= " bg-white rounded-lg shadow-md p-6  ">
            <div class="flex justify-between items-center mb-6 ">
                <h1 class="text-2xl font-bold text-gray-800">Donation Management</h1>
                <div class="flex space-x-4">
                    <div class="relative">
                        <form id="searchForm" action="${pageContext.request.contextPath}/admin/donation/search" method="post">
                            <input type="hidden" name="_csrf" value="${_csrf.token}"/>
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
            <div class="max-h-[calc(96vh-380px)] overflow-y-auto pb-8 mt-6">
                <table class="min-w-full divide-y border">
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
                                                         src="${not empty donation.user.profileImage ? donation.user.profileImage : 'default-profile.png'}"
                                                         alt="${donation.user.fullName}"/>
                                                </div>
                                                <div class="ml-4">
                                                    <div class="text-sm font-medium text-gray-900">${donation.user.fullName}</div>
                                                    <div class="text-sm text-gray-500">${donation.user.email}</div>
                                                </div>
                                            </div>
                                        </td>
                                        <td class="px-6 py-4 whitespace-nowrap">
                                            <div class="text-sm text-gray-900">
                                                <fmt:formatNumber value="${donation.donationEntry.amount}" type="currency" currencySymbol="Rs "/>
                                            </div>
                                        </td>
                                        <td class="px-6 py-4 whitespace-nowrap">
                                            <div class="text-sm text-gray-900">
                                                <fmt:formatDate value="${donation.donationEntry.donationTime}" pattern="yyyy-MM-dd HH:mm:ss"/>
                                            </div>
                                        </td>
                                        <td class="px-6 py-4 whitespace-nowrap">
                                            <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full
                                                ${donation.donationEntry.status eq 'COMPLETED' ? 'bg-green-100 text-green-800' :
                                                donation.donationEntry.status eq 'PENDING' ? 'bg-yellow-100 text-yellow-800' :
                                                'bg-red-100 text-red-800'}">
                                                ${donation.donationEntry.status}
                                            </span>
                                        </td>
                                        <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                                            <div class="flex space-x-3">
                                                <c:if test="${donation.donationEntry.status eq 'COMPLETED'}">
                                                    <form action="${pageContext.request.contextPath}/admin/donation/refund/${donation.donationEntry.donationId}"
                                                          method="post"
                                                          class="inline"
                                                          onsubmit="return confirm('Are you sure you want to refund this donation?');">
                                                                                                                  <input type="hidden" name="_csrf" value="${_csrf.token}"/>
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
        </div>
    </div>

    <script>
        const SEARCH_DELAY = 500;
        const EMPTY_SEARCH_DELAY = 5000;

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

            searchInput.addEventListener('input', function () {
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

                        searchInput.addEventListener('blur', function () {
                            if (this.value.trim() === '' && isEmptyState) {
                                setTimeout(() => {
                                    this.focus();
                                }, 10);
                            }
                        });
                    }, SEARCH_DELAY);
                }
            )
                ;

                searchForm.addEventListener('submit', function (e) {
                    e.preventDefault();
                    const formData = new FormData(this);
                    fetch(this.action, {
                        method: 'POST',
                        body: formData
                    })
                        .then(response => response.text())
                        .then(html => {
                            document.querySelector('tbody').innerHTML = html;
                        })
                        .catch(error => console.error('Error:', error));
                });
            });
        }
        });

    </script>
</body>
</html>