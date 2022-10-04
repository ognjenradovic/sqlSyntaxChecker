package database;

import resource.DBNode;
import resource.data.Row;

import java.util.List;

public interface Database{

    DBNode loadResource();

    List<Row> readDataFromTable(String tableName);
    String export(String tableToExport);
    List<Row> run(String query);
    void runNoReturn(String query);
    void insert(String from,String columnOrder,String values);


}
