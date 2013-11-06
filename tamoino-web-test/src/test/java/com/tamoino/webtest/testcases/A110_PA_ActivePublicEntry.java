package com.tamoino.webtest.testcases;

import org.junit.Test;

import com.tamoino.webtest.Constants;
import com.tamoino.webtest.TamoinoTestCase;

public class A110_PA_ActivePublicEntry extends TamoinoTestCase implements TestInput  {

	@Test
	public void test() throws Exception {
		goToPage(Constants.Page.SIGNIN);
		fillIn("EMAIL", PA1_EMAIL);
		fillIn("PASSWORD", PA1_PASSWORD);
		click(Constants.Button.SIGN_IN_BUTTON);
		assertPage(Constants.Page.MANAGEACCOUNT);
		
		assertElementPresent(Constants.Button.ADD_ENTRYGROUP_BUTTON.name());

		// TODO Verify the check-box is checked at this moment
		click(PA1_NEW_PUBLIC_KEY, Constants.Checkbox._ACTIVE_ENTRY_CHECKBOX);
		// TODO Verify the check-box is NOT checked at this moment
		goToPage(Constants.Page.SIGNOUT);
		fillIn("KEY", PA1_NEW_PUBLIC_KEY);
		clickAndWait(Constants.Button.GO_BUTTON);
		assertStatusMessage("There is no entry for "+PA1_NEW_PUBLIC_KEY);

		goToPage(Constants.Page.SIGNIN);
		fillIn("EMAIL", PA1_EMAIL);
		fillIn("PASSWORD", PA1_PASSWORD);
		click(Constants.Button.SIGN_IN_BUTTON);
		assertPage(Constants.Page.MANAGEACCOUNT);

		// TODO Verify the check-box is NOT checked at this moment
		click(PA1_NEW_PUBLIC_KEY, Constants.Checkbox._ACTIVE_ENTRY_CHECKBOX);
		// TODO Verify the check-box is checked at this moment
		goToPage(Constants.Page.SIGNOUT);
		fillIn("KEY", PA1_NEW_PUBLIC_KEY);
		clickAndWait(Constants.Button.GO_BUTTON);
		assertLocation(PA1_NEW_PUBLIC_URL);

	}

}
