package BE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Author: Carlo De Leon
 * Version: 1.0.0
 */
public class CSVData implements IParsedData {
    HashMap<Integer, CSVColumnData> data;

    public CSVData() {
        initialize();
    }

    /**
     * Initialize the class. Put all variables to initialize here.
     */
    private void initialize() {
        data = new HashMap<>();
    }

    /**
     * Add a new column with value.
     *
     * @param columnName  The column name.
     * @param columnValue The value.
     */
    public void addColumnData(String columnName, String columnValue) {
        if (data != null) {
            Integer index = data.size() > 0 ? data.size() + 1 : data.size();
            data.put(index, new CSVColumnData(columnName, columnValue));
        }
    }

    /**
     * Get a CSVColumnData with the given column name and column value.
     *
     * @param columnName  The column name.
     * @param columnValue The column value.
     * @return Returns the found  CSVColumnData otherwise null.
     */
    public CSVColumnData getColumnData(String columnName, String columnValue) {
        if (data.size() > 0) {
            var values = data.values().toArray();
            for (int i = 0; i < values.length; i++) {
                var columnData = ((CSVColumnData) values[i]);
                if (columnData.getColumnName().equals(columnName) && columnData.getColumnValue().startsWith(columnValue))
                    return columnData;
            }
        }
        return null;
    }

    @Override
    public Collection<IColumnData> getAllColumnData() {
        return new ArrayList<>(data.values());
    }

    /**
     * Get a CSVColumnData with the given line index.
     *
     * @param lineIndex The line index of the ColumnData.
     * @return
     */
    public CSVColumnData getColumnData(int lineIndex) {
        return data.get(lineIndex);
    }

    /**
     * Does the specified column name exist?
     *
     * @param columnName The column name to find.
     * @return Returns true if yes and false if not.
     */
    public boolean hasColumn(String columnName) {
        if (data.size() > 0) {
            var values = data.values().toArray();
            for (int i = 0; i < values.length; i++) {
                var columnData = ((CSVColumnData) values[i]);
                return columnData.getColumnName().equals(columnName);
            }
        }
        return false;
    }

    /**
     * Does the specified CSVColumnData with the given column name and column value exist?
     *
     * @param columnName  The column name to find.
     * @param columnValue The associated column value.
     * @return Returns true if yes otherwise false.
     */
    public boolean hasColumnValue(String columnName, String columnValue) {
        if (data.size() > 0) {
            var values = data.values().toArray();
            for (int i = 0; i < values.length; i++) {
                var columnData = ((CSVColumnData) values[i]);
                return columnData.getColumnName().equals(columnName) && columnData.getColumnValue().startsWith(columnValue);
            }
        }
        return false;
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        var values = data.values().toArray();
        for (int i = 0; i < values.length; i++) {
            var columnData = values[i];
            sb.append(String.format("Linje %d\n%s\n", i, columnData.toString()));
        }
        return sb.toString();
    }
}
