<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib
        prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ include file="../loader-animation.jsp" %>

<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> <%@ page
        isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>VoteSphere - Live Election Results</title>
    <link
            rel="stylesheet"
            href="${pageContext.request.contextPath}/styles/global.css"
    />
    <link rel="icon" src="/resources/favicon.ico" type="image/x-icon" />

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
    <div class="p-6 overflow-y-auto">
        <div class="bg-white rounded-lg shadow-md p-6 mx-auto">
            <div class="flex justify-between items-center mb-6">
                <h1 class="text-2xl font-bold text-gray-800">
                    Live Election Results
                </h1>
                <a
                        href="${pageContext.request.contextPath}/admin/election"
                        class="bg-gray-100 text-gray-600 px-4 py-2 rounded-lg hover:bg-gray-200 transition-colors duration-200"
                >
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
    <span id="connection-indicator" class="inline-flex items-center font-semibold">
        &#9679; Checking status...
    </span>
                            </p>

                        </div>
                        <div>
                            <span class="text-sm text-gray-500">Start Time</span>
                            <p id="start-time" class="font-semibold text-gray-800">
                                <fmt:formatDate value="${election.startDateTime}" pattern="MMM d, yyyy hh:mm a" />
                            </p>
                        </div>
                        <div>
                            <span class="text-sm text-gray-500">End Time</span>
                            <p id="end-time" class="font-semibold text-gray-800">
                                <fmt:formatDate value="${election.endDateTime}" pattern="MMM d, yyyy hh:mm a" />

                            </p>
                        </div>
                    </div>
                </div>
            </div>
            <h3
                    class="text-lg font-semibold text-gray-700 mb-4 border-b pb-2 flex items-center justify-between"
            >
                <div class="flex items-center">
                    <span>Live Vote Count</span>
                    <span class="ml-2 relative flex h-3 w-3">
      <span
              class="animate-ping absolute inline-flex h-full w-full rounded-full bg-green-400 opacity-75"
      ></span>
      <span class="relative inline-flex rounded-full h-3 w-3 bg-green-500"></span>
    </span>
                </div>

                <!-- Right side: total vote count -->
                <span class="text-lg font-medium text-gray-600">
    Total Votes: ( <span id="total-votes" class="font-semibold text-gray-800">0</span> )
  </span>
            </h3>




                <!-- Candidates Vote Count Grid -->
                <div
                        class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6"
                        id="candidates-container"
                >
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
    document.addEventListener("DOMContentLoaded", function () {
        // Get election ID from the path
        let electionId = window.location.pathname.split("/").pop();
        let totalVotes = 0;



        // WebSocket connection
        let socket = new WebSocket(
            `${wsUrl}${election.electionId}`
        );

        // WebSocket event handlers
        socket.onopen = function (event) {
            console.log("WebSocket connection established");
            updateConnectionStatus(true);

        };

        socket.onmessage = function (event) {
            const data = JSON.parse(event.data);
            updateResults(data);
        };

        socket.onclose = function (event) {
            console.log("WebSocket connection closed: ", event.reason);
            updateConnectionStatus(false);

            // Try to reconnect after 5 seconds
            setTimeout(function () {
                console.log("Attempting to reconnect...");
                const newSocket = new WebSocket(
                    `${wsUrl}${election.electionId}`
                );
                socket = newSocket;
            }, 5000);
        };

        socket.onerror = function (error) {
            console.error("WebSocket error: ", error);
            updateConnectionStatus(false);

        };

        // Function to update results in the UI
        function updateResults(results) {
            if (!results || results.length === 0) {
                document.getElementById("candidates-container").innerHTML =
                    '<div class="col-span-full text-center py-10 text-gray-500">No results available yet</div>';
                return;
            }

            console.log("Received results: ", results);

            // Calculate total votes
            totalVotes = results.reduce(
                (sum, candidate) => sum + candidate.voteCount,
                0
            );
            document.getElementById("total-votes").textContent = totalVotes;

            console.log("Total votes: ", totalVotes);

            // Sort results by vote count (highest first)
            results.sort((a, b) => b.voteCount - a.voteCount);
            console.log("Sorted results: ", results);

            // Clear existing content
            const container = document.getElementById("candidates-container");
            container.innerHTML = "";

            // Add candidate cards
            results.forEach((candidate, index) => {
                const card = createCandidateCard(candidate, index === 0);
                console.log("Candidate details:", JSON.stringify(candidate));
                container.appendChild(card);
            });
        }

        function createCandidateCard(candidate, index) {
            var cardDiv = document.createElement("div");
            cardDiv.className = "bg-white shadow-md border rounded-xl overflow-hidden";

            var safeName = candidate && candidate.candidateName ? candidate.candidateName : "Unknown";
            var safeParty = candidate.partyName ? candidate.partyName : "Independent";
            var safeVotes = candidate.voteCount ? candidate.voteCount : 0;
            var safePct = candidate.percentage ? candidate.percentage.toFixed(1) : "0.0";
            var safeImage = candidate.partyImage ? + candidate.partyImage : "https://placehold.co/400x800";

            // Color palette for first 10 parties (text color + background color)
            var partyColors = [
                { text: "#2563eb", bg: "#bfdbfe" },  // Blue (1st place)
                { text: "#dc2626", bg: "#fecaca" },  // Red (2nd place)
                { text: "#374151", bg: "#d1d5db" },  // Dark Grey (3rd place)
                { text: "#16a34a", bg: "#bbf7d0" },  // Green (4th)
                { text: "#9333ea", bg: "#e9d5ff" },  // Purple (5th)
                { text: "#ea580c", bg: "#fed7aa" },  // Orange (6th)
                { text: "#0891b2", bg: "#bae6fd" },  // Teal (7th)
                { text: "#e11d48", bg: "#fecdd3" },  // Pink (8th)
                { text: "#ca8a04", bg: "#fef08a" },  // Yellow (9th)
                { text: "#65a30d", bg: "#d9f99d" }   // Lime (10th)
            ];

            // Generate a random but visually pleasing color pair
            function getRandomColor() {
                var hues = [0, 30, 60, 120, 180, 210, 240, 270, 300, 330];
                var hue = hues[Math.floor(Math.random() * hues.length)];
                return {
                    text: "hsl(" + hue + ", 70%, 45%)",
                    bg: "hsl(" + hue + ", 70%, 90%)"
                };
            }

            // Get colors based on position
            var colors = partyColors[index] || getRandomColor();

            cardDiv.innerHTML =
                '<div class="p-4">' +
                '<div class="flex items-center gap-3 mb-2">' +
                '<div>' +
                '<img src="' + safeImage + '" alt="Party Logo" class="w-16 aspect-square rounded-full object-cover">' +
                '</div>' +
                '<div class="flex flex-col gap-1 justify-center items-start">' +
                '<h3 class="font-semibold text-gray-800 text-lg">' + safeName + '</h3>' +
                '<span class="text-xs py-1 px-3 rounded-full text-sm font-medium" ' +
                'style="color: ' + colors.text + '; background-color: ' + colors.bg + '">' +
                safeParty +
                '</span>' +
                '</div>' +
                '</div>' +
                '<div class="mt-3">' +
                '<div class="flex justify-between mb-1 text-gray-600">' +
                '<span class="text-sm">Total votes</span>' +
                '<span class="font-medium text-sm" style="color: ' + colors.text + '">' + safePct + '%</span>' +
                '</div>' +
                '<div class="w-full bg-gray-200 rounded-full h-2.5">' +
                '<div style="width: ' + safePct + '%; background-color: ' + colors.text + '; height: 10px; border-radius: 9999px;"></div>' +
                '</div>' +
                '<p class="mt-2 text-4xl font-bold">' + safeVotes + '</p>' +
                '</div>' +
                '</div>';

            return cardDiv;
        }
    });

    function updateConnectionStatus(isActive) {
        const indicator = document.getElementById("connection-indicator");
        if (isActive) {
            indicator.innerHTML = "&#9679; Active";
            indicator.classList.remove("text-red-500");
            indicator.classList.add("text-green-500");
        } else {
            indicator.innerHTML = "&#9679; Inactive";
            indicator.classList.remove("text-green-500");
            indicator.classList.add("text-red-500");
        }
    }

</script>
</body>
</html>