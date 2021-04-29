package BE;

/**
 * Author: Carlo De Leon
 * Version: 1.0.0
 */
public interface IParsedData {

    /**
     * Add a new column with value.
     *
     * @param columnName  The column name.
     * @param columnValue The value.
     */
    public void addColumnData(String columnName, String columnValue);

    /**
     * Get a CSVColumnData with the given column name and column value.
     *
     * @param columnName  The column name.
     * @param columnValue The column value.
     * @return Returns the found CSVColumnData otherwise null.
     */
    public IColumnData getColumnData(String columnName, String columnValue);

    /**
     * Get a CSVColumnData with the given line index.
     *
     * @param lineIndex The line index of the ColumnData.
     * @return
     */
    public IColumnData getColumnData(int lineIndex);

    /**
     * Does the specified column name exist?
     *
     * @param columnName The column name to find.
     * @return Returns true if yes and false if not.
     */
    public boolean hasColumn(String columnName);

    /**
     * Does the specified CSVColumnData with the given column name and column value exist?
     *
     * @param columnName  The column name to find.
     * @param columnValue The associated column value.
     * @return Returns true if yes otherwise false.
     */
    public boolean hasColumnValue(String columnName, String columnValue);
}
