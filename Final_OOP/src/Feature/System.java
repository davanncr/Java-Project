package Feature;

import Provider.Model;
import Provider.MysqlService;
import Provider.TableEditable;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class System {
    private static DefaultTableModel tableModel;
    private static JTable table;
    private static JScrollPane scrollPane;
    private static Connection connection = MysqlService.getConnection();
    private static Statement statement;
    private static int indexRow;
    private static JTextField jtfPrice = new JTextField();
    private static JComboBox<String> optionRoom;
    private static Font font = new Font("Arial",Font.BOLD,20);
    private static JTextField jtfNewFloor = new JTextField();
    private static JTextField jtfNumberRoom = new JTextField();

    public static JPanel getPanel(){
        JComboBox<String> listFloors =new JComboBox<>();
        JPanel systemPrice = new JPanel();
        JPanel systemReport = new JPanel();
        JPanel tablePanel = new JPanel();
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2,1));
        panel.setBackground(Color.WHITE);
        //System Price
        systemPrice.setLayout(null);
        systemPrice.setBackground(Color.WHITE);
        JButton buttonSet = new JButton("Update");
            //title
                JLabel lblUpdatePrice = new JLabel("System Price");
                lblUpdatePrice.setBounds(460,50,300,30);
                lblUpdatePrice.setFont(font);
                systemPrice.add(lblUpdatePrice);
            //Room Option
        optionRoom = new JComboBox<>();
        optionRoom.addItem("Normal");
        optionRoom.addItem("Raise price");
        optionRoom.addItem("Discount");
        optionRoom.addItem("Set price all");
        optionRoom.addItem("Increase price");
        optionRoom.addItem("Decrease price");
        optionRoom.setBounds(370,90,300,30);
        systemPrice.add(optionRoom);
            //table
        tableModel = new TableEditable();
        tableModel.addColumn("Floor");
        tableModel.addColumn("Price");
        try {
            statement = connection.createStatement();
            String commandSelect = "SELECT * FROM `priceroom`;";
            ResultSet resultSet = statement.executeQuery(commandSelect);
            while (resultSet.next()) {
                listFloors.addItem(resultSet.getString(1));
                tableModel.addRow(new Object[]{resultSet.getString(1),resultSet.getDouble(2)});
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        table = new JTable(tableModel);
        table.setSelectionForeground(Color.BLUE);
        table.getTableHeader().setReorderingAllowed(false);
        table.setSelectionBackground(new Color(245, 151, 0));
        table.getTableHeader().setFont(new Font(null,Font.BOLD,11));
        scrollPane = new JScrollPane(table);
        tablePanel.setBounds(30,50,300,219);
        tablePanel.setLayout(new GridLayout());
        tablePanel.add(scrollPane);
        systemPrice.add(tablePanel);
        panel.add(systemPrice);
            //Input value
            jtfPrice.setBounds(370,140,300,30);
            jtfPrice.addKeyListener(new KeyAdapter() {
                @Override
                public void keyTyped(KeyEvent e) {
                        if(!Character.isDigit(e.getKeyChar())){
                            e.consume();
                        }
                        super.keyTyped(e);
                    }
                });

            systemPrice.add(jtfPrice);
            //button set price
            buttonSet.setBounds(460,190,120,30);
            buttonSet.setFont(Model.font2);
            buttonSet.setUI(new javax.swing.plaf.basic.BasicButtonUI(){
                @Override
                public void installDefaults(AbstractButton btn){
                    super.installDefaults(btn);
                    btn.setBackground(new Color(250, 119, 2));
                }
            });
            systemPrice.add(buttonSet);
            buttonSet.addActionListener(e->{
                String commandSQL="";
                indexRow = table.getSelectedRow();
                try {
                    statement = connection.createStatement();

                    if(optionRoom.getSelectedIndex()==0){
                        commandSQL = "UPDATE `priceroom` SET `price` = "+Double.valueOf(jtfPrice.getText())+" WHERE `floor`='"+table.getValueAt(indexRow,0)+"';";
                        statement.executeUpdate(commandSQL);
                        tableModel.setValueAt(Double.valueOf(jtfPrice.getText()),table.getSelectedRow(),1);
                       optionRoom.setSelectedIndex(0);
                    }else if(optionRoom.getSelectedIndex()==1){
                        int size = table.getRowCount();
                        double priceIncrement = Double.parseDouble(jtfPrice.getText());
                        double defaultPrice;
                        java.lang.System.out.println(priceIncrement);
                        commandSQL="";
                        for(int i = table.getSelectedRow();i<size;i++){
                            defaultPrice = Double.parseDouble(table.getValueAt(i,1).toString());
                            tableModel.setValueAt(priceIncrement+defaultPrice,i,1);
                            commandSQL = "UPDATE `priceroom` SET `price` = "+Double.valueOf(table.getValueAt(i,1).toString())+" WHERE `floor`='"+table.getValueAt(i,0)+"';";
                            statement.executeUpdate(commandSQL);
                        }
                        optionRoom.setSelectedIndex(1);
                    }else if(optionRoom.getSelectedIndex()==2){
                        int size = table.getRowCount();
                        double priceDecrement = Double.parseDouble(jtfPrice.getText());
                        double defaultPrice;
                        java.lang.System.out.println(priceDecrement);
                        for(int i = table.getSelectedRow();i<size;i++){
                            defaultPrice = Double.parseDouble(table.getValueAt(i,1).toString());
                            tableModel.setValueAt(defaultPrice-priceDecrement,i,1);
                            commandSQL= "UPDATE `priceroom` SET `price` = "+Double.valueOf(table.getValueAt(i,1).toString())+" WHERE `floor`='"+table.getValueAt(i,0)+"';";
                            statement.executeUpdate(commandSQL);
                        }
                        optionRoom.setSelectedIndex(2);
                    }else if(optionRoom.getSelectedIndex()==3){
                        for(int i = 0;i<table.getRowCount();i++){
                            tableModel.setValueAt(Double.parseDouble(jtfPrice.getText()),i,1);
                            commandSQL= "UPDATE `priceroom` SET `price` = "+Double.valueOf(table.getValueAt(i,1).toString())+" WHERE `floor`='"+table.getValueAt(i,0)+"';";
                            statement.executeUpdate(commandSQL);
                        }
                        optionRoom.setSelectedIndex(3);
                    }else if(optionRoom.getSelectedIndex()==4){
                        int size = table.getRowCount();
                        double priceIncrement = Double.parseDouble(jtfPrice.getText());
                        double defaultPrice;
                        java.lang.System.out.println(priceIncrement);
                        for(int i = table.getSelectedRow();i<size;i++){
                            defaultPrice = Double.parseDouble(table.getValueAt(i,1).toString());
                            tableModel.setValueAt(priceIncrement*(i-table.getSelectedRow()+1)+defaultPrice,i,1);
                            commandSQL= "UPDATE `priceroom` SET `price` = "+Double.valueOf(table.getValueAt(i,1).toString())+" WHERE `floor`='"+table.getValueAt(i,0)+"';";
                            statement.executeUpdate(commandSQL);
                        }
                        optionRoom.setSelectedIndex(4);
                    }else if(optionRoom.getSelectedIndex()==5){
                        int size = table.getRowCount();
                        double priceDecrement = Double.parseDouble(jtfPrice.getText());
                        double defaultPrice;
                        for(int i = table.getSelectedRow();i<size;i++){
                            defaultPrice = Double.parseDouble(table.getValueAt(i,1).toString());
                            tableModel.setValueAt(defaultPrice-priceDecrement*(i+1),i,1);
                            commandSQL= "UPDATE `priceroom` SET `price` = "+Double.valueOf(table.getValueAt(i,1).toString())+" WHERE `floor`='"+table.getValueAt(i,0)+"';";
                            statement.executeUpdate(commandSQL);
                        }
                        optionRoom.setSelectedIndex(5);
                    }
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            });
            //Add more floor
                //title
                JLabel lblAddMore = new JLabel("Add More Floor");
                lblAddMore.setFont(font);
                lblAddMore.setBounds(100,10,200,30);
                systemReport.add(lblAddMore);
                //input new floor
                JLabel lblTitleInputNewFloor = new JLabel("Name of Floor");
                lblTitleInputNewFloor.setBounds(30,50,200,30);
                jtfNewFloor.setBounds(130,50,200,25);
                systemReport.add(jtfNewFloor);
                systemReport.add(lblTitleInputNewFloor);
                //input number floor
                JLabel lblTitleInputNumberFloor = new JLabel("Number of Room");
                lblTitleInputNumberFloor.setBounds(30,90,200,30);
                jtfNumberRoom.setBounds(130,90,200,25);
                jtfNumberRoom.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        if(!Character.isDigit(e.getKeyChar())){
                            e.consume();
                        }
                        super.keyTyped(e);
                    }
                });
                systemReport.add(jtfNumberRoom);
                systemReport.add(lblTitleInputNumberFloor);
                //button Add Floor
                JButton btnAddMoreFloor = new JButton("Add");
                btnAddMoreFloor.setBounds(170,130,120,30);
                btnAddMoreFloor.setFont(Model.font2);
                btnAddMoreFloor.setUI(new javax.swing.plaf.basic.BasicButtonUI(){
                    @Override
                    public void installDefaults(AbstractButton btn){
                        super.installDefaults(btn);
                        btn.setBackground(new Color(250, 119, 2));
                    }
                });
                btnAddMoreFloor.addActionListener(e->{
                    int x =JOptionPane.showConfirmDialog(null,"Are you sure?","Add More Floor",JOptionPane.YES_NO_OPTION);
                    if(x == 0){
                        try {
                            statement = connection.createStatement();
                            int numRoom = Integer.parseInt(jtfNumberRoom.getText());
                            String nameFloor = jtfNewFloor.getText().toUpperCase();
                            String commandLine="INSERT INTO `roomdb` VALUES ";
                            for(int i = 1;i<=numRoom;i++){
                                if(i<10&&i<numRoom){
                                    commandLine +="('"+nameFloor+"000"+i+"',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),";
                                }else if(i>=10&&i<numRoom){
                                    commandLine +="('"+nameFloor+"00"+i+"',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0),";
                                }else if(i==numRoom&&i>=10){
                                    commandLine +="('"+nameFloor+"00"+i+"',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0);";
                                }else if(i==numRoom&&i<10){
                                    commandLine +="('"+nameFloor+"000"+i+"',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,0);";
                                }
                            }
                            int success = statement.executeUpdate(commandLine);
                            commandLine = "INSERT INTO `priceroom` VALUES ('"+nameFloor+"',0)";
                            int success1 = statement.executeUpdate(commandLine);
                            if(success==numRoom &&success1==1){
                                tableModel.addRow(new Object[]{nameFloor,0.00});
                                listFloors.addItem(nameFloor);
                                jtfNumberRoom.setText("");
                                jtfNewFloor.setText("");
                                JOptionPane.showMessageDialog(null,"Add successfully 👌👌👌");
                            }else{
                                JOptionPane.showMessageDialog(null,"Add not successfully ❌❌❌");
                            }
                        } catch (SQLException ex) {
                            throw new RuntimeException(ex);
                        }
                    }else{

                    }
                });
                systemReport.add(btnAddMoreFloor);
        //remove floor
            //title
            JLabel lblRemoveFloor = new JLabel("Remove Floor");
            lblRemoveFloor.setFont(font);
            lblRemoveFloor.setBounds(450,10,200,30);
            systemReport.add(lblRemoveFloor);
            //list of floor
            JLabel blbListFloor  = new JLabel("Choose Floor");
            blbListFloor.setBounds(370,50,120,30);
            systemReport.add(blbListFloor);
            listFloors.setBounds(450,50,200,30);
            systemReport.add(listFloors);
            //button remove floor
            JButton btnRemoveFloor = new JButton("Remove");
            btnRemoveFloor.setBounds(495,100,120,30);
            btnRemoveFloor.setFont(Model.font2);
            btnRemoveFloor.setUI(new javax.swing.plaf.basic.BasicButtonUI(){
                @Override
                public void installDefaults(AbstractButton btn){
                    super.installDefaults(btn);
                    btn.setBackground(new Color(250, 119, 2));
                }
            });
            systemReport.add(btnRemoveFloor);
            btnRemoveFloor.addActionListener(e->{
                int x =JOptionPane.showConfirmDialog(null,"Are you sure?","Add More Floor",JOptionPane.YES_NO_OPTION);
                if(x == 0){
                    try {
                        statement = connection.createStatement();
                        String commandLine1 = "DELETE FROM `roomdb` WHERE `room` LIKE '"+listFloors.getSelectedItem()+"%';";
                        String commandLine2 = "DELETE FROM `priceroom` WHERE `floor`='"+listFloors.getSelectedItem()+"';";
                        int result1 = statement.executeUpdate(commandLine1);
                        int result2 = statement.executeUpdate(commandLine2);
                        if(result2 == 1&&result1>0){
                            tableModel.removeRow(listFloors.getSelectedIndex());
                            listFloors.removeItemAt(listFloors.getSelectedIndex());
                            listFloors.setSelectedIndex(0);
                            JOptionPane.showMessageDialog(null,"Remove successfully 👌👌👌");
                        }else{
                            JOptionPane.showMessageDialog(null,"Add not successfully ❌❌❌");
                        }
                    } catch (SQLException ex) {
                        throw new RuntimeException(ex);
                    }
                }

            });
        //System Report
        systemReport.setLayout(null);
        systemReport.setBackground(Color.WHITE);
        panel.add(systemReport);
        panel.revalidate();
        panel.repaint();
        return panel;
    }
}
