package controller.QueryChecker;

public class OrderCheck extends Rule{

    public OrderCheck() {
        super("POGRESAN_REDOSLED");
    }

    @Override
    public boolean check(String query) {
    return true;
    }
}
