<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <title>VoteSphere - Admin Dashboard</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <link
      href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600;700&display=swap"
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
              sans: ["Inter", "sans-serif"],
            },
            animation: {
              "fade-in": "fadeIn 0.5s ease-in-out",
              "slide-in-left": "slideInLeft 0.5s ease-out",
              "slide-in-right": "slideInRight 0.5s ease-out",
              "slide-up": "slideUp 0.5s ease-out",
              "pulse-slow": "pulse 3s infinite",
              "bounce-slow": "bounce 3s infinite",
              "spin-slow": "spin 8s linear infinite",
            },
            keyframes: {
              fadeIn: {
                "0%": { opacity: "0" },
                "100%": { opacity: "1" },
              },
              slideInLeft: {
                "0%": { transform: "translateX(-20px)", opacity: "0" },
                "100%": { transform: "translateX(0)", opacity: "1" },
              },
              slideInRight: {
                "0%": { transform: "translateX(20px)", opacity: "0" },
                "100%": { transform: "translateX(0)", opacity: "1" },
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
      /* Custom CSS for charts and animations */
      .chart-bar {
        transition: height 1s ease-out;
      }

      .chart-bar:hover {
        filter: brightness(1.1);
      }

      .chart-donut-segment {
        transition: all 0.3s;
      }

      .chart-donut-segment:hover {
        transform: scale(1.05);
        filter: brightness(1.1);
      }

      .progress-ring-circle {
        transition: stroke-dashoffset 0.5s ease-in-out;
        transform: rotate(-90deg);
        transform-origin: 50% 50%;
      }

      .animate-on-scroll {
        opacity: 0;
        transform: translateY(20px);
        transition: opacity 0.6s ease-out, transform 0.6s ease-out;
      }

      .animate-on-scroll.animate {
        opacity: 1;
        transform: translateY(0);
      }

      .stagger-item {
        transition-delay: calc(var(--i) * 0.1s);
      }

      @keyframes float {
        0% {
          transform: translateY(0px);
        }
        50% {
          transform: translateY(-5px);
        }
        100% {
          transform: translateY(0px);
        }
      }

      .animate-float {
        animation: float 3s ease-in-out infinite;
      }

      /* Tooltip styles */
      .tooltip {
        position: relative;
      }

      .tooltip:hover .tooltip-text {
        visibility: visible;
        opacity: 1;
      }

      .tooltip-text {
        visibility: hidden;
        opacity: 0;
        width: max-content;
        max-width: 200px;
        background-color: #333;
        color: #fff;
        text-align: center;
        border-radius: 6px;
        padding: 5px 10px;
        position: absolute;
        z-index: 1;
        bottom: 125%;
        left: 50%;
        transform: translateX(-50%);
        transition: opacity 0.3s;
      }

      .tooltip-text::after {
        content: "";
        position: absolute;
        top: 100%;
        left: 50%;
        margin-left: -5px;
        border-width: 5px;
        border-style: solid;
        border-color: #333 transparent transparent transparent;
      }
    </style>
  </head>
  <body class="font-sans bg-gray-100 flex h-screen overflow-hidden">
    <!-- Include sidebar -->
    <%@ include file="sidebar.jsp" %>

    <!-- Main Content -->
    <div
      class="flex-grow flex flex-col ml-0 lg:ml-64 transition-all duration-300 ease-in-out"
    >
      <!-- Navbar -->
      <%@ include file="navbar.jsp" %>

      <!-- Main Content Area -->
      <main class="flex-1 overflow-y-auto p-4 bg-gray-100">
        <!-- Dashboard Overview -->
        <div class="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-4 mb-6">
          <!-- Total Elections Card -->
          <div
            class="bg-white rounded-lg shadow-sm p-4 animate-slide-up"
            style="animation-delay: 0.1s"
          >
            <div class="flex items-center justify-between">
              <div>
                <p class="text-sm text-gray-500 font-medium">Total Elections</p>
                <p class="text-2xl font-bold text-gray-800">24</p>
              </div>
              <div
                class="h-12 w-12 rounded-full bg-primary-100 flex items-center justify-center animate-pulse-slow"
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  class="h-6 w-6 text-primary-600"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2"
                  />
                </svg>
              </div>
            </div>
            <div class="mt-4">
              <div class="flex items-center">
                <span
                  class="text-green-500 flex items-center text-sm font-medium"
                >
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    class="h-4 w-4 mr-1"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M5 10l7-7m0 0l7 7m-7-7v18"
                    />
                  </svg>
                  12%
                </span>
                <span class="text-gray-500 text-sm ml-2">from last month</span>
              </div>
            </div>
          </div>

          <!-- Active Voters Card -->
          <div
            class="bg-white rounded-lg shadow-sm p-4 animate-slide-up"
            style="animation-delay: 0.2s"
          >
            <div class="flex items-center justify-between">
              <div>
                <p class="text-sm text-gray-500 font-medium">Active Voters</p>
                <p class="text-2xl font-bold text-gray-800">8,549</p>
              </div>
              <div
                class="h-12 w-12 rounded-full bg-green-100 flex items-center justify-center animate-bounce-slow"
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  class="h-6 w-6 text-green-600"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197M13 7a4 4 0 11-8 0 4 4 0 018 0z"
                  />
                </svg>
              </div>
            </div>
            <div class="mt-4">
              <div class="flex items-center">
                <span
                  class="text-green-500 flex items-center text-sm font-medium"
                >
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    class="h-4 w-4 mr-1"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M5 10l7-7m0 0l7 7m-7-7v18"
                    />
                  </svg>
                  18%
                </span>
                <span class="text-gray-500 text-sm ml-2">from last month</span>
              </div>
            </div>
          </div>

          <!-- Votes Cast Card -->
          <div
            class="bg-white rounded-lg shadow-sm p-4 animate-slide-up"
            style="animation-delay: 0.3s"
          >
            <div class="flex items-center justify-between">
              <div>
                <p class="text-sm text-gray-500 font-medium">Votes Cast</p>
                <p class="text-2xl font-bold text-gray-800">42,891</p>
              </div>
              <div
                class="h-12 w-12 rounded-full bg-purple-100 flex items-center justify-center animate-pulse-slow"
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  class="h-6 w-6 text-purple-600"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"
                  />
                </svg>
              </div>
            </div>
            <div class="mt-4">
              <div class="flex items-center">
                <span
                  class="text-green-500 flex items-center text-sm font-medium"
                >
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    class="h-4 w-4 mr-1"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M5 10l7-7m0 0l7 7m-7-7v18"
                    />
                  </svg>
                  24%
                </span>
                <span class="text-gray-500 text-sm ml-2">from last month</span>
              </div>
            </div>
          </div>

          <!-- System Status Card -->
          <div
            class="bg-white rounded-lg shadow-sm p-4 animate-slide-up"
            style="animation-delay: 0.4s"
          >
            <div class="flex items-center justify-between">
              <div>
                <p class="text-sm text-gray-500 font-medium">System Status</p>
                <p class="text-2xl font-bold text-green-600">Operational</p>
              </div>
              <div
                class="h-12 w-12 rounded-full bg-green-100 flex items-center justify-center animate-spin-slow"
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  class="h-6 w-6 text-green-600"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M5 12h.01M12 12h.01M19 12h.01M6 12a1 1 0 11-2 0 1 1 0 012 0zm7 0a1 1 0 11-2 0 1 1 0 012 0zm7 0a1 1 0 11-2 0 1 1 0 012 0z"
                  />
                </svg>
              </div>
            </div>
            <div class="mt-4">
              <div class="w-full bg-gray-200 rounded-full h-2.5">
                <div
                  class="bg-green-600 h-2.5 rounded-full"
                  style="width: 98%"
                ></div>
              </div>
              <p class="text-gray-500 text-sm mt-1">98% uptime</p>
            </div>
          </div>
        </div>

        <!-- Charts and Analytics -->
        <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-6">
          <!-- Voting Activity Chart -->
          <div class="bg-white rounded-lg shadow-sm p-6 animate-on-scroll">
            <div class="flex justify-between items-center mb-4">
              <h2 class="text-lg font-semibold text-gray-800">
                Voting Participation
              </h2>
              <div class="relative">
                <select
                  class="appearance-none bg-gray-50 border border-gray-200 text-gray-700 py-1 px-3 pr-8 rounded-md text-xs font-medium focus:outline-none focus:ring-2 focus:ring-primary-500 focus:border-primary-500"
                >
                  <option>Daily</option>
                  <option>Weekly</option>
                  <option>Monthly</option>
                </select>
                <div
                  class="pointer-events-none absolute inset-y-0 right-0 flex items-center px-2 text-gray-700"
                >
                  <svg
                    class="fill-current h-4 w-4"
                    xmlns="http://www.w3.org/2000/svg"
                    viewBox="0 0 20 20"
                  >
                    <path
                      d="M9.293 12.95l.707.707L15.657 8l-1.414-1.414L10 10.828 5.757 6.586 4.343 8z"
                    />
                  </svg>
                </div>
              </div>
            </div>
            <div class="h-64">
              <div
                id="votingActivityChart"
                class="w-full h-full rounded-lg p-4 relative"
              >
                <!-- Static chart using HTML/CSS that can be replaced with dynamic JSP data later -->
                <div class="flex items-end justify-between h-48 relative">
                  <!-- Y-axis labels -->
                  <div
                    class="absolute left-0 top-0 h-full flex flex-col justify-between text-xs text-gray-500 pr-2"
                  >
                    <span>2000</span>
                    <span>1500</span>
                    <span>1000</span>
                    <span>500</span>
                    <span>0</span>
                  </div>

                  <!-- Bars for each day -->
                  <div
                    class="flex items-end justify-between w-full h-full pl-10"
                  >
                    <div class="flex flex-col items-center">
                      <div
                        class="w-8 bg-primary-500 rounded-t-sm h-32"
                        style="height: 60%"
                      ></div>
                      <span class="text-xs mt-1 text-gray-600">Mon</span>
                    </div>
                    <div class="flex flex-col items-center">
                      <div
                        class="w-8 bg-primary-500 rounded-t-sm h-40"
                        style="height: 75%"
                      ></div>
                      <span class="text-xs mt-1 text-gray-600">Tue</span>
                    </div>
                    <div class="flex flex-col items-center">
                      <div
                        class="w-8 bg-primary-500 rounded-t-sm h-24"
                        style="height: 45%"
                      ></div>
                      <span class="text-xs mt-1 text-gray-600">Wed</span>
                    </div>
                    <div class="flex flex-col items-center">
                      <div
                        class="w-8 bg-primary-500 rounded-t-sm h-44"
                        style="height: 90%"
                      ></div>
                      <span class="text-xs mt-1 text-gray-600">Thu</span>
                    </div>
                    <div class="flex flex-col items-center">
                      <div
                        class="w-8 bg-primary-500 rounded-t-sm h-36"
                        style="height: 65%"
                      ></div>
                      <span class="text-xs mt-1 text-gray-600">Fri</span>
                    </div>
                    <div class="flex flex-col items-center">
                      <div
                        class="w-8 bg-primary-500 rounded-t-sm h-16"
                        style="height: 30%"
                      ></div>
                      <span class="text-xs mt-1 text-gray-600">Sat</span>
                    </div>
                    <div class="flex flex-col items-center">
                      <div
                        class="w-8 bg-primary-500 rounded-t-sm h-12"
                        style="height: 25%"
                      ></div>
                      <span class="text-xs mt-1 text-gray-600">Sun</span>
                    </div>
                  </div>
                </div>

                <!-- Horizontal grid lines -->
                <div
                  class="absolute left-10 top-0 w-full h-full flex flex-col justify-between pointer-events-none"
                >
                  <div class="border-b border-gray-200 w-full"></div>
                  <div class="border-b border-gray-200 w-full"></div>
                  <div class="border-b border-gray-200 w-full"></div>
                  <div class="border-b border-gray-200 w-full"></div>
                  <div class="border-b border-gray-200 w-full"></div>
                </div>
              </div>
            </div>
            <div class="mt-6 grid grid-cols-3 gap-4 text-center">
              <div class="bg-gray-50 rounded-lg p-3">
                <p class="text-xs text-gray-500">Total Votes</p>
                <p class="text-lg font-bold text-gray-800">9,427</p>
              </div>
              <div class="bg-gray-50 rounded-lg p-3">
                <p class="text-xs text-gray-500">Peak Day</p>
                <p class="text-lg font-bold text-gray-800">Thursday</p>
              </div>
              <div class="bg-gray-50 rounded-lg p-3">
                <p class="text-xs text-gray-500">Growth</p>
                <p class="text-lg font-bold text-green-600">+17.3%</p>
              </div>
            </div>
            <script>
              // Initialize chart when document is loaded
              document.addEventListener("DOMContentLoaded", function () {
                const ctx = document
                  .getElementById("votingActivityChart")
                  .getContext("2d");
                new Chart(ctx, {
                  type: "line",
                  data: {
                    labels: ["Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"],
                    datasets: [
                      {
                        label: "Votes",
                        data: [1245, 1876, 932, 2189, 1567, 621, 512],
                        borderColor: "#6366f1",
                        backgroundColor: "rgba(99, 102, 241, 0.1)",
                        tension: 0.4,
                        fill: true,
                      },
                    ],
                  },
                  options: {
                    responsive: true,
                    maintainAspectRatio: false,
                  },
                });
              });
            </script>
          </div>

          <!-- Voter Demographics -->
          <div class="bg-white rounded-lg shadow-sm p-6 animate-on-scroll">
            <div class="flex justify-between items-center mb-4">
              <h2 class="text-lg font-semibold text-gray-800">
                Voter Demographics
              </h2>
              <button
                class="text-primary-600 hover:text-primary-700 text-sm font-medium transition-colors duration-200"
              >
                View Details
              </button>
            </div>
            <div class="flex justify-center items-center h-64">
              <div class="relative w-48 h-48">
                <!-- Donut Chart -->
                <svg viewBox="0 0 36 36" class="w-full h-full">
                  <!-- Age 18-24 -->
                  <path
                    class="chart-donut-segment"
                    d="M18 2.0845 a 15.9155 15.9155 0 0 1 0 31.831 a 15.9155 15.9155 0 0 1 0 -31.831"
                    fill="none"
                    stroke="#0ea5e9"
                    stroke-width="3"
                    stroke-dasharray="25, 100"
                    stroke-dashoffset="0"
                  ></path>
                  <!-- Age 25-34 -->
                  <path
                    class="chart-donut-segment"
                    d="M18 2.0845 a 15.9155 15.9155 0 0 1 0 31.831 a 15.9155 15.9155 0 0 1 0 -31.831"
                    fill="none"
                    stroke="#7dd3fc"
                    stroke-width="3"
                    stroke-dasharray="30, 100"
                    stroke-dashoffset="25"
                  ></path>
                  <!-- Age 35-44 -->
                  <path
                    class="chart-donut-segment"
                    d="M18 2.0845 a 15.9155 15.9155 0 0 1 0 31.831 a 15.9155 15.9155 0 0 1 0 -31.831"
                    fill="none"
                    stroke="#38bdf8"
                    stroke-width="3"
                    stroke-dasharray="20, 100"
                    stroke-dashoffset="55"
                  ></path>
                  <!-- Age 45+ -->
                  <path
                    class="chart-donut-segment"
                    d="M18 2.0845 a 15.9155 15.9155 0 0 1 0 31.831 a 15.9155 15.9155 0 0 1 0 -31.831"
                    fill="none"
                    stroke="#0284c7"
                    stroke-width="3"
                    stroke-dasharray="25, 100"
                    stroke-dashoffset="75"
                  ></path>
                </svg>
                <div
                  class="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 text-center"
                >
                  <p class="text-3xl font-bold text-gray-800">8,549</p>
                  <p class="text-sm text-gray-500">Total Voters</p>
                </div>
              </div>
            </div>
            <div class="grid grid-cols-2 gap-4 mt-4">
              <div class="flex items-center">
                <div class="w-3 h-3 rounded-full bg-primary-500 mr-2"></div>
                <span class="text-sm text-gray-600">18-24 (25%)</span>
              </div>
              <div class="flex items-center">
                <div class="w-3 h-3 rounded-full bg-primary-300 mr-2"></div>
                <span class="text-sm text-gray-600">25-34 (30%)</span>
              </div>
              <div class="flex items-center">
                <div class="w-3 h-3 rounded-full bg-primary-400 mr-2"></div>
                <span class="text-sm text-gray-600">35-44 (20%)</span>
              </div>
              <div class="flex items-center">
                <div class="w-3 h-3 rounded-full bg-primary-600 mr-2"></div>
                <span class="text-sm text-gray-600">45+ (25%)</span>
              </div>
            </div>
          </div>
        </div>

        <!-- Active Elections and Recent Activity -->
        <div class="grid grid-cols-1 lg:grid-cols-2 gap-6 mb-6">
          <!-- Active Elections -->
          <div class="bg-white rounded-lg shadow-sm p-6 animate-on-scroll">
            <div class="flex justify-between items-center mb-4">
              <h2 class="text-lg font-semibold text-gray-800">
                Active Elections
              </h2>
              <button
                class="text-primary-600 hover:text-primary-700 text-sm font-medium transition-colors duration-200"
              >
                View All
              </button>
            </div>
            <div class="space-y-4">
              <!-- Election 1 -->
              <div
                class="border rounded-lg p-4 hover:shadow-md transition-shadow duration-200"
              >
                <div class="flex justify-between items-start">
                  <div>
                    <h3 class="font-medium text-gray-800">
                      University Student Council
                    </h3>
                    <p class="text-sm text-gray-500">Ends in 2 days</p>
                  </div>
                  <span
                    class="px-2 py-1 text-xs font-medium rounded-full bg-green-100 text-green-800"
                    >Active</span
                  >
                </div>
                <div class="mt-3">
                  <div class="flex justify-between text-sm mb-1">
                    <span class="text-gray-600">Progress</span>
                    <span class="text-gray-800 font-medium">68%</span>
                  </div>
                  <div class="w-full bg-gray-200 rounded-full h-2">
                    <div
                      class="bg-primary-500 h-2 rounded-full"
                      style="width: 68%"
                    ></div>
                  </div>
                </div>
                <div class="mt-3 flex justify-between items-center">
                  <div class="flex -space-x-2">
                    <div
                      class="h-6 w-6 rounded-full bg-gray-200 border-2 border-white flex items-center justify-center text-xs"
                    >
                      S
                    </div>
                    <div
                      class="h-6 w-6 rounded-full bg-gray-200 border-2 border-white flex items-center justify-center text-xs"
                    >
                      J
                    </div>
                    <div
                      class="h-6 w-6 rounded-full bg-gray-200 border-2 border-white flex items-center justify-center text-xs"
                    >
                      M
                    </div>
                    <div
                      class="h-6 w-6 rounded-full bg-gray-200 border-2 border-white flex items-center justify-center text-xs"
                    >
                      +4
                    </div>
                  </div>
                  <button
                    class="text-primary-600 hover:text-primary-700 text-sm font-medium transition-colors duration-200"
                  >
                    Manage
                  </button>
                </div>
              </div>

              <!-- Election 2 -->
              <div
                class="border rounded-lg p-4 hover:shadow-md transition-shadow duration-200"
              >
                <div class="flex justify-between items-start">
                  <div>
                    <h3 class="font-medium text-gray-800">
                      Community Association Board
                    </h3>
                    <p class="text-sm text-gray-500">Ends in 5 days</p>
                  </div>
                  <span
                    class="px-2 py-1 text-xs font-medium rounded-full bg-green-100 text-green-800"
                    >Active</span
                  >
                </div>
                <div class="mt-3">
                  <div class="flex justify-between text-sm mb-1">
                    <span class="text-gray-600">Progress</span>
                    <span class="text-gray-800 font-medium">42%</span>
                  </div>
                  <div class="w-full bg-gray-200 rounded-full h-2">
                    <div
                      class="bg-primary-500 h-2 rounded-full"
                      style="width: 42%"
                    ></div>
                  </div>
                </div>
                <div class="mt-3 flex justify-between items-center">
                  <div class="flex -space-x-2">
                    <div
                      class="h-6 w-6 rounded-full bg-gray-200 border-2 border-white flex items-center justify-center text-xs"
                    >
                      R
                    </div>
                    <div
                      class="h-6 w-6 rounded-full bg-gray-200 border-2 border-white flex items-center justify-center text-xs"
                    >
                      T
                    </div>
                    <div
                      class="h-6 w-6 rounded-full bg-gray-200 border-2 border-white flex items-center justify-center text-xs"
                    >
                      +3
                    </div>
                  </div>
                  <button
                    class="text-primary-600 hover:text-primary-700 text-sm font-medium transition-colors duration-200"
                  >
                    Manage
                  </button>
                </div>
              </div>

              <!-- Election 3 -->
              <div
                class="border rounded-lg p-4 hover:shadow-md transition-shadow duration-200"
              >
                <div class="flex justify-between items-start">
                  <div>
                    <h3 class="font-medium text-gray-800">
                      Corporate Annual Meeting
                    </h3>
                    <p class="text-sm text-gray-500">Ends in 8 days</p>
                  </div>
                  <span
                    class="px-2 py-1 text-xs font-medium rounded-full bg-yellow-100 text-yellow-800"
                    >Pending</span
                  >
                </div>
                <div class="mt-3">
                  <div class="flex justify-between text-sm mb-1">
                    <span class="text-gray-600">Progress</span>
                    <span class="text-gray-800 font-medium">12%</span>
                  </div>
                  <div class="w-full bg-gray-200 rounded-full h-2">
                    <div
                      class="bg-primary-500 h-2 rounded-full"
                      style="width: 12%"
                    ></div>
                  </div>
                </div>
                <div class="mt-3 flex justify-between items-center">
                  <div class="flex -space-x-2">
                    <div
                      class="h-6 w-6 rounded-full bg-gray-200 border-2 border-white flex items-center justify-center text-xs"
                    >
                      A
                    </div>
                    <div
                      class="h-6 w-6 rounded-full bg-gray-200 border-2 border-white flex items-center justify-center text-xs"
                    >
                      B
                    </div>
                    <div
                      class="h-6 w-6 rounded-full bg-gray-200 border-2 border-white flex items-center justify-center text-xs"
                    >
                      +6
                    </div>
                  </div>
                  <button
                    class="text-primary-600 hover:text-primary-700 text-sm font-medium transition-colors duration-200"
                  >
                    Manage
                  </button>
                </div>
              </div>
            </div>
          </div>

          <!-- Recent Activity -->
          <div class="bg-white rounded-lg shadow-sm p-6 animate-on-scroll">
            <div class="flex justify-between items-center mb-4">
              <h2 class="text-lg font-semibold text-gray-800">
                Recent Activity
              </h2>
              <button
                class="text-primary-600 hover:text-primary-700 text-sm font-medium transition-colors duration-200"
              >
                View All
              </button>
            </div>
            <div class="space-y-4">
              <!-- Activity 1 -->
              <div class="flex items-start">
                <div
                  class="h-8 w-8 rounded-full bg-green-100 flex items-center justify-center mr-3 flex-shrink-0"
                >
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    class="h-4 w-4 text-green-600"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M9 12l2 2 4-4m6 2a9 9 0 11-18 0 9 9 0 0118 0z"
                    />
                  </svg>
                </div>
                <div>
                  <p class="text-sm text-gray-800">
                    <span class="font-medium">Sarah Johnson</span> cast a vote
                    in
                    <span class="font-medium">University Student Council</span>
                    election
                  </p>
                  <p class="text-xs text-gray-500 mt-1">2 minutes ago</p>
                </div>
              </div>

              <!-- Activity 2 -->
              <div class="flex items-start">
                <div
                  class="h-8 w-8 rounded-full bg-blue-100 flex items-center justify-center mr-3 flex-shrink-0"
                >
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    class="h-4 w-4 text-blue-600"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M12 6v6m0 0v6m0-6h6m-6 0H6"
                    />
                  </svg>
                </div>
                <div>
                  <p class="text-sm text-gray-800">
                    <span class="font-medium">Admin User</span> created a new
                    election
                    <span class="font-medium">Corporate Annual Meeting</span>
                  </p>
                  <p class="text-xs text-gray-500 mt-1">45 minutes ago</p>
                </div>
              </div>

              <!-- Activity 3 -->
              <div class="flex items-start">
                <div
                  class="h-8 w-8 rounded-full bg-yellow-100 flex items-center justify-center mr-3 flex-shrink-0"
                >
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    class="h-4 w-4 text-yellow-600"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M12 9v2m0 4h.01m-6.938 4h13.856c1.54 0 2.502-1.667 1.732-3L13.732 4c-.77-1.333-2.694-1.333-3.464 0L3.34 16c-.77 1.333.192 3 1.732 3z"
                    />
                  </svg>
                </div>
                <div>
                  <p class="text-sm text-gray-800">
                    <span class="font-medium">System</span> detected unusual
                    voting pattern in
                    <span class="font-medium">Community Association Board</span>
                    election
                  </p>
                  <p class="text-xs text-gray-500 mt-1">1 hour ago</p>
                </div>
              </div>

              <!-- Activity 4 -->
              <div class="flex items-start">
                <div
                  class="h-8 w-8 rounded-full bg-purple-100 flex items-center justify-center mr-3 flex-shrink-0"
                >
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    class="h-4 w-4 text-purple-600"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9"
                    />
                  </svg>
                </div>
                <div>
                  <p class="text-sm text-gray-800">
                    <span class="font-medium">System</span> sent reminder
                    notifications to 245 voters for
                    <span class="font-medium">University Student Council</span>
                    election
                  </p>
                  <p class="text-xs text-gray-500 mt-1">3 hours ago</p>
                </div>
              </div>

              <!-- Activity 5 -->
              <div class="flex items-start">
                <div
                  class="h-8 w-8 rounded-full bg-red-100 flex items-center justify-center mr-3 flex-shrink-0"
                >
                  <svg
                    xmlns="http://www.w3.org/2000/svg"
                    class="h-4 w-4 text-red-600"
                    fill="none"
                    viewBox="0 0 24 24"
                    stroke="currentColor"
                  >
                    <path
                      stroke-linecap="round"
                      stroke-linejoin="round"
                      stroke-width="2"
                      d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16"
                    />
                  </svg>
                </div>
                <div>
                  <p class="text-sm text-gray-800">
                    <span class="font-medium">Admin User</span> deleted an
                    expired election
                    <span class="font-medium"
                      >Neighborhood Watch Committee</span
                    >
                  </p>
                  <p class="text-xs text-gray-500 mt-1">5 hours ago</p>
                </div>
              </div>
            </div>
          </div>
        </div>

        <!-- Voter Management Preview -->
        <div class="bg-white rounded-lg shadow-sm p-6 mb-6 animate-on-scroll">
          <div class="flex justify-between items-center mb-4">
            <h2 class="text-lg font-semibold text-gray-800">
              Voter Management
            </h2>
            <div class="flex space-x-2">
              <button
                class="px-3 py-1.5 text-sm font-medium rounded-md bg-primary-600 text-white hover:bg-primary-700 transition-colors duration-200"
              >
                Add Voter
              </button>
              <button
                class="px-3 py-1.5 text-sm font-medium rounded-md border border-gray-300 text-gray-700 hover:bg-gray-50 transition-colors duration-200"
              >
                Import
              </button>
            </div>
          </div>
          <div class="overflow-x-auto">
            <table class="min-w-full divide-y divide-gray-200">
              <thead class="bg-gray-50">
                <tr>
                  <th
                    scope="col"
                    class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                  >
                    Name
                  </th>
                  <th
                    scope="col"
                    class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                  >
                    Email
                  </th>
                  <th
                    scope="col"
                    class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                  >
                    Status
                  </th>
                  <th
                    scope="col"
                    class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                  >
                    Elections
                  </th>
                  <th
                    scope="col"
                    class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider"
                  >
                    Last Activity
                  </th>
                  <th
                    scope="col"
                    class="px-6 py-3 text-right text-xs font-medium text-gray-500 uppercase tracking-wider"
                  >
                    Actions
                  </th>
                </tr>
              </thead>
              <tbody class="bg-white divide-y divide-gray-200">
                <!-- Voter 1 -->
                <tr class="hover:bg-gray-50 transition-colors duration-200">
                  <td class="px-6 py-4 whitespace-nowrap">
                    <div class="flex items-center">
                      <div
                        class="h-8 w-8 rounded-full bg-primary-100 flex items-center justify-center"
                      >
                        <span class="text-sm font-medium text-primary-600"
                          >SJ</span
                        >
                      </div>
                      <div class="ml-3">
                        <div class="text-sm font-medium text-gray-900">
                          Sarah Johnson
                        </div>
                      </div>
                    </div>
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap">
                    <div class="text-sm text-gray-500">
                      sarah.johnson@example.com
                    </div>
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap">
                    <span
                      class="px-2 py-1 text-xs font-medium rounded-full bg-green-100 text-green-800"
                      >Active</span
                    >
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    3
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    2 minutes ago
                  </td>
                  <td
                    class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium"
                  >
                    <button
                      class="text-primary-600 hover:text-primary-900 mr-3"
                    >
                      Edit
                    </button>
                    <button class="text-red-600 hover:text-red-900">
                      Delete
                    </button>
                  </td>
                </tr>

                <!-- Voter 2 -->
                <tr class="hover:bg-gray-50 transition-colors duration-200">
                  <td class="px-6 py-4 whitespace-nowrap">
                    <div class="flex items-center">
                      <div
                        class="h-8 w-8 rounded-full bg-primary-100 flex items-center justify-center"
                      >
                        <span class="text-sm font-medium text-primary-600"
                          >MC</span
                        >
                      </div>
                      <div class="ml-3">
                        <div class="text-sm font-medium text-gray-900">
                          Michael Chen
                        </div>
                      </div>
                    </div>
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap">
                    <div class="text-sm text-gray-500">
                      michael.chen@example.com
                    </div>
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap">
                    <span
                      class="px-2 py-1 text-xs font-medium rounded-full bg-green-100 text-green-800"
                      >Active</span
                    >
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    2
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    1 hour ago
                  </td>
                  <td
                    class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium"
                  >
                    <button
                      class="text-primary-600 hover:text-primary-900 mr-3"
                    >
                      Edit
                    </button>
                    <button class="text-red-600 hover:text-red-900">
                      Delete
                    </button>
                  </td>
                </tr>

                <!-- Voter 3 -->
                <tr class="hover:bg-gray-50 transition-colors duration-200">
                  <td class="px-6 py-4 whitespace-nowrap">
                    <div class="flex items-center">
                      <div
                        class="h-8 w-8 rounded-full bg-primary-100 flex items-center justify-center"
                      >
                        <span class="text-sm font-medium text-primary-600"
                          >ER</span
                        >
                      </div>
                      <div class="ml-3">
                        <div class="text-sm font-medium text-gray-900">
                          Emily Rodriguez
                        </div>
                      </div>
                    </div>
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap">
                    <div class="text-sm text-gray-500">
                      emily.rodriguez@example.com
                    </div>
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap">
                    <span
                      class="px-2 py-1 text-xs font-medium rounded-full bg-yellow-100 text-yellow-800"
                      >Pending</span
                    >
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    1
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    2 days ago
                  </td>
                  <td
                    class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium"
                  >
                    <button
                      class="text-primary-600 hover:text-primary-900 mr-3"
                    >
                      Edit
                    </button>
                    <button class="text-red-600 hover:text-red-900">
                      Delete
                    </button>
                  </td>
                </tr>

                <!-- Voter 4 -->
                <tr class="hover:bg-gray-50 transition-colors duration-200">
                  <td class="px-6 py-4 whitespace-nowrap">
                    <div class="flex items-center">
                      <div
                        class="h-8 w-8 rounded-full bg-primary-100 flex items-center justify-center"
                      >
                        <span class="text-sm font-medium text-primary-600"
                          >JD</span
                        >
                      </div>
                      <div class="ml-3">
                        <div class="text-sm font-medium text-gray-900">
                          John Doe
                        </div>
                      </div>
                    </div>
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap">
                    <div class="text-sm text-gray-500">
                      john.doe@example.com
                    </div>
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap">
                    <span
                      class="px-2 py-1 text-xs font-medium rounded-full bg-gray-100 text-gray-800"
                      >Inactive</span
                    >
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    0
                  </td>
                  <td class="px-6 py-4 whitespace-nowrap text-sm text-gray-500">
                    1 week ago
                  </td>
                  <td
                    class="px-6 py-4 whitespace-nowrap text-right text-sm font-medium"
                  >
                    <button
                      class="text-primary-600 hover:text-primary-900 mr-3"
                    >
                      Edit
                    </button>
                    <button class="text-red-600 hover:text-red-900">
                      Delete
                    </button>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <div class="mt-4 flex items-center justify-between">
            <div class="text-sm text-gray-500">Showing 4 of 1,285 voters</div>
            <div class="flex space-x-1">
              <button
                class="px-3 py-1 text-sm rounded-md border border-gray-300 text-gray-700 hover:bg-gray-50 transition-colors duration-200"
              >
                Previous
              </button>
              <button
                class="px-3 py-1 text-sm rounded-md bg-primary-50 border border-primary-300 text-primary-700 hover:bg-primary-100 transition-colors duration-200"
              >
                1
              </button>
              <button
                class="px-3 py-1 text-sm rounded-md border border-gray-300 text-gray-700 hover:bg-gray-50 transition-colors duration-200"
              >
                2
              </button>
              <button
                class="px-3 py-1 text-sm rounded-md border border-gray-300 text-gray-700 hover:bg-gray-50 transition-colors duration-200"
              >
                Next
              </button>
            </div>
          </div>
        </div>

        <!-- Quick Actions -->
        <div class="grid grid-cols-1 md:grid-cols-3 gap-4 mb-6">
          <!-- Create Election -->
          <div
            class="bg-white rounded-lg shadow-sm p-6 hover:shadow-md transition-shadow duration-200 animate-on-scroll"
          >
            <div class="flex items-center mb-4">
              <div
                class="h-10 w-10 rounded-full bg-primary-100 flex items-center justify-center mr-3 animate-pulse-slow"
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  class="h-5 w-5 text-primary-600"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M12 6v6m0 0v6m0-6h6m-6 0H6"
                  />
                </svg>
              </div>
              <h3 class="text-lg font-medium text-gray-800">Create Election</h3>
            </div>
            <p class="text-sm text-gray-600 mb-4">
              Set up a new election with custom settings, candidates, and voter
              eligibility.
            </p>
            <button
              class="w-full px-4 py-2 bg-primary-600 text-white rounded-md hover:bg-primary-700 transition-colors duration-200"
            >
              Create New
            </button>
          </div>

          <!-- Export Reports -->
          <div
            class="bg-white rounded-lg shadow-sm p-6 hover:shadow-md transition-shadow duration-200 animate-on-scroll"
          >
            <div class="flex items-center mb-4">
              <div
                class="h-10 w-10 rounded-full bg-green-100 flex items-center justify-center mr-3 animate-bounce-slow"
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  class="h-5 w-5 text-green-600"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M4 16v1a3 3 0 003 3h10a3 3 0 003-3v-1m-4-4l-4 4m0 0l-4-4m4 4V4"
                  />
                </svg>
              </div>
              <h3 class="text-lg font-medium text-gray-800">Export Reports</h3>
            </div>
            <p class="text-sm text-gray-600 mb-4">
              Generate and download detailed reports for elections, voters, and
              analytics.
            </p>
            <button
              class="w-full px-4 py-2 bg-green-600 text-white rounded-md hover:bg-green-700 transition-colors duration-200"
            >
              Export Data
            </button>
          </div>

          <!-- System Settings -->
          <div
            class="bg-white rounded-lg shadow-sm p-6 hover:shadow-md transition-shadow duration-200 animate-on-scroll"
          >
            <div class="flex items-center mb-4">
              <div
                class="h-10 w-10 rounded-full bg-purple-100 flex items-center justify-center mr-3 animate-spin-slow"
              >
                <svg
                  xmlns="http://www.w3.org/2000/svg"
                  class="h-5 w-5 text-purple-600"
                  fill="none"
                  viewBox="0 0 24 24"
                  stroke="currentColor"
                >
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z"
                  />
                  <path
                    stroke-linecap="round"
                    stroke-linejoin="round"
                    stroke-width="2"
                    d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"
                  />
                </svg>
              </div>
              <h3 class="text-lg font-medium text-gray-800">System Settings</h3>
            </div>
            <p class="text-sm text-gray-600 mb-4">
              Configure system preferences, security settings, and notification
              rules.
            </p>
            <button
              class="w-full px-4 py-2 bg-purple-600 text-white rounded-md hover:bg-purple-700 transition-colors duration-200"
            >
              Configure
            </button>
          </div>
        </div>
      </main>
    </div>

    <!-- JavaScript for animations and interactions -->
    <script>
      document.addEventListener("DOMContentLoaded", function () {
        // Chart animations
        const chartBars = document.querySelectorAll(".chart-bar");
        setTimeout(() => {
          chartBars.forEach((bar) => {
            const height = bar.style.height;
            bar.style.height = "0";
            setTimeout(() => {
              bar.style.height = height;
            }, 100);
          });
        }, 500);

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

        const animatedElements =
          document.querySelectorAll(".animate-on-scroll");
        animatedElements.forEach((element) => {
          observer.observe(element);
        });
      });
    </script>

  </body>
</html>
