package app;

import controller.KeyWords;
import controller.QueryChecker.Checker;
import controller.QueryChecker.DescriptionRepository;
import database.Database;
import database.DatabaseImplementation;
import database.MYSQLrepository;
import database.settings.Settings;
import database.settings.SettingsImplementation;
import gui.table.TableModel;
import lombok.Getter;
import lombok.Setter;
import observer.Notification;
import observer.enums.NotificationCode;
import observer.implementation.PublisherImplementation;
import resource.implementation.InformationResource;
import tree.Tree;
import tree.implementation.TreeImplementation;
import utils.Constants;

import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;

@Getter
@Setter
public class AppCore extends PublisherImplementation {

    private Database database;
    private Settings settings;
    private TableModel tableModel;
    private DefaultTreeModel defaultTreeModel;
    private Tree tree;
    private DescriptionRepository descriptionRepository;
    private Checker checker;
    private ArrayList keyWords;

    public AppCore() {
        this.settings = initSettings();
        this.database = new DatabaseImplementation(new MYSQLrepository(this.settings));
        this.tableModel = new TableModel();
        this.tree = new TreeImplementation();
        this.descriptionRepository=new DescriptionRepository();
        this.checker=new Checker();
        keyWords = new ArrayList<String>();
        for (KeyWords keyWord : KeyWords.values()) {
            keyWords.add(keyWord);
        }

    }

    private Settings initSettings() {
        Settings settingsImplementation = new SettingsImplementation();
        settingsImplementation.addParameter("mysql_ip", Constants.MYSQL_IP);
        settingsImplementation.addParameter("mysql_database", Constants.MYSQL_DATABASE);
        settingsImplementation.addParameter("mysql_username", Constants.MYSQL_USERNAME);
        settingsImplementation.addParameter("mysql_password", Constants.MYSQL_PASSWORD);
        return settingsImplementation;
    }


    public DefaultTreeModel loadResource(){
        InformationResource ir = (InformationResource) this.database.loadResource();
        return this.tree.generateTree(ir);
    }

    public void readDataFromTable(String fromTable){

        tableModel.setRows(this.database.readDataFromTable(fromTable));

        this.notifySubscribers(new Notification(NotificationCode.DATA_UPDATED, this.getTableModel()));
    }

    public String export(String tableToExport){

        return this.database.export(tableToExport);
    }

    public void run(String query){
        Checker checker=new Checker();
        if(checker.check(query)){
            tableModel.setRows(this.database.run(query));
            this.notifySubscribers(new Notification(NotificationCode.DATA_UPDATED, this.getTableModel()));
        }
    }

    public void runNoReturn(String query){
        Checker checker=new Checker();
        if(checker.check(query)){
            this.database.runNoReturn(query);
        }
    }

    public void insert(String from,String columnOrder,String values){
    }






}
