package database;

import database.settings.Settings;
import lombok.Data;
import resource.DBNode;
import resource.data.Row;
import controller.enums.AttributeType;
import resource.implementation.Attribute;
import resource.implementation.Entity;
import resource.implementation.InformationResource;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class MYSQLrepository implements Repository{

    private Settings settings;
    private Connection connection;

    public MYSQLrepository(Settings settings) {
        this.settings = settings;
    }

    private void initConnection() throws SQLException, ClassNotFoundException{
        String ip = (String) settings.getParameter("mysql_ip");
        String database = (String) settings.getParameter("mysql_database");
        String username = (String) settings.getParameter("mysql_username");
        String password = (String) settings.getParameter("mysql_password");
        //Class.forName("net.sourceforge.jtds.jdbc.Driver");
        connection = DriverManager.getConnection("jdbc:mysql://"+ip+"/"+database,username,password);


    }

    private void closeConnection(){
        try{
            connection.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        finally {
            connection = null;
        }
    }


    @Override
    public DBNode getSchema() {

        try{
            this.initConnection();

            DatabaseMetaData metaData = connection.getMetaData();
            InformationResource ir = new InformationResource("RAF_BP_Primer");

            String tableType[] = {"TABLE"};
            ResultSet tables = metaData.getTables(connection.getCatalog(), null, null, tableType);

            while (tables.next()){

                String tableName = tables.getString("TABLE_NAME");
                if(tableName.contains("trace"))continue;
                Entity newTable = new Entity(tableName, ir);
                ir.addChild(newTable);

                //Koje atribute imaja ova tabela?

                ResultSet columns = metaData.getColumns(connection.getCatalog(), null, tableName, null);

                while (columns.next()){

                    // COLUMN_NAME TYPE_NAME COLUMN_SIZE ....

                    String columnName = columns.getString("COLUMN_NAME");
                    String columnType = columns.getString("TYPE_NAME");

                   // System.out.println(columnType);

                    int columnSize = Integer.parseInt(columns.getString("COLUMN_SIZE"));
                    if(connection!=null){
                        ResultSet pkeys = metaData.getPrimaryKeys(connection.getCatalog(), null, tableName);
                        while (pkeys.next()){
                            String pkColumnName = pkeys.getString("COLUMN_NAME");
                            newTable.getPrimaryKeys().add(pkColumnName);
                        }
                        ResultSet fkeys = metaData.getImportedKeys(connection.getCatalog(), null, tableName);
                        while (fkeys.next()){
                            String fkColumnName = fkeys.getString("FKCOLUMN_NAME");
                           // fkColumnName=fkColumnName.substring(0,fkColumnName.length()-7);
                         //   System.out.println(fkColumnName);
                            if(fkColumnName!=null && newTable.getForeignKeys()!=null)newTable.getForeignKeys().add(fkColumnName);
                        }
                    }



                    Attribute attribute = new Attribute(columnName, newTable,
                            AttributeType.valueOf(
                                    Arrays.stream(columnType.toUpperCase().split(" "))
                                    .collect(Collectors.joining("_"))),
                            columnSize);
                    newTable.addChild(attribute);

                }



            }


            //TODO Ogranicenja nad kolonama? Relacije?


            return ir;
            //String isNullable = columns.getString("IS_NULLABLE");
            // ResultSet foreignKeys = metaData.getImportedKeys(connection.getCatalog(), null, table.getName());
            // ResultSet primaryKeys = metaData.getPrimaryKeys(connection.getCatalog(), null, table.getName());

        }
        catch (SQLException e1) {
            e1.printStackTrace();
        }
        catch (ClassNotFoundException e2){ e2.printStackTrace();}
        finally {
            this.closeConnection();
        }

        return null;
    }

    @Override
    public List<Row> get(String from) {

        List<Row> rows = new ArrayList<>();


        try{
            this.initConnection();

            String query = "SELECT * FROM " + from;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();

            while (rs.next()){

                Row row = new Row();
                row.setName(from);

                for (int i = 1; i<=resultSetMetaData.getColumnCount(); i++){
                    row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
                }
                rows.add(row);


            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.closeConnection();
        }

        return rows;
    }



    public void insert(String from,String columnOrder,String values) {
        try{
            this.initConnection();
            //insert into dept (departmant_name,department_id ....) values („99“,“Test 203“,77,100)-
            String query = "INSERT INTO " + from + columnOrder + " values " + values;
           // System.out.println(query);
            ResultSet rs;
         try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
              preparedStatement.execute();
           }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.closeConnection();
        }
        get(from);
    }
    //Dodati i boolean daLiVracaResultSet
    public void runNoReturn(String query) {

       //System.out.println(query);
        List<Row> rows = new ArrayList<>();


        try{
            this.initConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.execute();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.closeConnection();
        }

    }

//Dodati i boolean daLiVracaResultSet
    public List<Row> run(String query) {

        //System.out.println(query);
        List<Row> rows = new ArrayList<>();


        try{
            this.initConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(query);
           // preparedStatement.execute();
            ResultSet rs = preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();

            while (rs.next()){

                Row row = new Row();
                row.setName("output");

                for (int i = 1; i<=resultSetMetaData.getColumnCount(); i++){
                    row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
                }
                rows.add(row);


            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.closeConnection();
        }

        return rows;
    }
    @Override
    public String export(String from) {

       // List<Row> rows = new ArrayList<>();
        StringBuilder stringBuilder=new StringBuilder();

        try{
            this.initConnection();

            String query = "SELECT * FROM " + from;
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            for (int i = 1; i<=resultSetMetaData.getColumnCount(); i++){
                stringBuilder.append(resultSetMetaData.getColumnName(i));
                if(i!=resultSetMetaData.getColumnCount()){
                    stringBuilder.append(";");
                }
               // System.out.println(resultSetMetaData.getColumnName(i));

            }
            stringBuilder.append("\n");
            while (rs.next()){
                //System.out.println(rs);

                for (int i = 1; i<=resultSetMetaData.getColumnCount(); i++){
                    stringBuilder.append(rs.getString(i));
                    if(i!=resultSetMetaData.getColumnCount()){
                        stringBuilder.append(";");
                    }
                }
                stringBuilder.append("\n");
//                Row row = new Row();
//                row.setName(from);
//
//                for (int i = 1; i<=resultSetMetaData.getColumnCount(); i++){
//                    row.addField(resultSetMetaData.getColumnName(i), rs.getString(i));
//                }
//                rows.add(row);
//

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            this.closeConnection();
        }
        return stringBuilder.toString();
      //  System.out.print(stringBuilder.toString());
    }
}
