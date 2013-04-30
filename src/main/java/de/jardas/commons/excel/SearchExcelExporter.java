package de.jardas.commons.excel;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.web.servlet.ModelAndView;

import de.jardas.commons.Preconditions;
import de.jardas.commons.i18n.InternationalizationService;
import de.jardas.commons.search.SearchQuery;
import de.jardas.commons.search.SearchService;

public class SearchExcelExporter {
	private final InternationalizationService internationalizationService;

	public SearchExcelExporter(final InternationalizationService internationalizationService) {
		this.internationalizationService = Preconditions.notNull(internationalizationService,
				"internationalizationService");
	}

	public <E, Q extends SearchQuery<E>> ModelAndView exportExcel(final SearchService<E, Q> searchService,
			final Q search, final ExcelFormat<E> format) {
		search.setItemsPerPage(-1);
		final List<E> items = searchService.search(search).getPageItems();

		final ListExcelView<E> view = new ListExcelView<E>(format, internationalizationService);
		final Map<String, ?> model = Collections.singletonMap(ListExcelView.ITEMS_MODEL_ATTRIBUTE, items);

		return new ModelAndView(view, model);
	}
}
