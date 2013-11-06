package com.tamoino.web;

import java.io.IOException;

import javax.persistence.EntityExistsException;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.tamoino.core.dto.AccountDTO;
import com.tamoino.core.dto.EntrySimpleDTO;
import com.tamoino.core.model.Account;
import com.tamoino.core.model.Account.AccountType;
import com.tamoino.web.Page.PageName;
import com.tamoino.web.controller.AccountController;
import com.tamoino.web.controller.EntryController;
import com.tamoino.web.controller.EntryGroupController;
import com.tamoino.web.controller.GoController;

public class FrontControllerServlet extends HttpServlet {

	public enum Action {
		GO, ADD_ENTRY, ADD_GROUP, CREATE_ACCOUNT, DELETE_GROUP, DELETE_ENTRY, ACTIVE_ENTRY, SIGN_IN, SIGN_OUT
	}

	private static final long serialVersionUID = -3093858185904875318L;

	private static final Object RESULT_MESSAGE_CLASS = "resultMessage";
	private static final Object ERROR_MESSAGE_CLASS = "errorMessage";

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String _action = request.getParameter(RequestParameter.Action.name());
		Action action = Action.valueOf(_action);

		// SIGN_OUT is the only action that is allowed to be requested by a HTTP
		// GET request. All the others must be done necessarily by POST
		if (action.equals(Action.SIGN_OUT)) {
			doPost(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			ApplicationContext beanFactory = WebApplicationContextUtils.getRequiredWebApplicationContext(getServletContext());

			String _action = request.getParameter(RequestParameter.Action.name());
			Action action = Action.valueOf(_action);

			switch (action) {
			case GO:
				go(request, response, beanFactory);
				break;
			case CREATE_ACCOUNT:
				createAccount(request, response, beanFactory);
				break;
			case SIGN_IN:
				signIn(request, response, beanFactory);
				break;
			case SIGN_OUT:
				signOut(request, response, beanFactory);
				break;
			case ADD_ENTRY:
				addEntry(request, response, beanFactory);
				break;
			case DELETE_ENTRY:
				deleteEntry(request, response, beanFactory);
				break;
			case DELETE_GROUP:
				deleteGroup(request, response, beanFactory);
				break;
			case ACTIVE_ENTRY:
				activeEntry(request, response, beanFactory);
				break;
			case ADD_GROUP:
				addGroup(request, response, beanFactory);
				break;
			}
		} catch (Exception e) {
			request.setAttribute(RequestParameter.EXCEPTION.name(), e);
			String resource = Page.getPage(PageName.ERROR).getRelativeURL();
			getServletContext().getRequestDispatcher(resource).forward(request, response);
		}
	}

	private void go(HttpServletRequest request, HttpServletResponse response, ApplicationContext beanFactory) throws IOException, ServletException {
		String key = request.getParameter(RequestParameter.KEY.name());

		try {
			GoController goController = (GoController) beanFactory.getBean("goController");

			String goToURL = goController.getURLByKey(key);
			response.sendRedirect(goToURL);
		} catch (NoResultException e) {
			setErrorMessage(request, "There is no entry for " + key);
			String resource = Page.getPage(PageName.GO).getRelativeURL();
			getServletContext().getRequestDispatcher(resource).forward(request, response);
		}
	}

	private void createAccount(HttpServletRequest request, HttpServletResponse response, ApplicationContext beanFactory) throws IOException, ServletException {
		AccountType accountType = AccountType.valueOf(request.getParameter(RequestParameter.ACCOUNT_TYPE.name()));
		String email = request.getParameter(RequestParameter.EMAIL.name());
		String password = request.getParameter(RequestParameter.PASSWORD.name());
		String groupName = request.getParameter(RequestParameter.GROUP_NAME.name());

		AccountDTO accountDTO = new AccountDTO(accountType, email, password);

		String resource;

		try {
			AccountController accountController = (AccountController) beanFactory.getBean("accountController");
			accountController.createAccount(accountDTO, groupName);

			setResultMessage(request, "Account " + email + " has been created");
			resource = Page.getPage(PageName.SIGNIN).getRelativeURL();
			getServletContext().getRequestDispatcher(resource).forward(request, response);
		} catch (RuntimeException e) {
			if (e instanceof IllegalArgumentException) {
				setErrorMessage(request, "Invalid account information");
			} else if (e instanceof EntityExistsException || e instanceof PersistenceException) {
				setErrorMessage(request, "There is already an account with this e-mail or group name");
			}
			resource = Page.getPage(PageName.CREATEACCOUNT).getRelativeURL();
			getServletContext().getRequestDispatcher(resource).forward(request, response);
		}
	}

	private void signIn(HttpServletRequest request, HttpServletResponse response, ApplicationContext beanFactory) throws IOException, ServletException {
		String email = request.getParameter(RequestParameter.EMAIL.name());
		String password = request.getParameter(RequestParameter.PASSWORD.name());

		String resource;

		try {
			AccountController accountController = (AccountController) beanFactory.getBean("accountController");
			Account account = accountController.getAccountByCredentials(email, password);

			HttpSession session = request.getSession();
			session.setAttribute(SessionAttribute.ACCOUNT.name(), account);

			resource = Page.getPage(PageName.MANAGEACCOUNT).getRelativeURL();
			getServletContext().getRequestDispatcher(resource).forward(request, response);
		} catch (Exception e) {
			setErrorMessage(request, "The login id or password are invalid");
			resource = Page.getPage(PageName.SIGNIN).getRelativeURL();
			getServletContext().getRequestDispatcher(resource).forward(request, response);
		}
	}

	private void signOut(HttpServletRequest request, HttpServletResponse response, ApplicationContext beanFactory) throws IOException, ServletException {
		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute(SessionAttribute.ACCOUNT.name());
		String email = account.getEmail();
		session.invalidate();

		String resource = Page.getPage(PageName.GO).getRelativeURL();
		getServletContext().getRequestDispatcher(resource).forward(request, response);
		System.out.println(String.format("Session closed for e-mail %s", email));
	}

	private void addEntry(HttpServletRequest request, HttpServletResponse response, ApplicationContext beanFactory) throws IOException, ServletException {
		String key = request.getParameter(RequestParameter.NEW_KEY.name());
		String url = request.getParameter(RequestParameter.NEW_URL.name());

		String _groupId = request.getParameter(RequestParameter.NEW_GROUP_ID.name());
		long groupId = Long.parseLong(_groupId);

		String _active = request.getParameter(RequestParameter.NEW_ACTIVE.name());
		boolean active = false;
		if (_active != null && _active.equalsIgnoreCase("on")) {
			active = true;
		}

		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute(SessionAttribute.ACCOUNT.name());
		long accountId = account.getId();

		EntrySimpleDTO entryDescDTO = new EntrySimpleDTO(accountId, groupId, url, key, active);

		try {
			EntryController entryController = (EntryController) beanFactory.getBean("entryController");
			entryController.addEntry(entryDescDTO);
			setResultMessage(request, "Entry " + key + " has been added");
		} catch (EntityExistsException e) {
			setErrorMessage(request, "There is already an entry for " + key);
		} catch (PersistenceException e) {
			setErrorMessage(request, "There is already an entry for " + key);
		}

		String resource = Page.getPage(PageName.MANAGEACCOUNT).getRelativeURL();
		getServletContext().getRequestDispatcher(resource).forward(request, response);
	}

	private void deleteEntry(HttpServletRequest request, HttpServletResponse response, ApplicationContext beanFactory) throws IOException, ServletException {
		String _entryId = request.getParameter(RequestParameter.ENTRY_ID.name());
		long entryId = Long.parseLong(_entryId);

		String key = request.getParameter(RequestParameter.KEY.name());

		try {
			EntryController entryController = (EntryController) beanFactory.getBean("entryController");
			entryController.deleteEntry(entryId);
			setResultMessage(request, "Entry " + key + " has been deleted");
		} catch (IllegalArgumentException e) {
			setErrorMessage(request, "An error happened when deleting entry " + key);
		}

		String resource = Page.getPage(PageName.MANAGEACCOUNT).getRelativeURL();
		getServletContext().getRequestDispatcher(resource).forward(request, response);
	}

	private void deleteGroup(HttpServletRequest request, HttpServletResponse response, ApplicationContext beanFactory) throws IOException, ServletException {
		String _groupId = request.getParameter(RequestParameter.GROUP_ID.name());
		long groupId = Long.parseLong(_groupId);

		String groupName = request.getParameter(RequestParameter.GROUP_NAME.name());

		try {
			EntryGroupController entryGroupController = (EntryGroupController) beanFactory.getBean("entryGroupController");
			entryGroupController.deleteGroup(groupId);
			setResultMessage(request, "Entry group " + groupName + " has been deleted");
		} catch (PersistenceException e) {
			setErrorMessage(request, "Entry group " + groupName + " cannot be deleted because it still has entries");
		}

		String resource = Page.getPage(PageName.MANAGEACCOUNT).getRelativeURL();
		getServletContext().getRequestDispatcher(resource).forward(request, response);
	}

	private void activeEntry(HttpServletRequest request, HttpServletResponse response, ApplicationContext beanFactory) throws IOException, ServletException {
		String _entryId = request.getParameter(RequestParameter.ENTRY_ID.name());
		long entryId = Long.parseLong(_entryId);
		boolean active = new Boolean(request.getParameter(RequestParameter.ACTIVE.name()));

		String key = request.getParameter(RequestParameter.KEY.name());

		EntryController entryController = (EntryController) beanFactory.getBean("entryController");
		entryController.setEntryActiveById(entryId, active);

		if (active) {
			setResultMessage(request, "Entry " + key + " is now active");
		} else {
			setResultMessage(request, "Entry " + key + " is not active anymore");
		}

		String resource = Page.getPage(PageName.MANAGEACCOUNT).getRelativeURL();
		getServletContext().getRequestDispatcher(resource).forward(request, response);
	}

	private void addGroup(HttpServletRequest request, HttpServletResponse response, ApplicationContext beanFactory) throws IOException, ServletException {
		String groupName = request.getParameter(RequestParameter.NEW_GROUP_NAME.name());

		HttpSession session = request.getSession();
		Account account = (Account) session.getAttribute(SessionAttribute.ACCOUNT.name());

		try {
			EntryGroupController entryGroupController = (EntryGroupController) beanFactory.getBean("entryGroupController");
			entryGroupController.addGroup(account, groupName);
			setResultMessage(request, "Entry group " + groupName + " has been added");
		} catch (RuntimeException e) {
			if (e instanceof IllegalArgumentException) {
				setErrorMessage(request, "Invalid entry group information");
			} else if (e instanceof EntityExistsException) {
				setErrorMessage(request, "There is already an entry group called " + groupName);
			}
		}

		String resource = Page.getPage(PageName.MANAGEACCOUNT).getRelativeURL();
		getServletContext().getRequestDispatcher(resource).forward(request, response);
	}

	private void setResultMessage(HttpServletRequest request, String message) {
		request.setAttribute(RequestParameter.MESSAGE_CLASS.name(), RESULT_MESSAGE_CLASS);
		request.setAttribute(RequestParameter.MESSAGE.name(), message);
	}

	private void setErrorMessage(HttpServletRequest request, String message) {
		request.setAttribute(RequestParameter.MESSAGE_CLASS.name(), ERROR_MESSAGE_CLASS);
		request.setAttribute(RequestParameter.MESSAGE.name(), message);
	}

}
