package gui;

import app.AppCore;
import controller.ActionManager;
import lombok.Data;
import observer.Notification;
import observer.Subscriber;
import tree.implementation.SelectionListener;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;

@Data
public class MainFrame extends JFrame implements Subscriber {

    private static MainFrame instance = null;

    private AppCore appCore;
    private JTable jTable;
    private JScrollPane jsp;
    private JTree jTree;
    private ActionManager actionManager;
    private JPanel left;
    private JPanel buttonPanel;
    private JPanel console;
    private static JTextPane textArea;

    private MainFrame() {

    }

    public static MainFrame getInstance() {
        if (instance == null) {
            instance = new MainFrame();
            instance.initialise();
        }
        return instance;
    }


    private void initialise() {

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        jTable = new JTable();
        actionManager=new ActionManager();
        jTable.setPreferredScrollableViewportSize(new Dimension(700, 200));
        jTable.setFillsViewportHeight(true);
        console = new JPanel();
        buttonPanel = new JPanel();

        JButton importButton = new JButton(MainFrame.getInstance().getActionManager().getImportAction());
        JButton exportButton = new JButton(MainFrame.getInstance().getActionManager().getExportAction());
        JButton prettyButton = new JButton(MainFrame.getInstance().getActionManager().getPrettyAction());
        JButton runButton = new JButton(MainFrame.getInstance().getActionManager().getRunAction());
        buttonPanel.add(importButton);
        buttonPanel.add(exportButton);
        buttonPanel.add(prettyButton);
        buttonPanel.add(runButton);
        Border border = console.getBorder();
        Border margin = new EmptyBorder(10, 10, 10, 10);
        console.setBorder(new CompoundBorder(border, margin));
        console.setLayout(new BoxLayout(console, BoxLayout.PAGE_AXIS));

        textArea = new JTextPane();
       // textArea.setContentType("text/html");
        textArea.setPreferredSize(new Dimension(700, 200));
        textArea.setMinimumSize(new Dimension(700, 200));
        textArea.setMaximumSize(new Dimension(700, 200));
        console.add(buttonPanel);
        Font font = new Font("Arial", Font.PLAIN, 14);
        textArea.setFont(font);
        textArea.setMargin(new Insets(10, 10, 10, 10));

        console.add(textArea);
        this.add(console);
        console.add(new JScrollPane(jTable));
        this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);


    }


    public void setAppCore(AppCore appCore) {
        this.appCore = appCore;
        this.appCore.addSubscriber(this);
        this.jTable.setModel(appCore.getTableModel());
        initialiseTree();
    }

    private void initialiseTree() {
        DefaultTreeModel defaultTreeModel = appCore.loadResource();
        jTree = new JTree(defaultTreeModel);
        jTree.addTreeSelectionListener(new SelectionListener());
        jsp = new JScrollPane(jTree, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        left = new JPanel(new BorderLayout());
        left.add(jsp, BorderLayout.CENTER);
        add(left, BorderLayout.WEST);
        pack();
    }

    public static JTextPane getTextArea() {
        return textArea;
    }

    @Override
    public void update(Notification notification) {


    }

    public JTree getjTree() {
        return jTree;
    }
}
