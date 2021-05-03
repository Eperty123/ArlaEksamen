package BE;

/**
 * Author: Carlo De Leon
 * Version: 1.0.0
 */
public class XLSXColumnData implements IColumnData {
    private int lineIndex;
    private String columnName;
    private String columnValue;

    public XLSXColumnData(String columnName, String columnValue) {
        setColumnName(columnName);
        setColumnValue(columnValue);
    }

    public XLSXColumnData(int lineIndex, String columnName, String columnValue) {
        setLineIndex(lineIndex);
        setColumnName(columnName);
        setColumnValue(columnValue);
    }

    @Override
    public int getLineIndex() {
        return lineIndex;
    }

    /**
     * Get the column name.
     *
     * @return Returns the column name.
     */
    public String getColumnName() {
        return columnName;
    }

    /**
     * Set the column name.
     *
     * @param columnName The column name to use.
     */
    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    /**
     * Get the column value.
     *
     * @return Returns the column value.
     */
    public String getColumnValue() {
        return columnValue;
    }

    /**
     * Set the column value.
     *
     * @param columnValue The column value to use.
     */
    public void setColumnValue(String columnValue) {
        this.columnValue = columnValue;
    }

    @Override
    public void setLineIndex(int lineIndex) {
        this.lineIndex = lineIndex;
    }

    @Override
    public String toString() {
        return String.format("Kolonne: %s, Kolonne værdi: %s", getColumnName(), getColumnValue());
    }
}
