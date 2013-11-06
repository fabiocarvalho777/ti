package com.tamoino.webtest.testcases;

import org.junit.Test;

import com.tamoino.webtest.Constants;
import com.tamoino.webtest.TamoinoTestCase;

public class A140_PA_AddEntryGroup extends TamoinoTestCase implements TestInput {

	@Test
	public void test() throws Exception {
		goToPage(Constants.Page.SIGNIN);
		fillIn("EMAIL", PA1_EMAIL);
		fillIn("PASSWORD", PA1_PASSWORD);
		clickAndWait(Constants.Button.SIGN_IN_BUTTON);
		assertPage(Constants.Page.MANAGEACCOUNT);

		assertElementPresent(Constants.Button.ADD_ENTRYGROUP_BUTTON.name());

		fillIn("NEW_GROUP_NAME", PA1_NEW_GROUP);
		click(Constants.Button.ADD_ENTRYGROUP_BUTTON);
		assertStatusMessage("Entry group " + PA1_NEW_GROUP + " has been added");

		fillIn("NEW_WORD", PA1_NEW_PRIVATE_WORD2);
		fillIn("NEW_URL", PA1_NEW_PRIVATE_URL2);
		select("NEW_GROUP_ID", PA1_NEW_GROUP);
		click(Constants.Button.ADD_ENTRY_BUTTON);
		assertStatusMessage("Entry " + PA1_NEW_PRIVATE_KEY2 + " has been added");

		goToPage(Constants.Page.SIGNOUT);
		fillIn("KEY", PA1_NEW_PRIVATE_KEY2);
		clickAndWait(Constants.Button.GO_BUTTON);
		assertLocation(PA1_NEW_PRIVATE_URL2);

	}

}
