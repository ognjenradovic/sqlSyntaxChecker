package controller;

import controller.enums.AttributeType;
import gui.MainFrame;
import resource.DBNode;
import resource.implementation.Attribute;
import resource.implementation.Entity;
import tree.TreeItem;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class ImportAction extends AbstractAction {

    public ImportAction() {
        putValue(NAME, "Import");
    }

    public void actionPerformed(ActionEvent e) {
        JFileChooser jFileChooser = new JFileChooser();
        String from;
        StringBuilder columnOrderSB=new StringBuilder();
        TreeItem node = (TreeItem) MainFrame.getInstance().getjTree().getLastSelectedPathComponent();
        /* if nothing is selected */
        //String values;
       // jFileChooser.setFileFilter(new FileFilter());
        if(node!=null){
            if (jFileChooser.showOpenDialog(MainFrame.getInstance()) == JFileChooser.APPROVE_OPTION) {
                try {
                    DBNode dbNode= (DBNode) node.getDbNode();
                    int[] stringOrNum=new int[100];
                    if(dbNode instanceof Entity){
                        Entity tableEntity=(Entity)dbNode;
                        int i=0;
                        for(DBNode dbNodeAttribute:tableEntity.getChildren()) {
                            if (dbNodeAttribute instanceof Attribute) {
                                i++;
                                AttributeType attributeType = ((Attribute) dbNodeAttribute).getAttributeType();
                                if(attributeType.toString().equals("VARCHAR") || attributeType.toString().equals("TEXT") || attributeType.toString().equals("CHAR")){
                                    stringOrNum[i]=1;
                                }
                                else{
                                    stringOrNum[i]=0;
                                }

                            }
                        }
                    }


                    String line;
                    BufferedReader br = new BufferedReader(new FileReader(jFileChooser.getSelectedFile()));
                    line=br.readLine();

                    String[] order = line.split(";");
                    columnOrderSB.append("(");
                    for(int j=0;j<order.length;j++){
                        columnOrderSB.append(order[j]);
                        if(j!= order.length-1)columnOrderSB.append(",");
                    }
                    columnOrderSB.append(")");
                    while ((line = br.readLine()) != null)   //returns a Boolean value
                    {
                        StringBuilder valuesSB=new StringBuilder();
                        String[] values = line.split(";");
                        valuesSB.append("(");

                        for(int j=0;j<values.length;j++){
                            if(stringOrNum[j]==1)valuesSB.append("\"");
                            valuesSB.append(values[j]);
                            if(stringOrNum[j]==1)valuesSB.append("\"");
                            if(j!= values.length-1)valuesSB.append(",");
                        }
                        valuesSB.append(")");

                        //  MainFrame.getInstance().getAppCore().insert(node.getName(),columnOrderSB.toString(),valuesSB.toString());
                        String query = "INSERT INTO " + node.getName() +" "+ columnOrderSB.toString() + " values " + valuesSB.toString();
                        MainFrame.getInstance().getAppCore().runNoReturn(query);


                    }

                } catch (FileNotFoundException e1) {
                    // e.printStackTrace();
                } catch (IOException e1) {
                    // e.printStackTrace();
                }

            }
        }

    }
}
