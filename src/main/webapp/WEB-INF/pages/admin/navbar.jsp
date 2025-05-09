<!-- Navbar Component -->
<div class="bg-white shadow-sm border-b border-gray-200">
	<div class="px-6 py-4 flex justify-between items-center">
		<!-- Left side - toggle and title -->
		<div class="flex items-center">
			<!-- Mobile menu toggle -->
			<button id="sidebar-toggle"
				class="block lg:hidden p-2 mr-4 text-gray-600 hover:text-gray-900 focus:outline-none">
				<svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none"
					viewBox="0 0 24 24" stroke="currentColor">
          <path stroke-linecap="round" stroke-linejoin="round"
						stroke-width="2" d="M4 6h16M4 12h16M4 18h16" />
        </svg>
			</button>

			<!-- Page title - dynamic based on current page -->
			<h2 class="text-xl font-semibold text-gray-800" id="page-title">
				Dashboard</h2>
		</div>

		<!-- Right side - user profile and notifications -->
		<div class="flex items-center space-x-4">
			<!-- Notifications -->
			<div class="relative">
				<button
					class="p-2 text-gray-600 hover:text-gray-900 focus:outline-none">
					<svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none"
						viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round"
							stroke-width="2"
							d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9" />
          </svg>
					<span
						class="absolute top-0 right-0 block h-2 w-2 rounded-full bg-red-500"></span>
				</button>
			</div>

			<!-- Help / Documentation -->
			<div>
				<button
					class="p-2 text-gray-600 hover:text-gray-900 focus:outline-none">
					<svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none"
						viewBox="0 0 24 24" stroke="currentColor">
            <path stroke-linecap="round" stroke-linejoin="round"
							stroke-width="2"
							d="M8.228 9c.549-1.165 2.03-2 3.772-2 2.21 0 4 1.343 4 3 0 1.4-1.278 2.575-3.006 2.907-.542.104-.994.54-.994 1.093m0 3h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z" />
          </svg>
				</button>
			</div>

			<!-- User Profile -->
			<div class="relative">
				<button class="flex items-center space-x-2 focus:outline-none"
					id="user-menu-button">
					<div
						class="w-8 h-8 rounded-full bg-primary-500 flex items-center justify-center text-white text-sm font-medium">
						AD</div>
					<div class="hidden md:block text-left">
						<p class="text-sm font-medium text-gray-800">Admin User</p>
						<p class="text-xs text-gray-500">admin@votesphere.com</p>
					</div>
					<svg xmlns="http://www.w3.org/2000/svg"
						class="h-5 w-5 text-gray-400" viewBox="0 0 20 20"
						fill="currentColor">
            <path fill-rule="evenodd"
							d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z"
							clip-rule="evenodd" />
          </svg>
				</button>

				<!-- Dropdown menu, toggle with JS -->
				<div
					class="hidden absolute right-0 w-48 mt-2 bg-white rounded-md shadow-lg py-1 z-10"
					id="user-menu-dropdown">
					<a href="#"
						class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">Your
						Profile</a> <a href="#"
						class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">Settings</a>
					<a href="#"
						class="block px-4 py-2 text-sm text-gray-700 hover:bg-gray-100">Sign
						out</a>
				</div>
			</div>
		</div>
	</div>
</div>

<script>
  // Update page title based on current URL
  document.addEventListener("DOMContentLoaded", function () {
    const path = window.location.pathname;
    const filename = path.split("/").pop();
    let title = "Dashboard";

    // Map filenames to titles
    const titleMap = {
      "dashboard.html": "Dashboard",
      "voters.html": "Voter Management",
      "elections.html": "Election Management",
      "parties.html": "Party Management",
      "candidates.html": "Candidate Management",
      "votes.html": "Vote Management",
      "news.html": "News Management",
      "faqs.html": "FAQ Management",
      "donations.html": "Donation Management",
      "tokens.html": "Token Management",
    };

    // Set the title if we have a mapping
    if (filename && titleMap[filename]) {
      title = titleMap[filename];
    }

    document.getElementById("page-title").innerText = title;
  });

  // Toggle sidebar on mobile
  document
    .getElementById("sidebar-toggle")
    ?.addEventListener("click", function () {
      const sidebar = document.querySelector("#sidebar");
      const mainContent = document.querySelector(".ml-0.lg\\:ml-64");

      if (sidebar) {
        sidebar.classList.toggle("-translate-x-full");
      }

      if (mainContent) {
        mainContent.classList.toggle("ml-64");
        mainContent.classList.toggle("ml-0");
      }
    });

  // Toggle user dropdown menu
  document
    .getElementById("user-menu-button")
    ?.addEventListener("click", function () {
      const dropdown = document.getElementById("user-menu-dropdown");
      dropdown.classList.toggle("hidden");
    });

  // Close dropdown when clicking elsewhere
  window.addEventListener("click", function (event) {
    const dropdown = document.getElementById("user-menu-dropdown");
    const button = document.getElementById("user-menu-button");

    if (
      dropdown &&
      !dropdown.classList.contains("hidden") &&
      button &&
      !button.contains(event.target) &&
      !dropdown.contains(event.target)
    ) {
      dropdown.classList.add("hidden");
    }
  });
</script>
