package com.tamoino.webservices;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlValue;

import com.tamoino.core.dto.EntryGroupDTO;

public class IdEntryGroupDTOMapBoundTypeEntry {

	@XmlAttribute
	Long key;

	@XmlValue
	EntryGroupDTO value;

	IdEntryGroupDTOMapBoundTypeEntry(EntryGroupDTO entryGroupDTO) {
		setKey(entryGroupDTO.getId());
		setValue(entryGroupDTO);
	}

	private void setKey(Long key) {
		this.key = key;
	}

	private void setValue(EntryGroupDTO value) {
		if (value == null) {
			throw new IllegalArgumentException();
		}
		this.value = value;
	}

	Long getKey() {
		return key;
	}

	EntryGroupDTO getValue() {
		return value;
	}

}
