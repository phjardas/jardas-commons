package de.jardas.commons.excel;

import java.util.Date;
import java.util.Locale;

import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.NumberFormats;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

import org.joda.time.LocalDate;
import org.joda.time.ReadableInstant;

import de.jardas.commons.Preconditions;
import de.jardas.commons.i18n.Internationalizable;
import de.jardas.commons.i18n.InternationalizationService;

public class ExcelBuilder {
	private final WritableWorkbook workbook;
	private final InternationalizationService i18n;
	private final Locale locale;
	private final WritableFont regularFont = new WritableFont(WritableFont.TAHOMA, 10);
	private final WritableFont headerFont = new WritableFont(WritableFont.TAHOMA, 10, WritableFont.BOLD);
	private final WritableFont headingFont = new WritableFont(WritableFont.TAHOMA, 18, WritableFont.BOLD);
	private final WritableCellFormat headingFormat = new WritableCellFormat(headingFont);
	private final WritableCellFormat headerFormat = new WritableCellFormat(headerFont);
	private final WritableCellFormat stringFormat = new WritableCellFormat(regularFont);
	private final WritableCellFormat integerFormat = new WritableCellFormat(NumberFormats.INTEGER);
	private final WritableCellFormat dateFormat = new WritableCellFormat(new DateFormat("yyyy-MM-dd"));
	private final WritableCellFormat dateTimeFormat = new WritableCellFormat(new DateFormat("yyyy-MM-dd HH:mm:ss"));
	private WritableSheet sheet;
	private int row;
	private int col;

	public ExcelBuilder(final WritableWorkbook workbook, final InternationalizationService i18n, final Locale locale) {
		this.workbook = Preconditions.notNull(workbook, "workbook");
		this.i18n = Preconditions.notNull(i18n, "i18n");
		this.locale = locale;
	}

	public void sheet(final String name) {
		sheet = workbook.getSheet(Preconditions.notBlank(name, "name"));

		if (sheet == null) {
			sheet = workbook.createSheet(name, workbook.getSheetNames().length);
		}

		row = 0;
		col = 0;
	}

	public void heading(final String value) throws WriteException {
		sheet.addCell(new Label(col++, row, value, headingFormat));
	}

	public void header(final String value) throws WriteException {
		sheet.addCell(new Label(col++, row, value, headerFormat));
	}

	public void header(final Internationalizable value) throws WriteException {
		header(i18n.getLabel(value, locale));
	}

	public void label(final String value) throws WriteException {
		sheet.addCell(new Label(col++, row, value, stringFormat));
	}

	public void label(final boolean value) throws WriteException {
		label(String.valueOf(value));
	}

	public void label(final Number value) throws WriteException {
		sheet.addCell(new jxl.write.Number(col++, row, value.doubleValue(), integerFormat));
	}

	public void label(final Internationalizable item) throws WriteException {
		label(i18n.getLabel(item, locale));
	}

	public void label(final LocalDate value) throws WriteException {
		if (value == null) {
			label("");
			return;
		}

		sheet.addCell(new DateTime(col++, row, new Date(value.toDateTimeAtStartOfDay().getMillis()), dateFormat));
	}

	public void label(final ReadableInstant value) throws WriteException {
		sheet.addCell(new DateTime(col++, row, new Date(value.getMillis()), dateTimeFormat));
	}

	public void cr() {
		row++;
		col = 0;
	}
}
