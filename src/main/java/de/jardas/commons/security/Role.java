package de.jardas.commons.security;

import java.util.ArrayList;
import java.util.List;

public enum Role {
	SYSTEM(false), ADMIN(true), OFFICE(true);

	private static Role[] assignables;
	private final boolean assignable;

	private Role(final boolean assignable) {
		this.assignable = assignable;
	}

	public boolean isAssignable() {
		return assignable;
	}

	public String getRoleName() {
		return "ROLE_" + name();
	}

	public static Role forAuthority(final String authority) {
		return valueOf(authority.replaceFirst("ROLE_", ""));
	}

	public static Role[] assignables() {
		if (assignables == null) {
			final List<Role> list = new ArrayList<Role>();

			for (final Role role : values()) {
				if (role.isAssignable()) {
					list.add(role);
				}
			}

			assignables = list.toArray(new Role[list.size()]);
		}

		return assignables;
	}
}
