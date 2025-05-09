<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%-- <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> --%>
<html>
<head>
<title>eSewa Payment</title>
<script>
        function updateForm() {
            const amount = document.getElementById('amount').value;
            if (!amount) return;
            
            fetch('/initiate-payment', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/x-www-form-urlencoded',
                },
                body: 'amount=' + encodeURIComponent(amount)
            })
            .then(response => response.text())
            .then(html => {
                document.getElementById('esewa-form-container').innerHTML = html;
            });
        }
    </script>
</head>
<body>
	<h1>eSewa Payment</h1>



	<div id="esewa-form-container">
		<c:if test="${not empty paymentRequest}">
			<form action="https://rc-epay.esewa.com.np/api/epay/main/v2/form"
				method="POST">
				<p>You are paying: ${paymentRequest.amount}</p>

				<input type="text" name="amount" value="${paymentRequest.amount}">
				<input type="text" name="tax_amount"
					value="${paymentRequest.taxAmount}"> <input type="text"
					name="total_amount" value="${paymentRequest.totalAmount}">
				<input type="text" name="transaction_uuid"
					value="${paymentRequest.transactionUuid}"> <input
					type="text" name="product_code"
					value="${paymentRequest.productCode}"> <input type="text"
					name="product_service_charge"
					value="${paymentRequest.productServiceCharge}"> <input
					type="hidden" name="product_delivery_charge"
					value="${paymentRequest.productDeliveryCharge}"> <input
					type="text" name="success_url" value="${paymentRequest.successUrl}">
				<input type="text" name="failure_url"
					value="${paymentRequest.failureUrl}"> <input type="text"
					name="signed_field_names"
					value="${paymentRequest.signedFieldNames}"> <input
					type="text" name="signature" value="${paymentRequest.signature}">

				<p>Amount: ${paymentRequest.amount}</p>
				<p>Tax: ${paymentRequest.taxAmount}</p>
				<p>Total: ${paymentRequest.totalAmount}</p>

				<button type="submit">Proceed to eSewa Payment</button>
			</form>
		</c:if>
	</div>
</body>
</html>