<%@page import="com.WekaTest" %>
<%@ page session="true" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Stock Prices Prediction</title>
</head>
<body>
	<%
		String predict = new WekaTest().connectionTest(request.getParameter("stock_symbol")); 
		String symbol = (String)request.getParameter("stock_symbol");
		session.setAttribute("symbol",symbol);
	%>
	<center><h1>
	<%
		if(predict.equals("True")) {
			response.sendRedirect("rise.jsp");
		}
		else {
			response.sendRedirect("fall.jsp");
		} %>
	</h1>
	</center>
</body>
</html>