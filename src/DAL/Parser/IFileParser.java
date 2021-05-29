package DAL.Parser;

import java.io.FileNotFoundException;

/**
 * Author: Carlo De Leon
 * Version: 1.0.0
 */
public interface IFileParser {
    /**
     * Load a file.
     *
     * @param file The CSV file to load.
     */
    void loadFile(String file) throws FileNotFoundException;

    /**
     * Parse the file.
     *
     * @param file The file to parse.
     * @param <T>  The return type.
     * @return Returns the parsed file.
     */
    <T> T parse(String file) throws FileNotFoundException;

    /**
     * Parse the loaded file.
     *
     * @param <T> The return type.
     * @return Returns the parsed file.
     */
    <T> T parse();

    /**
     * Get the content from the given line index.
     *
     * @param rowIndex The line index.
     * @return Returns the content associated with the line.
     */
    <T> T getRow(int rowIndex);

    /**
     * Get the parsed content.
     *
     * @return Returns the parsed content.
     */
    <T> T getParsedData();
}

