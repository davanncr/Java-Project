package Dasboard;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

public class Window extends JFrame {
    public Window(){
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        setUndecorated(true);
        setSize(900,600);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        final int[] mouseX = new int[1];
        final int[] mouseY = new int[1];
        getContentPane().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                mouseX[0] = e.getX();
                mouseY[0] = e.getY();
            }
        });
        getContentPane().addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                setLocation(getX() + e.getX() - mouseX[0], getY() + e.getY() - mouseY[0]);
            }

            @Override
            public void mouseMoved(MouseEvent e) {

            }
        });
        openWindow();
    }
    public void openWindow(){
        setVisible(true);
    }
    public void closeWindow(){
        setVisible(false);
    }
}
