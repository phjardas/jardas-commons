package de.jardas.commons.excel;

import java.io.OutputStream;
import java.util.List;
import java.util.Locale;

import jxl.Workbook;
import jxl.write.WritableWorkbook;
import de.jardas.commons.Preconditions;
import de.jardas.commons.i18n.InternationalizationService;

public class ExcelExporter {
	private final InternationalizationService i18n;

	public ExcelExporter(final InternationalizationService i18n) {
		this.i18n = Preconditions.notNull(i18n, "i18n");
	}

	public <E> void export(final List<E> items, final ExcelFormat<E> format, final Locale locale, final OutputStream out) {
		try {
			final WritableWorkbook workbook = Workbook.createWorkbook(out);
			final ExcelBuilder x = new ExcelBuilder(workbook, i18n, locale);
			x.sheet(format.getSheetName(locale));

			format.createHeaderRow(x);
			x.cr();

			for (final E item : items) {
				try {
					format.createItemRow(item, x);
					x.cr();
				} catch (final Exception e) {
					throw new RuntimeException("Error creating Excel row for " + item + ": " + e, e);
				}
			}

			workbook.write();
			out.flush();
			workbook.close();
		} catch (final Exception e) {
			throw new RuntimeException("Error creating Excel workbook: " + e, e);
		}
	}
}
