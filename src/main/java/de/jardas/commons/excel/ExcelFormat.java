package de.jardas.commons.excel;

import java.io.IOException;
import java.util.Locale;

import jxl.write.WriteException;

public interface ExcelFormat<E> {
	String getSheetName(Locale locale);

	void createHeaderRow(ExcelBuilder x) throws WriteException, IOException;

	void createItemRow(E item, ExcelBuilder x) throws WriteException, IOException;
}
