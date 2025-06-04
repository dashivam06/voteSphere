<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:formatDate value="${election.startTime}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="startDateJs" />
<fmt:formatDate value="${election.endTime}" pattern="yyyy-MM-dd'T'HH:mm:ss" var="endDateJs" />
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="loader.jsp" %>

<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>VoteSphere - Election Details</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/global.css" />
    <link rel="icon" src="/resources/favicon.ico" type="image/x-icon" />

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
              success: {
                50: "#f0fdf4",
                100: "#dcfce7",
                200: "#bbf7d0",
                300: "#86efac",
                400: "#4ade80",
                500: "#22c55e",
                600: "#16a34a",
                700: "#15803d",
                800: "#166534",
                900: "#14532d",
              }
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
      <div class="p-8 overflow-y-auto">
        <div class="bg-white rounded-lg shadow-md p-6 mx-auto">
          <div class="flex justify-between items-center mb-6">
            <h1 class="text-2xl font-bold text-gray-800">Election Details</h1>
            <a href="/admin/election" class="bg-gray-100 text-gray-600 px-4 py-2 rounded-lg hover:bg-gray-200 transition-colors duration-200">
              Back to Elections
            </a>
          </div>

          <!-- Election Banner -->
          <div class="mb-8">
            <div class="h-48 w-full bg-gray-100 rounded-lg overflow-hidden">
              <img id="cover-image" src="${election.coverImage}" alt="Election Cover" class="w-full h-full object-cover object-center" />
            </div>
          </div>

         <!-- Election Header -->
                   <div class="mb-8 flex justify-between items-center">
                     <div>
                       <h2 id="election-name" class="text-2xl py-3 font-bold text-gray-800">
                         ${election.name}
                       </h2>
                       <p class="text-gray-600">
                         Election ID: <span id="election-id">${election.electionId}</span>
                       </p>
                     </div>

                     <form action="/admin/election/handleElectionAction" method="get">
                       <input type="hidden" name="electionId" value="${election.electionId}" />

                       <c:choose>
                         <c:when test="${election.status == 'Upcoming'}">
                           <c:set var="action" value="notify"/>
                           <c:set var="label" value="Notify Election"/>
                           <c:set var="icon" value="notify-icon.svg"/> <%-- optional custom icon path --%>
                         </c:when>

                         <c:when test="${election.status == 'Past'}">
                           <c:set var="action" value="download"/>
                           <c:set var="label" value="Download Report"/>
                           <c:set var="icon">

                           </c:set>
                         </c:when>

                         <c:otherwise>
                           <c:set var="action" value="view"/>
                           <c:set var="label" value="View Details"/>
                           <c:set var="icon" value="view-icon.svg"/>
                         </c:otherwise>
                       </c:choose>

                       <button
                         type="submit"
                         name="action"
                         value="${action}"

                         class="bg-primary-600 text-white px-6 py-2 rounded-lg hover:bg-primary-700 transition-colors duration-200 flex items-center"
                       >
                         ${label}
                         <%-- You can render icon dynamically if needed: --%>
                         <img src="/icons/${icon}" alt="${label}" class="ml-2 w-4 h-4"/>
                       </button>
                     </form>
                   </div>

          <!-- Election Information -->
          <div class="mb-8">
            <h3 class="text-lg font-semibold text-gray-700 mb-4 border-b pb-2">
              Election Information
            </h3>
            <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
              <div>
                <span class="font-medium text-gray-700">Type:</span>
                <p id="election-type" class="mt-1">${election.type}</p>
              </div>
              <div>
                <span class="font-medium text-gray-700">Date:</span>
                <p id="election-date" class="mt-1">
                  <fmt:formatDate value="${election.date}" pattern="MMMM d, yyyy" />
                </p>
              </div>
              <div>
                <span class="font-medium text-gray-700">Start Time:</span>
                <p id="start-time" class="mt-1">
                  <fmt:formatDate value="${election.startTime}" pattern="hh:mm a" />
                </p>
              </div>
              <div>
                <span class="font-medium text-gray-700">End Time:</span>
                <p id="end-time" class="mt-1">
                  <fmt:formatDate value="${election.endTime}" pattern="hh:mm a" />
                </p>
              </div>
            </div>
          </div>

          <!-- Election Countdown/Voting Status -->
          <%-- Only show countdown if the election status is "Upcoming" --%>
          <c:if test="${election.status == 'Upcoming'}">
            <div
              id="countdownContainer"
              class="mb-8 bg-primary-50 p-4 rounded-lg border border-primary-100"
            >
              <h3 class="text-lg font-semibold text-primary-800 mb-2">
                Election Countdown
              </h3>
              <div class="grid grid-cols-4 gap-2 text-center">
                <div>
                  <div id="days" class="text-3xl font-bold text-primary-700">0</div>
                  <div class="text-xs text-primary-600">Days</div>
                </div>
                <div>
                  <div id="hours" class="text-3xl font-bold text-primary-700">0</div>
                  <div class="text-xs text-primary-600">Hours</div>
                </div>
                <div>
                  <div id="minutes" class="text-3xl font-bold text-primary-700">0</div>
                  <div class="text-xs text-primary-600">Minutes</div>
                </div>
                <div>
                  <div id="seconds" class="text-3xl font-bold text-primary-700">0</div>
                  <div class="text-xs text-primary-600">Seconds</div>
                </div>
              </div>
            </div>
            </c:if>

          <!-- Vote Statistics (shown only during and after election) -->
          <div id="vote-stats-container" class="mb-8 hidden">
            <h3 class="text-lg font-semibold text-gray-700 mb-4 border-b pb-2">
              Voting Statistics
            </h3>
            <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
              <div class="bg-blue-50 rounded-lg p-4 border border-blue-100">
                <div class="flex items-center justify-between">
                  <h4 class="font-semibold text-blue-800">Total Votes Cast</h4>
                  <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 text-blue-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
                  </svg>
                </div>
                <p id="total-votes" class="text-3xl font-bold text-blue-800 mt-2">0</p>
                <div class="mt-2">
                  <div class="h-2 bg-blue-200 rounded-full">
                    <div id="vote-progress" class="h-2 bg-blue-600 rounded-full" style="width: 0%"></div>
                  </div>
                  <p class="text-xs text-blue-600 mt-1"><span id="vote-percentage">0</span>% of eligible voters</p>
                </div>
              </div>

              <div class="bg-green-50 rounded-lg p-4 border border-green-100">
                <div class="flex items-center justify-between">
                  <h4 class="font-semibold text-green-800">Active Polling Stations</h4>
                  <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 text-green-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 21V5a2 2 0 00-2-2H7a2 2 0 00-2 2v16m14 0h2m-2 0h-5m-9 0H3m2 0h5M9 7h1m-1 4h1m4-4h1m-1 4h1m-5 10v-5a1 1 0 011-1h2a1 1 0 011 1v5m-4 0h4" />
                  </svg>
                </div>
                <p id="active-stations" class="text-3xl font-bold text-green-800 mt-2">0</p>
                <p class="text-sm text-green-600 mt-1"><span id="stations-percentage">0</span>% operational</p>
              </div>

              <div class="bg-purple-50 rounded-lg p-4 border border-purple-100">
                <div class="flex items-center justify-between">
                  <h4 class="font-semibold text-purple-800">Voting Rate</h4>
                  <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 text-purple-500" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 7h8m0 0v8m0-8l-8 8-4-4-6 6" />
                  </svg>
                </div>
                <p id="voting-rate" class="text-3xl font-bold text-purple-800 mt-2">0</p>
                <p class="text-sm text-purple-600 mt-1">votes per hour</p>
              </div>
            </div>
          </div>

          <!-- Candidates Section -->
          <div class="mb-8">
            <h3 class="text-lg font-semibold text-gray-700 mb-4 border-b pb-2">
              Candidates
            </h3>
            <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
              <c:forEach var="candidate" items="${candidates}">
                <div class="bg-white rounded-lg border overflow-hidden shadow-sm hover:shadow-md transition-shadow">
                  <div class="h-41  bg-gray-100">
                    <img src="${candidate.profileImage}" alt="${candidate.fname} ${candidate.lname}" class="w-full h-full object-cover object-center" />
                  </div>
                  <div class="p-4">
                    <div class="flex items-center mb-2">
                      <h4 class="font-semibold text-gray-800">${candidate.fname} ${candidate.lname}</h4>
                      <c:choose>
                        <c:when test="${not empty candidate.partyName}">
                          <span class="ml-2 px-2 py-0.5 bg-blue-100 text-blue-800 text-xs rounded-full">
                            ${candidate.partyName}
                          </span>
                        </c:when>
                        <c:otherwise>
                          <span class="ml-2 px-2 py-0.5 bg-gray-100 text-gray-800 text-xs rounded-full">
                            Independent
                          </span>
                        </c:otherwise>
                      </c:choose>
                    </div>
                    <p class="text-sm text-gray-600 mb-4 line-clamp-2">
                      ${candidate.bio}
                    </p>
                    <div class="flex justify-between items-center">
                      <a href="/admin/candidate/view/${candidate.candidateId}" class="text-primary-600 hover:text-primary-800 text-sm font-medium">
                        View Profile >>
                      </a>
                      <span id="votes-${candidate.candidateId}" class="text-xs font-medium px-2 py-1 rounded-full bg-gray-100 text-gray-800 hidden">
                        0 votes
                      </span>
                    </div>
                  </div>
                </div>
              </c:forEach>
            </div>
          </div>

    <!-- Statistics Section -->
    <div class="mb-8">
      <h3 class="text-lg font-semibold text-gray-700 mb-4 border-b pb-2">
        Election Statistics
      </h3>

      <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-2 gap-6">

        <!-- Total Candidates -->
        <div class="bg-blue-50 rounded-lg p-4 border border-blue-100">
          <h4 class="font-semibold text-blue-800">Total Candidates</h4>
          <p class="text-3xl font-bold text-blue-800">
            ${fn:length(candidates)}
          </p>
        </div>

        <!-- Eligible Voters -->
        <div class="bg-green-50 rounded-lg p-4 border border-green-100">
          <h4 class="font-semibold text-green-800">Eligible Voters</h4>
          <p class="text-3xl font-bold text-green-800">
            <fmt:formatNumber value="${election.eligibleVoters}" />
          </p>
        </div>
    <!-- Shown only if election is Past -->
    <c:if test="${election.status == 'Past'}">

      <!-- Winning Party - spans 2 columns -->
      <div class="bg-purple-50 rounded-lg p-4 border border-purple-100 col-span-1 md:col-span-2">
        <h4 class="font-semibold text-purple-800">Winning Party</h4>
        <p class="text-xl font-bold text-purple-800">
          ${winningPartyName}
        </p>
      </div>

      <!-- Total Votes Cast - 1 column -->
      <div class="bg-yellow-50 rounded-lg p-4 border border-yellow-100">
        <h4 class="font-semibold text-yellow-800">Total Votes</h4>
        <p class="text-3xl font-bold text-yellow-800">
          ${votesCastCount}
        </p>
      </div>


        <!-- Independent Candidates -->
          <div class="bg-red-50 rounded-lg p-4 border border-red-100">
            <h4 class="font-semibold text-red-800">Independent Candidates</h4>
            <p class="text-3xl font-bold text-red-800">
              ${independentCount}
            </p>
          </div>

    </c:if>
      </div>
    </div>

          <!-- Actions Section -->
          <div class="flex justify-end space-x-3">
            <a href="/admin/election/edit?id=${election.electionId}" class="bg-blue-500 text-white px-8 py-2 rounded-lg hover:bg-yellow-600 transition-colors duration-200">
              Edit Election
            </a>
            <a href="/admin/election/delete?id=${election.electionId}" class="bg-red-600 text-white px-6 py-2 rounded-lg hover:bg-red-700 transition-colors duration-200" onclick="return confirm('Are you sure you want to delete this election?')">
              Delete
            </a>

          </div>
        </div>
      </div>
    </div>

    <!-- Report Generation Modal -->
    <div id="report-modal" class="fixed inset-0 bg-black bg-opacity-50 z-50 hidden flex items-center justify-center p-4">
      <div class="bg-white rounded-lg shadow-xl w-full max-w-2xl">
        <div class="p-6">
          <div class="flex justify-between items-center mb-4">
            <h3 class="text-xl font-bold text-gray-800">Generate Election Report</h3>
            <button onclick="document.getElementById('report-modal').classList.add('hidden')" class="text-gray-400 hover:text-gray-600">
              <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12" />
              </svg>
            </button>
          </div>

          <div class="mb-6">
            <p class="text-gray-600 mb-4">Select the report type and format for ${election.name}</p>

            <div class="space-y-4">
              <div>
                <label class="block text-sm font-medium text-gray-700 mb-1">Report Type</label>
                <select class="w-full px-3 py-2 border border-gray-300 rounded-md shadow-sm focus:outline-none focus:ring-primary-500 focus:border-primary-500">
                  <option>Full Election Report</option>
                  <option>Voter Turnout Analysis</option>
                  <option>Candidate Performance</option>
                  <option>Polling Station Statistics</option>
                </select>
              </div>

              <div>
                <label class="block text-sm font-medium text-gray-700 mb-1">Format</label>
                <div class="flex space-x-4">
                  <label class="inline-flex items-center">
                    <input type="radio" name="format" checked class="h-4 w-4 text-primary-600 focus:ring-primary-500 border-gray-300">
                    <span class="ml-2 text-gray-700">PDF</span>
                  </label>
                  <label class="inline-flex items-center">
                    <input type="radio" name="format" class="h-4 w-4 text-primary-600 focus:ring-primary-500 border-gray-300">
                    <span class="ml-2 text-gray-700">Excel</span>
                  </label>
                  <label class="inline-flex items-center">
                    <input type="radio" name="format" class="h-4 w-4 text-primary-600 focus:ring-primary-500 border-gray-300">
                    <span class="ml-2 text-gray-700">CSV</span>
                  </label>
                </div>
              </div>
            </div>
          </div>

          <div class="flex justify-end space-x-3">
            <button onclick="document.getElementById('report-modal').classList.add('hidden')" class="bg-gray-200 text-gray-800 px-4 py-2 rounded-lg hover:bg-gray-300 transition-colors duration-200">
              Cancel
            </button>
            <button class="bg-primary-600 text-white px-4 py-2 rounded-lg hover:bg-primary-700 transition-colors duration-200">
              <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 inline-block mr-1" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16v1a3 3 0 003 3h10a3 3 0 003-3v-1m-4-4l-4 4m0 0l-4-4m4 4V4" />
              </svg>
              Generate & Download
            </button>
          </div>
        </div>
      </div>
    </div>

    <script>
      document.addEventListener("DOMContentLoaded", function() {
        const startDate = new Date("${startDateJs}");
        const endDate = new Date("${endDateJs}");
        const now = new Date();

        const statusBadge = document.getElementById("status-badge");
        const countdownContainer = document.getElementById("countdown-container");
        const voteStatsContainer = document.getElementById("vote-stats-container");
        const generateReportBtn = document.getElementById("generate-report-btn");

        // Simulated vote data - replace with real data from your backend
        const electionStats = {
          totalVotes: 12543,
          eligibleVoters: ${election.eligibleVoters},
          eligibleVoters: ${election.eligibleVoters},
          votingRate: 342, // votes per hour
          candidateVotes: {
            <c:forEach var="candidate" items="${candidates}" varStatus="loop">
              ${candidate.candidateId}: ${loop.index * 250 + 500}<c:if test="${!loop.last}">,</c:if>
            </c:forEach>
          }
        };

        // Determine election status
        if (now < startDate) {
          // Election is upcoming
          statusBadge.textContent = "Upcoming";
          statusBadge.classList.add("bg-yellow-100", "text-yellow-800");

          countdownContainer.classList.add("bg-primary-50", "border-primary-100");
          countdownContainer.innerHTML = `
            <h3 class="text-lg font-semibold text-primary-800 mb-2">
              Election Countdown
            </h3>
            <div class="grid grid-cols-4 gap-2 text-center">
              <div>
                <div id="days" class="text-3xl font-bold text-primary-700">--</div>
                <div class="text-xs text-primary-600">Days</div>
              </div>
              <div>
                <div id="hours" class="text-3xl font-bold text-primary-700">--</div>
                <div class="text-xs text-primary-600">Hours</div>
              </div>
              <div>
                <div id="minutes" class="text-3xl font-bold text-primary-700">--</div>
                <div class="text-xs text-primary-600">Minutes</div>
              </div>
              <div>
                <div id="seconds" class="text-3xl font-bold text-primary-700">--</div>
                <div class="text-xs text-primary-600">Seconds</div>
              </div>
            </div>
          `;

          updateCountdown(startDate);
        } else if (now >= startDate && now <= endDate) {
          // Election is in progress
          statusBadge.textContent = "In Progress - Voting Active";
          statusBadge.classList.add("bg-green-100", "text-green-800");

          countdownContainer.classList.add("bg-green-50", "border-green-100");
          countdownContainer.innerHTML = `
            <div class="flex items-start justify-between">
              <div>
                <h3 class="text-lg font-semibold text-green-800 mb-1">Voting is currently in progress</h3>
                <p class="text-green-600">Time remaining until voting ends:</p>
              </div>
              <span class="px-2 py-1 bg-green-200 text-green-800 text-xs rounded-full flex items-center">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-3 w-3 mr-1" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
                </svg>
                LIVE
              </span>
            </div>
            <div class="grid grid-cols-4 gap-2 text-center mt-4">
              <div>
                <div id="days" class="text-3xl font-bold text-green-700">--</div>
                <div class="text-xs text-green-600">Days</div>
              </div>
              <div>
                <div id="hours" class="text-3xl font-bold text-green-700">--</div>
                <div class="text-xs text-green-600">Hours</div>
              </div>
              <div>
                <div id="minutes" class="text-3xl font-bold text-green-700">--</div>
                <div class="text-xs text-green-600">Minutes</div>
              </div>
              <div>
                <div id="seconds" class="text-3xl font-bold text-green-700">--</div>
                <div class="text-xs text-green-600">Seconds</div>
              </div>
            </div>
          `;

          // Show voting statistics
          voteStatsContainer.classList.remove("hidden");
          updateVoteStats(electionStats);
          updateCountdown(endDate);
        } else {
          // Election is over
          statusBadge.textContent = "Completed";
          statusBadge.classList.add("bg-gray-100", "text-gray-800");

          countdownContainer.classList.add("bg-gray-50", "border-gray-200");
          countdownContainer.innerHTML = `
            <h3 class="text-lg font-semibold text-gray-700 mb-2">
              This election has concluded
            </h3>
            <p class="text-gray-600">
              Voting took place from
              <span class="font-medium"><fmt:formatDate value="${election.startTime}" pattern="MMMM d, hh:mm a" /></span>
              to
              <span class="font-medium"><fmt:formatDate value="${election.endTime}" pattern="MMMM d, hh:mm a" /></span>
            </p>
          `;

          // Show final voting statistics
          voteStatsContainer.classList.remove("hidden");
          updateVoteStats(electionStats);

          // Show candidate vote counts
          <c:forEach var="candidate" items="${candidates}">
            const voteBadge = document.getElementById("votes-${candidate.candidateId}");
            if (voteBadge) {
              voteBadge.textContent = electionStats.candidateVotes[${candidate.candidateId}] + " votes";
              voteBadge.classList.remove("hidden");
            }
          </c:forEach>

          // Show generate report button
          generateReportBtn.classList.remove("hidden");
          generateReportBtn.addEventListener("click", function() {
            document.getElementById("report-modal").classList.remove("hidden");
          });
        }
      });

     const electionDate = new Date("${election.date}"); // example: 2025-12-31T10:00:00

         function updateCountdown() {
           const now = new Date().getTime();
           const timeLeft = electionDate - now;

           if (timeLeft <= 0) {
             document.getElementById("countdownContainer").style.display = "none";
             return;
           }

           const days = Math.floor(timeLeft / (1000 * 60 * 60 * 24));
           const hours = Math.floor((timeLeft / (1000 * 60 * 60)) % 24);
           const minutes = Math.floor((timeLeft / (1000 * 60)) % 60);
           const seconds = Math.floor((timeLeft / 1000) % 60);

           document.getElementById("days").innerText = days;
           document.getElementById("hours").innerText = hours;
           document.getElementById("minutes").innerText = minutes;
           document.getElementById("seconds").innerText = seconds;
         }

         updateCountdown();
         setInterval(updateCountdown, 1000);

      function updateVoteStats(stats) {
        // Update vote count
        document.getElementById("total-votes").textContent = stats.totalVotes.toLocaleString();

        // Calculate and update percentages
        const votePercentage = Math.round((stats.totalVotes / stats.eligibleVoters) * 100);
        document.getElementById("vote-percentage").textContent = votePercentage;
        document.getElementById("vote-progress").style.width = `${votePercentage}%`;

        // Update active stations
        const stationsPercentage = Math.round((stats.eligibleVoters / ${election.eligibleVoters}) * 100);
        document.getElementById("active-stations").textContent = stats.eligibleVoters.toLocaleString();
        document.getElementById("stations-percentage").textContent = stationsPercentage;

        // Update voting rate
        document.getElementById("voting-rate").textContent = stats.votingRate.toLocaleString();
      }
    </script>
  </body>
</html>