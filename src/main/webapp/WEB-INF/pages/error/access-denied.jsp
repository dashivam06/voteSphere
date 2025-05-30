
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>VoteSphere - Access Denied</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap" rel="stylesheet">
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
                        "pulse": "pulse 2s cubic-bezier(0.4, 0, 0.6, 1) infinite",
                    },
                },
            },
        };
    </script>
    <style>
        .animate-pulse {
            animation: pulse 2s cubic-bezier(0.4, 0, 0.6, 1) infinite;
        }
        @keyframes pulse {
            0%, 100% {
                opacity: 1;
            }
            50% {
                opacity: .5;
            }
        }
    </style>
</head>
<body class="font-sans bg-gradient-to-br from-gray-50 to-gray-100 min-h-screen flex items-center justify-center p-4">
<div class="max-w-2xl w-full">
    <div class="shadow-xl border-0 bg-white/80 backdrop-blur-sm rounded-lg">
        <div class="p-8 md:p-12 text-center">
            <!-- Error Icon -->
            <div class="relative mb-8">
                <div class="w-32 h-32 mx-auto bg-red-100 rounded-full flex items-center justify-center relative overflow-hidden">
                    <div class="absolute inset-0 bg-red-500/10 animate-pulse rounded-full"></div>
                    <!-- Shield Icon -->
                    <svg xmlns="http://www.w3.org/2000/svg" class="w-16 h-16 text-red-500 relative z-10" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M20.618 5.984A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z" />
                    </svg>
                    <!-- Lock Icon -->
                    <svg xmlns="http://www.w3.org/2000/svg" class="w-8 h-8 text-red-600 absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 15v2m-6 4h12a2 2 0 002-2v-6a2 2 0 00-2-2H6a2 2 0 00-2 2v6a2 2 0 002 2zm10-10V7a4 4 0 00-8 0v4h8z" />
                    </svg>
                </div>
            </div>

            <!-- Error Code -->
            <div class="mb-6">
                <h1 class="text-8xl font-bold text-red-500 mb-2 tracking-tight">403</h1>
                <h2 class="text-3xl font-bold text-gray-800 mb-4">Access Denied</h2>
            </div>

            <!-- Error Message -->
            <div class="mb-8 space-y-4">
                <p class="text-lg text-gray-600 leading-relaxed">
                    Sorry, you don't have permission to access this resource.
                </p>
                <p class="text-gray-500">This could be because:</p>
                <ul class="text-sm text-gray-500 space-y-2 max-w-md mx-auto">
                    <li class="flex items-center gap-2">
                        <div class="w-1.5 h-1.5 bg-gray-400 rounded-full"></div>
                        Your account doesn't have the required permissions
                    </li>
                    <li class="flex items-center gap-2">
                        <div class="w-1.5 h-1.5 bg-gray-400 rounded-full"></div>
                        You need to verify your identity first
                    </li>
                    <li class="flex items-center gap-2">
                        <div class="w-1.5 h-1.5 bg-gray-400 rounded-full"></div>
                        The resource is restricted to certain user roles
                    </li>
                </ul>
            </div>

            <!-- Action Buttons -->
            <div class="flex flex-col sm:flex-row gap-4 justify-center">
                <a href="${pageContext.request.contextPath}/" class="bg-blue-600 hover:bg-blue-700 text-white px-4 py-2 rounded-md flex items-center justify-center gap-2">
                    <!-- Home Icon -->
                    <svg xmlns="http://www.w3.org/2000/svg" class="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6" />
                    </svg>
                    Go to Homepage
                </a>
                <a href="javascript:history.back()" class="border border-gray-200 text-gray-700 hover:bg-gray-50 px-4 py-2 rounded-md flex items-center justify-center gap-2">
                    <!-- Arrow Left Icon -->
                    <svg xmlns="http://www.w3.org/2000/svg" class="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M10 19l-7-7m0 0l7-7m-7 7h18" />
                    </svg>
                    Go Back
                </a>
            </div>

            <!-- Help Section -->
            <div class="mt-8 pt-6 border-t border-gray-200">
                <p class="text-sm text-gray-500 mb-3">Need help? Contact our support team</p>
                <a href="${pageContext.request.contextPath}/contact" class="text-blue-600 hover:text-blue-700 text-sm px-3 py-1.5 rounded-md hover:bg-gray-100">
                    Contact Support
                </a>
            </div>
        </div>
    </div>
</div>
</body>
</html>