package controller;


public class ActionManager {

    private PrettyAction prettyAction;
    private ImportAction importAction;
    private ExportAction exportAction;
    private RunAction runAction;





    public ActionManager() {
        initActions();
    }

    private void initActions(){
        prettyAction = new PrettyAction();
        importAction =new ImportAction();
        exportAction =new ExportAction();
        runAction =new RunAction();
    }

    public RunAction getRunAction() {
        return runAction;
    }

    public void setRunAction(RunAction runAction) {
        this.runAction = runAction;
    }

    public PrettyAction getPrettyAction() {
        return prettyAction;
    }
    public void setPrettyAction(PrettyAction prettyAction) {
        this.prettyAction = prettyAction;
    }
    public ImportAction getImportAction() {
        return importAction;
    }
    public void setImportAction(ImportAction importAction) {
        this.importAction = importAction;
    }
    public ExportAction getExportAction() {
        return exportAction;
    }
    public void setExportAction(ExportAction exportAction) {
        this.exportAction = exportAction;
    }
}
