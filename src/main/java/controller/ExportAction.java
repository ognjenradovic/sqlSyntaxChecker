package controller;

import gui.MainFrame;
import resource.data.Row;
import resource.implementation.Entity;
import tree.TreeItem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;

public class ExportAction extends AbstractAction {

    public ExportAction() {
        putValue(NAME, "Export");
    }

    public void actionPerformed(ActionEvent e) {
        TreeItem node = (TreeItem) MainFrame.getInstance().getjTree().getLastSelectedPathComponent();
        /* if nothing is selected */
        String name = (String)JOptionPane.showInputDialog(
                MainFrame.getInstance(),
                "Unesi ime .CSV fajla",
                "Export",
                JOptionPane.PLAIN_MESSAGE,
                null,
                null,
                "exportTest"
        );
        if(name != null && name.length() > 0){

        }
        else{
            name="testCSV";
        }
        if (node == null || !(node.getDbNode() instanceof Entity)) return;

       // List<Row> rows = new ArrayList<>();
        StringBuilder stringBuilder=new StringBuilder();

        List<Row> rows=MainFrame.getInstance().getAppCore().getTableModel().getRows();
       // System.out.println(rows);
        int columnCount = rows.get(0).getFields().keySet().size();

        for(int j=0;j<rows.size();j++){
            Row row=rows.get(j);
            for (int i = 0; i<columnCount; i++){
                Object[] values=row.getFields().values().toArray();
                System.out.println(values[i].toString());
                stringBuilder.append(values[i].toString());
                if(i!=columnCount){
                    stringBuilder.append(";");
                }
            }
            stringBuilder.append("\n");
        }

        try (PrintWriter writer = new PrintWriter(name+".csv")) {
            String export=MainFrame.getInstance().getAppCore().export(node.getName());
            writer.write(export);
        } catch (FileNotFoundException e1) {
           System.out.println(e1.getMessage());
        }
    }
}
