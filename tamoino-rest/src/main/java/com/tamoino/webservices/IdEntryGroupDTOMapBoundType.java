package com.tamoino.webservices;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tamoino.core.dto.EntryGroupDTO;

public class IdEntryGroupDTOMapBoundType {

	private List<IdEntryGroupDTOMapBoundTypeEntry> entries;

	private IdEntryGroupDTOMapBoundType(HashMap<Long, EntryGroupDTO> map) {
		entries = new ArrayList<IdEntryGroupDTOMapBoundTypeEntry>(map.size());
		IdEntryGroupDTOMapBoundTypeEntry boundTypeEntry;
		for (Map.Entry<Long, EntryGroupDTO> mapEntry : map.entrySet()) {
			boundTypeEntry = new IdEntryGroupDTOMapBoundTypeEntry(mapEntry.getValue());
			entries.add(boundTypeEntry);
		}
	}

	public static IdEntryGroupDTOMapBoundType marshal(HashMap<Long, EntryGroupDTO> map) {
		IdEntryGroupDTOMapBoundType boundType = new IdEntryGroupDTOMapBoundType(map);
		return boundType;
	}

	public HashMap<Long, EntryGroupDTO> unmarshal() {
		HashMap<Long, EntryGroupDTO> map = new HashMap<Long, EntryGroupDTO>(entries.size());
		for (IdEntryGroupDTOMapBoundTypeEntry entry : entries) {
			map.put(entry.getKey(), entry.getValue());
		}
		return map;
	}

}
