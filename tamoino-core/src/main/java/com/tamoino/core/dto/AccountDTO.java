package com.tamoino.core.dto;

import java.io.Serializable;

import com.tamoino.core.model.Account.AccountType;

public class AccountDTO implements Serializable {

	private static final long serialVersionUID = 4042438869586396740L;

	private long id;
	private AccountType type;
	private String email;
	private String password;

	AccountDTO() {}
	
	public AccountDTO(AccountType type, String email, String password) {
		setType(type);
		setEmail(email);
		setPassword(password);
	}

	public void setId(long id) {
		this.id = id;
	}

	private void setType(AccountType type) {
		this.type = type;
	}

	private void setEmail(String email) {
		this.email = email;
	}

	private void setPassword(String password) {
		this.password = password;
	}

	public long getId() {
		return id;
	}

	public AccountType getType() {
		return type;
	}

	public String getEmail() {
		return email;
	}

	public String getPassword() {
		return password;
	}

}
