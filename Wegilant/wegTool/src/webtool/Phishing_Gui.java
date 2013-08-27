package webtool;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Create Gui of phishing generator
 * 
 */


public class Phishing_Gui 
{
    //creating new panels, labels and buttons
    public static JPanel panel1=new JPanel(new FlowLayout(FlowLayout.LEFT,20,40));
    JPanel panel2=new JPanel(new FlowLayout(FlowLayout.LEFT,30,0));
    JPanel panel3=new JPanel();
    JPanel panel4=new JPanel(new FlowLayout(FlowLayout.CENTER,30,40));
    public static JTextField target;
    JLabel label;
    public static JButton jb1;
    public static JTextField target1;
    public static JButton jb2;
    public static JPanel jp1=new JPanel(); 
    BufferedImage image;
    
    public Phishing_Gui()
    {
        Color newcolor=new Color(19,37,62);
        jp1=WebTool.jp3;
        
        //setting the background color 
        jp1.setBackground(Color.gray);
        panel1.setBackground(newcolor);
        panel2.setBackground(newcolor);
        panel4.setBackground(newcolor);
        
        //creating new font and color
        Font font1=new Font("Times New Roman", Font.BOLD, 19);
        Color slate=new Color(220,220,220);
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.Y_AXIS));
        
        //setting header image of phising gen.
        JLabel picLabel1 = new JLabel(new ImageIcon(getClass().getResource("/resources/phish.jpg")));
        jp1.add( picLabel1 );
        JPanel trypanel=new JPanel(new FlowLayout((FlowLayout.CENTER), 10, 40));
        jp1.add(trypanel);
        trypanel.setBackground(Color.gray);
        
        //adding panels to frame
        trypanel.add(panel3);
        panel3.add(panel1);
        panel3.add(panel2);
        panel3.add(panel4);
        
        //creating different labels to display
        label = new JLabel("Target : ");
        label.setFont(font1);
        label.setForeground(Color.white);
        panel1.add(label);
        Color slate1=new Color(245,245,245);
        target = new JTextField();
        target.setBackground(slate1);
        target.setPreferredSize(new Dimension(350, 23));
	panel1.add(target);
        
	JLabel label1 = new JLabel("Select location to Save : "); 
        label1.setFont(font1);
        label1.setForeground(Color.white);
        Font font2=new Font("Times New Roman", Font.BOLD, 23);
        
        //creating save button and setting its properties
        jb1= new JButton("save");
        jb1.setFont(font2);
        jb1.setForeground(Color.black);
        jb1.setBackground(slate);
        jb1.setPreferredSize(new Dimension(200, 30));
        panel2.add(label1);
        panel2.add(jb1);
        
        //adding a textfield to take url input
        target1 = new JTextField();
	target1.setColumns(29);
        jb2= new JButton("Start Downloading");
        jb2.setFont(font1);
        jb2.setForeground(Color.black);
        jb2.setBackground(slate);
        panel4.add(jb2);
    }
}
