package com.tamoino.core.persistence.jpa;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tamoino.core.dto.DTOFactory;
import com.tamoino.core.dto.EntryDTOFactory;
import com.tamoino.core.dto.EntrySimpleDTO;
import com.tamoino.core.model.Entry;
import com.tamoino.core.persistence.EntryDAO;

@Transactional(propagation = Propagation.MANDATORY)
public class JPA_EntryDAO implements EntryDAO {

	@PersistenceContext
	private EntityManager entityManager;
	private EntryDTOFactory dtoFactory;

	JPA_EntryDAO() {
		this.dtoFactory = (EntryDTOFactory) DTOFactory.getDTOFactory(DTOFactory.Type.Entry);
	}

	public void addEntry(Entry entry) {
		if (entry == null) {
			throw new IllegalArgumentException();
		}
		entityManager.persist(entry);
	}

	public void saveEntry(Entry entry) {
		if (entry == null) {
			throw new IllegalArgumentException();
		}
		entityManager.merge(entry);
	}

	public void deleteEntryById(long entryId) {
		Entry entry = entityManager.find(Entry.class, entryId);
		entityManager.remove(entry);
	}

	public Entry getEntryById(long entryId) {
		Entry entry = entityManager.find(Entry.class, entryId);
		return entry;
	}

	public String getURLByKey(String key) {
		TypedQuery<String> typedQuery = entityManager.createNamedQuery("Entry.getURLByKey", String.class);
		typedQuery.setParameter("key", key);
		String url = typedQuery.getSingleResult();
		return url;
	}

	@SuppressWarnings("unchecked")
	public List<String> getAllActiveKeys() {
		Query query = entityManager.createNamedQuery("Entry.getAllActiveKeys");
		return query.getResultList();
	}

	@SuppressWarnings("unchecked")
	private List<Entry> getEntriesByAccountId(long accountId) {
		Query query = entityManager.createNamedQuery("Entry.getEntriesByAccountId");
		query.setParameter("accountId", accountId);
		List<Entry> list = query.getResultList();
		return list;
	}

	public List<EntrySimpleDTO> getEntrySimpleDTOsByAccountId(long accountId) {
		List<Entry> entryList = getEntriesByAccountId(accountId);
		List<EntrySimpleDTO> dtoList = dtoFactory.createSimpleDTOList(entryList);
		return dtoList;
	}
}
