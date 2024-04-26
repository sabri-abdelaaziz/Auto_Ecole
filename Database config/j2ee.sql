/* j'ai executer ce fichier avec la base de données oracle */
/* creation de la table instructeur*/
CREATE TABLE Instructeur (
    id NUMBER PRIMARY KEY,
    nom VARCHAR2(100),
    prenom VARCHAR2(100),
    adresse VARCHAR2(255),
    telephone VARCHAR2(20)
);
/* creation de la table vehicule*/
CREATE TABLE Vehicule (
    id NUMBER PRIMARY KEY,
    marque VARCHAR2(100),
    modele VARCHAR2(100),
    annee_fabrication NUMBER
);
/* creation de table cours*/

CREATE TABLE Cours (
    id NUMBER PRIMARY KEY,
    date_debut DATE,
    date_fin DATE,
    heure_debut VARCHAR2(10),
    heure_fin VARCHAR2(10),
    vehicule_id NUMBER REFERENCES Vehicule(id)
);
/* creation de la table eleve */
CREATE TABLE Eleve (
    id NUMBER PRIMARY KEY,
    nom VARCHAR2(100),
    prenom VARCHAR2(100),
    adresse VARCHAR2(255),
    telephone VARCHAR2(20)
);
/* creation de la table reservation */
CREATE TABLE Reservation (
    id NUMBER PRIMARY KEY,
    eleve_id NUMBER REFERENCES Eleve(id),
    cours_id NUMBER REFERENCES Cours(id),
    date_reservation DATE,
    heure_reservation VARCHAR2(10)
);

-- creation de la table examen
CREATE TABLE Examen (
    id NUMBER PRIMARY KEY,
    date_examen DATE,
    heure_examen VARCHAR2(10),
    vehicule_id NUMBER,
    instructeur_id NUMBER,
    FOREIGN KEY (vehicule_id) REFERENCES Vehicule(id),
    FOREIGN KEY (instructeur_id) REFERENCES Instructeur(id)
);

-- creation de la table resultats
CREATE TABLE Resultat (
    id NUMBER PRIMARY KEY,
    eleve_id NUMBER REFERENCES Eleve(id),
    examen_id NUMBER REFERENCES Examen(id),
    note NUMBER,
    resultat VARCHAR2(50)
);
/* la table pour les types des examens*/
CREATE TABLE TypeExamen (
    id NUMBER PRIMARY KEY,
    nom VARCHAR2(255),
    description VARCHAR2(1000)
);


-- Ajout d'enregistrements dans la table Instructeur
INSERT INTO Instructeur (id, nom, prenom, adresse, telephone)
VALUES (1, 'Doe', 'John', '123 Main St, City', '0123456789');

INSERT INTO Instructeur (id, nom, prenom, adresse, telephone)
VALUES (2, 'Smith', 'Jane', '456 Elm St, City', '9876543210');

INSERT INTO Instructeur (id, nom, prenom, adresse, telephone)
VALUES (3, 'Williams', 'Robert', '789 Oak St, City', '1234567890');

INSERT INTO Instructeur (id, nom, prenom, adresse, telephone)
VALUES (4, 'Johnson', 'Emily', '101 Pine St, City', '0987654321');

INSERT INTO Instructeur (id, nom, prenom, adresse, telephone)
VALUES (5, 'Brown', 'Michael', '202 Cedar St, City', '1122334455');

-- Ajout d'enregistrements dans la table Vehicule
INSERT INTO Vehicule (id, marque, modele, annee_fabrication)
VALUES (1, 'Toyota', 'Corolla', 2020);

INSERT INTO Vehicule (id, marque, modele, annee_fabrication)
VALUES (2, 'Honda', 'Civic', 2019);

INSERT INTO Vehicule (id, marque, modele, annee_fabrication)
VALUES (3, 'Ford', 'Focus', 2021);

INSERT INTO Vehicule (id, marque, modele, annee_fabrication)
VALUES (4, 'Chevrolet', 'Malibu', 2018);

INSERT INTO Vehicule (id, marque, modele, annee_fabrication)
VALUES (5, 'Nissan', 'Sentra', 2022);

-- Ajout d'enregistrements dans la table Cours
INSERT INTO Cours (id, date_debut, date_fin, heure_debut, heure_fin, vehicule_id)
VALUES (1, '2024-05-01', '2024-05-10', '09:00', '11:00', 1);

INSERT INTO Cours (id, date_debut, date_fin, heure_debut, heure_fin, vehicule_id)
VALUES (2, '2024-05-15', '2024-05-24', '14:00', '16:00', 2);

INSERT INTO Cours (id, date_debut, date_fin, heure_debut, heure_fin, vehicule_id)
VALUES (3, '2024-06-01', '2024-06-10', '09:00', '11:00', 3);

INSERT INTO Cours (id, date_debut, date_fin, heure_debut, heure_fin, vehicule_id)
VALUES (4, '2024-06-15', '2024-06-24', '14:00', '16:00', 4);

INSERT INTO Cours (id, date_debut, date_fin, heure_debut, heure_fin, vehicule_id)
VALUES (5, '2024-07-01', '2024-07-10', '09:00', '11:00', 5);

-- Ajout d'enregistrements dans la table Eleve
INSERT INTO Eleve (id, nom, prenom, adresse, telephone)
VALUES (1, 'Williams', 'Sarah', '123 Maple St, City', '1122334455');

INSERT INTO Eleve (id, nom, prenom, adresse, telephone)
VALUES (2, 'Jones', 'David', '456 Oak St, City', '9988776655');

INSERT INTO Eleve (id, nom, prenom, adresse, telephone)
VALUES (3, 'Davis', 'Laura', '789 Pine St, City', '5544332211');

INSERT INTO Eleve (id, nom, prenom, adresse, telephone)
VALUES (4, 'Wilson', 'Brian', '101 Cedar St, City', '3322114455');

INSERT INTO Eleve (id, nom, prenom, adresse, telephone)
VALUES (5, 'Taylor', 'Karen', '202 Elm St, City', '4455667788');

-- Ajout d'enregistrements dans la table Reservation
INSERT INTO Reservation (id, eleve_id, cours_id, date_reservation, heure_reservation)
VALUES (1, 1, 1, '2024-04-25', '10:00');

INSERT INTO Reservation (id, eleve_id, cours_id, date_reservation, heure_reservation)
VALUES (2, 2, 2, '2024-04-26', '15:00');

INSERT INTO Reservation (id, eleve_id, cours_id, date_reservation, heure_reservation)
VALUES (3, 3, 3, '2024-04-27', '10:00');

INSERT INTO Reservation (id, eleve_id, cours_id, date_reservation, heure_reservation)
VALUES (4, 4, 4, '2024-04-28', '15:00');

INSERT INTO Reservation (id, eleve_id, cours_id, date_reservation, heure_reservation)
VALUES (5, 5, 5, '2024-04-29', '10:00');

-- Ajout d'enregistrements dans la table Examen
INSERT INTO Examen (id, date_examen, heure_examen, vehicule_id, instructeur_id)
VALUES (1, TO_DATE('2024-05-20', 'YYYY-MM-DD'), '10:00', 1, 1);

INSERT INTO Examen (id, date_examen, heure_examen, vehicule_id, instructeur_id)
VALUES (2, TO_DATE('2024-06-10', 'YYYY-MM-DD'), '14:00', 2, 2);

INSERT INTO Examen (id, date_examen, heure_examen, vehicule_id, instructeur_id)
VALUES (3, TO_DATE('2024-07-05', 'YYYY-MM-DD'), '10:00', 3, 3);

INSERT INTO Examen (id, date_examen, heure_examen, vehicule_id, instructeur_id)
VALUES (4, TO_DATE('2024-07-20', 'YYYY-MM-DD'), '14:00', 4, 4);

INSERT INTO Examen (id, date_examen, heure_examen, vehicule_id, instructeur_id)
VALUES (5, TO_DATE('2024-08-01', 'YYYY-MM-DD'), '10:00', 1, 5);


-- Ajout d'enregistrements dans la table TypeExamen
INSERT INTO TypeExamen (id, nom, description)
VALUES (1, 'Examen de Conduite', 'Examen pratique de conduite.');

INSERT INTO TypeExamen (id, nom, description)
VALUES (2, 'Examen de Code', 'Examen théorique de code de la route.');

INSERT INTO TypeExamen (id, nom, description)
VALUES (3, 'Examen de Manœuvres', 'Examen de manœuvres spécifiques.');

INSERT INTO TypeExamen (id, nom, description)
VALUES (4, 'Examen de Secourisme', 'Examen de premiers secours.');

INSERT INTO TypeExamen (id, nom, description)
VALUES (5, 'Examen de Connaissance des Véhicules', 'Examen sur la connaissance des véhicules et leur entretien.');

-- Ajout d'enregistrements dans la table Resultat
INSERT INTO Resultat (id, eleve_id, examen_id, note, resultat)
VALUES (1, 1, 1, 85, 'Réussi');

INSERT INTO Resultat (id, eleve_id, examen_id, note, resultat)
VALUES (2, 2, 2, 90, 'Réussi');

INSERT INTO Resultat (id, eleve_id, examen_id, note, resultat)
VALUES (3, 3, 3, 80, 'Réussi');

INSERT INTO Resultat (id, eleve_id, examen_id, note, resultat)
VALUES (4, 4, 4, 78, 'Échoué');

INSERT INTO Resultat (id, eleve_id, examen_id, note, resultat)
VALUES (5, 5, 5, 95, 'Réussi');




/*  dans cette section en va remplir notre basse de données puis afficant le pour test notre base de donner */

-- Ajout d'enregistrements dans la table Instructeur
INSERT INTO Instructeur (id, nom, prenom, adresse, telephone)
VALUES (1, 'Doe', 'John', '123 Main St, City', '0123456789');

INSERT INTO Instructeur (id, nom, prenom, adresse, telephone)
VALUES (2, 'Smith', 'Jane', '456 Elm St, City', '9876543210');

INSERT INTO Instructeur (id, nom, prenom, adresse, telephone)
VALUES (3, 'Williams', 'Robert', '789 Oak St, City', '1234567890');

INSERT INTO Instructeur (id, nom, prenom, adresse, telephone)
VALUES (4, 'Johnson', 'Emily', '101 Pine St, City', '0987654321');

INSERT INTO Instructeur (id, nom, prenom, adresse, telephone)
VALUES (5, 'Brown', 'Michael', '202 Cedar St, City', '1122334455');

-- Ajout d'enregistrements dans la table Vehicule
INSERT INTO Vehicule (id, marque, modele, annee_fabrication)
VALUES (1, 'Toyota', 'Corolla', 2020);

INSERT INTO Vehicule (id, marque, modele, annee_fabrication)
VALUES (2, 'Honda', 'Civic', 2019);

INSERT INTO Vehicule (id, marque, modele, annee_fabrication)
VALUES (3, 'Ford', 'Focus', 2021);

INSERT INTO Vehicule (id, marque, modele, annee_fabrication)
VALUES (4, 'Chevrolet', 'Malibu', 2018);

INSERT INTO Vehicule (id, marque, modele, annee_fabrication)
VALUES (5, 'Nissan', 'Sentra', 2022);

-- Ajout d'enregistrements dans la table Cours
INSERT INTO Cours (id, date_debut, date_fin, heure_debut, heure_fin, vehicule_id)
VALUES (1, TO_DATE('2024-05-01', 'YYYY-MM-DD'), TO_DATE('2024-05-10', 'YYYY-MM-DD'), '09:00', '11:00', 1);

INSERT INTO Cours (id, date_debut, date_fin, heure_debut, heure_fin, vehicule_id)
VALUES (2, TO_DATE('2024-05-15', 'YYYY-MM-DD'), TO_DATE('2024-05-24', 'YYYY-MM-DD'), '14:00', '16:00', 2);

INSERT INTO Cours (id, date_debut, date_fin, heure_debut, heure_fin, vehicule_id)
VALUES (3, TO_DATE('2024-06-01', 'YYYY-MM-DD'), TO_DATE('2024-06-10', 'YYYY-MM-DD'), '09:00', '11:00', 3);

INSERT INTO Cours (id, date_debut, date_fin, heure_debut, heure_fin, vehicule_id)
VALUES (4, TO_DATE('2024-06-15', 'YYYY-MM-DD'), TO_DATE('2024-06-24', 'YYYY-MM-DD'), '14:00', '16:00', 4);

INSERT INTO Cours (id, date_debut, date_fin, heure_debut, heure_fin, vehicule_id)
VALUES (5, TO_DATE('2024-07-01', 'YYYY-MM-DD'), TO_DATE('2024-07-10', 'YYYY-MM-DD'), '09:00', '11:00', 5);

-- Ajout d'enregistrements dans la table Eleve
INSERT INTO Eleve (id, nom, prenom, adresse, telephone)
VALUES (1, 'Williams', 'Sarah', '123 Maple St, City', '1122334455');

INSERT INTO Eleve (id, nom, prenom, adresse, telephone)
VALUES (2, 'Jones', 'David', '456 Oak St, City', '9988776655');

INSERT INTO Eleve (id, nom, prenom, adresse, telephone)
VALUES (3, 'Davis', 'Laura', '789 Pine St, City', '5544332211');

INSERT INTO Eleve (id, nom, prenom, adresse, telephone)
VALUES (4, 'Wilson', 'Brian', '101 Cedar St, City', '3322114455');

INSERT INTO Eleve (id, nom, prenom, adresse, telephone)
VALUES (5, 'Taylor', 'Karen', '202 Elm St, City', '4455667788');

-- Ajout d'enregistrements dans la table Reservation
INSERT INTO Reservation (id, eleve_id, cours_id, date_reservation, heure_reservation)
VALUES (1, 1, 1, '2024-04-25', '10:00');

INSERT INTO Reservation (id, eleve_id, cours_id, date_reservation, heure_reservation)
VALUES (2, 2, 2, '2024-04-26', '15:00');

INSERT INTO Reservation (id, eleve_id, cours_id, date_reservation, heure_reservation)
VALUES (3, 3, 3, '2024-04-27', '10:00');

INSERT INTO Reservation (id, eleve_id, cours_id, date_reservation, heure_reservation)
VALUES (4, 4, 4, '2024-04-28', '15:00');

INSERT INTO Reservation (id, eleve_id, cours_id, date_reservation, heure_reservation)
VALUES (5, 5, 5, '2024-04-29', '10:00');

-- Ajout d'enregistrements dans la table Examen
INSERT INTO Examen (id, date_examen, heure_examen, vehicule_id, instructeur_id)
VALUES (1, '2024-05-20', '10:00', 1, 1);

INSERT INTO Examen (id, date_examen, heure_examen, vehicule_id, instructeur_id)
VALUES (2, '2024-06-10', '14:00', 2, 2);

INSERT INTO Examen (id, date_examen, heure_examen, vehicule_id, instructeur_id)
VALUES (3, '2024-07-05', '10:00', 3, 3);

INSERT INTO Examen (id, date_examen, heure_examen, vehicule_id, instructeur_id)
VALUES (4, '2024-07-20', '14:00', 4, 4);

INSERT INTO Examen (id, date_examen, heure_examen, vehicule_id, instructeur_id)
VALUES (5, '2024-08-01', '10:00', 5, 5);

-- Ajout d'enregistrements dans la table TypeExamen
INSERT INTO TypeExamen (id, nom, description)
VALUES (1, 'Examen de Conduite', 'Examen pratique de conduite.');

INSERT INTO TypeExamen (id, nom, description)
VALUES (2, 'Examen de Code', 'Examen théorique de code de la route.');

INSERT INTO TypeExamen (id, nom, description)
VALUES (3, 'Examen de Manœuvres', 'Examen de manœuvres spécifiques.');

INSERT INTO TypeExamen (id, nom, description)
VALUES (4, 'Examen de Secourisme', 'Examen de premiers secours.');

INSERT INTO TypeExamen (id, nom, description)
VALUES (5, 'Examen de Connaissance des Véhicules', 'Examen sur la connaissance des véhicules et leur entretien.');

-- Ajout d'enregistrements dans la table Resultat
INSERT INTO Resultat (id, eleve_id, examen_id, note, resultat)
VALUES (1, 1, 1, 85, 'Réussi');

INSERT INTO Resultat (id, eleve_id, examen_id, note, resultat)
VALUES (2, 2, 2, 90, 'Réussi');

INSERT INTO Resultat (id, eleve_id, examen_id, note, resultat)
VALUES (3, 3, 3, 80, 'Réussi');

INSERT INTO Resultat (id, eleve_id, examen_id, note, resultat)
VALUES (4, 4, 4, 78, 'Échoué');

INSERT INTO Resultat (id, eleve_id, examen_id, note, resultat)
VALUES (5, 5, 5, 95, 'Réussi');


/* afficher les tables */

select * from Eleve;
select * from Instructeur;
select * from Vehicule;
select * from Cours;
select * from resultat;
select * from Examen;
select * from typeexamen;



--Retrieve exam results with student and exam details:



SELECT 
    r.id AS resultat_id,
    e.nom AS student_nom,
    e.prenom AS student_prenom,
    ex.date_examen,
    ex.heure_examen,
    re.nom AS exam_type,
    r.note,
    r.resultat
FROM Resultat r
JOIN Eleve e ON r.eleve_id = e.id
JOIN Examen ex ON r.examen_id = ex.id
JOIN TypeExamen re ON ex.   id = re.id;

