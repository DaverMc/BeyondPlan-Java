package de.daver.beyondplan.util.sql;

import de.daver.beyondplan.util.MultiHashMap;
import de.daver.beyondplan.util.ObjectTransformer;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class Result {

    private final MultiHashMap<String, Object> values;
    private int cursorIndex;
    private int size;

    private Result() {
        values = new MultiHashMap<>();
        cursorIndex = 0;
    }

    public boolean hasNext() {
        return cursorIndex < size;
    }

    public boolean next() {
        if(!hasNext()) return false;
        cursorIndex++;
        return true;
    }

    public <T> T get(String columnName, int entryIndex, ObjectTransformer<T> transformer) {
        return transformer.transform(values.getAll(columnName).get(entryIndex));
    }

    public <T> T get(String columnName, ObjectTransformer<T> transformer) {
        return get(columnName, cursorIndex, transformer);
    }

    public <T> List<T> getColumn(String columName, ObjectTransformer<T> transformer) {
        return values.getAll(columName).stream().map(transformer::transform).toList();
    }

    public static Result ofResultSet(ResultSet resultSet) throws SQLException{
        var result = new Result();
        var meta = resultSet.getMetaData();
        int columnCount = meta.getColumnCount();
        String[] columnNames = new String[columnCount];
        for(int i = 0; i < columnCount; i++ ) {
            columnNames[i] = meta.getColumnName(i + 1);
            System.out.println(columnNames[i]);
        }
        while(resultSet.next()) {
            result.size++;
            for(String columnName : columnNames) {
                result.values.add(columnName, resultSet.getObject(columnName));
            }
        }
        return result;
    }
}
