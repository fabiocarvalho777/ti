package com.tamoino.webtest.testcases;

import org.junit.Test;

import com.tamoino.webtest.Constants;
import com.tamoino.webtest.TamoinoTestCase;

public class A070_PA_CreateAccount extends TamoinoTestCase implements TestInput {

	@Test
	public void test() throws Exception {
		goToPage(Constants.Page.CREATEACCOUNT);
		select("ACCOUNT_TYPE", "PAID");
		fillIn("EMAIL", PA1_EMAIL);
		fillIn("PASSWORD", PA1_PASSWORD);
		fillIn("PASSWORD_check", PA1_PASSWORD);
		fillIn("GROUP_NAME", PA1_GROUP);
		click(Constants.Button.CREATE_ACCOUNT_BUTTON);
		assertPage(Constants.Page.SIGNIN);

		assertStatusMessage("Account " + PA1_EMAIL + " has been created");

		fillIn("EMAIL", PA1_EMAIL);
		fillIn("PASSWORD", PA1_PASSWORD);
		clickAndWait(Constants.Button.SIGN_IN_BUTTON);
		assertPage(Constants.Page.MANAGEACCOUNT);

		assertElementPresent(Constants.Button.ADD_ENTRYGROUP_BUTTON.name());

		goToPage(Constants.Page.SIGNOUT);
	}

}
