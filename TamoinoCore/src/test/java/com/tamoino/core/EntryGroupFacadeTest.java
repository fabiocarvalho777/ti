package com.tamoino.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;
import java.util.Map;

import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceException;

import org.apache.openjpa.persistence.InvalidStateException;
import org.junit.BeforeClass;
import org.junit.Test;

import com.tamoino.core.dto.AccountDTO;
import com.tamoino.core.dto.DTOFactory;
import com.tamoino.core.dto.EntryGroupDTO;
import com.tamoino.core.dto.EntryGroupDTOFactory;
import com.tamoino.core.dto.EntrySimpleDTO;
import com.tamoino.core.model.Account;
import com.tamoino.core.model.Account.AccountType;
import com.tamoino.core.model.EntryGroup;
import com.tamoino.core.model.EntryGroup.EntryGroupType;

// FIXME JUnits must not rely on any object that comes from demo data. Their setup method must provide everything they need and their cleanup method must remove everything the setup method created
public class EntryGroupFacadeTest {

	private static FacadeInterface facade;
	private static EntryGroupDTOFactory dtoFactory;
	private static Account testAccount;
	private static long testAccountId;
	private static long testGroupId;

	private static final String EMAIL = "entrygroupfacade@tests.com";
	private static final String PASSWORD = "tests";
	private static final String ENTRY_GROUP = "entrygrpfcdtest";

	@BeforeClass
	public static void setup() {
		facade = ApplicationContextHelper.getSingleton();

		dtoFactory = (EntryGroupDTOFactory) DTOFactory.getDTOFactory(DTOFactory.Type.EntryGroup);

		AccountDTO newAccountDTO = new AccountDTO(AccountType.FREE, EMAIL, PASSWORD);
		testAccount = facade.createAccount(newAccountDTO, ENTRY_GROUP);

		testAccountId = testAccount.getId();
		testGroupId = facade.getSingleEntryGroupIdByAccountId(testAccountId);
	}

	@Test
	public void createEntryGroup() {
		final String GROUP_NAME = "fordoes";

		EntryGroup entryGroup = facade.createEntryGroup(testAccount, GROUP_NAME);

		assertNotNull(entryGroup);
		assertEquals(entryGroup.getName(), GROUP_NAME);
		assertSame(entryGroup.getAccount(), testAccount);
		assertEquals(entryGroup.getType(), EntryGroupType.PRIVATE);
		assertTrue(entryGroup.isPrivate());
	}

	// Used by Hibernate
	// @Test(expected = PersistenceException.class)
	@Test(expected = InvalidStateException.class)
	public void createEntryGroupDuplicated() {
		final String GROUP_NAME = "fordoes2";

		facade.createEntryGroup(testAccount, GROUP_NAME);
		facade.createEntryGroup(testAccount, GROUP_NAME);
	}

	@Test
	public void deleteEntryGroupById() {
		final String GROUP_NAME = "fordoes3";

		// Creating entry group
		EntryGroup entryGroup = facade.createEntryGroup(testAccount, GROUP_NAME);

		// Creating entry group dto
		long entryGroupId = entryGroup.getId();
		EntryGroupDTO entryGroupDTO = dtoFactory.createDTO(entryGroup);

		// Making sure the entry group was created
		List<EntryGroupDTO> groupsBefore = facade.getEntryGroupDTOsListByAccountId(testAccountId);
		assertTrue(groupsBefore.contains(entryGroupDTO));

		// Deleting the entry group
		facade.deleteEntryGroupById(entryGroupId);

		// Making sure the entry group was deleted
		List<EntryGroupDTO> groupsAfter = facade.getEntryGroupDTOsListByAccountId(testAccountId);
		assertFalse(groupsAfter.contains(entryGroupDTO));
	}

	// Used by Hibernate
	// @Test(expected = IllegalArgumentException.class)
	public void deleteEntryGroupByIdNonExistent() {
		final long id = -1;
		facade.deleteEntryGroupById(id);
	}

	@Test(expected = PersistenceException.class)
	public void deleteEntryGroupByIdNonEmpty() {
		final String GROUP_NAME = "jacks27";
		final String URL = "www.jacks27.com";
		final String KEY = "j27@" + GROUP_NAME;

		EntryGroup entryGroup = facade.createEntryGroup(testAccount, GROUP_NAME);
		long entryGroupId = entryGroup.getId();

		EntrySimpleDTO entrySimpleDTO = new EntrySimpleDTO(testAccountId, entryGroupId, URL, KEY, true);
		facade.createEntry(entrySimpleDTO);

		facade.deleteEntryGroupById(entryGroupId);
	}

	@Test
	public void getEntryGroupById() {
		final String NEW_GROUP_NAME = "newGroupName";

		EntryGroup entryGroup = facade.createEntryGroup(testAccount, NEW_GROUP_NAME);
		EntryGroup sameEntryGroup = facade.getEntryGroupById(entryGroup.getId());

		assertNotNull(entryGroup);
		assertNotNull(sameEntryGroup);
		assertEquals(entryGroup, sameEntryGroup);
	}

	@Test
	public void getEntryGroupDTOsListByAccountId() {
		List<EntryGroupDTO> list = facade.getEntryGroupDTOsListByAccountId(testAccountId);

		assertNotNull(list);
		assertTrue(list.size() >= 1);

		for (EntryGroupDTO entryGroupDTO : list) {
			if (entryGroupDTO.getName().equals(ENTRY_GROUP)) {
				// the test succeed.
				return;
			}
		}

		fail();
	}

	public void getEntryGroupDTOsListByAccountIdNonExistent() {
		List<EntryGroupDTO> list = facade.getEntryGroupDTOsListByAccountId(-1);
		assertTrue(list.size() == 0);
	}

	@Test
	public void getEntryGroupDTOsMapByAccountId() {
		Map<Long, EntryGroupDTO> map = facade.getEntryGroupDTOsMapByAccountId(testAccountId);

		assertNotNull(map);
		assertTrue(map.size() >= 1);

		EntryGroupDTO entryGroupDTO = map.get(testGroupId);

		assertEquals(entryGroupDTO.getName(), ENTRY_GROUP);
	}

	public void getEntryGroupDTOsMapByAccountIdNonExistent() {
		Map<Long, EntryGroupDTO> map = facade.getEntryGroupDTOsMapByAccountId(-1);
		assertTrue(map.size() == 0);
	}

	@Test
	public void getSingleEntryGroupIdByAccountId() {
		final String NEW_GROUP_NAME = "newSingleGName";

		AccountDTO newAccountDTO = new AccountDTO(AccountType.FREE, "newAccountEmail", "newAPasswd");
		Account newAccount = facade.createAccount(newAccountDTO, NEW_GROUP_NAME);
		long accountId = newAccount.getId();

		long singleEntryGroupId = facade.getSingleEntryGroupIdByAccountId(accountId);
		assertTrue(singleEntryGroupId > 0);

		EntryGroup entryGroup = facade.getEntryGroupById(singleEntryGroupId);
		assertNotNull(entryGroup);
		assertEquals(entryGroup.getId(), singleEntryGroupId);
		assertEquals(entryGroup.getName(), NEW_GROUP_NAME);
	}

	@Test(expected = NonUniqueResultException.class)
	public void getSingleEntryGroupIdByAccountIdNotSingle() {
		final String NEW_GROUP_NAME_1 = "notSingle1";
		final String NEW_GROUP_NAME_2 = "notSingle2";

		AccountDTO newAccountDTO = new AccountDTO(AccountType.FREE, "newAccountEmail7", "newAPasswd");
		Account newAccount = facade.createAccount(newAccountDTO, NEW_GROUP_NAME_1);
		facade.createEntryGroup(newAccount, NEW_GROUP_NAME_2);

		long accountId = newAccount.getId();
		facade.getSingleEntryGroupIdByAccountId(accountId);
	}

	@Test
	public void getPublicEntryGroupDTO() {
		EntryGroupDTO entryGroupDTO = facade.getPublicEntryGroupDTO();

		assertNotNull(entryGroupDTO);
		assertEquals(entryGroupDTO.getName(), "PUBLIC");
		assertEquals(entryGroupDTO.getType(), EntryGroupType.PUBLIC);
	}

}
