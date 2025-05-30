<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>VoteSphere - Page Not Found</title>
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
                        "bounce": "bounce 1s infinite",
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
        .animate-bounce {
            animation: bounce 1s infinite;
        }
        @keyframes bounce {
            0%, 100% {
                transform: translateY(-25%);
                animation-timing-function: cubic-bezier(0.8, 0, 1, 1);
            }
            50% {
                transform: translateY(0);
                animation-timing-function: cubic-bezier(0, 0, 0.2, 1);
            }
        }
    </style>
</head>
<body class="font-sans bg-gradient-to-br from-blue-50 to-indigo-100 min-h-screen flex items-center justify-center p-4">
<div class="max-w-2xl w-full">
    <div class="shadow-xl border-0 bg-white/80 backdrop-blur-sm rounded-lg">
        <div class="p-8 md:p-12 text-center">
            <!-- Error Icon -->
            <div class="relative mb-8">
                <div class="w-32 h-32 mx-auto bg-blue-100 rounded-full flex items-center justify-center relative overflow-hidden">
                    <div class="absolute inset-0 bg-blue-500/10 animate-pulse rounded-full"></div>
                    <!-- Map Pin Icon -->
                    <svg xmlns="http://www.w3.org/2000/svg" class="w-16 h-16 text-blue-500 relative z-10" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17.657 16.657L13.414 20.9a1.998 1.998 0 01-2.827 0l-4.244-4.243a8 8 0 1111.314 0z" />
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 11a3 3 0 11-6 0 3 3 0 016 0z" />
                    </svg>
                    <div class="absolute top-4 right-4 w-6 h-6 bg-yellow-400 rounded-full animate-bounce"></div>
                </div>
            </div>

            <!-- Error Code -->
            <div class="mb-6">
                <h1 class="text-8xl font-bold text-blue-500 mb-2 tracking-tight">404</h1>
                <h2 class="text-3xl font-bold text-gray-800 mb-4">Page Not Found</h2>
            </div>

            <!-- Error Message -->
            <div class="mb-8 space-y-4">
                <p class="text-lg text-gray-600 leading-relaxed">
                    Oops! The page you're looking for seems to have wandered off.
                </p>
                <p class="text-gray-500">Don't worry, it happens to the best of us. Let's get you back on track.</p>
            </div>

            <!-- Search Bar -->
            <div class="mb-8">
                <div class="relative max-w-md mx-auto">
                    <svg xmlns="http://www.w3.org/2000/svg" class="absolute left-3 top-1/2 transform -translate-y-1/2 text-gray-400 w-4 h-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
                    </svg>
                    <form action="${pageContext.request.contextPath}/search" method="GET">
                        <input
                                type="text"
                                name="q"
                                placeholder="Search for what you need..."
                                class="w-full pl-10 pr-4 py-3 border border-gray-200 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500 focus:border-transparent"
                        />
                    </form>
                </div>
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

            <!-- Popular Links -->
            <div class="mt-8 pt-6 border-t border-gray-200">
                <p class="text-sm text-gray-500 mb-4">Popular pages you might be looking for:</p>
                <div class="flex flex-wrap gap-2 justify-center">
                    <a href="${pageContext.request.contextPath}/election" class="text-blue-600 hover:text-blue-700 text-sm px-3 py-1.5 rounded-md hover:bg-gray-100">
                        Elections
                    </a>
                    <a href="${pageContext.request.contextPath}/dashboard" class="text-blue-600 hover:text-blue-700 text-sm px-3 py-1.5 rounded-md hover:bg-gray-100">
                        Dashboard
                    </a>
                    <a href="${pageContext.request.contextPath}/profile" class="text-blue-600 hover:text-blue-700 text-sm px-3 py-1.5 rounded-md hover:bg-gray-100">
                        Profile
                    </a>
                    <a href="${pageContext.request.contextPath}/help" class="text-blue-600 hover:text-blue-700 text-sm px-3 py-1.5 rounded-md hover:bg-gray-100">
                        Help Center
                    </a>
                </div>
            </div>
        </div>
    </div>
</div>
</body>
</html>