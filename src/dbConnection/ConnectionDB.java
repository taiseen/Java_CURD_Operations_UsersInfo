// 14 - 10 - 2018
// must add "mysql-connector-java-5.1.46-bin.java" Driver file for connection...
package dbConnection;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class ConnectionDB 
{
    private static final String URL = "jdbc:mysql://localhost:";
    private static final String PORT = "3306/";
    private static final String DB_NAME = "diu_test";
    private static final String USER = "root";
    private static final String PASS = "";

    public static Connection getConnection() 
    {
        Connection con = null;
        try {
            con = DriverManager.getConnection(URL + PORT + DB_NAME, USER, PASS);
            System.out.println( con.getClass().getSimpleName() +  " : Load Successful!");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Not connect to server & message is : \n" + e.getMessage(), "ERROR!", JOptionPane.ERROR_MESSAGE);
        }
        return con;
    }
}

//private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
//Class.forName(JDBC_DRIVER);
//JOptionPane.showMessageDialog(null, "Connected with :- \n" + con.toString(), "Successful!", JOptionPane.INFORMATION_MESSAGE);

/*
CREATE DATABASE diu_test ; 

CREATE TABLE users (
	uID int not null primary key auto_increment ,
	uName varchar(15) not null , 
	uEmail varchar(15) not null , 
	uUserName varchar(10) not null , 
	uPass varchar(15) not null , 
	uAge int,
	uContact varchar(15) ,
	uGender VARCHAR(11) , 
	uHight varchar(10) , 
	uAddress varchar(25)
);

INSERT INTO users ( uName , uEmail, uUserName, uPass, uAge, uContact, uGender, uHight, uAddress) VALUES 
("taiseen khan" , "seen@gmail.com" , "seen" , "1234" , 26 , "01717416412" , "Male" , "5'5" , "Dhaka") ,
("tanzimul hasib" , "hasib@gmail.com" , "hasib" , "abcd" , 24 , "01717888888" , "Male" , "5'4" , "Rajshahi") ;


UPDATE users SET uPass = "12345" WHERE uID = 1 ;

ALTER TABLE users CHANGE uGender uGender VARCHAR(11) ;

*/