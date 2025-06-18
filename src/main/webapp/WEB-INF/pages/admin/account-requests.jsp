<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page isELIgnored="false" %>

<%@ page import="com.voteSphere.model.*"%>
<%@ include file="../loader-animation.jsp" %>


<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>VoteSphere - Account Requests</title>
	<link rel="icon" src="${pageContext.request.contextPath}/resources/favicon.ico" type="image/x-icon" />

	<script src="https://cdn.tailwindcss.com"></script>
<link
	href="https://fonts.googleapis.com/css2?family=Poppins:wght@400;500;600;700&display=swap"
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
            sans: ["Poppins", "sans-serif"],
          },
        },
      },
    };
  </script>
</head>

<%
UnverifiedUser user = (UnverifiedUser) request.getAttribute("user");
if (user != null) {
%>
<body class="font-sans bg-gray-100 flex h-screen overflow-hidden ">
	<%@ include file="sidebar.jsp"%>
	<div
		class="flex-1 flex flex-col ml-0  lg:ml-64 transition-all duration-300 ease-in-out">
		<%@ include file="../navbar.jsp"%>
		<div class="p-8 overflow-y-auto  overflow-y-auto h-screen">
			<div class="bg-white py-2 px-3  rounded-lg shadow-md p-6 mx-auto ">
				<div class="flex justify-between items-center mb-6 ">
					<h1 class="text-2xl font-bold text-gray-800">Account Request
						Details</h1>


					<a href="/admin/user-approval/"
						class="bg-gray-100 text-gray-600 px-4 py-2 rounded-lg hover:bg-gray-200 transition-colors duration-200">Back
						to List</a>
				</div>
				<div class="grid grid-cols-1 md:grid-cols-2 gap-6 mb-6">
					<div>
						<span class="font-medium text-gray-700">First Name:</span>
						<%=user.getFirstName()%>
					</div>
					<div>
						<span class="font-medium text-gray-700">Last Name:</span>
						<%=user.getLastName()%>
					</div>
					<div>
						<span class="font-medium text-gray-700">Voter ID:</span>
						<%=user.getVoterId()%>
					</div>
					<div>
						<span class="font-medium text-gray-700">Email:</span>
						<%=user.getNotificationEmail()%>
					</div>
					<div>
						<span class="font-medium text-gray-700">Profile Image:</span> <a
							target="_blank"
							href="<%=user.getProfileImage()%>"
							class="text-primary-600 underline">View</a>
					</div>
					<div>
						<span class="font-medium text-gray-700">Image Holding
							Citizenship:</span> <a target="_blank"
							href="<%= user.getImageHoldingCitizenship() %>"
							class="text-primary-600 underline">View</a>
					</div>
					<div>
						<span class="font-medium text-gray-700">Voter Card Front:</span> <a
							target="_blank"
							href="<%=user.getVoterCardFront()%>"
							class="text-primary-600 underline">View</a>
					</div>

					<div>
						<span class="font-medium text-gray-700">Citizenship Front:</span>
						<a target="_blank"
							href="<%=user.getCitizenshipFront()%>"
							class="text-primary-600 underline">View</a>
					</div>
					<div>
						<span class="font-medium text-gray-700">Citizenship Back:</span> <a
							target="_blank"
							href="<%=user.getCitizenshipBack()%>"
							class="text-primary-600 underline">View</a>
					</div>
					<div>
						<span class="font-medium text-gray-700">Thumb Print:</span> <a
							target="_blank"
							href="<%=user.getThumbPrint()%>"
							class="text-primary-600 underline">View</a>
					</div>
					<div>
						<span class="font-medium text-gray-700">Date of Birth:</span>
						<%=user.getDob()%>
					</div>
					<div>
						<span class="font-medium text-gray-700">Gender:</span>
						<%=user.getGender()%>
					</div>
					<div>
						<span class="font-medium text-gray-700">Temporary Address:</span>
						<%=user.getTemporaryAddress()%>
					</div>
					<div>
						<span class="font-medium text-gray-700">Permanent Address:</span>
						<%=user.getPermanentAddress()%>
					</div>

					<div>
						<span class="font-medium text-gray-700">Request received:</span>
						<%=user.getCreatedAt()%>
					</div>
				</div>
				<div class="flex justify-end space-x-3">
					<form
						action="/admin/account-requests/accept/<%=user.getUnverifiedUserId()%>"
						method="post">
						<button type="submit"
							class="bg-green-600 text-white px-6 py-2 rounded-lg hover:bg-green-700 transition-colors duration-200">
							Accept</button>
					</form>
					<form
						action="/admin/account-requests/reject/<%=user.getUnverifiedUserId()%>"
						method="post">
						<button type="submit"
							class="bg-red-600 text-white px-6 py-2 rounded-lg hover:bg-red-700 transition-colors duration-200">
							Reject</button>
					</form>
				</div>
			</div>
		</div>
	</div>
	<script>
      document.addEventListener("DOMContentLoaded", function () {
        fetch("sidebar.html")
          .then((response) => response.text())
          .then((data) => {
            document.getElementById("sidebar-container").innerHTML = data;
          });
        fetch("navbar.html")
          .then((response) => response.text())
          .then((data) => {
            document.getElementById("navbar-container").innerHTML = data;
          });
      });
    </script>
</body>
<%
} else {
List<UnverifiedUser> unverifiedUsers = (List<UnverifiedUser>) request.getAttribute("unverifiedUsers");
%>
<body class="font-sans bg-gray-100 flex h-screen overflow-hidden">
	<%@ include file="sidebar.jsp"%>
	<div
		class="flex-1 flex flex-col ml-0 lg:ml-64 transition-all duration-300 ease-in-out">
		<%@ include file="../navbar.jsp"%>

		<div class=" overflow-y-auto h-screen pb-12">
			<div class="bg-white rounded-lg shadow-md p-6 mb-6">
			<div class="flex justify-between items-center mb-6">
					<h1 class="text-2xl font-bold text-gray-800">Account Requests</h1>

					<div class="flex space-x-4">
						<div class="relative">

							<form id="searchForm" action="${pageContext.request.contextPath}/admin/donation/search" method="post">
								<input type="hidden" name="_csrf" value="${_csrf.token}"/>
								<input type="text"
									   name="searchInput"
									   id="searchInput"
									   value="${searchInput}"
									   placeholder="Search requests..."
									   class="pl-10 pr-4 py-2 border rounded-lg focus:outline-none focus:ring-2 focus:ring-primary-500"/>
								<svg class="absolute left-3 top-2.5 h-5 w-5 text-gray-400"
									 xmlns="http://www.w3.org/2000/svg"
									 fill="none"
									 viewBox="0 0 24 24"
									 stroke="currentColor">
									<path stroke-linecap="round"
										  stroke-linejoin="round"
										  stroke-width="2"
										  d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z"/>
								</svg>
							</form>



							<path stroke-linecap="round" stroke-linejoin="round"
								  stroke-width="2"
								  d="M21 21l-6-6m2-5a7 7 0 11-14 0 7 7 0 0114 0z" />
							</svg>
						</div>
					</div>
			</div>
				<div class="overflow-y-auto max-h-[calc(110vh-340px)] mb-2">
					<table class="w-full bg-gray-50 mb-4">
					<thead class="bg-gray-50 sticky top-0 z-10">
						<tr>
					<th scope="col"
									class="px-6 py-5 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Name</th>
								<th scope="col"
									class="px-6 py-5 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Email</th>
								<th scope="col"
									class="px-6 py-5 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Voter
									ID</th>
								<th scope="col"
									class="px-6 py-5 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
								<th scope="col"
									class="px-6 py-5 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
							</tr>
						</thead>
						<tbody class="bg-white divide-y divide-gray-200">
						<%
							if (unverifiedUsers != null && !unverifiedUsers.isEmpty()) {
								for (UnverifiedUser unverifiedUser : unverifiedUsers) {
							%>
							<tr>
								<td class="px-6  whitespace-nowrap"><%=unverifiedUser.getFirstName()%>
									<%=unverifiedUser.getLastName()%></td>
								<td class="px-6 py-5 whitespace-nowrap"><%=unverifiedUser.getNotificationEmail()%>
								</td>
								<td class="px-6 py-5 whitespace-nowrap"><%=unverifiedUser.getVoterId()%>
								</td>
								<td class="px-6 py-5 whitespace-nowrap">
									<%
									if (unverifiedUser.getIsVerified()) {
									%> <span
									class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-green-100 text-green-800">
										Verified </span> <%
 } else {
 %> <span
									class="px-2 inline-flex text-xs leading-5 font-semibold rounded-full bg-yellow-100 text-yellow-800">
										Pending </span> <%
 }
 %>
								</td>
								<td class="px-6 py-5 whitespace-nowrap text-sm font-medium">
									<a
									href="/admin/user-approval/view/<%=unverifiedUser.getUnverifiedUserId()%>"
									class="text-primary-600 hover:text-primary-900 mr-3">View</a>
								</td>
							</tr>
							<%
							}
							} else {
							%>
							<tr>
								<td colspan="5" class="px-6 py-5 text-center">No unverified
									users found</td>
							</tr>
							<%
							}
							%>
						</tbody>
					</table>
			</div>
		</div>
			</div>
		</div>
	</div>
</body>
<%
}
%>


<script>
	document.addEventListener('DOMContentLoaded', function() {
		const searchInput = document.getElementById('searchInput');
		const searchForm = document.getElementById('searchForm');
		let searchTimeout;
		let isEmptyState = false;
		let emptyTimer;

		// Restore focus if there's a search query
		if (searchInput.value) {
			searchInput.focus();
			searchInput.selectionStart = searchInput.selectionEnd = searchInput.value.length;
		}

		searchInput.addEventListener('input', function() {
			clearTimeout(searchTimeout);
			clearTimeout(emptyTimer);

			if (this.value.trim() === '') {
				// Field just became empty
				if (!isEmptyState) {
					isEmptyState = true;
					// Start 5 second countdown before refresh
					emptyTimer = setTimeout(() => {
						searchForm.submit();
					}, 5000); // 5 second delay for empty field
				}
			} else {
				// Field has content
				isEmptyState = false;
				// Normal search delay
				searchTimeout = setTimeout(() => {
					searchForm.submit();
				}, 500); // 0.5 second delay for typing
			}
		});

		// Maintain focus when field is empty
		searchInput.addEventListener('blur', function() {
			if (this.value.trim() === '' && isEmptyState) {
				// Only maintain focus if we're in the empty waiting period
				setTimeout(() => {
					this.focus();
				}, 10); // Small delay to ensure blur completes
			}
		});
	});
</script>
</html>