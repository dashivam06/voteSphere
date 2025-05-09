<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>VoteSphere - Voter Dashboard</title>
<link rel="stylesheet" href="../styles/global.css" />
<script src="https://cdn.tailwindcss.com"></script>
<link
	href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap"
	rel="stylesheet" />
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
            animation: {
              "fade-in": "fadeIn 0.5s ease-in-out",
              "slide-in-left": "slideInLeft 0.5s ease-out",
              "slide-in-right": "slideInRight 0.5s ease-out",
              "slide-up": "slideUp 0.5s ease-out",
            },
            keyframes: {
              fadeIn: {
                "0%": { opacity: "0" },
                "100%": { opacity: "1" },
              },
              slideInLeft: {
                "0%": { transform: "translateX(-20px)", opacity: "0" },
                "100%": { transform: "translateX(0)", opacity: "1" },
              },
              slideInRight: {
                "0%": { transform: "translateX(20px)", opacity: "0" },
                "100%": { transform: "translateX(0)", opacity: "1" },
              },
              slideUp: {
                "0%": { transform: "translateY(20px)", opacity: "0" },
                "100%": { transform: "translateY(0)", opacity: "1" },
              },
            },
          },
        },
      };
    </script>
<style>
/* Custom CSS for animations and hover effects */
.hover-scale {
	transition: transform 0.3s ease-in-out;
}

.hover-scale:hover {
	transform: scale(1.03);
}

.progress-ring-circle {
	transition: stroke-dashoffset 0.5s ease-in-out;
	transform: rotate(-90deg);
	transform-origin: 50% 50%;
}
</style>
</head>
<body class="font-sans bg-gray-100 flex h-screen overflow-hidden">
	<!-- Include sidebar -->
	<div id="sidebar-container"></div>

	<!-- Main Content -->
	<div
		class="flex-grow flex flex-col ml-0 lg:ml-64 transition-all duration-300 ease-in-out">
		<!-- Navbar -->
		<div id="navbar-container"></div>

		<!-- Main Content Area -->
		<main class="flex-1 overflow-y-auto p-4 bg-gray-100">
			<!-- Welcome Banner -->
			<div class="bg-white rounded-lg shadow-sm p-6 mb-6 animate-fade-in">
				<div class="flex flex-wrap items-center justify-between">
					<div class="w-full md:w-3/4">
						<h1 class="text-2xl font-bold text-gray-800">Welcome back,
							John!</h1>
						<p class="text-gray-600 mt-1">Your voice matters - participate
							in upcoming elections and help shape the future.</p>
					</div>
					<div class="w-full md:w-1/4 flex justify-end mt-4 md:mt-0">
						<a href="elections.html"
							class="bg-primary-600 text-white px-4 py-2 rounded-lg hover:bg-primary-700 transition-colors duration-200">
							View Elections </a>
					</div>
				</div>
			</div>

			<div class="grid grid-cols-1 md:grid-cols-3 gap-6 mb-6">
				<!-- Verification Status -->
				<div
					class="bg-white rounded-lg shadow-sm p-5 animate-slide-up hover-scale">
					<div class="flex items-center">
						<div class="rounded-full bg-green-100 p-3">
							<svg xmlns="http://www.w3.org/2000/svg"
								class="h-8 w-8 text-green-600" fill="none" viewBox="0 0 24 24"
								stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round"
									stroke-width="2"
									d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z" />
                </svg>
						</div>
						<div class="ml-4">
							<h2 class="text-lg font-semibold text-gray-800">
								Verification Status</h2>
							<p class="text-green-600 font-medium">Verified ✓</p>
						</div>
					</div>
				</div>

				<!-- Voting Stats -->
				<div
					class="bg-white rounded-lg shadow-sm p-5 animate-slide-up hover-scale"
					style="animation-delay: 0.1s">
					<div class="flex items-center">
						<div class="rounded-full bg-blue-100 p-3">
							<svg xmlns="http://www.w3.org/2000/svg"
								class="h-8 w-8 text-blue-600" fill="none" viewBox="0 0 24 24"
								stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round"
									stroke-width="2"
									d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z" />
                </svg>
						</div>
						<div class="ml-4">
							<h2 class="text-lg font-semibold text-gray-800">Total Votes
								Cast</h2>
							<p class="text-gray-700 font-medium">3 Elections</p>
						</div>
					</div>
				</div>

				<!-- Active Token -->
				<div
					class="bg-white rounded-lg shadow-sm p-5 animate-slide-up hover-scale"
					style="animation-delay: 0.2s">
					<div class="flex items-center">
						<div class="rounded-full bg-yellow-100 p-3">
							<svg xmlns="http://www.w3.org/2000/svg"
								class="h-8 w-8 text-yellow-600" fill="none" viewBox="0 0 24 24"
								stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round"
									stroke-width="2"
									d="M15 5v2m0 4v2m0 4v2M5 5a2 2 0 00-2 2v3a2 2 0 110 4v3a2 2 0 002 2h14a2 2 0 002-2v-3a2 2 0 110-4V7a2 2 0 00-2-2H5z" />
                </svg>
						</div>
						<div class="ml-4">
							<h2 class="text-lg font-semibold text-gray-800">Active
								Tokens</h2>
							<p class="text-gray-700 font-medium">2 Available</p>
						</div>
					</div>
				</div>
			</div>

			<!-- Upcoming Elections Section -->
			<div class="bg-white rounded-lg shadow-sm p-6 mb-6 animate-fade-in"
				style="animation-delay: 0.3s">
				<div class="flex justify-between items-center mb-6">
					<h2 class="text-xl font-bold text-gray-800">Upcoming Elections</h2>
					<a href="elections.html"
						class="text-primary-600 hover:text-primary-700 font-medium">View
						All</a>
				</div>

				<!-- Elections Grid -->
				<div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
					<!-- Election 1 -->
					<div
						class="border border-gray-200 rounded-lg p-4 hover:border-primary-300 transition-colors duration-200 hover-scale">
						<div class="flex items-center mb-3">
							<img src="https://placehold.co/100x100" alt="Election"
								class="w-12 h-12 rounded-lg object-cover" />
							<div class="ml-3">
								<h3 class="font-semibold text-gray-800">Presidential
									Election 2024</h3>
								<span
									class="text-xs px-2 py-1 rounded-full bg-yellow-100 text-yellow-800">
									Upcoming </span>
							</div>
						</div>
						<div class="text-gray-600 text-sm">
							<p class="mb-1">
								<span class="font-medium">Date:</span> Nov 5, 2024
							</p>
							<p class="mb-1">
								<span class="font-medium">Time:</span> 06:00 AM - 08:00 PM
							</p>
						</div>
						<div class="mt-3">
							<a href="vote.html"
								class="bg-primary-600 text-white text-sm px-3 py-1.5 rounded hover:bg-primary-700 transition-colors duration-200 inline-block">
								View Details </a>
						</div>
					</div>

					<!-- Election 2 -->
					<div
						class="border border-gray-200 rounded-lg p-4 hover:border-primary-300 transition-colors duration-200 hover-scale">
						<div class="flex items-center mb-3">
							<img src="https://placehold.co/100x100" alt="Election"
								class="w-12 h-12 rounded-lg object-cover" />
							<div class="ml-3">
								<h3 class="font-semibold text-gray-800">Municipal Elections
								</h3>
								<span
									class="text-xs px-2 py-1 rounded-full bg-green-100 text-green-800">
									Active </span>
							</div>
						</div>
						<div class="text-gray-600 text-sm">
							<p class="mb-1">
								<span class="font-medium">Date:</span> Oct 15, 2024
							</p>
							<p class="mb-1">
								<span class="font-medium">Time:</span> 07:00 AM - 07:00 PM
							</p>
						</div>
						<div class="mt-3">
							<a href="cast-vote.html"
								class="bg-green-600 text-white text-sm px-3 py-1.5 rounded hover:bg-green-700 transition-colors duration-200 inline-block">
								Cast Vote </a>
						</div>
					</div>

					<!-- Election 3 -->
					<div
						class="border border-gray-200 rounded-lg p-4 hover:border-primary-300 transition-colors duration-200 hover-scale">
						<div class="flex items-center mb-3">
							<img src="https://placehold.co/100x100" alt="Election"
								class="w-12 h-12 rounded-lg object-cover" />
							<div class="ml-3">
								<h3 class="font-semibold text-gray-800">State Assembly
									Election</h3>
								<span
									class="text-xs px-2 py-1 rounded-full bg-purple-100 text-purple-800">
									Coming Soon </span>
							</div>
						</div>
						<div class="text-gray-600 text-sm">
							<p class="mb-1">
								<span class="font-medium">Date:</span> Dec 10, 2024
							</p>
							<p class="mb-1">
								<span class="font-medium">Time:</span> 08:00 AM - 06:00 PM
							</p>
						</div>
						<div class="mt-3">
							<a href="vote.html"
								class="bg-primary-600 text-white text-sm px-3 py-1.5 rounded hover:bg-primary-700 transition-colors duration-200 inline-block">
								View Details </a>
						</div>
					</div>
				</div>
			</div>

			<!-- Recent Activity & Verification Status -->
			<div class="grid grid-cols-1 lg:grid-cols-3 gap-6">
				<!-- Recent Activity -->
				<div
					class="lg:col-span-2 bg-white rounded-lg shadow-sm p-6 animate-fade-in"
					style="animation-delay: 0.4s">
					<h2 class="text-xl font-bold text-gray-800 mb-4">Recent
						Activity</h2>

					<div class="space-y-4">
						<!-- Activity item 1 -->
						<div
							class="flex items-start p-3 border-l-4 border-green-500 bg-green-50 rounded-r-lg">
							<div
								class="flex-shrink-0 h-8 w-8 rounded-full bg-green-100 flex items-center justify-center">
								<svg xmlns="http://www.w3.org/2000/svg"
									class="h-4 w-4 text-green-600" fill="none" viewBox="0 0 24 24"
									stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round"
										stroke-width="2" d="M5 13l4 4L19 7" />
                  </svg>
							</div>
							<div class="ml-3">
								<p class="text-sm font-medium text-gray-900">Vote Cast
									Successfully</p>
								<p class="text-xs text-gray-500">Local Council Election -
									Sep 5, 2024</p>
							</div>
							<span class="ml-auto text-xs text-gray-400">2 days ago</span>
						</div>

						<!-- Activity item 2 -->
						<div
							class="flex items-start p-3 border-l-4 border-blue-500 bg-blue-50 rounded-r-lg">
							<div
								class="flex-shrink-0 h-8 w-8 rounded-full bg-blue-100 flex items-center justify-center">
								<svg xmlns="http://www.w3.org/2000/svg"
									class="h-4 w-4 text-blue-600" fill="none" viewBox="0 0 24 24"
									stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round"
										stroke-width="2"
										d="M15 5v2m0 4v2m0 4v2M5 5a2 2 0 00-2 2v3a2 2 0 110 4v3a2 2 0 002 2h14a2 2 0 002-2v-3a2 2 0 110-4V7a2 2 0 00-2-2H5z" />
                  </svg>
							</div>
							<div class="ml-3">
								<p class="text-sm font-medium text-gray-900">New Token
									Issued</p>
								<p class="text-xs text-gray-500">For Municipal Elections</p>
							</div>
							<span class="ml-auto text-xs text-gray-400">5 days ago</span>
						</div>

						<!-- Activity item 3 -->
						<div
							class="flex items-start p-3 border-l-4 border-yellow-500 bg-yellow-50 rounded-r-lg">
							<div
								class="flex-shrink-0 h-8 w-8 rounded-full bg-yellow-100 flex items-center justify-center">
								<svg xmlns="http://www.w3.org/2000/svg"
									class="h-4 w-4 text-yellow-600" fill="none" viewBox="0 0 24 24"
									stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round"
										stroke-width="2"
										d="M12 8v4l3 3m6-3a9 9 0 11-18 0 9 9 0 0118 0z" />
                  </svg>
							</div>
							<div class="ml-3">
								<p class="text-sm font-medium text-gray-900">Reminder</p>
								<p class="text-xs text-gray-500">School Board Election
									registration ends soon</p>
							</div>
							<span class="ml-auto text-xs text-gray-400">1 week ago</span>
						</div>
					</div>
				</div>

				<!-- Verification Progress -->
				<div class="bg-white rounded-lg shadow-sm p-6 animate-fade-in"
					style="animation-delay: 0.5s">
					<h2 class="text-xl font-bold text-gray-800 mb-4">Verification
						Status</h2>

					<div class="flex flex-col items-center">
						<div class="relative mb-4">
							<svg class="w-32 h-32" viewBox="0 0 36 36">
                  <path class="stroke-current text-gray-200" fill="none"
									stroke-width="3.8"
									d="M18 2.0845
                    a 15.9155 15.9155 0 0 1 0 31.831
                    a 15.9155 15.9155 0 0 1 0 -31.831" />
                  <path
									class="stroke-current text-green-500 progress-ring-circle"
									fill="none" stroke-width="3.8" stroke-dasharray="100, 100"
									d="M18 2.0845
                    a 15.9155 15.9155 0 0 1 0 31.831
                    a 15.9155 15.9155 0 0 1 0 -31.831" />
                  <text x="18" y="20.35"
									class="text-3xl font-bold text-gray-800" text-anchor="middle">
                    100%
                  </text>
                </svg>
							<div class="absolute inset-0 flex items-center justify-center">
								<svg xmlns="http://www.w3.org/2000/svg"
									class="h-10 w-10 text-green-500" fill="none"
									viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round"
										stroke-width="2" d="M5 13l4 4L19 7" />
                  </svg>
							</div>
						</div>

						<p class="text-center text-gray-600 mb-4">Your voter account
							is fully verified. You can participate in all available
							elections.</p>

						<div class="w-full">
							<div class="flex items-center mb-2">
								<svg xmlns="http://www.w3.org/2000/svg"
									class="h-5 w-5 text-green-500 mr-2" fill="none"
									viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round"
										stroke-width="2" d="M5 13l4 4L19 7" />
                  </svg>
								<span class="text-sm text-gray-700">Identity Verified</span>
							</div>
							<div class="flex items-center mb-2">
								<svg xmlns="http://www.w3.org/2000/svg"
									class="h-5 w-5 text-green-500 mr-2" fill="none"
									viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round"
										stroke-width="2" d="M5 13l4 4L19 7" />
                  </svg>
								<span class="text-sm text-gray-700">Email Confirmed</span>
							</div>
							<div class="flex items-center">
								<svg xmlns="http://www.w3.org/2000/svg"
									class="h-5 w-5 text-green-500 mr-2" fill="none"
									viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round"
										stroke-width="2" d="M5 13l4 4L19 7" />
                  </svg>
								<span class="text-sm text-gray-700">Voter Card Verified</span>
							</div>
						</div>
					</div>
				</div>
			</div>
		</main>
	</div>

	<script>
      // Load sidebar
      fetch("sidebar.html")
        .then((response) => response.text())
        .then((data) => {
          document.getElementById("sidebar-container").innerHTML = data;
        });

      // Load navbar
      fetch("navbar.html")
        .then((response) => response.text())
        .then((data) => {
          document.getElementById("navbar-container").innerHTML = data;
        });

      document.addEventListener("DOMContentLoaded", function () {
        // Example: Update progress ring display
        const progressRing = document.querySelector(".progress-ring-circle");
        const percent = 100; // Verification complete
        const dashArray = 100;
        const dashOffset = dashArray - (dashArray * percent) / 100;
        progressRing.style.strokeDasharray = dashArray;
        progressRing.style.strokeDashoffset = dashOffset;
      });
    </script>
</body>
</html>
