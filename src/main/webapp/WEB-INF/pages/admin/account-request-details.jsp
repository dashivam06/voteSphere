<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>VoteSphere | User Verification</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css" />
    <script src="https://cdn.tailwindcss.com"></script>
    <script src="https://unpkg.com/flowbite@1.5.2/dist/flowbite.js"></script>
    <style>
        :root {
            --primary-50: 240 249 255;
            --primary-600: 2 132 199;
            --primary-700: 3 105 161;
        }

        body {
            font-family: 'Inter', sans-serif;
            background-color: #f8fafc;
        }

        .document-card {
            transition: all 0.2s ease;
            box-shadow: 0 1px 2px 0 rgba(0,0,0,0.05);
        }

        .document-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 10px 15px -3px rgba(0,0,0,0.1), 0 4px 6px -2px rgba(0,0,0,0.05);
        }

        .status-badge {
            font-size: 0.75rem;
            padding: 0.25rem 0.5rem;
            border-radius: 9999px;
        }

        .image-preview {
            max-height: 70vh;
            max-width: 90vw;
            object-fit: contain;
        }

        .zoom-controls {
            position: absolute;
            bottom: 1rem;
            right: 1rem;
            z-index: 50;
        }
    </style>
</head>
<body class="antialiased">
    <!-- Main Container -->
    <div class="min-h-screen flex flex-col">
        <!-- Header -->
        <jsp:include page="navbar.jsp" />

        <!-- Main Content -->
        <div class="flex flex-1">
            <!-- Sidebar -->
            <jsp:include page="sidebar.jsp" />

            <!-- Content Area -->
            <main class="flex-1 p-6 lg:p-8 bg-gray-50">
                <div class="max-w-7xl mx-auto">
                    <!-- Page Header -->
                    <div class="flex flex-col md:flex-row justify-between items-start md:items-center mb-8">
                        <div>
                            <h1 class="text-2xl font-bold text-gray-900">User Verification</h1>
                            <p class="text-gray-600 mt-1">Review and verify account registration</p>
                        </div>
                        <a href="${pageContext.request.contextPath}/admin/user-approval/list"
                           class="inline-flex items-center px-4 py-2 border border-gray-300 rounded-md shadow-sm text-sm font-medium text-gray-700 bg-white hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary-500 mt-4 md:mt-0">
                            <i class="fas fa-arrow-left mr-2"></i> Back to list
                        </a>
                    </div>

                    <!-- User Profile Card -->
                    <div class="bg-white shadow rounded-lg overflow-hidden mb-8">
                        <div class="px-6 py-5 border-b border-gray-200 bg-gradient-to-r from-primary-600 to-primary-700">
                            <div class="flex flex-col md:flex-row items-start md:items-center">
                                <div class="flex items-center">
                                    <div class="relative group mr-4">
                                        <img class="h-16 w-16 rounded-full object-cover border-4 border-white"
                                             src="${pageContext.request.contextPath}/uploads/${user.profileImage}"
                                             alt="Profile image"
                                             onclick="openImageModal('${pageContext.request.contextPath}/uploads/${user.profileImage}')">
                                        <div class="absolute inset-0 bg-black bg-opacity-30 rounded-full flex items-center justify-center opacity-0 group-hover:opacity-100 transition-opacity">
                                            <i class="fas fa-search text-white"></i>
                                        </div>
                                    </div>
                                    <div>
                                        <h2 class="text-xl font-semibold text-white">${user.firstName} ${user.lastName}</h2>
                                        <p class="text-primary-100">${user.email}</p>
                                    </div>
                                </div>
                                <div class="mt-4 md:mt-0 md:ml-auto">
                                    <span class="status-badge ${user.verified ? 'bg-green-100 text-green-800' : 'bg-yellow-100 text-yellow-800'}">
                                        ${user.verified ? 'Verified' : 'Pending Verification'}
                                    </span>
                                </div>
                            </div>
                        </div>

                        <!-- User Details -->
                        <div class="px-6 py-5">
                            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                                <!-- Personal Info -->
                                <div>
                                    <h3 class="text-sm font-medium text-gray-500 uppercase tracking-wider mb-3">Personal Information</h3>
                                    <dl class="space-y-3">
                                        <div class="flex items-start">
                                            <dt class="text-sm font-medium text-gray-500 w-32 flex-shrink-0">Voter ID</dt>
                                            <dd class="text-sm text-gray-900">${user.voterId}</dd>
                                        </div>
                                        <div class="flex items-start">
                                            <dt class="text-sm font-medium text-gray-500 w-32 flex-shrink-0">Date of Birth</dt>
                                            <dd class="text-sm text-gray-900"><fmt:formatDate value="${user.dateOfBirth}" pattern="MMMM d, yyyy" /></dd>
                                        </div>
                                        <div class="flex items-start">
                                            <dt class="text-sm font-medium text-gray-500 w-32 flex-shrink-0">Gender</dt>
                                            <dd class="text-sm text-gray-900">${user.gender}</dd>
                                        </div>
                                    </dl>
                                </div>

                                <!-- Contact Info -->
                                <div>
                                    <h3 class="text-sm font-medium text-gray-500 uppercase tracking-wider mb-3">Contact Information</h3>
                                    <dl class="space-y-3">
                                        <div class="flex items-start">
                                            <dt class="text-sm font-medium text-gray-500 w-32 flex-shrink-0">Email</dt>
                                            <dd class="text-sm text-gray-900">${user.email}</dd>
                                        </div>
                                        <div class="flex items-start">
                                            <dt class="text-sm font-medium text-gray-500 w-32 flex-shrink-0">Phone</dt>
                                            <dd class="text-sm text-gray-900">${not empty user.phone ? user.phone : 'N/A'}</dd>
                                        </div>
                                    </dl>
                                </div>

                                <!-- Address Info -->
                                <h1> Address Information</h1>
                                <div>

                                    <h3 class="text-sm font-medium text-gray-500 uppercase tracking-wider mb-3">Address Information</h3>
                                    <dl class="space-y-3">
                                        <div class="flex items-start">
                                            <dt class="text-sm font-medium text-gray-500 w-32 flex-shrink-0">Temporary</dt>
                                            <dd class="text-sm text-gray-900">${user.temporaryAddress}</dd>
                                        </div>
                                        <div class="flex items-start">
                                            <dt class="text-sm font-medium text-gray-500 w-32 flex-shrink-0">Permanent</dt>
                                            <dd class="text-sm text-gray-900">${user.permanentAddress}</dd>
                                        </div>
                                    </dl>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Verification Documents -->
                    <div class="bg-white shadow rounded-lg overflow-hidden mb-8">
                        <div class="px-6 py-5 border-b border-gray-200">
                            <h3 class="text-lg font-medium text-gray-900">Verification Documents</h3>
                            <p class="mt-1 text-sm text-gray-500">Review all submitted documents for verification</p>
                        </div>
                        <div class="px-6 py-5">
                            <div class="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-4">
                                <!-- Citizenship Card -->
                                <div class="document-card bg-white rounded-lg border border-gray-200 overflow-hidden">
                                    <div class="p-4">
                                        <div class="flex items-center mb-3">
                                            <div class="p-2 rounded-full bg-primary-50 text-primary-600 mr-3">
                                                <i class="fas fa-id-card"></i>
                                            </div>
                                            <h4 class="font-medium text-gray-900">Citizenship Document</h4>
                                        </div>
                                        <div class="flex flex-wrap gap-2">
                                            <button onclick="openImageModal('${pageContext.request.contextPath}/uploads/${user.citizenshipFront}')"
                                                    class="inline-flex items-center px-3 py-1 border border-gray-300 rounded-md text-xs font-medium text-gray-700 bg-white hover:bg-gray-50">
                                                <i class="fas fa-image mr-1"></i> Front
                                            </button>
                                            <button onclick="openImageModal('${pageContext.request.contextPath}/uploads/${user.citizenshipBack}')"
                                                    class="inline-flex items-center px-3 py-1 border border-gray-300 rounded-md text-xs font-medium text-gray-700 bg-white hover:bg-gray-50">
                                                <i class="fas fa-image mr-1"></i> Back
                                            </button>
                                        </div>
                                    </div>
                                </div>

                                <!-- Voter ID Card -->
                                <div class="document-card bg-white rounded-lg border border-gray-200 overflow-hidden">
                                    <div class="p-4">
                                        <div class="flex items-center mb-3">
                                            <div class="p-2 rounded-full bg-primary-50 text-primary-600 mr-3">
                                                <i class="fas fa-vote-yea"></i>
                                            </div>
                                            <h4 class="font-medium text-gray-900">Voter ID Card</h4>
                                        </div>
                                        <div class="flex flex-wrap gap-2">
                                            <button onclick="openImageModal('${pageContext.request.contextPath}/uploads/${user.voterCardFront}')"
                                                    class="inline-flex items-center px-3 py-1 border border-gray-300 rounded-md text-xs font-medium text-gray-700 bg-white hover:bg-gray-50">
                                                <i class="fas fa-image mr-1"></i> Front
                                            </button>
                                            <button onclick="openImageModal('${pageContext.request.contextPath}/uploads/${user.voterCardBack}')"
                                                    class="inline-flex items-center px-3 py-1 border border-gray-300 rounded-md text-xs font-medium text-gray-700 bg-white hover:bg-gray-50">
                                                <i class="fas fa-image mr-1"></i> Back
                                            </button>
                                        </div>
                                    </div>
                                </div>

                                <!-- Additional Documents -->
                                <div class="document-card bg-white rounded-lg border border-gray-200 overflow-hidden">
                                    <div class="p-4">
                                        <div class="flex items-center mb-3">
                                            <div class="p-2 rounded-full bg-primary-50 text-primary-600 mr-3">
                                                <i class="fas fa-file-alt"></i>
                                            </div>
                                            <h4 class="font-medium text-gray-900">Additional Documents</h4>
                                        </div>
                                        <div class="flex flex-wrap gap-2">
                                            <button onclick="openImageModal('${pageContext.request.contextPath}/uploads/${user.imageHoldingCitizenship}')"
                                                    class="inline-flex items-center px-3 py-1 border border-gray-300 rounded-md text-xs font-medium text-gray-700 bg-white hover:bg-gray-50">
                                                <i class="fas fa-portrait mr-1"></i> Holding ID
                                            </button>
                                            <button onclick="openImageModal('${pageContext.request.contextPath}/uploads/${user.thumbPrint}')"
                                                    class="inline-flex items-center px-3 py-1 border border-gray-300 rounded-md text-xs font-medium text-gray-700 bg-white hover:bg-gray-50">
                                                <i class="fas fa-fingerprint mr-1"></i> Thumbprint
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <!-- Verification Actions -->
                    <div class="bg-white shadow rounded-lg overflow-hidden">
                        <div class="px-6 py-5 border-b border-gray-200">
                            <h3 class="text-lg font-medium text-gray-900">Verification Decision</h3>
                            <p class="mt-1 text-sm text-gray-500">Approve or reject this user's verification request</p>
                        </div>
                        <div class="px-6 py-5">
                            <div class="flex flex-col sm:flex-row justify-end gap-3">
                                <button type="button" onclick="openRejectModal()"
                                        class="inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md shadow-sm text-white bg-red-600 hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500">
                                    <i class="fas fa-times mr-2"></i> Reject Application
                                </button>
                                <form action="${pageContext.request.contextPath}/admin/user-approval/approve/${user.id}" method="post">
                                    <button type="submit"
                                            class="inline-flex items-center px-4 py-2 border border-transparent text-sm font-medium rounded-md shadow-sm text-white bg-green-600 hover:bg-green-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-green-500">
                                        <i class="fas fa-check mr-2"></i> Approve Application
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
        </div>
    </div>

    <!-- Image Preview Modal -->
    <div id="imageModal" class="hidden fixed inset-0 z-50 overflow-y-auto" aria-labelledby="modal-title" role="dialog" aria-modal="true">
        <div class="flex items-end justify-center min-h-screen pt-4 px-4 pb-20 text-center sm:block sm:p-0">
            <div class="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity" aria-hidden="true"></div>
            <span class="hidden sm:inline-block sm:align-middle sm:h-screen" aria-hidden="true">&#8203;</span>
            <div class="inline-block align-bottom bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-4xl sm:w-full">
                <div class="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                    <div class="sm:flex sm:items-start">
                        <div class="mt-3 text-center sm:mt-0 sm:ml-4 sm:text-left w-full">
                            <div class="flex justify-between items-center mb-4">
                                <h3 class="text-lg leading-6 font-medium text-gray-900" id="modal-title">Document Preview</h3>
                                <button type="button" onclick="closeImageModal()" class="text-gray-400 hover:text-gray-500">
                                    <i class="fas fa-times"></i>
                                </button>
                            </div>
                            <div class="mt-2 flex justify-center">
                                <img id="modalImageContent" src="" alt="Document preview" class="image-preview rounded-md">
                            </div>
                            <div class="zoom-controls hidden">
                                <button onclick="zoomIn()" class="p-2 bg-white rounded-full shadow-md mr-2">
                                    <i class="fas fa-search-plus"></i>
                                </button>
                                <button onclick="zoomOut()" class="p-2 bg-white rounded-full shadow-md">
                                    <i class="fas fa-search-minus"></i>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="bg-gray-50 px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse">
                    <button type="button" onclick="closeImageModal()" class="mt-3 w-full inline-flex justify-center rounded-md border border-gray-300 shadow-sm px-4 py-2 bg-white text-base font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary-500 sm:mt-0 sm:ml-3 sm:w-auto sm:text-sm">
                        Close
                    </button>
                </div>
            </div>
        </div>
    </div>

    <!-- Reject Modal -->
    <div id="rejectModal" class="hidden fixed inset-0 z-50 overflow-y-auto" aria-labelledby="reject-modal-title" role="dialog" aria-modal="true">
        <div class="flex items-end justify-center min-h-screen pt-4 px-4 pb-20 text-center sm:block sm:p-0">
            <div class="fixed inset-0 bg-gray-500 bg-opacity-75 transition-opacity" aria-hidden="true"></div>
            <span class="hidden sm:inline-block sm:align-middle sm:h-screen" aria-hidden="true">&#8203;</span>
            <div class="inline-block align-bottom bg-white rounded-lg text-left overflow-hidden shadow-xl transform transition-all sm:my-8 sm:align-middle sm:max-w-lg sm:w-full">
                <form action="${pageContext.request.contextPath}/admin/user-approval/reject/${user.id}" method="post">
                    <div class="bg-white px-4 pt-5 pb-4 sm:p-6 sm:pb-4">
                        <div class="sm:flex sm:items-start">
                            <div class="mx-auto flex-shrink-0 flex items-center justify-center h-12 w-12 rounded-full bg-red-100 sm:mx-0 sm:h-10 sm:w-10">
                                <i class="fas fa-exclamation text-red-600"></i>
                            </div>
                            <div class="mt-3 text-center sm:mt-0 sm:ml-4 sm:text-left">
                                <h3 class="text-lg leading-6 font-medium text-gray-900" id="reject-modal-title">Reject Application</h3>
                                <div class="mt-2">
                                    <p class="text-sm text-gray-500">Please provide a reason for rejecting this application. This will be sent to the user.</p>
                                    <textarea id="rejectReason" name="reason" rows="4" class="mt-3 shadow-sm focus:ring-primary-500 focus:border-primary-500 block w-full sm:text-sm border border-gray-300 rounded-md p-2" placeholder="Enter rejection reason..."></textarea>
                                </div>
                            </div>
                        </div>
                    </div>
                    <div class="bg-gray-50 px-4 py-3 sm:px-6 sm:flex sm:flex-row-reverse">
                        <button type="submit" class="w-full inline-flex justify-center rounded-md border border-transparent shadow-sm px-4 py-2 bg-red-600 text-base font-medium text-white hover:bg-red-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-red-500 sm:ml-3 sm:w-auto sm:text-sm">
                            Confirm Rejection
                        </button>
                        <button type="button" onclick="closeRejectModal()" class="mt-3 w-full inline-flex justify-center rounded-md border border-gray-300 shadow-sm px-4 py-2 bg-white text-base font-medium text-gray-700 hover:bg-gray-50 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary-500 sm:mt-0 sm:ml-3 sm:w-auto sm:text-sm">
                            Cancel
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <script>
        // Image Modal Functions
        function openImageModal(imageSrc) {
            document.getElementById('modalImageContent').src = imageSrc;
            document.getElementById('imageModal').classList.remove('hidden');
            document.body.style.overflow = 'hidden';
        }

        function closeImageModal() {
            document.getElementById('imageModal').classList.add('hidden');
            document.body.style.overflow = '';
        }

        // Reject Modal Functions
        function openRejectModal() {
            document.getElementById('rejectModal').classList.remove('hidden');
            document.body.style.overflow = 'hidden';
        }

        function closeRejectModal() {
            document.getElementById('rejectModal').classList.add('hidden');
            document.body.style.overflow = '';
        }

        // Zoom functionality for images
        let currentScale = 1;
        const imageElement = document.getElementById('modalImageContent');
        const zoomControls = document.querySelector('.zoom-controls');

        function zoomIn() {
            currentScale += 0.1;
            imageElement.style.transform = `scale(${currentScale})`;
        }

        function zoomOut() {
            if (currentScale > 0.5) {
                currentScale -= 0.1;
                imageElement.style.transform = `scale(${currentScale})`;
            }
        }

        // Show zoom controls when image is hovered
        if (imageElement) {
            imageElement.addEventListener('mouseenter', () => {
                zoomControls.classList.remove('hidden');
            });

            imageElement.addEventListener('mouseleave', () => {
                zoomControls.classList.add('hidden');
            });
        }

        // Close modals when clicking outside
        window.onclick = function(event) {
            if (event.target === document.getElementById('imageModal')) {
                closeImageModal();
            }
            if (event.target === document.getElementById('rejectModal')) {
                closeRejectModal();
            }
        }
    </script>
</body>
</html>