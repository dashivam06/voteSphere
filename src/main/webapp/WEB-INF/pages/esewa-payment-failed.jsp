<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Payment Failed - VoteSphere</title>
  <link rel="icon" src="/resources/favicon.ico" type="image/x-icon" />

  <script src="https://cdn.tailwindcss.com"></script>
  <link href="https://fonts.googleapis.com/css2?family=Inter:wght@300;400;500;600;700&display=swap" rel="stylesheet">
  <style>
    body {
      font-family: 'Inter', sans-serif;
      background-color: #f8fafc;
    }
    .error-animation {
      animation: errorShake 0.5s ease-in-out;
    }
    @keyframes errorShake {
      0%, 100% { transform: translateX(0); }
      20%, 60% { transform: translateX(-5px); }
      40%, 80% { transform: translateX(5px); }
    }
    .pulse {
      animation: pulse 2s cubic-bezier(0.4, 0, 0.6, 1) infinite;
    }
    @keyframes pulse {
      0%, 100% { opacity: 1; }
      50% { opacity: 0.7; }
    }
  </style>
</head>
<body class="min-h-screen flex items-center justify-center p-4">
<div class="max-w-md w-full bg-white rounded-xl shadow-lg overflow-hidden transition-all duration-300 hover:shadow-xl">
  <!-- Header -->
  <div class="bg-gradient-to-r from-red-400 to-red-600 p-6 text-center">
    <div class="error-animation inline-flex items-center justify-center w-20 h-20 bg-white rounded-full shadow-md">
      <svg xmlns="http://www.w3.org/2000/svg" class="h-12 w-12 text-red-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
      </svg>
    </div>
    <h1 class="mt-4 text-2xl font-bold text-white">Payment Failed</h1>
    <p class="mt-2 text-red-100">We couldn't process your payment</p>
  </div>

  <!-- Payment Details -->
  <div class="p-6">
    <div class="space-y-4">
      <div class="flex justify-between items-center border-b pb-3">
        <span class="text-gray-500">Amount</span>
        <span class="font-semibold text-gray-800">Rs. ${amount}</span>
      </div>

      <div class="flex justify-between items-center border-b pb-3">
        <span class="text-gray-500">Reference ID</span>
        <span class="font-mono text-sm text-gray-600">${transactionId}</span>
      </div>

      <div class="flex justify-between items-center border-b pb-3">
        <span class="text-gray-500">Payment Method</span>
        <div class="flex items-center">
          <img src="https://esewa.com.np/common/images/esewa_logo.png" alt="eSewa" class="h-6 mr-2">
          <span class="text-gray-800">eSewa</span>
        </div>
      </div>

      <c:if test="${not empty fullName}">
        <div class="flex justify-between items-center border-b pb-3">
          <span class="text-gray-500">Customer Name</span>
          <span class="font-medium text-gray-800">${fullName}</span>
        </div>
      </c:if>

      <div class="flex justify-between items-center">
        <span class="text-gray-500">Product Code</span>
        <span class="font-mono text-sm text-gray-600">${productCode}</span>
      </div>
    </div>

    <div class="mt-8 bg-red-50 rounded-lg p-4 border border-red-100">
      <div class="flex">
        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-red-500 mt-0.5 mr-2" viewBox="0 0 20 20" fill="currentColor">
          <path fill-rule="evenodd" d="M18 10a8 8 0 11-16 0 8 8 0 0116 0zm-7-4a1 1 0 11-2 0 1 1 0 012 0zM9 9a1 1 0 000 2v3a1 1 0 001 1h1a1 1 0 100-2h-1V9z" clip-rule="evenodd"></path>
        </svg>
        <div>
          <h3 class="font-medium text-red-800">Payment Not Completed</h3>
          <p class="text-sm text-red-600 mt-1">
            <c:choose>
              <c:when test="${not empty errorMessage}">
                ${errorMessage}
              </c:when>
              <c:otherwise>
                The transaction could not be processed. Please try again or contact your payment provider.
              </c:otherwise>
            </c:choose>
          </p>
        </div>
      </div>
    </div>

    <div class="mt-6 flex flex-col sm:flex-row justify-center gap-3">
      <a href="${pageContext.request.contextPath}/initiate-payment" class="pulse inline-flex items-center justify-center px-6 py-3 border border-transparent text-base font-medium rounded-md text-white bg-red-600 hover:bg-red-700 transition-colors duration-200">
        Try Again
        <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 ml-2" viewBox="0 0 20 20" fill="currentColor">
          <path fill-rule="evenodd" d="M4 2a1 1 0 011 1v2.101a7.002 7.002 0 0111.601 2.566 1 1 0 11-1.885.666A5.002 5.002 0 005.999 7H9a1 1 0 010 2H4a1 1 0 01-1-1V3a1 1 0 011-1zm.008 9.057a1 1 0 011.276.61A5.002 5.002 0 0014.001 13H11a1 1 0 110-2h5a1 1 0 011 1v5a1 1 0 11-2 0v-2.101a7.002 7.002 0 01-11.601-2.566 1 1 0 01.61-1.276z" clip-rule="evenodd"></path>
        </svg>
      </a>
      <a href="/dashboard" class="inline-flex items-center justify-center px-6 py-3 border border-gray-300 text-base font-medium rounded-md text-gray-700 bg-white hover:bg-gray-50 transition-colors duration-200">
        Back to Dashboard
      </a>
    </div>
  </div>

  <!-- Footer -->
  <div class="bg-gray-50 px-6 py-4 text-center">
    <p class="text-xs text-gray-500">
      Need help? <a href="mailto:support@votesphere.com" class="text-red-600 hover:underline">Contact our support team</a>
      <br>or call +977-89444001
    </p>
  </div>
</div>
</body>
</html>