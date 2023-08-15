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

@WebServlet("/search")
public class Recherche extends HttpServlet {

    PrintWriter out;
    Connection con;

    String colonne;
    String nom;

    String table;
    String colTrie;
    String order;
    String tableForm;

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        // Authentifie
        HttpSession session = req.getSession(true);
        Personne p = (Personne) session.getAttribute("login");
        if (p == null) {
            res.sendRedirect("Deconnect?origine=search");
        } else {

            out = res.getWriter();
            out.println("<html><header><title>Rechercher</title></header><center><body>");

            tableForm = req.getParameter("table");

            if (tableForm == null) {
                out.println("<form action=search method=post>");

                out.println("<select name=table id=table>" +
                        "<option value=>-Choisir une table-</option>" +
                        "<option value=joueurs>joueurs</option>" +
                        "<option value=equipes>equipes</option>" +
                        "<option value=rencontres>rencontres</option>" +
                        "</select>");

                out.println("<input type=submit value=valider la table>");

                out.println("</form>");
            }

            if (tableForm != null) {

                out.println("<form action=search method=post>");

                // RECUPERATION DES NOMS DE COLONNES POUR LES METTRES DANS LA LISTE DU
                // FORMULAIRE
                /*
                 * out.println("<select name=colonne id=colonne>" +
                 * "<option value=>-Choisir une colonne-</option>" +
                 * "<option value=num_joueur>num_joueur</option>" +
                 * "<option value=nom_joueur>nom_joueur</option>" +
                 * "<option value=prenom_joueur>prenom_joueur</option>" +
                 * "</select>");
                 */
                
                
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
                   
                    String requeteColonne = "select * from " + tableForm;
                    out.println("<h2>Choisissez maintenant une colonne dans la table " + tableForm
                            + " puis entrez la valeur recherché :</h2>");
                    PreparedStatement ps0 = con.prepareStatement(requeteColonne);
                    ResultSet rs0 = ps0.executeQuery();
                    ResultSetMetaData rsmd0 = rs0.getMetaData();
                    int nbColonnesSelection = rsmd0.getColumnCount();

                    out.println("<select name=colonne id=colonne>" +
                            "<option value=>-Choisir une colonne-</option>");

                    for (int i = 1; i <= nbColonnesSelection; i++) {
                        out.println(
                                "<option value=" + rsmd0.getColumnName(i) + ">" + rsmd0.getColumnName(i) + "</option>");
                    }
                    out.println("</select>");

                } catch (Exception e) {
                    // TODO: handle exception
                }

                // out.println("<label for=table> Table : </label> <input type=text name=table
                // id=table>");
                out.println("<label for=id> Nom : </label>  <input type=text name=nom id=nom>");

                out.println("<input type=hidden id=" + "table" + " name=" + "table value=" + tableForm + ">");

                // "<input type=hidden id=" + "table" + " name=" + "table value=" + table + ">"

                out.println("<input type=submit value=chercher>");

                out.println("</form>");

            }

            nom = req.getParameter("nom");
            colonne = req.getParameter("colonne");

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
            if ((table.equals("joueurs")) || (table.equals("equipes")) || (table.equals("rencontres"))) {
                out.println("<h1>Affichage de la table " + table + "</h1>");
                try {

                    String requete = "Select * from " + table + " where UPPER(" + colonne + ") LIKE UPPER('" + nom
                            + "%')" + colTrie + " " + order;

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
                        out.println("<a href=search?table=" + table + "&tri=" + rsmd.getColumnName(i) + "&order="
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

                    con.close();

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