package com.tamoino.webtest.testcases;

import org.junit.Test;

import com.tamoino.webtest.Constants;
import com.tamoino.webtest.TamoinoTestCase;

public class A020_Go extends TamoinoTestCase implements TestInput {

	@Test
	public void test() throws Exception {
		fillIn("KEY", KEY1);
		clickAndWait(Constants.Button.GO_BUTTON);
		assertLocation(URL1);

		goToBaseLocation();
		assertPage(Constants.Page.GO);

		fillIn("KEY", KEY2);
		clickAndWait(Constants.Button.GO_BUTTON);
		assertLocation(URL2);

	}

}
