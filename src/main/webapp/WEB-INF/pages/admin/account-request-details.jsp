
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title>VoteSphere - Account Request Details</title>
  <link rel="stylesheet" href="../styles/global.css" />
  <script src="https://cdn.tailwindcss.com"></script>
  <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet" />
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
  <style>
    /* Document preview styles */
    .document-preview {
      transition: transform 0.3s ease, box-shadow 0.3s ease;
    }
    .document-preview:hover {
      transform: scale(1.02);
      box-shadow: 0 10px 15px -3px rgba(0, 0, 0, 0.1), 0 4px 6px -2px rgba(0, 0, 0, 0.05);
    }

    /* Modal styles */
    .modal {
      transition: opacity 0.3s ease, visibility 0.3s ease;
    }
    .modal-content {
      transition: transform 0.3s ease;
    }
    .modal.active {
      visibility: visible;
      opacity: 1;
    }
    .modal.active .modal-content {
      transform: translateY(0);
    }

    /* Tooltip styles */
    .tooltip {
      position: relative;
    }
    .tooltip:hover .tooltip-text {
      visibility: visible;
      opacity: 1;
    }
    .tooltip-text {
      visibility: hidden;
      opacity: 0;
      width: max-content;
      max-width: 200px;
      background-color: #333;
      color: #fff;
      text-align: center;
      border-radius: 6px;
      padding: 5px 10px;
      position: absolute;
      z-index: 1;
      bottom: 125%;
      left: 50%;
      transform: translateX(-50%);
      transition: opacity 0.3s;
    }
    .tooltip-text::after {
      content: "";
      position: absolute;
      top: 100%;
      left: 50%;
      margin-left: -5px;
      border-width: 5px;
      border-style: solid;
      border-color: #333 transparent transparent transparent;
    }

    /* Progress bar styles */
    .progress-bar {
      height: 8px;
      border-radius: 4px;
      background-color: #e5e7eb;
      overflow: hidden;
    }
    .progress-bar-fill {
      height: 100%;
      background-color: #0ea5e9;
      transition: width 0.5s ease;
    }

    /* Document card styles */
    .document-card {
      border: 1px solid #e5e7eb;
      border-radius: 0.5rem;
      transition: all 0.3s ease;
    }
    .document-card:hover {
      border-color: #0ea5e9;
      box-shadow: 0 4px 6px -1px rgba(0, 0, 0, 0.1), 0 2px 4px -1px rgba(0, 0, 0, 0.06);
    }
    .document-card.active {
      border-color: #0ea5e9;
      background-color: #f0f9ff;
    }

    /* Zoom controls */
    .zoom-controls {
      position: absolute;
      bottom: 1rem;
      right: 1rem;
      display: flex;
      gap: 0.5rem;
    }
    .zoom-btn {
      background-color: rgba(255, 255, 255, 0.8);
      border-radius: 50%;
      width: 2.5rem;
      height: 2.5rem;
      display: flex;
      align-items: center;
      justify-content: center;
      cursor: pointer;
      transition: all 0.2s ease;
    }
    .zoom-btn:hover {
      background-color: rgba(255, 255, 255, 1);
      transform: scale(1.05);
    }
  </style>
</head>
<body class="font-sans bg-gray-100 flex h-screen overflow-hidden">
  <!-- Include sidebar -->
  <jsp:include page="sidebar.jsp" />

  <!-- Main Content -->
  <div class="flex-1 flex flex-col ml-0 lg:ml-64 transition-all duration-300 ease-in-out">
    <!-- Include navbar -->
    <jsp:include page="navbar.jsp" />

    <!-- Content Area -->
    <div class="p-4 md:p-8 overflow-y-auto">
      <!-- Header with breadcrumbs and status -->
      <div class="mb-6">
        <div class="flex flex-col md:flex-row md:items-center md:justify-between gap-4 mb-4">
          <div>
            <div class="flex items-center text-sm text-gray-500 mb-2">
              <a href="dashboard.jsp" class="hover:text-primary-600">Dashboard</a>
              <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 mx-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
              </svg>
              <a href="account-requests.jsp" class="hover:text-primary-600">Account Requests</a>
              <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 mx-2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 5l7 7-7 7" />
              </svg>
              <span class="font-medium text-gray-700">Request Details</span>
            </div>
            <h1 class="text-2xl font-bold text-gray-800">Account Request Details</h1>
          </div>
          <div class="flex items-center gap-3">
            <span class="bg-yellow-100 text-yellow-800 text-xs font-medium px-2.5 py-0.5 rounded-full flex items-center">
              <svg xmlns="http://www.w3.org/2000/svg" class="h-3.5 w-3.5 mr-1" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
              </svg>
              Pending Verification
            </span>
            <a href="account-requests.jsp" class="bg-gray-100 text-gray-600 px-4 py-2 rounded-lg hover:bg-gray-200 transition-colors duration-200 flex items-center">
              <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 mr-1" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18" />
              </svg>
              Back to List
            </a>
          </div>
        </div>

               </div>

      <div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
        <!-- Left Column: Personal Information -->
        <div class="lg:col-span-1">
          <div class="bg-white rounded-xl shadow-md overflow-hidden mb-6">
            <div class="p-6">
              <div class="flex items-center justify-between mb-4">
                <h2 class="text-lg font-semibold text-gray-800">Personal Information</h2>
                <span class="text-xs bg-primary-100 text-primary-800 px-2 py-1 rounded">Voter</span>
              </div>

              <div class="flex flex-col items-center mb-6">
                <div class="relative w-24 h-24 rounded-full overflow-hidden mb-3 border-4 border-primary-100">
                  <img src="/images/${user.profileImage}" alt="Profile" class="w-full h-full object-cover" />
                  <button class="absolute bottom-0 right-0 bg-primary-500 text-white p-1 rounded-full w-6 h-6 flex items-center justify-center" onclick="openDocumentPreview('https://hebbkx1anhila5yf.public.blob.vercel-storage.com/image-k8QLAvWE5XYRv8cM02f2QJivHcM8pA.png', 'Profile Image')">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                      <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
                    </svg>
                  </button>
                </div>
                <h3 class="text-xl font-semibold text-gray-800">John Doe</h3>
                <p class="text-gray-500 text-sm">Registered on Jun 01, 2024</p>
              </div>

              <div class="space-y-4">
                <div class="flex justify-between items-center pb-2 border-b border-gray-100">
                  <span class="text-gray-500">Voter ID</span>
                  <span class="font-medium text-gray-800">${user.voterId}</span>
                </div>
                <div class="flex justify-between items-center pb-2 border-b border-gray-100">
                  <span class="text-gray-500">Email</span>
                  <span class="font-medium text-gray-800">${user.notificationEmail}</span>
                </div>
                <div class="flex justify-between items-center pb-2 border-b border-gray-100">
                  <span class="text-gray-500">Date of Birth</span>
                  <span class="font-medium text-gray-800">                                                                                                           <fmt:formatDate value="${user.dob}" pattern="dd MMM yyyy" />
</span>
                </div>
                <div class="flex justify-between items-center pb-2 border-b border-gray-100">
                  <span class="text-gray-500">Gender</span>
                  <span class="font-medium text-gray-800">${user.gender}</span>
                </div>
                <div class="flex justify-between items-center pb-2 border-b border-gray-100">
                  <span class="text-gray-500">Phone</span>
                  <span class="font-medium text-gray-800">${user.phoneNumber}</span>
                </div>
              </div>
            </div>
          </div>

          <!-- Address Information -->
          <div class="bg-white rounded-xl shadow-md overflow-hidden mb-6">
            <div class="p-6">
              <h2 class="text-lg font-semibold text-gray-800 mb-4">Address Information</h2>

              <div class="space-y-4">
                <div>
                  <h3 class="text-sm font-medium text-gray-500 mb-1">Temporary Address</h3>
                  <p class="text-gray-800">${user.temporaryAddress}</p>
                </div>
                <div>
                  <h3 class="text-sm font-medium text-gray-500 mb-1">Permanent Address</h3>
                  <p class="text-gray-800">${user.permanentAddress}</p>
                </div>
              </div>
            </div>
          </div>


        </div>

        <!-- Right Column: Document Verification -->
        <div class="lg:col-span-2">
          <!-- Document Verification Section -->
          <div class="bg-white rounded-xl shadow-md overflow-hidden mb-6">
            <div class="p-6">
              <h2 class="text-lg font-semibold text-gray-800 mb-4">Verification Documents</h2>
              <p class="text-gray-500 mb-6">Review all submitted documents for verification</p>

              <!-- Document Tabs -->
              <div class="border-b border-gray-200 mb-6">
                <ul class="flex flex-wrap -mb-px text-sm font-medium text-center">
                  <li class="mr-2">
                    <a href="#" class="inline-block p-4 border-b-2 border-primary-500 text-primary-600 rounded-t-lg active" onclick="switchTab('citizenship'); return false;">
                      <svg xmlns="/images/${user.profileImage}" class="h-5 w-5 inline-block mr-1" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 6H5a2 2 0 00-2 2v9a2 2 0 002 2h14a2 2 0 002-2V8a2 2 0 00-2-2h-5m-4 0V5a2 2 0 114 0v1m-4 0a2 2 0 104 0m-5 8a2 2 0 100-4 2 2 0 000 4zm0 0c1.306 0 2.417.835 2.83 2M9 14a3.001 3.001 0 00-2.83 2M15 11h3m-3 4h2" />
                      </svg>
                      Citizenship Document
                    </a>
                  </li>
                  <li class="mr-2">
                    <a href="#" class="inline-block p-4 border-b-2 border-transparent hover:text-gray-600 hover:border-gray-300 rounded-t-lg" onclick="switchTab('voter'); return false;">
                      <svg xmlns="temporaryAddress" class="h-5 w-5 inline-block mr-1" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z" />
                      </svg>
                      Voter ID Card
                    </a>
                  </li>
                  <li>
                    <a href="#" class="inline-block p-4 border-b-2 border-transparent hover:text-gray-600 hover:border-gray-300 rounded-t-lg" onclick="switchTab('additional'); return false;">
                      <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 inline-block mr-1" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z" />
                      </svg>
                      Additional Documents
                    </a>
                  </li>
                </ul>
              </div>

              <!-- Citizenship Documents Tab -->
              <div id="citizenship-tab" class="tab-content">
                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                  <div class="document-card p-4">
                    <div class="flex items-center justify-between mb-3">
                      <h3 class="font-medium text-gray-700">Citizenship Front</h3>
                      <span class="text-xs bg-green-100 text-green-800 px-2 py-0.5 rounded">Verified</span>
                    </div>
                    <div class="relative aspect-[4/3] bg-gray-100 rounded-md overflow-hidden mb-3">
                      <img src="/images/${user.citizenshipFront}" alt="Citizenship Front" class="w-full h-full object-cover" />
                      <div class="absolute inset-0 flex items-center justify-center bg-black bg-opacity-40 opacity-0 hover:opacity-100 transition-opacity">
                        <button class="bg-white text-gray-800 px-3 py-1.5 rounded-lg text-sm font-medium hover:bg-gray-100" onclick="openDocumentPreview('/placeholder.svg?height=800&width=1200', 'Citizenship Front')">
                          <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 inline-block mr-1" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
                          </svg>
                          View Full Size
                        </button>
                      </div>
                    </div>
                    <div class="flex items-center justify-between text-sm">
                      <span class="text-gray-500">Uploaded: 2024-06-01</span>
                      <div class="flex items-center">
                        <button class="text-primary-600 hover:text-primary-800 mr-3" title="Download">
                          <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16v1a3 3 0 003 3h10a3 3 0 003-3v-1m-4-4l-4 4m0 0l-4-4m4 4V4" />
                          </svg>
                        </button>
                        <div class="tooltip">
                          <button class="text-gray-500 hover:text-gray-700" title="More Info">
                            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                            </svg>
                          </button>
                          <span class="tooltip-text">Document verified by system on 2024-06-02</span>
                        </div>
                      </div>
                    </div>
                  </div>

                  <div class="document-card p-4">
                    <div class="flex items-center justify-between mb-3">
                      <h3 class="font-medium text-gray-700">Citizenship Back</h3>
                      <span class="text-xs bg-green-100 text-green-800 px-2 py-0.5 rounded">Verified</span>
                    </div>
                    <div class="relative aspect-[4/3] bg-gray-100 rounded-md overflow-hidden mb-3">
                      <img src="/images/${user.citizenshipBack}" alt="Citizenship Back" class="w-full h-full object-cover" />
                      <div class="absolute inset-0 flex items-center justify-center bg-black bg-opacity-40 opacity-0 hover:opacity-100 transition-opacity">
                        <button class="bg-white text-gray-800 px-3 py-1.5 rounded-lg text-sm font-medium hover:bg-gray-100" onclick="openDocumentPreview('/placeholder.svg?height=800&width=1200', 'Citizenship Back')">
                          <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 inline-block mr-1" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
                          </svg>
                          View Full Size
                        </button>
                      </div>
                    </div>
                    <div class="flex items-center justify-between text-sm">
                      <span class="text-gray-500">Uploaded: 2024-06-01</span>
                      <div class="flex items-center">
                        <button class="text-primary-600 hover:text-primary-800 mr-3" title="Download">
                          <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16v1a3 3 0 003 3h10a3 3 0 003-3v-1m-4-4l-4 4m0 0l-4-4m4 4V4" />
                          </svg>
                        </button>
                        <div class="tooltip">
                          <button class="text-gray-500 hover:text-gray-700" title="More Info">
                            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                            </svg>
                          </button>
                          <span class="tooltip-text">Document verified by system on 2024-06-02</span>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Voter ID Card Tab -->
              <div id="voter-tab" class="tab-content hidden">
                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                  <div class="document-card p-4">
                    <div class="flex items-center justify-between mb-3">
                      <h3 class="font-medium text-gray-700">Voter Card Front</h3>
                      <span class="text-xs bg-yellow-100 text-yellow-800 px-2 py-0.5 rounded">Pending</span>
                    </div>
                    <div class="relative aspect-[4/3] bg-gray-100 rounded-md overflow-hidden mb-3">
                      <img src="/images/${user.voterCardFront}" alt="Voter Card Front" class="w-full h-full object-cover" />
                      <div class="absolute inset-0 flex items-center justify-center bg-black bg-opacity-40 opacity-0 hover:opacity-100 transition-opacity">
                        <button class="bg-white text-gray-800 px-3 py-1.5 rounded-lg text-sm font-medium hover:bg-gray-100" onclick="openDocumentPreview('/placeholder.svg?height=800&width=1200', 'Voter Card Front')">
                          <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 inline-block mr-1" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
                          </svg>
                          View Full Size
                        </button>
                      </div>
                    </div>
                    <div class="flex items-center justify-between text-sm">
                      <span class="text-gray-500">Uploaded: 2024-06-01</span>
                      <div class="flex items-center">
                        <button class="text-primary-600 hover:text-primary-800 mr-3" title="Download">
                          <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16v1a3 3 0 003 3h10a3 3 0 003-3v-1m-4-4l-4 4m0 0l-4-4m4 4V4" />
                          </svg>
                        </button>
                        <div class="tooltip">
                          <button class="text-gray-500 hover:text-gray-700" title="More Info">
                            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                            </svg>
                          </button>
                          <span class="tooltip-text">Awaiting manual verification</span>
                        </div>
                      </div>
                    </div>
                  </div>

                  <div class="document-card p-4">
                    <div class="flex items-center justify-between mb-3">
                      <h3 class="font-medium text-gray-700">Voter Card Back</h3>
                      <span class="text-xs bg-yellow-100 text-yellow-800 px-2 py-0.5 rounded">Pending</span>
                    </div>
                    <div class="relative aspect-[4/3] bg-gray-100 rounded-md overflow-hidden mb-3">
                      <img src="/images/${user.voterCardBack}" alt="Voter Card Back" class="w-full h-full object-cover" />
                      <div class="absolute inset-0 flex items-center justify-center bg-black bg-opacity-40 opacity-0 hover:opacity-100 transition-opacity">
                        <button class="bg-white text-gray-800 px-3 py-1.5 rounded-lg text-sm font-medium hover:bg-gray-100" onclick="openDocumentPreview('/placeholder.svg?height=800&width=1200', 'Voter Card Back')">
                          <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 inline-block mr-1" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
                          </svg>
                          View Full Size
                        </button>
                      </div>
                    </div>
                    <div class="flex items-center justify-between text-sm">
                      <span class="text-gray-500">Uploaded: 2024-06-01</span>
                      <div class="flex items-center">
                        <button class="text-primary-600 hover:text-primary-800 mr-3" title="Download">
                          <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16v1a3 3 0 003 3h10a3 3 0 003-3v-1m-4-4l-4 4m0 0l-4-4m4 4V4" />
                          </svg>
                        </button>
                        <div class="tooltip">
                          <button class="text-gray-500 hover:text-gray-700" title="More Info">
                            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                            </svg>
                          </button>
                          <span class="tooltip-text">Awaiting manual verification</span>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>

              <!-- Additional Documents Tab -->
              <div id="additional-tab" class="tab-content hidden">
                <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
                  <div class="document-card p-4">
                    <div class="flex items-center justify-between mb-3">
                      <h3 class="font-medium text-gray-700">Holding ID</h3>
                      <span class="text-xs bg-green-100 text-green-800 px-2 py-0.5 rounded">Verified</span>
                    </div>
                    <div class="relative aspect-[4/3] bg-gray-100 rounded-md overflow-hidden mb-3">
                      <img src="/images/${user.imageHoldingCitizenship}" alt="Holding ID" class="w-full h-full object-cover" />
                      <div class="absolute inset-0 flex items-center justify-center bg-black bg-opacity-40 opacity-0 hover:opacity-100 transition-opacity">
                        <button class="bg-white text-gray-800 px-3 py-1.5 rounded-lg text-sm font-medium hover:bg-gray-100" onclick="openDocumentPreview('/placeholder.svg?height=800&width=1200', 'Holding ID')">
                          <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 inline-block mr-1" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
                          </svg>
                          View Full Size
                        </button>
                      </div>
                    </div>
                    <div class="flex items-center justify-between text-sm">
                      <span class="text-gray-500">Uploaded: 2024-06-01</span>
                      <div class="flex items-center">
                        <button class="text-primary-600 hover:text-primary-800 mr-3" title="Download">
                          <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16v1a3 3 0 003 3h10a3 3 0 003-3v-1m-4-4l-4 4m0 0l-4-4m4 4V4" />
                          </svg>
                        </button>
                        <div class="tooltip">
                          <button class="text-gray-500 hover:text-gray-700" title="More Info">
                            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                            </svg>
                          </button>
                          <span class="tooltip-text">Document verified by admin on 2024-06-02</span>
                        </div>
                      </div>
                    </div>
                  </div>

                  <div class="document-card p-4">
                    <div class="flex items-center justify-between mb-3">
                      <h3 class="font-medium text-gray-700">Thumbprint</h3>
                      <span class="text-xs bg-green-100 text-green-800 px-2 py-0.5 rounded">Verified</span>
                    </div>
                    <div class="relative aspect-[4/3] bg-gray-100 rounded-md overflow-hidden mb-3">
                      <img src="/images/${user.thumbPrint}" alt="Thumbprint" class="w-full h-full object-cover" />
                      <div class="absolute inset-0 flex items-center justify-center bg-black bg-opacity-40 opacity-0 hover:opacity-100 transition-opacity">
                        <button class="bg-white text-gray-800 px-3 py-1.5 rounded-lg text-sm font-medium hover:bg-gray-100" onclick="openDocumentPreview('/placeholder.svg?height=800&width=1200', 'Thumbprint')">
                          <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4 inline-block mr-1" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z" />
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z" />
                          </svg>
                          View Full Size
                        </button>
                      </div>
                    </div>
                    <div class="flex items-center justify-between text-sm">
                      <span class="text-gray-500">Uploaded: 2024-06-01</span>
                      <div class="flex items-center">
                        <button class="text-primary-600 hover:text-primary-800 mr-3" title="Download">
                          <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16v1a3 3 0 003 3h10a3 3 0 003-3v-1m-4-4l-4 4m0 0l-4-4m4 4V4" />
                          </svg>
                        </button>
                        <div class="tooltip">
                          <button class="text-gray-500 hover:text-gray-700" title="More Info">
                            <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
                            </svg>
                          </button>
                          <span class="tooltip-text">Document verified by admin on 2024-06-02</span>
                        </div>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>




                </div>
</div>
                </div>
              </div>


            </div>
          </div>

        </div>
      </div>
    </div>
  </div>

  <!-- Reject with Reason Modal -->
  <div id="rejectReasonModal" class="modal fixed inset-0 bg-black bg-opacity-50 z-50 flex items-center justify-center p-4 opacity-0 invisible">
    <div class="modal-content bg-white rounded-xl shadow-xl max-w-lg w-full transform translate-y-8">
      <div class="p-6">
        <div class="flex justify-between items-center mb-4">
          <h3 class="text-xl font-bold text-gray-800">Rejection Reason</h3>
          <button id="closeModal" class="text-gray-400 hover:text-gray-600">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
            </svg>
          </button>
        </div>
        <p class="text-gray-600 mb-4">Please provide a reason for rejecting this account request. This will be sent to the applicant.</p>
        <div class="mb-4">
          <textarea id="rejectionReason" rows="5" class="w-full px-3 py-2 text-gray-700 border rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500 focus:border-transparent resize-none" placeholder="Enter rejection reason..."></textarea>
        </div>
        <div class="flex justify-end space-x-3">
          <button id="cancelReject" class="bg-gray-200 text-gray-800 px-5 py-2 rounded-lg hover:bg-gray-300 transition-colors duration-200">
            Cancel
          </button>
          <button id="submitReject" class="bg-red-600 text-white px-5 py-2 rounded-lg hover:bg-red-700 transition-colors duration-200">
            Send Rejection
          </button>
        </div>
      </div>
    </div>
  </div>


  <!-- Document Preview Modal -->
  <div id="documentPreviewModal" class="modal fixed inset-0 bg-black bg-opacity-75 z-50 flex items-center justify-center p-4 opacity-0 invisible">
    <div class="modal-content bg-white rounded-xl shadow-xl max-w-4xl w-full transform translate-y-8">
      <div class="p-6">
        <div class="flex justify-between items-center mb-4">
          <h3 id="previewTitle" class="text-xl font-bold text-gray-800">Document Preview</h3>
          <button id="closeDocumentModal" class="text-gray-400 hover:text-gray-600">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
              <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
            </svg>
          </button>
        </div>

          <!-- Zoom Controls -->
          <div class="zoom-controls">
            <button class="zoom-btn" id="zoomIn">
              <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0zM10 7v3m0 0v3m0-3h3m-3 0H7" />
              </svg>
            </button>
            <button class="zoom-btn" id="zoomOut">
              <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0zM13 10H7" />
              </svg>
            </button>
            <button class="zoom-btn" id="resetZoom">
              <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15" />
              </svg>
            </button>
          </div>
        </div>
        <div class="flex justify-end mt-4">
          <button id="closePreviewBtn" class="bg-gray-200 text-gray-800 px-5 py-2 rounded-lg hover:bg-gray-300 transition-colors duration-200">
            Close
          </button>
        </div>



                    <!-- Action Buttons -->
                    <div class="flex flex-col sm:flex-row justify-end gap-3 pt-4 border-t border-gray-200">
                        <form action="${pageContext.request.contextPath}/admin/user-approval/reject/${user.unverifiedUserId}" method="post" class="w-full sm:w-auto">
                            <button type="button" onclick="showRejectReasonModal()"
                                    class="w-full bg-red-600 hover:bg-red-700 text-white px-6 py-2 rounded-lg transition-colors duration-200 flex items-center justify-center gap-2">
                                <i class="fas fa-times"></i> Reject
                            </button>
                        </form>
                        <form action="${pageContext.request.contextPath}/admin/user-approval/approve/${user.unverifiedUserId}" method="post" class="w-full sm:w-auto">
                            <button type="submit"
                                    class="w-full bg-green-600 hover:bg-green-700 text-white px-6 py-2 rounded-lg transition-colors duration-200 flex items-center justify-center gap-2">
                                <i class="fas fa-check"></i> Approve
                            </button>
                        </form>
                    </div>
      </div>
    </div>
  </div>

  <script>
    // Modal functionality
    const rejectReasonModal = document.getElementById('rejectReasonModal');
    const documentPreviewModal = document.getElementById('documentPreviewModal');
    const rejectWithReasonBtn = document.getElementById('rejectWithReasonBtn');
    const closeModal = document.getElementById('closeModal');
    const cancelReject = document.getElementById('cancelReject');
    const submitReject = document.getElementById('submitReject');
    const closeDocumentModal = document.getElementById('closeDocumentModal');
    const closePreviewBtn = document.getElementById('closePreviewBtn');
    const previewImage = document.getElementById('previewImage');
    const previewTitle = document.getElementById('previewTitle');

    // Tab switching functionality
    function switchTab(tabName) {
      // Hide all tab contents
      const tabContents = document.querySelectorAll('.tab-content');
      tabContents.forEach(content => {
        content.classList.add('hidden');
      });

      // Show the selected tab content
      document.getElementById(`${tabName}-tab`).classList.remove('hidden');

      // Update active tab styling
      const tabLinks = document.querySelectorAll('.border-b-2');
      tabLinks.forEach(link => {
        link.classList.remove('border-primary-500', 'text-primary-600');
        link.classList.add('border-transparent', 'hover:text-gray-600', 'hover:border-gray-300');
      });

      // Set active tab
      event.currentTarget.classList.remove('border-transparent', 'hover:text-gray-600', 'hover:border-gray-300');
      event.currentTarget.classList.add('border-primary-500', 'text-primary-600');
    }

    // Open reject reason modal
    rejectWithReasonBtn.addEventListener('click', () => {
      rejectReasonModal.classList.add('active');
      document.body.style.overflow = 'hidden';
    });

    // Close reject reason modal
    const closeRejectModal = () => {
      rejectReasonModal.classList.remove('active');
      document.body.style.overflow = '';
    };

    closeModal.addEventListener('click', closeRejectModal);
    cancelReject.addEventListener('click', closeRejectModal);

    // Submit rejection with reason
    submitReject.addEventListener('click', () => {
      const reason = document.getElementById('rejectionReason').value;
      if (reason.trim() === '') {
        alert('Please provide a rejection reason');
        return;
      }

      // Here you would typically send the rejection reason to the server
      alert('Rejection submitted with reason: ' + reason);
      closeRejectModal();
    });

    // Document preview functionality
    function openDocumentPreview(imageUrl, title) {
      previewImage.src = imageUrl;
      previewTitle.textContent = title || 'Document Preview';

      documentPreviewModal.classList.add('active');
      document.body.style.overflow = 'hidden';

      // Reset zoom
      currentZoom = 1;
      previewImage.style.transform = `scale(${currentZoom})`;
    }

    // Close document preview modal
    const closeDocumentPreviewModal = () => {
      documentPreviewModal.classList.remove('active');
      document.body.style.overflow = '';
    };

    closeDocumentModal.addEventListener('click', closeDocumentPreviewModal);
    closePreviewBtn.addEventListener('click', closeDocumentPreviewModal);

    // Zoom functionality
    let currentZoom = 1;
    const zoomIn = document.getElementById('zoomIn');
    const zoomOut = document.getElementById('zoomOut');
    const resetZoom = document.getElementById('resetZoom');

    zoomIn.addEventListener('click', () => {
      currentZoom += 0.1;
      if (currentZoom > 3) currentZoom = 3;
      previewImage.style.transform = `scale(${currentZoom})`;
    });

    zoomOut.addEventListener('click', () => {
      currentZoom -= 0.1;
      if (currentZoom < 0.5) currentZoom = 0.5;
      previewImage.style.transform = `scale(${currentZoom})`;
    });

    resetZoom.addEventListener('click', () => {
      currentZoom = 1;
      previewImage.style.transform = `scale(${currentZoom})`;
    });

    // Approve and reject functionality
    const approveBtn = document.getElementById('approveBtn');
    const rejectBtn = document.getElementById('rejectBtn');

    approveBtn.addEventListener('click', () => {
      if (confirm('Are you sure you want to approve this account request?')) {
        // Here you would typically send the approval to the server
        alert('Account request approved successfully!');
      }
    });

    rejectBtn.addEventListener('click', () => {
      if (confirm('Are you sure you want to reject this account request without providing a reason?')) {
        // Here you would typically send the rejection to the server
        alert('Account request rejected!');
      }
    });
  </script>
</body>
</html>