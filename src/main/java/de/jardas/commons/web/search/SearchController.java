package de.jardas.commons.web.search;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import de.jardas.commons.search.SearchQuery;
import de.jardas.commons.search.SearchResult;
import de.jardas.commons.search.SearchService;

@Controller
@SessionAttributes(SearchController.QUERY_MODEL)
public abstract class SearchController<E, Q extends SearchQuery<E>> {
	public static final String QUERY_MODEL = "query";
	private final String viewName;
	private final String url;

	public SearchController(final String viewName, final String url) {
		this.viewName = viewName;
		this.url = url;
	}

	@RequestMapping
	public String performSearch(@ModelAttribute(QUERY_MODEL) final Q query, final BindingResult binding,
			@RequestParam(value = "searching", required = false, defaultValue = "false") final boolean searching,
			@RequestParam(value = "reset", required = false) final String reset, final ModelMap model,
			final SessionStatus session) {
		if (StringUtils.hasText(reset) || !createDefaultSearchQuery().getClass().equals(query.getClass())) {
			session.setComplete();

			return "redirect:" + url;
		}

		if (!binding.hasErrors()) {
			final SearchResult<E> results = getSearchService().search(query);

			if (searching) {
				final List<E> items = results.getPageItems();

				if (items.size() == 1) {
					final String view = viewOnSingleHit(items.get(0));

					if (view != null) {
						return view;
					}
				}
			}

			model.addAttribute("result", results);
		}

		return viewName;
	}

	protected String viewOnSingleHit(final E e) {
		return null;
	}

	protected abstract SearchService<E, Q> getSearchService();

	@ModelAttribute(QUERY_MODEL)
	public Q getSearchQuery() {
		return createDefaultSearchQuery();
	}

	protected abstract Q createDefaultSearchQuery();
}
