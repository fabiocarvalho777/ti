package com.tamoino.core.dto;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class EntryDTO extends EntrySimpleDTO {

	private static final long serialVersionUID = -3498155570201188905L;

	private List<Date> history = new ArrayList<Date>();

	public EntryDTO(long accountId, long groupId, String word, String url, String groupName, String key, boolean active,
			Date expirationDate, List<Date> history) {
		super(accountId, groupId, word, url, groupName, key, active, expirationDate);
		setHistory(history);
	}

	public EntryDTO(long accountId, long groupId, String url, String key, boolean active, Date expirationDate,
			List<Date> history) {
		this(accountId, groupId, null, url, null, key, active, expirationDate, history);
	}

	private void setHistory(List<Date> history) {
		if (history == null) {
			history = new ArrayList<Date>();
		}
		this.history = history;
	}

	public List<Date> getHistory() {
		return history;
	}

	public int getHistoryCount() {
		return history.size();
	}

}
