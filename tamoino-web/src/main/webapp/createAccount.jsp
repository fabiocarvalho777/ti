<%@page import="com.tamoino.core.model.Account.AccountType"%>
<%@page import="com.tamoino.web.FrontControllerServlet.Action"%>
<%@page import="com.tamoino.web.RequestParameter"%>
<%@page import="com.tamoino.web.Page"%>
<%@ include file="topToolbar.jsp" %>
<%
	StringBuilder sb = new StringBuilder();
	for(AccountType accountType : AccountType.externalValues()) {
		sb.append("<option>");
		sb.append(accountType.name());
		sb.append("</option>");
	}
	String accountTypeNamesOptions = sb.toString();
%>
		<br>
		<br>
		<form name="create_account_form" action="tifc.do" method="POST" onsubmit="return formValidation();">
			<input type="hidden" name="<%=RequestParameter.Action.name()%>" id="<%=RequestParameter.Action.name()%>" value="<%=Action.CREATE_ACCOUNT.name()%>">
			<table style="width: 400px;">
				<tr><td colspan="2">&nbsp;</td></tr>
				<tr><td><p>Account type:<p></td><td><select id="<%=RequestParameter.ACCOUNT_TYPE.name()%>" name="<%=RequestParameter.ACCOUNT_TYPE.name()%>" class="formField"><%=accountTypeNamesOptions%></select></td></tr>
				<tr><td><p>E-mail:<p></td><td><input type="text" id="<%=RequestParameter.EMAIL.name()%>" name="<%=RequestParameter.EMAIL.name()%>" maxlength="50" class="formField"></td></tr>
				<tr><td><p>Password:<p></td><td><input type="password" id="<%=RequestParameter.PASSWORD.name()%>" name="<%=RequestParameter.PASSWORD.name()%>" maxlength="10" class="formField"></td></tr>
				<tr><td><p>Retype password:<p></td><td><input type="password" id="PASSWORD_check" maxlength="10" class="formField"></td></tr>
				<tr><td><p>Group name:<p></td><td><input type="text" id="<%=RequestParameter.GROUP_NAME.name()%>" name="<%=RequestParameter.GROUP_NAME.name()%>" maxlength="16" class="formField"></td></tr>
				<tr><td></td><td><a id="CREATE_ACCOUNT_BUTTON" href="#" class="button" onclick="return createAccountFormValidation();">Create account</a></td></tr>
				<tr><td colspan="2">&nbsp;</td></tr>
			</table>
		</form>
	</body>
	<script>
		function onLoad() {
			setPageTitle("<%=Page.getPage(Page.PageName.CREATEACCOUNT).getTitle()%>");
			setUpToolbar(<%=Page.getPage(Page.PageName.CREATEACCOUNT).getHTMLToolbar()%>);
			setFocus("<%=RequestParameter.EMAIL.name()%>");
			showStatusMessage("<%=messageClass%>", "<%=message%>");
		}
		
		function createAccountFormValidation() {
			var email = document.getElementById("<%=RequestParameter.EMAIL.name()%>").value;
			var password = document.getElementById("<%=RequestParameter.PASSWORD.name()%>").value;
			var passwordCheck = document.getElementById("PASSWORD_check").value;
			var groupName = document.getElementById("<%=RequestParameter.GROUP_NAME.name()%>").value;

			if(isBlank(email) || isBlank(password) || isBlank(groupName)) {
				showErrorMessage("Fill in all the fields");
				return false;
			}
			if(!validateEmail(email)) {
				showErrorMessage("Invalid email");
				return false;
			}
			if(password.length < 6) {
				showErrorMessage("Password must have at least 6 characters");
				return false;
			}
			if(passwordCheck != password) {
				showErrorMessage("Passwords do not match");
				return false;
			}
			if(groupName.length < 2) {
				showErrorMessage("Password must have at least 6 characters");
				return false;
			}
			
			return formSubmit('create_account_form');
		}
	</script>
</html>