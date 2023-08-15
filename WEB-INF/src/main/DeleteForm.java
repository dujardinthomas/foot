package main;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/*
 * http://localhost:8080/foot/select?table=rencontres => select * from rencontres;
 * http://localhost:8080/foot/select?table=rencontres&trie=eq1 => select * from rencontres order by eq1;
 */

@WebServlet("/delete")
public class DeleteForm extends HttpServlet {

	PrintWriter out;
	String table = null;
	String trie = null;
	String sens = null;

	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		table = "";

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

			// out.println(p);

			// TABLE AUTORISE
			if (p.getRole().equals("admin") || p.getRole().equals("capitaine")) {
				if (((table.equals("joueurs")) || (table.equals("equipes")) || (table.equals("rencontres")))
						|| p.getRole().equals("admin")) {

					res.setContentType("text/html;charset=UTF-8");
					out = res.getWriter();
					out.println("<!doctype html>");

					// String uri = req.getRequestURI();
					// String pageName = uri.substring(uri.lastIndexOf("/")+1);
					// out.println("<head><title>"+ pageName +"</title></head><body><center> ");

					out.println("<head><title>" + table + "</title></head><body><center> ");

					// pour enlever le "s" vu qu'on ajoute qu'un élémént
					out.println("<h1>Suppression d'un(e) " + table.substring(0, table.length() - 1) + " : </h1> ");
					out.println(
							"<h2>Insérer le plus possible de champs pour effectuer la bonne suppression et éviter les conflits !</h2> ");
					out.println(
							"<h3>Vous n'étes pas obliger de tout remplir, mais au plus vous en mettez au mieux c'est !</h3> ");

					out.println("<br>");
					out.println("<blink></blink>");

					Connection con = null;
					try {
						con = DS.getConnection();
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
						out.println("connexion refusé");
						res.sendError(404, "connexion refusé !");
					}

					String query = "select * from " + table;

					try {
						Statement stat = con.createStatement();
						ResultSet rs = stat.executeQuery(query);

						// GENERATION DU FORMULAIRE GENERIQUE= A CHAQUE COLONNE ON AJOUTE UN CHAMPS AU
						// FORMULAIRE
						ResultSetMetaData rsmd = rs.getMetaData();
						int numberOfColumns = rsmd.getColumnCount();
						out.println("<form action=" + "servlet-delete>");

						out.println("<div>" +
						// "<label for="+ "table" +">" + "table" + "</label>" +
								"<input type=hidden id=" + "table" + " name=" + "table value=" + table + ">" +
								"</div>");

						for (int i = 1; i <= numberOfColumns; i++) {
							String nomColonne = rsmd.getColumnName(i);
							String type = "text";
							if (rsmd.getColumnType(i) == 4) {
								type = "number";
							}
							if (rsmd.getColumnType(i) == 91) {
								type = "date";
							}
							out.println("<div>" +
									"<label for=" + nomColonne + ">" + nomColonne + "</label>" +
									"<input type=" + type + " id=" + nomColonne + " name=" + nomColonne + ">" +
									"</div>");
						}
						out.println("<input type=submit value=Supprimer></form>");

						out.println("<br><br><br> Ou sinon inserer la requete de suppression : ");

					} catch (Exception e) {
						e.printStackTrace();
						out.println("erreur de creation du formulaire");
						out.println(query);
					}

					// fermeture des espaces TOUJOURS FERMER LA BASE CAR ELLE EST RESTERA OUVERTE ET
					// AUCUN CHANGEMENT APRES
					try {
						con.close();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						out.println("IMPOSSIBLE DE FERMER LA TABLE");
					}

					out.println("</center> ");
					out.println("</body>");
					out.println("<footer>");
					out.println("Retour relatif : <a href=Accueil>Accueil</a>");
					out.println("</footer>");
					out.println("</html> ");
				} else {
					System.out.println("PAS AUTORISE A SUPPRIMER SUR D'AUTRES TABLES");
					res.sendRedirect("Accueil");
				}
			}

			else {
				System.out.println("PAS AUTORISE A SUPPRIMER SUR D'AUTRES TABLES");
				res.sendRedirect("Accueil");
			}

		}
	}

}