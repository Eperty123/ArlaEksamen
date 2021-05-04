package DAL.Parser;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Author: Carlo De Leon
 * Version: 1.0.0
 */
public class XLSXParser implements IFileParser {
    protected XSSFWorkbook excelSheet;
    protected HashMap<Integer, List<Object>> rows;


    public XLSXParser() {
        initialize();
    }

    public XLSXParser(String file) {
        initialize();
        loadFile(file);
    }

    /**
     * Initialize the class.
     */
    private void initialize() {
        rows = new HashMap<Integer, List<Object>>();
    }

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

    public <T> T parse(String file) {
        loadFile(file);
        return parse();
    }

    public <T> T parse() {
        if (excelSheet != null) {
            try {
                // Return first sheet from the XLSX workbook
                XSSFSheet mySheet = excelSheet.getSheetAt(0);

                // Get iterator to all the rows in current sheet
                Iterator<Row> rowIterator = mySheet.iterator();

                var row_index = 0;

                // Traversing over each row of XLSX file
                while (rowIterator.hasNext()) {
                    Row row = rowIterator.next();

                    Iterator<Cell> cellIterator = row.cellIterator();
                    var values = new ArrayList<Object>();
                    while (cellIterator.hasNext()) {

                        Cell cell = cellIterator.next();
                        // Got the header information, now read all the column values.
                        String value = "";
                        switch (cell.getCellType()) {
                            case STRING:
                                value = cell.getStringCellValue();
                                break;
                            case NUMERIC:
                                value = Double.toString(cell.getNumericCellValue());
                                break;
                            case BOOLEAN:
                                value = Boolean.toString(cell.getBooleanCellValue());
                                break;
                        }
                        values.add(value);
                    }

                    // Columns included.
                    rows.put(row_index, values);
                    row_index++;
                }

                return (T) this.rows;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Get the XLSX content from the given line index.
     *
     * @param rowIndex The row index.
     * @return Returns the CSV content associated with the line.
     */
    public <T> T getRow(int rowIndex) {
        return (T) rows.get(rowIndex);
    }

    /**
     * Get the parsed XLSX content.
     *
     * @param <T> The return type.
     * @return Returns the parsed XLSX content.
     */
    public <T> T getParsedData() {
        return (T) rows;
    }

    /**
     * STATIC METHODS
     */

    /**
     * Parse a XLSX file.
     *
     * @param file The file to parse.
     * @return Returns an instance of XLSXParser with parsed data.
     */
    public static XLSXParser parseFile(String file) {
        return new XLSXParser(file);
    }

}
