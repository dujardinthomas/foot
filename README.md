# foot

site regroupant tout les membres d'un club de foot avec authentification lié dans une base de données en local et avec servlet java dans *Tomcat*.

## Utilisation

- placer le dossier foot dans le dossier webapps de tomcat
- Exécuter le terminal de la base de données (postgresql pour ma part)
- Exécuter le script ***foot/ScriptFoot.sql*** dans la base de données pour récupérer les tables 
    - users (identifiants pour accéder au site)
    - joueurs
    - equipes
    - rencontres
- Mettre a jour le fichier ***foot/connexionBDD.txt*** en y incluant vos identifiants de connexion de la base de données
- Recompiler si nécéssaire le projet java pour obtenir les nouveaux fichiers .classes
- Exécuter Tomcat

## Fonctionnalités

- Pour un utilisateur : 
    - lister les joueurs, équipes et rencontres sous forme de tableau (page **select?table=[NomDeLaTable]&tri=[NomDeLaColonne]&order=[ASCouDESC]**)

    - rechercher un joueur, une équipe ou une rencontre (page **search** avec formulaire qui demande la table puis sa colonne et sa valeur à afficher)

- Pour un capitaine :
    - insertion d'une rencontre (page **insert**)

- Pour un administrateur :
    - insertion d'un joueur et d'un utilisateur (page **insert?table=[NomDeLaTable]** puis formulaire avec toutes les colonnes)

    - affichage des utilisateurs

    - suppression d'un joueur et d'un utilisateur (page **delete?table=[NomDeLaTable]** puis formulaire avec toutes les colonnes)


Si mot de passe oublié, demande adresse mail du compte à changer, genere un nouveau, et le met à jour dans la base de donnée puis envoie un mail avec le nouveau mot de passe à l'adresse mail renseigné.