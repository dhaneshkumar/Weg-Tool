package webtool;

/**
 *
 * @author dhanesh kumar
 */

import java.util.*;
import java.util.List;
import java.io.*;
import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.table.*;
import java.net.*;

/**
 * Create different threads and
 * operate functions of sqli_back class
 * 
 */
class SThread1 implements Runnable 
{
    public static Thread t;
    int count;
    static Vector<String> result=webtool.Sqli_back.result;
    static Vector<String> store=new Vector<String>();
    public String query;
    
    SThread1(String qry,int i){
          //creating a new thread
         t=new Thread(this,"thread"+i);
         t.start();
         count=i;
         query=qry;
    }
    
    public void run()
    {
        try
        {
            /**
             * running parameter : check whether stopped button is clicked or not
             * if stopped button is clicked, it will terminate the thread
             * 
             */  
            if(Sqli_back.running)
            {
                //calling siteList function to get google search result
                store = Sqli_back.siteList(count,query);
                for(int j=0;j<store.size();j++)
                {  
                    if(Sqli_back.running)
                    {
                        String urlString=store.get(j);
                        int flag=0;
                        
                        //checking the header of websites and correct if it's wrong
                        if(urlString.contains("http://" ))
                        {
                            flag=1;
                        }
                        if(urlString.contains("https://" ))
                        {
                            flag=1;
                        }
                    
                        if(flag==0){
                        try
                        {
                            urlString= adminFinder.httpSite(urlString);
                        }
                        catch(IOException exx){
                        }
               }
                
               //checking the given site is vulnerable or not by calling checkBox function
               if(!Sqli_back.checkBox(urlString))
               {
                   //calling output function to add result in the table to display
                    sqliThread.output(urlString, 1);
               }
               else{
                   //calling output function to add result in the table to display
                   sqliThread.output(urlString, 0);
               }
           }
              else{
                  break;
              }
         }
       }
        }
       catch(IOException ec){
       }               
}
}


/**
 * creating a new thread to control progress of  progressbar
 * 
 */
class SThread2 implements Runnable 
{
    public static Thread t;
    int count;
    static Vector<String> result=webtool.Sqli_back.result;
    static Vector<String> store=new Vector<String>();
    String query;
    static JProgressBar pbar=webtool.Sqli_Gui.spbar;
    
    SThread2(String qry,int i){
        //creating a new thread
         t=new Thread(this,"thread"+i);
         t.start();
         count=i;
         query=qry;
    }
    
    
    public void run()
    {
        try{
            /**
             * running parameter : check whether stopped button is clicked or not
             * if stopped button is clicked, it will terminate the thread
             * 
             */  
            if(Sqli_back.running){
                //calling siteList function to get google search result
                store = Sqli_back.siteList(count,query);
                
                //maintaing the progressbar's progress 
                pbar.setMinimum(0);
                pbar.setMaximum(store.size()-1);
                pbar.setForeground(Color.BLUE);
                
                for(int j=0;j<store.size();j++)
                {  
                    if(Sqli_back.running)
                    {
                        final int percen = j; 
                        SwingUtilities.invokeLater(new Runnable() 
                        {
                            public void run() 
                            {
                                //update the value of progress bar
                                pbar.setValue(percen);
                            }
                        });
                    
                     //checking the header of websites and correct if it's wrong
                    String urlString=store.get(j);
                    int flag=0;
                    if(urlString.contains("http://" ))
                    {
                       flag=1;
                    }
                    if(urlString.contains("https://" ))
                    {
                        flag=1;
                    }
                    
                    if(flag==0){
                    try
                    {
                        urlString= adminFinder.httpSite(urlString);
                    }
                    catch(IOException exx){
                    }
               }
                
               if(!Sqli_back.checkBox(urlString))
               {
                   //calling output function to add result in the table to display
                   sqliThread.output(urlString, 1);
               }
               else{
                   //calling output function to add result in the table to display
                   sqliThread.output(urlString, 0);
               }
           }
           else{
               break;
              }
          }
        }
       }
       catch(IOException ec){
       }               
    }
}
 
/**
 * 
 * Start all threads and display output
 * 
 */    
public class sqliThread implements Runnable{
    static int  k=0;
    static Vector<String> result=Sqli_back.result;
    static JTable table=webtool.Sqli_Gui.table;
    static JScrollPane scrollPane=webtool.Sqli_Gui.scrollPane;
    static JComboBox dorkList=Sqli_Gui.dorkList;
    public String qry;
    public static Thread t;
    public static Set<String> set = new HashSet<String>();
    public static HashMap hash1=new HashMap();
   
            
    public sqliThread(String qry1) throws IOException, InterruptedException
    {
        qry=qry1;
        t=new Thread(this, "thread");
        t.start();
    }
        
    public void run()
    {
        //Starting different threads by creating different objects
        SThread1 t1=new SThread1(qry,1);
        SThread1 t3=new SThread1(qry,11);
        SThread1 t4=new SThread1(qry,21);
        SThread2 t2=new SThread2(qry,31);
        SThread1 t5=new SThread1(qry,41);
        
        try
        {
            //joining all threads. so that main wait until all threads finish their job
            t1.t.join();
            t2.t.join();
            t3.t.join();
            t4.t.join();
            t5.t.join();
        }
        catch(InterruptedException e){
        }
    }     
        
        /************************************************************/
    
    /**
     * Display sql vulnerable websites
     * 
     * @param url
     * @param p 
     */
    public static void output(String url, int p){
        final String start=adminFinder.start;
        final String end=adminFinder.end;
        hash1.put(start+url+end, p);
        set.add(start+url+end);
        result.clear();
        result.addAll(set);
        //System.out.println("size : " + result.size());
        final Color lskyBlue= new Color(135,206,250);
        int k;
        int    i=0; 
        String[] columnNames = {"<html><body><p <span style=\"font: bold 18pt Georgia, 'Times New Roman', Times;\">Result &nbsp</span> <span style=\"font: bold 12pt Georgia, 'Times New Roman', Times;color: #D10026;\"> ( Double Click to open the link )</span></p></body></html>"};
        String s,t;
        Color slate=new Color(220,220,220);
        Object[][] data=new Object[result.size()][1];
        for(i=0;i<result.size();i++)
        {
            data[i][0]= result.get(i);
        }
        
        //creating a new table to display all sites with different colors
        table = new JTable(data, columnNames) 
        {
                public Component prepareRenderer(TableCellRenderer renderer,int Index_row, int Index_col) {
		Component comp = super.prepareRenderer(renderer, Index_row,Index_col);
                Color slate=new Color(220,220,220);
                Color slate1=new Color(69,225,245);
                
                //setting default lool and feel to change the background color of rows 
                try {
                    UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
                } 
                catch (Exception e) {
                }
                
                /**
                 * setting the color 
                 * if site is vulnerable then it will set it's background color green
                 * otherwise sky blue
                 */ 
		if (hash1.get(table.getValueAt(Index_row,0).toString())==1) {
                    comp.setBackground(Color.green);
                    comp.setForeground(Color.BLACK);    
		}
                else {
                        comp.setForeground(Color.BLACK); 
			comp.setBackground(lskyBlue);
		}
	
                return comp;    
              }
            };
                //setting background color of header column
                JTableHeader header = table.getTableHeader();
                header.setBackground(lskyBlue);
                
                //setting different properties of table
                table.setRowHeight(20);
                table.setEnabled(false);
                table.setBackground(lskyBlue);
                table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
                table.getColumnModel().getColumn(0).setPreferredWidth(500);
                
                /**
                 * setting mouse adapter to rows,
                 * so that on double click , it open the Website
                 * 
                 */
                table.addMouseListener(new MouseAdapter() 
                {
                    public void mouseClicked(MouseEvent e) 
                    {
                        if (e.getClickCount() == 2)
                        {
            			int row = table.rowAtPoint(e.getPoint());
                		String temp = (String) table.getValueAt(row, 0);
                                temp= temp.substring(start.length(),temp.length()-end.length());
                           try {
                            Desktop.getDesktop().browse(new URI(temp));
                            } catch (URISyntaxException | IOException ex) {
                        }
                    }
                }
            });
                dorkList.setSelectedIndex(0);
                //adding table to scrollpane 
                scrollPane.setViewportView(table);
    }
}
    
