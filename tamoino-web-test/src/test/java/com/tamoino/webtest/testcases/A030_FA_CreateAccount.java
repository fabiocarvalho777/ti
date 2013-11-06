package com.tamoino.webtest.testcases;

import org.junit.Test;

import com.tamoino.webtest.Constants;
import com.tamoino.webtest.TamoinoTestCase;

public class A030_FA_CreateAccount extends TamoinoTestCase implements TestInput {

	@Test
	public void test() throws Exception {
		goToPage(Constants.Page.CREATEACCOUNT);
		select("ACCOUNT_TYPE", "FREE");
		fillIn("EMAIL", FA1_EMAIL);
		fillIn("PASSWORD", FA1_PASSWORD);
		fillIn("PASSWORD_check", FA1_PASSWORD);
		fillIn("GROUP_NAME", FA1_GROUP);
		click(Constants.Button.CREATE_ACCOUNT_BUTTON);
		assertStatusMessage("Account " + FA1_EMAIL + " has been created");

		assertPage(Constants.Page.SIGNIN);

		fillIn("EMAIL", FA1_EMAIL);
		fillIn("PASSWORD", FA1_PASSWORD);
		click(Constants.Button.SIGN_IN_BUTTON);
		assertPage(Constants.Page.MANAGEACCOUNT);

		goToPage(Constants.Page.SIGNOUT);
	}

}
