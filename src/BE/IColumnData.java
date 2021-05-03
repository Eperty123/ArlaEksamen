package BE;

/**
 * Author: Carlo De Leon
 * Version: 1.0.0
 */
public interface IColumnData {

    /**
     * Get the line index of where this IColumnData belongs to.
     *
     * @return
     */
    int getLineIndex();

    /**
     * Get the column name.
     *
     * @return Returns the column name.
     */
    String getColumnName();

    /**
     * Set the column name.
     *
     * @param columnName The column name to use.
     */
    void setColumnName(String columnName);

    /**
     * Get the column value.
     *
     * @return Returns the column value.
     */
    String getColumnValue();

    /**
     * Set the column value.
     *
     * @param columnValue The column value to use.
     */
    void setColumnValue(String columnValue);

    /**
     * Set the line index for this IColumn Data.
     *
     * @param lineIndex The line index.
     */
    void setLineIndex(int lineIndex);
}
