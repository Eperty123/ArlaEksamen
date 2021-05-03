package BE;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Author: Carlo De Leon
 * Version: 1.0.0
 */
public class CSVData implements IParsedData {
    HashMap<Integer, List<IColumnData>> data;
    List<String> columns;

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
     * @param columnData The column data array list to add.
     */
    @Override
    public void addColumnData(List<IColumnData> columnData) {
        if (data != null) {
            Integer index = data.size() > 0 ? data.size() + 1 : data.size();
            data.put(index, columnData);
        }
    }

    /**
     * Get a CSVColumnData with the given column name and column value.
     *
     * @param columnName  The column name.
     * @param columnValue The column value.
     * @return Returns the found  CSVColumnData otherwise null.
     */
    @Override
    public IColumnData getColumnData(int lineIndex, String columnName, String columnValue) {
        if (data.size() > 0) {
            var values = data.get(lineIndex);
            for (int i = 0; i < values.size(); i++) {
                var columnData = ((IColumnData) values.get(i));
                if (columnData.getColumnName().equals(columnName) && columnData.getColumnValue().startsWith(columnValue))
                    return (IColumnData) columnData;
            }
        }
        return null;
    }

    /**
     * Get a CSVColumnData with the given line index.
     *
     * @param lineIndex The line index of the ColumnData.
     * @return
     */
    @Override
    public IColumnData getColumnData(int lineIndex, int columnIndex) {
        return data.get(lineIndex).get(columnIndex);
    }

    @Override
    public IColumnData getColumnData(int lineIndex, String columnName) {
        var desired = data.get(lineIndex);
        for (int i = 0; i < desired.size(); i++) {
            var desiredData = desired.get(i);
            if (desiredData.getColumnName().equals(columnName))
                return desiredData;
        }
        return null;
    }

    @Override
    public HashMap<Integer, List<IColumnData>> getAllColumnData() {
        return data;
    }

    @Override
    public boolean hasColumnValue(String columnName, String columnValue) {
        if (data.size() > 0) {
            var values = data.values().toArray();
            for (int i = 0; i < values.length; i++) {
                var columnData = ((IColumnData) values[i]);
                return columnData.getColumnName().equals(columnName) && columnData.getColumnValue().startsWith(columnValue);
            }
        }
        return false;
    }

    @Override
    public boolean hasColumnValue(int lineIndex, String columnName, String columnValue) {
        if (data.size() > 0) {
            var values = data.get(lineIndex);
            for (int i = 0; i < values.size(); i++) {
                var columnData = ((IColumnData) values.get(i));
                return columnData.getColumnName().equals(columnName) && columnData.getColumnValue().startsWith(columnValue);
            }
        }
        return false;
    }

    @Override
    public void addColumn(String columnName) {
        columns.add(columnName);
    }

    @Override
    public String getColumn(String columnName) {
        for (int i = 0; i < columns.size(); i++) {
            var column = columns.get(i);
            if (column.equals(columnName)) return column;
        }
        return null;
    }

    @Override
    public boolean hasColumn(String columnName) {
        return columns.contains(columnName);
    }

    @Override
    public String getColumn(int columnIndex) {
        return columns.get(columnIndex);
    }

    @Override
    public void removeColumn(String columnName) {
        for (int i = 0; i < columns.size(); i++) {
            var column = columns.get(i);
            if (column.equals(columnName)) columns.remove(column);
        }
    }

    @Override
    public void removeColumn(int columnIndex) {
        if (columns.size() >= columnIndex)
            columns.remove(columnIndex);
    }

    @Override
    public Collection<String> getColumns() {
        return columns;
    }

    @Override
    public void setColumns(Collection<String> columns) {
        this.columns = new ArrayList<>(columns);
    }

    @Override
    public boolean isValid() {
        return data != null && data.size() > 0 && columns.size() > 0;
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
