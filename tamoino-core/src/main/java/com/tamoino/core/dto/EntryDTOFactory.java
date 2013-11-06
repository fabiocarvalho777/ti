package com.tamoino.core.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.tamoino.core.model.Entry;

public class EntryDTOFactory extends DTOFactory<EntryDTO, Entry> {

	@Override
	public EntryDTO createDTO(Entry entry) {
		long groupId = entry.getGroup().getId();
		long accountId = entry.getAccount().getId();
		String word = entry.getWord();
		String key = entry.getKey();
		String url = entry.getURL();
		String groupName = entry.getGroup().getName();
		boolean active = entry.isActive();
		Date expirationDate = entry.getExpirationDate();
		List<Date> history = entry.getHistory();

		EntryDTO entryDTO = new EntryDTO(accountId, groupId, word, url, groupName, key, active, expirationDate, history);
		entryDTO.setId(entry.getId());
		return entryDTO;
	}

	public EntrySimpleDTO createSimpleDTO(Entry entry) {
		long groupId = entry.getGroup().getId();
		long accountId = entry.getAccount().getId();
		String word = entry.getWord();
		String key = entry.getKey();
		String url = entry.getURL();
		String groupName = entry.getGroup().getName();
		boolean active = entry.isActive();
		Date expirationDate = entry.getExpirationDate();

		EntrySimpleDTO entryDescDTO = new EntrySimpleDTO(accountId, groupId, word, url, groupName, key, active, expirationDate);
		entryDescDTO.setId(entry.getId());
		return entryDescDTO;
	}

	public List<EntrySimpleDTO> createSimpleDTOList(List<Entry> objectList) {
		List<EntrySimpleDTO> dtoList = new ArrayList<EntrySimpleDTO>();
		EntrySimpleDTO dto;
		for (Entry entry : objectList) {
			dto = createSimpleDTO(entry);
			dtoList.add(dto);
		}
		return dtoList;
	}

}
