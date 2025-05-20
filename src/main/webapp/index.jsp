<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>VoteSphere - Online Voting System</title>
<script src="https://cdn.tailwindcss.com"></script>
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:ital,wght@0,100;0,200;0,300;0,400;0,500;0,600;0,700;0,800;0,900;1,100;1,200;1,300;1,400;1,500;1,600;1,700;1,800;1,900&display=swap"
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
              poppins: ["Poppins", "sans-serif"],
            },
            animation: {
              "fade-in": "fadeIn 1s ease-in-out",
              "slide-up": "slideUp 0.8s ease-out",
              "slide-right": "slideRight 0.8s ease-out",
              "slide-left": "slideLeft 0.8s ease-out",
              "bounce-slow": "bounce 3s infinite",
              "pulse-slow": "pulse 3s infinite",
              "spin-slow": "spin 8s linear infinite",
            },
            keyframes: {
              fadeIn: {
                "0%": { opacity: "0" },
                "100%": { opacity: "1" },
              },
              slideUp: {
                "0%": { transform: "translateY(50px)", opacity: "0" },
                "100%": { transform: "translateY(0)", opacity: "1" },
              },
              slideRight: {
                "0%": { transform: "translateX(-50px)", opacity: "0" },
                "100%": { transform: "translateX(0)", opacity: "1" },
              },
              slideLeft: {
                "0%": { transform: "translateX(50px)", opacity: "0" },
                "100%": { transform: "translateX(0)", opacity: "1" },
              },
            },
          },
        },
      };
    </script>
<style>
.animate-on-scroll {
	opacity: 0;
	transform: translateY(30px);
	transition: opacity 0.8s ease-out, transform 0.8s ease-out;
}

.animate-on-scroll.animate {
	opacity: 1;
	transform: translateY(0);
}

.animate-on-scroll-right {
	opacity: 0;
	transform: translateX(-30px);
	transition: opacity 0.8s ease-out, transform 0.8s ease-out;
}

.animate-on-scroll-right.animate {
	opacity: 1;
	transform: translateX(0);
}

.animate-on-scroll-left {
	opacity: 0;
	transform: translateX(30px);
	transition: opacity 0.8s ease-out, transform 0.8s ease-out;
}

.animate-on-scroll-left.animate {
	opacity: 1;
	transform: translateX(0);
}

.stagger-item {
	transition-delay: calc(var(--i)* 0.1s);
}

@
keyframes float { 0% {
	transform: translateY(0px);
}

50




%
{
transform




:




translateY


(




-10px




)


;
}
100




%
{
transform




:




translateY


(




0px




)


;
}
}
.animate-float {
	animation: float 3s ease-in-out infinite;
}

.count-up {
	counter-reset: count 0;
	animation: count-up 2s forwards;
}

@
keyframes count-up {to { counter-increment:countattr(data-target);
	content: counter(count);
}

}
.number-counter::after {
	content: "0";
	animation: countUp 2s forwards;
}

@
keyframes countUp {to { content:attr(data-value);

}

}
.bg-gradient-animated {
	background-size: 200% 200%;
	animation: gradientAnimation 5s ease infinite;
}

@
keyframes gradientAnimation { 0% {
	background-position: 0% 50%;
}
50




%
{
background-position




:




100


%
50


%;
}
100




%
{
background-position




:




0


%
50


%;
}
}
</style>
</head>
<body class="font-poppins text-gray-800 bg-gray-50">
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
					<a href="#features"
						class="text-gray-600 hover:text-primary-600 px-3 py-2 text-sm font-medium transition-colors duration-300 hover:scale-105 transform">Features</a>
					<a href="#how-it-works"
						class="text-gray-600 hover:text-primary-600 px-3 py-2 text-sm font-medium transition-colors duration-300 hover:scale-105 transform">How
						It Works</a> <a href="#faqs"
						class="text-gray-600 hover:text-primary-600 px-3 py-2 text-sm font-medium transition-colors duration-300 hover:scale-105 transform">FAQs</a>
					<a href="#contact"
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

	<!-- Hero Section -->
	<section
		class="relative bg-white overflow-hidden min-h-screen flex items-center justify-center">
		<div
			class="absolute inset-0 bg-gradient-to-r from-primary-50 to-white opacity-70 bg-gradient-animated"></div>
		<div class="container mx-auto px-4 sm:px-6 lg:px-8 py-8 relative">
			<div class="lg:grid lg:grid-cols-12 lg:gap-8">
				<div
					class="sm:text-center md:max-w-2xl md:mx-auto lg:col-span-6 lg:text-left">
					<h1
						class="text-4xl tracking-tight font-bold text-gray-900 sm:text-5xl md:text-6xl animate-slide-right">
						<span class="block">Secure Online</span> <span
							class="block text-primary-600">Voting System</span>
					</h1>
					<p
						class="mt-3 text-base text-gray-500 sm:mt-5 sm:text-lg sm:max-w-xl sm:mx-auto md:mt-5 md:text-xl lg:mx-0 animate-slide-right"
						style="animation-delay: 0.2s">A modern, secure, and
						transparent platform for conducting elections online. Accessible
						from anywhere, anytime.</p>
					<div
						class="mt-8 sm:max-w-lg sm:mx-auto sm:text-center lg:text-left lg:mx-0 animate-slide-right"
						style="animation-delay: 0.4s">
						<div
							class="flex flex-col sm:flex-row sm:justify-center lg:justify-start gap-4">
							<a href="voter/dashboard.html"
								class="inline-flex items-center justify-center px-5 py-3 border border-transparent text-base font-medium rounded-md text-white bg-primary-600 hover:bg-primary-700 transition-all duration-300 hover:shadow-lg transform hover:-translate-y-1 animate-pulse-slow">
								Get Started </a> <a href="#how-it-works"
								class="inline-flex items-center justify-center px-5 py-3 border border-transparent text-base font-medium rounded-md text-primary-600 bg-white hover:bg-gray-50 border-primary-600 transition-all duration-300 hover:shadow-lg transform hover:-translate-y-1">
								Learn More </a>
						</div>
					</div>
				</div>
				<div
					class="mt-12 relative sm:max-w-lg sm:mx-auto lg:mt-0 lg:max-w-none lg:mx-0 lg:col-span-6 lg:flex lg:items-center animate-slide-left">
					<div
						class="relative mx-auto w-full rounded-lg lg:max-w-sm animate-float">
						<div class="relative block w-full rounded-lg overflow-hidden">

								<img class="w-full" src="<c:url value='/uploads/vote.png' />" alt="Vote Image -I">

						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<!-- Features Section -->
	<section id="features" class="py-16 bg-gray-50">
		<div class="container mx-auto px-4 sm:px-6 lg:px-8">
			<div class="text-center animate-on-scroll">
				<h2 class="text-3xl font-extrabold text-gray-900 sm:text-4xl">
					Features of Our Voting System</h2>
				<p class="mt-4 max-w-2xl text-xl text-gray-500 mx-auto">
					Designed with security, accessibility, and transparency in mind.</p>
			</div>

			<div class="mt-16 grid gap-8 md:grid-cols-2 lg:grid-cols-3">
				<!-- Feature 1 -->
				<div
					class="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-all duration-500 transform hover:-translate-y-2 animate-on-scroll stagger-item"
					style="--i: 1">
					<div
						class="inline-flex items-center justify-center rounded-md bg-primary-100 p-3 text-primary-600 mb-4 transition-all duration-300 group-hover:bg-primary-200">
						<svg xmlns="http://www.w3.org/2000/svg"
							class="h-6 w-6 animate-bounce-slow" fill="none"
							viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round"
								stroke-width="2"
								d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z" />
              </svg>
					</div>
					<h3 class="text-xl font-bold text-gray-900">Secure Voting</h3>
					<p class="mt-2 text-gray-600">End-to-end encryption and
						blockchain technology ensure your vote is secure and tamper-proof.
					</p>
				</div>

				<!-- Feature 2 -->
				<div
					class="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-all duration-500 transform hover:-translate-y-2 animate-on-scroll stagger-item"
					style="--i: 2">
					<div
						class="inline-flex items-center justify-center rounded-md bg-primary-100 p-3 text-primary-600 mb-4 transition-all duration-300 group-hover:bg-primary-200">
						<svg xmlns="http://www.w3.org/2000/svg"
							class="h-6 w-6 animate-pulse-slow" fill="none"
							viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round"
								stroke-width="2"
								d="M3 15a4 4 0 004 4h9a5 5 0 10-.1-9.999 5.002 5.002 0 10-9.78 2.096A4.001 4.001 0 003 15z" />
              </svg>
					</div>
					<h3 class="text-xl font-bold text-gray-900">Vote Anywhere</h3>
					<p class="mt-2 text-gray-600">Cast your vote from anywhere in
						the world using any device with internet access.</p>
				</div>

				<!-- Feature 3 -->
				<div
					class="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-all duration-500 transform hover:-translate-y-2 animate-on-scroll stagger-item"
					style="--i: 3">
					<div
						class="inline-flex items-center justify-center rounded-md bg-primary-100 p-3 text-primary-600 mb-4 transition-all duration-300 group-hover:bg-primary-200">
						<svg xmlns="http://www.w3.org/2000/svg"
							class="h-6 w-6 animate-spin-slow" fill="none" viewBox="0 0 24 24"
							stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round"
								stroke-width="2"
								d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z" />
              </svg>
					</div>
					<h3 class="text-xl font-bold text-gray-900">Verified Results</h3>
					<p class="mt-2 text-gray-600">Real-time results with
						verification mechanisms to ensure transparency and trust.</p>
				</div>

				<!-- Feature 4 -->
				<div
					class="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-all duration-500 transform hover:-translate-y-2 animate-on-scroll stagger-item"
					style="--i: 4">
					<div
						class="inline-flex items-center justify-center rounded-md bg-primary-100 p-3 text-primary-600 mb-4 transition-all duration-300 group-hover:bg-primary-200">
						<svg xmlns="http://www.w3.org/2000/svg"
							class="h-6 w-6 animate-bounce-slow" fill="none"
							viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round"
								stroke-width="2"
								d="M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z" />
              </svg>
					</div>
					<h3 class="text-xl font-bold text-gray-900">Flexible
						Scheduling</h3>
					<p class="mt-2 text-gray-600">Set up elections with custom
						voting periods to accommodate all voters' schedules.</p>
				</div>

				<!-- Feature 5 -->
				<div
					class="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-all duration-500 transform hover:-translate-y-2 animate-on-scroll stagger-item"
					style="--i: 5">
					<div
						class="inline-flex items-center justify-center rounded-md bg-primary-100 p-3 text-primary-600 mb-4 transition-all duration-300 group-hover:bg-primary-200">
						<svg xmlns="http://www.w3.org/2000/svg"
							class="h-6 w-6 animate-pulse-slow" fill="none"
							viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round"
								stroke-width="2"
								d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z" />
              </svg>
					</div>
					<h3 class="text-xl font-bold text-gray-900">User
						Authentication</h3>
					<p class="mt-2 text-gray-600">Multi-factor authentication
						ensures only eligible voters can participate.</p>
				</div>

				<!-- Feature 6 -->
				<div
					class="bg-white rounded-lg shadow-md p-6 hover:shadow-lg transition-all duration-500 transform hover:-translate-y-2 animate-on-scroll stagger-item"
					style="--i: 6">
					<div
						class="inline-flex items-center justify-center rounded-md bg-primary-100 p-3 text-primary-600 mb-4 transition-all duration-300 group-hover:bg-primary-200">
						<svg xmlns="http://www.w3.org/2000/svg"
							class="h-6 w-6 animate-spin-slow" fill="none" viewBox="0 0 24 24"
							stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round"
								stroke-width="2"
								d="M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z" />
              </svg>
					</div>
					<h3 class="text-xl font-bold text-gray-900">Analytics
						Dashboard</h3>
					<p class="mt-2 text-gray-600">Comprehensive analytics and
						reporting tools for election administrators.</p>
				</div>
			</div>
		</div>
	</section>

	<!-- How It Works Section -->
	<section id="how-it-works" class="py-16 bg-white">
		<div class="container mx-auto px-4 sm:px-6 lg:px-8">
			<div class="text-center animate-on-scroll">
				<h2 class="text-3xl font-extrabold text-gray-900 sm:text-4xl">
					How It Works</h2>
				<p class="mt-4 max-w-2xl text-xl text-gray-500 mx-auto">Our
					voting system is designed to be simple, secure, and accessible.</p>
			</div>

			<div class="mt-16 max-w-4xl mx-auto">
				<div class="relative">
					<!-- Steps -->
					<div class="lg:grid lg:grid-cols-2 lg:gap-6">
						<!-- Step 1 -->
						<div
							class="relative mb-8 lg:mb-0 animate-on-scroll-right stagger-item"
							style="--i: 1">
							<div class="flex items-center">
								<div
									class="flex-shrink-0 h-10 w-10 rounded-full bg-primary-600 flex items-center justify-center text-white font-bold transform transition-transform duration-500 hover:scale-110">
									1</div>
								<div class="ml-4 text-lg font-medium text-gray-900">
									Register</div>
							</div>
							<div class="mt-2 ml-14">
								<p class="text-base text-gray-500">Create an account with
									your email and verify your identity using our secure
									verification process.</p>
							</div>
						</div>

						<!-- Step 2 -->
						<div
							class="relative mb-8 lg:mb-0 animate-on-scroll-left stagger-item"
							style="--i: 2">
							<div class="flex items-center">
								<div
									class="flex-shrink-0 h-10 w-10 rounded-full bg-primary-600 flex items-center justify-center text-white font-bold transform transition-transform duration-500 hover:scale-110">
									2</div>
								<div class="ml-4 text-lg font-medium text-gray-900">
									Receive Ballot</div>
							</div>
							<div class="mt-2 ml-14">
								<p class="text-base text-gray-500">Once verified, you'll
									receive access to your digital ballot for the elections you're
									eligible to vote in.</p>
							</div>
						</div>

						<!-- Step 3 -->
						<div
							class="relative mb-8 lg:mb-0 animate-on-scroll-right stagger-item"
							style="--i: 3">
							<div class="flex items-center">
								<div
									class="flex-shrink-0 h-10 w-10 rounded-full bg-primary-600 flex items-center justify-center text-white font-bold transform transition-transform duration-500 hover:scale-110">
									3</div>
								<div class="ml-4 text-lg font-medium text-gray-900">Cast
									Your Vote</div>
							</div>
							<div class="mt-2 ml-14">
								<p class="text-base text-gray-500">Review the candidates and
									issues, make your selections, and submit your encrypted ballot.
								</p>
							</div>
						</div>

						<!-- Step 4 -->
						<div class="relative animate-on-scroll-left stagger-item"
							style="--i: 4">
							<div class="flex items-center">
								<div
									class="flex-shrink-0 h-10 w-10 rounded-full bg-primary-600 flex items-center justify-center text-white font-bold transform transition-transform duration-500 hover:scale-110">
									4</div>
								<div class="ml-4 text-lg font-medium text-gray-900">
									Verify & Track</div>
							</div>
							<div class="mt-2 ml-14">
								<p class="text-base text-gray-500">Receive a confirmation
									receipt and track your anonymized ballot through the counting
									process.</p>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</section>

	<!-- FAQs Section -->
	<section id="faqs" class="py-16 bg-white">
		<div class="container mx-auto px-4 sm:px-6 lg:px-8">
			<div class="text-center animate-on-scroll">
				<h2 class="text-3xl font-extrabold text-gray-900 sm:text-4xl">
					Frequently Asked Questions</h2>
				<p class="mt-4 max-w-2xl text-xl text-gray-500 mx-auto">Find
					answers to common questions about our secure voting platform.</p>
			</div>

			<div class="mt-12 max-w-3xl mx-auto">
				<!-- FAQ Item 1 -->
				<div class="mb-8 animate-on-scroll stagger-item" style="--i: 1">
					<button class="w-full text-left focus:outline-none"
						onclick="toggleFAQ(this)">
						<h3
							class="text-lg font-medium text-gray-900 flex items-center justify-between">
							<span class="flex items-center"> <svg
									xmlns="http://www.w3.org/2000/svg"
									class="h-5 w-5 text-primary-600 mr-2" viewBox="0 0 20 20"
									fill="currentColor">
                    <path fill-rule="evenodd"
										d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-8-3a1 1 0 00-.867.5 1 1 0 11-1.731-1A3 3 0 0113 8a3.001 3.001 0 01-2 2.83V11a1 1 0 11-2 0v-1a1 1 0 011-1 1 1 0 100-2zm0 8a1 1 0 100-2 1 1 0 000 2z"
										clip-rule="evenodd" />
                  </svg> How secure is VoteSphere?
							</span>
							<svg xmlns="http://www.w3.org/2000/svg"
								class="h-5 w-5 text-gray-500 transform transition-transform duration-300"
								viewBox="0 0 20 20" fill="currentColor">
                  <path fill-rule="evenodd"
									d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z"
									clip-rule="evenodd" />
                </svg>
						</h3>
					</button>
					<p class="mt-2 text-gray-600 ml-7 hidden faq-answer">
						VoteSphere uses end-to-end encryption and blockchain technology to
						ensure vote integrity. Each vote is encrypted and anonymized,
						making it impossible to link votes to individual voters while
						maintaining a verifiable audit trail.</p>
				</div>

				<!-- FAQ Item 2 -->
				<div class="mb-8 animate-on-scroll stagger-item" style="--i: 2">
					<button class="w-full text-left focus:outline-none"
						onclick="toggleFAQ(this)">
						<h3
							class="text-lg font-medium text-gray-900 flex items-center justify-between">
							<span class="flex items-center"> <svg
									xmlns="http://www.w3.org/2000/svg"
									class="h-5 w-5 text-primary-600 mr-2" viewBox="0 0 20 20"
									fill="currentColor">
                    <path fill-rule="evenodd"
										d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-8-3a1 1 0 00-.867.5 1 1 0 11-1.731-1A3 3 0 0113 8a3.001 3.001 0 01-2 2.83V11a1 1 0 11-2 0v-1a1 1 0 011-1 1 1 0 100-2zm0 8a1 1 0 100-2 1 1 0 000 2z"
										clip-rule="evenodd" />
                  </svg> Can voters verify their votes were counted?
							</span>
							<svg xmlns="http://www.w3.org/2000/svg"
								class="h-5 w-5 text-gray-500 transform transition-transform duration-300"
								viewBox="0 0 20 20" fill="currentColor">
                  <path fill-rule="evenodd"
									d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z"
									clip-rule="evenodd" />
                </svg>
						</h3>
					</button>
					<p class="mt-2 text-gray-600 ml-7 hidden faq-answer">Yes, each
						voter receives a unique verification code that allows them to
						confirm their vote was recorded correctly without revealing their
						specific choices, maintaining ballot secrecy while ensuring
						transparency.</p>
				</div>

				<!-- FAQ Item 3 -->
				<div class="mb-8 animate-on-scroll stagger-item" style="--i: 3">
					<button class="w-full text-left focus:outline-none"
						onclick="toggleFAQ(this)">
						<h3
							class="text-lg font-medium text-gray-900 flex items-center justify-between">
							<span class="flex items-center"> <svg
									xmlns="http://www.w3.org/2000/svg"
									class="h-5 w-5 text-primary-600 mr-2" viewBox="0 0 20 20"
									fill="currentColor">
                    <path fill-rule="evenodd"
										d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-8-3a1 1 0 00-.867.5 1 1 0 11-1.731-1A3 3 0 0113 8a3.001 3.001 0 01-2 2.83V11a1 1 0 11-2 0v-1a1 1 0 011-1 1 1 0 100-2zm0 8a1 1 0 100-2 1 1 0 000 2z"
										clip-rule="evenodd" />
                  </svg> What types of elections can VoteSphere handle?
							</span>
							<svg xmlns="http://www.w3.org/2000/svg"
								class="h-5 w-5 text-gray-500 transform transition-transform duration-300"
								viewBox="0 0 20 20" fill="currentColor">
                  <path fill-rule="evenodd"
									d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z"
									clip-rule="evenodd" />
                </svg>
						</h3>
					</button>
					<p class="mt-2 text-gray-600 ml-7 hidden faq-answer">
						VoteSphere supports various election types including
						single-choice, multiple-choice, ranked-choice, and approval
						voting. It's suitable for organizations of all sizes, from student
						councils to national elections.</p>
				</div>

				<!-- FAQ Item 4 -->
				<div class="mb-8 animate-on-scroll stagger-item" style="--i: 4">
					<button class="w-full text-left focus:outline-none"
						onclick="toggleFAQ(this)">
						<h3
							class="text-lg font-medium text-gray-900 flex items-center justify-between">
							<span class="flex items-center"> <svg
									xmlns="http://www.w3.org/2000/svg"
									class="h-5 w-5 text-primary-600 mr-2" viewBox="0 0 20 20"
									fill="currentColor">
                    <path fill-rule="evenodd"
										d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-8-3a1 1 0 00-.867.5 1 1 0 11-1.731-1A3 3 0 0113 8a3.001 3.001 0 01-2 2.83V11a1 1 0 11-2 0v-1a1 1 0 011-1 1 1 0 100-2zm0 8a1 1 0 100-2 1 1 0 000 2z"
										clip-rule="evenodd" />
                  </svg> How accessible is the voting platform?
							</span>
							<svg xmlns="http://www.w3.org/2000/svg"
								class="h-5 w-5 text-gray-500 transform transition-transform duration-300"
								viewBox="0 0 20 20" fill="currentColor">
                  <path fill-rule="evenodd"
									d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z"
									clip-rule="evenodd" />
                </svg>
						</h3>
					</button>
					<p class="mt-2 text-gray-600 ml-7 hidden faq-answer">
						VoteSphere is designed with accessibility in mind, complying with
						WCAG guidelines. The platform works on any device with internet
						access and includes features like screen reader compatibility and
						keyboard navigation.</p>
				</div>

				<!-- FAQ Item 5 -->
				<div class="animate-on-scroll stagger-item" style="--i: 5">
					<button class="w-full text-left focus:outline-none"
						onclick="toggleFAQ(this)">
						<h3
							class="text-lg font-medium text-gray-900 flex items-center justify-between">
							<span class="flex items-center"> <svg
									xmlns="http://www.w3.org/2000/svg"
									class="h-5 w-5 text-primary-600 mr-2" viewBox="0 0 20 20"
									fill="currentColor">
                    <path fill-rule="evenodd"
										d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-8-3a1 1 0 00-.867.5 1 1 0 11-1.731-1A3 3 0 0113 8a3.001 3.001 0 01-2 2.83V11a1 1 0 11-2 0v-1a1 1 0 011-1 1 1 0 100-2zm0 8a1 1 0 100-2 1 1 0 000 2z"
										clip-rule="evenodd" />
                  </svg> What customer support options are available?
							</span>
							<svg xmlns="http://www.w3.org/2000/svg"
								class="h-5 w-5 text-gray-500 transform transition-transform duration-300"
								viewBox="0 0 20 20" fill="currentColor">
                  <path fill-rule="evenodd"
									d="M5.293 7.293a1 1 0 011.414 0L10 10.586l3.293-3.293a1 1 0 111.414 1.414l-4 4a1 1 0 01-1.414 0l-4-4a1 1 0 010-1.414z"
									clip-rule="evenodd" />
                </svg>
						</h3>
					</button>
					<p class="mt-2 text-gray-600 ml-7 hidden faq-answer">We offer
						24/7 technical support during active elections, comprehensive
						documentation, video tutorials, and dedicated account managers for
						enterprise clients to ensure your voting process runs smoothly.</p>
				</div>
			</div>
		</div>

		<script>
        function toggleFAQ(element) {
          const answer = element.nextElementSibling;
          const icon = element.querySelector("svg:last-child");

          // Toggle the answer visibility
          answer.classList.toggle("hidden");

          // Rotate the arrow icon
          if (answer.classList.contains("hidden")) {
            icon.classList.remove("rotate-180");
          } else {
            icon.classList.add("rotate-180");
          }
        }
      </script>
	</section>

	<!-- CTA Section -->
	<section class="py-16 bg-primary-600 bg-gradient-animated">
		<div class="container mx-auto px-4 sm:px-6 lg:px-8">
			<div class="max-w-3xl mx-auto text-center animate-on-scroll">
				<h2 class="text-3xl font-extrabold text-white sm:text-4xl">
					Ready to transform your voting process?</h2>
				<p class="mt-4 text-xl text-primary-100">Join thousands of
					organizations that trust VoteSphere for their elections.</p>
				<div class="mt-8 flex justify-center">
					<div class="inline-flex rounded-md shadow">
						<a href="#"
							class="inline-flex items-center justify-center px-5 py-3 border border-transparent text-base font-medium rounded-md text-primary-600 bg-white hover:bg-gray-50 transition-all duration-300 hover:shadow-lg transform hover:-translate-y-1 animate-pulse-slow">
							Get Started </a>
					</div>
					<div class="ml-3 inline-flex">
						<a href="/contact.html"
							class="inline-flex items-center justify-center px-5 py-3 border border-transparent text-base font-medium rounded-md text-white bg-primary-700 hover:bg-primary-800 transition-all duration-300 hover:shadow-lg transform hover:-translate-y-1">
							Contact VoteSphere </a>
					</div>
				</div>
			</div>
		</div>
	</section>

	<!-- Footer -->
	<footer id="contact" class="bg-white">
		<div class="container mx-auto px-4 sm:px-6 lg:px-8 py-12">
			<div class="grid grid-cols-1 md:grid-cols-3 gap-10">
				<!-- Company Info -->
				<div class="col-span-1 animate-on-scroll">
					<div class="flex items-center">
						<svg xmlns="http://www.w3.org/2000/svg"
							class="h-10 w-10 text-primary-600 animate-spin-slow" fill="none"
							viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round"
								stroke-width="2"
								d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z" />
              </svg>
						<span class="ml-3 text-2xl font-bold text-primary-600">VoteSphere</span>
					</div>
					<p class="mt-4 text-gray-500 text-lg">Secure, transparent, and
						accessible online voting for organizations of all sizes.</p>
					<div class="mt-6 flex space-x-5">
						<a href="#"
							class="text-gray-400 hover:text-primary-600 transition-colors duration-300 transform hover:scale-110">
							<span class="sr-only">Facebook</span> <svg class="h-7 w-7"
								fill="currentColor" viewBox="0 0 24 24" aria-hidden="true">
                  <path fill-rule="evenodd"
									d="M22 12c0-5.523-4.477-10-10-10S2 6.477 2 12c0 4.991 3.657 9.128 8.438 9.878v-6.987h-2.54V12h2.54V9.797c0-2.506 1.492-3.89 3.777-3.89 1.094 0 2.238.195 2.238.195v2.46h-1.26c-1.243 0-1.63.771-1.63 1.562V12h2.773l-.443 2.89h-2.33v6.988C18.343 21.128 22 16.991 22 12z"
									clip-rule="evenodd" />
                </svg>
						</a> <a href="#"
							class="text-gray-400 hover:text-primary-600 transition-colors duration-300 transform hover:scale-110">
							<span class="sr-only">Twitter</span> <svg class="h-7 w-7"
								fill="currentColor" viewBox="0 0 24 24" aria-hidden="true">
                  <path
									d="M8.29 20.251c7.547 0 11.675-6.253 11.675-11.675 0-.178 0-.355-.012-.53A8.348 8.348 0 0022 5.92a8.19 8.19 0 01-2.357.646 4.118 4.118 0 001.804-2.27 8.224 8.224 0 01-2.605.996 4.107 4.107 0 00-6.993 3.743 11.65 11.65 0 01-8.457-4.287 4.106 4.106 0 001.27 5.477A4.072 4.072 0 012.8 9.713v.052a4.105 4.105 0 003.292 4.022 4.095 4.095 0 01-1.853.07 4.108 4.108 0 003.834 2.85A8.233 8.233 0 012 18.407a11.616 11.616 0 006.29 1.84" />
                </svg>
						</a> <a href="#"
							class="text-gray-400 hover:text-primary-600 transition-colors duration-300 transform hover:scale-110">
							<span class="sr-only">LinkedIn</span> <svg class="h-7 w-7"
								fill="currentColor" viewBox="0 0 24 24" aria-hidden="true">
                  <path fill-rule="evenodd"
									d="M19 0h-14c-2.761 0-5 2.239-5 5v14c0 2.761 2.239 5 5 5h14c2.762 0 5-2.239 5-5v-14c0-2.761-2.238-5-5-5zm-11 19h-3v-11h3v11zm-1.5-12.268c-.966 0-1.75-.79-1.75-1.764s.784-1.764 1.75-1.764 1.75.79 1.75 1.764-.783 1.764-1.75 1.764zm13.5 12.268h-3v-5.604c0-3.368-4-3.113-4 0v5.604h-3v-11h3v1.765c1.396-2.586 7-2.777 7 2.476v6.759z"
									clip-rule="evenodd" />
                </svg>
						</a>
					</div>
				</div>

				<!-- Quick Links -->
				<div class="animate-on-scroll" style="--i: 1">
					<h3 class="text-lg font-semibold text-gray-800 tracking-wider mb-5">
						Quick Links</h3>
					<div class="grid grid-cols-2 gap-4">
						<a href="#features"
							class="text-gray-500 hover:text-primary-600 transition-colors duration-300 hover:translate-x-1 inline-block transform">
							Features </a> <a href="#"
							class="text-gray-500 hover:text-primary-600 transition-colors duration-300 hover:translate-x-1 inline-block transform">
							About Us </a> <a href="#how-it-works"
							class="text-gray-500 hover:text-primary-600 transition-colors duration-300 hover:translate-x-1 inline-block transform">
							How It Works </a> <a href="#"
							class="text-gray-500 hover:text-primary-600 transition-colors duration-300 hover:translate-x-1 inline-block transform">
							Privacy Policy </a> <a href="#faqs"
							class="text-gray-500 hover:text-primary-600 transition-colors duration-300 hover:translate-x-1 inline-block transform">
							FAQs </a> <a href="#"
							class="text-gray-500 hover:text-primary-600 transition-colors duration-300 hover:translate-x-1 inline-block transform">
							Blog </a>
					</div>
				</div>

				<!-- Contact -->
				<div class="animate-on-scroll-left" style="--i: 2">
					<h3 class="text-lg font-semibold text-gray-800 tracking-wider mb-5">
						Contact Us</h3>
					<div class="space-y-4">
						<div class="flex items-start">
							<svg xmlns="http://www.w3.org/2000/svg"
								class="h-5 w-5 text-primary-600 mt-1 mr-3" fill="none"
								viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round"
									stroke-width="2"
									d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
                  <path stroke-linecap="round" stroke-linejoin="round"
									stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
                </svg>
							<p class="text-gray-500">123 Voting Street, Democracy City,
								10001</p>
						</div>
						<div class="flex items-start">
							<svg xmlns="http://www.w3.org/2000/svg"
								class="h-5 w-5 text-primary-600 mt-1 mr-3" fill="none"
								viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round"
									stroke-width="2"
									d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" />
                </svg>
							<p class="text-gray-500">support@votesphere.com</p>
						</div>
						<div class="flex items-start">
							<svg xmlns="http://www.w3.org/2000/svg"
								class="h-5 w-5 text-primary-600 mt-1 mr-3" fill="none"
								viewBox="0 0 24 24" stroke="currentColor">
                  <path stroke-linecap="round" stroke-linejoin="round"
									stroke-width="2"
									d="M3 5a2 2 0 012-2h3.28a1 1 0 01.948.684l1.498 4.493a1 1 0 01-.502 1.21l-2.257 1.13a11.042 11.042 0 005.516 5.516l1.13-2.257a1 1 0 011.21-.502l4.493 1.498a1 1 0 01.684.949V19a2 2 0 01-2 2h-1C9.716 21 3 14.284 3 6V5z" />
                </svg>
							<p class="text-gray-500">+1 (555) 123-4567</p>
						</div>
					</div>
				</div>
			</div>

			<div class="mt-12 border-t border-gray-200 pt-8">
				<p class="text-base text-gray-500 text-center animate-pulse-slow">
					&copy; 2025 VoteSphere. All rights reserved.</p>
			</div>
		</div>
	</footer>

	<!-- JavaScript for animations -->
	<script>
      // Intersection Observer for scroll animations
      document.addEventListener("DOMContentLoaded", function () {
        // Number counter animation
        const counters = document.querySelectorAll(".number-counter");

        counters.forEach((counter) => {
          const target = parseInt(counter.getAttribute("data-value"));
          const duration = 2000; // 2 seconds
          const step = Math.ceil(target / (duration / 20)); // Update every 20ms
          let current = 0;

          const updateCounter = () => {
            current += step;
            if (current > target) current = target;
            counter.textContent = current.toLocaleString();

            if (current < target) {
              setTimeout(updateCounter, 20);
            }
          };

          updateCounter();
        });

        // Scroll animations
        const observerOptions = {
          root: null,
          rootMargin: "0px",
          threshold: 0.1,
        };

        const observer = new IntersectionObserver((entries, observer) => {
          entries.forEach((entry) => {
            if (entry.isIntersecting) {
              entry.target.classList.add("animate");
              observer.unobserve(entry.target);
            }
          });
        }, observerOptions);

        const animatedElements = document.querySelectorAll(
          ".animate-on-scroll, .animate-on-scroll-right, .animate-on-scroll-left"
        );
        animatedElements.forEach((element) => {
          observer.observe(element);
        });
      });
    </script>
</body>
</html>
