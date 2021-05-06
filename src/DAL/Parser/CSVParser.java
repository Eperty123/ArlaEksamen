package DAL.Parser;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import org.apache.commons.io.input.BOMInputStream;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: Carlo De Leon
 * Version: 1.0.0
 */
public class CSVParser implements IFileParser {
    private String[] header;
    private List<String[]> rows;
    private CSVReader csvReader;
    public static char delimeter = ';';

    public CSVParser() {
        initialize();
    }

    public CSVParser(String file) {
        initialize();
        loadFile(file);
    }

    public CSVParser(String file, char delimeter) {
        initialize();
        this.delimeter = delimeter;
        loadFile(file);
    }

    /**
     * Initialize the class.
     */
    private void initialize() {
        header = new String[]{};
        rows = new ArrayList<>();
    }

    /**
     * Load a CSV file.
     *
     * @param file The CSV file to load.
     */
    public void loadFile(String file) {
        var _file = new File(file);

        try {
            var fs = new FileInputStream(_file.toURI().getPath());

            // To avoid any utf-8 bom issues we must use the BOMInputStream.
            // https://stackoverflow.com/questions/23551683/two-identical-strings-are-not-equalnot-pointer-reference-mistake
            // https://stackoverflow.com/questions/56189424/opencsv-csvtobean-first-column-not-read-for-utf-8-without-bom/56222034
            // Took me a while to figure out why identical strings didn't match and that's because of said issue.
            com.opencsv.CSVParser csvParser = new CSVParserBuilder().withSeparator(delimeter).build(); // custom separator
            CSVReader reader = new CSVReaderBuilder(new InputStreamReader(new BOMInputStream(fs), StandardCharsets.UTF_8))
                    .withCSVParser(csvParser)   // custom CSV parser
                    //.withSkipLines(1)           // skip the first line, header info
                    .build();
            this.csvReader = reader;
            parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Load and parse the given CSV file.
     *
     * @param file The CSV file to parse.
     * @param <T>  The return type.
     * @return Returns a List of parsed content.
     */
    public <T> T parse(String file) {
        loadFile(file);
        return parse();
    }

    /**
     * Parse the loaded CSV file.
     *
     * @param <T> The return type.
     * @return Returns a List of parsed content.
     */
    public <T> T parse() {
        if (csvReader != null) {
            try {
                var rows = csvReader.readAll();
                for (int i = 0; i < rows.size(); i++) {
                    var row = rows.get(i);

                    // Only add if there's more than 1 columns.
                    if (row.length > 1) this.rows.add(row);
                }
                setHeader(this.rows.get(0));
                return (T) rows;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CsvException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * Get the CSV content from the given line index.
     *
     * @param lineIndex The line index.
     * @return Returns the CSV content associated with the line.
     */
    public String[] getRow(int lineIndex) {
        return rows.get(lineIndex);
    }

    /**
     * Get the List of parsed CSV content.
     *
     * @return Returns the parsed CSV content.
     */
    public List<String[]> getParsedData() {
        return rows;
    }

    /**
     * Set the header (columns).
     *
     * @param columns The columns.
     */
    public void setHeader(String[] columns) {
        header = columns;
    }


    /**
     * STATIC METHODS
     */

    /**
     * Parse a CSV file.
     *
     * @param file The file to parse.
     * @return Returns an instance of CSVParser with parsed data.
     */
    public static CSVParser parseFile(String file) {
        return new CSVParser(file);
    }
}
