package Feature;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellEditor;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.EventObject;
import java.util.concurrent.atomic.AtomicReference;

import com.toedter.calendar.JDateChooser;

public class Staying {
    private static int leangNumber=0;
    private static AtomicReference<Object> objectSearch = new AtomicReference<>(null);
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
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        defaultTableModel.addColumn("ID Card");
        defaultTableModel.addColumn("Full Name");
        defaultTableModel.addColumn("Phone Number");
        defaultTableModel.addColumn("Date Hire");
        defaultTableModel.addColumn("Date Expire");
        defaultTableModel.addRow(new Object[]{"1001","Davann CR","0967960968","12/12/2021","12/12/2023"});
        defaultTableModel.addRow(new Object[]{"1001","Davann CR","0967960968","12/12/2021","12/12/2023"});
        defaultTableModel.addRow(new Object[]{"1001","Davann CR","0967960968","12/12/2021","12/12/2023"});
        defaultTableModel.addRow(new Object[]{"1001","Davann CR","0967960968","12/12/2021","12/12/2023"});
        defaultTableModel.addRow(new Object[]{"1001","Davann CR","0967960968","12/12/2021","12/12/2023"});
        JTable table = new JTable(defaultTableModel);
        table.setCellEditor(new TableCellEditor() {
            @Override
            public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
                return null;
            }

            @Override
            public Object getCellEditorValue() {
                return null;
            }

            @Override
            public boolean isCellEditable(EventObject anEvent) {
                return false;
            }

            @Override
            public boolean shouldSelectCell(EventObject anEvent) {
                return false;
            }

            @Override
            public boolean stopCellEditing() {
                return false;
            }

            @Override
            public void cancelCellEditing() {

            }

            @Override
            public void addCellEditorListener(CellEditorListener l) {

            }

            @Override
            public void removeCellEditorListener(CellEditorListener l) {

            }
        });
        table.setBackground(Color.white);
        table.setCellSelectionEnabled(false);
        table.setCellSelectionEnabled(true);
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
        return panel;
    }
}
