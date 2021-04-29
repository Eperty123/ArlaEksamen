package BE;

/**
 * Author: Carlo De Leon
 * Version: 1.0.0
 */
public interface IColumnData {
    /**
     * Get the column name.
     *
     * @return Returns the column name.
     */
    public String getColumnName();

    /**
     * Set the column name.
     *
     * @param columnName The column name to use.
     */
    public void setColumnName(String columnName);

    /**
     * Get the column value.
     *
     * @return Returns the column value.
     */
    public String getColumnValue();

    /**
     * Set the column value.
     *
     * @param columnValue The column value to use.
     */
    public void setColumnValue(String columnValue);

}
