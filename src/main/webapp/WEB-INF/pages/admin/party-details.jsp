<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>VoteSphere - Party Details</title>
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
            <h1 class="text-2xl font-bold text-gray-800">Party Details</h1>
            <a
              href="/admin/party/"
              class="bg-gray-100 text-gray-600 px-4 py-2 rounded-lg hover:bg-gray-200 transition-colors duration-200"
              >Back to Parties</a
            >
          </div>

          <!-- Party Banner -->
          <div class="mb-8">
            <div class="h-48 w-full bg-gray-100 rounded-lg overflow-hidden">
<!-- Cover Image -->
<c:choose>
    <c:when test="${not empty party.coverImage}">
        <img
            id="cover-image"
            src="/uploads/${party.coverImage}"
            alt="Party Cover Image"
            class="w-full h-full object-cover object-center"
        />
    </c:when>
    <c:otherwise>
        <img
            id="cover-image"
            src="https://placehold.co/1200x400"
            alt="Party Cover Image"
            class="w-full h-full object-cover object-center"
        />
    </c:otherwise>
</c:choose>

            </div>
          </div>

          <!-- Party Header -->
          <div class="flex items-center mb-8">
            <div
              class="h-24 w-24 bg-gray-100 rounded-lg overflow-hidden flex-shrink-0 border-4 border-white shadow-md"
            >
           <c:choose>
               <c:when test="${not empty party.symbolImage}">
                   <img
                       id="symbol-image"
                       src="/uploads/${party.symbolImage}"
                       alt="party Symbol"
                       class="w-full h-full object-cover object-center"
                   />
               </c:when>
               <c:otherwise>
                   <img
                       id="symbol-image"
                       src="https://placehold.co/200x200"
                       alt="party Symbol"
                       class="w-full h-full object-cover object-center"
                   />
               </c:otherwise>
           </c:choose>


            </div>
            <div class="ml-6">
              <h2 id="party-name" class="text-2xl font-bold text-gray-800">
                ${party.name}
              </h2>
              <p class="text-gray-600">
                Party ID: <span id="party-id">${party.partyId}</span>
              </p>
            </div>
          </div>

          <!-- Party Information -->
          <div class="mb-8">
            <h3 class="text-lg font-semibold text-gray-700 mb-4 border-b pb-2">
              Party Information
            </h3>
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div>
                <span class="font-medium text-gray-700">Leader Name:</span>
                <p id="leader-name" class="mt-1">${party.leaderName}</p>
              </div>
              <div>
                <span class="font-medium text-gray-700">Founder Name:</span>
                <p id="founder-name" class="mt-1">${party.founderName}</p>
              </div>
            </div>
          </div>

          <!-- Party Description -->
          <div class="mb-8">
            <h3 class="text-lg font-semibold text-gray-700 mb-4 border-b pb-2">
              Description
            </h3>
            <div class="prose max-w-none">
              <p id="description" class="text-gray-700">
                ${party.description}
              </p>
            </div>
          </div>
 <!-- Party Candidates -->
 <div class="mb-8">
     <h3 class="text-lg font-semibold text-gray-700 mb-4 border-b pb-2">
         Party Candidates
     </h3>
     <div class="overflow-x-auto">
         <table class="min-w-full divide-y divide-gray-200">
             <thead class="bg-gray-50">
                 <tr>
                     <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                         Name
                     </th>
                     <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                         Election
                     </th>
                     <th class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
                         Actions
                     </th>
                 </tr>
             </thead>
             <tbody class="bg-white divide-y divide-gray-200">
                 <c:choose>
                     <c:when test="${not empty partyCandidates}">
                         <c:forEach items="${partyCandidates}" var="candidate">
                             <tr>
                                 <td class="px-6 py-4 whitespace-nowrap">
                                     <div class="flex items-center">
                                         <div class="h-10 w-10 flex-shrink-0 rounded-full overflow-hidden">
                                             <img
                                                 src="${not empty candidate.profileImage ? pageContext.request.contextPath.concat('/uploads/').concat(candidate.profileImage) : 'https://placehold.co/100x100'}"
                                                 alt="Candidate"
                                                 class="h-full w-full object-cover"
                                             />
                                         </div>
                                         <div class="ml-4">
                                             <div class="text-sm font-medium text-gray-900">
                                                 ${candidate.fname} ${candidate.lname}
                                             </div>
                                         </div>
                                     </div>
                                 </td>
                                 <td class="px-6 py-4 whitespace-nowrap">
                                     <div class="text-sm text-gray-900">
                                         ${candidate.electionName}
                                     </div>
                                 </td>
                                 <td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
                                     <a
                                         href="${pageContext.request.contextPath}/admin/candidate/view/${candidate.candidateId}"
                                         class="text-primary-600 hover:text-primary-900"
                                     >
                                         View
                                     </a>
                                 </td>
                             </tr>
                         </c:forEach>
                     </c:when>
                     <c:otherwise>
                         <tr>
                             <td colspan="3" class="px-6 py-4 text-center text-sm text-gray-500">
                                 No candidates found for this party.
                             </td>
                         </tr>
                     </c:otherwise>
                 </c:choose>
             </tbody>
         </table>
     </div>
         </div>

          <!-- Actions Section -->
          <div class="flex justify-end space-x-3">
                    <a href="/admin/party/update/${party.partyId}"

            <button
              class="bg-primary-600 text-white px-6 py-2 rounded-lg hover:bg-primary-700 transition-colors duration-200"
            >
              Update Party Details
            </button>
            </a>

           <form action="${pageContext.request.contextPath}/admin/party/delete/${party.partyId}"
                                                                                                                          method="POST"
                                                                                                                          class="inline"
                                                                                                                          onsubmit="return confirm('Are you sure you want to delete this party?');">

                      <button
                        class="bg-red-600 text-white px-6 py-2 rounded-lg hover:bg-red-700 transition-colors duration-200"
                      >
                                                                                                                        </form>

                        Delete
                      </button>
            </a>
          </div>
        </div>
      </div>
    </div>

  </body>
</html>
