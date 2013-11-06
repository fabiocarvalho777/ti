<%@page import="java.util.List"%>
<%@include file="topToolbar.jsp" %>
<%
	List<String> list = goController.getAllActiveKeys();
	StringBuilder sb = new StringBuilder("<br>");
	for(String key : list) {
		sb.append("<p>");
		sb.append(key);
		sb.append("</p>");
	}
	String entriesList = sb.toString();
%>
<html>
	<head>
		<title>Tamoino keys</title>
		<link type="text/css" rel="stylesheet" href="style.css">
		<link rel="shortcut icon" href="images/tamoino.ico" />
	</head>
	<body>
		<%=entriesList%>		
	</body>
</html>