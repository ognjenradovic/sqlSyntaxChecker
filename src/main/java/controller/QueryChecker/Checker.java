package controller.QueryChecker;

import gui.MainFrame;

import java.util.ArrayList;

public class Checker {
    private ArrayList<Rule> rules;
    private ArrayList<ErrorDescription> errors;
    public boolean check(String query){
            for(Rule r:rules){
                if(!r.check(query)){
                    for(ErrorDescription error:MainFrame.getInstance().getAppCore().getDescriptionRepository().getErrorDescriptions()){
                        if(r.getError_code().equalsIgnoreCase(error.getError_code())){
                            errors.add(error);
                            error.setError_source(r.getError_source());
                        }
                    }
                }
            }
            if(errors.size()==0){
                return true;
            }
            else{
                for(ErrorDescription error:errors){

                    System.out.println(error.getError_code());
                    if(error.getError_source()[0]!=null){
                        System.out.println(error.getDescription().toString().replace("%s",error.getError_source()[0]));
                        System.out.println(error.getSuggestion().toString().replace("%s",error.getError_source()[0]));
                    }

                }
                return false;
            }
    }

    public Checker(){
        rules=new ArrayList<Rule>();
        errors=new ArrayList<ErrorDescription>();
        rules.add(new TableExsistsRule());
        rules.add(new CSVcompatableRule());
        rules.add(new RequiredCheck());
        rules.add(new WhereCheck());
        rules.add(new GroupByRequired());
        rules.add(new PkJoinCheck());
    }
    public void cleanErrors(){
        //this.errors=errors.removeAll();
    }
}
