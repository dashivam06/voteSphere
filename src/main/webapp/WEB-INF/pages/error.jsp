<%@ page contentType="text/html;charset=UTF-8" language="java"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Something Went Wrong | VoteSphere</title>
<script src="https://cdn.tailwindcss.com"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
<script>
	tailwind.config = {
		theme : {
			extend : {
				colors : {
					primary : {
						50 : "#f0f9ff",
						100 : "#e0f2fe",
						200 : "#bae6fd",
						300 : "#7dd3fc",
						400 : "#38bdf8",
						500 : "#0ea5e9",
						600 : "#0284c7",
						700 : "#0369a1",
						800 : "#075985",
						900 : "#0c4a6e",
					},
				}
			}
		}
	}
</script>
</head>
<body
	class="bg-gradient-to-br from-primary-600 to-primary-700 min-h-screen flex items-center justify-center p-4">
	<div
		class="bg-white rounded-xl shadow-2xl max-w-md w-full overflow-hidden">
		<!-- Error Icon -->
		<div class="flex justify-center pt-10 pb-6">
			<div class="bg-red-100 rounded-full p-3">
				<svg class="w-12 h-12 text-red-500" fill="none"
					stroke="currentColor" viewBox="0 0 24 24"
					xmlns="http://www.w3.org/2000/svg">
                    <path stroke-linecap="round" stroke-linejoin="round"
						stroke-width="2"
						d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"></path>
                </svg>
			</div>
		</div>

		<!-- Content -->
		<div class="px-6 pb-8 text-center">
			<h1 class="text-2xl font-bold text-gray-800 mb-1">Something Went
				Wrong</h1>
			<p class="text-primary-500 font-medium mb-4">We encountered an
				error</p>

			<div class="bg-red-50 rounded-lg p-4 mb-6 text-gray-700 text-sm">
				<p class="mb-2">
					<%= request.getAttribute("error") %>
				</p>
				<p class="font-medium">
					Error code: <span class="text-red-600">ERR_SERVER_500</span>
				</p>
			</div>

			<div class="grid grid-cols-2 gap-3 mb-4">
				<a href="#"
					class="inline-flex items-center justify-center px-4 py-2.5 bg-primary-50 text-primary-700 border border-primary-200 rounded-lg font-medium hover:bg-primary-100 transition-colors">
					<i class="fas fa-redo-alt mr-2"></i> Try Again
				</a> <a href="#"
					class="inline-flex items-center justify-center px-4 py-2.5 bg-primary-500 text-white rounded-lg font-medium hover:bg-primary-600 transition-colors">
					<i class="fas fa-home mr-2"></i> Home
				</a>
			</div>

			<p class="text-sm text-gray-500">If the problem persists, please
				contact our support team.</p>
		</div>

		<!-- Footer -->
		<div
			class="border-t border-gray-200 px-6 py-4 flex justify-between items-center bg-gray-50">
			<span class="text-sm text-gray-500">Need help? <a href="#"
				class="text-primary-600 hover:underline">Contact support</a></span>
			<div class="flex space-x-3">
				<a href="#"
					class="text-gray-400 hover:text-primary-500 transition-colors">
					<i class="fab fa-twitter"></i>
				</a> <a href="#"
					class="text-gray-400 hover:text-primary-500 transition-colors">
					<i class="fab fa-instagram"></i>
				</a> <a href="#"
					class="text-gray-400 hover:text-primary-500 transition-colors">
					<i class="fab fa-linkedin"></i>
				</a>
			</div>
		</div>
	</div>
</body>
</html>