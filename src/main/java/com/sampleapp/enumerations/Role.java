package com.sampleapp.enumerations;


public enum Role {
	
	ROLE_ADMIN, ROLE_ORDINARY;

	private Role() {
	}

	public static Role get(int ordinal) {
		Role[] values = Role.values();
		for (Role role : values) {
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
