package com.tamoino.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.PersistenceException;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tamoino.core.dto.AccountDTO;
import com.tamoino.core.dto.EntryGroupDTO;
import com.tamoino.core.dto.EntrySimpleDTO;
import com.tamoino.core.model.Account;
import com.tamoino.core.model.Entry;
import com.tamoino.core.model.EntryGroup;
import com.tamoino.core.persistence.AccountDAO;
import com.tamoino.core.persistence.EntryDAO;
import com.tamoino.core.persistence.EntryGroupDAO;

public class Facade implements FacadeInterface {

	private EntryDAO entryDAO;
	private AccountDAO accountDAO;
	private EntryGroupDAO entryGroupDAO;

	@Required
	public void setEntryDAO(EntryDAO entryDAO) {
		this.entryDAO = entryDAO;
	}

	@Required
	public void setAccountDAO(AccountDAO accountDAO) {
		this.accountDAO = accountDAO;
	}

	@Required
	public void setEntryGroupDAO(EntryGroupDAO entryGroupDAO) {
		this.entryGroupDAO = entryGroupDAO;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Account createAccount(AccountDTO accountDTO, String groupName) {
		Account account = accountDAO.createAccount(accountDTO);
		entryGroupDAO.createEntryGroup(account, groupName);

		return account;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public Entry createEntry(EntrySimpleDTO entrySimpleDTO) {
		if (entrySimpleDTO == null) {
			throw new IllegalArgumentException();
		}

		long accountId = entrySimpleDTO.getAccountId();
		Account account = accountDAO.getAccountById(accountId);

		long entryGroupId = entrySimpleDTO.getGroupId();
		EntryGroup entryGroup = entryGroupDAO.getEntryGroupById(entryGroupId);

		String word = entrySimpleDTO.getWord();
		String url = entrySimpleDTO.getURL();

		Entry entry = new Entry(account, entryGroup, word, url);
		entryDAO.addEntry(entry);

		return entry;
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public EntryGroup createEntryGroup(Account account, String groupName) {
		EntryGroup entryGroup = entryGroupDAO.createEntryGroup(account, groupName);
		return entryGroup;
	}

	// FIXME the parameter has to be a DTO instead, and then it has to be
	// commented out
	// public void saveEntry(Entry entry) {
	// entryDAO.saveEntry(entry);
	// }

	// FIXME has to be replaced by a method that saves a whole changed Entry
	// using a EntrySimpleDTO, instead of individual change methods
	// TODO add a JUnit for this
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void setEntryActiveById(long entryId, boolean active) {
		Entry entry = entryDAO.getEntryById(entryId);
		entry.setActive(active);
		entryDAO.saveEntry(entry);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void deleteEntryById(long entryId) {
		entryDAO.deleteEntryById(entryId);
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void deleteEntryGroupById(long entryGroupId) {
		if (!entryGroupDAO.isEntryGroupEmpty(entryGroupId)) {
			throw new PersistenceException("Attempt to delete a non empty entry group");
		}
		entryGroupDAO.deleteEntryGroupById(entryGroupId);
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public Entry getEntryById(long entryId) {
		Entry entry = entryDAO.getEntryById(entryId);
		return entry;
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public String getURLByKey(String key) {
		String url = entryDAO.getURLByKey(key);
		return url;
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public List<String> getAllActiveKeys() {
		return entryDAO.getAllActiveKeys();
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public Account getAccountByCredentials(String email, String password) {
		Account account = accountDAO.getAccountByCredentials(email, password);
		return account;
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public EntryGroup getEntryGroupById(long entryGroupId) {
		EntryGroup entryGroup = entryGroupDAO.getEntryGroupById(entryGroupId);
		return entryGroup;
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public List<EntrySimpleDTO> getEntrySimpleDTOsByAccountId(long accountId) {
		List<EntrySimpleDTO> list = entryDAO.getEntrySimpleDTOsByAccountId(accountId);
		return list;
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public List<EntryGroupDTO> getEntryGroupDTOsListByAccountId(long accountId) {
		List<EntryGroupDTO> list = entryGroupDAO.getEntryGroupDTOsListByAccountId(accountId);
		return list;
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public HashMap<Long, EntryGroupDTO> getEntryGroupDTOsMapByAccountId(long accountId) {
		Map<Long, EntryGroupDTO> map = entryGroupDAO.getEntryGroupDTOsMapByAccountId(accountId);
		return (HashMap<Long, EntryGroupDTO>) map;
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public long getSingleEntryGroupIdByAccountId(long accountId) {
		long id = entryGroupDAO.getSingleEntryGroupIdByAccountId(accountId);
		return id;
	}

	@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
	public EntryGroupDTO getPublicEntryGroupDTO() {
		EntryGroupDTO entryGroupDTO = entryGroupDAO.getPublicEntryGroupDTO();
		return entryGroupDTO;
	}

}
