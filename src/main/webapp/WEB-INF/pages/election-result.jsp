<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Live Election Results</title>
<style>
body {
	font-family: Arial, sans-serif;
	background-color: #eef2f5;
	margin: 20px;
}

h2 {
	text-align: center;
	color: #2c3e50;
}

#status {
	text-align: center;
	font-weight: bold;
	margin-bottom: 10px;
}

#error {
	text-align: center;
	color: red;
	margin-top: 10px;
}

table {
	width: 90%;
	margin: auto;
	border-collapse: collapse;
	background-color: #fff;
	box-shadow: 0px 2px 10px rgba(0, 0, 0, 0.1);
}

th, td {
	padding: 12px;
	text-align: center;
	border-bottom: 1px solid #ddd;
}

th {
	background-color: #3498db;
	color: white;
}

tr:hover {
	background-color: #f1f1f1;
}
</style>
</head>
<body>

	<h2>Live Election Results</h2>
	<div id="status">Connecting to WebSocket...</div>
	<div id="error"></div>

	<table>
		<thead>
			<tr>
				<th>Candidate ID</th>
				<th>Candidate Name</th>
				<th>Party Name</th>
				<th>Party ID</th>
				<th>Vote Count</th>
				<th>Percentage (%)</th>
			</tr>
		</thead>
		<tbody id="resultsBody">
			<!-- Dynamic rows will be inserted here -->
		</tbody>
	</table>

	<script>
        const wsUrl = "ws://localhost:8080/voteSphere/election-results/2";
        const status = document.getElementById("status");
        const error = document.getElementById("error");
        const tableBody = document.getElementById("resultsBody");

        const socket = new WebSocket(wsUrl);

        socket.onopen = () => {
            status.textContent = "Connected to WebSocket!";
            status.style.color = "green";
        };

        socket.onclose = () => {
            status.textContent = "WebSocket connection closed.";
            status.style.color = "orange";
        };

        socket.onerror = () => {
            status.textContent = "WebSocket error!";
            status.style.color = "red";
            error.textContent = "Ensure WebSocket server is running.";
        };

        socket.onmessage = (event) => {
            try {
                const results = JSON.parse(event.data);
                updateTable(results);
            } catch (e) {
                console.error("Invalid JSON", e);
                error.textContent = "Error parsing result data.";
            }
        };

        function updateTable(results) {
            tableBody.innerHTML = "";

            if (!Array.isArray(results) || results.length === 0) {
                const row = tableBody.insertRow();
                const cell = row.insertCell();
                cell.colSpan = 6;
                cell.textContent = "No results available.";
                return;
            }

            results.forEach(result => {
                const row = tableBody.insertRow();
                row.insertCell().textContent = result.candidateId;
                row.insertCell().textContent = result.candidateName;
                row.insertCell().textContent = result.partyName;
                row.insertCell().textContent = result.partyId ?? "N/A";
                row.insertCell().textContent = result.voteCount;
                row.insertCell().textContent = result.percentage.toFixed(2);
            });
        }
    </script>

</body>
</html>
