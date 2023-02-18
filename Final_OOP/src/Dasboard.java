import javax.swing.*;
import java.awt.*;

public class Dasboard extends Window{
    private JPanel superPanel;//for store other panel (rightPanel,featurePanel)
    private JPanel rightPanel;//for store other panel on the right
    private JPanel featurePanel;//for store other feature
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
        featurePanel.setBackground(new Color(242, 0, 116));
        //declare variables


    }
    private void setRightPanel(){
        rightPanel = new JPanel();
        rightPanel.setSize(getWidth()-getWidth()/5,getHeight());
        rightPanel.setBackground(Color.white);
        rightPanel.setLocation(getWidth()/5,0);
    }

}
