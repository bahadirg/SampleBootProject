package com.sampleapp.enumerations;


public enum UserState {
	
	PASSIVE, ACTIVE, DELETED;


	private UserState() {
	}

	public static UserState get(int ordinal) {
		UserState[] values = UserState.values();
		for (UserState role : values) {
			if (role.ordinal() == ordinal) {
				return role;
			}
		}

		return null;
	}

	public String getName() {
		return name();
	}
}
