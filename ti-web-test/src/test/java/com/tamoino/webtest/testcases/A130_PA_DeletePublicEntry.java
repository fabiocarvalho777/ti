package com.tamoino.webtest.testcases;

import org.junit.Test;

import com.tamoino.webtest.Constants;
import com.tamoino.webtest.TamoinoTestCase;

public class A130_PA_DeletePublicEntry extends TamoinoTestCase implements TestInput {

	@Test
	public void test() throws Exception {
		goToPage(Constants.Page.SIGNIN);
		fillIn("EMAIL", PA1_EMAIL);
		fillIn("PASSWORD", PA1_PASSWORD);
		click(Constants.Button.SIGN_IN_BUTTON);
		assertPage(Constants.Page.MANAGEACCOUNT);

		assertElementPresent(Constants.Button.ADD_ENTRYGROUP_BUTTON.name());

		click(PA1_NEW_PUBLIC_KEY, Constants.Button._DELETE_ENTRY_BUTTON);
		assertStatusMessage("Entry " + PA1_NEW_PUBLIC_KEY + " has been deleted");

		goToPage(Constants.Page.SIGNOUT);
		fillIn("KEY", PA1_NEW_PUBLIC_KEY);
		clickAndWait(Constants.Button.GO_BUTTON);
		assertStatusMessage("There is no entry for " + PA1_NEW_PUBLIC_KEY);

	}

}
