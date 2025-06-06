<%@ page import="com.voteSphere.model.AuthUser" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ include file="../loader-animation.jsp" %>

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
  <title>VoteSphere - Voter Profile</title>
  <link rel="stylesheet" href="../styles/global.css" />
  <link rel="icon" src="/resources/favicon.ico" type="image/x-icon" />

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
    <div class="max-w-5xl mx-auto">
      <!-- Profile Header -->
      <div class="bg-white rounded-lg shadow-sm p-6 mb-6">
        <div class="flex flex-wrap items-center">
          <div
                  class="w-full sm:w-1/4 flex justify-center sm:justify-start mb-4 sm:mb-0"
          >
            <div class="relative">
              <div
                      class="h-32 w-32 rounded-full overflow-hidden bg-gray-200 border-4 border-white shadow"
              >
                <img
                        src="${user.profileImage}"
                        alt="Profile"
                        class="w-[120px] aspect-square rounded-full object-cover"
                />
              </div>
<%--              <button --%>
<%--                      class="absolute bottom-0 right-0 bg-primary-600 text-white p-2 rounded-full hover:bg-primary-700 transition-colors duration-200"--%>
<%--              >--%>
<%--                <svg--%>
<%--                        xmlns="http://www.w3.org/2000/svg"--%>
<%--                        class="h-4 w-4"--%>
<%--                        fill="none"--%>
<%--                        viewBox="0 0 24 24"--%>
<%--                        stroke="currentColor"--%>
<%--                >--%>
<%--                  <path--%>
<%--                          stroke-linecap="round"--%>
<%--                          stroke-linejoin="round"--%>
<%--                          stroke-width="2"--%>
<%--                          d="M3 9a2 2 0 012-2h.93a2 2 0 001.664-.89l.812-1.22A2 2 0 0110.07 4h3.86a2 2 0 011.664.89l.812 1.22A2 2 0 0018.07 7H19a2 2 0 012 2v9a2 2 0 01-2 2H5a2 2 0 01-2-2V9z"--%>
<%--                  />--%>
<%--                  <path--%>
<%--                          stroke-linecap="round"--%>
<%--                          stroke-linejoin="round"--%>
<%--                          stroke-width="2"--%>
<%--                          d="M15 13a3 3 0 11-6 0 3 3 0 016 0z"--%>
<%--                  />--%>
<%--                </svg>--%>
<%--              </button>--%>
            </div>
          </div>
          <div class="w-full sm:w-3/4 text-center sm:text-left">
            <h1 class="text-2xl font-bold text-gray-800">${user.fullName}</h1>
            <p class="text-gray-500">Voter Id: ${user.voterId}</p>
            <div
                    class="mt-2 flex flex-wrap justify-center sm:justify-start gap-2"
            >
              <c:choose>
                <c:when test="${user.isVerified}">
                     <span class="px-3 py-1 bg-green-100 text-green-800 rounded-full text-sm">
                       Verified
                     </span>
                </c:when>
                <c:otherwise>
                     <span class="px-3 py-1 bg-red-100 text-red-800 rounded-full text-sm">
                       Unverified
                     </span>
                </c:otherwise>
              </c:choose>

              <span
                      class="px-3 py-1 bg-blue-100 text-blue-800 rounded-full text-sm"
              >Active Voter</span
              >
            </div>
          </div>
        </div>
      </div>

      <!-- Tabs -->
      <div class="mb-6">
        <div class="border-b border-gray-200">
          <nav class="-mb-px flex">
            <button
                    class="tab-btn active-tab py-3 px-6 text-sm font-medium border-b-2 border-primary-500 text-primary-600"
                    data-tab="personal-info"
            >
              Personal Information
            </button>
            <button
                    class="tab-btn py-3 px-6 text-sm font-medium border-b-2 border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300"
                    data-tab="verification"
            >
              Verification Documents
            </button>
            <button
                    class="tab-btn py-3 px-6 text-sm font-medium border-b-2 border-transparent text-gray-500 hover:text-gray-700 hover:border-gray-300"
                    data-tab="voting-history"
            >
              Voting History
            </button>
          </nav>
        </div>
      </div>

      <!-- Tab Content -->
      <div id="personal-info" class="tab-content">
        <div class="bg-white rounded-lg shadow-sm p-6 mb-6">
          <div class="flex justify-between items-center mb-4">
            <h2 class="text-xl font-bold text-gray-800">
              Personal Information
            </h2>
            <button
                    class="text-primary-600 hover:text-primary-700 focus:outline-none flex items-center"
                    id="edit-profile-btn"
            >
              <svg
                      xmlns="http://www.w3.org/2000/svg"
                      class="h-4 w-4 mr-1"
                      fill="none"
                      viewBox="0 0 24 24"
                      stroke="currentColor"
              >
                <path
                        stroke-linecap="round"
                        stroke-linejoin="round"
                        stroke-width="2"
                        d="M15.232 5.232l3.536 3.536m-2.036-5.036a2.5 2.5 0 113.536 3.536L6.5 21.036H3v-3.572L16.732 3.732z"
                />
              </svg>
              Edit
            </button>
          </div>

          <!-- View Mode -->
          <div id="view-mode">
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div>
                <p class="text-sm text-gray-500 mb-1">First Name</p>
                <p class="text-gray-800">${user.firstName}</p>
              </div>
              <div>
                <p class="text-sm text-gray-500 mb-1">Last Name</p>
                <p class="text-gray-800">${user.lastName}</p>
              </div>
              <div>
                <p class="text-sm text-gray-500 mb-1">Email</p>
                <p class="text-gray-800">${user.email}</p>
              </div>
              <div>
                <p class="text-sm text-gray-500 mb-1">Date of Birth</p>
                <p class="text-gray-800">${user.dobInYear}</p>
              </div>
              <div>
                <p class="text-sm text-gray-500 mb-1">Voter ID</p>
                <p class="text-gray-800">${user.voterId}</p>
              </div>
              <div>
                <p class="text-sm text-gray-500 mb-1">Role</p>
                <p class="text-gray-800">${user.role}</p>
              </div>
              <div>
                <p class="text-sm text-gray-500 mb-1">Temporary Address</p>
                <p class="text-gray-800">
                  ${user.temporaryAddress}
                </p>
              </div>
              <div>
                <p class="text-sm text-gray-500 mb-1">Permanent Address</p>
                <p class="text-gray-800">
                  ${user.permanentAddress}
                </p>
              </div>
            </div>
          </div>

          <!-- Edit Mode (hidden by default) -->
          <div id="edit-mode" class="hidden">
            <form id="profile-form" action="${pageContext.request.contextPath}/update-user-detail" method="post" enctype="multipart/form-data">
              <div class="grid grid-cols-1 md:grid-cols-2 gap-6">


                <div>


                  <label class="block text-sm text-gray-500 mb-1"
                  >First Name</label
                  >
                  <input
                          type="text"
                          class="w-full p-2 border border-gray-300 rounded-md focus:ring-primary-500 focus:border-primary-500"
                          value="${user.firstName}"
                          name="firstName"
                  />
                </div>
                <div>
                  <label class="block text-sm text-gray-500 mb-1"
                  >Last Name</label
                  >
                  <input
                          type="text"
                          class="w-full p-2 border border-gray-300 rounded-md focus:ring-primary-500 focus:border-primary-500"
                          value="${user.lastName}"
                          name="lastName"
                  />
                </div>
                <div>
                  <label class="block text-sm text-gray-500 mb-1"
                  >Email</label
                  >
                  <input
                          type="email"
                          class="w-full p-2 border border-gray-300 rounded-md focus:ring-primary-500 focus:border-primary-500"
                          value="${user.email}"
                          name="email"
                  />
                </div>
                <div>
                  <label class="block text-sm text-gray-500 mb-1"
                  >Date of Birth</label
                  >
                  <input
                          type="date"
                          class="w-full p-2 border border-gray-300 rounded-md focus:ring-primary-500 focus:border-primary-500"
                          value="<fmt:formatDate value='${user.dob}' pattern='yyyy-MM-dd' />"
                          name="dob"
                          disabled
                  />

                  <p class="text-xs text-gray-500 mt-1">
                    Date of birth cannot be changed
                  </p>
                </div>
                <div>
                  <label class="block text-sm text-gray-500 mb-1"
                  >Temporary Address</label
                  >
                  <textarea
                          class="w-full p-2 border border-gray-300 rounded-md focus:ring-primary-500 focus:border-primary-500"
                          rows="2"
                          name="temporary_address"
                  >${user.temporaryAddress}</textarea
                  >
                </div>
                <div>
                  <label class="block text-sm text-gray-500 mb-1"
                  >Permanent Address</label
                  >
                  <textarea
                          class="w-full p-2 border border-gray-300 rounded-md focus:ring-primary-500 focus:border-primary-500"
                          rows="2"
                          name="permanent_address"
                  >${user.permanentAddress}</textarea
                  >
                </div>
                <div class="flex flex-col md:flex-row gap-4 md:col-span-2">
                  <!-- File Upload Section -->
                  <div class="flex flex-col gap-2 w-full md:w-1/2">
                    <label class="block text-sm text-gray-500 mb-1">Profile Picture</label>
                    <input
                            type="file"
                            name="profile_image"
                            accept="image/*"
                            onchange="previewImage(event)"
                            class="w-full p-2 border border-gray-300 rounded-md focus:ring-primary-500 focus:border-primary-500"
                    />
                  </div>

                  <!-- Image Preview Section -->
                  <div class="w-full md:w-1/2 flex items-center justify-center border border-dashed border-gray-300 rounded-md p-2">
                    <img
                            id="imagePreview"
                            src=""
                            alt="Image Preview"
                            class="max-h-40 rounded-md hidden"
                    />
                  </div>
                </div>
              </div>
              <div class="mt-6 flex justify-end space-x-3">
                <button
                        type="button"
                        class="px-4 py-2 border border-gray-300 rounded-md text-gray-700 hover:bg-gray-50"
                        id="cancel-edit-btn"
                >
                  Cancel
                </button>
                <button
                        type="submit"
                        class="px-4 py-2 bg-primary-600 text-white rounded-md hover:bg-primary-700"
                >
                  Save Changes
                </button>
              </div>
            </form>
          </div>
        </div>

        <!-- Change Password -->
        <div class="bg-white rounded-lg shadow-sm p-6 mb-6">
          <h2 class="text-xl font-bold text-gray-800 mb-4">
            Change Password
          </h2>
          <form id="password-form" action="/voter/password-change" method="post">
            <div class="grid grid-cols-1 gap-4 max-w-md">
              <div>
                <label class="block text-sm text-gray-500 mb-1"
                >Current Password</label
                >
                <input
                        type="password"
                        name="current_password"
                        class="w-full p-2 border border-gray-300 rounded-md focus:ring-primary-500 focus:border-primary-500"
                />
              </div>
              <div>
                <label class="block text-sm text-gray-500 mb-1"
                >New Password</label
                >
                <input
                        type="password"
                        name="new_password"
                        class="w-full p-2 border border-gray-300 rounded-md focus:ring-primary-500 focus:border-primary-500"
                />
              </div>
              <div>
                <label class="block text-sm text-gray-500 mb-1"
                >Confirm New Password</label
                >
                <input
                        type="password"
                        name="confirm_password"
                        class="w-full p-2 border border-gray-300 rounded-md focus:ring-primary-500 focus:border-primary-500"
                />
              </div>
            </div>
            <div class="mt-4">
              <button
                      type="submit"
                      class="px-4 py-2 bg-primary-600 text-white rounded-md hover:bg-primary-700"
              >
                Update Password
              </button>
            </div>
          </form>
        </div>
      </div>

      <!-- Verification Documents Tab -->
      <div id="verification" class="tab-content hidden">
        <div class="bg-white rounded-lg shadow-sm p-6 mb-6">
          <h2 class="text-xl font-bold text-gray-800 mb-4">
            Verification Documents
          </h2>

          <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
            <!-- Citizenship Document Front -->
            <div class="border border-gray-200 rounded-lg p-4">
              <h3 class="font-semibold text-gray-800 mb-2">
                Citizenship Document - Front
              </h3>
              <div class="aspect-video bg-gray-100 rounded-lg flex items-center justify-center mb-2">
                <c:choose>
                  <c:when test="${not empty user.citizenshipFront}">
                    <img src="${user.citizenshipFront}"
                         alt="Citizenship Front"
                         class="max-h-full object-contain"
                         onerror="this.onerror=null;this.src='https://placehold.co/600x400?text=Image+Not+Found'">
                  </c:when>
                  <c:otherwise>
                    <div class="text-gray-400">No image available</div>
                  </c:otherwise>
                </c:choose>
              </div>
              <div class="flex justify-between items-center">
                    <span class="text-green-600 text-sm font-medium"
                    >✓ Verified</span
                    >
              </div>
            </div>

            <!-- Citizenship Document Back -->
            <div class="border border-gray-200 rounded-lg p-4">
              <h3 class="font-semibold text-gray-800 mb-2">
                Citizenship Document - Back
              </h3>
              <div
                      class="aspect-video bg-gray-100 rounded-lg flex items-center justify-center mb-2"
              >
                <img
                        src="${user.citizenshipBack}"
                        alt="Citizenship Back"
                        class="max-h-full object-contain"
                />
              </div>
              <div class="flex justify-between items-center">
                    <span class="text-green-600 text-sm font-medium"
                    >✓ Verified</span
                    >
              </div>
            </div>

            <!-- Voter ID Card Front -->
            <div class="border border-gray-200 rounded-lg p-4">
              <h3 class="font-semibold text-gray-800 mb-2">
                Voter ID Card - Front
              </h3>
              <div
                      class="aspect-video bg-gray-100 rounded-lg flex items-center justify-center mb-2"
              >
                <img
                        src="${user.voterCardFront}"
                        alt="Voter ID Front"
                        class="max-h-full object-contain"
                />
              </div>
              <div class="flex justify-between items-center">
                    <span class="text-green-600 text-sm font-medium"
                    >✓ Verified</span
                    >
              </div>
            </div>

            <!-- Thumbprint -->
            <div class="border border-gray-200 rounded-lg p-4">
              <h3 class="font-semibold text-gray-800 mb-2">Thumbprint</h3>
              <div
                      class="aspect-square max-w-[200px] mx-auto bg-gray-100 rounded-lg flex items-center justify-center mb-2"
              >
                <img
                        src="${user.thumbPrint}"
                        alt="Thumbprint"
                        class="max-h-full object-contain"
                />
              </div>
              <div class="flex justify-between items-center">
                    <span class="text-green-600 text-sm font-medium"
                    >✓ Verified</span
                    >
              </div>
            </div>

            <!-- Citizenship Document Holding -->
            <div class="border border-gray-200 rounded-lg p-4">
              <h3 class="font-semibold text-gray-800 mb-2">
                Photo holding Citizenship
              </h3>
              <div
                      class="aspect-video bg-gray-100 rounded-lg flex items-center justify-center mb-2"
              >
                <img
                        src="${user.imageHoldingCitizenship}"
                        alt="Person holding Citizenship"
                        class="max-h-full object-contain"
                />
              </div>
              <div class="flex justify-between items-center">
                    <span class="text-green-600 text-sm font-medium"
                    >✓ Verified</span
                    >
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Voting History Tab -->
      <div id="voting-history" class="tab-content hidden">
        <div class="bg-white rounded-lg shadow-sm p-6 mb-6">
          <h2 class="text-xl font-bold text-gray-800 mb-4">
            Voting History
          </h2>

          <div class="overflow-x-auto">
            <table class="min-w-full divide-y divide-gray-200">
              <thead class="bg-gray-50">
              <tr>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Election
                </th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Date
                </th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Token
                </th>
                <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                  Status
                </th>
              </tr>
              </thead>
              <tbody class="bg-white divide-y divide-gray-200">
              <c:forEach items="${votingHistoryList}" var="history">
                <tr>
                  <td class="px-6 py-4 whitespace-nowrap">
                    <div class="flex items-center">
                      <div class="flex-shrink-0 h-10 w-10">
                        <img class="h-10 w-10 rounded-full"
                             src="${history.electionImageUrl}"
                             alt="${history.electionName}"
                             onerror="this.onerror=null;this.src='https://placehold.co/100x100'"/>
                      </div>
                      <div class="ml-4">
                        <div class="text-sm font-medium text-gray-900">
                            ${history.electionName}
                        </div>
                        <div class="text-xs text-gray-500">
                          Party: ${history.partyName}
                        </div>
                      </div>
                    </div>
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap">
                    <div class="text-sm text-gray-900">
                      <fmt:formatDate value="${history.votedAt}" pattern="MMM d, yyyy"/>
                    </div>
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap">
                    <div class="text-sm text-gray-900">
                        ${history.voteToken}
                    </div>
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap">
                    <c:choose>
                      <c:when test="${history.status == 'VOTE_CAST'}">
                                              <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800">
                                                  Vote Cast
                                              </span>
                      </c:when>
                      <c:when test="${history.status == 'PENDING'}">
                                              <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-yellow-100 text-yellow-800">
                                                  Pending
                                              </span>
                      </c:when>
                      <c:when test="${history.status == 'REJECTED'}">
                                              <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-red-100 text-red-800">
                                                  Rejected
                                              </span>
                      </c:when>
                      <c:otherwise>
                                              <span class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-gray-100 text-gray-800">
                                                  Unknown
                                              </span>
                      </c:otherwise>
                    </c:choose>
                  </td>
                </tr>
              </c:forEach>

              <%-- Show empty state if no history --%>
              <c:if test="${empty votingHistoryList}">
                <tr>
                  <td colspan="4" class="px-6 py-4 text-center text-sm text-gray-500">
                    No voting history found
                  </td>
                </tr>
              </c:if>
              </tbody>
            </table>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<script>

  // Tab switching
  document.addEventListener("DOMContentLoaded", function () {
    const tabButtons = document.querySelectorAll(".tab-btn");
    const tabContents = document.querySelectorAll(".tab-content");

    tabButtons.forEach((button) => {
      button.addEventListener("click", () => {
        // Remove active class from all buttons
        tabButtons.forEach((btn) => {
          btn.classList.remove("active-tab");
          btn.classList.remove("border-primary-500");
          btn.classList.remove("text-primary-600");
          btn.classList.add("border-transparent");
          btn.classList.add("text-gray-500");
        });

        // Add active class to clicked button
        button.classList.add("active-tab");
        button.classList.add("border-primary-500");
        button.classList.add("text-primary-600");
        button.classList.remove("border-transparent");
        button.classList.remove("text-gray-500");

        // Hide all tab contents
        tabContents.forEach((content) => {
          content.classList.add("hidden");
        });

        // Show the selected tab content
        const tabId = button.getAttribute("data-tab");
        document.getElementById(tabId).classList.remove("hidden");
      });
    });

    // Profile editing
    const editProfileBtn = document.getElementById("edit-profile-btn");
    const cancelEditBtn = document.getElementById("cancel-edit-btn");
    const viewMode = document.getElementById("view-mode");
    const editMode = document.getElementById("edit-mode");

    if (editProfileBtn && cancelEditBtn && viewMode && editMode) {
      editProfileBtn.addEventListener("click", () => {
        viewMode.classList.add("hidden");
        editMode.classList.remove("hidden");
      });

      cancelEditBtn.addEventListener("click", () => {
        editMode.classList.add("hidden");
        viewMode.classList.remove("hidden");
      });

    }


  });


  function previewImage(event) {
    const image = document.getElementById("imagePreview");
    const file = event.target.files[0];

    if (file) {
      image.src = URL.createObjectURL(file);
      image.classList.remove("hidden");
    } else {
      image.src = "";
      image.classList.add("hidden");
    }
  }
</script>
</body>
</html>
