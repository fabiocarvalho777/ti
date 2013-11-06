package com.tamoino.core.dto;

import com.tamoino.core.model.EntryGroup;
import com.tamoino.core.model.EntryGroup.EntryGroupType;

public class EntryGroupDTOFactory extends DTOFactory<EntryGroupDTO, EntryGroup> {

	@Override
	public EntryGroupDTO createDTO(EntryGroup entryGroup) {
		long id = entryGroup.getId();
		String name = entryGroup.getName();
		EntryGroupType type = entryGroup.getType();

		EntryGroupDTO dto = new EntryGroupDTO(id, name, type);

		return dto;
	}

}
