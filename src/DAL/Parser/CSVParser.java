package DAL.Parser;

import BE.CSVColumnData;
import BE.CSVData;
import BE.IColumnData;
import BE.IParsedData;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.CSVWriter;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.commons.io.input.BOMInputStream;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

/**
 * Author: Carlo De Leon
 * Version: 1.0.0
 */
public class CSVParser implements IFileParser {
    protected CSVReader csvReader;
    protected CSVData csvData;
    public static char delimeter = ';';

    public CSVParser() {

    }

    public CSVParser(String file) {
        loadFile(file);
    }

    public CSVParser(String file, char delimeter) {
        this.delimeter = delimeter;
        loadFile(file);
    }

    @Override
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

            csvReader = reader;
            parse();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveFile(String outputFile) {
        File file = new File(outputFile);
        try {
            // create FileWriter object with file as parameter
            FileWriter fileWriter = new FileWriter(file);

            // create CSVWriter object filewriter object as parameter
            CSVWriter writer = new CSVWriter(fileWriter);

            // adding header to csv
            var header = new String[csvData.getColumns().size()];
            csvData.getColumns().toArray(header);

            writer.writeNext(header);

            // add exportableData to csv
//            var exportableData = new ArrayList(csvData.getAllColumnData());
//            for (int i = 0; i < exportableData.size(); i++) {
//                var data = (CSVColumnData) exportableData.get(i);
//                var columnValues = new String[header.length];
//
//                for (int c = 0; c < header.length; c++) {
//                    var column = header[c];
//                    if (c == 0)
//                        columnValues[c] = data.getColumnValue();
//                    System.out.println(String.format("Wrote: %s", columnValues[c]));
//                }
//
//                writer.writeNext(columnValues);
//            }

            // closing writer connection
            writer.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
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
        if (csvReader != null) {
            try {
                var csvData = new CSVData();

                var header = new ArrayList<String>();
                var lines = csvReader.readAll();

                // Parse the file if there are actual lines to read.
                if (lines.size() > 0) {
                    for (int i = 0; i < lines.size(); i++) {
                        var line = lines.get(i);
                        // Got the header information, now read all the column values.
                        var columnData = new ArrayList<IColumnData>();

                        /* Get the header information.
                        /* Index 0 is always the header information. We NEED the header in order to
                        /* know what the values refer to what.
                        */
                        if (i == 0) {
                            for (int h = 0; h < line.length; h++) {
                                var columnName = line[h].strip().trim();

                                // Add the column name.
                                header.add(columnName);
                                //System.out.println(columnName);
                            }
                        } else {

                            for (int c = 0; c < line.length; c++) {
                                var columnValue = line[c].strip().trim();
                                columnData.add(new CSVColumnData(i, header.get(c), columnValue));
                            }
                            csvData.addColumnData(columnData);
                        }
                    }
                    csvData.setColumns(header);
                    this.csvData = csvData;
                    return (T) this.csvData;
                }

            } catch (CsvValidationException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (CsvException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public IParsedData getParsedData() {
        return csvData;
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
