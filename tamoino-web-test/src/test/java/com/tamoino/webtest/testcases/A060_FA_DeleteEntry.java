package com.tamoino.webtest.testcases;

import org.junit.Test;

import com.tamoino.webtest.Constants;
import com.tamoino.webtest.TamoinoTestCase;

public class A060_FA_DeleteEntry extends TamoinoTestCase implements TestInput {

	@Test
	public void test() throws Exception {
		goToPage(Constants.Page.SIGNIN);
		fillIn("EMAIL", FA1_EMAIL);
		fillIn("PASSWORD", FA1_PASSWORD);
		click(Constants.Button.SIGN_IN_BUTTON);
		assertPage(Constants.Page.MANAGEACCOUNT);

		// assertElementNotPresent("ADD_ENTRYGROUP_BUTTON");

		click(FA1_NEW_KEY, Constants.Button._DELETE_ENTRY_BUTTON);
		assertStatusMessage("Entry " + FA1_NEW_KEY + " has been deleted");

		goToPage(Constants.Page.SIGNOUT);
		fillIn("KEY", FA1_NEW_KEY);
		clickAndWait(Constants.Button.GO_BUTTON);
		assertStatusMessage("There is no entry for " + FA1_NEW_KEY);

	}

}
