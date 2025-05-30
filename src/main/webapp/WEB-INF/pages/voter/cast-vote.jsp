
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>VoteSphere - Cast Your Vote</title>
    <link rel="stylesheet" href="../styles/global.css" />
    <script src="https://cdn.tailwindcss.com"></script>
    <link
            href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap"
            rel="stylesheet"
    />
    <style>
        *{
            font-family: "Poppins";
        }
    </style>
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
        .party-card {
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }

        .party-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 10px 25px -5px rgba(0, 0, 0, 0.1),
            0 10px 10px -5px rgba(0, 0, 0, 0.04);
        }

        .party-card.selected {
            border-color: #0ea5e9;
            background-color: #f0f9ff;
        }
    </style>
</head>
<body class="font-sans bg-gray-100 flex h-screen overflow-hidden">
<!-- Include sidebar -->
<%@ include file="sidebar.jsp" %>

<!-- Main Content -->
<div class="flex-grow flex flex-col ml-0 lg:ml-64 transition-all duration-300 ease-in-out">
    <!-- Navbar -->
    <%@ include file="navbar.jsp" %>

    <!-- Content Area -->
    <div class="flex-1 overflow-y-auto bg-gray-100">
        <!-- Hero Section with Election Details -->
        <div class="bg-white shadow-sm">
            <div class="relative">
                <div class="h-64 w-full bg-gray-200">
                    <img
                            id="cover_image"
                            src="${election.coverImage}"
                            alt="Election Cover"
                            class="w-full h-full object-cover"
                    />
                    <div
                            class="absolute inset-0 bg-gradient-to-t from-black/60 to-transparent"
                    ></div>
                </div>

                <div
                        class="absolute bottom-4 left-4 md:bottom-8 md:left-8 text-white"
                >
              <span
                      id="election_type"
                      class="inline-block px-3 py-1 bg-primary-600 text-white text-xs font-semibold rounded-full mb-2"
              >
                  ${election.type}
              </span>
                    <h1
                            id="election_name"
                            class="text-2xl md:text-3xl font-bold mb-1"
                    >
                        ${election.name}
                    </h1>
                    <div class="flex flex-wrap items-center gap-x-6 gap-y-2 mt-2">
                        <div class="flex items-center">
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
                                        d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z"
                                />
                            </svg>
                            <span id="election_date" class="text-sm"
                            ><fmt:parseDate var="formattedDate" value="${election.date}" pattern="yyyy-MM-dd" />
                     <fmt:formatDate value="${formattedDate}" pattern="MMMM dd, yyyy" /></span
                            >
                        </div>
                        <div class="flex items-center">
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
                                        d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z"
                                />
                            </svg>
                            <span id="election_time" class="text-sm"
                            > <fmt:formatDate value="${election.startTime}" pattern="hh:mm a" /> -
                       <fmt:formatDate value="${election.endTime}" pattern="hh:mm a" /></span
                            >
                        </div>
                    </div>
                </div>
            </div>

            <!-- Election Information Bar -->
            <div
                    class="px-4 md:px-8 py-4 border-b border-gray-200 flex flex-wrap justify-between items-center"
            >
                <div class="flex items-center mb-2 md:mb-0">
                    <div class="h-2 bg-gray-200 rounded-full w-32 md:w-48 mr-3">
                        <div
                                id="time_progress"
                                class="h-2 bg-primary-500 rounded-full"
                                style="width: 60%"
                        ></div>
                    </div>
                    <c:set var="startMillis" value="${election.startTime.time}" />
                    <c:set var="endMillis" value="${election.endTime.time}" />

                    <c:set var="durationMillis" value="${endMillis - startMillis}" />
                    <c:set var="durationMinutes" value="${durationMillis / 60000}" />

                    <c:set var="hoursStr" value="${fn:substringBefore(hours, '.')}" />
                    <c:set var="minutesStr" value="${fn:substringBefore(minutes, '.')}" />


                    <!-- Floor hours by subtracting remainder -->
                    <c:set var="hours" value="${(durationMinutes - (durationMinutes % 60)) / 60}" />
                    <c:set var="minutes" value="${durationMinutes % 60}" />

                    <c:set var="hoursStr" value="${fn:substringBefore(hours, '.')}" />
                    <c:set var="minutesStr" value="${fn:substringBefore(minutes, '.')}" />



                    <!-- Assuming you have set start and end time -->
                    <span id="time_remaining" class="text-sm text-gray-600"
                          data-start-time="${election.startTime}"
                          data-end-time="${election.endTime}">
                  <p>
                    ${hoursStr} hour<c:if test="${hoursStr != '1'}">s</c:if>
                    ${minutesStr} minute<c:if test="${minutesStr != '1'}">s</c:if> remaining
                  </p>
            </span>



                </div>
                <div>
              <span class="text-sm text-gray-600 mr-2"
              >Your election token:</span
              >
                    <span class="bg-gray-100 px-3 py-1 rounded text-sm font-mono"
                    >${election.electionToken}</span
                    >
                </div>
            </div>
        </div>



        <!-- Voting Instructions -->
        <div class="max-w-7xl mx-auto px-4 md:px-8 py-6">
            <!-- Keep your existing instructions section -->
            <!-- ... -->

            <!-- Candidates/Parties List -->
            <h2 class="text-xl font-bold text-gray-800 mb-6">
                Select a Party to Vote
            </h2>

            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6 mb-8">
                <!-- Dynamic Party Cards using JSTL -->
                <c:forEach var="candidate" items="${candidateList}">
                    <div class="party-card bg-white rounded-lg shadow-sm border-2 border-transparent overflow-hidden"
                         data-party-id="${candidate.partyId}"
                         data-party-name="${candidate.partyName}">
                        <div class="p-6">
                            <div class="flex items-center mb-4">
                                <div class="h-16 w-16 bg-gray-100 rounded-full overflow-hidden flex-shrink-0">
                                    <c:choose>
                                        <c:when test="${not empty candidate.profileImage}">
                                            <img src="${candidate.profileImage}"
                                                 alt="${candidate.partyName}"
                                                 class="w-full h-full object-cover" />
                                        </c:when>
                                        <c:otherwise>
                                            <img src="https://placehold.co/100x100"
                                                 alt="${candidate.partyName}"
                                                 class="w-full h-full object-cover" />
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class="ml-4">
                                    <h3 class="font-bold text-lg party-name">${candidate.partyName}</h3>
                                    <p class="text-gray-600">Founder: ${candidate.foundingMember}</p>
                                    <p class="text-gray-600">Candidate: ${candidate.name}</p>
                                </div>
                            </div>

                            <div class="flex items-center justify-between">
                                <button type="button"
                                        class="vote-btn bg-transparent hover:bg-blue-600 hover:text-white hover:border-blue-600 text-primary-600 border border-primary-600 px-4 py-2 rounded-md text-sm transition-colors">
                                    Select
                                </button>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>

            <!-- Vote Confirmation Button (Initially Disabled) -->
            <div class="sticky bottom-0 bg-white p-4 shadow-lg rounded-t-lg border-t border-gray-200">
                <div class="max-w-7xl mx-auto flex flex-col md:flex-row items-center justify-between">
                    <div class="mb-4 md:mb-0 text-center md:text-left">
                        <p id="selected-party-text" class="text-gray-600">
                            No party selected
                        </p>
                        <p class="text-xs text-gray-500">
                            Your vote is anonymous and secure
                        </p>
                    </div>
                    <button id="confirm-vote-btn" disabled
                            class="w-full md:w-auto bg-gray-300 text-gray-500 cursor-not-allowed px-6 py-3 rounded-lg font-medium">
                        Confirm Vote
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Vote Confirmation Modal -->
<div id="vote-confirmation-modal" class="fixed inset-0 z-50 flex items-center justify-center hidden">
    <div class="absolute inset-0 bg-black bg-opacity-50"></div>
    <div class="relative bg-white rounded-lg max-w-md w-full p-6 m-4">
        <h3 class="text-xl font-semibold text-gray-900 mb-4">
            Confirm Your Vote
        </h3>
        <p class="text-gray-600 mb-6">
            You are about to cast your vote for
            <span id="confirm-party-name" class="font-semibold"></span>. This
            action cannot be undone.
        </p>
        <div class="flex flex-col gap-3 sm:flex-row sm:justify-end">
            <button id="cancel-vote-btn"
                    class="px-4 py-2 bg-white border border-gray-300 rounded-md text-gray-700 hover:bg-gray-50">
                Cancel
            </button>
            <button id="submit-vote-btn"
                    class="px-4 py-2 bg-primary-600 text-white rounded-md hover:bg-primary-700">
                Yes, Cast My Vote
            </button>
        </div>
    </div>
</div>

<!-- Vote Success Modal -->
<div id="vote-success-modal" class="fixed inset-0 z-50 flex items-center justify-center hidden">
    <div class="absolute inset-0 bg-black bg-opacity-50"></div>
    <div class="relative bg-white rounded-lg max-w-md w-full p-6 m-4 text-center">
        <div class="w-16 h-16 mx-auto mb-4 flex items-center justify-center rounded-full bg-green-100">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-10 w-10 text-green-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7" />
            </svg>
        </div>
        <h3 class="text-xl font-semibold text-gray-900 mb-2">
            Vote Cast Successfully!
        </h3>
        <p class="text-gray-600 mb-6">
            Thank you for participating in this election. Your vote has been
            recorded securely.
        </p>
        <a href="/election"
           class="inline-block px-4 py-2 bg-primary-600 text-white rounded-md hover:bg-primary-700">
            Return to Elections
        </a>
    </div>
</div>

<script>
    document.addEventListener('DOMContentLoaded', function() {
        // Get all party cards and vote buttons
        const partyCards = document.querySelectorAll('.party-card');
        const voteButtons = document.querySelectorAll('.vote-btn');
        const confirmVoteBtn = document.getElementById('confirm-vote-btn');
        const selectedPartyText = document.getElementById('selected-party-text');
        const voteConfirmationModal = document.getElementById('vote-confirmation-modal');
        const confirmPartyName = document.getElementById('confirm-party-name');
        const cancelVoteBtn = document.getElementById('cancel-vote-btn');
        const submitVoteBtn = document.getElementById('submit-vote-btn');
        const voteSuccessModal = document.getElementById('vote-success-modal');

        let selectedPartyId = null;
        let selectedPartyName = null;
        const electionId = '${election.electionId}';
        const userId = '${user_id}'; // Assuming user object is in session

        // Add event listeners to party cards and vote buttons
        partyCards.forEach((card, index) => {
            const voteBtn = voteButtons[index];

            card.addEventListener('click', function() {
                // Remove selection from all cards
                partyCards.forEach(c => {
                    c.classList.remove('selected');
                    c.querySelector('.vote-btn').textContent = 'Select';
                    c.querySelector('.vote-btn').classList.remove(
                        'bg-primary-600',
                        'text-white',
                        'border-primary-600'
                    );
                    c.querySelector('.vote-btn').classList.add(
                        'bg-transparent',
                        'text-primary-600',
                        'border-primary-600'
                    );
                });

                // Add selection to clicked card
                this.classList.add('selected');
                voteBtn.textContent = 'Selected';
                voteBtn.classList.remove(
                    'bg-transparent',
                    'text-primary-600',
                    'border-primary-600'
                );
                voteBtn.classList.add(
                    'bg-primary-600',
                    'text-white'
                );

                // Enable confirm button
                confirmVoteBtn.disabled = false;
                confirmVoteBtn.classList.remove(
                    'bg-gray-300',
                    'text-gray-500',
                    'cursor-not-allowed'
                );
                confirmVoteBtn.classList.add(
                    'bg-primary-600',
                    'text-white',
                    'hover:bg-primary-700'
                );

                // Update selected party info
                selectedPartyId = this.dataset.partyId;
                selectedPartyName = this.dataset.partyName; // Use dataset if stored as attribute
                selectedPartyText.textContent = `You selected: ${selectedPartyName}`;
            });
        });

        // Confirm vote button
        confirmVoteBtn.addEventListener('click', function() {
            if (selectedPartyId && selectedPartyName) {
                confirmPartyName.textContent = selectedPartyName;
                voteConfirmationModal.classList.remove('hidden');
            }
        });

        // Cancel vote button
        cancelVoteBtn.addEventListener('click', function() {
            voteConfirmationModal.classList.add('hidden');
        });

        // Submit vote button
        submitVoteBtn.addEventListener('click', function() {
            if (selectedPartyId && userId && electionId) {
                // Create a form dynamically
                const form = document.createElement('form');
                form.method = 'POST';
                form.action = '${pageContext.request.contextPath}/cast-vote';

                // Add hidden inputs
                const userIdInput = document.createElement('input');
                userIdInput.type = 'hidden';
                userIdInput.name = 'user_id';
                userIdInput.value = userId;
                form.appendChild(userIdInput);

                const partyIdInput = document.createElement('input');
                partyIdInput.type = 'hidden';
                partyIdInput.name = 'party_id';
                partyIdInput.value = selectedPartyId;
                form.appendChild(partyIdInput);

                const electionIdInput = document.createElement('input');
                electionIdInput.type = 'hidden';
                electionIdInput.name = 'election_id';
                electionIdInput.value = electionId;
                form.appendChild(electionIdInput);

                // Add CSRF token if needed (for Spring Security)
                <c:if test="${not empty _csrf}">
                const csrfToken = document.createElement('input');
                csrfToken.type = 'hidden';
                csrfToken.name = '${_csrf.parameterName}';
                csrfToken.value = '${_csrf.token}';
                form.appendChild(csrfToken);
                </c:if>

                // Submit the form
                document.body.appendChild(form);
                form.submit();

                // Show loading state
                submitVoteBtn.disabled = true;
                submitVoteBtn.innerHTML = 'Processing... <span class="ml-2 loading-spinner"></span>';

                // Hide confirmation modal
                voteConfirmationModal.classList.add('hidden');
            }
        });

        // Close modals when clicking outside
        window.addEventListener('click', function(e) {
            if (e.target === voteConfirmationModal.querySelector('.absolute')) {
                voteConfirmationModal.classList.add('hidden');
            }
            if (e.target === voteSuccessModal.querySelector('.absolute')) {
                voteSuccessModal.classList.add('hidden');
            }
        });

        // Check if there's a success message to show
        <c:if test="${not empty voteSuccess}">
        voteSuccessModal.classList.remove('hidden');
        </c:if>
    });


    document.addEventListener("DOMContentLoaded", () => {
        const partyCards = document.querySelectorAll('.party-card');
        const selectedPartyText = document.getElementById('selected-party-text');
        let selectedPartyId = null;
        let selectedPartyName = null;

        partyCards.forEach(card => {
            card.addEventListener('click', function () {
                // Remove previous selection
                partyCards.forEach(c => c.classList.remove('selected'));

                // Add current selection
                this.classList.add('selected');

                // Update selected party info
                selectedPartyId = this.dataset.partyId;
                selectedPartyName = this.dataset.partyName;
                selectedPartyText.textContent = `You selected: ${selectedPartyName}`;
            });
        });
    });

        document.addEventListener('DOMContentLoaded', function() {
        // ... (keep all your existing JavaScript code)

        // Check if there's a success message to show
        <c:if test="${not empty voteSuccess}">
        // Show success modal immediately
        voteSuccessModal.classList.remove('hidden');

        // Hide all other content
        document.querySelector('.flex-1.overflow-y-auto.bg-gray-100').style.display = 'none';
        </c:if>
    });
</script>
</body>
</html>