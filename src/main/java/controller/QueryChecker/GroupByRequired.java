package controller.QueryChecker;

public class GroupByRequired extends Rule{

    public GroupByRequired() {
        super("NIJE_UNET_GROUPBY");


    }

    @Override
    public boolean check(String query) {
        //Ako select ima nesto select nesto,sum(nesto) onda mu treba group by
        //SELECT ProductName, SUM(Price) FROM [Products] GROUP BY ProductName
        boolean execute;
        boolean select=false;
        boolean selectEnd=false;
        boolean nonAgregate=false;
        boolean agregate=false;
        boolean groupBy=false;
        String[] statements = query.split(" ");
        int i = 0;
        for (String statement : statements) {
        if(statement.equalsIgnoreCase("SELECT"))select=true;
        if(select && !selectEnd && isStatement(statement) && !statement.equalsIgnoreCase("SELECT")){
        selectEnd=true;
        }
        if(select && !selectEnd && statement.toLowerCase().matches(".*(sum|avg|count|max|min).*")){
        error_source[0]=statement.toString();
        agregate=true;
        }
        if(select && !selectEnd && !statement.toLowerCase().matches(".*(sum|avg|count|max|min).*")){
        nonAgregate=true;
        }
        if(statement.equalsIgnoreCase("GROUP") && i+1<=statements.length && statements[i+1].equalsIgnoreCase("BY")){
        groupBy=true;
        }
        i++;
        }
        if(!groupBy && agregate && nonAgregate){
            return false;
        }
        return true;
    }
}
