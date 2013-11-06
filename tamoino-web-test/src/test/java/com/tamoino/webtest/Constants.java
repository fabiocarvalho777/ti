package com.tamoino.webtest;

public interface Constants {

	public enum Page {
		GO("Go page"), SIGNIN("Sign in"), SIGNOUT("Go page"), CREATEACCOUNT("Create your account"), MANAGEACCOUNT("Manage your account"), ABOUT(
				"What is Tamoino?"), ERROR("Boom!");

		private String title;

		private Page(String title) {
			this.title = title;
		}

		public String getTitle() {
			return title;
		}

		public String getLinkId() {
			return name() + "_PAGE_LINK";
		}
	};

	public interface Clickable {
		public String getName();
	}

	enum Button implements Clickable {
		GO_BUTTON, CREATE_ACCOUNT_BUTTON, SIGN_IN_BUTTON, ADD_ENTRY_BUTTON, ADD_ENTRYGROUP_BUTTON, _DELETE_ENTRY_BUTTON, _DELETE_ENTRYGROUP_BUTTON;

		public String getName() {
			return name();
		}
	}

	enum Checkbox implements Clickable {
		_ACTIVE_ENTRY_CHECKBOX;

		public String getName() {
			return name();
		}
	}

	enum Label {
	}

	enum Field {
	}

	enum Message {
	}

}
