<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>VoteSphere - Candidate Management</title>
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
          },
        },
      };
    </script>
<style>
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
</style>
</head>
<body class="font-sans bg-gray-100 flex h-screen overflow-hidden">
	<!-- Include sidebar -->
        <%@ include file="sidebar.jsp" %>

	<!-- Main Content -->
	<div
		class="flex-1 flex flex-col ml-0 lg:ml-64 transition-all duration-300 ease-in-out">
		<!-- Include navbar -->
        		<%@ include file="navbar.jsp" %>


		<!-- Content Area -->
		<div class="p-8">
			<div class="bg-white rounded-lg shadow-md p-6">
				<div class="flex justify-between items-center mb-6">
					<h1 class="text-2xl font-bold text-gray-800">Candidate
						Management</h1>
					<div class="flex space-x-4">
						<div class="relative">

						<form id="searchForm" action="${pageContext.request.contextPath}/admin/candidate/search/" method="post" >
                        							<input type="text" name="searchInput" id="searchInput" value="${searchInput}" placeholder="Search candidates..."
                        								class="pl-10 pr-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500" />
                        							<svg class="absolute left-3 top-2.5 h-5 w-5 text-gray-400"
                        								xmlns="http://www.w3.org/2000/svg" fill="none"
                        								viewBox="0 0 24 24" stroke="currentColor">

                        								</form>



                  <path stroke-linecap="round" stroke-linejoin="round"
									stroke-width="2"
									d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
                </svg>
						</div>
						<button
							class="bg-primary-600 text-white px-4 py-2 rounded-lg hover:bg-primary-700 transition-colors duration-200">
							Add Candidate</button>
					</div>
				</div>

				<!-- Candidates Table -->
				<div class="overflow-x-auto">
					<table class="min-w-full divide-y divide-gray-200">
						<thead class="bg-gray-50">
							<tr>
								<th
									class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
									Candidate</th>
								<th
									class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
									Party</th>
								<th
									class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
									Election</th>
								<th
									class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
									Status</th>

								<th
									class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">
									Actions</th>
							</tr>
						</thead>
						<tbody class="bg-white divide-y divide-gray-200">
							<!-- Sample candidate row -->
							<tr>
								<td class="px-6 py-4 whitespace-nowrap">
									<div class="flex items-center">
										<div class="h-10 w-10 flex-shrink-0">
											<img class="h-10 w-10 rounded-full object-cover"
												src="https://placehold.co/100x100" alt="" />
										</div>
										<div class="ml-4">
											<div class="text-sm font-medium text-gray-900">Jane
												Smith</div>
											<div class="text-sm text-gray-500">ID: CAND123</div>
										</div>
									</div>
								</td>
								<td class="px-6 py-4 whitespace-nowrap">
									<div class="text-sm text-gray-900">Democratic Party</div>
								</td>
								<td class="px-6 py-4 whitespace-nowrap">
									<div class="text-sm text-gray-900">Presidential Election
										2024</div>
								</td>
								<td class="px-6 py-4 whitespace-nowrap"><span
									class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800">
										Approved </span></td>

								<td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
									<div class="flex space-x-3">
										<button class="text-primary-600 hover:text-primary-900">
											View</button>
										<button class="text-yellow-600 hover:text-yellow-900">
											Edit</button>
										<button class="text-red-600 hover:text-red-900">
											Delete</button>
									</div>
								</td>
							</tr>
							<!-- Add more candidate rows as needed -->
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<script>
        document.addEventListener('DOMContentLoaded', function() {
            const searchInput = document.getElementById('searchInput');
            const searchForm = document.getElementById('searchForm');
            let searchTimeout;
            let isEmptyState = false;
            let emptyTimer;

            // Restore focus if there's a search query
            if (searchInput.value) {
                searchInput.focus();
                searchInput.selectionStart = searchInput.selectionEnd = searchInput.value.length;
            }

            searchInput.addEventListener('input', function() {
                clearTimeout(searchTimeout);
                clearTimeout(emptyTimer);

                if (this.value.trim() === '') {
                    // Field just became empty
                    if (!isEmptyState) {
                        isEmptyState = true;
                        // Start 5 second countdown before refresh
                        emptyTimer = setTimeout(() => {
                            searchForm.submit();
                        }, 5000); // 5 second delay for empty field
                    }
                } else {
                    // Field has content
                    isEmptyState = false;
                    // Normal search delay
                    searchTimeout = setTimeout(() => {
                        searchForm.submit();
                    }, 500); // 0.5 second delay for typing
                }
            });

            // Maintain focus when field is empty
            searchInput.addEventListener('blur', function() {
                if (this.value.trim() === '' && isEmptyState) {
                    // Only maintain focus if we're in the empty waiting period
                    setTimeout(() => {
                        this.focus();
                    }, 10); // Small delay to ensure blur completes
                }
            });
        });
        </script>

</body>
</html>
