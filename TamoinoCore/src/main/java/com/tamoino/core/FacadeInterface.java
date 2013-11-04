package com.tamoino.core;

import java.util.HashMap;
import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.PersistenceException;

import com.tamoino.core.dto.AccountDTO;
import com.tamoino.core.dto.EntryGroupDTO;
import com.tamoino.core.dto.EntrySimpleDTO;
import com.tamoino.core.model.Account;
import com.tamoino.core.model.Entry;
import com.tamoino.core.model.EntryGroup;

/* TODO
 * 
 * "Service Facades can (should!) be made responsible for mapping from DTO’s to domain objects and back.
 * Service Facades are invoked with DTO’s as arguments, map them to domain objects, invoke the actual
 * service (or services), map the result back to DTO’s and return those DTO’s to the client. Actually,
 * to make sure your Service Facades only adhere to Single Responsibility Principle, you should factor
 * the mapping logic out to separate DTO2DOMapper and DO2DTOMapper classes."
 * 
 * The statement above came from this blog post:
 * http://blog.xebia.com/2009/05/11/jpa-implementation-patterns-service-facades-and-data-transfers-objects/) 
 *
 * Research and think about that and, if necessary, do the proper changes to the FacadeInterface
 */
public interface FacadeInterface {

	/**
	 * Creates a new {@link Account} and also its mandatory private EntryGroup.
	 * Returns the new Account created.
	 * 
	 * @param accountDTO
	 * @param groupName
	 *            the name of the mandatory private group
	 * @throws EntityExistsException
	 *             exception if the Account or EntryGroup is duplicated
	 * @throws IllegalArgumentException
	 *             if any of the parameters are null, blank or invalid
	 * @return the new Account created
	 */
	public abstract Account createAccount(AccountDTO accountDTO, String groupName);

	/**
	 * Creates a new {@link Entry} and returns the new Entry created.
	 * 
	 * @throws PersistenceException
	 *             if the Entry's key is duplicated
	 * @throws EntityExistsException
	 *             if the Entry itself is duplicated
	 * @throws IllegalArgumentException
	 *             if the {@link EntrySimpleDTO} is null or invalid
	 * @param entrySimpleDTO
	 * @return the new Entry created
	 */
	public abstract Entry createEntry(EntrySimpleDTO entrySimpleDTO);

	// FIXME The Account parameter should be replaced by the account id
	public abstract EntryGroup createEntryGroup(Account account, String groupName);

	// FIXME has to be replaced by a method that saves a whole changed Entry
	// using a EntrySimpleDTO, instead of individual change methods
	// TODO add a JUnit for this
	public abstract void setEntryActiveById(long entryId, boolean active);

	public abstract void deleteEntryById(long entryId);

	public abstract void deleteEntryGroupById(long entryGroupId);

	public abstract Entry getEntryById(long entryId);

	public abstract String getURLByKey(String key);

	public abstract List<String> getAllActiveKeys();

	public abstract Account getAccountByCredentials(String email, String password);

	public abstract EntryGroup getEntryGroupById(long entryGroupId);

	public abstract List<EntrySimpleDTO> getEntrySimpleDTOsByAccountId(long accountId);

	public abstract List<EntryGroupDTO> getEntryGroupDTOsListByAccountId(long accountId);

	public abstract HashMap<Long, EntryGroupDTO> getEntryGroupDTOsMapByAccountId(long accountId);

	public abstract long getSingleEntryGroupIdByAccountId(long accountId);

	public abstract EntryGroupDTO getPublicEntryGroupDTO();

}