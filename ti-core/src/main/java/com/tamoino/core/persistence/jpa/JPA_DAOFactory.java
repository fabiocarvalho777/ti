package com.tamoino.core.persistence.jpa;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.transaction.annotation.Transactional;

import com.tamoino.core.model.Account;
import com.tamoino.core.model.Account.AccountType;
import com.tamoino.core.model.Entry;
import com.tamoino.core.model.EntryGroup;
import com.tamoino.core.model.EntryGroup.EntryGroupType;
import com.tamoino.core.persistence.AccountDAO;
import com.tamoino.core.persistence.DAOFactory;
import com.tamoino.core.persistence.EntryDAO;
import com.tamoino.core.persistence.EntryGroupDAO;

public class JPA_DAOFactory extends DAOFactory {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	@Transactional
	public void addFunctionalData() {
		Account masterAccount = new Account(AccountType.MASTER, "MASTER", "MASTER");
		EntryGroup publicEntryGroup = new EntryGroup(masterAccount, EntryGroupType.PUBLIC, "PUBLIC");

		entityManager.persist(masterAccount);
		entityManager.persist(publicEntryGroup);
	}

	@Override
	@Transactional
	public void addDemoData() {
		TypedQuery<EntryGroup> typedQuery = entityManager.createNamedQuery("EntryGroup.getPublicGroup", EntryGroup.class);
		EntryGroup publicGroup = typedQuery.getSingleResult();

		// Free accounts
		Account freezaoAccount = new Account(AccountType.FREE, "freezao@freezao.com", "freezao");
		Account gundegaAccount = new Account(AccountType.FREE, "gundega@gmail.com", "gundega");
		Account jharrisAccount = new Account(AccountType.FREE, "jharris@hotmail.com", "jharris");
		Account toadiesAccount = new Account(AccountType.FREE, "toadies@yahoo.com", "toadies");
		Account inowesAccount = new Account(AccountType.FREE, "inowes@hotmail.com", "inowes");
		entityManager.persist(freezaoAccount);
		entityManager.persist(gundegaAccount);
		entityManager.persist(jharrisAccount);
		entityManager.persist(toadiesAccount);
		entityManager.persist(inowesAccount);

		// Paid accounts
		Account paidaoAccount = new Account(AccountType.PAID, "paidao@paidao.com", "paidao");
		Account fordAccount = new Account(AccountType.PAID, "admin@ford.com", "fordao");
		Account sxswAccount = new Account(AccountType.PAID, "admin@sxsw.com", "sxswsw");
		Account coelhoAccount = new Account(AccountType.PAID, "paulo@coelho.com", "coelho");
		Account dannonAccount = new Account(AccountType.PAID, "admin@dannon.com", "dannon");
		Account universalAccount = new Account(AccountType.PAID, "admin@universal.com", "universal");
		Account ftHondaAccount = new Account(AccountType.PAID, "admin@firsttexashonda.com", "texashonda");
		Account ibmAccount = new Account(AccountType.PAID, "jblack@us.ibm.com", "jblack");
		Account gsimonsAccount = new Account(AccountType.PAID, "gsimons@us.ibm.com", "gsimons");
		entityManager.persist(paidaoAccount);
		entityManager.persist(fordAccount);
		entityManager.persist(sxswAccount);
		entityManager.persist(coelhoAccount);
		entityManager.persist(dannonAccount);
		entityManager.persist(universalAccount);
		entityManager.persist(ftHondaAccount);
		entityManager.persist(ibmAccount);
		entityManager.persist(gsimonsAccount);

		// Private entry groups from free accounts
		EntryGroup freezaoGroup = new EntryGroup(freezaoAccount, EntryGroupType.PRIVATE, "freezao");
		EntryGroup gundegaGroup = new EntryGroup(gundegaAccount, EntryGroupType.PRIVATE, "gundega");
		EntryGroup jharrisGroup = new EntryGroup(jharrisAccount, EntryGroupType.PRIVATE, "jharris");
		EntryGroup toadiesGroup = new EntryGroup(toadiesAccount, EntryGroupType.PRIVATE, "toadies");
		EntryGroup inowesGroup = new EntryGroup(inowesAccount, EntryGroupType.PRIVATE, "inowes");
		entityManager.persist(freezaoGroup);
		entityManager.persist(gundegaGroup);
		entityManager.persist(jharrisGroup);
		entityManager.persist(toadiesGroup);
		entityManager.persist(inowesGroup);

		// Private entry groups from paid accounts
		EntryGroup paidaoGroup = new EntryGroup(paidaoAccount, EntryGroupType.PRIVATE, "paidao");
		EntryGroup fordGroup = new EntryGroup(fordAccount, EntryGroupType.PRIVATE, "ford");
		EntryGroup sxswGroup = new EntryGroup(sxswAccount, EntryGroupType.PRIVATE, "sxsw");
		EntryGroup coelhoGroup = new EntryGroup(coelhoAccount, EntryGroupType.PRIVATE, "coelho");
		EntryGroup dannonGroup = new EntryGroup(dannonAccount, EntryGroupType.PRIVATE, "nestle");
		EntryGroup ibmPulseGroup = new EntryGroup(ibmAccount, EntryGroupType.PRIVATE, "pulse");
		EntryGroup ibmGroup = new EntryGroup(gsimonsAccount, EntryGroupType.PRIVATE, "ibm");
		entityManager.persist(paidaoGroup);
		entityManager.persist(fordGroup);
		entityManager.persist(sxswGroup);
		entityManager.persist(coelhoGroup);
		entityManager.persist(dannonGroup);
		entityManager.persist(ibmPulseGroup);
		entityManager.persist(ibmGroup);

		// Entries for private groups
		Entry asilvaFreezaoEntry = new Entry(freezaoAccount, freezaoGroup, "asilva", "http://www.ufc.com/fighter/Anderson-Silva");
		Entry contactGundegaEntry = new Entry(gundegaAccount, gundegaGroup, "contact", "http://www.gundegayoga.com/contact.html");
		Entry yogaatworkGundegaEntry = new Entry(gundegaAccount, gundegaGroup, "yogaatwork", "http://www.gundegayoga.com/yoga-at-work.html");
		Entry yogaatparkGundegaEntry = (new Entry(gundegaAccount, gundegaGroup, "yogaatpark", "http://www.gundegayoga.com/yoga-at-park.html"));
		yogaatparkGundegaEntry.setActive(false);
		Entry pizzaPaidaoEntry = new Entry(paidaoAccount, paidaoGroup, "pizza", "http://eastsidepies.com/");
		Entry investorsFordEntry = new Entry(fordAccount, fordGroup, "investors", "http://corporate.ford.com/our-company/investors");
		Entry accordJharrisEntry = new Entry(jharrisAccount, jharrisGroup, "accord", "http://austin.craigslist.org/cto/2902053478.html");
		Entry stubsToadiesEntry = new Entry(toadiesAccount, toadiesGroup, "stubs", "https://www.facebook.com/events/256068321148553/");
		Entry algebraInowesEntry = new Entry(inowesAccount, inowesGroup, "algebra", "http://linear.ups.edu/download/fcla-screen-2.30.pdf");
		Entry pptsPulseEntry = new Entry(ibmAccount, ibmPulseGroup, "ppts", "http://portal.pulse12.alliancetech.com/");
		Entry opinePulseEntry = new Entry(ibmAccount, ibmPulseGroup, "opine", "http://www-01.ibm.com/software/tivoli/pulse/connect/index.html");
		Entry lovedPulseEntry = new Entry(ibmAccount, ibmPulseGroup, "loved", "http://www-01.ibm.com/software/tivoli/pulse/connect/index.html");
		Entry goodPulseEntry = new Entry(ibmAccount, ibmPulseGroup, "good", "http://www-01.ibm.com/software/tivoli/pulse/connect/index.html");
		Entry sosoPulseEntry = new Entry(ibmAccount, ibmPulseGroup, "soso", "http://www-01.ibm.com/software/tivoli/pulse/connect/index.html");
		Entry devopsIbmEntry = new Entry(gsimonsAccount, ibmGroup, "devops", "http://www-01.ibm.com/software/rational/devops/");
		entityManager.persist(asilvaFreezaoEntry);
		entityManager.persist(contactGundegaEntry);
		entityManager.persist(yogaatworkGundegaEntry);
		entityManager.persist(yogaatparkGundegaEntry);
		entityManager.persist(pizzaPaidaoEntry);
		entityManager.persist(investorsFordEntry);
		entityManager.persist(accordJharrisEntry);
		entityManager.persist(stubsToadiesEntry);
		entityManager.persist(algebraInowesEntry);
		entityManager.persist(pptsPulseEntry);
		entityManager.persist(opinePulseEntry);
		entityManager.persist(lovedPulseEntry);
		entityManager.persist(goodPulseEntry);
		entityManager.persist(sosoPulseEntry);
		entityManager.persist(devopsIbmEntry);

		// Entries for the PUBLIC group
		Entry fordEntry = new Entry(fordAccount, publicGroup, "ford", "http://www.ford.com");
		Entry focus2012Entry = new Entry(fordAccount, publicGroup, "focus2012", "http://www.ford.com/cars/focus/?intcmp=fv-hpbb-dflt-40mpg-focus");
		Entry focus2013Entry = new Entry(fordAccount, publicGroup, "focus2013", "http://www.ford.com/cars/focus2013");
		Entry sxswEntry = new Entry(sxswAccount, publicGroup, "sxsw", "http://sxsw.com/");
		Entry coelhonewbookEntry = new Entry(coelhoAccount, publicGroup, "coelhonewbook", "http://paulocoelhoblog.com/2011/01/05/the-aleph/");
		Entry activiaEntry = new Entry(dannonAccount, publicGroup, "activia", "http://www.activia.us.com/promotions");
		Entry expeditionEntry = new Entry(fordAccount, publicGroup, "expedition", "http://www.ford.com/suvs/expedition/");
		Entry maroon5Entry = new Entry(universalAccount, publicGroup, "maroon5", "http://www.maroon5.com/");
		Entry hondaaustinEntry = new Entry(ftHondaAccount, publicGroup, "hondaaustin", "http://www.firsttexashonda.com/");
		entityManager.persist(fordEntry);
		entityManager.persist(focus2012Entry);
		entityManager.persist(focus2013Entry);
		entityManager.persist(sxswEntry);
		entityManager.persist(coelhonewbookEntry);
		entityManager.persist(activiaEntry);
		entityManager.persist(expeditionEntry);
		entityManager.persist(maroon5Entry);
		entityManager.persist(hondaaustinEntry);
	}

	@Override
	public AccountDAO createAccountDAO() {
		return new JPA_AccountDAO();
	}

	@Override
	public EntryDAO createEntryDAO() {
		return new JPA_EntryDAO();
	}

	@Override
	public EntryGroupDAO createEntryGroupDAO() {
		return new JPA_EntryGroupDAO();
	}

}
