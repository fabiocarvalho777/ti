package com.tamoino.web.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Required;

import com.tamoino.core.FacadeInterface;
import com.tamoino.core.dto.EntryGroupDTO;
import com.tamoino.core.model.Account;

public class EntryGroupController {

	private FacadeInterface facade;

	@Required
	public void setFacade(FacadeInterface facade) {
		this.facade = facade;
	}

	public List<EntryGroupDTO> getGroupSimpleDTOsListByAccountId(long accountId) {
		return facade.getEntryGroupDTOsListByAccountId(accountId);
	}

	public Map<Long, EntryGroupDTO> getEntryGroupDTOsMapByAccountId(long accountId) {
		return facade.getEntryGroupDTOsMapByAccountId(accountId);
	}

	public long getSingleGroupId(long accountId) {
		return facade.getSingleEntryGroupIdByAccountId(accountId);
	}

	public EntryGroupDTO getPublicGroupSimpleDTO() {
		return facade.getPublicEntryGroupDTO();
	}

	public void addGroup(Account account, String groupName) {
		facade.createEntryGroup(account, groupName);
	}

	public void deleteGroup(long accountId) {
		facade.deleteEntryGroupById(accountId);
	}

}
