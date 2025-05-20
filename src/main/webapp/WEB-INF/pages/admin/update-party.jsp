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
    <title>VoteSphere - Update Party</title>
    <link rel="stylesheet" href="../../../../../../../../Downloads/Frontend%2010/styles/global.css" />
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
        <div class="bg-white rounded-lg shadow-md p-6">
          <div class="flex justify-between items-center mb-6">
            <h1 class="text-2xl font-bold text-gray-800">Update Party</h1>
            <a
              href="/admin/party"
              class="bg-gray-100 text-gray-600 px-4 py-2 rounded-lg hover:bg-gray-200 transition-colors duration-200"
            >
              Back to List
            </a>
          </div>

          <!-- Update Party Form -->
          <form action="${pageContext.request.contextPath}/admin/party/update/${party.partyId}" method="post" enctype="multipart/form-data" id="addPartyForm" class="space-y-6">
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div>
                <label class="block text-sm font-medium text-gray-700"
                  >Party Name</label
                >
                <input
                  type="text"
                  name="name"
                  value="${party.name}"
                  required
                  placeholder="Enter Party Name"
                  class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 placeholder:text-gray-400 p-2"
                />
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700"
                  >Leader Name</label
                >
                <input
                  type="text"
                  name="leader_name"
                  value="${party.leaderName}"
                  placeholder="Enter Leader Name"
                  class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 placeholder:text-gray-400 p-2"
                />
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700"
                  >Founder Name</label
                >
                <input
                  type="text"
                  name="founder_name"
                  value="${party.founderName}"
                  placeholder="Enter Founder Name"
                  class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 placeholder:text-gray-400 p-2"
                />
              </div>
<br>
               <div class="md:col-span-2">
                            <label class="block text-sm font-medium text-gray-700"
                              >Description</label
                            >
                            <textarea
                              name="description"

                              rows="3"
                              placeholder="Enter Party Description"
                              class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 placeholder:text-gray-400 p-2"
                            >${party.description}</textarea>
                          </div>

            <div>
              <label class="block text-sm font-medium text-gray-700">Symbol Image</label>

              <c:choose>
                <c:when test="${not empty party.symbolImage}">
                  <img src="/uploads/${party.symbolImage}" alt="Symbol Image" class="w-32 h-32 object-cover border mb-2" />
                </c:when>
                <c:otherwise>
                  <img src="https://placehold.co/600x400" alt="Placeholder Symbol" class="w-32 h-32 object-cover border mb-2" />
                </c:otherwise>
              </c:choose>

              <input
                type="file"
                accept="image/*"
                name="symbol_image"
                class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 placeholder:text-gray-400 p-2"
              />
            </div>

            <div>
              <label class="block text-sm font-medium text-gray-700">Cover Image</label>

              <c:choose>
                <c:when test="${not empty party.coverImage}">
                  <img src="/uploads/${party.coverImage}" alt="Cover Image" class="w-32 h-32 object-cover border mb-2" />
                </c:when>
                <c:otherwise>
                  <img src="https://placehold.co/600x400" alt="Placeholder Cover" class="w-32 h-32 object-cover border mb-2" />
                </c:otherwise>
              </c:choose>

              <input
                type="file"
                accept="image/*"
                name="cover_image"
                class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 placeholder:text-gray-400 p-2"
              />
            </div>
</div>


            <div class="flex justify-end space-x-3">
              <a
                href="/admin/party/update"
                class="bg-gray-100 text-gray-600 px-6 py-2 rounded-lg hover:bg-gray-200 transition-colors duration-200"
              >
                Cancel
              </a>
              <button
                type="submit"
                class="bg-primary-600 text-white px-6 py-2 rounded-lg hover:bg-primary-700 transition-colors duration-200"
              >
                Update Party
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>



  </body>
</html>
