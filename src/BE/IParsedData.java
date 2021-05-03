package BE;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Author: Carlo De Leon
 * Version: 1.0.0
 */
public interface IParsedData {

    /* ==============================
    /* COLUMN DATA
    /* ===============================
    */

    /**
     * Add a new column with value.
     *
     * @param columnData The column data array list to add.
     */
    void addColumnData(List<IColumnData> columnData);

    /**
     * Get an IColumnData with the given column name and column value.
     *
     * @param columnName  The column name.
     * @param columnValue The column value.
     * @return Returns the found CSVColumnData otherwise null.
     */
    IColumnData getColumnData(int lineIndex, String columnName, String columnValue);

    /**
     * Get a IColumnData from given line index and column index.
     *
     * @param lineIndex   The line index of the ColumnData.
     * @param columnIndex The column index.
     * @return Returns the column found.
     */
    IColumnData getColumnData(int lineIndex, int columnIndex);

    /**
     * Get a IColumnData from given line index and column name.
     *
     * @param lineIndex  The line index of the ColumnData.
     * @param columnName The column name.
     * @return Returns the column found.
     */
    IColumnData getColumnData(int lineIndex, String columnName);

    /**
     * Get all parsed IColumnData.
     *
     * @return Returns a list of IColumnData.
     */
    HashMap<Integer, List<IColumnData>> getAllColumnData();

    /**
     * Does the specified IColumnData with the given column name and column value exist?
     *
     * @param columnName  The column name to find.
     * @param columnValue The associated column value.
     * @return Returns true if yes otherwise false.
     */
    boolean hasColumnValue(String columnName, String columnValue);

    /**
     * Does the specified IColumnData with the given line index, column name and column value exist?
     *
     * @param columnName  The column name to find.
     * @param columnValue The associated column value.
     * @return Returns true if yes otherwise false.
     */
    boolean hasColumnValue(int lineIndex, String columnName, String columnValue);

    /* ==============================
    /* COLUMN HEADERS
    /* ===============================
    */

    /**
     * Add a new column name.
     *
     * @param columnName The column name.
     */
    void addColumn(String columnName);

    /**
     * Get the column with the given name.
     *
     * @param columnName The column name to get.
     * @return Returns the desired column.
     */
    String getColumn(String columnName);

    /**
     * Get the column with the given index.
     *
     * @param columnIndex The index of the column to get.
     * @return Returns the desired column.
     */
    String getColumn(int columnIndex);

    /**
     * Does the specified column name exist?
     *
     * @param columnName The column name to find.
     * @return Returns true if yes and false if not.
     */
    boolean hasColumn(String columnName);

    /**
     * Remove the column with the given name.
     *
     * @param columnName The column name to remove.
     */
    void removeColumn(String columnName);

    /**
     * Remove the column from the given index.
     *
     * @param columnIndex The column index to remove column at.
     */
    void removeColumn(int columnIndex);

    /**
     * Get all the column names from the header.
     *
     * @return
     */
    Collection<String> getColumns();

    /**
     * Set the column names.
     *
     * @param columns
     */
    void setColumns(Collection<String> columns);

    /* ==============================
    /* DATA VALIDATION
    /* ===============================
    */

    /**
     * Is the parsed data valid?
     *
     * @return
     */
    boolean isValid();
}
