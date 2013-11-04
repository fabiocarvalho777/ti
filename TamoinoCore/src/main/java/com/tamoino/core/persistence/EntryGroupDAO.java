package com.tamoino.core.persistence;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;

import com.tamoino.core.dto.EntryGroupDTO;
import com.tamoino.core.model.Account;
import com.tamoino.core.model.Entry;
import com.tamoino.core.model.EntryGroup;

public interface EntryGroupDAO {

	/**
	 * Returns the {@link EntryGroup} object related to the given id. Returns
	 * null if there is not an EntryGroup that matches the given id.
	 * 
	 * @param entryGroupId
	 * @return the {@link EntryGroup} object related to the given id
	 */
	public abstract EntryGroup getEntryGroupById(long entryGroupId);

	/**
	 * Returns a list of {@link EntryGroupDTO} related to the given account id.
	 * Returns an empty list if the related account does not have any
	 * {@link EntryGroup} or if there is not an account with such id
	 * 
	 * @param accountId
	 * @return a list of {@link EntryGroupDTO}
	 */
	public abstract List<EntryGroupDTO> getEntryGroupDTOsListByAccountId(long accountId);

	/**
	 * Returns a map of {@link EntryGroupDTO} related to the given account id.
	 * Returns an empty map if the related account does not have any EntryGroup
	 * or if there is not an account with such id
	 * 
	 * @param accountId
	 * @return a map of {@link EntryGroupDTO}
	 */
	public abstract Map<Long, EntryGroupDTO> getEntryGroupDTOsMapByAccountId(long accountId);

	/**
	 * Some accounts have only one {@link EntryGroup}. This method returns the
	 * EntryGroup id of accounts like that. If for any reason that is not the
	 * case (the account has zero or more than one EntryGroup), an exception is
	 * thrown
	 * 
	 * @param accountId
	 * @throws NoResultException
	 *             if the account does not have any EntryGroup
	 * @throws NonUniqueResultException
	 *             if the account has more than one EntryGroup
	 * @return the single EntryGroup id of the given account
	 */
	public abstract long getSingleEntryGroupIdByAccountId(long accountId);

	/**
	 * Creates a new {@link EntryGroup} to the given account and returns the new
	 * EntryGroup object created
	 * 
	 * @param entrySimpleDTO
	 * @throws IllegalArgumentException
	 *             if groupName is null or invalid, or if that account cannot
	 *             have more EntryGroups
	 * @throws EntityExistsException
	 *             if this is a duplicated EntryGroup name
	 * @return the new created Entry object
	 */
	public abstract EntryGroup createEntryGroup(Account account, String groupName);

	/**
	 * Delete the related {@link EntryGroup}
	 * 
	 * @param entryId
	 * @throws IllegalArgumentException
	 *             if the related EntryGroup does not exist or if it is not
	 *             empty (still has entries)
	 */
	public abstract void deleteEntryGroupById(long entryGroupId);

	/**
	 * Returns a {@link EntryGroupDTO} of the PUBLIC {@link EntryGroup}
	 * 
	 * @return a {@link EntryGroupDTO} of the PUBLIC {@link EntryGroup}
	 */
	public abstract EntryGroupDTO getPublicEntryGroupDTO();

	/**
	 * Returns the PUBLIC {@link EntryGroup}
	 * 
	 * @return the PUBLIC {@link EntryGroup}
	 */
	public abstract EntryGroup getPublicEntryGroup();

	/**
	 * Returns true only if the related {@link EntryGroup} does not have any
	 * {@link Entry}
	 * 
	 * @param entryGroupId
	 * @throws IllegalArgumentException
	 *             if there is not an {@link EntryGroup} with such id
	 * @return true only if the related {@link EntryGroup} does not have any
	 *         {@link Entry}
	 */
	public abstract boolean isEntryGroupEmpty(long entryGroupId);

	/**
	 * Returns the amount of entries an {@link EntryGroup} has.
	 * 
	 * @param entryGroupId
	 * @throws IllegalArgumentException
	 *             if there is not an {@link EntryGroup} with such id
	 * @return the amount of entries an {@link EntryGroup} has.
	 */
	public abstract long getEntriesCountByEntryGroupId(long entryGroupId);

}
