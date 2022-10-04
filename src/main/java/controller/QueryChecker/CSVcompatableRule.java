package controller.QueryChecker;

import gui.MainFrame;

public class CSVcompatableRule extends Rule {

    public CSVcompatableRule() {
        super("CSV_NIJE_VALIDAN");
    }

    @Override
    public boolean check(String query) {
        boolean insert = false;
        boolean into = false;
        boolean tableBoolean = false;
        boolean declaredOrder = false;
        boolean nasao = false;
        String table;

        String[] statements = query.split(" ");
        for (String statement : statements) {
            if (statement.equalsIgnoreCase("INSERT")) insert = true;
            if (statement.equalsIgnoreCase("INTO")) into = true;
            if (insert && into && !tableBoolean) table = statement;
            tableBoolean = true;
            if (insert && into && tableBoolean && !declaredOrder && statement.matches("\\((.*?)\\)")) {

                String newStatement = statement;
                newStatement = newStatement.substring(1, statement.length() - 1);
                declaredOrder = true;
                String[] columns = newStatement.split(",");
                Object[] tableColumns = MainFrame.getInstance().getAppCore().getTableModel().getRows().get(0).getFields().keySet().toArray();
                for (String column : columns) {
                    nasao = false;
                    for (int i = 0; i < tableColumns.length; i++) {
                        if (tableColumns[i].toString().equalsIgnoreCase(column)) {
                            nasao = true;
                        }
                    }
                    if (nasao == false) {
                        return false;
                    }
                }

            }
        }


        return true;
    }
}
