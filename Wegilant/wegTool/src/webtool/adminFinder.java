
package webtool;

/**
 *
 * @author Dhanesh kumar
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * 
 * Class to Create Gui of Admin Finder
 */

public class adminFinder {
    BufferedImage image;
    public static JTextField txtHttp;
    JButton btnNewButton;
    JButton btnNewButton_1;
    public static JProgressBar pbar;
    public static JTable table;
    File file;
    public static JScrollPane scrollPane;
    JLabel label;
    public static String start="<html><body><p style=\"font: 15 pt,bold, 'Times New Roman', Times;\">";
    public static String end="</p></body></html>";
    public static String start1="<html><body><p align=\"right\"  style=\"font: 15 pt,bold, 'Times New Roman', Times;text-align:center;\">";
    
    /*
     * Creating panels for storing components
     *  adminPanel  : contains panel1, panel2 and panel3
     *  newPanel    : contains label target and textfield
     *  panel2      : contains scrollPane and progress bar
     *  Panel3      : contains start and stop buttons
     */
    
    JPanel adminPanel=new JPanel();
    JPanel newPanel=new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 10));
    JPanel panel2=new JPanel(new BorderLayout(50, 9));
    int size;
    JPanel panel3=new JPanel(new FlowLayout(FlowLayout.CENTER,100, 12));
    JLabel pic = new JLabel();
    public static JPanel jp1 = new JPanel();
    public static String finalUrl;
    Vector<String> fileStorage = new Vector<String>(1000);
    Vector<String> storage1 = new Vector<String>(200);
    Vector<String> storage2 = new Vector<String>(200);
    Vector<String> storage3 = new Vector<String>(200);
    Vector<String> storage4 = new Vector<String>(200);
    Vector<String> storage5 = new Vector<String>(200);
    public static Vector<String> output = new Vector<String>(1000);
    public static int stopFlag=0;
    public static volatile boolean running = true;

  
    public adminFinder()
    {
        super();
       
       //Intialising first tab of wegTool 
       jp1=WebTool.jp1;
       
       //setting gray background
       jp1.setBackground(Color.GRAY);
       newPanel.setBackground(Color.GRAY);
       panel2.setBackground(Color.GRAY);
       panel3.setBackground(Color.GRAY);
       
       
       try{
           //loading the header image of admin panel tab
            JLabel picLabel1 = new JLabel(new ImageIcon(getClass().getResource("/resources/admin2.jpg")));
            jp1.add( picLabel1);
            
            //Reading admin.txt and storing its values in five different vectors
            InputStream stream = adminFinder.class.getResourceAsStream("/resources/admin.txt");
            BufferedReader br = new BufferedReader (new InputStreamReader(stream));
            String s,t;
             
            while ((s =br.readLine()) !=null)
            {
                 fileStorage.add(s);
            }
            size=fileStorage.size()/5;
            stream.close();
               
             for(int i=0;i<size;i++)
             {
                 storage1.add(fileStorage.get(i));
             }
            
             for(int i=0;i<size;i++)
             {
                 storage2.add(fileStorage.get(size + i));
             }
             
             for(int i=0;i<size;i++)
             {
                 storage3.add(fileStorage.get(2*size+i));
             }
             
             for(int i=0;i<size;i++)
             {
                 storage4.add(fileStorage.get(3*size + i));
             }
             
             for(int i=0;i<fileStorage.size()-4*size;i++)
             {
                 storage5.add(fileStorage.get(4*size + i));
             }
       }
       catch(IOException ex){
       }
       
       //setting boxLayout in adminpanel
       adminPanel.setLayout(new BoxLayout(adminPanel, BoxLayout.Y_AXIS));
       
       jp1.add(adminPanel);
       adminPanel.add(newPanel);
       adminPanel.add(panel2);
       adminPanel.add(panel3);
       
       //Creating new color
       Color slate=new Color(220,220,220);
       Color slate1=new Color(245,245,245);
        
        JLabel lblTarget = new JLabel("<html><body style=\"font: bold 19pt Georgia, 'Times New Roman', Times, serif;color: black; \">Target : </body></html>");
	newPanel.add(lblTarget);
        
        //adding a textfield to take input websites from user
	txtHttp = new JTextField();
        txtHttp.setBackground(slate1);
        txtHttp.setPreferredSize(new Dimension(350, 23));
	newPanel.add(txtHttp);
        
        //Adding a progressbar
        pbar = new JProgressBar();
        pbar.setPreferredSize(new Dimension(100, 20));
       
        
        
	//txtHttp.setColumns(36);
        btnNewButton = new JButton("<html><body style=\"font: bold 16pt Georgia, 'Times New Roman', Times, serif; \">Start </body></html>");
        btnNewButton_1 = new JButton("<html><body style=\"font: bold 16pt Georgia, 'Times New Roman', Times, serif; \">Stop </body></html>");
        
        btnNewButton.setBackground(slate);
        btnNewButton.setForeground(Color.BLACK);
        btnNewButton_1.setBackground(slate);
        btnNewButton_1.setForeground(Color.BLACK);
        
        //Adding a scroollpane
        scrollPane = new JScrollPane();
        scrollPane.setPreferredSize(new Dimension(100, 200));
	panel2.add(scrollPane, BorderLayout.NORTH);
        panel2.add(pbar , BorderLayout.SOUTH);
        
        //Adding a table to scrollpane
        String[] columnNames = {"<html><body><p style=\"font: bold 18pt Georgia, 'Times New Roman', Times;\">Result</p></body></html>"};
        Object[][] data=new Object[0][1];
        table = new JTable(data, columnNames);
        table.setEnabled(false);
        scrollPane.setViewportView(table);
        
    }
    
    
    /*function defined for getting server response
     * 
     */
    public static int getResponseCode(String url) 
	{
            try {
                    //setting url connection and getting server response
                    URL u = new URL(url); 
                    HttpURLConnection huc =  (HttpURLConnection) u.openConnection(); 
                    huc.setRequestMethod("GET"); 
                    huc.setRequestProperty("User-Agent", "Mozilla/5.0 (Macintosh; U; Intel Mac OS X 10_5_8; en-US) AppleWebKit/532.5 (KHTML, like Gecko) Chrome/4.0.249.0 Safari/532.5");
                    huc.connect();
                    return huc.getResponseCode();
		}
             catch (java.net.SocketTimeoutException e) {
			return 0;
              } 
            catch (java.io.IOException e) {
                        return 0;
		}
            catch (Exception ex) {
				return 0;
            }
	}
    
    /**
    *function to append http:// or https:// header based server response
    */ 
    public static String httpSite(String site) throws IOException
    {
        String res="http://" + site;
        if(getResponseCode("http://"+site)==200){
            res= "http://" + site;
        }
        else if(getResponseCode("https://"+site)==200){
            res= "https://" + site;
        }
        
        return res;
    }
    
    /**
     * Adding actionListner to buttons and
     * performing actions based on button query
     */
    public void admin() 
    {
        //Adding Start button action Listner
        btnNewButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e) {
                
                //adding table to scroollpane
                output.clear();
                running=true;
                scrollPane.setViewportView(table);
                String urlString =txtHttp.getText();
                
                //checking for http or https header
                if(urlString.length() !=0){
                if(!(urlString.substring(urlString.length() - 1)).equals("/"))
                {
                    urlString=urlString+ "/";
                }
                int flag=0;
                if(urlString.contains("http://" ))
                {
                    flag=1;
                }
                if(urlString.contains("https://" ))
                {
                    flag=1;
                    urlString=urlString.replace("https://", "http://");
                }
                if(flag==0){
                    urlString= "http://"+urlString;
                }
                finalUrl=urlString;
                
                 try{
                     //clearing content of output and res vector
                    AdminThread.output.clear();
                    AdminThread.res.clear();
                    
                    //Creating object of adminThread to starting different threads
                    new AdminThread(storage1, storage2,storage3, storage4, storage5);
                 }
                    catch(InterruptedException ex){}
            }
            else
            {
                //if user doesn't give input anything and press start Button
                JLabel label=new JLabel("<html><body><p style=\"margin-left:50px;font: bold 15pt Georgia, 'Times New Roman', Times, serif;color: #D10026;\">Ente the target, then press Start</p></body></html>");
                scrollPane.setViewportView(label);  
            }
            }
	});
        
	btnNewButton.setBounds(164, 312, 89, 23);
	panel3.add(btnNewButton);
        
        //Adding action listner to stop button 
	btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                            //setting running varible false to stop all the threads
                            running=false;	
			}
		});
		btnNewButton_1.setBounds(263, 312, 89, 23);
		panel3.add(btnNewButton_1);
    }      
}
