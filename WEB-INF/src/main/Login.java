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
import javax.servlet.http.HttpSession;

@WebServlet("/Login")
public class Login extends HttpServlet {

    PrintWriter out;
    String login;
    String password;

    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        out = res.getWriter();
        out.println("<html><header><title>Login</title></header><body>");

        login = req.getParameter("login");
        password = req.getParameter("password");

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
            String requete = "select * from users where login = '" + login + "' and password = '" + password + "'";
            PreparedStatement ps = con.prepareStatement(requete);
            ResultSet rs = ps.executeQuery();
            // si user connu on creer une personne
            if (rs.next()) {
                out.println("utilisateur connu, bienvenue !, redirection...");
                Personne p = new Personne(rs.getString("login"),
                        rs.getString("password"),
                        rs.getString("nom"),
                        rs.getString("prenom"),
                        rs.getString("mail"),
                        rs.getString("role"));
                HttpSession session = req.getSession(true);
                session.setAttribute("login", p);
                res.sendRedirect("Accueil");
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