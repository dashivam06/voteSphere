<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>VoteSphere - Update Candidate</title>
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
            <h1 class="text-2xl font-bold text-gray-800">Update Candidate</h1>
            <a
              href="/admin/candidate/"
              class="bg-gray-100 text-gray-600 px-4 py-2 rounded-lg hover:bg-gray-200 transition-colors duration-200"
            >
              Back to List
            </a>
          </div>

          <!-- Upadate Candidate Form -->
           <form
             action="${pageContext.request.contextPath}/admin/candidate/update/${candidate.candidateId}"
             method="POST"
             class="inline space-y-6"
             onsubmit="return confirm('Are you sure you want to update this candidate?');"
             enctype="multipart/form-data"
             id="addCandidateForm"
           >

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
                  value="${candidate.fname}"
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
                  value="${candidate.lname}"
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
                  value="${candidate.dob}"
                  placeholder="Enter Date of Birth"
                  class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 placeholder:text-gray-400 p-2"
                />
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700"
                  >Gender</label
                >
              <select name="gender" required class="...">
                <option value="">Select Gender</option>
                <option value="Male" ${candidate.gender == 'Male' ? 'selected' : ''}>Male</option>
                <option value="Female" ${candidate.gender == 'Female' ? 'selected' : ''}>Female</option>
                <option value="Other" ${candidate.gender == 'Other' ? 'selected' : ''}>Other</option>
              </select>

              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700"
                  >Address</label
                >
                <input
                  name="address"
                  rows="2"
                  value="${candidate.address}"
                  placeholder="Enter Address"
                  class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 placeholder:text-gray-400 p-2"
                />
              </div>
               <div>
                              <label class="block text-sm font-medium text-gray-700"
                                >Highest Education</label
                              >
                              <input
                                type="text"
                                value="${candidate.highestEducation}"
                                name="highest_education"
                                placeholder="Enter Highest Education"
                                class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 placeholder:text-gray-400 p-2"
                              />
                            </div>
           <div class="flex items-center justify-center gap-6 mt-6">
             <!-- Left: Current Picture -->
             <div class="text-left">
               <label class="block text-sm font-medium text-gray-700 mb-1">Current Picture</label>

              <c:choose>
                <c:when test="${not empty candidate.profileImage}">
                  <img src="/uploads/${candidate.profileImage}"
                       id="profileImagePreview"
                       alt="Image Preview"
                       class="w-32 h-32 object-cover rounded-md border border-gray-300 mx-auto"
                       style="display: inline;" />
                </c:when>
                <c:otherwise>
                  <img src="https://placehold.co/600x400"
                       id="profileImagePreview"
                       alt="Placeholder Image"
                       class="w-32 h-32 object-cover rounded-md border border-gray-300 mx-auto"
                       style="display: inline;" />
                </c:otherwise>
              </c:choose>



             </div>

             <!-- Right: Upload Input -->
             <div class="flex flex-col justify-center">
               <label for="profileImageInput" class="block text-sm font-medium text-gray-700">Upload New Profile Image</label>
               <input
                                 type="file"
                                 accept="image/*"
                                 name="candidate_profile_image"
                                 class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 placeholder:text-gray-400 p-2"
                               />
             </div>


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
                >${candidate.bio}</textarea>
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
                >${candidate.manifesto}</textarea>
              </div>

             <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                 <!-- Party Dropdown -->
                 <div>
                     <label class="block text-sm font-medium text-gray-700">Party</label>
                     <select
                         name="party_id"
                         required
                         class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 placeholder:text-gray-400 p-2"
                     >
                         <option value="${candidate.partyName}">Select Party</option>
                        <c:forEach items="${parties}" var="party">
                          <option value="${party.partyId}" ${party.partyId == candidate.partyId ? 'selected' : ''}>
                            ${party.name}
                          </option>
                        </c:forEach>

                     </select>
                 </div>

                 <!-- Election Dropdown -->
                 <div>
                     <label class="block text-sm font-medium text-gray-700">Election</label>
                     <select
                         name="election_id"
                         required
                         class="mt-1 block w-full rounded-md border-gray-300 shadow-sm focus:border-primary-500 focus:ring-primary-500 placeholder:text-gray-400 p-2"
                     >
                         <option value="${candidate.electionName}">Select Election</option>
                         <c:forEach items="${elections}" var="election">
                           <option value="${election.electionId}" ${election.electionId == candidate.electionId ? 'selected' : ''}>
                             ${election.name}
                           </option>
                         </c:forEach>
                     </select>
                 </div>
             </div>

              <div class="flex items-center space-x-2 mb-4">
                <label class="flex items-center space-x-2">
                 <input type="checkbox" name="is_independent"
                        ${candidate.isIndependent ? 'checked' : ''}
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
              href="/admin/candidate/"
                class="bg-gray-100 text-gray-600 px-6 py-2 rounded-lg hover:bg-gray-200 transition-colors duration-200"
              >
                Cancel
              </a>

             <button
                type="submit"
                class="bg-primary-600 text-white px-6 py-2 rounded-lg hover:bg-primary-700 transition-colors duration-200"
              >
                Update Candidate
              </button>
            </div>
          </form>
        </div>
      </div>
    </div>


  </body>
</html>
