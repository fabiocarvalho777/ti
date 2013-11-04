<%@page import="org.springframework.web.context.support.WebApplicationContextUtils"%>
<%@page import="com.tamoino.web.controller.EntryGroupController"%>
<%@page import="com.tamoino.web.controller.EntryController"%>
<%@page import="com.tamoino.web.controller.AccountController"%>
<%@page import="com.tamoino.web.controller.GoController"%>
<%@page import="org.springframework.context.ApplicationContext"%>
<%@page import="com.tamoino.web.RequestParameter"%>
<%@page import="org.springframework.web.bind.annotation.RequestParam"%>
<%@page import="com.tamoino.core.model.Account"%>
<%@page import="com.tamoino.web.SessionAttribute"%>
<%@page import="java.util.*" %>
<%
	Account account = (Account) session.getAttribute(SessionAttribute.ACCOUNT.name());
	String email = "";
	long accountId = -1;
	if(account != null) {
		email = account.getEmail();
		accountId = account.getId();
	}

	boolean DISPLAY_ENTRIES_LIST = true;

	String messageClass = (String) request.getAttribute(RequestParameter.MESSAGE_CLASS.name());
	messageClass = (messageClass == null ? "" : messageClass);
	String message = (String) request.getAttribute(RequestParameter.MESSAGE.name());
	message = (message == null ? "" : message);
	
	ApplicationContext beanFactory = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());
	GoController goController = (GoController) beanFactory.getBean("goController");
	AccountController accountController = (AccountController) beanFactory.getBean("accountController");
	EntryController entryController = (EntryController) beanFactory.getBean("entryController");
	EntryGroupController entryGroupController = (EntryGroupController) beanFactory.getBean("entryGroupController");
%>
<html>
	<head>
		<title>Tamoino</title>
		<script type="text/javascript" src="script.js"></script>
		<link type="text/css" rel="stylesheet" href="style.css">
		<link rel="shortcut icon" href="images/tamoino.ico" />
	</head>
	<body onload="onLoad();">
		<table class="topToolbar">
			<tr>
				<td rowspan="2" width="100px"><img src="images/tamoino.jpg"></td>
				<td width="150px"><p class="topToolbarLogo">Tamoino</p></td>
				<td>&nbsp;</td>
				<td width="400px"><p class="topToolbarButtons" id="toolbar"></p></td>
			</tr>
			<tr>
				<td style="padding-bottom: 32px"><p id="pageTitle" class="pageTitle">&nbsp;</p></td>
				<td><p id="statusMessage">&nbsp;</p></td>
				<td>
					<p id="loggedin" style="text-align: right">
						<%if(DISPLAY_ENTRIES_LIST) {%>
							<a href='entriesList.jsp' target='_blank' style="font-weight:normal; color:blue">words (dev mode)</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						<%}%>
						<%=email%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					</p>
				</td>
			</tr>
		</table>
		<br>
		<br>