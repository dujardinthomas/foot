package main;
public class Personne {

    String login;
    String password;
    String nom;
    String prenom;
    String mail;
    String role;


    public Personne(String login, String password, String nom, String prenom, String mail, String role) {
        this.login = login;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.mail = mail;
        this.role = role;
    }
    
    public static void main(String[] args) {
		System.out.println("cghch");
	}


    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public String getNom() {
        return nom;
    }

    public String getPrenom() {
        return prenom;
    }


    public String getMail() {
        return mail;
    }


    public String getRole() {
        return role;
    }
}
