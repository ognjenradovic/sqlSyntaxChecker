package controller.QueryChecker;

public class WhereCheck extends Rule {

    public WhereCheck() {
        super("WHERE_NEMA_AGREGACIJU");
    }

    @Override
    public boolean check(String query) {
        String[] statements = query.split(" ");
        int i = 0;
        boolean where=false;
        boolean whereEnd=false;
        boolean aggregate=false;
        for (String statement : statements) {

            //TESTIRANJE EXECUTA
            if (statement.equalsIgnoreCase("WHERE")) {
                where=true;
            }
            if(where && isStatement(statement)) {
                whereEnd = true;
            }
            if(where && !whereEnd){
                if (statement.contains("<") || statement.contains(">") || statement.contains("like") || statement.contains("=")) {
                    aggregate=true;
                }
            }
            }
        if(!aggregate && where){
            error_source[0]=query.toString();
            return false;
        }

        return true;
    }
}
