<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
	<title>eSewa Payment</title>
	<script>
		async function updateForm() {
			const amountInput = document.getElementById('amount');
			const amount = amountInput.value;

			if (!amount) return;

			try {
				const response = await fetch('/payment-update-servlet?amount=' + encodeURIComponent(amount));
				if (!response.ok) {
					console.error('Failed to fetch payment data');
					return;
				}
				const data = await response.json();

				// Update hidden fields
				document.getElementById('tax_amount').value = data.taxAmount;
				document.getElementById('total_amount').value = data.totalAmount;
				document.getElementById('transaction_uuid').value = data.transactionUuid;
				document.getElementById('product_code').value = data.productCode;
				document.getElementById('product_service_charge').value = data.productServiceCharge;
				document.getElementById('product_delivery_charge').value = data.productDeliveryCharge;
				document.getElementById('success_url').value = data.successUrl;
				document.getElementById('failure_url').value = data.failureUrl;
				document.getElementById('signed_field_names').value = data.signedFieldNames;
				document.getElementById('signature').value = data.signature;

				// Update visible fields
				document.getElementById('displayAmount').textContent = data.amount;
				document.getElementById('displayTax').textContent = data.taxAmount;
				document.getElementById('displayTotal').textContent = data.totalAmount;

			} catch (err) {
				console.error('Error:', err);
			}
		}
	</script>
</head>

<body>
<h1>eSewa Payment</h1>

<form id="esewaForm" action="https://rc-epay.esewa.com.np/api/epay/main/v2/form" method="POST">
	<label for="amount">Enter Amount:</label>
	<input type="number" id="amount" name="amount" value="${paymentRequest.amount}" onchange="updateForm()" required />

	<p>You are paying: <span id="displayAmount">${paymentRequest.amount}</span></p>

	<input type="hidden" id="tax_amount" name="tax_amount" value="${paymentRequest.taxAmount}" />
	<input type="hidden" id="total_amount" name="total_amount" value="${paymentRequest.totalAmount}" />
	<input type="hidden" id="transaction_uuid" name="transaction_uuid" value="${paymentRequest.transactionUuid}" />
	<input type="hidden" id="product_code" name="product_code" value="${paymentRequest.productCode}" />
	<input type="hidden" id="product_service_charge" name="product_service_charge" value="${paymentRequest.productServiceCharge}" />
	<input type="hidden" id="product_delivery_charge" name="product_delivery_charge" value="${paymentRequest.productDeliveryCharge}" />
	<input type="hidden" id="success_url" name="success_url" value="${paymentRequest.successUrl}" />
	<input type="hidden" id="failure_url" name="failure_url" value="${paymentRequest.failureUrl}" />
	<input type="hidden" id="signed_field_names" name="signed_field_names" value="${paymentRequest.signedFieldNames}" />
	<input type="hidden" id="signature" name="signature" value="${paymentRequest.signature}" />

	<p>Tax: <span id="displayTax">${paymentRequest.taxAmount}</span></p>
	<p>Total: <span id="displayTotal">${paymentRequest.totalAmount}</span></p>

	<button type="submit">Proceed to eSewa Payment</button>
</form>
</body>
</html>
