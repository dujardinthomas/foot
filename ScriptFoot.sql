DROP TABLE rencontres;
DROP TABLE equipes;
DROP TABLE joueurs;
DROP TABLE users;

create table Joueurs( 
	num_joueur SERIAL PRIMARY KEY, 
	nom_joueur varchar(50), 
	prenom_joueur varchar(50), 
	classement char(4), 
	date_naissance date, 
	mail varchar(50), 
	telephone char(10), 
	adresse varchar(50)
);

insert into joueurs (nom_joueur, prenom_joueur, classement, date_naissance, mail, telephone, adresse) values
('Adiceam', 'Raphael', '30/3','1990-07-03', 'raph@gmail.com', '0634323456', '67 rue Jean Eude'),
('Banaziak', 'Gauthier', '30/4','2003-07-01', 'gaut@gmail.com', '0634542387', '97 rue Hello'),
('Bouet', 'Christophe', '30/3','1990-07-03', '	chr@gmail.com', '0676132412', '778 rue Claude Eude'),
('Gurtner', 'Régis', '30/4','2003-07-01', 'reg@gmail.com', '0678098765', '88 rue Hello'),
('Adenon', 'Khaled', '30/3','1990-07-03', 'kha@gmail.com', '0654321211', '4567 rue Jean Eude'),
('Avelar', 'Danilo', '30/4','2003-07-01', 'dan@gmail.com', '0654321345', '8989 rue Hello'),
('Baradji', 'Sekou', '30/3','1990-07-03', 'sek@gmail.com', '0609876543', '678 rue Jean Eude'),
('Bodmer', 'Mathieu', '30/5','1997-07-03', 'mat@gmail.com', '0612345678', '978 rue Jean Eude'),

('Cissokho', 'Issa', '30/3','1990-07-03', 'iss@gmail.com', '0634323454', '67 rue Jean Eude'),
('Dibassy', 'Bakaye', '30/4','2003-07-01', 'bak@gmail.com', '0634541387', '97 rue Hello'),
('Hajjam', 'Oualid', '30/3','1990-07-03', '	oua@gmail.com', '0676133412', '778 rue Claude Eude'),
('Gouano', 'Prince', '30/4','2003-07-01', 'pri@gmail.com', '0678098265', '88 rue Hello'),
('Ielsch', 'Julien', '30/3','1990-07-03', 'jul@gmail.com', '0654321511', '4567 rue Jean Eude'),
('Seba', 'Djessine', '30/4','2003-07-01', 'dje@gmail.com', '0654321645', '8989 rue Hello'),
('Bourgaud', 'Emmanuel', '30/3','1990-07-03', 'emm@gmail.com', '0609976543', '678 rue Jean Eude'),
('Charrier', 'Charly', '30/5','1997-07-03', 'char@gmail.com', '0612349678', '978 rue Jean Eude');

create table Equipes(
	num_equipe SERIAL PRIMARY KEY,
	nom_equipe varchar(80),
	num_joueur1 INTEGER,
	num_joueur2 INTEGER,
	num_joueur3 INTEGER,
	num_joueur4 INTEGER,
	num_joueur5 INTEGER,
	num_joueur6 INTEGER,
	num_joueur7 INTEGER,
	num_joueur8 INTEGER,
	num_joueur9 INTEGER,
	num_joueur10 INTEGER,
	CONSTRAINT fk_Equipes1 FOREIGN KEY(num_joueur1) REFERENCES Joueurs(num_joueur) ON UPDATE cascade,
	CONSTRAINT fk_Equipes2 FOREIGN KEY(num_joueur2) REFERENCES Joueurs(num_joueur) ON UPDATE cascade,
	CONSTRAINT fk_Equipes3 FOREIGN KEY(num_joueur3) REFERENCES Joueurs(num_joueur) ON UPDATE cascade,
	CONSTRAINT fk_Equipes4 FOREIGN KEY(num_joueur4) REFERENCES Joueurs(num_joueur) ON UPDATE cascade,
	CONSTRAINT fk_Equipes5 FOREIGN KEY(num_joueur5) REFERENCES Joueurs(num_joueur) ON UPDATE cascade,
	CONSTRAINT fk_Equipes6 FOREIGN KEY(num_joueur6) REFERENCES Joueurs(num_joueur) ON UPDATE cascade,
	CONSTRAINT fk_Equipes7 FOREIGN KEY(num_joueur7) REFERENCES Joueurs(num_joueur) ON UPDATE cascade,
	CONSTRAINT fk_Equipes8 FOREIGN KEY(num_joueur8) REFERENCES Joueurs(num_joueur) ON UPDATE cascade,
	CONSTRAINT fk_Equipes9 FOREIGN KEY(num_joueur9) REFERENCES Joueurs(num_joueur) ON UPDATE cascade,
	CONSTRAINT fk_Equipes10 FOREIGN KEY(num_joueur10) REFERENCES Joueurs(num_joueur) ON UPDATE cascade
);

insert into equipes (nom_equipe, num_joueur1, num_joueur2) values
('vert', 1, 2),
('bleu', 3, 4),
('rouge', 5, 6),
('noir', 7, 8),
('jaune', 9, 10),
('vert', 11, 12),
('gris', 13, 14),
('marron', 15, 16);

create table Rencontres(
	num_match SERIAL PRIMARY KEY,
	jour date,
	eq1 integer,
	eq2 integer,
	sc1 integer,
	sc2 integer,
	CONSTRAINT fk_Rencontres1 FOREIGN KEY(eq1) REFERENCES Equipes(num_equipe) ON UPDATE cascade,
	CONSTRAINT fk_Rencontres2 FOREIGN KEY(eq2) REFERENCES Equipes(num_equipe) ON UPDATE cascade
);

insert into rencontres (jour, eq1, eq2, sc1, sc2) values
('2023-01-01', 1, 2, 3, 1),
('2023-01-02', 3, 4, 3, 1),
('2023-01-03', 5, 6, 3, 1),
('2023-01-04', 7, 8, 3, 1);


create table users(
	login text PRIMARY KEY,
	password text,
	nom text,
	prenom text,
	mail text,
	role text
);

insert into users values
('thomas', 'thomas', 'DUJARDIN' , 'Thomas', 'thomasdujardin2003@gmail.com', 'admin'),
('ludo', 'ludo', 'DEMOL' , 'Ludo', 'ludo@gmail.com', 'capitaine'),
('simon', 'simon', 'BARBEAU' , 'Simon', 'simon@gmail.com', 'joueur');