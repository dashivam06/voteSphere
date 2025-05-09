<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<html>
<head>
<title>eSewa Payment</title>
</head>
<body>
	<h1>Enter Payment Details</h1>

	<form action="${pageContext.request.contextPath}/initiate-payment"
		method="POST">
		<div>
			<label for="amount">Amount:</label> <input type="number" id="amount"
				name="amount" step="0.01" required>
		</div>

		<button type="submit">Proceed to eSewa Payment</button>
	</form>
</body>
</html>