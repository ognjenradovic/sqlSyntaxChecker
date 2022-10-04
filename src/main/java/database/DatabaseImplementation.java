package database;

import lombok.AllArgsConstructor;
import lombok.Data;
import resource.DBNode;
import resource.data.Row;

import java.util.List;

@Data
@AllArgsConstructor
public class DatabaseImplementation implements Database {

    private Repository repository;


    @Override
    public DBNode loadResource() {
        return repository.getSchema();
    }

    public String export(String tableToExport){
        return repository.export(tableToExport);
    }

    public void insert(String from,String columnOrder,String values){
        repository.insert(from,columnOrder,values);
    }
    public List<Row> run(String query){
        return repository.run(query);
    }
    public void runNoReturn(String query){repository.runNoReturn(query);}
    @Override
    public List<Row> readDataFromTable(String tableName) {
        return repository.get(tableName);
    }
}
