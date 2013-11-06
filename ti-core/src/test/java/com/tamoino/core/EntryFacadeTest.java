package com.tamoino.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import javax.persistence.NoResultException;

import org.apache.openjpa.persistence.InvalidStateException;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tamoino.core.dto.AccountDTO;
import com.tamoino.core.dto.EntrySimpleDTO;
import com.tamoino.core.model.Account;
import com.tamoino.core.model.Account.AccountType;
import com.tamoino.core.model.Entry;

//FIXME JUnits must must remove everything the setup method created (cleanup method)
//TODO Public entries are not being tested enough!
public class EntryFacadeTest {

	private static FacadeInterface facade;
	private static Account testAccount;
	private static long testAccountId;
	private static long testGroupId;
	private static Entry testEntry;
	private static long testEntryId;

	private static final String EMAIL = "entryfacade@tests.com";
	private static final String PASSWORD = "tests";
	private static final String ENTRY_GROUP = "entryfacadetest";
	private static final String KEY = "testkey@" + ENTRY_GROUP;
	private static final String URL = "http://www.testurl.com";

	@BeforeClass
	public static void setup() {
		facade = ApplicationContextHelper.getSingleton();

		AccountDTO newAccountDTO = new AccountDTO(AccountType.FREE, EMAIL, PASSWORD);
		testAccount = facade.createAccount(newAccountDTO, ENTRY_GROUP);

		testAccountId = testAccount.getId();
		testGroupId = facade.getSingleEntryGroupIdByAccountId(testAccountId);

		EntrySimpleDTO entrySimpleDTO = new EntrySimpleDTO(testAccountId, testGroupId, URL, KEY, true);
		testEntry = facade.createEntry(entrySimpleDTO);
		testEntryId = testEntry.getId();
	}

	@Test
	public void createPrivateEntry() {
		final String NEW_WORD = "createentry";
		final String NEW_KEY = "createentry@" + ENTRY_GROUP;
		final String NEW_URL = "www.createentry.com";

		EntrySimpleDTO entrySimpleDTO = new EntrySimpleDTO(testAccountId, testGroupId, NEW_URL, NEW_KEY, true);
		Entry entry = facade.createEntry(entrySimpleDTO);

		assertNotNull(entry);
		assertTrue(entry.getId() > -1);
		assertEquals(entry.getURL(), NEW_URL);
		assertEquals(entry.getKey(), NEW_KEY);
		assertEquals(entry.getWord(), NEW_WORD);
		assertTrue(entry.isActive());
		assertEquals(entry.getAccount(), testAccount);

		try {
			facade.createEntry(entrySimpleDTO);
			fail("A PersistenceException was expected but was not thrown");
			// Used by Hibernate
			// } catch (PersistenceException e) {
		} catch (InvalidStateException e) {
		}
	}

	@Test
	public void createPublicEntry() {
		final String NEW_EMAIL = "pee@pee.com";
		final String NEW_PASSWORD = "peepee";
		final String NEW_ENTRY_GROUP = "pee";

		final String NEW_WORD = "createentry";
		final String NEW_KEY = "createentry";
		final String NEW_URL = "www.createentry.com";

		AccountDTO newAccountDTO = new AccountDTO(AccountType.PAID, NEW_EMAIL, NEW_PASSWORD);
		Account newAccount = facade.createAccount(newAccountDTO, NEW_ENTRY_GROUP);
		long newAccountId = newAccount.getId();

		long publicGroupId = facade.getPublicEntryGroupDTO().getId();

		EntrySimpleDTO entrySimpleDTO = new EntrySimpleDTO(newAccountId, publicGroupId, NEW_URL, NEW_KEY, true);
		Entry entry = facade.createEntry(entrySimpleDTO);

		assertNotNull(entry);
		assertTrue(entry.getId() > -1);
		assertEquals(entry.getURL(), NEW_URL);
		assertEquals(entry.getKey(), NEW_KEY);
		assertEquals(entry.getWord(), NEW_WORD);
		assertTrue(entry.isActive());
		assertEquals(entry.getAccount(), newAccount);

		try {
			facade.createEntry(entrySimpleDTO);
			fail("A PersistenceException was expected but was not thrown");
			// Used by Hibernate
			// } catch (PersistenceException e) {
		} catch (InvalidStateException e) {
		}
	}

	@Test(expected = IllegalArgumentException.class)
	public void createEntryNull() {
		facade.createEntry(null);
	}

	@Test
	public void setEntryActiveById() {
		facade.setEntryActiveById(testEntryId, true);
		Entry beforeEntry = facade.getEntryById(testEntryId);
		assertTrue(beforeEntry.isActive());

		facade.setEntryActiveById(testEntryId, false);
		Entry afterEntry = facade.getEntryById(testEntryId);
		assertFalse(afterEntry.isActive());
		assertFalse(beforeEntry.isActive());

		// Making test entry active again to do not jeopardize other unit tests
		facade.setEntryActiveById(testEntryId, true);

		assertTrue(testEntry.isActive());
	}

	@Test
	public void deleteEntryById() {
		final String NEW_KEY = "createentry2@" + ENTRY_GROUP;
		final String NEW_URL = "www.createentry2.com";

		EntrySimpleDTO entrySimpleDTO = new EntrySimpleDTO(testAccountId, testGroupId, NEW_URL, NEW_KEY, true);
		Entry newEntry = facade.createEntry(entrySimpleDTO);
		long newEntryId = newEntry.getId();

		List<EntrySimpleDTO> listBefore = facade.getEntrySimpleDTOsByAccountId(testAccountId);
		assertTrue(listBefore.contains(entrySimpleDTO));

		facade.deleteEntryById(newEntryId);

		List<EntrySimpleDTO> listAfter = facade.getEntrySimpleDTOsByAccountId(testAccountId);
		assertFalse(listAfter.contains(entrySimpleDTO));
	}

	@Test
	public void getURLByKey() {
		String url = facade.getURLByKey(KEY);
		assertEquals(URL, url);
	}

	@Test(expected = NoResultException.class)
	public void getURLByKeyNonExistent() {
		facade.getURLByKey("non existent key");
	}

	@Test
	public void getURLByKeyNotActive() {
		facade.setEntryActiveById(testEntryId, false);

		try {
			facade.getURLByKey(KEY);
			fail();
		} catch (NoResultException e) {
			// Worked as expected, since this entry is now not active
		}

		facade.setEntryActiveById(testEntryId, true);
		String url = facade.getURLByKey(KEY);
		assertEquals(URL, url);
	}

	@Test
	public void getAllActiveKeys() {
		List<String> list = facade.getAllActiveKeys();
		assertNotNull(list);
		assertTrue(list.size() >= 1);
		assertTrue(list.contains(KEY));
	}

	@Test
	public void getEntrySimpleDTOsByAccountId() {
		List<EntrySimpleDTO> list = facade.getEntrySimpleDTOsByAccountId(testAccountId);
		assertNotNull(list);
		assertTrue(list.size() >= 1);

		for (EntrySimpleDTO entrySimpleDTO : list) {
			if (entrySimpleDTO.getKey().equalsIgnoreCase(KEY)) {
				assertEquals(entrySimpleDTO.getURL(), URL);
				return;
			}
		}

		fail();
	}

}
