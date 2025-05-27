<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> <%@ taglib
        prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:formatDate
        value="${election.startTime}"
        pattern="yyyy-MM-dd'T'HH:mm:ss"
        var="startDateJs"
/>
<fmt:formatDate
        value="${election.endTime}"
        pattern="yyyy-MM-dd'T'HH:mm:ss"
        var="endDateJs"
/>
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
                        href="${pageContext.request.contextPath}/elections"
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
                    <span class="inline-flex items-center text-green-500 font-semibold">
                   &#9679; Active
                    </span>
                            </p>
                        </div>
                        <div>
                            <span class="text-sm text-gray-500">Start Time</span>
                            <p id="start-time" class="font-semibold text-gray-800">
                                <fmt:formatDate
                                        value="${election.startTime}"
                                        pattern="MMM d, yyyy hh:mm a"
                                />
                            </p>
                        </div>
                        <div>
                            <span class="text-sm text-gray-500">End Time</span>
                            <p id="end-time" class="font-semibold text-gray-800">
                                <fmt:formatDate
                                        value="${election.endTime}"
                                        pattern="MMM d, yyyy hh:mm a"
                                />
                            </p>
                        </div>
                    </div>
                </div>
            </div>

            <!-- Live Results Section -->
            <div class="mb-8">
                <h3
                        class="text-lg font-semibold text-gray-700 mb-4 border-b pb-2 flex items-center"
                >
                    <span>Live Vote Count</span>
                    <span class="ml-2 relative flex h-3 w-3">
                <span
                        class="animate-ping absolute inline-flex h-full w-full rounded-full bg-green-400 opacity-75"
                ></span>
                <span
                        class="relative inline-flex rounded-full h-3 w-3 bg-green-500"
                ></span>
              </span>
                    <span class="ml-2 text-sm font-normal text-gray-500"
                    >Real-time updates</span
                    >
                </h3>

                <!-- Total Votes -->
                <div class="mb-6 bg-gray-50 p-4 rounded-lg">
                    <div class="flex justify-between items-center">
                        <span class="text-gray-600">Total Votes Cast</span>
                        <span id="total-votes" class="text-xl font-bold text-gray-800"
                        >0</span
                        >
                    </div>
                </div>

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
            `ws://votesphere.com/election-results/${election.electionId}`
        );

        // WebSocket event handlers
        socket.onopen = function (event) {
            console.log("WebSocket connection established");
        };

        socket.onmessage = function (event) {
            const data = JSON.parse(event.data);
            updateResults(data);
        };

        socket.onclose = function (event) {
            console.log("WebSocket connection closed: ", event.reason);

            // Try to reconnect after 5 seconds
            setTimeout(function () {
                console.log("Attempting to reconnect...");
                const newSocket = new WebSocket(
                    `ws://votesphere.com/election-results/${election.electionId}`
                );
                socket = newSocket;
            }, 5000);
        };

        socket.onerror = function (error) {
            console.error("WebSocket error: ", error);
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

        // Function to create a candidate result card
        function createCandidateCard(candidate, isLeader) {
            const cardDiv = document.createElement("div");
            cardDiv.className =
                "bg-white shadow-md border rounded-xl overflow-hidden";

            // Safely handle missing data
            const safeName = candidate?.candidateName || "Unknown";
            const safeParty = candidate.partyName || "Independent";
            const safeVotes = candidate.voteCount || 0;
            const safePct = candidate.percentage
                ? candidate.percentage.toFixed(1)
                : "0.0";
            // const safeImage = candidate.image || "https://placehold.co/400x800";
            const safeImage = candidate.partyImage || "https://placehold.co/400x800";
            console.log(candidate.partyImage)
            console.log(candidate);
            console.log(safeName);

            cardDiv.innerHTML =
                `
        <div class="p-6">
            <div class="flex items-center gap-3 mb-2">
                <div>
                <img src="/uploads/`+safeImage+`" alt="Candidate Image" class="w-20 aspect-square rounded-full object-cover">
                </div>
                <div class="flex flex-col gap-1 justify-center items-start">
                    <h3 class="font-semibold text-gray-800 text-xl">` +
                safeName +
                `</h3>
                    <span class="text-xs text-gray-500 bg-red-100 text-red-500 py-1 px-3 rounded-full text-sm font-medium">` +
                safeParty +
                `</span>
                </div>
            </div>
            <div class="mt-4">
                <div class="flex justify-between mb-1 text-gray-600">
                    <span>Total votes</span>
                    <span class="font-medium"> ` + safePct + `%</span>
                </div>
                <div class="w-full bg-gray-200 rounded-full h-3.5 text-gray-600">
                    <div class="bg-primary-600 h-3.5 rounded-full"
                         style="width: ` + safePct + `%"></div>
                </div>
                <p class="mt-3 text-6xl font-bold"> ` +  safeVotes + `</p>
            </div>
        </div>
    `;

            console.log(cardDiv);

            return cardDiv;
        }


    });
</script>
</body>
</html>