package Dasboard;

import Feature.*;
import Feature.System;
import Provider.GeneratorIcon;
import Provider.Model;
import Provider.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Dasboard extends Window {
    private JPanel superPanel;//for store other panel (rightPanel,featurePanel)
    private JPanel rightPanel;//for store other panel on the right
    private JPanel featurePanel;//for store other feature
    private JPanel subRightPanel;
    private static byte k=1;
    public Dasboard(){
        setSuperPanel();
        add(superPanel);
        revalidate();
        repaint();
    }
    private void setSuperPanel(){
        superPanel = new JPanel();
        superPanel.setSize(getWidth(), getHeight());
        superPanel.setBackground(Color.RED);
        superPanel.setLayout(null);
        setFeaturePanel();
        add(featurePanel);
        setRightPanel();
        add(rightPanel);
    }
    private void setFeaturePanel(){
        featurePanel = new JPanel();
        featurePanel.setSize(getWidth()/5, getHeight());
        featurePanel.setBackground(new Color(229, 90, 0));
        featurePanel.setLayout(null);
        //home icon
        ImageIcon homeIcon = GeneratorIcon.create("src/ICON/home.png",50,50);
        JLabel homeLabel = new JLabel(homeIcon);
        homeLabel.setBounds(50,20,60,60);
        featurePanel.add(homeLabel);
        //label
        String[] labelTitle ={"Hire","Staying","Leaving","History","System"};
        String[] iconSrc ={"hire.png","staying.png","leaving.png","history.png","setting.png"};
        JLabel[] label = new JLabel[labelTitle.length];
        for(int i=0; i<label.length;i++){
            label[i] = new JLabel(labelTitle[i],GeneratorIcon.create("src/ICON/"+iconSrc[i],25,20),SwingConstants.LEFT);
            label[i].setBounds(10, 90+45*i, featurePanel.getWidth(), 30);
            label[i].setFont(Model.font);
            label[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            featurePanel.add(label[i]);
        }
        JPanel selectedLabel = new JPanel();
        selectedLabel.setBounds(0,label[0].getY()-5,featurePanel.getWidth(),40);
        selectedLabel.setBackground(new Color(245, 151, 0));
        label[0].addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                selectedLabel.setBounds(0,label[0].getY()-5,featurePanel.getWidth(),40);
                rightPanel.removeAll();
                rightPanel.add(Hire.getPanel());
                revalidate();
                repaint();
                k=1;
            }
        });
        label[1].addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                selectedLabel.setBounds(0,label[1].getY()-5,featurePanel.getWidth(),40);
                rightPanel.removeAll();
                rightPanel.add(Staying.getPanel());
                revalidate();
                repaint();
                k=1;
            }
        });
        label[2].addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                selectedLabel.setBounds(0,label[2].getY()-5,featurePanel.getWidth(),40);
                rightPanel.removeAll();
                rightPanel.add(Leaving.getPanel());
                revalidate();
                repaint();
                k=1;
            }
        });
        label[3].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectedLabel.setBounds(0,label[3].getY()-5,featurePanel.getWidth(),40);
                rightPanel.removeAll();
                rightPanel.add(History.getPanel());
                revalidate();
                repaint();
                k=1;
            }
        });
        label[4].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(k==1){
                    JPasswordField jpf = new JPasswordField();

                    int verify= JOptionPane.showConfirmDialog(Model.messageBackground(),jpf,"Security",JOptionPane.YES_NO_OPTION,JOptionPane.PLAIN_MESSAGE);
                    String password = jpf.getText();
                    if(password.equals("system")&&verify==0){
                        selectedLabel.setBounds(0,label[4].getY()-5,featurePanel.getWidth(),40);
                        rightPanel.removeAll();
                        rightPanel.add(System.getPanel());
                        revalidate();
                        repaint();
                        k=0;
                    }else if(verify==0){
                        JOptionPane.showMessageDialog(null,"Password is invalid!");
                    }
                }else{
                    selectedLabel.setBounds(0,label[4].getY()-5,featurePanel.getWidth(),40);
                    rightPanel.removeAll();
                    rightPanel.add(System.getPanel());
                    revalidate();
                    repaint();
                }


            }
        });
        //Exit Button
        JPanel btnExit = new JPanel();
        JLabel lblExit = new JLabel();
        lblExit.setIcon(GeneratorIcon.create("src/ICON/shutdown.png",25,25));
        lblExit.setHorizontalAlignment(SwingConstants.CENTER);
        btnExit.setBackground(new Color(240, 40, 0));
        btnExit.setBounds(0,featurePanel.getHeight()-30,featurePanel.getWidth(),30);
        btnExit.setBorder(BorderFactory.createEmptyBorder());
        btnExit.setLayout(new GridLayout());
        lblExit.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnExit.add(lblExit);
        btnExit.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int x =JOptionPane.showConfirmDialog(null,"Are you sure?","Shutdown",JOptionPane.YES_NO_OPTION);
                if(x ==0){
                    dispose();
                }
                super.mousePressed(e);
            }
        });
        featurePanel.add(btnExit);
        featurePanel.add(selectedLabel);
    }
    private void setRightPanel(){
        rightPanel = new JPanel();
        rightPanel.setSize(getWidth()-getWidth()/5,getHeight());
        rightPanel.setBackground(new Color(222, 218, 209));
        rightPanel.setLocation(getWidth()/5,0);
        rightPanel.setLayout(new GridLayout());
        rightPanel.add(Hire.getPanel());
    }
}
