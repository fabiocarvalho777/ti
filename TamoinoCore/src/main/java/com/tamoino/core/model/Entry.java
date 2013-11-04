package com.tamoino.core.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@NamedQueries({
	@NamedQuery(name = "Entry.getURLByKey", query = "select e.url from Entry e where e.key = :key and e.active = true"),
	@NamedQuery(name = "Entry.getAllActiveKeys", query = "select e.key from Entry e where e.active = true"),
	@NamedQuery(name = "Entry.getEntriesByAccountId", query = "select e from Entry e where e.account.id = :accountId")
})
public class Entry implements Serializable {

	private static final long serialVersionUID = 25779392556727360L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;
	@ManyToOne
	private Account account;
	@ManyToOne
	private EntryGroup entryGroup;
	@Column(nullable = false, length = 16)
	private String word;
	private String url;
	@Column(name = "entryKey", nullable = false, length = 33, unique = true)
	private String key;
	// Keeps an ordered list of all dates when this Entry's URL was
	@ElementCollection
	@Temporal(value = TemporalType.TIMESTAMP)
	private List<Date> history = new ArrayList<Date>();
	// A not active Entry will not show up for end users (or external systems)
	// but will still exist in the system, in case it is decided it should be
	// activated again in the future
	private boolean active = true;
	// An Entry might have an expiration date. After that date, the Entry is no
	@Temporal(value = TemporalType.DATE)
	private Date expirationDate;
	@Temporal(value = TemporalType.DATE)
	private Date activationDate;

	// Public no-argument constructor for JPA
	public Entry() {
	}

	public Entry(Account account, EntryGroup group, String word, String url) {
		setAccount(account);
		setGroup(group);
		setWord(word);
		setUrl(url);
		setKey();
	}

	private void setAccount(Account account) {
		this.account = account;
	}

	private void setWord(String word) {
		this.word = word;
	}

	private void setGroup(EntryGroup group) {
		this.entryGroup = group;
	}

	private void setUrl(String url) {
		this.url = url;
	}

	private void setKey() {
		if (entryGroup.isPrivate()) {
			key = String.format("%s@%s", word, entryGroup.getName());
		} else {
			// Assuming that group is the Public one
			key = word;
		}
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public void setExpirationDate(Date expirationDate) {
		this.expirationDate = expirationDate;
	}

	public void setActivationDate(Date activationDate) {
		this.activationDate = activationDate;
	}

	public long getId() {
		return id;
	}

	public Account getAccount() {
		return account;
	}

	public String getWord() {
		return word;
	}

	public EntryGroup getGroup() {
		return entryGroup;
	}

	public String getURL() {
		return url;
	}

	public String getKey() {
		return key;
	}

	/**
	 * This method tracks the retrieval of this entry's URL request by recording
	 * the date when it happened in this entry's history
	 * 
	 * @return
	 */
	public String requestURL() {
		Date today = new Date();
		history.add(today);

		return getURL();
	}

	public List<Date> getHistory() {
		return Collections.unmodifiableList(history);
	}

	public int getHistoryCount() {
		return history.size();
	}

	/**
	 * Returns all Dates when this Entry's URL was requested, but only after
	 * cutDate
	 * 
	 * @param cutDate
	 *            The date to be used as a parameter for the partial history of
	 *            URL requests for this Entry
	 * @return
	 */
	public Date[] getPartialHistory(Date cutDate) {
		if (cutDate == null) {
			throw new IllegalArgumentException();
		}

		Date[] historyArray = (Date[]) history.toArray();
		int j = historyArray.length - 1;
		int i = j;
		Date indexDate;
		while (i >= 0) {
			indexDate = historyArray[i];
			if (cutDate.compareTo(indexDate) == 0) {
				break;
			} else if (cutDate.compareTo(indexDate) < 0) {
				// cutDate is before indexDate
				i = i / 2;
			} else {
				// cutDate is after indexDate
				do {
					i++;
				} while (cutDate.compareTo(indexDate) > 0);
				break;
			}
		}

		Date[] patialHistoryArray = Arrays.copyOfRange(historyArray, i, j);

		return patialHistoryArray;
	}

	public int getPartialHistoryCount(Date cutDate) {
		return getPartialHistory(cutDate).length;
	}

	/**
	 * Simply returns true if the Entry is active, regardless of the expiration
	 * date. In order to ensure that this Entry's active state is accurate, an
	 * external object should use the method {@link #updateState()} often. In
	 * order to make sure the accurate state of the Entry is returned
	 * (respecting the Expiration Date) the method {@link #checkExpiration()}
	 * must be used instead.
	 * 
	 * @return
	 */
	public boolean isActive() {
		return active;
	}

	public Date getExpirationDate() {
		return expirationDate;
	}

	public Date getActivationDate() {
		return activationDate;
	}

	/**
	 * Update the state of the entry (checking the expiration date)
	 * 
	 * @return true only if this Entry is still active
	 */
	public void updateState() {
		if (expirationDate != null) {
			Date today = new Date();
			if (today.compareTo(expirationDate) > 0) {
				setActive(false);
			}
		}
	}

	/**
	 * Update the state of the entry (checking the expiration date) and then
	 * returns true or false based on that
	 * 
	 * @return true only if this Entry is still active
	 */
	public boolean checkExpiration() {
		updateState();
		return isActive();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Entry)) {
			return false;
		}
		Entry entry = (Entry) obj;
		if (!this.word.equals(entry.word)) {
			return false;
		}
		if (!this.url.equals(entry.url)) {
			return false;
		}
		if (!this.key.equals(entry.key)) {
			return false;
		}
		if (!this.account.equals(entry.account)) {
			return false;
		}
		if (!this.entryGroup.equals(entry.entryGroup)) {
			return false;
		}
		if (!this.history.equals(entry.history)) {
			return false;
		}
		if (this.active != entry.active) {
			return false;
		}
		if (!this.activationDate.equals(entry.activationDate)) {
			return false;
		}
		if (!this.expirationDate.equals(entry.expirationDate)) {
			return false;
		}
		return true;
	}

}
