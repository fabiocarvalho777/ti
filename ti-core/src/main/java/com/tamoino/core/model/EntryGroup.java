package com.tamoino.core.model;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@NamedQueries({ @NamedQuery(name = "EntryGroup.getPublicGroup", query = "select g from EntryGroup g where g.name = 'PUBLIC'"),
		@NamedQuery(name = "EntryGroup.getEntryGroupByAccountId", query = "select g from EntryGroup g where g.account.id = :accountId"),
		@NamedQuery(name = "EntryGroup.getSingleGroupIdByAccountId", query = "select g.id from EntryGroup g where g.account.id = :accountId"),
		@NamedQuery(name = "EntryGroup.getEntriesCountByEntryGroupId", query = "select count(e) from Entry e where e.entryGroup.id = :entryGroupId") })
public class EntryGroup implements Serializable {

	private static final long serialVersionUID = -6071917746449109949L;

	public enum EntryGroupType {
		PUBLIC, PRIVATE;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@ManyToOne
	private Account account;
	@Enumerated(EnumType.STRING)
	private EntryGroupType type;
	@Column(nullable = false, length = 16, unique = true)
	private String name;

	// Public no-argument constructor for JPA
	public EntryGroup() {
	}

	public EntryGroup(Account account, EntryGroupType type, String name) {
		setAccount(account);
		setType(type);
		setName(name);
	}

	private void setAccount(Account account) {
		this.account = account;
	}

	private void setType(EntryGroupType type) {
		this.type = type;
	}

	private void setName(String name) {
		this.name = name;
	}

	public long getId() {
		return id;
	}

	public Account getAccount() {
		return account;
	}

	public EntryGroupType getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public boolean isPrivate() {
		return (type.equals(EntryGroupType.PRIVATE));
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof EntryGroup)) {
			return false;
		}
		EntryGroup dto = (EntryGroup) obj;
		if (this.id != dto.id) {
			return false;
		}
		if (!this.account.equals(dto.account)) {
			return false;
		}
		if (!this.name.equals(dto.name)) {
			return false;
		}
		if (!this.type.equals(dto.type)) {
			return false;
		}
		return true;
	}

}
