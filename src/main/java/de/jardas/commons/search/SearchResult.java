package de.jardas.commons.search;

import java.io.Serializable;
import java.util.List;

public class SearchResult<E> implements Serializable {
	private static final long serialVersionUID = 1L;
	private final SearchQuery<E> query;
	private final List<E> pageItems;
	private final int totalCount;
	private final int numberOfPages;
	private final int pageStartIndex;
	private final int pageEndIndex;

	public SearchResult(final SearchQuery<E> query, final List<E> pageItems, final int totalCount) {
		this.query = query;
		this.pageItems = pageItems;
		this.totalCount = totalCount;
		this.numberOfPages = calculateNumberOfPages(totalCount, query.getItemsPerPage());
		this.pageStartIndex = query.getPageIndex() * query.getItemsPerPage();
		this.pageEndIndex = calculatePageEndIndex(query.getPageIndex(), query.getItemsPerPage(), totalCount);
	}

	public List<E> getPageItems() {
		return pageItems;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public int getPageIndex() {
		return query.getPageIndex();
	}

	public int getNumberOfPages() {
		return numberOfPages;
	}

	public boolean isPreviousPage() {
		return getPageIndex() > 0;
	}

	public boolean isNextPage() {
		return getPageIndex() < getNumberOfPages() - 1;
	}

	public int getPageStartIndex() {
		return pageStartIndex;
	}

	public int getPageEndIndex() {
		return pageEndIndex;
	}

	public int getItemsPerPage() {
		return query.getItemsPerPage();
	}

	public SearchQuery<E> getQuery() {
		return query;
	}

	private static int calculateNumberOfPages(final int totalCount, final int itemsPerPage) {
		if (totalCount <= itemsPerPage) {
			return 1;
		}

		int pages = totalCount / itemsPerPage;

		if (pages * itemsPerPage < totalCount) {
			pages++;
		}

		return pages;
	}

	private static int calculatePageEndIndex(final int pageIndex, final int itemsPerPage, final int totalCount) {
		final int end = (pageIndex + 1) * itemsPerPage;

		return end <= totalCount ? end : totalCount;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "<query=" + query + ", totalCount=" + totalCount + ", numberOfPages="
				+ numberOfPages + ", startIndex=" + pageStartIndex + ", endIndex=" + pageEndIndex + ">";
	}
}
