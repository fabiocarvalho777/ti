package com.tamoino.core.persistence;

import javax.persistence.EntityExistsException;

import com.tamoino.core.dto.AccountDTO;
import com.tamoino.core.model.Account;

public interface AccountDAO {

	/**
	 * Returns the {@link Account} object related to the given id. Returns null if there
	 * is not an account that matches the given id.
	 * 
	 * @param accountId
	 * @return the Account object related to the given id
	 */
	public abstract Account getAccountById(long accountId);

	/**
	 * Returns the {@link Account} object related to the given email and password.
	 * Returns null if there is not an account that matches the given email and
	 * password
	 * 
	 * @param email
	 * @param password
	 * @throws IllegalArgumentException if any of the parameters are
	 *         null or blank
	 * @return the Account object related to the given email and password
	 */
	public abstract Account getAccountByCredentials(String email, String password);

	/**
	 * Create a new {@link Account} using its related DTO object and returns the new
	 * Account object created
	 * 
	 * @param accountDTO
	 * @throws IllegalArgumentException if the DTO parameter is null
	 * @throws EntityExistsException if this is a duplicated Account
	 * @return the new created Account
	 */
	public abstract Account createAccount(AccountDTO accountDTO);

}
