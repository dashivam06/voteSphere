<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ page import="java.util.Date, java.util.Random" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>VoteSphere - Server Error</title>
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
                        "ping": "ping 1.5s cubic-bezier(0, 0, 0.2, 1) infinite",
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
        .animate-ping {
            animation: ping 1.5s cubic-bezier(0, 0, 0.2, 1) infinite;
        }
        @keyframes ping {
            75%, 100% {
                transform: scale(2);
                opacity: 0;
            }
        }
    </style>
</head>
<body class="font-sans bg-gradient-to-br from-orange-50 to-red-100 min-h-screen flex items-center justify-center p-4">
<div class="max-w-2xl w-full">
    <div class="shadow-xl border-0 bg-white/80 backdrop-blur-sm rounded-lg">
        <div class="p-8 md:p-12 text-center">
            <!-- Error Icon -->
            <div class="relative mb-8">
                <div class="w-32 h-32 mx-auto bg-orange-100 rounded-full flex items-center justify-center relative overflow-hidden">
                    <div class="absolute inset-0 bg-orange-500/10 animate-pulse rounded-full"></div>
                    <!-- Alert Triangle Icon -->
                    <svg xmlns="http://www.w3.org/2000/svg" class="w-16 h-16 text-orange-500 relative z-10" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z" />
                    </svg>
                    <div class="absolute inset-0 border-4 border-orange-200 rounded-full animate-ping"></div>
                </div>
            </div>

            <!-- Error Code -->
            <div class="mb-6">
                <h1 class="text-8xl font-bold text-orange-500 mb-2 tracking-tight">500</h1>
                <h2 class="text-3xl font-bold text-gray-800 mb-4">Server Error</h2>
            </div>

            <!-- Error Message -->
            <div class="mb-8 space-y-4">
                <p class="text-lg text-gray-600 leading-relaxed">
                    Something went wrong on our end. We're working to fix it!
                </p>
                <p class="text-gray-500">
                    Our team has been notified and is investigating the issue. Please try again in a few moments.
                </p>
            </div>

            <!-- Status Information -->
            <div class="mb-8 p-4 bg-gray-50 rounded-lg">
                <div class="flex items-center justify-center gap-2 text-sm text-gray-600">
                    <div class="w-2 h-2 bg-orange-400 rounded-full animate-pulse"></div>
                    <span>Error ID: <%= new Random().nextInt(900000) + 100000 %></span>
                </div>
                <p class="text-xs text-gray-500 mt-2">Reference this ID when contacting support</p>
            </div>

            <!-- Action Buttons -->
            <div class="flex flex-col sm:flex-row gap-4 justify-center">
                <a href="javascript:window.location.reload()" class="bg-orange-600 hover:bg-orange-700 text-white px-4 py-2 rounded-md flex items-center justify-center gap-2">
                    <!-- Refresh Icon -->
                    <svg xmlns="http://www.w3.org/2000/svg" class="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 4v5h.582m15.356 2A8.001 8.001 0 004.582 9m0 0H9m11 11v-5h-.581m0 0a8.003 8.003 0 01-15.357-2m15.357 2H15" />
                    </svg>
                    Try Again
                </a>
                <a href="${pageContext.request.contextPath}/" class="border border-gray-200 text-gray-700 hover:bg-gray-50 px-4 py-2 rounded-md flex items-center justify-center gap-2">
                    <!-- Home Icon -->
                    <svg xmlns="http://www.w3.org/2000/svg" class="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6" />
                    </svg>
                    Go to Homepage
                </a>
            </div>

            <!-- Support Section -->
            <div class="mt-8 pt-6 border-t border-gray-200">
                <p class="text-sm text-gray-500 mb-4">
                    If the problem persists, please contact our technical support team
                </p>
                <div class="flex flex-col sm:flex-row gap-2 justify-center">
                    <a href="${pageContext.request.contextPath}/contact" class="flex items-center justify-center gap-2 text-orange-600 hover:text-orange-700 text-sm px-3 py-1.5 rounded-md hover:bg-gray-100">
                        <!-- Mail Icon -->
                        <svg xmlns="http://www.w3.org/2000/svg" class="w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M3 8l7.89 5.26a2 2 0 002.22 0L21 8M5 19h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v10a2 2 0 002 2z" />
                        </svg>
                        Contact Support
                    </a>
                    <a href="${pageContext.request.contextPath}/status" class="text-orange-600 hover:text-orange-700 text-sm px-3 py-1.5 rounded-md hover:bg-gray-100">
                        System Status
                    </a>
                </div>
            </div>

            <!-- Additional Info -->
            <div class="mt-6 text-xs text-gray-400">
                <p>Time: <%= new Date().toString() %></p>
            </div>
        </div>
    </div>
</div>
</body>
</html>