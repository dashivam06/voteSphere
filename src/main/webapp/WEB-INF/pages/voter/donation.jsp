<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<div class="preloader" id="preloader">
    <%@ include file="../loader-animation.jsp" %>
</div>
<script>
    document.addEventListener('DOMContentLoaded', function () {
        document.getElementById('preloader').style.display = 'none';
    });
</script>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>VoteSphere - Donate</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/styles/global.css" />
    <script src="https://cdn.tailwindcss.com"></script>
    <link rel="icon" src="/resources/favicon.ico" type="image/x-icon" />

    <link
            href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap"
            rel="stylesheet"
    />
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
                        sans: ["Poppins", "sans-serif"],
                    },
                    animation: {
                        "fade-in": "fadeIn 0.5s ease-in-out",
                        "slide-up": "slideUp 0.5s ease-out",
                    },
                    keyframes: {
                        fadeIn: {
                            "0%": { opacity: "0" },
                            "100%": { opacity: "1" },
                        },
                        slideUp: {
                            "0%": { transform: "translateY(20px)", opacity: "0" },
                            "100%": { transform: "translateY(0)", opacity: "1" },
                        },
                    },
                },
            },
        };
    </script>
    <style>
        .hover-scale {
            transition: transform 0.3s ease-in-out;
        }
        .hover-scale:hover {
            transform: scale(1);
        }
        .loading-spinner {
            display: none;
            width: 1.5rem;
            height: 1.5rem;
            border: 0.25rem solid rgba(255, 255, 255, 0.3);
            border-radius: 50%;
            border-top-color: white;
            animation: spin 1s ease-in-out infinite;
        }
        @keyframes spin {
            to { transform: rotate(360deg); }
        }
    </style>
</head>
<body class="font-[Poppins] bg-gray-100 flex h-screen overflow-hidden">

<!-- Include sidebar -->
<%@ include file="sidebar.jsp" %>
<!-- Main Content -->
<div class="flex-grow flex flex-col ml-0 lg:ml-64 transition-all duration-300 ease-in-out">
    <!-- Navbar -->
    <%@ include file="navbar.jsp" %>
    <!-- Main Content Area -->
    <main class="flex-1 overflow-y-auto p-4 md:p-8 bg-gray-100">
        <!-- Donation Form Section -->
        <div class="">
            <!-- Donation Header -->
            <div class="bg-white rounded-lg shadow-sm p-6 mb-6 animate-fade-in">
                <h1 class="text-2xl font-bold text-gray-800 mb-2">
                    Support Election Commission
                </h1>
                <p class="text-gray-600">
                    Your contribution helps maintain our platform and ensures fair,
                    transparent elections for all.
                </p>
            </div>

            <!-- Donation Form -->
            <div class="overflow-y-auto max-h-[calc(117vh-340px)] mb-2">
                <form id="donationForm" class="space-y-6">
                    <div class="mb-6">
                        <label for="amount" class="block text-sm font-medium text-gray-700 mb-2">
                        Donation Amount (NRS)
                        </label>
                        <div class="relative mt-1">
                            <div class="absolute inset-y-0 left-0 pl-3 flex items-center pointer-events-none">
                                <span class="text-gray-500 sm:text-lg">NRS</span>
                            </div>
                            <input
                                    type="number"
                                    id="amount"
                                    name="amount"
                                    min="1"
                                    value="100"
                                    placeholder="Enter amount"
                                    class="block w-full pl-20 pr-12 py-3 border border-gray-300 rounded-md shadow-sm focus:ring-primary-500 focus:border-primary-500 text-lg"
                                    required
                            />
                        </div>
                    </div>

                    <!-- Hidden fields that will be populated before eSewa submission -->
                    <input type="hidden" id="tax_amount" name="tax_amount" />
                    <input type="hidden" id="total_amount" name="total_amount" />
                    <input type="hidden" id="transaction_uuid" name="transaction_uuid" />
                    <input type="hidden" id="product_code" name="product_code" />
                    <input type="hidden" id="product_service_charge" name="product_service_charge" value="0" />
                    <input type="hidden" id="product_delivery_charge" name="product_delivery_charge" value="0" />
                    <input type="hidden" id="success_url" name="success_url" />
                    <input type="hidden" id="failure_url" name="failure_url" />
                    <input type="hidden" id="signed_field_names" name="signed_field_names" />
                    <input type="hidden" id="signature" name="signature" />

                    <!-- Quick Amount Buttons -->
                    <div class="grid grid-cols-1 md:grid-cols-6 gap-3 mb-6">
                        <button type="button" class="amount-btn py-2 px-4 bg-primary-50 text-primary-700 rounded-md hover:bg-primary-100 focus:outline-none focus:ring-2 focus:ring-primary-500">
                            NRS 500
                        </button>
                        <button type="button" class="amount-btn py-2 px-4 bg-primary-50 text-primary-700 rounded-md hover:bg-primary-100 focus:outline-none focus:ring-2 focus:ring-primary-500">
                            NRS 1000
                        </button>
                        <button type="button" class="amount-btn py-2 px-4 bg-primary-50 text-primary-700 rounded-md hover:bg-primary-100 focus:outline-none focus:ring-2 focus:ring-primary-500">
                            NRS 2500
                        </button>
                        <button type="button" class="amount-btn py-2 px-4 bg-primary-50 text-primary-700 rounded-md hover:bg-primary-100 focus:outline-none focus:ring-2 focus:ring-primary-500">
                            NRS 5000
                        </button>
                        <button type="button" class="amount-btn py-2 px-4 bg-primary-50 text-primary-700 rounded-md hover:bg-primary-100 focus:outline-none focus:ring-2 focus:ring-primary-500">
                            NRS 10000
                        </button>
                        <button type="button" class="amount-btn py-2 px-4 bg-primary-50 text-primary-700 rounded-md hover:bg-primary-100 focus:outline-none focus:ring-2 focus:ring-primary-500">
                            NRS 20000
                        </button>
                    </div>

                    <button
                            type="submit"
                            id="donateButton"
                            class="w-full flex justify-center items-center py-3 px-4 border border-transparent rounded-md shadow-sm text-base font-medium text-white bg-primary-600 hover:bg-primary-700 focus:outline-none focus:ring-2 focus:ring-offset-2 focus:ring-primary-500 transition-colors duration-200"
                    >
                        <span id="buttonText">Donate Now</span>
                        <span id="loadingSpinner" class="loading-spinner ml-2"></span>
                    </button>

                    <p class="text-center text-sm text-gray-500 mt-4">
                        All donations are secure and encrypted. <br />
                        Thank you for your support!
                    </p>
                </form>
            </div>

            <!-- Benefits Section -->
            <div class="bg-white rounded-lg shadow-sm p-6 mt-6 animate-slide-up" style="animation-delay: 0.1s">
                <h2 class="text-lg font-semibold text-gray-800 mb-4">
                    How Your Donation Helps
                </h2>
                <div class="grid grid-cols-1 md:grid-cols-3 gap-4">
                    <div class="flex items-start">
                        <div class="flex-shrink-0">
                            <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 text-primary-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9 12l2 2 4-4m5.618-4.016A11.955 11.955 0 0112 2.944a11.955 11.955 0 01-8.618 3.04A12.02 12.02 0 003 9c0 5.591 3.824 10.29 9 11.622 5.176-1.332 9-6.03 9-11.622 0-1.042-.133-2.052-.382-3.016z" />
                            </svg>
                        </div>
                        <div class="ml-3">
                            <p class="text-sm text-gray-600">
                                Improve security and transparency
                            </p>
                        </div>
                    </div>
                    <div class="flex items-start">
                        <div class="flex-shrink-0">
                            <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 text-primary-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13 7h8m0 0v8m0-8l-8 8-4-4-6 6" />
                            </svg>
                        </div>
                        <div class="ml-3">
                            <p class="text-sm text-gray-600">
                                Enhance platform capabilities
                            </p>
                        </div>
                    </div>
                    <div class="flex items-start">
                        <div class="flex-shrink-0">
                            <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6 text-primary-600" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M17 20h5v-2a3 3 0 00-5.356-1.857M17 20H7m10 0v-2c0-.656-.126-1.283-.356-1.857M7 20H2v-2a3 3 0 015.356-1.857M7 20v-2c0-.656.126-1.283.356-1.857m0 0a5.002 5.002 0 019.288 0M15 7a3 3 0 11-6 0 3 3 0 016 0zm6 3a2 2 0 11-4 0 2 2 0 014 0zM7 10a2 2 0 11-4 0 2 2 0 014 0z" />
                            </svg>
                        </div>
                        <div class="ml-3">
                            <p class="text-sm text-gray-600">Support voting awareness</p>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </main>
</div>

<script>
    document.addEventListener("DOMContentLoaded", function () {
        // Amount button functionality
        const amountBtns = document.querySelectorAll(".amount-btn");
        const amountInput = document.getElementById("amount");

        amountBtns.forEach((btn) => {
            btn.addEventListener("click", function () {
                const value = Number(this.textContent.replace("NRS", "").trim());
                amountInput.value = value;

                // Remove active class from all buttons
                amountBtns.forEach((b) => b.classList.remove("bg-primary-200"));
                // Add active class to clicked button
                this.classList.add("bg-primary-200");
            });
        });

        // Form submission
        document.getElementById("donationForm").addEventListener("submit", async function (e) {
            e.preventDefault();

            const amount = document.getElementById("amount").value;
            if (!amount || amount <= 0) {
                alert("Please enter a valid donation amount.");
                return;
            }

            // Show loading state
            const donateButton = document.getElementById("donateButton");
            const buttonText = document.getElementById("buttonText");
            const loadingSpinner = document.getElementById("loadingSpinner");

            donateButton.disabled = true;
            buttonText.textContent = "Processing...";
            loadingSpinner.style.display = "inline-block";

            try {
                // 1. First call your servlet to prepare payment data
                const response = await fetch('${pageContext.request.contextPath}/initiate-payment', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/x-www-form-urlencoded',
                    },
                    body: new URLSearchParams({
                        amount: amount
                    })
                });

                if (!response.ok) {
                    throw new Error('Failed to prepare payment');
                }

                const paymentData = await response.json();

                // 2. Create a new form for eSewa submission
                const esewaForm = document.createElement('form');
                esewaForm.method = 'POST';
                esewaForm.action = 'https://rc-epay.esewa.com.np/api/epay/main/v2/form';
                esewaForm.style.display = 'none';

                // Add all required fields to the form
                const addHiddenField = (name, value) => {
                    const input = document.createElement('input');
                    input.type = 'hidden';
                    input.name = name;
                    input.value = value;
                    esewaForm.appendChild(input);
                };

                addHiddenField('amount', paymentData.amount);
                addHiddenField('tax_amount', paymentData.taxAmount);
                addHiddenField('total_amount', paymentData.totalAmount);
                addHiddenField('transaction_uuid', paymentData.transactionUuid);
                addHiddenField('product_code', paymentData.productCode);
                addHiddenField('product_service_charge', paymentData.productServiceCharge || '0');
                addHiddenField('product_delivery_charge', paymentData.productDeliveryCharge || '0');
                addHiddenField('success_url', paymentData.successUrl);
                addHiddenField('failure_url', paymentData.failureUrl);
                addHiddenField('signed_field_names', paymentData.signedFieldNames);
                addHiddenField('signature', paymentData.signature);

                // Submit the form to eSewa
                document.body.appendChild(esewaForm);
                esewaForm.submit();

            } catch (error) {
                console.error('Error:', error);
                alert('Error processing payment. Please try again.');

                // Reset button state
                donateButton.disabled = false;
                buttonText.textContent = "Donate Now";
                loadingSpinner.style.display = "none";
            }
        });
    });
</script>
</body>
</html>