package com.tamoino.core.persistence.jpa;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.tamoino.core.dto.DTOFactory;
import com.tamoino.core.dto.EntryGroupDTO;
import com.tamoino.core.dto.EntryGroupDTOFactory;
import com.tamoino.core.model.Account;
import com.tamoino.core.model.EntryGroup;
import com.tamoino.core.model.EntryGroup.EntryGroupType;
import com.tamoino.core.persistence.EntryGroupDAO;

@Transactional(propagation = Propagation.MANDATORY)
public class JPA_EntryGroupDAO implements EntryGroupDAO {

	@PersistenceContext
	private EntityManager entityManager;
	private EntryGroup publicEntryGroup = null;
	private EntryGroupDTO publicEntryGroupDTO = null;
	private EntryGroupDTOFactory dtoFactory;

	JPA_EntryGroupDAO() {
		this.dtoFactory = (EntryGroupDTOFactory) DTOFactory.getDTOFactory(DTOFactory.Type.EntryGroup);
	}

	public EntryGroup getEntryGroupById(long entryGroupId) {
		EntryGroup entryGroup = entityManager.find(EntryGroup.class, entryGroupId);
		return entryGroup;
	}

	@SuppressWarnings("unchecked")
	private List<EntryGroup> getEntryGroupListByAccountId(long accountId) {
		Query query = entityManager.createNamedQuery("EntryGroup.getEntryGroupByAccountId");
		query.setParameter("accountId", accountId);
		List<EntryGroup> list = (List<EntryGroup>) query.getResultList();
		return list;
	}

	public List<EntryGroupDTO> getEntryGroupDTOsListByAccountId(long accountId) {
		List<EntryGroup> entryList = getEntryGroupListByAccountId(accountId);
		List<EntryGroupDTO> dtoList = dtoFactory.createDTOList(entryList);
		return dtoList;
	}

	public Map<Long, EntryGroupDTO> getEntryGroupDTOsMapByAccountId(long accountId) {
		Map<Long, EntryGroupDTO> map = new HashMap<Long, EntryGroupDTO>();
		List<EntryGroupDTO> list = getEntryGroupDTOsListByAccountId(accountId);
		for (EntryGroupDTO dto : list) {
			map.put(dto.getId(), dto);
		}
		return map;
	}

	public long getSingleEntryGroupIdByAccountId(long accountId) {
		TypedQuery<Long> typedQuery = entityManager.createNamedQuery("EntryGroup.getSingleGroupIdByAccountId", Long.class);
		typedQuery.setParameter("accountId", accountId);
		long entryGroupId = (Long) typedQuery.getSingleResult();
		return entryGroupId;
	}

	public EntryGroup createEntryGroup(Account account, String groupName) {
		EntryGroup entryGroup = new EntryGroup(account, EntryGroupType.PRIVATE, groupName);
		entityManager.persist(entryGroup);
		return entryGroup;
	}

	public void deleteEntryGroupById(long entryGroupId) {
		EntryGroup entryGroup = entityManager.find(EntryGroup.class, entryGroupId);
		entityManager.remove(entryGroup);
	}

	public EntryGroupDTO getPublicEntryGroupDTO() {
		if (publicEntryGroupDTO == null) {
			EntryGroup publicEntryGroup = getPublicEntryGroup();
			publicEntryGroupDTO = dtoFactory.createDTO(publicEntryGroup);
		}

		return publicEntryGroupDTO;
	}

	public EntryGroup getPublicEntryGroup() {
		if (publicEntryGroup == null) {
			TypedQuery<EntryGroup> getPublicGroupNQ = entityManager.createNamedQuery("EntryGroup.getPublicGroup", EntryGroup.class);
			publicEntryGroup = getPublicGroupNQ.getSingleResult();
		}

		return publicEntryGroup;
	}

	public boolean isEntryGroupEmpty(long entryGroupId) {
		long entriesCount = getEntriesCountByEntryGroupId(entryGroupId);
		boolean isEmpty = (entriesCount == 0);

		return isEmpty;
	}

	public long getEntriesCountByEntryGroupId(long entryGroupId) {
		TypedQuery<Long> query = entityManager.createNamedQuery("EntryGroup.getEntriesCountByEntryGroupId", Long.class);
		query.setParameter("entryGroupId", entryGroupId);
		long entriesCount = query.getSingleResult();

		return entriesCount;
	}

}
