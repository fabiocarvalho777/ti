package com.tamoino.webtest.testcases;

import org.junit.Test;

import com.tamoino.webtest.Constants;
import com.tamoino.webtest.TamoinoTestCase;

public class A040_FA_AddEntry extends TamoinoTestCase implements TestInput  {

	@Test
	public void test() throws Exception {
		goToPage(Constants.Page.SIGNIN);
		fillIn("EMAIL", FA1_EMAIL);
		fillIn("PASSWORD", FA1_PASSWORD);
		click(Constants.Button.SIGN_IN_BUTTON);
		assertPage(Constants.Page.MANAGEACCOUNT);

//		assertElementNotPresent("ADD_ENTRYGROUP_BUTTON");

		fillIn("NEW_WORD", FA1_NEW_WORD);
		fillIn("NEW_URL", FA1_NEW_URL);
		click(Constants.Button.ADD_ENTRY_BUTTON);
		assertStatusMessage("Entry "+FA1_NEW_KEY+" has been added");

		goToPage(Constants.Page.SIGNOUT);
		fillIn("KEY", FA1_NEW_KEY);
		clickAndWait(Constants.Button.GO_BUTTON);
		assertLocation(FA1_NEW_URL);

	}

}
