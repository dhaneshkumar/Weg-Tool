package webtool;

/**
 *
 * @author Dhanesh kumar
 */

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import java.awt.*;
import java.net.*;
import java.lang.*;
import java.util.*;
import java.io.IOException;
import javax.swing.UIManager.*;
import webtool.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import com.seaglasslookandfeel.SeaGlassLookAndFeel;

/*
 * Main gui class 
 * It contains main GUI window
 */
public class WebTool extends JFrame
{
    public static  BufferedImage image;
    public static int width_tab;
    public static JLabel pic = new JLabel();
    public static JPanel jp1 = new JPanel();//This will create the first tab
    public static JPanel jp2 = new JPanel();//This will create the second tab
    public static JPanel jp3 = new JPanel();
    //Google api key for finding google  search result
    public static String googleApiKey ="AIzaSyCFv2EEWfYdgZzBTPDR0-P5tRuAWG6h730";
            //"AIzaSyBVHBECAJ7vBT1mFT-9xclZN59QcWp29GA";        
    public static int count=0;        
 
    /*
     * constructor : specifing width of tab 
     *               setting title and layout
     *               setting wegilant header
     */
    public WebTool(int b)
    {
        width_tab=b;
        setTitle("WegTooL"); 
        setLayout(new BorderLayout());
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                System.exit(0);
            }
        });
       
        //adding wegilant header image in main window
        JLabel picLabel = new JLabel(new ImageIcon(getClass().getResource("/resources/top.jpg")));
        add( picLabel, BorderLayout.NORTH );
            
	/**
         * Creating different tabs and adding different properties
         */
        JTabbedPane jtp = new JTabbedPane();
        jtp.setBackground(Color.DARK_GRAY);
        jtp.setForeground(Color.black);
        add(jtp);
      
        jtp.addTab("<html><body style=\"font: bold 17pt Georgia, 'Times New Roman';\"><table width= " + width_tab +">Admin Finder</table></body></html>", jp1);
        jp1.setBackground(Color.GRAY); 
        jtp.addTab("<html><body style=\"font: bold 17pt Georgia, 'Times New Roman';\"><table width= " + width_tab +">Sql Scanner</table></body></html>", jp2);
        jtp.addTab("<html><body style=\"font: bold 17pt Georgia, 'Times New Roman';\"><table width= " + "190" +">Phishing Generator</table></body></html>", jp3);
        jp2.setBackground(Color.GRAY);
    }
   
  
    public static void main(String[] args) {
        
        //setting the proxy
        /*
        final String authUser = "";
        final String authPassword = "";

        System.getProperties().put("http.proxyHost", "netmon.iitb.ac.in");
        System.getProperties().put("http.proxyPort", "80");
        System.getProperties().put("http.proxyUser", authUser);
        System.getProperties().put("http.proxyPassword", authPassword);
                    
        System.getProperties().put("https.proxyHost", "netmon.iitb.ac.in");
        System.getProperties().put("https.proxyPort", "80");
        System.getProperties().put("https.proxyUser", authUser);
        System.getProperties().put("https.proxyPassword", authPassword);
                    
        Authenticator.setDefault(
            new Authenticator() {
                public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(
                    authUser, authPassword.toCharArray());
                }
            }
        );
        
       */
        
        
        
        /**
         *  Creating a new frame & adding it's specifications
         */
        int no_tab=4; 
        int width_tab=150;  // width of each tab
        int height_window=580;
        
        //adding seaglasslookandfeel look and feel 
        try{
            UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
        
        WebTool tp = new WebTool(width_tab);
        
        //Creating an adminFinder object to display admin finder gui in main window
        adminFinder add=new adminFinder();
        add.admin();
        
        //Creating an Sql Scanner object to display sqli gui in main window
        new Sqli_Gui();
        new Sqli_back(googleApiKey);
        
        //Creating an Phishing Generator object to display phishing gui in main window
        new Phishing_Gui();
        new phishing_back();
       
       //Setting width of main window
        int width;
        width = (no_tab * width_tab);
        if(width<500){
            width=500;
        }
        else{
            width = width + 80;
        }
       
        // setting gui properties of main window
        tp.setBounds(50, 50, width, height_window);
        tp.setResizable(false);
        removeMinMaxClose(tp); 
        tp.setVisible(true);
        
        /************************************************************************************************/ 
    }
    
    
    
    /**
     * Function for removing maximize and menu Button
     * @param comp 
     */
    public static void removeMinMaxClose(Component comp)  
  {  
      
    if(comp instanceof AbstractButton)  
    {  
     
         if(comp !=null && comp.getName()!=null){
                if(comp.getName().toString().equals("InternalFrameTitlePane.maximizeButton")){
                    comp.getParent().remove(comp);  
                 }
                if(comp.getName().toString().equals("InternalFrameTitlePane.menuButton")){
                    comp.getParent().remove(comp);  
                 }
               
         }
    }
   
    if (comp instanceof Container)  
    {  
      
      Component[] comps = ((Container)comp).getComponents();  
      
      for(int x = 0, y = comps.length; x < y; x++)  
      {  
         
        removeMinMaxClose(comps[x]);  
      }  
    }
  }  
}