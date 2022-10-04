package controller.QueryChecker;

import gui.MainFrame;
import resource.DBNode;
import resource.DBNodeComposite;
import resource.implementation.Attribute;
import resource.implementation.Entity;
import resource.implementation.InformationResource;

import java.util.ArrayList;

public class TableExsistsRule extends Rule{

    public TableExsistsRule() {
        super("NEPOSTOJECA_TABELA");


    }

    @Override
    public boolean check(String query) {
        boolean into=false;
        boolean tableBoolean=false;
        boolean declaredOrder=false;
        boolean nasao=false;
        boolean foundTable=false;
        Entity mainTableEntity = null;
        String table;
       // System.out.println();
        //  String[] columns;
        //insert into dept (departmant_name,department_id ....) values („99“,“Test 203“,77,100)-
        String[] statements=query.split(" ");
        int i=0;
        for(String statement:statements){
            if(statement.equalsIgnoreCase("INTO"))into=true;
            if(i>0 && statements[i-1].equalsIgnoreCase("INTO") && !tableBoolean){
                table=statement;
            //Provera da li je tabela u
                tableBoolean=true;
                DBNode databaseNode=MainFrame.getInstance().getAppCore().getDatabase().loadResource();
                if(databaseNode instanceof InformationResource){
                   DBNodeComposite databaseNodeComposite= (DBNodeComposite)databaseNode;
                   for(DBNode tableNode:databaseNodeComposite.getChildren()) {
                       if(tableNode instanceof Entity){
                           Entity tableEntity=(Entity)tableNode;
                           if(tableEntity.getName().equalsIgnoreCase(statement)){
                               foundTable=true;
                               System.out.println("Nasao tabelu");
                               mainTableEntity=tableEntity;

                           }
                       }
                   }
                   if(foundTable==false){
                       error_source[0]=statement;
                       return false;
                   }
                }
            }
            if(into && tableBoolean && !declaredOrder && statement.matches("\\((.*?)\\)")){
                String newStatement=statement;
                newStatement=newStatement.substring(1, statement.length()-1);
                declaredOrder=true;
                String[] columns=newStatement.split(",");
                ArrayList<Attribute>tableAttributes=new ArrayList<Attribute>();
                if(mainTableEntity!=null){
                    for(DBNode dbNodeAttribute:mainTableEntity.getChildren()) {
                        if (dbNodeAttribute instanceof Attribute) {
                            tableAttributes.add((Attribute)dbNodeAttribute);
                        }
                    }
                }


                for(String column:columns){
                    nasao=false;

                    for (int j=0;j<tableAttributes.size();j++) {
                        //System.out.println("Ime kolone iz modela:" +tableColumns[i].toString());
                        if(tableAttributes.get(j).getName().toString().equalsIgnoreCase(column)){
                            nasao=true;
                        }
                    }
                    if(nasao==false){
                        error_source[0]=columns.toString();
                        return false;
                    }
                }
            }
            i++;
        }
        return true;
    }
}
