<%@ page isErrorPage="true" %>
<%@ page import="java.util.*" %>
<%
    String errorMessage = null;
    Enumeration<String> attrNames = request.getAttributeNames();

    while (attrNames.hasMoreElements()) {
        String attrName = attrNames.nextElement();
        if (attrName.toLowerCase().endsWith("error")) {
            Object val = request.getAttribute(attrName);
            if (val != null) {
                errorMessage = val.toString();
                break;
            }
        }
    }

    if (errorMessage == null) {
        errorMessage = "An unexpected error occurred. Please try again later.";
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>VoteSphere | Error</title>
    <script src="https://cdn.tailwindcss.com"></script>
    <script>
        tailwind.config = {
            theme: {
                extend: {
                    colors: {
                        votesphere: '#4F46E5',
                    },
                    animation: {
                        'fade-in': 'fadeIn 1s ease-out',
                    },
                    keyframes: {
                        fadeIn: {
                            '0%': { opacity: '0' },
                            '100%': { opacity: '1' },
                        },
                    }
                },
            },
        };
    </script>
</head>
<body class="bg-gradient-to-br from-gray-100 to-white min-h-screen flex items-center justify-center p-4">
<div class="bg-white border border-gray-200 rounded-3xl shadow-2xl p-10 max-w-2xl w-full animate-fade-in text-center">
    <!-- SVG Logo -->
    <div class="mb-6 flex justify-center">
        <svg xmlns="http://www.w3.org/2000/svg" version="1.1" width="100" height="100" viewBox="0 0 256 256">
            <g transform="scale(2.81)">
                <polygon points="45,3.02 6.25,69.13 83.75,69.13" fill="#4F46E5"/>
                <path d="M49.174,55.957c0,2.166-1.756,3.923-3.923,3.923h-0.502c-2.166,0-3.923-1.756-3.923-3.923s1.756-3.923,3.923-3.923h0.502 C47.418,52.034,49.174,53.79,49.174,55.957z M49.958,35.734c0,0.183-0.016,0.366-0.047,0.547l-1.746,10.105 c-0.266,1.54-1.602,2.666-3.166,2.666s-2.899-1.125-3.166-2.666l-1.746-10.105c-0.031-0.181-0.047-0.364-0.047-0.547v-4.839 c0-1.774,1.438-3.213,3.213-3.213h3.492c1.774,0,3.213,1.438,3.213,3.213V35.734z" fill="#FACC15"/>
            </g>
        </svg>
    </div>

    <!-- Error Message -->
    <h1 class="text-2xl font-semibold text-votesphere mb-4">Oops! Something went wrong.</h1>
    <p class="text-gray-700 mb-6"><%= errorMessage %></p>

    <!-- Back Button -->
    <a href="/" class="inline-block px-6 py-2 text-white bg-votesphere rounded-full hover:bg-indigo-600 transition-all duration-300">
        Go to Home
    </a>
</div>
</body>
</html>
