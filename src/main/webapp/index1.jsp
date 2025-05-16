<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.voteSphere.model.Election" %>
<%@ page import="com.voteSphere.service.ElectionService" %>

<%
    // Fetch election list from ElectionService
    List<Election> elections = (List<Election>) ElectionService.getPastElections();
%>

<!DOCTYPE html>
<html>
<head>
    <title>Generate Vote Chart</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body class="bg-light">

<div class="container mt-5">
    <h2 class="mb-4">Election Vote Chart Generator</h2>

    <form id="chartForm" class="row g-3" action="voteChart" method="get" target="_blank">
        <div class="col-auto">
            <label for="electionId" class="form-label">Select Election</label>
            <select class="form-select" id="electionId" name="electionId" required>
                <option value="" disabled selected>Select an election</option>
                <%
                    for (Election election : elections) {
                        out.println("<option value='" + election.getElectionId() + "'>" + election.getName() + "</option>");
                    }
                %>
            </select>
        </div>
        <div class="col-auto">
            <button type="submit" class="btn btn-primary mb-3">Generate & Download Chart</button>
        </div>
    </form>
</div>

</body>
</html>
