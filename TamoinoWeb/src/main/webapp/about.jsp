<%@page import="com.tamoino.web.Page"%>
<%@ include file="topToolbar.jsp" %>
		<br>
		<br>
		<p id="aboutMessage">Tamoino is URL redirector website based on key words and groups, instead of random alphanumeric codes.</p>
	</body>
	<script>
		function onLoad() {
			setPageTitle("<%=Page.getPage(Page.PageName.ABOUT).getTitle()%>");
			setUpToolbar(<%=Page.getPage(Page.PageName.ABOUT).getHTMLToolbar()%>);
		}
	</script>
</html>