package main;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/select")
public class Select extends HttpServlet {

	PrintWriter out;
	Connection con;

	String table;
	String colTrie;
	String order;

	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		// Authentifie
		HttpSession session = req.getSession(true);
		Personne p = (Personne) session.getAttribute("login");
		if (p == null) {
			res.sendRedirect("Deconnect");
		} else {

			out = res.getWriter();
			out.println("<html><header><title>Select</title></header><center><body>");

			table = req.getParameter("table"); // ?table=''
			colTrie = req.getParameter("tri"); // &tri=

			if (colTrie != null) {
				colTrie = " order by " + req.getParameter("tri");
			} else {
				colTrie = "";
			}

			order = req.getParameter("order"); // &order=
			if (order == null) {
				order = "";
			}

			// AUTORISATION DE SELECTIONNER UNIQUEMENT LES TABLES CITES POUR EVITER
			// D'AFFICHER DES AUTRES TABLES
			if ((table.equals("joueurs")) || (table.equals("equipes")) || (table.equals("rencontres"))
					|| (p.getRole().equals("admin"))) {
				out.println("<h1>Affichage de la table " + table + "</h1>");


				Connection con = null;
				try {
					con = DS.getConnection();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					out.println("connexion refusé");
					res.sendError(404, "connexion refusé !");
				}

				try {
					String requete = "Select * from " + table + colTrie + " " + order;

					out.println(requete);

					PreparedStatement ps = con.prepareStatement(requete);
					ResultSet rs = ps.executeQuery();
					ResultSetMetaData rsmd = rs.getMetaData();

					out.println("<style>");
					out.println("td{border : 1px solid black; margin : 10px; text-align : center;}");
					out.println("</style>");

					// ECRITURE DES NOM DES COLONNES AVEC TRI ET ORDRE INVERSE SI RECLIQUE DESSUS
					int nbColonne = rsmd.getColumnCount();
					out.println("<table><tr>");
					for (int i = 1; i <= nbColonne; i++) {
						out.println("<td>");
						out.println("<a href=select?table=" + table + "&tri=" + rsmd.getColumnName(i) + "&order="
								+ (order.equals("asc") ? "desc" : "asc") + ">" + rsmd.getColumnName(i) + "</a>");
						out.println("</td>");
					}
					out.println("</tr>");

					// ECRITURE DES LIGNES EN ITERATION
					while (rs.next()) {
						out.println("<tr>");
						for (int i = 1; i <= nbColonne; i++) {
							out.println("<td>");
							out.println(rs.getObject(i));
							out.println("</td>");
						}
						out.println("</tr>");
					}

					out.println("</table>");

				} catch (SQLException e) {
					// TODO Auto-generated catch block
					out.println(e.getMessage());
				}

			} else {
				out.println("<h1>TABLE NON VALIDE</h1>");
			}

			out.println("</body></center>");
			out.println(
					"<footer> <button> <a href=Accueil>Accueil</a></button> <button> <a href=Deconnect>Déconnexion</a></button></footer");
			out.println("<html>");
		}
	}
}