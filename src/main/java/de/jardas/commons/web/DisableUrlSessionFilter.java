package de.jardas.commons.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import javax.servlet.http.HttpSession;

import org.springframework.web.filter.OncePerRequestFilter;

public class DisableUrlSessionFilter extends OncePerRequestFilter {
	@Override
	protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
			final FilterChain filterChain) throws ServletException, IOException {
		// clear session if session id in URL
		if (request.isRequestedSessionIdFromURL()) {
			final HttpSession session = request.getSession();
			if (session != null) {
				session.invalidate();
			}
		}

		// wrap response to remove URL encoding
		final HttpServletResponseWrapper wrappedResponse = new Wrapper(response);

		// process next request in chain
		filterChain.doFilter(request, wrappedResponse);
	}

	private static final class Wrapper extends HttpServletResponseWrapper {
		private Wrapper(final HttpServletResponse response) {
			super(response);
		}

		@Override
		public String encodeRedirectUrl(final String url) {
			return url;
		}

		@Override
		public String encodeRedirectURL(final String url) {
			return url;
		}

		@Override
		public String encodeUrl(final String url) {
			return url;
		}

		@Override
		public String encodeURL(final String url) {
			return url;
		}
	}
}