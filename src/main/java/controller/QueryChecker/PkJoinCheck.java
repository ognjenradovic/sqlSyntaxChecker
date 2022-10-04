package controller.QueryChecker;

import gui.MainFrame;
import resource.DBNode;
import resource.DBNodeComposite;
import resource.implementation.Entity;
import resource.implementation.InformationResource;

public class PkJoinCheck extends Rule {

    public PkJoinCheck() {
        super("JOIN_NIJE_STRANI_KLJUC");
    }

    @Override
    public boolean check(String query) {
        String[] statements = query.split(" ");
        int i = 0;
        boolean join = false;
        boolean on = false;
        boolean onEnd = false;
        boolean foreignKey=false;
        boolean primaryKey=false;
        String[] keys=new String[10];
        //Select last_name, first_name, department_name from
        // hr.employees e join hr.departments d on (e.department_name = d.department_name)
        for (String statement : statements) {

            //TESTIRANJE EXECUTA
            if (statement.equalsIgnoreCase("join")) {
                join = true;
            }
            if (join && statement.equalsIgnoreCase("on")) {
                on = true;
            }
            if (join && on && isStatement(statement) && !statement.equalsIgnoreCase("ON")) onEnd = true;
            if (join && on && !onEnd && !statement.equalsIgnoreCase("ON") && !statement.equalsIgnoreCase("=")) {
                statement=statement.replace("(", "");
                statement=statement.replace(")", "");
                keys = statement.split("[.]");
                //if(primaryKey)
                DBNode databaseNode = MainFrame.getInstance().getAppCore().getDatabase().loadResource();
                if (databaseNode instanceof InformationResource) {
                    DBNodeComposite databaseNodeComposite = (DBNodeComposite) databaseNode;
                    for (DBNode tableNode : databaseNodeComposite.getChildren()) {
                        if (tableNode instanceof Entity) {
                            Entity tableEntity = (Entity) tableNode;
                            if (tableEntity.getName().equalsIgnoreCase(keys[0])) {
                                if (keys.length>1 && tableEntity.getForeignKeys()!=null) {;
                                    for(Object key:tableEntity.getForeignKeys().toArray()){
                                    if(key.toString().equalsIgnoreCase(keys[1])){
                                        foreignKey=true;

                                    }
                                    }

                                }
                                if (keys.length>1  && tableEntity.getPrimaryKeys()!=null) {
                                    for(Object key:tableEntity.getPrimaryKeys().toArray()){
                                        if(key.toString().equalsIgnoreCase(keys[1])){
                                            primaryKey=true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }


        }
        if(join && on && !foreignKey){
            error_source[0]=keys[1];
            return false;
        }
        return true;
    }

}
