package controller;

import gui.MainFrame;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RunAction extends AbstractAction {

    public RunAction() {
        putValue(NAME, "Run");
    }

    public void actionPerformed(ActionEvent e) {
        String text = MainFrame.getInstance().getTextArea().getText();
        text = text.replaceAll("\\r|\\n", "");
        if(text.toLowerCase().matches(".*(select).*")){
            MainFrame.getInstance().getAppCore().run(text);
        }
        else{
            MainFrame.getInstance().getAppCore().runNoReturn(text);
        }
       // MainFrame.getInstance().getAppCore().run("select * from countries");
        //MainFrame.getInstance().getAppCore().run(text);
        //MainFrame.getInstance().getAppCore().run("insert into regions (region_id,region_name) values (5,\"Juzna Amerika\")");
       // MainFrame.getInstance().getAppCore().run("select");
        //SELECT ProductName, SUM(Price) FROM [Products] GROUP BY ProductName
        //MainFrame.getInstance().getAppCore().run("Select city from locations join countries on (locations.country_id = countries.country_id)");

    }
}
