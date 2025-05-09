<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page import="java.util.List"%>
<%@ page import="com.voteSphere.model.*"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>VoteSphere - Account Requests</title>
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
          },
          fontFamily: {
            sans: ["Inter", "sans-serif"],
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
<body class="font-sans bg-gray-100 flex h-screen overflow-hidden">
	<%@ include file="sidebar.jsp"%>
	<div
		class="flex-1 flex flex-col ml-0 lg:ml-64 transition-all duration-300 ease-in-out">
		<%@ include file="navbar.jsp"%>
		<div class="p-8 overflow-y-auto">
			<div class="bg-white rounded-lg shadow-md p-6 mx-auto">
				<div class="flex justify-between items-center mb-6">
					<h1 class="text-2xl font-bold text-gray-800">Account Request
						Details</h1>
					<a href="account-requests.html"
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
							href="${pageContext.request.contextPath}/images/<%=user.getProfileImage()%>"
							class="text-primary-600 underline">View</a>
					</div>
					<div>
						<span class="font-medium text-gray-700">Image Holding
							Citizenship:</span> <a target="_blank"
							href="${pageContext.request.contextPath}/images/<%= user.getImageHoldingCitizenship() %>"
							class="text-primary-600 underline">View</a>
					</div>
					<div>
						<span class="font-medium text-gray-700">Voter Card Front:</span> <a
							target="_blank"
							href="${pageContext.request.contextPath}/images/<%=user.getVoterCardFront()%>"
							class="text-primary-600 underline">View</a>
					</div>
					<div>
						<span class="font-medium text-gray-700">Voter Card Back:</span> <a
							target="_blank"
							href="${pageContext.request.contextPath}/images/<%=user.getVoterCardBack()%>"
							class="text-primary-600 underline">View</a>
					</div>
					<div>
						<span class="font-medium text-gray-700">Citizenship Front:</span>
						<a target="_blank"
							href="${pageContext.request.contextPath}/images/<%=user.getCitizenshipFront()%>"
							class="text-primary-600 underline">View</a>
					</div>
					<div>
						<span class="font-medium text-gray-700">Citizenship Back:</span> <a
							target="_blank"
							href="${pageContext.request.contextPath}/images/<%=user.getCitizenshipBack()%>"
							class="text-primary-600 underline">View</a>
					</div>
					<div>
						<span class="font-medium text-gray-700">Thumb Print:</span> <a
							target="_blank"
							href="${pageContext.request.contextPath}/images/<%=user.getThumbPrint()%>"
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
						action="/voteSphere/admin/account-requests?action=approve&id=<%=user.getUnverifiedUserId()%>"
						method="post">
						<button type="submit"
							class="bg-green-600 text-white px-6 py-2 rounded-lg hover:bg-green-700 transition-colors duration-200">
							Accept</button>
					</form>
					<form
						action="/voteSphere/admin/account-requests?action=reject&id=<%=user.getUnverifiedUserId()%>"
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
		<%@ include file="navbar.jsp"%>

		<div class="p-8 overflow-y-auto">
			<div class="bg-white rounded-lg shadow-md p-6">
				<div class="flex justify-between items-center mb-6">
					<h1 class="text-2xl font-bold text-gray-800">Account Requests</h1>
				</div>
				<div class="overflow-x-auto">
					<table class="min-w-full divide-y divide-gray-200">
						<thead class="bg-gray-50">
							<tr>
								<th scope="col"
									class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Name</th>
								<th scope="col"
									class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Email</th>
								<th scope="col"
									class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Voter
									ID</th>
								<th scope="col"
									class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Status</th>
								<th scope="col"
									class="px-6 py-3 text-left text-xs font-medium text-gray-500 uppercase tracking-wider">Actions</th>
							</tr>
						</thead>
						<tbody class="bg-white divide-y divide-gray-200">
							<%
							if (unverifiedUsers != null && !unverifiedUsers.isEmpty()) {
								for (UnverifiedUser unverifiedUser : unverifiedUsers) {
							%>
							<tr>
								<td class="px-6 py-4 whitespace-nowrap"><%=unverifiedUser.getFirstName()%>
									<%=unverifiedUser.getLastName()%></td>
								<td class="px-6 py-4 whitespace-nowrap"><%=unverifiedUser.getNotificationEmail()%>
								</td>
								<td class="px-6 py-4 whitespace-nowrap"><%=unverifiedUser.getVoterId()%>
								</td>
								<td class="px-6 py-4 whitespace-nowrap">
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
								<td class="px-6 py-4 whitespace-nowrap text-sm font-medium">
									<a
									href="/voteSphere/admin/account-requests?action=view&id=<%=unverifiedUser.getUnverifiedUserId()%>"
									class="text-primary-600 hover:text-primary-900 mr-3">View</a>
								</td>
							</tr>
							<%
							}
							} else {
							%>
							<tr>
								<td colspan="5" class="px-6 py-4 text-center">No unverified
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
</body>
<%
}
%>
</html>