package de.jardas.commons.web.security;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import de.jardas.commons.spring.OrderedComparator;

public class RedirectAfterLoginHandler extends SavedRequestAwareAuthenticationSuccessHandler {
	@Autowired(required = false)
	private final List<LoginRedirectHandler> loginRedirectHandlers = new ArrayList<>();

	@PostConstruct
	public void initialize() {
		OrderedComparator.sort(loginRedirectHandlers);
	}

	@Override
	protected String determineTargetUrl(final HttpServletRequest request, final HttpServletResponse response) {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		for (final LoginRedirectHandler handler : loginRedirectHandlers) {
			final String url = handler.createRedirectUrl(authentication);

			if (url != null) {
				return url;
			}
		}

		return "/";
	}
}
