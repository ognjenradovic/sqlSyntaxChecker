package controller.QueryChecker;

public class AliasCheck extends Rule{

    public AliasCheck() {
        super("ALIAS_NEMA_NAVODNIKE");


    }

    @Override
    public boolean check(String query) {
    return true;
    }
}
