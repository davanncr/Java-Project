package Feature;

import Provider.GeneratorIcon;
import Provider.Model;
import Provider.MysqlService;
import Provider.TableEditable;
import com.toedter.calendar.JDateChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class History {
    private static int leangNumber=8;
    private static AtomicReference<Object> objectSearch = new AtomicReference<>(null);
    private static TableEditable defaultTableModel;
    private static ArrayList<Integer> rows = new ArrayList<>();
    private static boolean verifySaving = true;
    private static ImageIcon iconTable;
    private static Statement statement;
    private static int no;
    private static JScrollPane scrollPane;
    private static JTable table;
    public static JPanel getPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        //Filter
        JComboBox<String> filter = new JComboBox<String>();
        filter.addItem("ID Card");
        filter.addItem("Phone Number");
        filter.addItem("Room");
        filter.addItem("Full Name");
        filter.addItem("Date Hire");
        filter.addItem("Date Expire");
        filter.setBounds(50,30,150,30);
        filter.setRequestFocusEnabled(true);
        //filter.setBorder(Model.roundedBorder);
        panel.add(filter);
        //search
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout());
        searchPanel.setBackground(Color.RED);
        searchPanel.setBounds(250,30,250,30);
        //search by date
        JDateChooser searchDate = new JDateChooser();
        //search by text
        JTextField searchText = new JTextField();
        //search by number
        JTextField searchNumber = new JTextField();
        searchNumber.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(searchNumber.getText().trim().length()>leangNumber){
                    e.consume();
                }else{
                    if(!Character.isDigit(e.getKeyChar())){
                        e.consume();
                    }
                    super.keyTyped(e);
                }
            }
        });
        searchPanel.add(searchNumber);
        filter.setSelectedIndex(0);
        filter.addItemListener(e->{
            if(filter.getSelectedIndex()<2){
                if(searchNumber.getText().trim().length()>9){
                    searchNumber.setText(searchNumber.getText().substring(0,9));
                }
                searchPanel.removeAll();
                searchPanel.add(searchNumber);
                leangNumber=filter.getSelectedIndex()==0?8:9;
                objectSearch.set(searchNumber);
                searchPanel.revalidate();
                searchPanel.repaint();
            }else if(filter.getSelectedIndex()==2||filter.getSelectedIndex()==3){
                searchPanel.removeAll();
                searchPanel.add(searchText);
                objectSearch.set(searchText);
                searchPanel.revalidate();
                searchPanel.repaint();
            } else{
                searchPanel.removeAll();
                searchPanel.add(searchDate);
                objectSearch.set(searchDate);
                searchPanel.revalidate();
                searchPanel.repaint();
            }
        });
        panel.add(searchPanel);

        //button search
        JButton btnSearch = new JButton("Search");
        btnSearch.setBounds(550,30,100,30);
        panel.add(btnSearch);
        //Table
        JPanel tablePanel = new JPanel();
        tablePanel.setBounds(50,100,600,400);
        tablePanel.setLayout(new GridLayout());
        tablePanel.setBackground(Color.white);
        defaultTableModel = new TableEditable();
        defaultTableModel.addColumn("NO");
        defaultTableModel.addColumn("Room");
        defaultTableModel.addColumn("ID Card");
        defaultTableModel.addColumn("Full Name");
        defaultTableModel.addColumn("Sex");
        defaultTableModel.addColumn("Phone");
        defaultTableModel.addColumn("Date Hire");
        defaultTableModel.addColumn("Date Expire");


        try {
            statement= MysqlService.getConnection().createStatement();
            String commandSelect = "SELECT `no`,`id_card`,`fullname`,`sex`,`phone`,`date_hire`,`date_expire`,`room` FROM `history` ORDER BY `no`";
            ResultSet resultSet = statement.executeQuery(commandSelect);
            while (resultSet.next()){
                defaultTableModel.addRow(new Object[]{resultSet.getInt(1),resultSet.getString(8),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),resultSet.getString(7)});
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        table = new JTable(defaultTableModel);
        table.setRowHeight(25);
        table.setSelectionForeground(Color.BLUE);
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionBackground(new Color(245, 151, 0));
        table.getTableHeader().setFont(new Font(null,Font.BOLD,11));
//        table.getTableHeader().setBackground(new Color(229, 90, 0));

        //new Color(229, 90, 0);
//        table.getTableHeader().setUI(new javax.swing.plaf.basic.BasicTableHeaderUI(){
//            @Override
//            public void installUI(JComponent c) {
//                super.installUI(c);
//                c.setBackground( new Color(229, 90, 0));
//            }
//        });
        //tablePanel.setBorder(BorderFactory.createLineBorder( new Color(229, 90, 0)));
        scrollPane = new JScrollPane(table);
        scrollPane.setBackground(Color.white);
        tablePanel.add(scrollPane);
        panel.add(tablePanel);
        //button operators
        String[] operatorName={"Edit","Delete"};
        JButton[] operatorButton = new JButton[operatorName.length];
        for (int i=0; i<operatorButton.length;i++){
            operatorButton[i] = new JButton(operatorName[i]);
            operatorButton[i].setFont(Model.font2);
            operatorButton[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            operatorButton[i].setUI(new javax.swing.plaf.basic.BasicButtonUI(){
                @Override
                public void installDefaults(AbstractButton btn){
                    super.installDefaults(btn);
                    btn.setBackground(new Color(250, 119, 2));
                }
            });
            //operatorButton[i].setBackground(new Color(250, 119, 2));
            operatorButton[i].setBounds(230+150*i,535,100,30);
            panel.add(operatorButton[i]);
        }
        //icons
        JLabel iconLabel = new JLabel();
        iconTable= GeneratorIcon.create("src/ICON/pointing.png",20,20);
        iconLabel.setIcon(iconTable);
        //iconLabel.setBounds(650,150,30,30);
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                iconLabel.setVisible(true);
                if(verifySaving){
                    iconLabel.setBounds(650,110+e.getY(),30,30);
                }
                super.mousePressed(e);
            }
        });
        panel.add(iconLabel);
        //edit on click
        operatorButton[0].addActionListener(e->{
            int selectedRow = table.getSelectedRow();
            rows.add(selectedRow);

            if (selectedRow != -1) {
                if(operatorButton[0].getLabel().equalsIgnoreCase("Edit")) {
                    no =Integer.parseInt(table.getValueAt(table.getSelectedRow(),0).toString());
                    verifySaving=false;
                    iconLabel.removeAll();
                    iconLabel.setIcon(GeneratorIcon.create("src/ICON/writing.png",20,20));
                    defaultTableModel.setRowEditable(selectedRow, true);
                    operatorButton[0].setLabel("Save");
                } else{
                    verifySaving=true;
                    iconLabel.removeAll();
                    iconLabel.setIcon(iconTable= GeneratorIcon.create("src/ICON/pointing.png",20,20));
                    defaultTableModel.setRowEditable(rows.get(0), false);
                    operatorButton[0].setLabel("Edit");
                    rows=new ArrayList<>();
                }
                table.setRowSelectionInterval(selectedRow, selectedRow);
                table.setEnabled(true);
                table.editCellAt(selectedRow, 0);
                Component editorComponent = table.getEditorComponent();
                if (editorComponent != null) {
                    editorComponent.requestFocus();
                }
                if(verifySaving){
                    try {
                        int index = table.getSelectedRow();
                        statement = MysqlService.getConnection().createStatement();
                        String commandUpdate = "UPDATE `history` set `room`='"+table.getValueAt(index,1)+"',`id_card`='"+table.getModel().getValueAt(index,2)+"',`fullname`='"+table.getModel().getValueAt(index,3)+"',`sex`='"+table.getModel().getValueAt(index,4)+"',`phone`='"+table.getModel().getValueAt(index,5)+"',`date_hire`='"+table.getModel().getValueAt(index,6)+"',`date_expire`='"+table.getModel().getValueAt(index,7)+"' WHERE `no`="+ no +";";
                        table.setValueAt(no,index,0);
                        //System.out.println(commandUpdate);
                        statement.executeUpdate(commandUpdate);
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            }
            iconLabel.revalidate();
            iconLabel.repaint();
        });
        //delete on click
        operatorButton[1].addActionListener(e->{
            try {
                statement = MysqlService.getConnection().createStatement();
                String commandUpdate = "DELETE FROM `history` WHERE `no` = "+table.getValueAt(table.getSelectedRow(),0)+";";
                statement.executeUpdate(commandUpdate);
                iconLabel.setVisible(false);
                defaultTableModel.removeRow(table.getSelectedRow());
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        //searching
        btnSearch.addActionListener(e->{
            String[] option={"id_card","phone","room","fullname","date_hire","date_expire"};
            String commandLine;
            if(filter.getSelectedIndex()<2){
                commandLine = "SELECT `no`,`id_card`,`fullname`,`sex`,`phone`,`date_hire`,`date_expire`,`room` FROM `history` WHERE `"+option[filter.getSelectedIndex()]+"`LIKE '%"+searchNumber.getText().trim()+"%' ORDER BY `room`;";
            }else if(filter.getSelectedIndex()==2||filter.getSelectedIndex()==3){
                commandLine = "SELECT `no`,`id_card`,`fullname`,`sex`,`phone`,`date_hire`,`date_expire`,`room` FROM `history` WHERE `"+option[filter.getSelectedIndex()]+"` LIKE '%"+searchText.getText().trim()+"%' ORDER BY `room`;";
            }else{
                Date dt = new Date(searchDate.getDate().getTime());
                commandLine = "SELECT `no`,`id_card`,`fullname`,`sex`,`phone`,`date_hire`,`date_expire`,`room` FROM `history` WHERE `"+option[filter.getSelectedIndex()]+"` = '"+dt+"' ORDER BY `room`;";
            }
            while (defaultTableModel.getRowCount()>0){
                defaultTableModel.removeRow(0);
            }
            try {
                statement= MysqlService.getConnection().createStatement();
                ResultSet resultSet = statement.executeQuery(commandLine);
                while (resultSet.next()){
                    defaultTableModel.addRow(new Object[]{resultSet.getInt(1),resultSet.getString(8),resultSet.getString(2),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5),resultSet.getString(6),resultSet.getString(7)});
                }
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        });
        return panel;
    }
}
