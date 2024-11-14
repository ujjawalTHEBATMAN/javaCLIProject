import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.Scanner;

public class projectMoneyTransferModel {

    static int currentID=randomNumberGeneratorWithMinAndMax(1,6);

    public static void main(String... a)throws Exception{



        System.out.println("HERE WE ARE TRY TO PLAY SOME GAMES THAT WILL HELP YOU OUT TO THINK LIKE AND MASTER AND PLAY WITH YOUR FULL POWER OK UJJAWAL");

        System.out.println( "did you want to use legacy such as this ");
        Connection con= DriverManager.getConnection("jdbc:mysql://localhost:3306/projectmoneytransfermodel","root","root");
        ResultSet rs=con.createStatement().executeQuery("select * from legacybank");

        while(rs.next()){
            System.out.println(rs.getString(1)+" "+rs.getString(2));
        }

        System.out.println(" or you want to create new bank so that your can perform and insert data as you want all DESISIION IS YOUR'S SIR WHAT DO YOU WANT JUST REPLIYE ME ? YES(use legacy class ):NO(no use of lagacy class)");
        if(new Scanner(System.in).next().equalsIgnoreCase("yes")){
            boolean check=true;
            // work for main program to destribut the money between all of them ok
            while(check){
                ResultSet resultSetforgetsaving=con.createStatement().executeQuery("select saving from legacybank where id="+currentID+"");
                resultSetforgetsaving.next();
                int mainparticipatationmoney=(int)(Math.random()*resultSetforgetsaving.getInt(1));
                con.setAutoCommit(false);

                int creditorID=currentIDIncrementationWork(currentID);
                try{
                    //main transaction process


                    con.createStatement().executeUpdate("update legacybank set saving=saving-"+mainparticipatationmoney+" where id="+currentID+"");
                    con.createStatement().executeUpdate("update legacybank set saving=saving+"+mainparticipatationmoney+" where id="+creditorID+"");
                    con.createStatement().executeUpdate("insert into transaction(dbtid,amt,crdt) values("+currentID+","+mainparticipatationmoney+","+creditorID+" ) ");
                    con.commit();
                }
                catch(Exception e){
                    con.rollback();
                }
                ResultSet rs1=con.createStatement().executeQuery("select saving from legacybank where id="+currentID+"");
                rs1.next();
                ;
                if(rs1.getInt(1)<1000){
                    check=false;
                }

                currentID=currentIDIncrementationWork(currentID);
            }



        }
        else{
            // without use legacy table
            //crete new bank table then insert all data and then use to perform the game program

        }

        System.out.print("THE WINNER OF THE GAME = ");
        ResultSet rs12=con.createStatement().executeQuery("select * from legacybank where saving=(select max(saving) from legacybank)");
        if(rs12.next()){
            System.out.println(rs12.getString(1)+" "+rs12.getString(2)+" "+rs12.getString(3));
        }

    }

    public static void performToDebitRandomFromTheOneAndCreditToAnotherSavingAccount(){}
    public static int randomNumberGeneratorWithMinAndMax(int minnumber,int maxnumber)
    {
        while(true) {
            int result = (int) (Math.random() * 7);
            if (result >= minnumber && result <= maxnumber) {
                return result;
            }
        }

    }
    public static int currentIDIncrementationWork(int ci){
        if(ci==6){
            ci=1;
        }
        else{
            ci++;
        }
        return ci;
    }


}
