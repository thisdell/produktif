package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;        

public class koneksi {
   private static Connection mysqlconfig;
    public static Connection configDB() throws SQLException {
        try{
            String url = "jdbc:mysql://localhost:3306/tehpoci_new";
            String user = "root";
            String pass = "";
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            mysqlconfig = DriverManager.getConnection(url,user,pass);
            System.out.println("Koneksie Done");
        }catch(Exception e){
            System.err.println("koneksi gagal"+ e.getMessage());
        }
    return mysqlconfig;
        }
}
