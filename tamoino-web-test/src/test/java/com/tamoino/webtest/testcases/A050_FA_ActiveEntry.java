package com.tamoino.webtest.testcases;

import org.junit.Test;

import com.tamoino.webtest.Constants;
import com.tamoino.webtest.TamoinoTestCase;

public class A050_FA_ActiveEntry extends TamoinoTestCase implements TestInput {

	@Test
	public void test() throws Exception {
		goToPage(Constants.Page.SIGNIN);
		fillIn("EMAIL", FA1_EMAIL);
		fillIn("PASSWORD", FA1_PASSWORD);
		click(Constants.Button.SIGN_IN_BUTTON);
		assertPage(Constants.Page.MANAGEACCOUNT);

		// assertElementNotPresent("ADD_ENTRYGROUP_BUTTON");

		// TODO Verify the check-box is checked at this moment
		click(FA1_NEW_KEY, Constants.Checkbox._ACTIVE_ENTRY_CHECKBOX);
		// TODO Verify the check-box is NOT checked at this moment
		goToPage(Constants.Page.SIGNOUT);
		fillIn("KEY", FA1_NEW_KEY);
		clickAndWait(Constants.Button.GO_BUTTON);
		assertStatusMessage("There is no entry for " + FA1_NEW_KEY);

		goToPage(Constants.Page.SIGNIN);
		fillIn("EMAIL", FA1_EMAIL);
		fillIn("PASSWORD", FA1_PASSWORD);
		click(Constants.Button.SIGN_IN_BUTTON);
		assertPage(Constants.Page.MANAGEACCOUNT);

		// TODO Verify the check-box is NOT checked at this moment
		click(FA1_NEW_KEY, Constants.Checkbox._ACTIVE_ENTRY_CHECKBOX);
		// TODO Verify the check-box is checked at this moment
		goToPage(Constants.Page.SIGNOUT);
		fillIn("KEY", FA1_NEW_KEY);
		clickAndWait(Constants.Button.GO_BUTTON);
		assertLocation(FA1_NEW_URL);

	}

}
