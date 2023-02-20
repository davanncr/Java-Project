import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

public class Dasboard extends Window{
    private JPanel superPanel;//for store other panel (rightPanel,featurePanel)
    private JPanel rightPanel;//for store other panel on the right
    private JPanel featurePanel;//for store other feature
    private Font font = new Font("Arial", Font.BOLD,20);
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
        featurePanel.setLayout(null);
        //home icon
        ImageIcon homeIcon = GeneratorIcon.create("src/ICON/home.png",50,50);
        JLabel homeLabel = new JLabel(homeIcon);
        homeLabel.setBounds(30,10,60,60);
        featurePanel.add(homeLabel);
        //label
        String[] labelTitle ={"Hire","Staying","Leaving","History"};
        String[] iconSrc ={"hire.png","staying.png","leaving.png","history.png"};
        JLabel[] label = new JLabel[labelTitle.length];
        for(int i=0; i<label.length;i++){
            label[i] = new JLabel(labelTitle[i],GeneratorIcon.create("src/ICON/"+iconSrc[i],25,20),SwingConstants.LEFT);
            label[i].setBounds(10, 90+45*i, featurePanel.getWidth(), 30);
            label[i].setFont(font);
            label[i].setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
            featurePanel.add(label[i]);
        }
        JPanel selectedLabel = new JPanel();
        selectedLabel.setBounds(0,label[0].getY()-5,featurePanel.getWidth(),40);
        selectedLabel.setBackground(new Color(140, 140, 140));
        label[0].addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                selectedLabel.setBounds(0,label[0].getY()-5,featurePanel.getWidth(),40);
            }
        });
        label[1].addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                selectedLabel.setBounds(0,label[1].getY()-5,featurePanel.getWidth(),40);
            }
        });
        label[2].addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                selectedLabel.setBounds(0,label[2].getY()-5,featurePanel.getWidth(),40);
            }
        });
        label[3].addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                selectedLabel.setBounds(0,label[3].getY()-5,featurePanel.getWidth(),40);
            }
        });
        featurePanel.add(selectedLabel);
    }
    private void setRightPanel(){
        rightPanel = new JPanel();
        rightPanel.setSize(getWidth()-getWidth()/5,getHeight());
        rightPanel.setBackground(Color.white);
        rightPanel.setLocation(getWidth()/5,0);
    }
}
