package de.jardas.commons.excel;

import java.io.OutputStream;
import java.util.List;
import java.util.Locale;

import de.jardas.commons.Preconditions;
import de.jardas.commons.search.SearchQuery;
import de.jardas.commons.search.SearchService;

public class SearchExcelExporter {
	private final ExcelExporter excelExporter;

	public SearchExcelExporter(final ExcelExporter excelExporter) {
		this.excelExporter = Preconditions.notNull(excelExporter, "excelExporter");
	}

	public <E, Q extends SearchQuery<E>> void exportExcel(final SearchService<E, Q> searchService, final Q search,
			final ExcelFormat<E> format, final Locale locale, final OutputStream out) {
		search.setItemsPerPage(-1);
		final List<E> items = searchService.search(search).getPageItems();

		excelExporter.export(items, format, locale, out);
	}
}
