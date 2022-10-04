package controller.QueryChecker;

public class RequiredCheck extends Rule {

    public RequiredCheck() {
        super("NIJE_UNETO_OBAVEZNO");
    }

    @Override
    public boolean check(String query) {
        boolean execute;
        String[] statements = query.split(" ");
        int i = 0;
        for (String statement : statements) {

            //TESTIRANJE EXECUTA
            if (statement.equalsIgnoreCase("EXECUTE")) {
                if (statements.length==i+1 || isStatement(statements[i+1])) {
                    error_source[0]="EXECUTE";
                   return false;
                }

                if(statements.length>i+2 && statements[i + 2].equalsIgnoreCase("USING")){
                    if(statements.length<=i+3){
                        error_source[0]="USING";
                        return false;
                    }
                    if(statements.length>i+3 && isStatement(statements[i+3])){
                        error_source[0]="USING";
                        return false;
                    }
                }
            }

            //TESTIRANJE DELETE
            if (statement.equalsIgnoreCase("DELETE")) {
                //DA LI TREBA DA PODRZIMO [LOW_PRIORITY] [QUICK] [IGNORE]
                if (statements.length<i+3 || statements[i+1].equalsIgnoreCase("FROM") || isStatement(statements[i+2])) {
                    error_source[0]="DELETE";
                    return false;
                }
            }

            //Podrzavam insert nesto ili insert into nesto
            //TESTIRANJE INSERTA
            if (statement.equalsIgnoreCase("INSERT")) {
                if (statements.length==i+1 || (isStatement(statements[i+1]) && !statements[i+1].equalsIgnoreCase("INTO")) ) {
                    error_source[0]="INSERT";
                    return false;
                }
                if(statements.length>=i+2 && statements[i+1].equalsIgnoreCase("INTO")){
                   if(isStatement(statements[i+2])){
                       error_source[0]="INTO";
                       return false;
                   }
                }
            }
            // TESTIRANJE UPDATEA
            if (statement.equalsIgnoreCase("UPDATE")) {
                if (statements.length<4 || isStatement(statements[1]) || !statements[2].equalsIgnoreCase("SET") || isStatement(statements[3])) {
                    error_source[0]="UPDATE";
                    return false;
                }
            }

            //TESTIRANJE SELECTA
            if (statement.equalsIgnoreCase("SELECT")) {
                if (statements.length<2 || isStatement(statements[1])) {
                    error_source[0]="SELECT";
                    return false;
                }
            }

            i++;
        }

        return true;
    }
}
