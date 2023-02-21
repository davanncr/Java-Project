package Provider;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import com.toedter.calendar.JDateChooser;

public class JDateChooseRadius extends JDateChooser {



    @Override
    protected void paintComponent(Graphics g) {
        setBorder(Model.roundedBorder);
    }

}
