package com.tamoino.web.controller;

import org.springframework.beans.factory.annotation.Required;

import com.tamoino.core.FacadeInterface;
import com.tamoino.core.dto.AccountDTO;
import com.tamoino.core.model.Account;

public class AccountController {

	private FacadeInterface facade;

	@Required
	public void setFacade(FacadeInterface facade) {
		this.facade = facade;
	}

	public Account getAccountByCredentials(String email, String password) {
		Account account = facade.getAccountByCredentials(email, password);
		return account;
	}

	public void createAccount(AccountDTO accountDTO, String groupName) {
		facade.createAccount(accountDTO, groupName);
	}

}
