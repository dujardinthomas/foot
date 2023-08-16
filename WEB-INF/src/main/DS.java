package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;

public class DS {
	
	//REMPLI PAR LA CLASSE LOGIN
	public static String pathFile = "";
	
	public void setPathFile(String path) {
		pathFile = path;
	}

	public static void main(String[] args) {
		System.out.println("ok");
	}

	//LIT FICHIER DE CONNEXION TXT
	public static Connection getConnection() {
		
		ArrayList<String> connexion = new ArrayList<>();
		
		try (BufferedReader reader = new BufferedReader(new FileReader(pathFile))) {
            String line;
            
            while ((line = reader.readLine()) != null) {
               // System.out.println(line);
                connexion.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
		
		String url = connexion.get(1);
		String nom = connexion.get(2);
		String mdp = connexion.get(3);
		String driver = connexion.get(4);
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url,nom,mdp);
		} catch (Exception e){
			e.printStackTrace();
		}
		return con;
	}
	

	//SUR PC ACER
	//    public static Connection getConnection(){
	//        String url = "jdbc:postgresql://localhost/foot";
	//        String nom = "thoma";
	//        String mdp = "thoma";
	//        String driver = "org.postgresql.Driver";
	//        Connection con = null;
	//        try {
	//            Class.forName(driver);
	//            con = DriverManager.getConnection(url,nom,mdp);
	//        } catch (Exception e){
	//            e.printStackTrace();
	//        }
	//        return con;
	//    }

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
