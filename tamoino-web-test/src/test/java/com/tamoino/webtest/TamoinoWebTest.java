package com.tamoino.webtest;

import junit.framework.Test;
import junit.framework.TestSuite;

import com.tamoino.webtest.testcases.A010_WhatIs;
import com.tamoino.webtest.testcases.A020_Go;
import com.tamoino.webtest.testcases.A030_FA_CreateAccount;
import com.tamoino.webtest.testcases.A040_FA_AddEntry;
import com.tamoino.webtest.testcases.A050_FA_ActiveEntry;
import com.tamoino.webtest.testcases.A060_FA_DeleteEntry;
import com.tamoino.webtest.testcases.A070_PA_CreateAccount;
import com.tamoino.webtest.testcases.A080_PA_AddPrivateEntry;
import com.tamoino.webtest.testcases.A090_PA_AddPublicEntry;
import com.tamoino.webtest.testcases.A100_PA_ActivePrivateEntry;
import com.tamoino.webtest.testcases.A110_PA_ActivePublicEntry;
import com.tamoino.webtest.testcases.A120_PA_DeletePrivateEntry;
import com.tamoino.webtest.testcases.A130_PA_DeletePublicEntry;
import com.tamoino.webtest.testcases.A140_PA_AddEntryGroup;
import com.tamoino.webtest.testcases.A150_PA_DeleteEntryGroup;

/**
 * Automation Functional Test
 * 
 * @author fabio
 * 
 */
public class TamoinoWebTest {

	public static Test suite() {
		TestSuite suite = new TestSuite();

		suite.addTestSuite(A010_WhatIs.class);
		suite.addTestSuite(A020_Go.class);
		suite.addTestSuite(A030_FA_CreateAccount.class);
		suite.addTestSuite(A040_FA_AddEntry.class);
		suite.addTestSuite(A050_FA_ActiveEntry.class);
		suite.addTestSuite(A060_FA_DeleteEntry.class);
		suite.addTestSuite(A070_PA_CreateAccount.class);
		suite.addTestSuite(A080_PA_AddPrivateEntry.class);
		suite.addTestSuite(A090_PA_AddPublicEntry.class);
		suite.addTestSuite(A100_PA_ActivePrivateEntry.class);
		suite.addTestSuite(A110_PA_ActivePublicEntry.class);
		suite.addTestSuite(A120_PA_DeletePrivateEntry.class);
		suite.addTestSuite(A130_PA_DeletePublicEntry.class);
		suite.addTestSuite(A140_PA_AddEntryGroup.class);
		suite.addTestSuite(A150_PA_DeleteEntryGroup.class);

		return suite;
	}

	public static void main(String[] args) {
		junit.textui.TestRunner.run(suite());
	}

}
