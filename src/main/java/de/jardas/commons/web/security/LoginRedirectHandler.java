package de.jardas.commons.web.security;

import org.springframework.core.Ordered;
import org.springframework.security.core.Authentication;

public interface LoginRedirectHandler extends Ordered {
	String createRedirectUrl(final Authentication authentication);
}
