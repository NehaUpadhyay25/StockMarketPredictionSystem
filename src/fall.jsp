<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Stock Prices Prediction</title>
</head>
<body>
	<center><h1>
	<%
	String symbol = (String) session.getAttribute("symbol");
	String output = (String) session.getAttribute("result");
	out.print("The stock for " +symbol+ " will Fall");
	%>
	</h1>
	</center>
</body>
</html>