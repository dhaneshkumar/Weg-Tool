package webtool;

/**
 *
 * @author Dhanesh kumar
 */


import java.util.Vector;
import javax.swing.*;
import webtool.adminFinder;
import java.awt.*;
import javax.swing.table.TableCellRenderer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.*;
import java.io.*;
import javax.swing.table.*;



/**
 * 
 * Creating first thread for computation of server response
 */

class Thread1 implements Runnable 
{
    JProgressBar pbar;
    public static Thread t;
    String finalUrl;
    String start,end,start1;
    Vector<String> storage1=new Vector<String>(500);
    
    Thread1(Vector<String> storage){
        
        //setting difference attributes of Progress bar
        pbar=webtool.adminFinder.pbar;
        pbar.setMinimum(0);
        pbar.setMaximum(storage.size()-1);
        pbar.setForeground(Color.BLUE);
        
        //Creating a new thread
         t=new Thread(this,"1st thread");
         t.start();
         storage1=storage;
    }
    
    /**
     * Getting server Response &
     * Storing them in a vector 
     * 
     */
    public void run(){
       
         finalUrl=webtool.adminFinder.finalUrl;
         start=webtool.adminFinder.start;
         start1=webtool.adminFinder.start1;
         end=webtool.adminFinder.end;
        
         String s,t;
         int k=0;
         for(int j=0;j<storage1.size();j++)
         {
             //Updating the value of progress bar
             final int percen = j; 
             SwingUtilities.invokeLater(new Runnable()
             {
                 public void run() 
                 {
                    pbar.setValue(percen);
                 }
             });
             
         /**
          * running parameter : check whether stopped button is clicked or not
          * if stopped button is clicked, it will terminate the thread
          * 
          */               
         if(webtool.adminFinder.running)
         {
               s= storage1.get(j);
               
               //Getting server response
               k = adminFinder.getResponseCode( finalUrl + s);
               t = finalUrl + s;
               
               //checking the server response and it in to the table
               if(k==200)
               {
                     //calling functin to display output on table
                     AdminThread.feed(start + t + end,start1+k+end); 
               }
               else
               {
                    //calling functin to display output on table
                    AdminThread.feed(start + t + end,start1+k+end);
               }
          }
          else{
                break;
          }
    }
  }
}

/**************************************<THREAD 2> *********************************************/

/**
 * 
 * Creating different threads 
 * but not updating the progressbar like the previous class
 */
class Thread2 implements Runnable 
{
    public static Thread t;
    String finalUrl;
    String start,end,start1;
    Vector<String> storage1=new Vector<String>(500);
    
    Thread2(Vector<String> storage, int i){
         t=new Thread(this,""+i);
         t.start();
         storage1=storage;
    }
    
    /**
     * Getting server Response &
     * Storing them in a vector 
     * 
     */
    public void run()
    {
        
        //Intialising the variables
        finalUrl=webtool.adminFinder.finalUrl;
        start=webtool.adminFinder.start;
        start1=webtool.adminFinder.start1;
        end=webtool.adminFinder.end;
        String s,t1;
        int k=0;
        
        //loop for traversing content of admin.txt and checking server response
        for(int j=0;j<storage1.size();j++)
        {
             /**
              * running parameter : check whether stopped button is clicked or not
              * if stopped button is clicked, it will terminate the thread
              * 
              */ 
             if(webtool.adminFinder.running)
             {
                 s= storage1.get(j);
                 k = adminFinder.getResponseCode( finalUrl + s);
                 t1 = finalUrl + s;
                 
                 //checking serever  response
                 if(k==200)
                 {
                     //calling functin to display output on table
                     AdminThread.feed(start + t1 + end, start1+k+end); 
                 }
                 else
                 {
                    //calling functin to display output on table 
                     AdminThread.feed(start + t1 + end, start1+k+end);
                 }
            }
            else
            {
                 break;
            }
      }
   }
}


/*********************************************************************************/


/**
 * 
 * Class starting all different threads 
 */
class FinalThread implements Runnable
{
    public static Thread t;
    static Thread1 th1;
    static Thread2 th2;
    static Thread2 th3;
    static Thread2 th4;
    static Thread2 th5;
    
    //vectors to store all content of admin.txt 
    Vector<String> Store1=new Vector<String>(300);
    Vector<String> Store2=new Vector<String>(300);
    Vector<String> Store3=new Vector<String>(300);
    Vector<String> Store4=new Vector<String>(300);
    Vector<String> Store5=new Vector<String>(300);
    
    
    FinalThread(Vector<String> store1, Vector<String> store2,Vector<String> store3,Vector<String> store4, Vector<String> store5)
    {
       //starting new thread
       t=new Thread(this,"controller thread");
       t.start();
      Store1=store1;
      Store2=store2;
      Store3=store3;
      Store4=store4;
      Store5=store5;
      
        
    }
    
    public void run()
    {
       //creating new threads by making different object of Thread1 and Thread2 class
       th1 = new Thread1(Store1);
       th2 =new Thread2(Store2,2);
       th3 =new Thread2(Store3,3);
       th4 =new Thread2(Store4,4);
       th5 =new Thread2(Store5,5);
       
       try{
           /**
            * joining all the threads.so that 
            * main thread will until all threads finish their job
            */
           
            th1.t.join();
            th2.t.join();
            th3.t.join();
            th4.t.join();
            th5.t.join();
       }
       catch(InterruptedException ex){
       }
    }
}


/*********************************************************************************************/

/**
 * Display output on a table with scrollpane
 * 
 */
public class AdminThread {
    Vector<String> store=new Vector<String>(500);
    public static Vector<String> output=new Vector<String>();
    public static Vector<String> res=new Vector<String>() ;
    public static JTable table=webtool.adminFinder.table;
    public static JScrollPane scrollPane=webtool.adminFinder.scrollPane;
    static int threadFlag=0;
    int stopflag=webtool.adminFinder.stopFlag;
    public static  Object[][] data;
    static String start,start1;
    static String end;                   
            
    public AdminThread(Vector<String> store1, Vector<String> store2,Vector<String> store3,Vector<String> store4, Vector<String> store5)
            throws InterruptedException,IllegalMonitorStateException, IllegalThreadStateException
    {
        start=webtool.adminFinder.start;
        start1=webtool.adminFinder.start1;
        end=webtool.adminFinder.end;
       
        //creating an object of FinalThread for stating the threads
        new FinalThread(store1,store2,store3,store4,store5);
    }
    
    
    /**
     * Add given url and response to the table and display it 
     * @param url
     * @param response 
     */
    public static void feed(String url, String response)
    {
        final Color skyBlue=new Color(135,206,235);
        final Color lskyBlue= new Color(135,206,250);
        int k,i=0;  
        String[] columnNames = {"<html><body ><p <span style=\"font: bold 18pt Georgia, 'Times New Roman', Times;\">URL &nbsp</span> <span style=\"font: bold 12pt Georgia, 'Times New Roman', Times;color: #D10026;\"> ( Double Click to open the link )</span></p></body></html>","<html><body><p <span style=\"font: bold 18pt Georgia, 'Times New Roman', Times;\">Response &nbsp</span> </p></body></html>"};
        String s,t;
        Color slate=new Color(220,220,220);
        output.add(url);
        res.add(response);
        
        //creating an object array to store all urls and their response
        Object[][] data=new Object[output.size()][2];
        for(i=0;i<output.size();i++)
        {
             data[i][0]= output.get(i);
             data[i][1]=res.get(i);
        }
        
        //creating a new table with object array
        table = new JTable(data, columnNames)   
        {
             public Component prepareRenderer(TableCellRenderer renderer,int Index_row, int Index_col)
             {
		Component comp = super.prepareRenderer(renderer, Index_row,Index_col);
                Color slate=new Color(220,220,220);
                Color slate1=new Color(69,225,245);
                
                //setting window default look and feel for changing the background color of rows
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                } 
                catch (Exception e) { System.err.println("Error: " + e.getMessage()); }
                
                String s=table.getValueAt(Index_row,1).toString();
                       s= s.substring(start1.length(), s.length()-end.length());
		
                //setting color of rows based on their response
                if (s.equals("200")) {
                    comp.setBackground(Color.GREEN);
                    comp.setForeground(Color.BLACK);    
		}
                else 
                { 
                    comp.setForeground(Color.BLACK); 
		    comp.setBackground(skyBlue);
		}
                    return comp;
              }
            };
        
                table.setRowHeight(20);
                table.setEnabled(false);
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                table.getColumnModel().getColumn(0).setPreferredWidth(330);
                table.setBackground(lskyBlue);
                table.getColumnModel().getColumn(1).setPreferredWidth(150);
                table.addMouseListener(new MouseAdapter() {
                    
                //setting mouse adapter with table
                //on double click the url, it will open that url    
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
			int row = table.rowAtPoint(e.getPoint());
			String temp = (String) table.getValueAt(row, 0);
                        temp= temp.substring(start.length(),temp.length()-end.length());
                        try {
                            Desktop.getDesktop().browse(new URI(temp));
                        } 
                        catch (URISyntaxException | IOException ex) {
                    }
                    }
                }
            });
                
             webtool.adminFinder.txtHttp.setText("");
             
             //setting table to scrollpane
             scrollPane.setViewportView(table); 
    }   
}