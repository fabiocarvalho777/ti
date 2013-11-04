package com.tamoino.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.openjpa.persistence.InvalidStateException;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tamoino.core.dto.AccountDTO;
import com.tamoino.core.model.Account;
import com.tamoino.core.model.Account.AccountType;

//FIXME JUnits must must remove everything the setup method created (cleanup method)
public class AccountFacadeTest {

	private static FacadeInterface facade;

	private static final String EMAIL = "testaccount@account.com";
	private static final String PASSWORD = "testaccoun";
	private static final String ENTRY_GROUP = "testaccount";

	@BeforeClass
	public static void setup() {
		facade = ApplicationContextHelper.getSingleton();

		AccountDTO newAccountDTO = new AccountDTO(AccountType.FREE, EMAIL, PASSWORD);
		facade.createAccount(newAccountDTO, ENTRY_GROUP);
	}

	@Test
	public void getAccountByCredentials() {
		Account account = facade.getAccountByCredentials(EMAIL, PASSWORD);

		assertNotNull(account);
		assertEquals(account.getEmail(), EMAIL);
		assertEquals(account.getPassword(), PASSWORD);
	}

	@Test
	public void createFreeAccount() {
		final String NEW_EMAIL = "admin@freeaccount.com";
		final String NEW_PASSWORD = "freeacc";
		final String NEW_GROUP = "freeaccount";

		createAccount(AccountType.FREE, NEW_EMAIL, NEW_PASSWORD, NEW_GROUP);
	}

	@Test
	public void createPaidAccount() {
		final String NEW_EMAIL = "admin@paidaccount.com";
		final String NEW_PASSWORD = "paidacc";
		final String NEW_GROUP = "paidaccount";

		createAccount(AccountType.PAID, NEW_EMAIL, NEW_PASSWORD, NEW_GROUP);
	}

	// Used by Hibernate
	// @Test(expected = PersistenceException.class)
	@Test(expected = InvalidStateException.class)
	public void createDuplicatedFreeAccount() {
		final String NEW_EMAIL = "admin2@freeaccount.com";
		final String NEW_PASSWORD = "freeacc";
		final String NEW_GROUP = "freeaccount2";

		createAccount(AccountType.FREE, NEW_EMAIL, NEW_PASSWORD, NEW_GROUP);
		createAccount(AccountType.FREE, NEW_EMAIL, NEW_PASSWORD, NEW_GROUP);
	}

	// Used by Hibernate
	// @Test(expected = PersistenceException.class)
	@Test(expected = InvalidStateException.class)
	public void createDuplicatedPaidAccount() {
		final String NEW_EMAIL = "admin2@paidaccount.com";
		final String NEW_PASSWORD = "paidacc";
		final String NEW_GROUP = "paidaccount2";

		createAccount(AccountType.PAID, NEW_EMAIL, NEW_PASSWORD, NEW_GROUP);
		createAccount(AccountType.PAID, NEW_EMAIL, NEW_PASSWORD, NEW_GROUP);
	}

	private void createAccount(AccountType accountType, String newEmail, String newPassword, String newGroup) {
		AccountDTO newAccountDTO = new AccountDTO(accountType, newEmail, newPassword);

		Account newAccount = facade.createAccount(newAccountDTO, newGroup);
		assertNotNull(newAccount);

		Account newAccountFromDB = facade.getAccountByCredentials(newEmail, newPassword);
		assertEquals(newAccount, newAccountFromDB);

		assertEquals("Account email does not match", newAccountFromDB.getEmail(), newEmail);
		assertEquals("Account password does not match", newAccountFromDB.getPassword(), newPassword);
		assertEquals("Account type does not match", newAccountFromDB.getType(), accountType);
	}

}
