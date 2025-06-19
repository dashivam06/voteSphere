<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ include file="../loader-animation.jsp" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>VoteSphere - Add Election</title>
    <link rel="icon" src="${pageContext.request.contextPath}/resources/favicon.ico" type="image/x-icon" />

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
          <%@ include file="../navbar.jsp" %>

      <!-- Content Area -->
      <div class="p-8 overflow-y-auto">
        <div class="bg-white rounded-lg shadow-md p-6">
          <div class="flex justify-between items-center mb-6">
            <h1 class="text-2xl font-bold text-gray-800">Add New Election</h1>
            <a
              href="../../../../../../../../Downloads/Frontend%2010/admin/elections.html"
              class="bg-gray-100 text-gray-600 px-4 py-2 rounded-lg hover:bg-gray-200 transition-colors duration-200"
            >
              Back to List
            </a>
          </div>

          <!-- Add Election Form -->
          <form id="addElectionForm" action="/admin/election/add/" method="post" enctype="multipart/form-data" class="space-y-6" >
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
                  class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 placeholder:text-gray-400 p-2"
                />
              </div>
              <div>
                <label class="block text-sm font-medium text-gray-700">Symbol Image</label>

                <c:if test="${not empty party.symbolImage}">
                  <img src="${party.symbolImage}" alt="${party.name} Symbol Image"
                       id="symbolPreview" class="w-full max-w-md h-60 object-cover border mb-2 rounded-lg shadow-md" />
                </c:if>

                <input
                        type="file"
                        accept="image/*"
                        name="symbol_image"
                        id="symbolImageInput"
                        onchange="previewImage(this, 'symbolPreview', 'symbolWarning')"
                        class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 p-2"
                />
                <p id="symbolWarning" class="hidden text-sm text-red-600 mt-1">File too large (max 2MB)</p>
              </div>

              <div class="mt-6">
                <label class="block text-sm font-medium text-gray-700">Cover Image</label>

                <c:if test="${not empty party.coverImage}">
                  <img src="${party.coverImage}" alt="${party.name} Cover Image"
                       id="coverPreview" class="w-full max-w-md h-60 object-cover border mb-2 rounded-lg shadow-md" />
                </c:if>

                <input
                        type="file"
                        accept="image/*"
                        name="cover_image"
                        id="coverImageInput"
                        onchange="previewImage(this, 'coverPreview', 'coverWarning')"
                        class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 p-2"
                />
                <p id="coverWarning" class="hidden text-sm text-red-600 mt-1">File too large (max 2MB)</p>
              </div>
              <script>
                function previewImage(input, previewId, warningId) {
                  const preview = document.getElementById(previewId);
                  const warning = document.getElementById(warningId);

                  if (input.files && input.files[0]) {
                    const fileSizeMB = input.files[0].size / 1024 / 1024;

                    if (fileSizeMB > 2) {
                      warning.classList.remove('hidden');
                      preview.classList.add('hidden');
                      input.value = ""; // Clear file input
                      return;
                    }

                    warning.classList.add('hidden');
                    const reader = new FileReader();
                    reader.onload = function (e) {
                      preview.src = e.target.result;
                      preview.classList.remove('hidden');
                    };
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
                  required
                  class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 placeholder:text-gray-400 p-2"
                />
              </div>
            </div>

            <div class="flex justify-end space-x-3">
              <a
                href="../../../../../../../../Downloads/Frontend%2010/admin/elections.html"
                class="bg-gray-100 text-gray-600 px-6 py-2 rounded-lg hover:bg-gray-200 transition-colors duration-200"
              >
                Cancel
              </a>
              <button
                type="submit"
                class="bg-primary-600 text-white px-6 py-2 rounded-lg hover:bg-primary-700 transition-colors duration-200"
              >
                Add Election
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>


  </body>
</html>
