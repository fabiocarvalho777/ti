package com.tamoino.core.dto;

import java.io.Serializable;
import java.util.Date;

public class EntrySimpleDTO implements Serializable {

	private static final long serialVersionUID = 2056496642063437237L;

	private long id;
	private long accountId;
	private String word;
	private long groupId;
	private String url;
	private String key;
	private boolean active = true;
	private Date expirationDate = null;

	EntrySimpleDTO() {}

	public EntrySimpleDTO(long accountId, long groupId, String word, String url, String groupName, String key, boolean active, Date expirationDate) {
		setAccountId(accountId);
		setKey(key);
		setWord(word);
		setGroupId(groupId);
		setUrl(url);
		setActive(active);
		setExpirationDate(expirationDate);
	}

	public EntrySimpleDTO(long accountId, long groupId, String url, String key, boolean active, Date expirationDate) {
		this(accountId, groupId, null, url, null, key, active, expirationDate);
	}

	public EntrySimpleDTO(long accountId, long groupId, String url, String key, boolean active) {
		this(accountId, groupId, null, url, null, key, active, null);
	}

	public void setId(long id) {
		this.id = id;
	}

	private void setAccountId(long accountId) {
		this.accountId = accountId;
	}

	private void setGroupId(long groupId) {
		this.groupId = groupId;
	}

	private void setWord(String word) {
		if (word != null && !word.trim().equals("")) {
			this.word = word;
		} else {
			int i = -1;
			if (key != null) {
				i = key.indexOf('@');
			}
			if (i < 0) {
				this.word = key;
			} else {
				this.word = key.substring(0, i);
			}
		}
	}

	private void setUrl(String url) {
		this.url = url;
	}

	private void setKey(String key) {
		this.key = key;
	}

	private void setActive(boolean active) {
		this.active = active;
	}

	private void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public long getId() {
		return id;
	}

	public long getAccountId() {
		return accountId;
	}

	public long getGroupId() {
		return groupId;
	}

	public String getKey() {
		return key;
	}

	public String getURL() {
		return url;
	}

	public String getWord() {
		return word;
	}

	public boolean isActive() {
		return active;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof EntrySimpleDTO)) {
			return false;
		}
		EntrySimpleDTO dto = (EntrySimpleDTO) obj;
		if (!this.word.equals(dto.word)) {
			return false;
		}
		if (!this.url.equals(dto.url)) {
			return false;
		}
		if (!this.key.equals(dto.key)) {
			return false;
		}
		if (this.accountId != dto.accountId) {
			return false;
		}
		if (this.groupId != dto.groupId) {
			return false;
		}
		if (this.active != dto.active) {
			return false;
		}
		if (this.expirationDate != null && !this.expirationDate.equals(dto.expirationDate)) {
			return false;
		}
		return true;
	}

}
