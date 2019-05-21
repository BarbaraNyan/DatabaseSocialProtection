package db;

import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DatabaseConnection {

    public static final String URL = "jdbc:mysql://46.0.192.202:3306/social_security?autoReconnect=true&useSSL=FALSE&useLegacyDatetimeCode=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    public static final String USERNAME = "eugenia";
    public static final String PASSWORD = "ilovetabletennis";
    public Connection myConnection;
    public Statement statement;
    //комментарий

    public DatabaseConnection() throws SQLException {
        myConnection = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        statement = myConnection.createStatement();

    }

    public Connection getMyConnection(){
        return myConnection;
    }


    public void close(ResultSet rs){
        if(rs !=null){
            try{
                rs.close();
            }
            catch(Exception e){}
        }
    }

    public void close(java.sql.Statement stmt){
        if(stmt !=null){
            try{
                stmt.close();
            }
            catch(Exception e){}
        }
    }

    public void destroy(){
        if(myConnection !=null){
            try{
                myConnection.close();
            }
            catch(Exception e){}
        }
    }

    public static void main(String[] args) {
        JFrame jFrame = new SocialProtectionForm();
        jFrame.setDefaultCloseOperation(jFrame.EXIT_ON_CLOSE);
        jFrame.setTitle("База данных");

        JMenuBar menuBar = new JMenuBar();

        JMenu windowMenu = new JMenu("Окно");
        JMenu helpMenu = new JMenu("Помощь");

//        JMenu newMenu = new JMenu("Open");
//        fileMenu.add(newMenu);
//
//        JMenuItem newTxtFile = new JMenuItem("TxtFile");
//        newMenu.add(newTxtFile);
        menuBar.add(windowMenu);
        menuBar.add(helpMenu);
        jFrame.setJMenuBar(menuBar);

        jFrame.setPreferredSize(new Dimension(1000,500));
        jFrame.pack();
        jFrame.setVisible(true);
    }
}
