<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
            <label for="electionId" class="visually-hidden">Election ID</label>
            <input type="number" class="form-control" id="electionId" name="electionId" placeholder="Enter Election ID" required>
        </div>
        <div class="col-auto">
            <button type="submit" class="btn btn-primary mb-3">Generate & Download Chart</button>
        </div>
    </form>
</div>

</body>
</html>
