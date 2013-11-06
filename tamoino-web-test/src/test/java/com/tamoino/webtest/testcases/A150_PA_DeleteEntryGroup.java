package com.tamoino.webtest.testcases;

import org.junit.Test;

import com.tamoino.webtest.Constants;
import com.tamoino.webtest.TamoinoTestCase;

public class A150_PA_DeleteEntryGroup extends TamoinoTestCase implements TestInput {

	@Test
	public void test() throws Exception {
		goToPage(Constants.Page.SIGNIN);
		fillIn("EMAIL", PA1_EMAIL);
		fillIn("PASSWORD", PA1_PASSWORD);
		clickAndWait(Constants.Button.SIGN_IN_BUTTON);
		assertPage(Constants.Page.MANAGEACCOUNT);

		assertElementPresent(Constants.Button.ADD_ENTRYGROUP_BUTTON.name());

		click(PA1_NEW_GROUP, Constants.Button._DELETE_ENTRYGROUP_BUTTON);
		assertStatusMessage("Entry group " + PA1_NEW_GROUP + " cannot be deleted because it still has entries");

		assertElementPresent(PA1_NEW_GROUP, Constants.Button._DELETE_ENTRYGROUP_BUTTON);

		click(PA1_NEW_PRIVATE_KEY2, Constants.Button._DELETE_ENTRY_BUTTON);
		assertStatusMessage("Entry " + PA1_NEW_PRIVATE_KEY2 + " has been deleted");

		click(PA1_NEW_GROUP, Constants.Button._DELETE_ENTRYGROUP_BUTTON);
		assertStatusMessage("Entry group " + PA1_NEW_GROUP + " has been deleted");

		// assertElementNotPresent("groupaidao_DELETE_ENTRYGROUP_BUTTON");

		goToPage(Constants.Page.SIGNOUT);
		fillIn("KEY", PA1_NEW_PRIVATE_KEY2);
		clickAndWait(Constants.Button.GO_BUTTON);
		assertStatusMessage("There is no entry for " + PA1_NEW_PRIVATE_KEY2);

	}

}
