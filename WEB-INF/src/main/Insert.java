package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/servlet-insert") // nom du fichier en url

public class Insert extends HttpServlet {

	PrintWriter out;
	String table;
	int numberOfColumns;
	ResultSetMetaData rsmd;

	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html;charset=UTF-8");
		out = res.getWriter();
		out.println("<head><title>Insertion </title>");
		out.println("<META http-equiv=refresh content='10 ; URL=Accueil'>");
		// </head><body><center>");
		out.println("<h1>Insertion d'une nouvelle donnée</h1>");

		// authentifie ?
		HttpSession session = req.getSession(true);
		Personne p = (Personne) session.getAttribute("login");
		if (p == null) {
			res.sendRedirect("deconnect");
		} else {

			table = req.getParameter("table");
			// table par défaut
			if (table == null) {
				table = "Joueurs";
			}

			if (p.getRole().equals("admin") || p.getRole().equals("capitaine")) {
				if (((table.equals("joueurs")) || (table.equals("equipes")) || (table.equals("rencontres")))
						|| p.getRole().equals("admin")) {

					Connection con = null;
					try {
						con = DS.getConnection();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						out.println("connexion refusé");
						res.sendError(404, "connexion refusé !");
					}
					
					try {// OBTENTION DES COLONNES

						Statement stat = con.createStatement();
						ResultSet rs = stat.executeQuery("select * from " + table);
						rsmd = rs.getMetaData();
						numberOfColumns = rsmd.getColumnCount();
					} catch (Exception e) {
						e.printStackTrace();
						out.println("erreur d'obtention des colonnes ");
						out.println(e.getMessage());
					}

					try { // INSERTION
						String insert = "INSERT INTO " + table + " VALUES (";
						for (int i = 1; i <= numberOfColumns; i++) {
							insert = insert + " ? ";
							if (i != numberOfColumns) {
								insert = insert + ",";
							}
							if (i == numberOfColumns) {
								insert = insert + ")";
							}
						}
						out.println("requete : " + insert);
						PreparedStatement requete = con.prepareStatement(insert);

						out.println("<br>");
						out.println("<br>");

						for (int i = 1; i <= numberOfColumns; i++) {
							String colonneIteration = rsmd.getColumnName(i);
							int typeColonne = rsmd.getColumnType(i); // retourne un nombre bizzare signifiant le type de valeur (voir
							// internet)

							if (typeColonne == 4) { // colonne de type int
								requete.setInt(i, Integer.parseInt(req.getParameter("" + colonneIteration)));
								System.out.println("insertion int ok");
							}

							else if (typeColonne == 91) { // colonne de type date

								String dateString = req.getParameter("" + colonneIteration);
								Date date = null;
								date = date.valueOf(dateString);

								requete.setDate(i, date);
								System.out.println("insertion date ok");
							}

							// if(typeColonne == 12){ //colonne de type char, j'ai mis en commentaire car
							// tout ce qui est pas int devient string
							else {
								requete.setString(i, req.getParameter("" + colonneIteration));
								System.out.println("insertion string ok");
							}
						}

						out.println("<br>");
						out.println(requete.toString());

						requete.executeUpdate();
						out.println("<h2>INSERTION REUSSIE</h2>");

					} catch (Exception e) {
						e.printStackTrace();
						out.println("erreur d'insertion ");
						out.println(e.getMessage());
					}

					out.println("<h3>Redirection sur la page d'Accueil automatiquement dans 10 secondes...");
					out.println("</center> ");
					out.println("</body>");
					out.println("<footer>");
					out.println("Retour relatif : <a href=Accueil>Accueil</a>");
					out.println("</footer>");
					out.println("</body> </head>");

				}
			} else {
				out.println(p.getPrenom() + ", tu ne peux pas ajouter dans cette table !");
			}
		}
	}
}