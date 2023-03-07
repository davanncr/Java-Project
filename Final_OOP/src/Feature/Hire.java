package Feature;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import java.util.Objects;

import Provider.Model;
import Provider.MysqlService;
import com.toedter.calendar.JDateChooser;

public class Hire{
    private static Statement statement;
    private static int numberDays;
    private static JTextField jtfFullname = new JTextField();
    private static JRadioButton jtfFemale = new JRadioButton("Female");
    private static JRadioButton jtfMale = new JRadioButton("Male");
    private static JTextField jtfPhone = new JTextField();
    private static JTextField jtfIDCard = new JTextField();
    private static JDateChooser dateHire = new JDateChooser(new Date());
    private static JDateChooser dateExpire = new JDateChooser(new Date(new Date().getYear(),new Date().getMonth(),new Date().getDate()+1));
    private static JComboBox<String> room = new JComboBox<>();
    public static JPanel getPanel(){
        JPanel panel = new JPanel();
        panel.setBackground(new Color(247, 247, 245));
        panel.setLayout(null);
        //fullname
        JLabel lblFullname=new JLabel("Full Name");
        lblFullname.setBounds(50,20,100,25);
        lblFullname.setFont(Model.font1);
        panel.add(lblFullname);

        jtfFullname.setBounds(50,45,600,30);
        jtfFullname.setRequestFocusEnabled(true);
        jtfFullname.setHorizontalAlignment(SwingConstants.CENTER);

        panel.add(jtfFullname);
        //Gender

        ButtonGroup groupGender = new ButtonGroup();
        jtfFemale.setBounds(70,80,80,25);
        jtfFemale.setBackground(new Color(247, 247, 245));
        jtfFemale.setFont(Model.font2);
        panel.add(jtfFemale);

        jtfMale.setBounds(150,80,100,25);
        jtfMale.setBackground(new Color(247, 247, 245));
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

        jtfPhone.setBounds(50,135,600,30);
        jtfPhone.setRequestFocusEnabled(true);
        jtfPhone.setHorizontalAlignment(SwingConstants.CENTER);
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

        jtfIDCard.setBounds(50,200,600,30);
        jtfIDCard.setRequestFocusEnabled(true);
        jtfIDCard.setHorizontalAlignment(SwingConstants.CENTER);
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

        dateHire.setBounds(50,270,600,30);

        dateHire.setRequestFocusEnabled(true);
        dateHire.setSelectableDateRange(new Date(),null);
        panel.add(dateHire);
        //Date expire
        JLabel lblExpire = new JLabel("Date Expire");
        lblExpire.setBounds(50,320,150,25);
        lblExpire.setFont(Model.font1);
        panel.add(lblExpire);

        dateExpire.setBounds(50,345,600,30);
        dateExpire.setRequestFocusEnabled(true);

        dateExpire.setSelectableDateRange(new Date(new Date().getYear(),new Date().getMonth(),new Date().getDate()+1),null);
        dateExpire.setBorder(Model.roundedBorder);
        panel.add(dateExpire);
        //room
        JLabel roomLabel = new JLabel("Room");
        roomLabel.setBounds(50,390,600,30);
        roomLabel.setFont(Model.font1);
        panel.add(roomLabel);

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
        JButton btnSave = new JButton("Submit");
        btnSave.setFont(Model.font2);
        btnSave.setUI(new javax.swing.plaf.basic.BasicButtonUI(){
            @Override
            public void installDefaults(AbstractButton btn){
                super.installDefaults(btn);
                btn.setBackground(new Color(250, 119, 2));
            }
        });
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
                                java.sql.Date hire_date = new java.sql.Date(dateHire.getDate().getTime());
                                java.sql.Date expire_date = new java.sql.Date(dateExpire.getDate().getTime());
                                String commandUpdate = "UPDATE `roomdb` SET `id_card` = '"+jtfIDCard.getText().trim()+"', `fullname` = '"+jtfFullname.getText().trim()+"', `sex` = '"+gender+"', `phone` = '"+jtfPhone.getText().trim()+"', `date_hire` = '"+hire_date+"', `date_expire` = '"+expire_date+"', `number_day` = "+numberDays+", `total_price` = "+prices+", `status` = 1 WHERE `room` = '"+roomName+"';";
                                int option = JOptionPane.showConfirmDialog(null,"Total: "+prices+"$","Total price",JOptionPane.YES_NO_OPTION);
                                if(option == 0){
                                    int result = statement.executeUpdate(commandUpdate);
                                    jtfFullname.setText("");
                                    jtfFemale.setSelected(false);
                                    jtfMale.setSelected(false);
                                    jtfIDCard.setText("");
                                    jtfPhone.setText("");
                                    jtfFemale.setSelected(false);
                                    jtfMale.setSelected(false);
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
