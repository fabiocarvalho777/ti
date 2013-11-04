package com.tamoino.core.persistence;

import java.util.List;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;

import com.tamoino.core.dto.EntrySimpleDTO;
import com.tamoino.core.model.Entry;

public interface EntryDAO {

	/**
	 * Adds a new {@link Entry}
	 * 
	 * @param entry
	 * @throws IllegalArgumentException
	 *             if the Entry parameter is null
	 * @throws EntityExistsException
	 *             if this is a duplicated Entry
	 */
	public abstract void addEntry(Entry entry);

	/**
	 * Persist the current state of the Entry, saving any changes the object
	 * might have compared to the entity on the data base
	 * 
	 * @param entry
	 * @throws IllegalArgumentException
	 *             if the Entry parameter is null or has never been persisted or
	 *             has been already removed
	 */
	public abstract void saveEntry(Entry entry);

	/**
	 * Delete the related {@link Entry}
	 * 
	 * @param entryId
	 * @throws IllegalArgumentException
	 *             if the related Entry does not exist
	 */
	public abstract void deleteEntryById(long entryId);

	/**
	 * Returns an {@link Entry} by its id
	 * 
	 * @param entryId
	 * @throws IllegalArgumentException
	 *             if the related Entry does not exist
	 * @return an {@link Entry} by its id
	 */
	public abstract Entry getEntryById(long entryId);

	/**
	 * Returns the URL string related to the key (whose Entry must be active).
	 * 
	 * @param key
	 * @throws NoResultException
	 *             if there is no Entry with such key or if that Entry is not
	 *             active
	 * @return the URL string related to the key
	 */
	public abstract String getURLByKey(String key);

	/**
	 * Return a list of all active {@link Entry} keys. Returns an empty list if
	 * there is not any active Entry
	 * 
	 * @return a list of all active Entrie's keys
	 */
	public abstract List<String> getAllActiveKeys();

	/**
	 * Returns a list of {@link EntrySimpleDTO} related to the given account id.
	 * Returns an empty list if the related account does not have any Entry
	 * 
	 * @param accountId
	 * @throws IllegalArgumentException
	 *             if accountId is null or if there is not an Account related to
	 *             that account id
	 * @return a list of {@link EntrySimpleDTO}
	 */
	public abstract List<EntrySimpleDTO> getEntrySimpleDTOsByAccountId(long accountId);

}