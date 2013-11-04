package com.tamoino.webservices;

import java.util.HashMap;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.tamoino.core.dto.EntryGroupDTO;

public final class IdEntryGroupDTOMapAdapter extends XmlAdapter<IdEntryGroupDTOMapBoundType, HashMap<Long, EntryGroupDTO>> {

	@Override
	public HashMap<Long, EntryGroupDTO> unmarshal(IdEntryGroupDTOMapBoundType boundType) throws Exception {
		return boundType.unmarshal();
	}

	@Override
	public IdEntryGroupDTOMapBoundType marshal(HashMap<Long, EntryGroupDTO> map) throws Exception {
		return IdEntryGroupDTOMapBoundType.marshal(map);
	}

}
