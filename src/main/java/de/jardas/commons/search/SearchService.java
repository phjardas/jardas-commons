package de.jardas.commons.search;

public interface SearchService<E, Q extends SearchQuery<E>> {
	SearchResult<E> search(Q search);
}
