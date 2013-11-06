package com.tamoino.core.persistence.jpa;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tamoino.core.dto.AccountDTO;
import com.tamoino.core.dto.AccountDTOFactory;
import com.tamoino.core.dto.DTOFactory;
import com.tamoino.core.model.Account;
import com.tamoino.core.persistence.AccountDAO;

@Transactional(propagation = Propagation.MANDATORY)
public class JPA_AccountDAO implements AccountDAO {

	@PersistenceContext
	private EntityManager entityManager;
	private AccountDTOFactory dtoFactory;

	JPA_AccountDAO() {
		dtoFactory = (AccountDTOFactory) DTOFactory.getDTOFactory(DTOFactory.Type.Account);
	}

	public Account getAccountById(long accountId) {
		Account account = entityManager.find(Account.class, accountId);
		return account;
	}

	public Account getAccountByCredentials(String email, String password) {
		TypedQuery<Account> typedQuery = entityManager.createNamedQuery("Account.getAccountByCredentials", Account.class);
		typedQuery.setParameter("email", email);
		typedQuery.setParameter("password", password);

		Account account;
		try {
			account = typedQuery.getSingleResult();
		} catch (NoResultException e) {
			account = null;
		}
		return account;
	}

	public Account createAccount(AccountDTO accountDTO) {
		Account account = dtoFactory.createObject(accountDTO);
		entityManager.persist(account);

		return account;
	}

}
