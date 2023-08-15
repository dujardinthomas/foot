package main;

import java.sql.Connection;
import java.sql.DriverManager;

public class DS {
    
    //SUR PC ACER
    public static Connection getConnection(){
        String url = "jdbc:postgresql://localhost/foot";
        String nom = "thoma";
        String mdp = "thoma";
        String driver = "org.postgresql.Driver";
        Connection con = null;
        try {
            Class.forName(driver);
            con = DriverManager.getConnection(url,nom,mdp);
        } catch (Exception e){
            e.printStackTrace();
        }
        return con;
    }

    // SUR IUT
    // public static Connection getConnection(){
    //     String url = "jdbc:postgresql://psqlserv/but2";
    //     String nom = "thomasdujardin2etu";
    //     String mdp = "moi";
    //     String driver = "org.postgresql.Driver";
    //     Connection con = null;
    //     try {
    //         Class.forName(driver);
    //         con = DriverManager.getConnection(url,nom,mdp);
    //     } catch (Exception e){
    //         e.printStackTrace();
    //     }
    //     return con;
    // }
}
