package webtool;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.util.regex.*;
import javax.swing.*;
import java.awt.Color;
import javax.swing.table.TableCellRenderer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * class for sql scanner back end coding
 * it finds sql vulnerable websites
 *
 * @author dhanesh
 */

public class Sqli_back
{
    static String key;
    static String qry;
    static JButton btnNew=webtool.Sqli_Gui.btnNewButton; 
    static JComboBox dorkList=webtool.Sqli_Gui.dorkList;
    public static boolean running=true;
    public static Vector<String> result=new Vector<String>(100);
    public JScrollPane scrollPane=Sqli_Gui.scrollPane;
    public static URL url;
    sqliThread s1;
    
    public Sqli_back(String key1)
    {
        //key : google api key . required for getting google search result
        key=key1;
        btnNew.addActionListener(new ActionListener() 
        {
            public void actionPerformed(ActionEvent e) {   
            running=true;                    
            result.clear();
            String[] columnNames = {"<html><body><p style=\"font: bold 18pt Georgia, 'Times New Roman', Times;\">Result</p></body></html>"};
            Object[][] data=new Object[0][1];
            JTable table = new JTable(data, columnNames);
            table.setEnabled(false);
            scrollPane.setViewportView(table);
            
            //select query from dorkList based on user input 
            String query=dorkList.getSelectedItem().toString();
            qry="inurl:"+ query;
            sqliThread.hash1.clear();
            sqliThread.set.clear();
            try{
                s1 = new sqliThread(qry);
            }
            catch(IOException ee ){
                ee.printStackTrace();
            }
            catch(InterruptedException ef){
                ef.printStackTrace();
            }
          }
        });
    
        /**
         * adding action listener to stop button
         * on clicking stop it sets running variable false 
         * and stop all the threads
         * 
         */
        
        Sqli_Gui.btnNewButton1.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                //setting running variable to false to stop all threads
                running=false;	
	    }
	});
    }
    
    /**
     * take user query and returns google search results in a vector
     * 
     * @param count
     * @param qry
     * @return
     * @throws IOException 
     */
    public static Vector<String> siteList(int count,String qry) throws IOException
    {
            //defining vector to store google search result
            Vector<String> result=new Vector<String>(100);
        
            //actual google query using google api-key
            url = new URL("https://www.googleapis.com/customsearch/v1?key="+key+ "&cx=013036536707430787589:_pqjad5hr1a&q="+ qry + "&alt=json&start="+count);
            
            //setting the connection and getting google results
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");
            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
            
            int i=0;
            String output;
            while ((output = br.readLine()) != null) 
            {      
                //filtering the links
                if(output.contains("\"link\": \""))
                {                
                    String link=output.substring(output.indexOf("\"link\": \"")+("\"link\": \"").length(), output.indexOf("\","));
                    result.add(link);
                }     
            }
            conn.disconnect();
            return result;
    }
    
    /**
     * Checks whether given url is sql vulnearble or not
     * 
     * @param site
     * @return 
     */
    public static boolean checkBox(String site)
    {
            StringBuilder builder;
            StringBuilder builder1;
            String site2="";
            String site1="";
            
            try
            {
                //setting up the url connection and storing whole website a string 
                URL url = new URL(site);
                URLConnection urlc = url.openConnection();
                BufferedInputStream buffer = new BufferedInputStream(urlc.getInputStream());
                builder = new StringBuilder();
                int byteRead;
                
                //reading the website
                while ((byteRead = buffer.read()) != -1){
                    builder.append((char) byteRead);
                }
                buffer.close();
                site1=builder.toString();
        
            }
            catch(IOException e){
                site1="";
                e.printStackTrace();
            }
            
            
            
            try{
                
                //reading modified url (modified url : url + ')
                URL url = new URL(site+"'");
                URLConnection urlc = url.openConnection();
                BufferedInputStream buffer = new BufferedInputStream(urlc.getInputStream());
                builder1 = new StringBuilder();
                int byteRead1;
                //reading site
                while ((byteRead1 = buffer.read()) != -1)
                {
                    builder1.append((char) byteRead1);
                }
                buffer.close();
                site2=builder1.toString();
            }
            catch(IOException ee){
                site2="";
                ee.printStackTrace();
            }
            
            
            boolean rest=true;
            int l1=site1.length();
            int l2=site2.length();
            
            /**
             * comparing content of both strings
             * depending on the differences of file it decide
             * whether site is vulnerable or not
             */
            
            if(l1> 10000  && (l1-l2)>1000 )
            {
                rest=false;
            }
            else if(l1 < 10000  && (l1-l2)>400)
            {
                rest=false; 
            }
            return rest;
    }
}