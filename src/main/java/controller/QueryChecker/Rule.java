package controller.QueryChecker;

import gui.MainFrame;

import java.util.ArrayList;

public abstract class Rule implements RuleInterface{
    String error_code;
    String[] error_source;

    public Rule(String code) {
        this.error_code=code;
        error_source=new String[10];
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String[] getError_source() {
        return error_source;
    }

    public void setError_source(String[] error_source) {
        this.error_source = error_source;
    }

    public boolean isStatement(String statement){
        for (Object key : MainFrame.getInstance().getAppCore().getKeyWords()) {
            if(key.toString().equalsIgnoreCase(statement)){
                return true;
            };
        }
        return false;
    }
}
