package com.tamoino.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Required;

import com.tamoino.core.FacadeInterface;

public class GoController {

	private FacadeInterface facade;

	@Required
	public void setFacade(FacadeInterface facade) {
		this.facade = facade;
	}

	public String getURLByKey(String key) {
		return facade.getURLByKey(key);
	}

	public List<String> getAllActiveKeys() {
		return facade.getAllActiveKeys();
	}

}
