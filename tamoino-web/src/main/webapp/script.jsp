function setPageTitle(title) {
	var titleElement = document.getElementById("pageTitle");
	titleElement.innerHTML = title;
}

function setUpToolbar(content) {
	var toolbar = document.getElementById("toolbar");
	toolbar.innerHTML = content;
}

function showErrorMessage(messageText) {
	showStatusMessage("errorMessage", messageText);
}

function showResultMessage(messageText) {
	showStatusMessage("resultMessage", messageText);
}

function showStatusMessage(messageClass, messageText) {
	if(messageClass == "" || messageText == "") {
		clearStatusMessage();
		return;
	}
	
	var statusMessage = document.getElementById("statusMessage");

	statusMessage.innerHTML = messageText;
	statusMessage.className = messageClass;
	statusMessage.style.visibility = 'visible';
	
	// Clean the message after 5 seconds
	setTimeout("clearStatusMessage()", 5000);
}

function clearStatusMessage() {
	var statusMessage = document.getElementById("statusMessage");
	statusMessage.style.visibility = 'hidden';
}

function setFocus(elementId) {
	var element = document.getElementById(elementId);
	element.focus();
}

function trim(string) {
	var newString = string.replace(/^\s\s*/, "").replace(/\s\s*$/, '');
	return newString;
}

function isBlank(string) {
	if(string == null || trim(string) == '') {
		return true;
	}
	return false;
}

function validateEmail(email) { 
    var re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;

    return re.test(email);
}

function formatURL(urlElement) {
	var url = urlElement.value;	
    var re = /^https?:\/\//i;
    
    if(!re.test(url)) {
    	url = "http://" + url;
    	urlElement.value = url;
    }
}

function validateURL(url) { 
	var re = /^(((ht|f){1}(tps?:[/][/]){1})|((www.){1}))[-a-zA-Z0-9@:%_\+.~#?&//=]+$/;
    
    return re.test(url);
}

function formSubmit(formName) {
	document.forms[formName].submit();
}
