package main;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;
import java.util.Random;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;

@WebServlet("/Pass")
public class Pass extends HttpServlet{

	PrintWriter out;
	String mail;

	public static void main(String[] args) {
		System.out.println("okffffdd");
	}

	public void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		out = res.getWriter();
		out.println("<html><header><title>Login</title></header><body>");

		mail = req.getParameter("mail");

		Connection con = null;
		String filePath = getServletContext().getRealPath("connexionBDD.txt");
        DS.pathFile = filePath;
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

				String mail = rs.getString("mail");
				out.println("utilisateur connu, nous allons vous envoyer un mail à " + mail + " avec votre nouveau mot de passe !");

				try { // ENVOIE DU MAIL

					System.out.println("envoie du mail à : " + mail );

					String newPasword = generateRandomFrenchWord(4);
					String update = "UPDATE users SET password = '" + newPasword + "' WHERE mail = '" + mail + "'";
					PreparedStatement requeteUpdate = con.prepareStatement(update);
					try {
						requeteUpdate.executeUpdate();

						boolean resMail = sendEmail(mail, "REINITIALISATION DU MOT DE PASSE FOOT", "Bonjour, Vous avez demandé un nouveau mot de passe : \n" + newPasword );
						System.out.println("mail envoyé ? : " + resMail);

					} catch (Exception e) {
						System.out.println(e);
						System.out.println("impossible de mettre à jour le mot de passe");
					}


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

	protected boolean sendEmail(String destinataire, String subject, String messageText) throws MessagingException {

		String toEmail = destinataire; // Adresse e-mail du destinataire
		String fromEmail = "b9291ed4fcf6df"; // Votre adresse
		final String username = "b9291ed4fcf6df";
		final String password = "57da31d7d984e8";

		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "sandbox.smtp.mailtrap.io"); // Serveur SMTP
		props.put("mail.smtp.port", "2525"); // Port SMTP

		//DEBUG
		props.put("mail.debug", "true");

		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(fromEmail));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
			message.setSubject(subject);
			message.setText(messageText);

			Transport.send(message);

			System.out.println("Message envoyé avec succès!");
			return true;

		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}

	}

	public static String generateRandomWord(int length) {
		String alphabet = "abcdefghijklmnopqrstuvwxyz";
		StringBuilder word = new StringBuilder();

		Random random = new Random();
		for (int i = 0; i < length; i++) {
			int index = random.nextInt(alphabet.length());
			char randomChar = alphabet.charAt(index);
			word.append(randomChar);
		}

		return word.toString();
	}


	public static String generateRandomFrenchWord(int length) {
		String[] syllables = {
				"a", "e", "i", "o", "u", "y", "an", "en", "on", "eu", "ai", "oi",
				"ou", "in", "un", "ch", "ph", "gn", "qu", "th", "tr", "vr", "bl", "pl"
				// Ajoutez plus de syllabes ici
		};

		StringBuilder word = new StringBuilder();

		Random random = new Random();
		for (int i = 0; i < length; i++) {
			int index = random.nextInt(syllables.length);
			String randomSyllable = syllables[index];
			word.append(randomSyllable);
		}

		return word.toString();
	}

}
