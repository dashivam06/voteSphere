<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>VoteSphere - Candidate Details</title>
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
      class="flex-1 flex flex-col ml-0 lg:ml-64 transition-all duration-300 ease-in-out"
    >
      <!-- Include navbar -->
        		<%@ include file="navbar.jsp" %>

      <!-- Content Area -->
      <div class="p-8 overflow-y-auto">
        <div class="bg-white rounded-lg shadow-md p-6 mx-auto">
          <div class="flex justify-between items-center mb-6">
            <h1 class="text-2xl font-bold text-gray-800">Candidate Details</h1>
            <a
              href="/admin/candidate/"
              class="bg-gray-100 text-gray-600 px-4 py-2 rounded-lg hover:bg-gray-200 transition-colors duration-200"
              >Back to Candidates</a
            >
          </div>

          <!-- Candidate Header -->
          <div
            class="flex flex-col md:flex-row items-center md:items-start mb-8"
          >
            <div
              class="h-40 w-40 bg-gray-100 rounded-full overflow-hidden flex-shrink-0 border-4 border-white shadow-md mb-4 md:mb-0"
            >
              <img
                id="profile-image"
                src="/uploads/${candidate.profileImage}"
                alt="Candidate Profile"
                class="w-full h-full object-cover object-center"
              />
            </div>
            <div class="md:ml-6 text-center md:text-left">
              <h2 id="candidate-name" class="text-2xl font-bold text-gray-800">
                ${candidate.fname} ${candidate.lname}
              </h2>
              <div
                class="mt-2 flex flex-wrap justify-center md:justify-start gap-2"
              >
                <span
                  id="party-badge"
                  class="px-3 py-1 inline-flex text-sm leading-5 font-semibold rounded-full bg-blue-100 text-blue-800"
                >
                  ${candidate.partyName}
                </span>
                <span
                  id="independent-badge"
                  class="px-3 py-1 inline-flex text-sm leading-5 font-semibold rounded-full bg-gray-100 text-gray-800 hidden"
                >
                  Independent
                </span>
                <span
                  id="election-badge"
                  class="px-3 py-1 inline-flex text-sm leading-5 font-semibold rounded-full bg-green-100 text-green-800"
                >
                  ${candidate.electionName}
                </span>
              </div>

              <p class="text-gray-600 mt-2">
                              Candidate ID: <span id="candidate-id">${candidate.candidateId}</span>
                            </p>
            </div>
          </div>

          <!-- Personal Information Section -->
          <div class="mb-8">
            <h3 class="text-lg font-semibold text-gray-700 mb-4 border-b pb-2">
              Personal Information
            </h3>
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div>
                <span class="font-medium text-gray-700">First Name:</span>
                <p id="first-name" class="mt-1">${candidate.fname}</p>
              </div>
              <div>
                <span class="font-medium text-gray-700">Last Name:</span>
                <p id="last-name" class="mt-1">${candidate.lname}</p>
              </div>
              <div>
                <span class="font-medium text-gray-700">Date of Birth:</span>
                <p id="dob" class="mt-1">${candidate.dob}</p>
              </div>
              <div>
                <span class="font-medium text-gray-700">Gender:</span>
                <p id="gender" class="mt-1">${candidate.gender}</p>
              </div>
              <div>
                <span class="font-medium text-gray-700">Address:</span>
                <p id="address" class="mt-1">${candidate.address}</p>
              </div>
              <div>
                <span class="font-medium text-gray-700"
                  >Highest Education:</span
                >
                <p id="education" class="mt-1">${candidate.highestEducation}</p>
              </div>
            </div>
          </div>

          <!-- Biography Section -->
          <div class="mb-8">
            <h3 class="text-lg font-semibold text-gray-700 mb-4 border-b pb-2">
              Biography
            </h3>
            <div class="prose max-w-none">
              <p id="bio" class="text-gray-700">
                ${candidate.bio}
              </p>
            </div>
          </div>

          <!-- Manifesto Section -->
          <div class="mb-8">
            <h3 class="text-lg font-semibold text-gray-700 mb-4 border-b pb-2">
              Manifesto
            </h3>
            <div class="prose max-w-none">
              <p id="manifesto" class="text-gray-700">
               ${candidate.manifesto}
              </p>
            </div>
          </div>

          <!-- Election Information -->
          <div class="mb-8">
            <h3 class="text-lg font-semibold text-gray-700 mb-4 border-b pb-2">
              Election Information
            </h3>
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div>
                <span class="font-medium text-gray-700">Election:</span>
                <p id="election-name" class="mt-1">
                  ${candidate.electionName}
                </p>
              </div>
              <div>
                <span class="font-medium text-gray-700">Party:</span>
                <p id="party-name" class="mt-1">${candidate.partyName}</p>
              </div>
            </div>
          </div>

          <!-- Actions Section -->
          <div class="flex justify-end space-x-3">
          <a href="/admin/candidate/update/${candidate.candidateId}"
            <button
              class="bg-primary-600 text-white px-6 py-2 rounded-lg hover:bg-primary-700 transition-colors duration-200"
            >
              Update Details
            </button>
            </a>
             <form action="${pageContext.request.contextPath}/admin/candidate/delete/${candidate.candidateId}"
                                                                                                                method="POST"
                                                                                                                class="inline"
                                                                                                                onsubmit="return confirm('Are you sure you want to delete this candidate?');">

            <button
              class="bg-red-600 text-white px-6 py-2 rounded-lg hover:bg-red-700 transition-colors duration-200"
            >
                                                                                                              </form>

              Delete
            </button>
          </div>
        </div>
      </div>
    </div>


  </body>
</html>
