package com.tamoino.core.dto;

import java.io.Serializable;

public class AccountTypeDTO implements Serializable {

	private static final long serialVersionUID = 1096593068113539460L;

	private long id;
	private String name;
	private int maxGroups;

	public AccountTypeDTO(String name, int maxGroups) {
		setName(name);
		setMaxGroups(maxGroups);
	}
	
	public void setId(long id) {
		this.id = id;
	}

	private void setName(String name) {
		this.name = name;
	}

	private void setMaxGroups(int maxGroups) {
		this.maxGroups = maxGroups;
	}

	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}

	public int getMaxGroups() {
		return maxGroups;
	}

}
