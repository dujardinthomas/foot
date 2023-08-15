package main;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Accueil")
public class Accueil extends HttpServlet {

    PrintWriter out;
    Personne p;
    
    public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        // Authentifie
        HttpSession session = req.getSession(true);
        p = (Personne) session.getAttribute("login");
        if (p == null) {
            res.sendRedirect("Deconnect?Accueil");
        } else {

            out = res.getWriter();
            out.println("<html><header><title>Accueil</title></header><body>");

            out.println("<h1>Bienvenue " + p.prenom + "</h1>");

            if (p.role.equals("admin")) {
                out.println("<h2>En tant qu'" + p.role
                        + ", vous pouvez gérer les equipes, les rencontres, les joueurs et les utilisateurs de cette plateforme.</h2");

                outInsert();
                outAdmin();
            }

            if (p.role.equals("capitaine")) {
                out.println("<h2>En tant qu'" + p.role + ", vous pouvez gérer votre équipe et les rencontres.</h2");

                outInsert();
            }

            if (p.role.equals("user")) {
                out.println("<h2>En tant qu'" + p.role + ", vous pouvez consulter les equipes.</h2");
            }

            outSelect();

            out.println("</body>");
            out.println("<footer> <button> <a href=Deconnect>Déconnexion</a></button></footer");
            out.println("<html>");
        }
    }

    private void outSelect() {
        out.println("<ul>");
        out.println("<li><a href=select?table=joueurs>Liste des Joueurs</a></li>");
        out.println("<li><a href=select?table=equipes>Liste des Equipe</a></li>");
        out.println("<li><a href=select?table=rencontres>Liste des Rencontres</a></li>");
        out.println("<li><a href=search>Rechercher un joueur, une equipe ou une rencontre</a></li>");

        out.println("</ul>");
    }

    private void outInsert() {
        out.println("<ul>");
        out.println("<li><a href=insert?table=rencontres>Insertion d'une rencontre</a></li>");

        if (p.getRole().equals("admin")) {
            out.println("<li><a href=insert?table=joueurs>Insertion d'un Joueur</a></li>");
            out.println("<li><a href=insert?table=equipes>Insertion d'une équipe</a></li>");
        }

        out.println("</ul>");
    }

    private void outAdmin() {
        out.println("<ul>");
        out.println("<li><a href=select?table=users>Affichages des utilisateurs</a></li>");
        out.println("<li><a href=insert?table=users>Ajout d'un nouvel utilisateur</a></li>");
        out.println("<li><a href=delete?table=users>Suppression d'un utilisateur</a></li>");
        out.println("<li><a href=delete?table=joueurs>Suppression d'un joueur</a></li>");

        out.println("</ul>");

    }

}
