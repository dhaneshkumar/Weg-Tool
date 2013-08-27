package webtool;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * 
 * Creating GUI of sql scanner
 * @author Dhanesh kumar
 */
public class Sqli_Gui {
    JPanel sqlPanel=new JPanel();
    JPanel newPanel=new JPanel();
    JPanel panel1=new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
    JPanel panel2=new JPanel(new BorderLayout(50,9));
    JPanel panel3=new JPanel(new FlowLayout(FlowLayout.CENTER,100, 12));
    public static JButton btnNewButton;
    public static JButton btnNewButton1;
    public static JScrollPane scrollPane;
    public static JPanel jp1 = new JPanel();
    BufferedImage image;
    public static JComboBox dorkList;
    public static JTable table;
    public static JProgressBar spbar;
    
    public Sqli_Gui()
    {
        jp1=WebTool.jp2;
        
        //setting the background, layout, header image
        panel1.setBackground(Color.GRAY);
        panel2.setBackground(Color.GRAY);
        panel3.setBackground(Color.GRAY);
        
        //adding header image of sql scanner
        JLabel picLabel1 = new JLabel(new ImageIcon(getClass().getResource("/resources/sql.jpg")));
        jp1.add( picLabel1);
        sqlPanel.setLayout(new BoxLayout(sqlPanel, BoxLayout.Y_AXIS));
        jp1.add(sqlPanel);
        sqlPanel.add(panel1);
        sqlPanel.add(panel2);
        sqlPanel.add(panel3);
      
        //creating a new font
        Font font1=new Font("Times New Roman", Font.BOLD, 20);
        JLabel lblTarget = new JLabel("Search : ");
        lblTarget.setForeground(Color.BLACK);
        lblTarget.setFont(font1);
        panel1.add(lblTarget);
        
        //creating drop-down list of dorks
        String[] dorks = {"","index.php?id=","trainers.php?id=","pageid=","games.php?id=", "page.php?file=","newsDetail.php?id=","gallery.php?id=","show.php?id=","staff_id=","newsitem.php?num=","readnews.php?id="};
        dorkList = new JComboBox(dorks);
        dorkList.setEditable(true);
        dorkList.setPreferredSize(new Dimension(350, 23));
        panel1.add(dorkList);

        //creating a new scrolllpane 
        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(100, 200));
	panel2.add(scrollPane, BorderLayout.NORTH);
        Color slate=new Color(220,220,220);
        
        //crating a new table for storing and displaying data
        String[] columnNames = {"<html><body><p style=\"font: bold 18pt Georgia, 'Times New Roman', Times;\">Result</p></body></html>"};
        Object[][] data=new Object[0][1];
        table = new JTable(data, columnNames);
        table.setEnabled(false);
        scrollPane.setViewportView(table);
        
        //Adding a progres Bar
        spbar = new JProgressBar();
        spbar.setMinimum(0);
        spbar.setPreferredSize(new Dimension(100, 20));
        panel2.add(spbar, BorderLayout.SOUTH);
        Font font2=new Font("Times New Roman",Font.BOLD, 19);

        //Adding start button
        btnNewButton = new JButton("Start");
        btnNewButton.setFont(font2);
        panel3.add(btnNewButton);
        btnNewButton.setBackground(slate);
        btnNewButton.setForeground(Color.BLACK);
        
        //Adding stop button
        btnNewButton1 = new JButton("Stop");
        btnNewButton1.setFont(font2);
        panel3.add(btnNewButton1);
        btnNewButton1.setBackground(slate);
        btnNewButton1.setForeground(Color.BLACK);
    }
}
