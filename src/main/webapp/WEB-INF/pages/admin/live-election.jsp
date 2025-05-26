<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:formatDate value="${election.startTime}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="startDateJs" />
<fmt:formatDate value="${election.endTime}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="endDateJs" />
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>VoteSphere - Live Election Results</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/global.css" />
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
</head>
<body class="font-sans bg-gray-100 flex h-screen overflow-hidden">
    <!-- Include sidebar -->
    <%@ include file="sidebar.jsp" %>

    <!-- Main Content -->
    <div class="flex-1 flex flex-col ml-0 lg:ml-64 transition-all duration-300 ease-in-out">
        <!-- Include navbar -->
        <%@ include file="navbar.jsp" %>

        <!-- Content Area -->
        <div class="p-6 overflow-y-auto">
            <div class="bg-white rounded-lg shadow-md p-6 mx-auto">
                <div class="flex justify-between items-center mb-6">
                    <h1 class="text-2xl font-bold text-gray-800">
                        Live Election Results
                    </h1>
                    <a href="${pageContext.request.contextPath}/elections"
                       class="bg-gray-100 text-gray-600 px-4 py-2 rounded-lg hover:bg-gray-200 transition-colors duration-200">
                        Back to Elections
                    </a>
                </div>

                <!-- Election Information -->
                <div class="mb-8">
                    <div class="bg-primary-50 border border-primary-100 rounded-lg p-4">
                        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4">
                            <div>
                                <span class="text-sm text-gray-500">Election</span>
                                <p id="election-name" class="font-semibold text-gray-800">
                                    ${election.name}
                                </p>
                            </div>
                            <div>
                                <span class="text-sm text-gray-500">Status</span>
                                <p id="election-status" class="font-semibold">
                                    <span class="inline-flex items-center">
                                        <span id="status-indicator" class="h-2 w-2 rounded-full mr-2"></span>
                                        <span id="status-text">Loading...</span>
                                    </span>
                                </p>
                            </div>
                            <div>
                                <span class="text-sm text-gray-500">Start Time</span>
                                <p id="start-time" class="font-semibold text-gray-800">
                                    <fmt:formatDate value="${election.startTime}" pattern="MMM d, yyyy hh:mm a" />
                                </p>
                            </div>
                            <div>
                                <span class="text-sm text-gray-500">End Time</span>
                                <p id="end-time" class="font-semibold text-gray-800">
                                    <fmt:formatDate value="${election.endTime}" pattern="MMM d, yyyy hh:mm a" />
                                </p>
                            </div>
                        </div>
                    </div>
                </div>

                <!-- Live Results Section -->
                <div class="mb-8">
                    <h3 class="text-lg font-semibold text-gray-700 mb-4 border-b pb-2 flex items-center">
                        <span>Live Vote Count</span>
                        <span class="ml-2 relative flex h-3 w-3">
                            <span class="animate-ping absolute inline-flex h-full w-full rounded-full bg-green-400 opacity-75"></span>
                            <span class="relative inline-flex rounded-full h-3 w-3 bg-green-500"></span>
                        </span>
                        <span class="ml-2 text-sm font-normal text-gray-500">Real-time updates</span>
                    </h3>

                    <!-- Total Votes -->
                    <div class="mb-6 bg-gray-50 p-4 rounded-lg">
                        <div class="flex justify-between items-center">
                            <span class="text-gray-600">Total Votes Cast</span>
                            <span id="total-votes" class="text-xl font-bold text-gray-800">0</span>
                        </div>
                    </div>

                    <!-- Candidates Vote Count Grid -->
                    <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6" id="candidates-container">
                        <!-- Will be populated by JavaScript -->
                        <div class="text-center py-10 text-gray-500">
                            Loading candidate data...
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            // Get election ID from the path
            const electionId = window.location.pathname.split('/').pop();

            // WebSocket connection
            const socket = new WebSocket(`ws://votesphere.com/election-results/${electionId}`);

            // Party color mapping
            const partyColors = {
                'Democratic': { bg: 'bg-blue-100', text: 'text-blue-800', progress: 'bg-blue-600' },
                'Republican': { bg: 'bg-red-100', text: 'text-red-800', progress: 'bg-red-600' },
                'Independent': { bg: 'bg-gray-100', text: 'text-gray-800', progress: 'bg-gray-600' },
                'default': { bg: 'bg-purple-100', text: 'text-purple-800', progress: 'bg-purple-600' }
            };

            // Current election data
            const electionData = {
                id: ${election.electionId},
                name: '${election.name}',
                startTime: new Date('${election.startTime}'),
                endTime: new Date('${election.endTime}'),
                status: '${election.status}'
            };

            // Update election status display
            function updateElectionStatus() {
                const now = new Date();
                const statusElement = document.getElementById('status-text');
                const indicator = document.getElementById('status-indicator');
                const statusContainer = document.getElementById('election-status');

                if (now < electionData.startTime) {
                    statusElement.textContent = 'Upcoming';
                    indicator.className = 'h-2 w-2 rounded-full bg-yellow-500 mr-2';
                    statusContainer.className = 'font-semibold text-yellow-600';
                } else if (now > electionData.endTime) {
                    statusElement.textContent = 'Completed';
                    indicator.className = 'h-2 w-2 rounded-full bg-gray-500 mr-2';
                    statusContainer.className = 'font-semibold text-gray-600';
                } else {
                    statusElement.textContent = 'Active';
                    indicator.className = 'h-2 w-2 rounded-full bg-green-500 mr-2';
                    statusContainer.className = 'font-semibold text-green-600';
                }
            }

            // Format number with commas
            function formatNumber(num) {
                return num.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ",");
            }

            // Create candidate card HTML
            function createCandidateCard(candidate) {
                const partyInfo = partyColors[candidate.partyName] || partyColors['default'];

                return `
                    <div class="bg-white rounded-lg border border-gray-200 shadow-sm hover:shadow-md transition-shadow"
                         data-candidate-id="${candidate.candidateId}">
                        <div class="p-5">
                            <div class="flex items-center mb-3">
                                <div class="h-16 w-16 rounded-full bg-gray-100 overflow-hidden mr-4 flex-shrink-0">
                                    <img src="/candidate-images/${candidate.candidateId}.jpg"
                                         alt="${candidate.candidateName}"
                                         class="h-full w-full object-cover"
                                         onerror="this.onerror=null;this.src='https://placehold.co/200x200?text=Candidate'">
                                </div>
                                <div>
                                    <h4 class="font-semibold text-gray-800 text-lg">${candidate.candidateName}</h4>
                                    <span class="inline-block px-2 py-1 ${partyInfo.bg} ${partyInfo.text} text-xs rounded-full">
                                        ${candidate.partyName}
                                    </span>
                                </div>
                            </div>

                            <div class="mt-4">
                                <div class="flex justify-between items-center mb-1">
                                    <span class="text-sm text-gray-500">Total Votes</span>
                                    <span class="text-sm font-medium text-gray-600 candidate-percentage">
                                        ${candidate.percentage.toFixed(1)}%
                                    </span>
                                </div>
                                <div class="w-full bg-gray-200 rounded-full h-3">
                                    <div class="${partyInfo.progress} h-3 rounded-full candidate-progress"
                                         style="width: ${candidate.percentage.toFixed(1)}%"></div>
                                </div>
                                <p class="text-3xl font-bold text-gray-800 mt-3 candidate-votes">
                                    ${formatNumber(candidate.voteCount)}
                                </p>
                            </div>
                        </div>
                    </div>
                `;
            }

            // Update candidate data
            function updateCandidateData(candidate) {
                const candidateElement = document.querySelector(`[data-candidate-id="${candidate.candidateId}"]`);
                if (candidateElement) {
                    const partyInfo = partyColors[candidate.partyName] || partyColors['default'];

                    // Update name and party (in case they changed)
                    candidateElement.querySelector('h4').textContent = candidate.candidateName;
                    const partyBadge = candidateElement.querySelector('span');
                    partyBadge.className = `inline-block px-2 py-1 ${partyInfo.bg} ${partyInfo.text} text-xs rounded-full`;
                    partyBadge.textContent = candidate.partyName;

                    // Update votes and percentage
                    candidateElement.querySelector('.candidate-votes').textContent = formatNumber(candidate.voteCount);
                    candidateElement.querySelector('.candidate-percentage').textContent = `${candidate.percentage.toFixed(1)}%`;
                    candidateElement.querySelector('.candidate-progress').style.width = `${candidate.percentage.toFixed(1)}%`;
                }
            }

            // Calculate and update total votes
            function updateTotalVotes(candidates) {
                const totalVotes = candidates.reduce((sum, candidate) => sum + candidate.voteCount, 0);
                document.getElementById('total-votes').textContent = formatNumber(totalVotes);

                // Update percentages for all candidates
                candidates.forEach(candidate => {
                    const candidateElement = document.querySelector(`[data-candidate-id="${candidate.candidateId}"]`);
                    if (candidateElement) {
                        const percentage = totalVotes > 0 ? (candidate.voteCount / totalVotes * 100) : 0;
                        candidateElement.querySelector('.candidate-percentage').textContent = `${percentage.toFixed(1)}%`;
                        candidateElement.querySelector('.candidate-progress').style.width = `${percentage.toFixed(1)}%`;
                    }
                });
            }

            // WebSocket event handlers
            socket.onopen = function(e) {
                console.log('WebSocket connection established for election', electionId);
                updateElectionStatus();
            };

            socket.onmessage = function(event) {
                const data = JSON.parse(event.data);
                console.log('Received data:', data);

                if (data.type === 'initialData') {
                    // Initial load of all candidates
                    const container = document.getElementById('candidates-container');
                    container.innerHTML = data.candidates.map(createCandidateCard).join('');
                    updateTotalVotes(data.candidates);
                }
                else if (data.type === 'update') {
                    // Single candidate update
                    const container = document.getElementById('candidates-container');

                    // Check if candidate already exists
                    const existingCandidate = document.querySelector(`[data-candidate-id="${data.candidate.candidateId}"]`);

                    if (existingCandidate) {
                        updateCandidateData(data.candidate);
                    } else {
                        // Add new candidate
                        container.insertAdjacentHTML('beforeend', createCandidateCard(data.candidate));
                    }

                    // Recalculate all percentages
                    const candidates = Array.from(document.querySelectorAll('[data-candidate-id]')).map(el => {
                        return {
                            candidateId: parseInt(el.getAttribute('data-candidate-id')),
                            voteCount: parseInt(el.querySelector('.candidate-votes').textContent.replace(/,/g, ''))
                        };
                    });

                    updateTotalVotes(candidates);
                }
                else if (data.type === 'electionStatus') {
                    // Election status changed (started/ended)
                    electionData.status = data.status;
                    updateElectionStatus();
                }
            };

            socket.onclose = function(event) {
                if (event.wasClean) {
                    console.log(`WebSocket connection closed cleanly, code=${event.code}, reason=${event.reason}`);
                } else {
                    console.log('WebSocket connection died');
                    // Show connection error message
                    const container = document.getElementById('candidates-container');
                    container.innerHTML = `
                        <div class="col-span-3 text-center py-10">
                            <div class="text-red-500 mb-2">Connection lost</div>
                            <div class="text-gray-500 text-sm">Attempting to reconnect...</div>
                        </div>
                    `;

                    // Try to reconnect after 5 seconds
                    setTimeout(() => {
                        window.location.reload();
                    }, 5000);
                }
            };

            socket.onerror = function(error) {
                console.log('WebSocket error:', error);
            };

            // Update remaining time until election ends
            function updateRemainingTime() {
                const now = new Date();
                if (now > electionData.endTime) {
                    document.getElementById('time-remaining').textContent = "Election ended";
                    return;
                }

                const diff = electionData.endTime - now;
                const hours = Math.floor(diff / (1000 * 60 * 60));
                const minutes = Math.floor((diff % (1000 * 60 * 60)) / (1000 * 60));
                const seconds = Math.floor((diff % (1000 * 60)) / 1000);

                document.getElementById('time-remaining').textContent =
                    `${hours.toString().padStart(2, '0')}:${minutes.toString().padStart(2, '0')}:${seconds.toString().padStart(2, '0')}`;

                setTimeout(updateRemainingTime, 1000);
            }

            // Start the countdown
            updateRemainingTime();
        });
    </script>
</body>
</html>