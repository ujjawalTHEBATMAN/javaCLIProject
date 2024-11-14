import com.mysql.cj.protocol.Resultset;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import javax.swing.*;
import java.awt.*;
import java.io.FileOutputStream;
import java.net.URL;
import java.sql.*;
import java.util.Scanner;

public class projectImageStoring {

    public static void main(String[] args) throws Exception{
        System.out.println("THIS IS IMAGE STORATION PROCESSURE OK JUST CLICK FOR SEND THE IMAGE ON THE DATABASE ");
        System.out.println("1 = SEND");
        System.out.println("2 = RETRIEVE ");
        if(new Scanner(System.in).nextInt()==1){
            String temp=send();
            System.out.println(temp);
        }
        else{
            System.out.println(retrieve());

        }
        System.gc();

    }

    private static boolean retrieve() throws Exception {
        String uniqueid="";
        System.out.println("ENTER YOUR UNIQUE IMAGE CODE FOR RETRIVING IMAGE FROM THE DATABASE ");
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/projectImageStoration","root","root");
        uniqueid=new Scanner(System.in).nextLine();
        ResultSet rs=con.createStatement().executeQuery("select count(*) from nameimageuid where uniqueid='"+uniqueid+"' ");
        rs.next();
        if(Integer.parseInt(rs.getString(1))>0){
            System.out.println("WHERE DID YOU WANT TO STORE YOUR IMAGE DO MANSION HERE : ");
            String Filepath=new Scanner(System.in).next()+"Output"+Math.round(Math.random()*100)+".jpg";
            File storeOnPath=new File(Filepath);

            ResultSet a=con.createStatement().executeQuery("select * from nameimageuid where uniqueid='"+uniqueid+"'");
            a.next();

            FileOutputStream temp=new FileOutputStream(storeOnPath);
            temp.write(a.getBytes(2));
            temp.flush();
            temp.close();
            System.out.println("hay man did you want to display this image ");
            if(new Scanner(System.in).nextLine().equals("yes")){
                JFrame frame=new JFrame("image display");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
               ImageIcon imageIcon =new ImageIcon(Filepath);
               JLabel label=new JLabel(imageIcon);
               frame.getContentPane().add(label,BorderLayout.CENTER);
                frame.setSize(800,400);
                frame.setVisible(true);
            }
            else{
                System.out.println("ok no problem you may go ");
            }
            System.gc();
       return true;
        }
        else {
            System.out.println("do some change and make it possible ujjawal vishwakarma");
        return false;
        }

    }

    private static String send() throws Exception{
        String name,imageDirecotry,uniqueCode;
boolean work=false;
        System.out.println("hey there how are you ");
        System.out.println("JUST ENTER YOUR NAME = ");name=new Scanner(System.in).nextLine();
        System.out.println("ENTER IMGE DIRECTORY = ");imageDirecotry=new Scanner(System.in).nextLine();
        uniqueCode=generateRandomImageCode();
        // database connectivity and store image to the database
        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/projectImageStoration","root","root");
        File a=new File(imageDirecotry);
        FileInputStream temp=new FileInputStream(a);
        PreparedStatement ps=con.prepareStatement("insert into nameimageuid values(?,?,?) ");
        ps.setString(1,name);
        ps.setBlob(2,temp);
        ps.setString(3,uniqueCode);
        int u=ps.executeUpdate();
        //here we return the unique code for image
        work=(u!=0)?true:false;
        System.gc();
        if(work){
            return uniqueCode;
        }
       else{
            return "0";
        }

    }

    public static String generateRandomImageCode(){
        // rule is = to make 20 character code where we have to put 15 char and last 5 is numeric
        return generateCharString()+generateNumbericString();
    }
    public static String generateCharString(){
        String result="e";
        for(int a=1;a<=15;a++){
            int temp=0;
            while(!((temp>=65 && temp<=90)||(temp>=97 && temp<=122) )){
                temp=(int)Math.round(Math.random()*150);

            }
            result+=(char)temp;
        }
        System.gc();
    return result;
    }
    public static String generateNumbericString(){
        String result="";
        for(int a=1;a<=5;a++){
            int temp=0;
                temp=(int)Math.round(Math.random()*9);
            result+=temp;
        }
// i did not eligible any variable for gc but i use it for showcase so that you can understand  i learn a lot about the java garbage collection and java

        System.gc();
        return result;
    }

}
