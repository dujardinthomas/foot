package main;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/Pass")
public class Pass extends HttpServlet {

	PrintWriter out;
	String mail;

	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		out = res.getWriter();
		out.println("<html><header><title>Login</title></header><body>");

		mail = req.getParameter("mail");

		Connection con = null;
		try {
			con = DS.getConnection();
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			out.println("connexion refusé");
			res.sendError(404, "connexion refusé !");
		}

		try { // VERIF SI MAIL EXISTE
			String requete = "select * from users where mail = '" + mail + "'";
			PreparedStatement ps = con.prepareStatement(requete);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				out.println("utilisateur connu, nous allons vous envoyer un mail avec un nouveau mot de passe !");

				try { // ENVOIE DU MAIL

				} catch (Exception e) {
					// TODO: handle exception
				}

			} else {
				out.println(
						"<h1>utilisateur non reconnu !</h1> <br><h2>Assurez vous d'avoir entrer correctement vos informations de connexions !</h2>");
			}
		} catch (Exception e) {
			out.println("<h1>" + e.getMessage() + "</h1>");
		}

		out.println("</body>");
		out.println("<footer> <button> <a href=index.html>Se connecter</a></button></footer");
		out.println("<html>");

	}
}
