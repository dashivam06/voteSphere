<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%@ include file="../loader-animation.jsp" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>VoteSphere - Election Details</title>
    <link rel="icon" src="/resources/favicon.ico" type="image/x-icon" />

    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/global.css" />
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
<jsp:include page="sidebar.jsp" />

<!-- Main Content -->
<div class="flex-grow flex flex-col ml-0 lg:ml-64 transition-all duration-300 ease-in-out">
    <!-- Navbar -->
    <jsp:include page="navbar.jsp" />

    <!-- Content Area -->
    <div class="flex-1 overflow-y-auto p-4 md:p-8 bg-gray-100">
        <div class="max-w-7xl mx-auto">
            <!-- Page Header with Back Button -->
            <div class="flex justify-between items-center mb-6">
                <div>
                    <h1 class="text-2xl font-bold text-gray-800">Election Details</h1>
                    <p class="text-gray-600">
                        View detailed information about this election
                    </p>
                </div>
                <a
                        href="${pageContext.request.contextPath}/election"
                        class="flex items-center text-primary-600 hover:text-primary-700"
                >
                    <svg
                            xmlns="http://www.w3.org/2000/svg"
                            class="h-5 w-5 mr-1"
                            fill="none"
                            viewBox="0 0 24 24"
                            stroke="currentColor"
                    >
                        <path
                                stroke-linecap="round"
                                stroke-linejoin="round"
                                stroke-width="2"
                                d="M10 19l-7-7m0 0l7-7m-7 7h18"
                        />
                    </svg>
                    Back to Elections
                </a>
            </div>

            <!-- Election Banner -->
            <div class="bg-white rounded-lg shadow-sm overflow-hidden mb-6">
                <div class="h-64 bg-gray-200 relative">
                    <img
                            id="election-cover"
                            src="${election.coverImage}"
                            alt="Election Cover"
                            class="w-full h-full object-cover"
                    />
                    <div class="absolute top-4 right-4">
                        <c:choose>
                            <c:when test="${fn:toUpperCase(election.status) == 'UPCOMING'}">
                                <span class="px-3 py-1 bg-yellow-500 text-white text-sm font-semibold rounded-full">Upcoming</span>
                            </c:when>
                            <c:when test="${fn:toUpperCase(election.status) == 'ONGOING'}">
                                <span class="px-3 py-1 bg-green-500 text-white text-sm font-semibold rounded-full">Active</span>
                            </c:when>
                            <c:otherwise>
                                <span class="px-3 py-1 bg-gray-500 text-white text-sm font-semibold rounded-full">Completed</span>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
                <div class="p-6">
                    <h2 class="text-2xl font-bold text-gray-800 mb-2">
                        ${election.name}
                    </h2>
                    <p class="text-gray-600 mb-6">
<%--                        ${election.description}--%>
                    </p>

                    <!-- Election Details -->
                    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-6">
                        <div class="bg-gray-50 p-4 rounded-lg">
                            <p class="text-sm text-gray-500 mb-1">Date</p>
                            <p class="font-medium">

                                <fmt:formatDate value="${election.date}" pattern="yyyy-MM-dd" />

                            </p>
                        </div>
                        <div class="bg-gray-50 p-4 rounded-lg">
                            <p class="text-sm text-gray-500 mb-1">Start Time</p>
                            <p class="font-medium">
                                <fmt:formatDate value="${election.startTime}" pattern="hh:mm a" />
                        </div>
                        <div class="bg-gray-50 p-4 rounded-lg">
                            <p class="text-sm text-gray-500 mb-1">End Time</p>
                            <p class="font-medium">
                                <fmt:formatDate value="${election.endTime}" pattern="hh:mm a" />
                        </div>
                        <div class="bg-gray-50 p-4 rounded-lg">
                            <p class="text-sm text-gray-500 mb-1">Type</p>
                            <p class="font-medium">${election.type}</p>
                        </div>
                    </div>

                    <!-- Election Action Button -->
                    <div class="flex justify-center">
                        <c:choose>
                            <c:when test="${fn:toUpperCase(election.status) == 'UPCOMING'}">
                                <div class="text-center">
                                    <p class="text-gray-600 mb-3">
                                        This election is not yet active. You can vote when it starts.
                                    </p>
                                    <button
                                            disabled
                                            class="bg-gray-300 text-gray-600 px-6 py-3 rounded-lg cursor-not-allowed"
                                    >
                                        Election Not Started
                                    </button>
                                </div>
                            </c:when>
                            <c:when test="${fn:toUpperCase(election.status) == 'ONGOING'}">
                                <div class="text-center">
                                    <p class="text-green-600 mb-3">
                                        This election is currently active. Cast your vote now!
                                    </p>
                                    <a
                                            href="${pageContext.request.contextPath}/user/cast-vote?election_id=${election.electionId}"
                                            class="bg-green-600 text-white px-6 py-3 rounded-lg hover:bg-green-700 transition-colors inline-block"
                                    >
                                        Cast Your Vote
                                    </a>
                                </div>
                            </c:when>
                            <c:otherwise>
                                <div class="text-center">
                                    <p class="text-gray-600 mb-3">
                                        This election has ended. View the results below.
                                    </p>
                                    <a
                                            href="#results"
                                            class="bg-primary-600 text-white px-6 py-3 rounded-lg hover:bg-primary-700 transition-colors inline-block"
                                    >
                                        View Results
                                    </a>
                                </div>
                            </c:otherwise>
                        </c:choose>
                    </div>
                </div>
            </div>

            <!-- Candidates Section -->
            <div class="mb-8">
                <h3 class="text-xl font-bold text-gray-800 mb-4">Candidates</h3>
                <c:choose>
                    <c:when test="${not empty candidates}">
                        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
                            <c:forEach var="candidate" items="${candidates}">
                                <div class="bg-white rounded-lg shadow-sm overflow-hidden">
                                    <div class="h-48 bg-gray-100">
                                        <img
                                                src="${candidate.profileImage}"
                                                alt="${candidate.fname} ${candidate.lname}"
                                                class="w-full h-full object-cover object-center"
                                                onerror="this.onerror=null;this.src='https://placehold.co/100x100'"
                                        />
                                    </div>
                                    <div class="p-4">
                                        <div class="flex items-center mb-2">
                                            <h4 class="font-semibold text-gray-800">${candidate.fname} ${candidate.lname}</h4>
                                            <c:if test="${not candidate.isIndependent}">
                                    <span class="ml-2 px-2 py-0.5 bg-blue-100 text-blue-800 text-xs rounded-full">
                                            ${candidate.partyName}
                                    </span>
                                            </c:if>
                                            <c:if test="${candidate.isIndependent}">
                                    <span class="ml-2 px-2 py-0.5 bg-gray-100 text-gray-800 text-xs rounded-full">
                                        Independent
                                    </span>
                                            </c:if>
                                        </div>
                                        <p class="text-sm text-gray-600 mb-4">
                                                ${fn:length(candidate.bio) > 100 ? fn:substring(candidate.bio, 0, 100) : candidate.bio}...
                                        </p>
                                        <div class="flex justify-between items-center">
                                            <span class="text-xs text-gray-500">Candidate #${candidate.candidateId}</span>
                                            <button
                                                    class="text-primary-600 hover:text-primary-800 text-sm font-medium"
                                                    onclick="showCandidateModal('${candidate.candidateId}')"
                                            >
                                                View Profile
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </c:forEach>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <div class="bg-white rounded-lg shadow-sm p-6 text-center">
                            <p class="text-gray-600">No candidates available at this time.</p>
                            <p class="text-sm text-gray-500 mt-2">Please check back later for updates.</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>

            <!-- Information Section -->
            <div class="bg-white rounded-lg shadow-sm p-6 mb-8">
                <h3 class="text-xl font-bold text-gray-800 mb-4">
                    Election Information
                </h3>

                <div class="mb-6">
                    <h4 class="font-semibold text-gray-700 mb-2">
                        Voting Requirements
                    </h4>
                    <ul class="list-disc pl-5 text-gray-600 space-y-1">
                        <li>Must be a registered voter</li>
                        <li>Must present valid voter ID</li>
                        <li>Must vote at your designated polling station</li>
                        <li>Must not have already voted in this election</li>
                    </ul>
                </div>

                <div class="mb-6">
                    <h4 class="font-semibold text-gray-700 mb-2">How to Vote</h4>
                    <ol class="list-decimal pl-5 text-gray-600 space-y-1">
                        <li>
                            Visit your designated polling station during voting hours
                        </li>
                        <li>Present your voter ID to the polling officials</li>
                        <li>Receive your ballot</li>
                        <li>Mark your ballot for your chosen candidate</li>
                        <li>Submit your completed ballot</li>
                    </ol>
                </div>

                <div>
                    <h4 class="font-semibold text-gray-700 mb-2">
                        Additional Information
                    </h4>
                    <p class="text-gray-600">
                        For more information about this election, please visit the
                        official election website or contact the election commission at
                        <a
                                href="mailto:elections@votesphere.com"
                                class="text-primary-600 hover:underline"
                        >elections@votesphere.com</a
                        >.
                    </p>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Candidate Modal (Hidden by default) -->
<div
        id="candidate-modal"
        class="fixed inset-0 bg-black bg-opacity-50 flex items-center justify-center z-50 hidden"
>
    <div class="bg-white rounded-lg max-w-2xl w-full max-h-[90vh] overflow-y-auto">
        <div class="p-6">
            <div class="flex justify-between items-center mb-4">
                <h3 id="modal-candidate-name" class="text-xl font-bold text-gray-800"></h3>
                <button
                        onclick="closeCandidateModal()"
                        class="text-gray-500 hover:text-gray-700"
                >
                    <svg
                            xmlns="http://www.w3.org/2000/svg"
                            class="h-6 w-6"
                            fill="none"
                            viewBox="0 0 24 24"
                            stroke="currentColor"
                    >
                        <path
                                stroke-linecap="round"
                                stroke-linejoin="round"
                                stroke-width="2"
                                d="M6 18L18 6M6 6l12 12"
                        />
                    </svg>
                </button>
            </div>

            <div class="flex flex-col md:flex-row gap-4 mb-4">
                <div class="w-full md:w-1/3">
                    <img
                            id="modal-candidate-image"
                            src=""
                            alt="Candidate"
                            class="w-full h-auto rounded-lg"
                            onerror="this.src='https://placehold.co/100x100'"
                    />
                </div>
                <div class="w-full md:w-2/3">
                    <div class="mb-3">
                        <span class="text-sm text-gray-500">Party</span>
                        <p id="modal-party" class="font-medium"></p>
                    </div>
                    <div class="mb-3">
                        <span class="text-sm text-gray-500">Address</span>
                        <p id="modal-address" class="font-medium"></p>
                    </div>
                    <div class="mb-3">
                        <span class="text-sm text-gray-500">Education</span>
                        <p id="modal-education" class="font-medium"></p>
                    </div>
                    <div class="mb-3">
                        <span class="text-sm text-gray-500">Birth Year</span>
                        <p id="modal-age" class="font-medium"> ${candidate.dob}</p>
                    </div>
                </div>
            </div>

            <div class="mb-4">
                <h4 class="font-semibold text-gray-700 mb-2">Biography</h4>
                <p id="modal-bio" class="text-gray-600"></p>
            </div>

            <div>
                <h4 class="font-semibold text-gray-700 mb-2">Manifesto</h4>
                <p id="modal-manifesto" class="text-gray-600"></p>
            </div>
        </div>
    </div>
</div>

<script>
    function showCandidateModal(candidateId) {
        // Reset modal content first
        document.getElementById("modal-candidate-name").textContent = "";
        document.getElementById("modal-party").textContent = "";
        document.getElementById("modal-education").textContent = "";
        document.getElementById("modal-age").textContent = "";
        document.getElementById("modal-bio").textContent = "";
        document.getElementById("modal-manifesto").textContent = "";
        document.getElementById("modal-candidate-image").src = "";

        // Find the candidate in the candidates array
        <c:forEach var="candidate" items="${candidates}">
        if (${candidate.candidateId} == candidateId) {
            document.getElementById("modal-candidate-name").textContent =
                "${candidate.fname} ${candidate.lname}";
            document.getElementById("modal-party").textContent =
                "${candidate.isIndependent ? 'Independent' : candidate.partyName}";
            document.getElementById("modal-education").textContent =
                "${candidate.highestEducation}";
            const dobString = "${candidate.dob}";
            const age = calculateAge(dobString);
            document.getElementById("modal-address").textContent =
                "${candidate.address}";


            // Calculate age from DOB - fixed calculation
            try {

                const dob = new Date("${candidate.dob}"); // Make sure this is a valid date string like "2000-05-20"
                const age = new Date().getFullYear() - dob.getFullYear();
                console.log(${candidate.dob})

                document.getElementById("modal-age").textContent = ${candidate.dob} + " AD";
            } catch (e) {
                console.error("Error calculating age:", e);
                document.getElementById("modal-age").textContent = "Unknown";
            }

            document.getElementById("modal-bio").textContent =
                "${candidate.bio}";
            document.getElementById("modal-manifesto").textContent =
                "${candidate.manifesto}";
            document.getElementById("modal-candidate-image").src =
                "${candidate.profileImage}";
        }
        </c:forEach>

        document.getElementById("candidate-modal").classList.remove("hidden");
    }

    function closeCandidateModal() {
        document.getElementById("candidate-modal").classList.add("hidden");
    }

    function calculateAge(dobString) {
        const dob = new Date(dobString); // parse "yyyy-MM-dd"
        const today = new Date();

        let age = today.getFullYear() - dob.getFullYear();
        const m = today.getMonth() - dob.getMonth();

        if (m < 0 || (m === 0 && today.getDate() < dob.getDate())) {
            age--;
        }

        return age;
    }
    // Update election status based on current time
    document.addEventListener("DOMContentLoaded", function() {
        const electionDate = new Date("${election.date}");
        const startTime = "${election.startTime}".split(':');
        const endTime = "${election.endTime}".split(':');

        electionDate.setHours(parseInt(startTime[0]), parseInt(startTime[1]), 0);
        const endDateTime = new Date("${election.date}");
        endDateTime.setHours(parseInt(endTime[0]), parseInt(endTime[1]), 0);

        const now = new Date();

        // This is just for UI display - the server should determine the actual status
        if (now < electionDate) {
            // Election is upcoming
        } else if (now >= electionDate && now <= endDateTime) {
            // Election is active
        } else {

        }
    });
</script>
</body>
</html>