package com.tamoino.core.dto;

import com.tamoino.core.model.EntryGroup.EntryGroupType;

public class EntryGroupDTO {

	private long id;
	private String name;
	private EntryGroupType type;

	EntryGroupDTO() {
	}
	
	public EntryGroupDTO(long id, String name, EntryGroupType type) {
		setId(id);
		setName(name);
		setType(type);
	}

	private void setId(long id) {
		this.id = id;
	}

	private void setName(String name) {
		this.name = name;
	}

	private void setType(EntryGroupType type) {
		this.type = type;
	}

	public long getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public EntryGroupType getType() {
		return type;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof EntryGroupDTO)) {
			return false;
		}
		EntryGroupDTO dto = (EntryGroupDTO) obj;
		if (this.id != dto.id) {
			return false;
		}
		if (!this.name.equals(dto.name)) {
			return false;
		}
		if (!this.type.equals(dto.type)) {
			return false;
		}
		return true;
	}

}
