package database;

import resource.DBNode;
import resource.data.Row;

import java.util.List;

public interface Repository {

    DBNode getSchema();

    List<Row> get(String from);

    String export(String tableToExport);
    void insert(String from,String columnOrder,String values);
    List<Row> run(String query);
    void runNoReturn(String query);
}
