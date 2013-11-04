package com.tamoino.core.model;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;

@Entity
@NamedQueries({ @NamedQuery(name = "Account.getAccountByCredentials", query = "select a from Account a where a.email = :email and a.password = :password"),
		@NamedQuery(name = "Account.getAccountTypeById", query = "select a.type from Account a where a.id = :id") })
public class Account implements Serializable {

	private static final long serialVersionUID = -7084184517411720605L;

	public enum AccountType {
		MASTER(), FREE(), PAID(-1);

		private int maxGroups = 1;
		private static AccountType[] externalValues = null;

		AccountType() {
		}

		AccountType(int maxGroups) {
			setMaxGroups(maxGroups);
		}

		private void setMaxGroups(int maxGroups) {
			this.maxGroups = maxGroups;
		}

		public int getMaxGroups() {
			return maxGroups;
		}

		public static AccountType[] externalValues() {
			if (externalValues == null) {
				setExternalValues();
			}
			return externalValues;
		}

		private static void setExternalValues() {
			AccountType[] values = values();
			int lenght = values.length - 1;
			externalValues = new AccountType[lenght];
			int i = 0;
			for (AccountType accountType : values) {
				if (accountType.equals(AccountType.MASTER)) {
					continue;
				}
				externalValues[i] = accountType;
				i++;
			}
		}
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@Enumerated(EnumType.STRING)
	private AccountType type;
	@Column(unique = true, nullable = false, length = 70)
	private String email;
	@Column(nullable = false, length = 10)
	private String password;
	// The key for this map of Entries is the Entry's key
	@OneToMany
	private Map<String, Entry> entries = new HashMap<String, Entry>();
	// The key for this map of Groups is the Group's name
	@OneToMany
	private Map<String, EntryGroup> groups = new HashMap<String, EntryGroup>();

	// Public no-argument constructor for JPA
	public Account() {
	}

	public Account(AccountType type, String email, String password) {
		setType(type);
		setEmail(email);
		setPassword(password);
	}

	public Account(AccountType type, String email, String password, Map<String, Entry> entries, Map<String, EntryGroup> groups) {
		this(type, email, password);
		setEntries(entries);
		setGroups(groups);
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

	private void setEntries(Map<String, Entry> entries) {
		if (entries == null) {
			throw new IllegalArgumentException();
		}
		this.entries = entries;
	}

	private void setGroups(Map<String, EntryGroup> groups) {
		this.groups = groups;
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

	public int getQuantityGroups() {
		return groups.size();
	}

	/**
	 * Returns a read-only map with all this account's Entries
	 * 
	 * @return
	 */
	public Map<String, Entry> getEntries() {
		return Collections.unmodifiableMap(entries);
	}

	/**
	 * Add the new Entry to this account. If an Entry with the same key already
	 * exists, then a IllegalArgumentException is thrown
	 * 
	 * @param entry
	 * @throws IllegalArgumentException
	 *             if an Entry with the same word already exists
	 */
	public void addEntry(Entry entry) throws IllegalArgumentException {
		if (entry == null || entries.containsKey(entry.getKey())) {
			throw new IllegalArgumentException();
		}
		entries.put(entry.getKey(), entry);
	}

	/**
	 * Remove the Entry based on its key. If no Entry with such key exists for
	 * this account, nothing happens
	 * 
	 * @param word
	 */
	public void removeEntry(String key) {
		entries.remove(key);
	}

	public Map<String, EntryGroup> getGroups() {
		return groups;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Account)) {
			return false;
		}
		Account account = (Account) obj;
		if (!this.type.equals(account.type)) {
			return false;
		}
		if (!this.email.equals(account.email)) {
			return false;
		}
		if (!this.entries.equals(account.entries)) {
			return false;
		}
		if (!this.groups.equals(account.groups)) {
			return false;
		}
		if (!this.password.equals(account.password)) {
			return false;
		}

		return true;
	}

}
