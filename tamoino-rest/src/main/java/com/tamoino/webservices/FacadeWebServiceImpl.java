package com.tamoino.webservices;

import java.util.HashMap;
import java.util.List;

import javax.jws.WebService;

import org.springframework.beans.factory.annotation.Required;

import com.tamoino.core.Facade;
import com.tamoino.core.dto.AccountDTO;
import com.tamoino.core.dto.EntryGroupDTO;
import com.tamoino.core.dto.EntrySimpleDTO;
import com.tamoino.core.model.Account;
import com.tamoino.core.model.Entry;
import com.tamoino.core.model.EntryGroup;

@WebService(endpointInterface = "com.tamoino.webservices.FacadeWebService", serviceName = "FacadeWebService")
public class FacadeWebServiceImpl implements FacadeWebService {

	private Facade facade;

	@Required
	public void setFacade(Facade facade) {
		this.facade = facade;
	}

	public Account createAccount(AccountDTO accountDTO, String groupName) {
		return facade.createAccount(accountDTO, groupName);
	}

	public Entry createEntry(EntrySimpleDTO entrySimpleDTO) {
		return facade.createEntry(entrySimpleDTO);
	}

	public EntryGroup createEntryGroup(Account account, String groupName) {
		return facade.createEntryGroup(account, groupName);
	}

	public void setEntryActiveById(long entryId, boolean active) {
		facade.setEntryActiveById(entryId, active);
	}

	public void deleteEntryById(long entryId) {
		facade.deleteEntryById(entryId);
	}

	public void deleteEntryGroupById(long entryGroupId) {
		facade.deleteEntryGroupById(entryGroupId);
	}

	public Entry getEntryById(long entryId) {
		return facade.getEntryById(entryId);
	}

	public String getURLByKey(String key) {
		return facade.getURLByKey(key);
	}

	public List<String> getAllActiveKeys() {
		return facade.getAllActiveKeys();
	}

	public Account getAccountByCredentials(String email, String password) {
		return facade.getAccountByCredentials(email, password);
	}

	public EntryGroup getEntryGroupById(long entryGroupId) {
		return facade.getEntryGroupById(entryGroupId);
	}

	public List<EntrySimpleDTO> getEntrySimpleDTOsByAccountId(long accountId) {
		return facade.getEntrySimpleDTOsByAccountId(accountId);
	}

	public List<EntryGroupDTO> getEntryGroupDTOsListByAccountId(long accountId) {
		return facade.getEntryGroupDTOsListByAccountId(accountId);
	}

	public HashMap<Long, EntryGroupDTO> getEntryGroupDTOsMapByAccountId(long accountId) {
		return (HashMap<Long, EntryGroupDTO>) facade.getEntryGroupDTOsMapByAccountId(accountId);
	}

	public long getSingleEntryGroupIdByAccountId(long accountId) {
		return facade.getSingleEntryGroupIdByAccountId(accountId);
	}

	public EntryGroupDTO getPublicEntryGroupDTO() {
		return facade.getPublicEntryGroupDTO();
	}

}
