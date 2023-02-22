package Feature;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;

import Provider.Model;
import com.toedter.calendar.JDateChooser;

public class Hire{

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
        dateExpire.setSelectableDateRange(new Date(),null);
        dateExpire.setBorder(Model.roundedBorder);
        panel.add(dateExpire);
        //Save
        Button btnSave = new Button("Submit");
        btnSave.setBackground(new Color(242, 0, 116));
        btnSave.setBounds(300,425,100,30);
        panel.add(btnSave);
        return panel;
    }
}
