package com.tamoino.webtest.testcases;

import org.junit.Test;

import com.tamoino.webtest.Constants;
import com.tamoino.webtest.TamoinoTestCase;

public class A010_WhatIs extends TamoinoTestCase  implements TestInput {

	@Test
	public void test() throws Exception {
		goToPage(Constants.Page.ABOUT);
		assertText("aboutMessage", ABOUT_TEXT);

	}

}
