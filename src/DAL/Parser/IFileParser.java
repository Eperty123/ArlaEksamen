package DAL.Parser;

import BE.IParsedData;

/**
 * Author: Carlo De Leon
 * Version: 1.0.0
 */
public interface IFileParser {

    /**
     * Load a file to parse.
     *
     * @param file The file to parse.
     */
    void loadFile(String file);

    /**
     * Load and parse the given file.
     *
     * @param file The file to parse.
     * @param <T>  The expected parsed file type.
     * @return Returns the parsed file type.
     */
    <T> T parse(String file);

    /**
     * Parse the loaded data.
     *
     * @param <T> The expected parsed file type.
     * @return Returns the parsed file type.
     */
    <T> T parse();

    /**
     * Get the parsed data.
     * @return Returns the parsed data.
     */
    IParsedData getParsedData();
}
