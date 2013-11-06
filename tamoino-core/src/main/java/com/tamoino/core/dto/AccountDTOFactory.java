package com.tamoino.core.dto;

import com.tamoino.core.model.Account;
import com.tamoino.core.model.Account.AccountType;

public class AccountDTOFactory extends DTOFactory<AccountDTO, Account> {

	@Override
	public AccountDTO createDTO(Account account) {
		AccountType type = account.getType();
		String email = account.getEmail();
		String password = account.getPassword();

		AccountDTO dto = new AccountDTO(type, email, password);
		dto.setId(account.getId());

		return dto;
	}

//	@Override
	public Account createObject(AccountDTO dto) {
		AccountType accountType = dto.getType();
		String email = dto.getEmail();
		String password = dto.getPassword();

		Account account = new Account(accountType, email, password);
		return account;
	}

}
