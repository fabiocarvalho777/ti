package com.tamoino.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Required;

import com.tamoino.core.FacadeInterface;
import com.tamoino.core.dto.EntrySimpleDTO;

public class EntryController {

	private FacadeInterface facade;

	@Required
	public void setFacade(FacadeInterface facade) {
		this.facade = facade;
	}

	public List<EntrySimpleDTO> getEntrySimpleDTOsByAccountId(long accountId) {
		return facade.getEntrySimpleDTOsByAccountId(accountId);
	}

	public void addEntry(EntrySimpleDTO entryDescDTO) {
		facade.createEntry(entryDescDTO);
	}

	// FIXME has to be replaced by a method that saves a whole changed Entry,
	// instead
	// of individual change methods
	public void setEntryActiveById(long entryId, boolean active) {
		facade.setEntryActiveById(entryId, active);
	}

	public void deleteEntry(long entryId) {
		facade.deleteEntryById(entryId);
	}
}
