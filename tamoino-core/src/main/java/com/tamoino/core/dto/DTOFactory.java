package com.tamoino.core.dto;

import java.util.ArrayList;
import java.util.List;

public abstract class DTOFactory<DTO, O> {

	public enum Type {
		Account, Entry, EntryGroup
	}

	public abstract DTO createDTO(O o);

	// public abstract O createObject(DTO dto);

	public List<DTO> createDTOList(List<O> objectList) {
		List<DTO> dtoList = new ArrayList<DTO>();
		DTO dto;
		for (O o : objectList) {
			dto = createDTO(o);
			dtoList.add(dto);
		}
		return dtoList;
	}

	// public List<O> createObjectList(List<DTO> dtoList) {
	// List<O> objectList = new ArrayList<O>();
	// O o;
	// for (DTO dto : dtoList) {
	// o = createObject(dto);
	// objectList.add(o);
	// }
	// return objectList;
	// }

	@SuppressWarnings("rawtypes")
	public final static DTOFactory getDTOFactory(Type type) {
		DTOFactory factory = null;
		switch (type) {
		case Account:
			factory = new AccountDTOFactory();
			break;
		case Entry:
			factory = new EntryDTOFactory();
			break;
		case EntryGroup:
			factory = new EntryGroupDTOFactory();
			break;
		}
		return factory;
	}

}
