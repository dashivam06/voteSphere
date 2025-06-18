<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page isELIgnored="false" %>
<%@ include file="../loader-animation.jsp" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>VoteSphere - Update Election</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="icon" src="${pageContext.request.contextPath}/resources/favicon.ico" type="image/x-icon" />

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
          <%@ include file="../navbar.jsp" %>

      <!-- Content Area -->
      <div class="p-8 overflow-y-auto">
        <div class="bg-white rounded-lg shadow-md p-6">
          <div class="flex justify-between items-center mb-6">
            <h1 class="text-2xl font-bold text-gray-800">Update ${election.name} </h1>
            <a
              href="/admin/election/"
              class="bg-gray-100 text-gray-600 px-4 py-2 rounded-lg hover:bg-gray-200 transition-colors duration-200"
            >
              Back to List
            </a>
          </div>

          <!-- Update Election Form -->
          <form id="addElectionForm" action="/admin/election/update/" method="post" enctype="multipart/form-data" class="space-y-6" >
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div>
                <label class="block text-sm font-medium text-gray-700"
                  >Election Name</label
                >
                <input
                  type="text"
                  name="name"
                  required
                  placeholder="Enter Election Name"
                  value="${election.name}"
                  class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 placeholder:text-gray-400 p-2"
                />
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700"
                  >Type</label
                >
                <input
                  type="text"
                  name="type"
                  required
                  placeholder="Enter Election Type"
                  value="${election.type}"
                  class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 placeholder:text-gray-400 p-2"
                />
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700"
                  >Cover Image</label
                >
                
                <input
                  type="file"
                  accept="image/*"
                  name="cover_image"
                  id="coverImage"
                  value="${election.coverImage}"
                  required
                  onchange="previewImage(this)"
                  class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 placeholder:text-gray-400 p-2"
                />
                <div id="imagePreview" class="mt-4 relative">
                  <img id="preview" src="${election.coverImage}" alt="Preview"
                       class="w-full max-w-md h-48 object-cover rounded-lg shadow-lg"/>
                  <div id="sizeError" class="hidden absolute inset-0">
                    <div class="absolute inset-0 bg-black bg-opacity-70 backdrop-blur-sm rounded-lg flex items-center justify-center">
                      <div class="bg-white p-6 rounded-xl shadow-xl max-w-sm mx-4 text-center transform transition-all duration-300">
                        <div class="mx-auto flex items-center justify-center h-12 w-12 rounded-full bg-red-100 mb-4">
                          <svg class="h-6 w-6 text-red-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                  d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"/>
                          </svg>
                        </div>
                        <h3 class="text-lg font-semibold text-gray-900">Image Too Large</h3>
                        <p class="text-sm text-gray-500 mt-2">Please choose an image smaller than 2MB</p>
                      </div>
                    </div>
                  </div>
                </div>
                <script>
                  function previewImage(input) {
                    const preview = document.getElementById('preview');
                    const sizeError = document.getElementById('sizeError');
                    const submitButton = document.querySelector('button[type="submit"]');

                    if (input.files && input.files[0]) {
                      const reader = new FileReader();
                      const fileSize = input.files[0].size / 1024 / 1024; // in MB

                      reader.onload = function (e) {
                        preview.src = e.target.result;
                        if (fileSize > 2) {
                          sizeError.classList.remove('hidden');
                          submitButton.disabled = true;
                          submitButton.classList.add('opacity-50', 'cursor-not-allowed');
                        } else {
                          sizeError.classList.add('hidden');
                          submitButton.disabled = false;
                          submitButton.classList.remove('opacity-50', 'cursor-not-allowed');
                        }
                      }

                      reader.readAsDataURL(input.files[0]);
                    }
                  }
                </script>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700"
                  >Date</label
                >
                <input
                  type="date"
                  name="date"
                  value="${election.date}"
                  required
                  class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 placeholder:text-gray-400 p-2"
                />
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700"
                  >Start Time</label
                >
                <input
                  type="time"
                  name="start_time"
                  value="${election.startTime}"
                  required
                  class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 placeholder:text-gray-400 p-2"
                />
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700"
                  >End Time</label
                >
                <input
                  type="time"
                  name="end_time"
                  value="${election.endTime}"
                  required
                  class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 placeholder:text-gray-400 p-2"
                />
              </div>
            </div>

            <div class="flex justify-end space-x-3">
              <a
                href="/admin/election/"
                class="bg-gray-100 text-gray-600 px-6 py-2 rounded-lg hover:bg-gray-200 transition-colors duration-200"
              >
                Cancel
              </a>
              <button
                type="submit"
                class="bg-primary-600 text-white px-6 py-2 rounded-lg hover:bg-primary-700 transition-colors duration-200"
              >
                Update Election
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>


  </body>
</html>
