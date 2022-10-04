package controller;

import gui.MainFrame;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class PrettyAction extends AbstractAction {

    public PrettyAction() {
        putValue(NAME, "Pretty");
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        String[] toUpper = MainFrame.getInstance().getTextArea().getText().split(" ");

        for (int i=0;i< toUpper.length;i++) {
            if (isStatement(toUpper[i])) {
                MainFrame.getInstance().getTextArea().setText(MainFrame.getInstance().getTextArea().getText().replaceAll(" "+toUpper[i]+" ","\n"+" "+toUpper[i].toUpperCase()+" "));
            }
        }

        Document document = MainFrame.getInstance().getTextArea().getStyledDocument();
        char[] charArray=MainFrame.getInstance().getTextArea().getText().toCharArray();
        for(int i=0;i< charArray.length;i++){
            if(charArray[i]==' ' && i+1<charArray.length && charArray[i+1]==' '){
                charArray[i]='␝';
            }
        }
        System.out.println(charArray);
        String text= String.valueOf(charArray);
        text=text.replace("␝","");
        MainFrame.getInstance().getTextArea().setText(text);
        String[] words = text.split(" ");

        int pos=0;
        for (int i=0;i< words.length;i++) {
            if (isStatement(words[i])){
                Element element = ((StyledDocument) document).getCharacterElement(pos);
                AttributeSet attributeSet = element.getAttributes();
                MainFrame.getInstance().getTextArea().setSelectionStart(pos);
                MainFrame.getInstance().getTextArea().setSelectionEnd(pos+words[i].length());
                MainFrame.getInstance().getTextArea().replaceSelection(words[i].toUpperCase());
                MutableAttributeSet mutableAttributeSet = new SimpleAttributeSet(attributeSet.copyAttributes());
                StyleConstants.setForeground(mutableAttributeSet, Color.BLUE);
                ((StyledDocument) document).setCharacterAttributes(pos, words[i].length(), mutableAttributeSet, true);
            }
            pos+=words[i].length()+1;
        }
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
