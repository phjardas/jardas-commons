package de.jardas.commons.web.search;

import java.util.concurrent.Callable;

import de.jardas.commons.Preconditions;
import de.jardas.commons.search.SearchQuery;
import de.jardas.commons.search.SearchResult;
import de.jardas.commons.search.SearchService;

public final class SearchTask<E, Q extends SearchQuery<E>> implements Callable<SearchResult<E>> {
	private final SearchService<E, Q> searchService;
	private final Q search;

	private SearchTask(final SearchService<E, Q> searchService, final Q search) {
		this.searchService = Preconditions.notNull(searchService, "searchService");
		this.search = Preconditions.notNull(search, "search");
	}

	@Override
	public SearchResult<E> call() throws Exception {
		return searchService.search(search);
	}

	public static <E, Q extends SearchQuery<E>> SearchTask<E, Q> create(final SearchService<E, Q> searchService,
			final Q search) {
		return new SearchTask<E, Q>(searchService, search);
	}
}
