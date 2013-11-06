<%@page import="com.tamoino.web.FrontControllerServlet.Action"%>
<%@page import="com.tamoino.web.RequestParameter"%>
<%@page import="com.tamoino.web.Page"%>
<%@include file="topToolbar.jsp" %>
		<br>
		<br>
		<form name="signin_form" action="tifc.do" method="POST">
			<input type="hidden" name="<%=RequestParameter.Action.name()%>" id="<%=RequestParameter.Action.name()%>" value="<%=Action.SIGN_IN.name()%>">
			<table style="width: 300px">
				<tr><td colspan="2">&nbsp;</td></tr>
				<tr><td><p>E-mail:<p></td><td><input type="text" id="<%=RequestParameter.EMAIL.name()%>" name="<%=RequestParameter.EMAIL.name()%>" maxlength="50" class="formField"></td></tr>
				<tr><td><p>Password:<p></td><td><input type="password" id="<%=RequestParameter.PASSWORD.name()%>" name="<%=RequestParameter.PASSWORD.name()%>" maxlength="10" class="formField"></td></tr>
				<tr><td></td><td><a id="SIGN_IN_BUTTON" href="#" class="button" onclick="formSubmit('signin_form');">Sign in</a></td></tr>
				<tr><td colspan="2">&nbsp;</td></tr>
			</table>
		</form>
	</body>
	<script>
		function onLoad() {
			setPageTitle("<%=Page.getPage(Page.PageName.SIGNIN).getTitle()%>");
			setUpToolbar(<%=Page.getPage(Page.PageName.SIGNIN).getHTMLToolbar()%>);
			setFocus("<%=RequestParameter.EMAIL.name()%>");
			showStatusMessage("<%=messageClass%>", "<%=message%>");
		}
	</script>
</html>