<%@page import="com.tamoino.core.model.EntryGroup.EntryGroupType"%>
<%@page import="com.tamoino.core.model.Account.AccountType"%>
<%@page import="com.tamoino.core.dto.EntrySimpleDTO"%>
<%@page import="com.tamoino.core.dto.EntryGroupDTO"%>
<%@page import="com.tamoino.web.RequestParameter"%>
<%@page import="com.tamoino.web.FrontControllerServlet.Action"%>
<%@page import="com.tamoino.web.SessionAttribute"%>
<%@page import="com.tamoino.web.Page"%>
<%@include file="topToolbar.jsp"%>
<%
	AccountType accountType = account.getType();
	boolean paidAccount = accountType.equals(AccountType.PAID);

	String groupsOptions = null;
	String rowsOfGroups = null;
	Map<Long, EntryGroupDTO> groupMap = entryGroupController.getEntryGroupDTOsMapByAccountId(accountId);

	EntryGroupDTO group = null;
	String groupName = null;
	String singleGroupName = null;
	long singleGroupId = -1;

	if(paidAccount) {
		EntryGroupDTO publicGroup = entryGroupController.getPublicGroupSimpleDTO();
		groupMap.put(publicGroup.getId(), publicGroup);
		
		// Building all rows of Groups and Group options
		StringBuilder rowsOfGroupsSB = new StringBuilder("");
		String groupRow;
		long groupId;

		String rowOfGroupFormat = 
			"<tr id='groupRow_%s' name='groupRow_%s'>" +
				"<td>%s</td>" +
				"<td><a id='%s_DELETE_ENTRYGROUP_BUTTON' href='#' class='operationButton' onclick='deleteGroup(\"%s\", \"%s\");'>delete</a></td>" +
			"</tr>";

		StringBuilder groupsOptionsSB = new StringBuilder();
		String buttonClass;
		boolean hasEntries = true;
		for (Map.Entry<Long, EntryGroupDTO> entry : groupMap.entrySet()) {
			// Building rows of ** My Groups **
			group = entry.getValue();
			groupId = group.getId();
			groupName = group.getName();
			if(group.getType().equals(EntryGroupType.PRIVATE)) {
				groupRow = String.format(rowOfGroupFormat, groupId, groupId, groupName, groupName, groupId, groupName);
				rowsOfGroupsSB.append(groupRow);
			}
			
			// Building groups options in ** My Entries **
			groupsOptionsSB.append("<option value='");
			groupsOptionsSB.append(groupId);
			groupsOptionsSB.append("'>");
			groupsOptionsSB.append(groupName);
			groupsOptionsSB.append("</option>");
		}
		rowsOfGroups = rowsOfGroupsSB.toString();
		groupsOptions = groupsOptionsSB.toString();
	} else {
		singleGroupId = entryGroupController.getSingleGroupId(accountId);
		EntryGroupDTO singleGroup = groupMap.get(singleGroupId);
		singleGroupName = singleGroup.getName();
	}
	
	// Building all rows of Entries
	long entryId;
	String word, key, url, row, active, activationDate, expirationDate;
	StringBuilder rowsOfEntriesSB = new StringBuilder("");
	
	List<EntrySimpleDTO> entryList = entryController.getEntrySimpleDTOsByAccountId(accountId);

	String rowOfEntryFormat = 
		"<tr id='entryRow_%s' name='entryRow_%s'>" +
			"<td>%s</td>" +
			"<td>%s</td>" +
			"<td><a href='%s' target='_blank'>%s</a></td>" +
			"<td>%s</td>" +
			"<td><input id='%s_ACTIVE_ENTRY_CHECKBOX' class='rowField' type='checkbox' %s onclick='activeEntry(\"%s\", \"%s\", this.checked);'></td>" +
			(paidAccount ? "<td>%s</td><td>%s</td>" : "") +
			"<td><a id='%s_DELETE_ENTRY_BUTTON'  href='#' class='operationButton' onclick='deleteEntry(\"%s\", \"%s\");'>delete</a></td>" +
		"</tr>";

	groupName = singleGroupName;
	for (EntrySimpleDTO entry : entryList) {
		entryId = entry.getId();
		word = entry.getWord();
		key = entry.getKey();
		url = entry.getURL();
		active = (entry.isActive() ? "checked" : "");

		if(paidAccount) {
			activationDate = "-";
			expirationDate = (entry.getExpirationDate() == null ? "-" : String.valueOf(entry.getExpirationDate()));
			group = groupMap.get(entry.getGroupId());
			groupName = group.getName();
			row = String.format(rowOfEntryFormat, entryId, entryId, word, key, url, url, groupName, key, active, entryId, key, activationDate, expirationDate, key, entryId, key);
		} else {
			row = String.format(rowOfEntryFormat, entryId, entryId, word, key, url, url, singleGroupName, key, active, entryId, key, key, entryId, key);
		}
		rowsOfEntriesSB.append(row);
	}
	String rowsOfEntries = rowsOfEntriesSB.toString();

%>
		<table class="layout">
			<%if(paidAccount) {%>
			<tr>
				<td>
					<form name="new_group_form" action="tifc.do" method="POST">
						<input type="hidden" name="<%=RequestParameter.Action.name()%>" id="<%=RequestParameter.Action.name()%>" value="<%=Action.ADD_GROUP.name()%>">
						<input type="hidden" id="<%=RequestParameter.GROUP_NAME.name()%>" name="<%=RequestParameter.GROUP_NAME.name()%>">
						<table title="My Groups" style="width: 270px">
							<tr><td colspan="2" class="title">My Groups</td></tr>
							<tr>
								<td class="header">group name</td>
								<td class="header">operation</td>
							</tr>
							<tr style="background-color: #99CCFF">
								<td><input class="rowField" type="text" id="<%=RequestParameter.NEW_GROUP_NAME%>" name="<%=RequestParameter.NEW_GROUP_NAME.name()%>" maxlength="16"></td>
								<td><a id="ADD_ENTRYGROUP_BUTTON" href="#" class="operationButton" onclick="newGroupFormValidation();">add</a></td>
							</tr>
							<%=rowsOfGroups%>
							<tr><td colspan="2">&nbsp;</td></tr>
						</table>
					</form>
				</td>
			</tr>
			<tr><td>&nbsp;<br>&nbsp;</td></tr>
			<%}%>
			<tr>
				<td>
					<form name="new_entry_form" action="tifc.do" method="POST">
						<input type="hidden" id="<%=RequestParameter.Action.name()%>" name="<%=RequestParameter.Action.name()%>" value="<%=Action.ADD_ENTRY.name()%>">
						<table title="My Entries" style="width: <%=(paidAccount?960:775)%>px">
							<tr><td colspan="<%=(paidAccount?7:5)%>" class="title">My Entries</td></tr>
							<tr>
								<td class="header">word</td>
								<td class="header">key</td>
								<td class="header">url</td>
								<td class="header">group</td>
								<td class="header">active</td>
<%if(paidAccount) {%>
								<td class="header">activation date</td>
								<td class="header">expiration date</td>
<%}%>
								<td class="header">operation</td>
							</tr>
							<tr style="background-color: #99CCFF">
								<td><input class="rowField" type="text" id="<%=RequestParameter.NEW_WORD%>" name="<%=RequestParameter.NEW_WORD.name()%>" maxlength="16" onchange="setKey()"></td>
								<td><input class="rowField" type="text" id="<%=RequestParameter.NEW_KEY%>" name="<%=RequestParameter.NEW_KEY.name()%>" maxlength="33" readonly="readonly"></td>
								<td><input class="rowField" style="width: 300px;" type="text" id="<%=RequestParameter.NEW_URL%>" name="<%=RequestParameter.NEW_URL.name()%>" maxlength="160"></td>
<%if(paidAccount) {%>
								<td><select class="rowField" id="<%=RequestParameter.NEW_GROUP_ID.name()%>" name="<%=RequestParameter.NEW_GROUP_ID.name()%>" onchange="setKey()"><%=groupsOptions%></select></td>
<%} else {%>
								<td><input type="hidden" id="<%=RequestParameter.NEW_GROUP_ID.name()%>" name="<%=RequestParameter.NEW_GROUP_ID.name()%>" value="<%=singleGroupId%>"><%=singleGroupName%><input type="hidden" id="<%=RequestParameter.NEW_GROUP_NAME%>" name="<%=RequestParameter.NEW_GROUP_NAME.name()%>" value="<%=singleGroupName%>"></td>
<%}%>
								<td><input class="rowField" style="width: 40px" type='checkbox' id='<%=RequestParameter.NEW_ACTIVE%>' name='<%=RequestParameter.NEW_ACTIVE.name()%>' checked></td>
<%if(paidAccount) {%>
								<td>-</td>
								<td>-</td>
<%}%>
								<td><a id="ADD_ENTRY_BUTTON" href="#" class="operationButton" onclick="newEntryFormValidation();">add</a></td>
							</tr>
							<%=rowsOfEntries%>
							<tr><td colspan="<%=(paidAccount?7:5)%>">&nbsp;</td></tr>
						</table>
					</form>
				</td>
			</tr>
		</table>

		<form name="group_deletion_form" action="tifc.do" method="POST">
			<input type="hidden" name="<%=RequestParameter.Action.name()%>" id="<%=RequestParameter.Action.name()%>" value="<%=Action.DELETE_GROUP.name()%>">
			<input type="hidden" name="<%=RequestParameter.GROUP_NAME.name()%>">
			<input type="hidden" name="<%=RequestParameter.GROUP_ID.name()%>">
		</form>
		<form name="entry_deletion_form" action="tifc.do" method="POST">
			<input type="hidden" name="<%=RequestParameter.Action.name()%>" id="<%=RequestParameter.Action.name()%>" value="<%=Action.DELETE_ENTRY.name()%>">
			<input type="hidden" name="<%=RequestParameter.KEY.name()%>">
			<input type="hidden" name="<%=RequestParameter.ENTRY_ID.name()%>">
		</form>
		<form name="entry_active_form" action="tifc.do" method="POST">
			<input type="hidden" name="<%=RequestParameter.Action.name()%>" id="<%=RequestParameter.Action.name()%>" value="<%=Action.ACTIVE_ENTRY.name()%>">
			<input type="hidden" name="<%=RequestParameter.KEY.name()%>">
			<input type="hidden" name="<%=RequestParameter.ENTRY_ID.name()%>">
			<input type="hidden" name="<%=RequestParameter.ACTIVE.name()%>">
		</form>
	</body>
	<script>
		function onLoad() {
			setPageTitle("<%=Page.getPage(Page.PageName.MANAGEACCOUNT).getTitle()%>");
			setUpToolbar(<%=Page.getPage(Page.PageName.MANAGEACCOUNT).getHTMLToolbar()%>);
			setFocus("<%=RequestParameter.NEW_KEY.name()%>");
			showStatusMessage("<%=messageClass%>", "<%=message%>");
		}
		
		function newGroupFormValidation() {
			setKey();
			
			var newGroupName = document.getElementById("<%=RequestParameter.NEW_GROUP_NAME.name()%>").value;
			
			var validationIsOk = true;
			
			if(isBlank(newGroupName)) {
				showErrorMessage("Fill in all the fields");
				validationIsOk = false;
			} else if(newGroupName.length < 2) {
				showErrorMessage("Group name must have at least 2 characters");
				validationIsOk = false;
			}

			setFocus("<%=RequestParameter.NEW_GROUP_NAME.name()%>");
			
			if(validationIsOk) {
				return formSubmit('new_group_form');
			}
			
			return false;			
		}

		function deleteGroup(groupId, groupName) {
			document.forms["group_deletion_form"]["<%=RequestParameter.GROUP_ID.name()%>"].value = groupId;
			document.forms["group_deletion_form"]["<%=RequestParameter.GROUP_NAME.name()%>"].value = groupName;
			document.forms["group_deletion_form"].submit();
		}
			
		function newEntryFormValidation() {
			var word = document.getElementById("<%=RequestParameter.NEW_WORD.name()%>").value;
			var urlElement = document.getElementById("<%=RequestParameter.NEW_URL.name()%>");
			var url = urlElement.value;
			
			var validationIsOk = true;
			
			if(isBlank(word) || isBlank(url)) {
				showErrorMessage("Fill in all the fields");
				validationIsOk = false;
			} else {
				formatURL(urlElement);
				url = urlElement.value;
				
				if(!validateURL(url)) {
					showErrorMessage("Invalid URL");
					validationIsOk = false;
				} else if(word.length < 2) {
					showErrorMessage("Word must have at least 2 characters");
					validationIsOk = false;
				}
			}

			setFocus("<%=RequestParameter.NEW_WORD.name()%>");
			
			if(validationIsOk) {
				return formSubmit('new_entry_form');
			}
			
			return false;			
		}
		
		function deleteEntry(entryId, key) {
			document.forms["entry_deletion_form"]["<%=RequestParameter.KEY.name()%>"].value = key;
			document.forms["entry_deletion_form"]["<%=RequestParameter.ENTRY_ID.name()%>"].value = entryId;
			document.forms["entry_deletion_form"].submit();
		}
		
		function activeEntry(entryId, key, active) {
			document.forms["entry_active_form"]["<%=RequestParameter.KEY.name()%>"].value = key;
			document.forms["entry_active_form"]["<%=RequestParameter.ENTRY_ID.name()%>"].value = entryId;
			document.forms["entry_active_form"]["<%=RequestParameter.ACTIVE.name()%>"].value = active;
			document.forms["entry_active_form"].submit();
		}
		
		function setKey() {
			var word = document.getElementById("<%=RequestParameter.NEW_WORD.name()%>").value;

			<%if(paidAccount) {%>
			var selectElement = document.getElementById("<%=RequestParameter.NEW_GROUP_ID.name()%>");
			var i = selectElement.selectedIndex;
			var groupName = selectElement.options[i].text;
			<%} else {%>
			var groupName = document.getElementById("<%=RequestParameter.NEW_GROUP_NAME.name()%>").value;
			<%}%>
			
			var key;
			if(groupName == 'PUBLIC') {
				key = word;
			} else {
				key = word + "@" + groupName;
			}
			document.getElementById("<%=RequestParameter.NEW_KEY.name()%>").value = key;
		}
		
	</script>
</html>