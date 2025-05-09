<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>VoteSphere - Register</title>
<link rel="stylesheet" href="styles/global.css" />
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
              secondary: {
                400: "#eab308",
              },
            },
            fontFamily: {
              sans: ["Inter", "sans-serif"],
            },
            animation: {
              "fade-in": "fadeIn 0.6s ease-in-out",
              "slide-up": "slideUp 0.6s ease-out",
            },
            keyframes: {
              fadeIn: {
                "0%": { opacity: "0" },
                "100%": { opacity: "1" },
              },
              slideUp: {
                "0%": { transform: "translateY(30px)", opacity: "0" },
                "100%": { transform: "translateY(0)", opacity: "1" },
              },
            },
          },
        },
      };
    </script>
<style>
.form-step {
	display: none;
	opacity: 0;
	transform: translateX(100px);
	transition: all 0.5s ease-in-out;
}

.form-step.active {
	display: block;
	opacity: 1;
	transform: translateX(0);
}

.form-step.slide-left {
	animation: slideLeft 0.5s forwards;
}

.form-step.slide-right {
	animation: slideRight 0.5s forwards;
}

@
keyframes slideLeft { 0% {
	opacity: 1;
	transform: translateX(0);
}

100
%
{
opacity
:
0;
transform
:
translateX(
-100px
);
}
}
@
keyframes slideRight { 0% {
	opacity: 1;
	transform: translateX(0);
}

100
%
{
opacity
:
0;
transform
:
translateX(
100px
);
}
}
.slide-in-right {
	animation: slideInRight 0.5s forwards;
}

.slide-in-left {
	animation: slideInLeft 0.5s forwards;
}

@
keyframes slideInRight { 0% {
	opacity: 0;
	transform: translateX(100px);
}

100
%
{
opacity
:
1;
transform
:
translateX(
0
);
}
}
@
keyframes slideInLeft { 0% {
	opacity: 0;
	transform: translateX(-100px);
}
100
%
{
opacity
:
1;
transform
:
translateX(
0
);
}
}
</style>
</head>
<body
	class="font-sans text-gray-800 bg-gray-50 min-h-screen flex flex-col">
	<!-- Header/Navigation -->
	<header class="bg-white shadow-sm sticky top-0 z-10 animate-fade-in">
		<div class="container mx-auto px-4 sm:px-6 lg:px-8">
			<div class="flex justify-between items-center h-16">
				<div class="flex items-center">
					<a href="/" class="flex items-center group"> <svg
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
					<a href="/#features"
						class="text-gray-600 hover:text-primary-600 px-3 py-2 text-sm font-medium transition-colors duration-300 hover:scale-105 transform">Features</a>
					<a href="/#how-it-works"
						class="text-gray-600 hover:text-primary-600 px-3 py-2 text-sm font-medium transition-colors duration-300 hover:scale-105 transform">How
						It Works</a> <a href="/#faqs"
						class="text-gray-600 hover:text-primary-600 px-3 py-2 text-sm font-medium transition-colors duration-300 hover:scale-105 transform">FAQs</a>
					<a href="/#contact"
						class="text-gray-600 hover:text-primary-600 px-3 py-2 text-sm font-medium transition-colors duration-300 hover:scale-105 transform">Contact</a>
				</nav>
				<div class="flex items-center">
					<a href="/login.html"
						class="hidden md:inline-flex items-center justify-center px-4 py-2 border border-transparent text-sm font-medium rounded-md text-white bg-primary-600 hover:bg-primary-700 transition-all duration-300 hover:shadow-lg transform hover:-translate-y-1">
						Log in </a> <a href="/register.html"
						class="ml-4 inline-flex items-center justify-center px-4 py-2 border border-transparent text-sm font-medium rounded-md text-primary-600 bg-white hover:bg-gray-50 border-primary-600 transition-all duration-300 hover:shadow-lg transform hover:-translate-y-1">
						Register </a>
					<button type="button"
						class="md:hidden ml-4 bg-white p-2 rounded-md text-gray-400 hover:text-gray-500 hover:bg-gray-100 focus:outline-none focus:ring-2 focus:ring-inset focus:ring-primary-800 transition-colors duration-300">
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
	<div class="container mx-auto p-4 py-10 flex-grow">
		<div
			class="max-w-3xl mx-auto bg-white rounded-lg shadow-lg p-6 animate-slide-up">
			<h1 class="text-2xl font-bold text-center text-gray-800 mb-6">
				Register as a Voter</h1>

			<!-- Progress Bar -->
			<div class="mb-8">
				<div class="flex justify-between mb-2">
					<span class="step-indicator text-sm font-medium"
						id="step-indicator">Step 1 of 4</span> <span
						class="text-sm font-medium" id="progress-percentage">25%</span>
				</div>
				<div class="w-full bg-gray-200 rounded-full h-2.5">
					<div
						class="bg-primary-700 h-2.5 rounded-full transition-all duration-500 ease-in-out"
						id="progress-bar" style="width: 25%"></div>
				</div>
			</div>

			<!-- Registration Form -->
			<form id="registrationForm" action="register.jsp" method="post"
				enctype="multipart/form-data">
				<!-- Step 1: Personal Information -->
				<div class="form-step active" id="step1">
					<h2 class="text-xl font-semibold mb-4 text-gray-700">Personal
						Information</h2>

					<div class="grid grid-cols-1 md:grid-cols-2 gap-4 mb-4">
						<div>
							<label for="first_name"
								class="block text-sm font-medium text-gray-700 mb-1">First
								Name</label> <input type="text" id="first_name" name="first_name"
								required
								class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary-700" />
						</div>
						<div>
							<label for="last_name"
								class="block text-sm font-medium text-gray-700 mb-1">Last
								Name</label> <input type="text" id="last_name" name="last_name" required
								class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary-700" />
						</div>
					</div>

					<div class="mb-4">
						<label for="voter_id"
							class="block text-sm font-medium text-gray-700 mb-1">Voter
							ID</label> <input type="text" id="voter_id" name="voter_id" required
							class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary-700" />
					</div>
					<div class="grid grid-cols-1 md:grid-cols-2 gap-4 mb-4">
						<div class="mb-4">
							<label for="email"
								class="block text-sm font-medium text-gray-700 mb-1">Email</label>
							<input type="email" id="email" name="email" required
								class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary-700" />
						</div>

						<div class="mb-4">
							<label for="dob"
								class="block text-sm font-medium text-gray-700 mb-1">Date
								of Birth</label> <input type="date" id="dob" name="dob" required
								class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary-700" />
						</div>
					</div>

					<div class="grid grid-cols-1 md:grid-cols-2 gap-4 mb-4">
						<div class="mb-4">
							<label for="password"
								class="block text-sm font-medium text-gray-700 mb-1">Password</label>
							<input type="password" id="password" name="password" required
								class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary-700" />
						</div>

						<div class="mb-4">
							<label for="confirm_password"
								class="block text-sm font-medium text-gray-700 mb-1">Confirm
								Password</label> <input type="password" id="confirm_password"
								name="confirm_password" required
								class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary-700" />
						</div>
					</div>

					<div class="flex justify-end">
						<button type="button" onclick="nextStep(1, 2)"
							class="px-4 py-2 bg-primary-700 text-white rounded-md hover:bg-primary-800 focus:outline-none focus:ring-2 focus:ring-primary-300 transition-all duration-300 hover:scale-105">
							Next</button>
					</div>
				</div>

				<!-- Step 2: Address Information -->
				<div class="form-step" id="step2">
					<h2 class="text-xl font-semibold mb-4 text-gray-700">Address
						Information</h2>

					<div class="mb-4">
						<label for="temporary_address"
							class="block text-sm font-medium text-gray-700 mb-1">Temporary
							Address</label>
						<textarea id="temporary_address" name="temporary_address" rows="3"
							required
							class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary-700"></textarea>
					</div>

					<div class="mb-4">
						<label for="permanent_address"
							class="block text-sm font-medium text-gray-700 mb-1">Permanent
							Address</label>
						<textarea id="permanent_address" name="permanent_address" rows="3"
							required
							class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary-700"></textarea>
					</div>

					<div class="flex justify-between">
						<button type="button" onclick="prevStep(2, 1)"
							class="px-4 py-2 bg-gray-300 text-gray-700 rounded-md hover:bg-gray-400 focus:outline-none focus:ring-2 focus:ring-gray-500 transition-all duration-300 hover:scale-105">
							Previous</button>
						<button type="button" onclick="nextStep(2, 3)"
							class="px-4 py-2 bg-primary-700 text-white rounded-md hover:bg-primary-800 focus:outline-none focus:ring-2 focus:ring-primary-300 transition-all duration-300 hover:scale-105">
							Next</button>
					</div>
				</div>

				<!-- Step 3: Profile Image -->
				<div class="form-step" id="step3">
					<h2 class="text-xl font-semibold mb-4 text-gray-700">Profile
						Image</h2>

					<div class="mb-4">
						<label for="profile_image"
							class="block text-sm font-medium text-gray-700 mb-1">Profile
							Image</label> <input type="file" id="profile_image" name="profile_image"
							accept="image/*" required
							class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary-700" />
						<p class="text-xs text-gray-500 mt-1">Upload a clear photo of
							yourself</p>
					</div>

					<div class="mb-4">
						<label for="image_holding_citizenship"
							class="block text-sm font-medium text-gray-700 mb-1">Image
							Holding Citizenship</label> <input type="file"
							id="image_holding_citizenship" name="image_holding_citizenship"
							accept="image/*" required
							class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary-700" />
						<p class="text-xs text-gray-500 mt-1">Upload a photo of
							yourself holding your citizenship document</p>
					</div>

					<div class="mb-4">
						<label for="thumb_print"
							class="block text-sm font-medium text-gray-700 mb-1">Thumb
							Print</label> <input type="file" id="thumb_print" name="thumb_print"
							accept="image/*" required
							class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary-700" />
						<p class="text-xs text-gray-500 mt-1">Upload a clear image of
							your thumb print</p>
					</div>

					<div class="flex justify-between">
						<button type="button" onclick="prevStep(3, 2)"
							class="px-4 py-2 bg-gray-300 text-gray-700 rounded-md hover:bg-gray-400 focus:outline-none focus:ring-2 focus:ring-gray-500 transition-all duration-300 hover:scale-105">
							Previous</button>
						<button type="button" onclick="nextStep(3, 4)"
							class="px-4 py-2 bg-primary-700 text-white rounded-md hover:bg-primary-800 focus:outline-none focus:ring-2 focus:ring-primary-300 transition-all duration-300 hover:scale-105">
							Next</button>
					</div>
				</div>

				<!-- Step 4: Document Verification -->
				<div class="form-step" id="step4">
					<h2 class="text-xl font-semibold mb-4 text-gray-700">Document
						Verification</h2>

					<div class="grid grid-cols-1 md:grid-cols-2 gap-4 mb-4">
						<div>
							<label for="voter_card_front"
								class="block text-sm font-medium text-gray-700 mb-1">Voter
								Card (Front)</label> <input type="file" id="voter_card_front"
								name="voter_card_front" accept="image/*" required
								class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary-700" />
						</div>
						<div>
							<label for="voter_card_back"
								class="block text-sm font-medium text-gray-700 mb-1">Voter
								Card (Back)</label> <input type="file" id="voter_card_back"
								name="voter_card_back" accept="image/*" required
								class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary-700" />
						</div>
					</div>

					<div class="grid grid-cols-1 md:grid-cols-2 gap-4 mb-6">
						<div>
							<label for="citizenship_front"
								class="block text-sm font-medium text-gray-700 mb-1">Citizenship
								(Front)</label> <input type="file" id="citizenship_front"
								name="citizenship_front" accept="image/*" required
								class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary-700" />
						</div>
						<div>
							<label for="citizenship_back"
								class="block text-sm font-medium text-gray-700 mb-1">Citizenship
								(Back)</label> <input type="file" id="citizenship_back"
								name="citizenship_back" accept="image/*" required
								class="w-full px-3 py-2 border border-gray-300 rounded-md focus:outline-none focus:ring-2 focus:ring-primary-700" />
						</div>
					</div>

					<div class="mb-6">
						<div class="flex items-center">
							<input type="checkbox" id="terms" name="terms" required
								class="h-4 w-4 text-primary-700 focus:ring-primary-800 border-gray-300 rounded" />
							<label for="terms" class="ml-2 block text-sm text-gray-700">
								I agree to the <a href="#"
								class="text-primary-700 hover:underline">Terms
									and Conditions</a> and confirm that all information provided is
								accurate.
							</label>
						</div>
					</div>

					<div class="flex justify-between">
						<button type="button" onclick="prevStep(4, 3)"
							class="px-4 py-2 bg-gray-300 text-gray-700 rounded-md hover:bg-gray-400 focus:outline-none focus:ring-2 focus:ring-gray-500 transition-all duration-300 hover:scale-105">
							Previous</button>
						<button type="submit"
							class="px-4 py-2 bg-primary-700 text-white rounded-md hover:bg-primary-800 focus:outline-none focus:ring-2 focus:ring-primary-300 transition-all duration-300 hover:scale-105">
							Submit Registration</button>
					</div>
				</div>
			</form>
		</div>
	</div>

	<!-- Footer -->
	<footer class="bg-white border-t border-gray-200 py-8 mt-auto">
		<div class="container mx-auto px-4 sm:px-6 lg:px-8">
			<div class="text-center text-gray-500 text-sm">
				<p>&copy; 2023 VoteSphere. All rights reserved.</p>
				<p class="mt-2">Secure online voting system</p>
			</div>
		</div>
	</footer>
	<script>
      document.addEventListener('DOMContentLoaded', function() {
          // Get all form steps
          const formSteps = document.querySelectorAll('.form-step');
          const nextButtons = document.querySelectorAll('.btn-next');
          const prevButtons = document.querySelectorAll('.btn-prev');
          const progressBar = document.getElementById('progressBar');
          const totalSteps = formSteps.length;
          
          // Initialize the first step
          updateProgress();
          
          // Next button functionality
          nextButtons.forEach(button => {
              button.addEventListener('click', function() {
                  const currentStep = this.closest('.form-step');
                  const nextStepId = this.getAttribute('data-next');
                  const nextStep = document.getElementById(nextStepId);
                  
                  // Validate current step before proceeding
                  if (validateStep(currentStep)) {
                      currentStep.classList.remove('active');
                      nextStep.classList.add('active');
                      updateProgress();
                  }
              });
          });
          
          // Previous button functionality
          prevButtons.forEach(button => {
              button.addEventListener('click', function() {
                  const currentStep = this.closest('.form-step');
                  const prevStepId = this.getAttribute('data-prev');
                  const prevStep = document.getElementById(prevStepId);
                  
                  currentStep.classList.remove('active');
                  prevStep.classList.add('active');
                  updateProgress();
              });
          });
          
          // Update progress bar
          function updateProgress() {
              const activeStep = document.querySelector('.form-step.active');
              const stepNumber = parseInt(activeStep.id.replace('step', ''));
              const progress = Math.round((stepNumber / totalSteps) * 100);
              
              progressBar.style.width = progress + '%';
              progressBar.textContent = `Step ${stepNumber} of ${totalSteps}`;
          }
          
          // Validate current step
          function validateStep(step) {
              let isValid = true;
              const requiredInputs = step.querySelectorAll('[required]');
              
              // Clear previous errors
              step.querySelectorAll('.error').forEach(el => el.textContent = '');
              step.querySelectorAll('.invalid').forEach(el => el.classList.remove('invalid'));
              
              // Validate each required field
              requiredInputs.forEach(input => {
                  if (!input.value.trim()) {
                      showError(input, 'This field is required');
                      isValid = false;
                  } else if (input.type === 'email' && !validateEmail(input.value)) {
                      showError(input, 'Please enter a valid email');
                      isValid = false;
                  }
              });
              
              return isValid;
          }
          
          // Show error message
          function showError(input, message) {
              input.classList.add('invalid');
              const errorElement = document.getElementById(input.id + 'Error') || 
                                  input.closest('.form-group').querySelector('.error');
              if (errorElement) {
                  errorElement.textContent = message;
              }
          }
          
          // Email validation
          function validateEmail(email) {
              const re = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
              return re.test(email);
          }
          
          // Form submission - ensure all steps are validated
          document.getElementById('multiPartForm').addEventListener('submit', function(e) {
              // Validate all steps before submission
              let allValid = true;
              
              formSteps.forEach(step => {
                  if (!validateStep(step)) {
                      allValid = false;
                  }
              });
              
              // Show first invalid step
              if (!allValid) {
                  e.preventDefault();
                  const firstInvalidStep = document.querySelector('.form-step .invalid');
                  if (firstInvalidStep) {
                      const invalidStep = firstInvalidStep.closest('.form-step');
                      document.querySelectorAll('.form-step').forEach(step => {
                          step.classList.remove('active');
                      });
                      invalidStep.classList.add('active');
                      updateProgress();
                      firstInvalidStep.focus();
                  }
              }
          });
      });
  </script>
</body>
</html>