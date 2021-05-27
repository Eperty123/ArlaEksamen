package DAL.Parser;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Author: Carlo De Leon
 * Version: 1.0.0
 */
public class XLSXParser implements IFileParser {
    protected XSSFWorkbook excelSheet;
    protected List<String[]> rows;


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
        rows = new ArrayList<>();
    }

    /**
     * Load the specified Excel file.
     *
     * @param file The Excel file to load.
     */
    public void loadFile(String file) {
        var _file = new File(file);

        try {
            var fs = new FileInputStream(_file.toURI().getPath());

            // Finds the workbook instance for XLSX file
            XSSFWorkbook myWorkBook = new XSSFWorkbook(fs);
            excelSheet = myWorkBook;
            parse();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load and parse the given Excel file.
     *
     * @param file The file to parse.
     * @param <T>  The return type.
     * @return Returns a Hashmap of parsed content.
     */
    public <T> T parse(String file) {
        loadFile(file);
        return parse();
    }

    /**
     * Parse the loaded Excel file.
     *
     * @param <T> The return type.
     * @return Returns a Hashmap of parsed content.
     */
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
                    //var rowColumns = new ArrayList<String[]>();

                    Iterator<Cell> cellIterator = row.cellIterator();
                    String[] columnValues = new String[row.getLastCellNum()];
                    int cellIndex = 0;
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
                        columnValues[cellIndex] = value.replace(";", "");
                        cellIndex++;
                    }

                    // Columns included.
                    rows.add(columnValues);
                    row_index++;
                }

                return (T) rows;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Get the Excel content from the given line index.
     *
     * @param rowIndex The row index.
     * @return Returns the CSV content associated with the line.
     */
    public <T> T getRow(int rowIndex) {
        return (T) rows.get(rowIndex);
    }

    /**
     * Get the parsed Excel content.
     *
     * @param <T> The return type.
     * @return Returns a Hashmap of parsed content.
     */
    public <T> T getParsedData() {
        return (T) rows;
    }

    /**
     * STATIC METHODS
     */

    /**
     * Parse an Excel file.
     *
     * @param file The file to parse.
     * @return Returns an instance of XLSXParser with parsed data.
     */
    public static XLSXParser parseFile(String file) {
        return new XLSXParser(file);
    }

}
