package com.tamoino.webtest.testcases;

import org.junit.Test;

import com.tamoino.webtest.Constants;
import com.tamoino.webtest.TamoinoTestCase;

public class A090_PA_AddPublicEntry extends TamoinoTestCase implements TestInput {

	@Test
	public void test() throws Exception {
		goToPage(Constants.Page.SIGNIN);
		fillIn("EMAIL", PA1_EMAIL);
		fillIn("PASSWORD", PA1_PASSWORD);
		click(Constants.Button.SIGN_IN_BUTTON);
		assertPage(Constants.Page.MANAGEACCOUNT);

		assertElementPresent(Constants.Button.ADD_ENTRYGROUP_BUTTON.name());

		fillIn("NEW_WORD", PA1_NEW_PUBLIC_WORD);
		fillIn("NEW_URL", PA1_NEW_PUBLIC_URL);
		select("NEW_GROUP_ID", "PUBLIC");
		click(Constants.Button.ADD_ENTRY_BUTTON);
		assertStatusMessage("Entry " + PA1_NEW_PUBLIC_KEY + " has been added");

		goToPage(Constants.Page.SIGNOUT);
		fillIn("KEY", PA1_NEW_PUBLIC_KEY);
		clickAndWait(Constants.Button.GO_BUTTON);
		assertLocation(PA1_NEW_PUBLIC_URL);

	}

}
