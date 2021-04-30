package DAL.Parser;

import BE.IParsedData;
import BE.XLSVData;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Author: Carlo De Leon
 * Version: 1.0.0
 */
public class XLSVParser implements IFileParser {
    protected XSSFWorkbook excelSheet;
    protected XLSVData xlsvData;

    public XLSVParser() {

    }

    public XLSVParser(String file) {
        loadFile(file);
    }

    @Override
    public void loadFile(String file) {
        var _file = new File(file);

        try {
            var fs = new FileInputStream(_file.toURI().getPath());

            // Finds the workbook instance for XLSX file
            XSSFWorkbook myWorkBook = new XSSFWorkbook(fs);
            excelSheet = myWorkBook;
            parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public <T> T parse(String file) {
        loadFile(file);
        return parse();
    }

    @Override
    public <T> T parse() {
        if (excelSheet != null) {
            try {
                var xlsvData = new XLSVData();

                // Return first sheet from the XLSX workbook
                XSSFSheet mySheet = excelSheet.getSheetAt(0);

                // Get iterator to all the rows in current sheet
                Iterator<Row> rowIterator = mySheet.iterator();

                // Data
                var header = new ArrayList<String>();

                int row_index = 0;
                // Traversing over each row of XLSX file
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();
                    int column_index = 0;

                    // For each row, iterate through each columns
                    Iterator<Cell> cellIterator = row.cellIterator();
                    while (cellIterator.hasNext()) {

                        Cell cell = cellIterator.next();

                        /* Get the header information.
                        /* Index 0 is always the header information. We NEED the header in order to
                        /* know what the values refer to what.
                        */
                        if (row_index == 0) {

                            // Read header information. Get the column names.
                            if (cell != null) {
                                String columnName = "";

                                switch (cell.getCellType()) {
                                    case STRING:
                                        columnName = cell.getStringCellValue();
                                        break;
                                    case NUMERIC:
                                        columnName = Double.toString(cell.getNumericCellValue());
                                        break;
                                    case BOOLEAN:
                                        columnName = Boolean.toString(cell.getBooleanCellValue());
                                        break;
                                }
                                header.add(columnName);
                            }
                        } else {
                            // Got the header information, now read all the column values.
                            String value = "";
                            switch (cell.getCellType()) {
                                case STRING:
                                    value = row.getCell(column_index).toString();
                                    break;
                                case NUMERIC:
                                    value = Double.toString(cell.getNumericCellValue());
                                    break;
                                case BOOLEAN:
                                    value = Boolean.toString(cell.getBooleanCellValue());
                                    break;
                            }
                            // Add the column value.
                            xlsvData.addColumnData(header.get(column_index), value);
                        }

                        // Increase the column index to get the next column.
                        column_index++;
                    }

                    row_index++;
                }

                this.xlsvData = xlsvData;
                return (T) this.xlsvData;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public IParsedData getParsedData() {
        return xlsvData;
    }

}
