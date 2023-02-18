import javax.swing.*;
public class Window extends JFrame {
    public Window(){
        setSize(900,600);
        setLocationRelativeTo(null);
        setLayout(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        openWindow();
    }
    public void openWindow(){
        setVisible(true);
    }
    public void closeWindow(){
        setVisible(false);
    }
}
