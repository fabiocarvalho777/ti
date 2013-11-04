package com.tamoino.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

public class Page implements Comparable<Page> {

	private PageName pageName;
	private String resourceName;
	private int order;
	private Set<Page> linkedPages = new HashSet<Page>();

	private static boolean initialized = false;
	private static Set<Page> orderedPages = new TreeSet<Page>();
	private static Map<PageName, Page> mappedPages = new HashMap<PageName, Page>();
	private static final String HTML_TOOLBAR_ROW_FORMAT = "&nbsp;&nbsp;&nbsp;<a id='%s' href='%s'>%s</a>&nbsp;&nbsp;&nbsp;";

	public enum PageName {
		GO("Go page"), SIGNIN("Sign in"), SIGNOUT("Sign out"), CREATEACCOUNT("Create your account"), MANAGEACCOUNT("Manage your account"), ABOUT("What is Tamoino?"), ERROR("Boom!");

		private String title;
		
		private PageName(String title) {
			this.title = title;
		}
		
		public String getTitle() {
			return title;
		}
		
		public String getLinkId() {
			return name() + "_PAGE_LINK";
		}
	};

	private static void initialize() {
		if (initialized) {
			return;
		}

		final String signoutParams = RequestParameter.Action.name() + "=" + FrontControllerServlet.Action.SIGN_OUT.name();

		Page goPage = new Page(PageName.GO, "index.jsp");
		Page signinPage = new Page(PageName.SIGNIN, "signin.jsp");
		Page signoutPage = new Page(PageName.SIGNOUT, "tifc.do?" + signoutParams);
		Page createAccountPage = new Page(PageName.CREATEACCOUNT, "createAccount.jsp");
		Page manageAccountPage = new Page(PageName.MANAGEACCOUNT, "manageAccount.jsp");
		Page aboutPage = new Page(PageName.ABOUT, "about.jsp");
		Page errorPage = new Page(PageName.ERROR, "error.jsp");

		goPage.linkBothPages(signinPage);
		goPage.linkBothPages(createAccountPage);
		goPage.linkBothPages(aboutPage);

		signinPage.linkBothPages(createAccountPage);
		signinPage.linkBothPages(aboutPage);

		aboutPage.linkBothPages(createAccountPage);

		manageAccountPage.linkPage(signoutPage);

		errorPage.linkPage(signoutPage);

		initialized = true;
	}

	private Page(PageName pageName, String resourceName) {
		this(pageName, resourceName, pageName.ordinal());
	}

	private Page(PageName pageName, String resourceName, int order) {
		setName(pageName);
		setResourceName(resourceName);
		setOrder(order);
		addPage();
	}

	private void setName(PageName pageName) {
		if (pageName == null || mappedPages.containsKey(pageName)) {
			throw new IllegalArgumentException();
		}
		this.pageName = pageName;
	}

	private void setResourceName(String resourceName) {
		if (resourceName == null || resourceName.trim().equals("")) {
			throw new IllegalArgumentException();
		}
		this.resourceName = resourceName;
	}

	private void setOrder(int order) {
		this.order = order;
	}

	private void addPage() {
		orderedPages.add(this);
		mappedPages.put(pageName, this);
	}

	public static Page getPage(PageName pageName) {
		initialize();

		return mappedPages.get(pageName);
	}

	private void linkPage(Page page) {
		linkedPages.add(page);
	}

	private void linkBothPages(Page page) {
		linkPage(page);
		page.linkPage(this);
	}

	private List<Page> getOrderedLinkedPages() {
		List<Page> orderedLinkedPages = new ArrayList<Page>(orderedPages.size() - linkedPages.size());
		for (Page page : orderedPages) {
			if (linkedPages.contains(page)) {
				orderedLinkedPages.add(page);
			}
		}
		return orderedLinkedPages;
	}

	public int compareTo(Page page) {
		return (this.order - page.order);
	}

	public String getTitle() {
		return pageName.getTitle();
	}

	public String getHTMLToolbar() {
		StringBuilder sb = new StringBuilder("\"");

		String row;
		for (Page page : getOrderedLinkedPages()) {
			row = String.format(HTML_TOOLBAR_ROW_FORMAT, page.pageName.getLinkId(), page.resourceName, page.getTitle());
			sb.append(row);
		}
		sb.append("&nbsp;&nbsp;&nbsp;\"");
		String htmlToolbar = sb.toString();
		return htmlToolbar;
	}

	public String getRelativeURL() {
		return "/" + resourceName;
	}

}
