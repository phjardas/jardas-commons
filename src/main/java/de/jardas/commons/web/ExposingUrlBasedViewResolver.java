package de.jardas.commons.web;

import java.util.Locale;

import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.RedirectView;

public class ExposingUrlBasedViewResolver extends InternalResourceViewResolver {
	private boolean exposeModelAttributesOnRedirect = true;

	@Override
	protected View createView(final String viewName, final Locale locale) throws Exception {
		// If this resolver is not supposed to handle the given view,
		// return null to pass on to the next resolver in the chain.
		if (!canHandle(viewName, locale)) {
			return null;
		}

		// Check for special "redirect:" prefix.
		if (viewName.startsWith(REDIRECT_URL_PREFIX)) {
			final String redirectUrl = viewName.substring(REDIRECT_URL_PREFIX.length());

			return new RedirectView(redirectUrl, isRedirectContextRelative(), isRedirectHttp10Compatible(),
					exposeModelAttributesOnRedirect);
		}

		// Check for special "forward:" prefix.
		if (viewName.startsWith(FORWARD_URL_PREFIX)) {
			final String forwardUrl = viewName.substring(FORWARD_URL_PREFIX.length());

			return new InternalResourceView(forwardUrl);
		}

		// Else fall back to superclass implementation: calling loadView.
		return super.createView(viewName, locale);
	}

	public void setExposeModelAttributesOnRedirect(final boolean exposeModelAttributesOnRedirect) {
		this.exposeModelAttributesOnRedirect = exposeModelAttributesOnRedirect;
	}
}
