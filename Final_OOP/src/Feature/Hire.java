package Feature;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.Date;
import java.util.Objects;

import Provider.Model;
import Provider.MysqlService;
import com.toedter.calendar.JDateChooser;

public class Hire{
    private static Statement statement;
    private static int numberDays;
    private static double price;
    public static JPanel getPanel(){
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setLayout(null);
        //fullname
        JLabel lblFullname=new JLabel("Full Name");
        lblFullname.setBounds(50,20,100,25);
        lblFullname.setFont(Model.font1);
        panel.add(lblFullname);
        JTextField jtfFullname = new JTextField();
        jtfFullname.setBounds(50,45,600,30);
        jtfFullname.setRequestFocusEnabled(true);
        jtfFullname.setHorizontalAlignment(SwingConstants.CENTER);
        jtfFullname.setBorder(Model.roundedBorder);
        panel.add(jtfFullname);
        //Gender
        JLabel lblGender = new JLabel("Choose gender: ");
        JRadioButton jtfFemale = new JRadioButton("Female");
        ButtonGroup groupGender = new ButtonGroup();
        jtfFemale.setBounds(70,80,80,25);
        jtfFemale.setBackground(Color.white);
        jtfFemale.setFont(Model.font2);
        panel.add(jtfFemale);
        JRadioButton jtfMale = new JRadioButton("Male");
        jtfMale.setBounds(150,80,100,25);
        jtfMale.setBackground(Color.white);
        jtfMale.setFont(Model.font2);
        groupGender.add(jtfMale);
        groupGender.add(jtfFemale);
        panel.add(jtfMale);
        panel.add(jtfMale);
        //phone number
        JLabel lblPhone = new JLabel("Phone Number");
        lblPhone.setBounds(50,110,150,25);
        lblPhone.setFont(Model.font1);
        panel.add(lblPhone);
        JTextField jtfPhone = new JTextField();
        jtfPhone.setBounds(50,135,600,30);
        jtfPhone.setRequestFocusEnabled(true);
        jtfPhone.setHorizontalAlignment(SwingConstants.CENTER);
        jtfPhone.setBorder(Model.roundedBorder);
        jtfPhone.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(jtfPhone.getText().trim().length()>9){
                    e.consume();
                }else{
                    if(!Character.isDigit(e.getKeyChar())){
                        e.consume();
                    }
                    super.keyTyped(e);
                }

            }
        });
        panel.add(jtfPhone);
        //ID Card
        JLabel lblIDCard = new JLabel("ID Card");
        lblIDCard.setBounds(50,175,150,25);
        lblIDCard.setFont(Model.font1);
        panel.add(lblIDCard);
        JTextField jtfIDCard = new JTextField();
        jtfIDCard.setBounds(50,200,600,30);
        jtfIDCard.setRequestFocusEnabled(true);
        jtfIDCard.setHorizontalAlignment(SwingConstants.CENTER);
        jtfIDCard.setBorder(Model.roundedBorder);
        jtfIDCard.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent e) {
                if(jtfIDCard.getText().trim().length()>8){
                    e.consume();
                }else{
                    if(!Character.isDigit(e.getKeyChar())){
                        e.consume();
                    }
                    super.keyTyped(e);
                }

            }
        });
        panel.add(jtfIDCard);
        //Date hire
        JLabel lblHire = new JLabel("Date Hire");
        lblHire.setBounds(50,245,150,25);
        lblHire.setFont(Model.font1);
        panel.add(lblHire);
        JDateChooser dateHire = new JDateChooser();
        dateHire.setBounds(50,270,600,30);
        dateHire.setDate(new Date());
        dateHire.setRequestFocusEnabled(true);
        dateHire.setSelectableDateRange(new Date(),null);
        dateHire.setBorder(Model.roundedBorder);
        panel.add(dateHire);
        //Date expire
        JLabel lblExpire = new JLabel("Date Expire");
        lblExpire.setBounds(50,320,150,25);
        lblExpire.setFont(Model.font1);
        panel.add(lblExpire);
        JDateChooser dateExpire = new JDateChooser();
        dateExpire.setBounds(50,345,600,30);
        dateExpire.setRequestFocusEnabled(true);
        dateExpire.setDate(new Date(new Date().getYear(),new Date().getMonth(),new Date().getDate()+1));
        dateExpire.setSelectableDateRange(new Date(new Date().getYear(),new Date().getMonth(),new Date().getDate()+1),null);
        dateExpire.setBorder(Model.roundedBorder);
        panel.add(dateExpire);
        //room
        JLabel roomLabel = new JLabel("Room");
        roomLabel.setBounds(50,390,600,30);
        roomLabel.setFont(Model.font1);
        panel.add(roomLabel);
        JComboBox<String> room = new JComboBox<>();
        room.setBounds(50,420,600,30);
        try {
            Statement statement = MysqlService.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT `room` FROM `roomdb` WHERE `status`=0 ORDER BY `room`");
            while (resultSet.next()) {
                room.addItem(resultSet.getString(1));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        panel.add(room);
        //Save
        Button btnSave = new Button("Submit");

        btnSave.setBackground(new Color(242, 0, 116));
        btnSave.setBounds(300,490,100,30);
        btnSave.addActionListener(e->{
            if(jtfPhone.getText().length()==9||jtfPhone.getText().length()==10){
                if(jtfIDCard.getText().length()==9){
                    if(jtfFemale.isSelected()||jtfMale.isSelected()){
                        String[] separatedName = jtfFullname.getText().split(" ");
                        if(separatedName.length==2){
                            try {
                                statement=MysqlService.getConnection().createStatement();
                                numberDays = (int)((dateExpire.getDate().getTime()-dateHire.getDate().getTime())/(24*60*60*1000));
                                String roomName = Objects.requireNonNull(room.getSelectedItem()).toString();
                                ResultSet roomPrice = statement.executeQuery("SELECT `price` FROM `priceroom` WHERE `floor`='"+roomName.charAt(0)+"' limit 1");
                                double prices=0;
                                while (roomPrice.next()){
                                    prices = roomPrice.getDouble(1);
                                }
                                prices = prices*numberDays;
                                String gender = jtfFemale.isSelected()?"Female":"Male";
                                String hire_date = dateHire.getDate().getDate()+"-"+(dateHire.getDate().getMonth()+1)+"-"+(dateHire.getDate().getYear()+1900);
                                String expire_date = dateExpire.getDate().getDate()+"-"+(dateExpire.getDate().getMonth()+1)+"-"+(dateExpire.getDate().getYear()+1900);
                                String commandUpdate = "UPDATE `roomdb` SET `id_card` = '"+jtfIDCard.getText().trim()+"', `fullname` = '"+jtfFullname.getText().trim()+"', `sex` = '"+gender+"', `phone` = '"+jtfPhone.getText().trim()+"', `date_hire` = '"+hire_date+"', `date_expire` = '"+expire_date+"', `number_day` = "+numberDays+", `total_price` = "+prices+", `status` = 1 WHERE `room` = '"+roomName+"';";
                                int option = JOptionPane.showConfirmDialog(null,"Total: "+prices+"$","Total price",JOptionPane.YES_NO_OPTION);
                                if(option == 0){
                                    int result = statement.executeUpdate(commandUpdate);
                                    jtfFullname.setText("");
                                    jtfFemale.setSelected(false);
                                    jtfMale.setSelected(false);
                                    jtfIDCard.setText("");
                                    jtfPhone.setText("");
                                    dateHire.setDate(new Date());
                                    room.removeItemAt(room.getSelectedIndex());
                                    dateExpire.setDate(new Date(new Date().getYear(),new Date().getMonth(),new Date().getDate()+1));
                                    if(result ==1){
                                        JOptionPane.showMessageDialog(null,"Submit is successfully","Submission",JOptionPane.INFORMATION_MESSAGE);
                                    }else{
                                        JOptionPane.showMessageDialog(null,"Submit is not successfully","Submission",JOptionPane.ERROR);
                                    }
                                }
                            } catch (SQLException ex) {
                                ex.printStackTrace();
                            }
                        }else{
                            JOptionPane.showMessageDialog(null,"Fullname is invalid","Fullname",JOptionPane.WARNING_MESSAGE);
                        }
                    }else{
                        JOptionPane.showMessageDialog(null,"Don't forget choose sex","Sex",JOptionPane.WARNING_MESSAGE);
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Length of ID Card must equal to 9","ID Card",JOptionPane.WARNING_MESSAGE);
                }
            }else{
                JOptionPane.showMessageDialog(null,"Length of phone number must equal to 9 or 10","Phone Number",JOptionPane.WARNING_MESSAGE);
            }
        });
        panel.add(btnSave);
        return panel;
    }
}
