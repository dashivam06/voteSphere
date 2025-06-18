<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment Successful - VoteSphere</title>
    <link rel="icon" src="${pageContext.request.contextPath}/resources/favicon.ico" type="image/x-icon" />

    <script src="https://cdn.tailwindcss.com"></script>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
    <style>
        body {
            font-family: 'Inter', sans-serif;
            background-color: #f8fafc;
        }
        .success-animation {
            animation: successScale 0.5s ease-in-out;
        }
        @keyframes successScale {
            0% { transform: scale(0.8); opacity: 0; }
            70% { transform: scale(1.1); opacity: 1; }
            100% { transform: scale(1); }
        }
    </style>
</head>
<body class="min-h-screen flex items-center justify-center p-4">
    <div class="max-w-md w-full bg-white rounded-xl shadow-lg overflow-hidden transition-all duration-300 hover:shadow-xl">
        <!-- Header -->
        <div class="bg-gradient-to-r from-green-400 to-green-600 p-6 text-center">
            <div class="success-animation inline-flex items-center justify-center w-20 h-20 bg-white rounded-full shadow-md">
                <svg xmlns="http://www.w3.org/2000/svg" class="h-12 w-12 text-green-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path>
                </svg>
            </div>
            <h1 class="mt-4 text-2xl font-bold text-white">Payment Successful!</h1>
            <p class="mt-2 text-green-100">Thank you for your payment</p>
        </div>

        <!-- Payment Details -->
        <div class="p-6">
            <div class="space-y-4">
                <div class="flex justify-between items-center border-b pb-3">
                    <span class="text-gray-500">Amount Paid</span>
                    <span class="font-semibold text-gray-800">Rs. ${amount}</span>
                </div>

                <div class="flex justify-between items-center border-b pb-3">
                    <span class="text-gray-500">Transaction ID</span>
                    <span class="font-mono text-sm text-gray-600">${transactionId}</span>
                </div>

                <div class="flex justify-between items-center border-b pb-3">
                    <span class="text-gray-500">Payment Method</span>
                    <div class="flex items-center">
                        <img src="https://esewa.com.np/common/images/esewa_logo.png" alt="eSewa" class="h-6 mr-2">
                        <span class="text-gray-800">eSewa</span>
                    </div>
                </div>

                <div class="flex justify-between items-center">
                    <span class="text-gray-500">Product Code</span>
                    <span class="font-mono text-sm text-gray-600">${productCode}</span>
                </div>
            </div>

            <div class="mt-8 bg-green-50 rounded-lg p-4 border border-green-100">
                <div class="flex">
                    <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-green-500 mt-0.5 mr-2" viewBox="0 0 20 20" fill="currentColor">
                        <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2h-1V9z" clip-rule="evenodd"></path>
                    </svg>
                    <div>
                        <h3 class="font-medium text-green-800">Payment Confirmed</h3>
                        <p class="text-sm text-green-600 mt-1">Your transaction has been processed successfully. A receipt has been sent to your email.</p>
                    </div>
                </div>
            </div>
        </div>

        <!-- Footer -->
        <div class="bg-gray-50 px-6 py-4 text-center">
            <a href="/dashboard"  class="inline-flex items-center justify-center px-6 py-3 border border-transparent text-base font-medium rounded-md text-white bg-green-600 hover:bg-green-700 transition-colors duration-200">
                Back to Homepage
                <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 ml-2" viewBox="0 0 20 20" fill="currentColor">
                    <path fill-rule="evenodd" d="M10.293 5.293a1 1 0 011.414 0l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414-1.414L12.586 11H5a1 1 0 110-2h7.586l-2.293-2.293a1 1 0 010-1.414z" clip-rule="evenodd"></path>
                </svg>
            </a>
            <p class="mt-4 text-xs text-gray-500">
                Need help? <a href="mailto:support@votesphere.com" class="text-green-600 hover:underline">Contact our support team</a>
            </p>
        </div>
    </div>


</body>
</html>