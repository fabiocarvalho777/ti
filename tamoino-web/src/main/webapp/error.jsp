<%@page import="com.tamoino.web.Page"%>
<%@ include file="topToolbar.jsp" %>
<%
	Exception exception = (Exception) request.getAttribute(RequestParameter.EXCEPTION.name());
	String errorMessage = exception.getMessage().replace("\n", "<br>");
%>
		<br>
		<br>
		<p>Tamoino has just exploded!! Take a look at the error message in the server log.</p>
		<br>
		<table style="width: 600px; text-align: left;">
			<tr><td>&nbsp;</td></tr>
			<tr><td><%=errorMessage%></td></tr>
			<tr><td>&nbsp;</td></tr>
		</table>
	</body>
	<script>
		function onLoad() {
			setPageTitle("<%=Page.getPage(Page.PageName.ERROR).getTitle()%>");
			setUpToolbar(<%=Page.getPage(Page.PageName.ERROR).getHTMLToolbar()%>);
		}
	</script>
</html>