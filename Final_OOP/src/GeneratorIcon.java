import javax.swing.*;
import java.awt.*;

public class GeneratorIcon {
    public static ImageIcon create(String src,int width,int height){
        ImageIcon icon = new ImageIcon(src);
       Image ico = icon.getImage().getScaledInstance(width,height,0);
       return new ImageIcon(ico);
    }
}
