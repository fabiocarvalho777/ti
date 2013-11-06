<%@page import="com.tamoino.web.FrontControllerServlet.Action"%>
<%@page import="com.tamoino.web.RequestParameter"%>
<%@page import="com.tamoino.web.Page"%>
<%@ include file="topToolbar.jsp" %>
		<br>
		<br>
		<br>
		<br>
		<form name="go_form" action="tifc.do" method="POST" onsubmit="return formValidation();">
			<input type="hidden" name="<%=RequestParameter.Action.name()%>" id="<%=RequestParameter.Action.name()%>" value="<%=Action.GO.name()%>">
			<table style="width: 600px">
				<tr><td>&nbsp;</td></tr>
				<tr><td>&nbsp;</td></tr>
				<tr><td><input type="text" class="goTextbox" id="<%=RequestParameter.KEY.name()%>" name="<%=RequestParameter.KEY.name()%>" maxlength="33"></td></tr>
				<tr><td>&nbsp;</td></tr>
				<tr><td><a id="GO_BUTTON" href="#" class="goButton" onclick="formSubmit('go_form');">Go</a></td></tr>
				<tr><td>&nbsp;</td></tr>
			</table>
		</form>
	</body>
	<script>
		function onLoad() {
			setPageTitle("<%=Page.getPage(Page.PageName.GO).getTitle()%>");
			setUpToolbar(<%=Page.getPage(Page.PageName.GO).getHTMLToolbar()%>);
			setFocus("<%=RequestParameter.KEY.name()%>");
			showStatusMessage("<%=messageClass%>", "<%=message%>");
		}
		
		function formatKey(keyElement) {
			if(keyElement.value == null) {
				keyElement.value = "";
			} 
			if(keyElement.value == "") {
				return;
			}
			
			// trim
			keyElement.value = trim(keyElement.value);
			// eliminate blank spaces in between
			keyElement.value = keyElement.value.replace(/\s+/g,"");
			// set to lowercase
			keyElement.value = keyElement.value.toLowerCase();
		}
		
		function formValidation() {
			var keyElement = document.getElementById("<%=RequestParameter.KEY.name()%>");
			formatKey(keyElement);
			if(keyElement.value != "") {
				return true;
			}
			setFocus("<%=RequestParameter.KEY.name()%>");
			return false;
		}
	</script>
</html>