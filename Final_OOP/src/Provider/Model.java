package Provider;

import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class Model {
    public static Font font1=new Font("Arial",Font.PLAIN,13);
    public static Font font2 = new Font("Arial",Font.BOLD,14);
    public static Font font = new Font("Arial", Font.BOLD,20);
    public static Border roundedBorder = new LineBorder(Color.BLACK, 0, true) {
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
}
