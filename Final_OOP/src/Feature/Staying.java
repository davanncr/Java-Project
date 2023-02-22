package Feature;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import Provider.GeneratorIcon;
import Provider.Model;
import Provider.TableEditable;
import com.toedter.calendar.JDateChooser;

public class Staying {
    private static int leangNumber=0;
    private static AtomicReference<Object> objectSearch = new AtomicReference<>(null);
    private static TableEditable defaultTableModel;
    private static ArrayList<Integer> rows = new ArrayList<>();
    private static ImageIcon iconTable;
    public static JPanel getPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        //Filter
        JComboBox<String> filter = new JComboBox<String>();
        filter.addItem("ID Card");
        filter.addItem("Phone Number");
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
            searchPanel.add(searchText);
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
            }else if(filter.getSelectedIndex()==2){
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
        defaultTableModel.addColumn("ID Card");
        defaultTableModel.addColumn("Full Name");
        defaultTableModel.addColumn("Phone Number");
        defaultTableModel.addColumn("Date Hire");
        defaultTableModel.addColumn("Date Expire");
        defaultTableModel.addRow(new Object[]{"1001","Davann Tet","0967960968","12/12/2021","12/12/2023"});
        defaultTableModel.addRow(new Object[]{"1002","Davann Tet","0967960968","12/12/2021","12/12/2023"});
        defaultTableModel.addRow(new Object[]{"1003","Davann Tet","0967960968","12/12/2021","12/12/2023"});
        defaultTableModel.addRow(new Object[]{"1004","Davann Tet","0967960968","12/12/2021","12/12/2023"});
        defaultTableModel.addRow(new Object[]{"1005","Davann Tet","0967960968","12/12/2021","12/12/2023"});
        JTable table = new JTable(defaultTableModel);
        table.setBackground(Color.white);
        table.setCellSelectionEnabled(false);
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBackground(Color.white);
        tablePanel.add(scrollPane);
        panel.add(tablePanel);
        //button operators
        String[] operatorName={"Edit","Delete","Finish"};
        JButton[] operatorButton = new JButton[operatorName.length];
        for (int i=0; i<operatorButton.length;i++){
            operatorButton[i] = new JButton(operatorName[i]);
            operatorButton[i].setBounds(150+150*i,535,100,30);
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
                iconLabel.setBounds(650,110+e.getY(),30,30);
                super.mousePressed(e);
            }
        });
        panel.add(iconLabel);
        //edit on click
        operatorButton[0].addActionListener(e->{
                int selectedRow = table.getSelectedRow();
                rows.add(selectedRow);

                if (selectedRow != -1) {
                    if(operatorButton[0].getText().equalsIgnoreCase("Edit")) {
                        iconLabel.removeAll();
                        iconLabel.setIcon(GeneratorIcon.create("src/ICON/writing.png",20,20));
                        defaultTableModel.setRowEditable(selectedRow, true);
                        operatorButton[0].setText("Save");
                    } else{
                        iconLabel.removeAll();
                        iconLabel.setIcon(iconTable= GeneratorIcon.create("src/ICON/pointing.png",20,20));

                        defaultTableModel.setRowEditable(rows.get(0), false);
                        operatorButton[0].setText("Edit");
                        rows=new ArrayList<>();
                    }
                    table.setRowSelectionInterval(selectedRow, selectedRow);
                    table.setEnabled(true);
                    table.editCellAt(selectedRow, 0);
                    Component editorComponent = table.getEditorComponent();
                    if (editorComponent != null) {
                        editorComponent.requestFocus();
                    }


                }
                iconLabel.revalidate();
                iconLabel.repaint();
        });
        //delete on click
        operatorButton[1].addActionListener(e->{
            defaultTableModel.removeRow(table.getSelectedRow());
        });
        //finish on click
        operatorButton[2].addActionListener(e->{
            defaultTableModel.removeRow(table.getSelectedRow());
        });
        return panel;
    }
}
