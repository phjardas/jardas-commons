package de.jardas.commons.excel;

import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.write.WritableWorkbook;

import org.springframework.web.servlet.view.document.AbstractJExcelView;

import de.jardas.commons.Preconditions;
import de.jardas.commons.i18n.InternationalizationService;

public class ListExcelView<E> extends AbstractJExcelView {
	static final String ITEMS_MODEL_ATTRIBUTE = "items";
	private final InternationalizationService internationalizationService;
	private final ExcelFormat<E> format;

	public ListExcelView(final ExcelFormat<E> format, final InternationalizationService internationalizationService) {
		this.format = Preconditions.notNull(format, "format");
		this.internationalizationService = Preconditions.notNull(internationalizationService,
				"internationalizationService");
	}

	@Override
	protected void buildExcelDocument(final Map<String, Object> model, final WritableWorkbook workbook,
			final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final Locale locale = request.getLocale();
		final ExcelBuilder x = new ExcelBuilder(workbook, internationalizationService, locale);
		x.sheet(format.getSheetName(locale));

		format.createHeaderRow(x);
		x.cr();

		@SuppressWarnings("unchecked")
		final Iterable<E> items = (Iterable<E>) model.get(ITEMS_MODEL_ATTRIBUTE);

		for (final E item : items) {
			try {
				format.createItemRow(item, x);
				x.cr();
			} catch (final Exception e) {
				throw new RuntimeException("Error creating Excel row for " + item + ": " + e, e);
			}
		}
	}
}
