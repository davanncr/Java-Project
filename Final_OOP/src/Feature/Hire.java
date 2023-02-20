package Feature;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class Hire {
    private static Font font=new Font("Arial",Font.PLAIN,15);
    private static Border roundedBorder = new LineBorder(Color.BLACK, 0, true) {
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g;
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.setColor(getLineColor());
            g2d.setStroke(new BasicStroke(getThickness()));
            RoundRectangle2D rect = new RoundRectangle2D.Double(x, y, width-2 , height-2 , 30, 30);
            g2d.draw(rect);
        }
    };
    public static JPanel getPanel(){
        JPanel panel = new JPanel();
        panel.setBackground(Color.white);
        panel.setLayout(null);
        //fullname
        JLabel lblFullname=new JLabel("Full Name");
        lblFullname.setBounds(55,20,100,25);
        lblFullname.setFont(font);
        panel.add(lblFullname);
        JTextField jtfFullname = new JTextField();
        TextField fn =new TextField();
        jtfFullname.setBounds(50,45,600,30);
        jtfFullname.setRequestFocusEnabled(true);
        jtfFullname.setHorizontalAlignment(SwingConstants.CENTER);
        jtfFullname.setBorder(roundedBorder);
        panel.add(jtfFullname);
        //
        return panel;
    }
}
