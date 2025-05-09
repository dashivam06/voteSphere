<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>VoteSphere - Add Candidate</title>
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
    <div id="sidebar-container"></div>

    <!-- Main Content -->
    <div
      class="flex-1 flex flex-col ml-0 lg:ml-64 transition-all duration-300 ease-in-out"
    >
      <!-- Include navbar -->
      <div id="navbar-container"></div>

      <!-- Content Area -->
      <div class="p-8 overflow-y-auto">
        <div class="bg-white rounded-lg shadow-md p-6">
          <div class="flex justify-between items-center mb-6">
            <h1 class="text-2xl font-bold text-gray-800">Add New Candidate</h1>
            <a
              href="../../../../../../../../Downloads/Frontend%2010/admin/candidates.html"
              class="bg-gray-100 text-gray-600 px-4 py-2 rounded-lg hover:bg-gray-200 transition-colors duration-200"
            >
              Back to List
            </a>
          </div>

          <!-- Add Candidate Form -->
          <form id="addCandidateForm" class="space-y-6">
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
              <!-- Basic Information -->

              <div>
                <label class="block text-sm font-medium text-gray-700"
                  >First Name</label
                >
                <input
                  type="text"
                  name="first_name"
                  required
                  placeholder="Enter First Name"
                  class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 placeholder:text-gray-400 p-2"
                />
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700"
                  >Last Name</label
                >
                <input
                  type="text"
                  name="last_name"
                  required
                  placeholder="Enter Last Name"
                  class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 placeholder:text-gray-400 p-2"
                />
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700"
                  >Date of Birth</label
                >
                <input
                  type="date"
                  name="dob"
                  required
                  placeholder="Enter Date of Birth"
                  class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 placeholder:text-gray-400 p-2"
                />
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700"
                  >Gender</label
                >
                <select
                  name="gender"
                  required
                  placeholder="Select Gender"
                  class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 placeholder:text-gray-400 p-2"
                >
                  <option value="">Select Gender</option>
                  <option value="Male">Male</option>
                  <option value="Female">Female</option>
                  <option value="Other">Other</option>
                </select>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700"
                  >Address</label
                >
                <input
                  name="address"
                  rows="2"
                  placeholder="Enter Address"
                  class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 placeholder:text-gray-400 p-2"
                />
              </div>
              <div>
                <label class="block text-sm font-medium text-gray-700"
                  >Profile Image URL</label
                >
                <input
                  type="file"
                  accept="image/*"
                  name="profile_image"
                  class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 placeholder:text-gray-400 p-2"
                />
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700"
                  >Highest Education</label
                >
                <input
                  type="text"
                  name="highest_education"
                  placeholder="Enter Highest Education"
                  class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 placeholder:text-gray-400 p-2"
                />
              </div>
            </div>

            <!-- Full Width Fields -->
            <div class="space-y-4">
              <div>
                <label class="block text-sm font-medium text-gray-700"
                  >Bio</label
                >
                <textarea
                  name="bio"
                  rows="3"
                  placeholder="Enter Bio"
                  class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 placeholder:text-gray-400 p-2"
                ></textarea>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700"
                  >Manifesto</label
                >
                <textarea
                  name="manifesto"
                  rows="4"
                  placeholder="Enter Manifesto"
                  class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 placeholder:text-gray-400 p-2"
                ></textarea>
              </div>

              <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                <div>
                  <label class="block text-sm font-medium text-gray-700"
                    >Party</label
                  >
                  <select
                    name="party_id"
                    required
                    placeholder="Select Party"
                    class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 placeholder:text-gray-400 p-2"
                  >
                    <option value="">Select Party</option>
                    <!-- Will be populated dynamically -->
                  </select>
                </div>

                <div>
                  <label class="block text-sm font-medium text-gray-700"
                    >Election</label
                  >
                  <select
                    name="election_id"
                    required
                    placeholder="Select Election"
                    class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 placeholder:text-gray-400 p-2"
                  >
                    <option value="">Select Election</option>
                    <!-- Will be populated dynamically -->
                  </select>
                </div>
              </div>

              <div class="flex items-center space-x-2 mb-4">
                <label class="flex items-center space-x-2">
                  <input
                    type="checkbox"
                    name="is_independent"
                    id="is_independent"
                    class="rounded border-gray-300 text-primary-600 shadow-sm focus:border-primary-500 focus:ring-primary-500"
                  />
                  <label
                    for="is_independent"
                    class="text-sm text-gray-700 text-nowrap"
                    >Independent Candidate</label
                  >
                </label>
              </div>
            </div>

            <div class="flex justify-end space-x-3">
              <a
                href="../../../../../../../../Downloads/Frontend%2010/admin/candidates.html"
                class="bg-gray-100 text-gray-600 px-6 py-2 rounded-lg hover:bg-gray-200 transition-colors duration-200"
              >
                Cancel
              </a>
              <button
                type="submit"
                class="bg-primary-600 text-white px-6 py-2 rounded-lg hover:bg-primary-700 transition-colors duration-200"
              >
                Add Candidate
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>

    <!-- Scripts -->
    <script>
      // Load sidebar and navbar
      document.addEventListener("DOMContentLoaded", function () {
        fetch("sidebar.html")
          .then((response) => response.text())
          .then((data) => {
            document.getElementById("sidebar-container").innerHTML = data;
          });

        fetch("navbar.html")
          .then((response) => response.text())
          .then((data) => {
            document.getElementById("navbar-container").innerHTML = data;
          });
      });

      // Form submission handler
      document
        .getElementById("addCandidateForm")
        .addEventListener("submit", function (e) {
          e.preventDefault();
          // Add your form submission logic here
          // This should include validation and API calls to save the candidate
        });
    </script>
  </body>
</html>
