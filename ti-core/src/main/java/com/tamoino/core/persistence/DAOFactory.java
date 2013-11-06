package com.tamoino.core.persistence;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.transaction.annotation.Transactional;

public abstract class DAOFactory {

	@Autowired
	private ApplicationContext applicationContext;

	// For test purposes using embedded memory DBMS
	private boolean addFunctionalData = false;
	private boolean addDemoData = false;

	public void setAddFunctionalData(boolean addFunctionalData) {
		this.addFunctionalData = addFunctionalData;
	}

	public void setAddDemoData(boolean addDemoData) {
		this.addDemoData = addDemoData;
	}

	private DAOFactory getSpringProxy() {
		return applicationContext.getBean(this.getClass());
	}

	@PostConstruct
	@Transactional
	public void init() {
		if (addFunctionalData) {
			System.out.println("--------------------------------------------");
			System.out.println("- Adding functional data");
			getSpringProxy().addFunctionalData();
			System.out.println("- Functional data has been added");
			System.out.println("--------------------------------------------");
		}
		if (addDemoData) {
			System.out.println("--------------------------------------------");
			System.out.println("- Adding demo data");
			getSpringProxy().addDemoData();
			System.out.println("- Demo data has been added");
			System.out.println("--------------------------------------------");
		}
	}

	public abstract void addFunctionalData();

	public abstract void addDemoData();

	public abstract AccountDAO createAccountDAO();

	public abstract EntryDAO createEntryDAO();

	public abstract EntryGroupDAO createEntryGroupDAO();

}
