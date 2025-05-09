<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>VoteSphere - Login</title>
<script src="https://cdn.tailwindcss.com"></script>
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
	rel="stylesheet" />
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
				},
				fontFamily : {
					poppins : [ "Poppins", "sans-serif" ],
				},
				animation : {
					"fade-in" : "fadeIn 1s ease-in-out",
					"slide-up" : "slideUp 0.8s ease-out",
					"slide-right" : "slideRight 0.8s ease-out",
					"slide-left" : "slideLeft 0.8s ease-out",
					"bounce-slow" : "bounce 3s infinite",
					"pulse-slow" : "pulse 3s infinite",
					"spin-slow" : "spin 8s linear infinite",
				},
				keyframes : {
					fadeIn : {
						"0%" : {
							opacity : "0"
						},
						"100%" : {
							opacity : "1"
						},
					},
					slideUp : {
						"0%" : {
							transform : "translateY(50px)",
							opacity : "0"
						},
						"100%" : {
							transform : "translateY(0)",
							opacity : "1"
						},
					},
					slideRight : {
						"0%" : {
							transform : "translateX(-50px)",
							opacity : "0"
						},
						"100%" : {
							transform : "translateX(0)",
							opacity : "1"
						},
					},
					slideLeft : {
						"0%" : {
							transform : "translateX(50px)",
							opacity : "0"
						},
						"100%" : {
							transform : "translateX(0)",
							opacity : "1"
						},
					},
				},
			},
		},
	};
</script>
</head>
<body
	class="font-sans text-gray-800 bg-gray-50 min-h-screen flex flex-col">
	<!-- Header/Navigation -->
	<header class="bg-white shadow-sm sticky top-0 z-10 animate-fade-in">
		<div class="container mx-auto px-4 sm:px-6 lg:px-8">
			<div class="flex justify-between items-center h-16">
				<div class="flex items-center">
					<a href="" class="flex items-center group"> <svg
							xmlns="http://www.w3.org/2000/svg"
							class="h-8 w-8 text-primary-600 transition-transform duration-300 group-hover:rotate-12"
							fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round"
								stroke-width="2"
								d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z" />
              </svg> <span
						class="ml-2 text-xl font-bold text-primary-600 transition-all duration-300 group-hover:text-primary-700">VoteSphere</span>
					</a>
				</div>
				<nav class="hidden md:flex space-x-8">
					<a href="/voteSphere#features"
						class="text-gray-600 hover:text-primary-600 px-3 py-2 text-sm font-medium transition-colors duration-300 hover:scale-105 transform">Features</a>
					<a href="/voteSphere#how-it-works"
						class="text-gray-600 hover:text-primary-600 px-3 py-2 text-sm font-medium transition-colors duration-300 hover:scale-105 transform">How
						It Works</a> <a href="/voteSphere#faqs"
						class="text-gray-600 hover:text-primary-600 px-3 py-2 text-sm font-medium transition-colors duration-300 hover:scale-105 transform">FAQs</a>
					<a href="/voteSphere#contact"
						class="text-gray-600 hover:text-primary-600 px-3 py-2 text-sm font-medium transition-colors duration-300 hover:scale-105 transform">Contact</a>
				</nav>
				<div class="flex items-center">
					<a href="login"
						class="hidden md:inline-flex items-center justify-center px-4 py-2 border border-transparent text-sm font-medium rounded-md text-white bg-primary-600 hover:bg-primary-700 transition-all duration-300 hover:shadow-lg transform hover:-translate-y-1">
						Log in </a> <a href="register"
						class="ml-4 inline-flex items-center justify-center px-4 py-2 border border-transparent text-sm font-medium rounded-md text-primary-600 bg-white hover:bg-gray-50 border-primary-600 transition-all duration-300 hover:shadow-lg transform hover:-translate-y-1">
						Register </a>
					<button type="button"
						class="md:hidden ml-4 bg-white p-2 rounded-md text-gray-400 hover:text-gray-500 hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-inset focus:ring-primary-500 transition-colors duration-300">
						<svg class="h-6 w-6" xmlns="http://www.w3.org/2000/svg"
							fill="none" viewBox="0 0 24 24" stroke="currentColor"
							aria-hidden="true">
                <path stroke-linecap="round" stroke-linejoin="round"
								stroke-width="2" d="M4 6h16M4 12h16M4 18h16" />
              </svg>
					</button>
				</div>
			</div>
		</div>
	</header>

	<!-- Main Content -->
	<main
		class="flex-grow flex items-center justify-center py-12 px-4 sm:px-6 lg:px-8">
		<div class="max-w-md w-full space-y-8 animate-slide-up">
			<div class="text-center">
				<h2 class="mt-6 text-3xl font-extrabold text-gray-800">Sign in
					to your account</h2>
				<p class="mt-2 text-sm text-gray-600">
					Or <a href="register.html"
						class="font-medium text-primary-700 hover:text-primary-800">
						register for a new account </a>
				</p>
			</div>

			<div class="mt-8 bg-white py-8 px-6 shadow-lg rounded-lg">
				<form id="loginForm" class="space-y-6" action="login" method="post">
					<div>
						<label for="userId"
							class="block text-sm font-medium text-gray-700"> User ID
						</label>
						<div class="mt-1 relative rounded-md shadow-sm">
							<div
								class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
								<svg xmlns="http://www.w3.org/2000/svg"
									class="h-5 w-5 text-gray-400" fill="none" viewBox="0 0 24 24"
									stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round"
										stroke-width="2"
										d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z" />
                  </svg>
							</div>
							<input type="text" id="userId" name="voter_id" required
								autocomplete="username" placeholder="Enter your user ID"
								class="block w-full pl-10 pr-3 py-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary-400 focus:border-primary-400 text-sm" />
						</div>
						<p class="mt-1 text-sm text-red-600 hidden" id="userIdError"></p>
					</div>

					<div>
						<label for="password"
							class="block text-sm font-medium text-gray-700"> Password
						</label>
						<div class="mt-1 relative rounded-md shadow-sm">
							<div
								class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
								<svg xmlns="http://www.w3.org/2000/svg"
									class="h-5 w-5 text-gray-400" fill="none" viewBox="0 0 24 24"
									stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round"
										stroke-width="2"
										d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z" />
                  </svg>
							</div>
							<input type="password" id="password" name="password" required
								autocomplete="current-password"
								placeholder="Enter your password"
								class="block w-full pl-10 pr-3 py-3 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary-400 focus:border-primary-400 text-sm" />
						</div>
						<p class="mt-1 text-sm text-red-600 hidden" id="passwordError"></p>
					</div>

					<div class="flex items-center justify-between">
						<div class="flex items-center">
							<input id="remember-me" name="remember_me" type="checkbox"
								class="h-4 w-4 text-primary-400 focus:ring-primary-400 border-gray-300 rounded" />
							<label for="remember-me" class="ml-2 block text-sm text-gray-700">
								Remember me </label>
						</div>

						<div class="text-sm">
							<a href="#"
								class="font-medium text-primary-700 hover:text-primary-800">
								Forgot your password? </a>
						</div>
					</div>

					<div>
						<button type="submit"
							class="group relative w-full flex justify-center py-3 px-4 border border-transparent text-sm font-medium rounded-md text-white bg-primary-600 hover:bg-primary-800 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary-400 transition-colors duration-300">
							<span class="absolute left-0 inset-y-0 flex items-center pl-3">
								<svg xmlns="http://www.w3.org/2000/svg"
									class="h-5 w-5 text-primary-300 group-hover:text-primary-200"
									fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round"
										stroke-width="2"
										d="M11 16l-4-4m0 0l4-4m-4 4h14m-5 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h7a3 3 0 013 3v1" />
                  </svg>
							</span> <a href="<%=request.getContextPath() + "/login"%>">Sign in</a>
						</button>
					</div>
				</form>
			</div>
		</div>
	</main>

	<!-- Footer -->
	<footer class="bg-white border-t border-gray-200 py-8">
		<div class="container mx-auto px-4 sm:px-6 lg:px-8">
			<div class="text-center text-gray-500 text-sm">
				<p>&copy; 2023 VoteSphere. All rights reserved.</p>
				<p class="mt-2">Secure online voting system</p>
			</div>
		</div>
	</footer>

	<!-- <script>
      function validateForm() {
        let isValid = true;
        const userId = document.getElementById("userId").value;
        const password = document.getElementById("password").value;

        // Reset error messages
        document.getElementById("userIdError").classList.add("hidden");
        document.getElementById("passwordError").classList.add("hidden");

        // Validate user ID
        if (userId.trim() === "") {
          document.getElementById("userIdError").textContent =
            "User ID is required";
          document.getElementById("userIdError").classList.remove("hidden");
          isValid = false;
        }

        // Validate password
        if (password.trim() === "") {
          document.getElementById("passwordError").textContent =
            "Password is required";
          document.getElementById("passwordError").classList.remove("hidden");
          isValid = false;
        }

        if (isValid) {
          // Form is valid - you would normally submit the form here
          // or handle authentication via AJAX
          console.log("Form submitted:", { userId, password });

          // For demonstration purposes - replace with actual authentication
          alert("Login data submitted");
        }

        return false; // Prevent actual form submission for this example
      }
    </script> -->
</body>
</html>
