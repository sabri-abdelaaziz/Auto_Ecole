-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : localhost
-- Généré le : ven. 26 avr. 2024 à 12:21
-- Version du serveur : 10.4.28-MariaDB
-- Version de PHP : 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `auto_ecole`
--

-- --------------------------------------------------------

--
-- Structure de la table `Cours`
--

CREATE TABLE `Cours` (
  `id` int(11) NOT NULL,
  `date_debut` date DEFAULT NULL,
  `date_fin` date DEFAULT NULL,
  `heure_debut` varchar(10) DEFAULT NULL,
  `heure_fin` varchar(10) DEFAULT NULL,
  `vehicule_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `Cours`
--

INSERT INTO `Cours` (`id`, `date_debut`, `date_fin`, `heure_debut`, `heure_fin`, `vehicule_id`) VALUES
(1, '2024-05-01', '2024-05-10', '09:00', '11:00', 1),
(2, '2024-05-15', '2024-05-24', '14:00', '16:00', 2),
(3, '2024-06-01', '2024-06-10', '09:00', '11:00', 3),
(4, '2024-06-15', '2024-06-24', '14:00', '16:00', 4),
(5, '2024-07-01', '2024-07-10', '09:00', '11:00', 5);

-- --------------------------------------------------------

--
-- Structure de la table `Eleve`
--

CREATE TABLE `Eleve` (
  `id` int(11) NOT NULL,
  `nom` varchar(100) DEFAULT NULL,
  `prenom` varchar(100) DEFAULT NULL,
  `adresse` varchar(255) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `Eleve`
--

INSERT INTO `Eleve` (`id`, `nom`, `prenom`, `adresse`, `telephone`) VALUES
(1, 'Williams', 'Sarah', '123 Maple St, City', '1122334455'),
(2, 'Jones', 'David', '456 Oak St, City', '9988776655'),
(3, 'Davis', 'Laura', '789 Pine St, City', '5544332211'),
(4, 'Wilson', 'Brian', '101 Cedar St, City', '3322114455'),
(5, 'Taylor', 'Karen', '202 Elm St, City', '4455667788');

-- --------------------------------------------------------

--
-- Structure de la table `Examen`
--

CREATE TABLE `Examen` (
  `id` int(11) NOT NULL,
  `date_examen` date DEFAULT NULL,
  `heure_examen` varchar(10) DEFAULT NULL,
  `vehicule_id` int(11) DEFAULT NULL,
  `instructeur_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `FacturePaiement`
--

CREATE TABLE `FacturePaiement` (
  `id` int(11) NOT NULL,
  `eleve_id` int(11) DEFAULT NULL,
  `montant` float DEFAULT NULL,
  `date_paiement` date DEFAULT NULL,
  `mode_paiement` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `Instructeur`
--

CREATE TABLE `Instructeur` (
  `id` int(11) NOT NULL,
  `nom` varchar(100) DEFAULT NULL,
  `prenom` varchar(100) DEFAULT NULL,
  `adresse` varchar(255) DEFAULT NULL,
  `telephone` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `Instructeur`
--

INSERT INTO `Instructeur` (`id`, `nom`, `prenom`, `adresse`, `telephone`) VALUES
(1, 'Doe', 'John', '123 Main St, City', '0123456789'),
(2, 'Smith', 'Jane', '456 Elm St, City', '9876543210'),
(3, 'Williams', 'Robert', '789 Oak St, City', '1234567890'),
(4, 'Johnson', 'Emily', '101 Pine St, City', '0987654321'),
(5, 'Brown', 'Michael', '202 Cedar St, City', '1122334455');

-- --------------------------------------------------------

--
-- Structure de la table `PlanningSeances`
--

CREATE TABLE `PlanningSeances` (
  `id` int(11) NOT NULL,
  `cours_id` int(11) DEFAULT NULL,
  `date_seance` date DEFAULT NULL,
  `heure_debut` varchar(10) DEFAULT NULL,
  `heure_fin` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `Reservation`
--

CREATE TABLE `Reservation` (
  `id` int(11) NOT NULL,
  `eleve_id` int(11) DEFAULT NULL,
  `cours_id` int(11) DEFAULT NULL,
  `date_reservation` date DEFAULT NULL,
  `heure_reservation` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `Reservation`
--

INSERT INTO `Reservation` (`id`, `eleve_id`, `cours_id`, `date_reservation`, `heure_reservation`) VALUES
(1, 1, 1, '2024-04-25', '10:00'),
(2, 2, 2, '2024-04-26', '15:00'),
(3, 3, 3, '2024-04-27', '10:00'),
(4, 4, 4, '2024-04-28', '15:00'),
(5, 5, 5, '2024-04-29', '10:00');

-- --------------------------------------------------------

--
-- Structure de la table `Resultat`
--

CREATE TABLE `Resultat` (
  `id` int(11) NOT NULL,
  `eleve_id` int(11) DEFAULT NULL,
  `examen_id` int(11) DEFAULT NULL,
  `note` int(11) DEFAULT NULL,
  `resultat` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Structure de la table `TypeExamen`
--

CREATE TABLE `TypeExamen` (
  `id` int(11) NOT NULL,
  `nom` varchar(255) DEFAULT NULL,
  `description` varchar(1000) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `TypeExamen`
--

INSERT INTO `TypeExamen` (`id`, `nom`, `description`) VALUES
(1, 'Examen de Conduite', 'Examen pratique de conduite.'),
(2, 'Examen de Code', 'Examen théorique de code de la route.'),
(3, 'Examen de Manœuvres', 'Examen de manœuvres spécifiques.'),
(4, 'Examen de Secourisme', 'Examen de premiers secours.'),
(5, 'Examen de Connaissance des Véhicules', 'Examen sur la connaissance des véhicules et leur entretien.');

-- --------------------------------------------------------

--
-- Structure de la table `Vehicule`
--

CREATE TABLE `Vehicule` (
  `id` int(11) NOT NULL,
  `marque` varchar(100) DEFAULT NULL,
  `modele` varchar(100) DEFAULT NULL,
  `annee_fabrication` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `Vehicule`
--

INSERT INTO `Vehicule` (`id`, `marque`, `modele`, `annee_fabrication`) VALUES
(1, 'Toyota', 'Corolla', 2020),
(2, 'Honda', 'Civic', 2019),
(3, 'Ford', 'Focus', 2021),
(4, 'Chevrolet', 'Malibu', 2018),
(5, 'Nissan', 'Sentra', 2022);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `Cours`
--
ALTER TABLE `Cours`
  ADD PRIMARY KEY (`id`),
  ADD KEY `vehicule_id` (`vehicule_id`);

--
-- Index pour la table `Eleve`
--
ALTER TABLE `Eleve`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `Examen`
--
ALTER TABLE `Examen`
  ADD PRIMARY KEY (`id`),
  ADD KEY `vehicule_id` (`vehicule_id`),
  ADD KEY `instructeur_id` (`instructeur_id`);

--
-- Index pour la table `FacturePaiement`
--
ALTER TABLE `FacturePaiement`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `Instructeur`
--
ALTER TABLE `Instructeur`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `PlanningSeances`
--
ALTER TABLE `PlanningSeances`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `Reservation`
--
ALTER TABLE `Reservation`
  ADD PRIMARY KEY (`id`),
  ADD KEY `eleve_id` (`eleve_id`),
  ADD KEY `cours_id` (`cours_id`);

--
-- Index pour la table `Resultat`
--
ALTER TABLE `Resultat`
  ADD PRIMARY KEY (`id`),
  ADD KEY `eleve_id` (`eleve_id`),
  ADD KEY `examen_id` (`examen_id`);

--
-- Index pour la table `TypeExamen`
--
ALTER TABLE `TypeExamen`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `Vehicule`
--
ALTER TABLE `Vehicule`
  ADD PRIMARY KEY (`id`);

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `Cours`
--
ALTER TABLE `Cours`
  ADD CONSTRAINT `cours_ibfk_1` FOREIGN KEY (`vehicule_id`) REFERENCES `Vehicule` (`id`);

--
-- Contraintes pour la table `Examen`
--
ALTER TABLE `Examen`
  ADD CONSTRAINT `examen_ibfk_1` FOREIGN KEY (`vehicule_id`) REFERENCES `Vehicule` (`id`),
  ADD CONSTRAINT `examen_ibfk_2` FOREIGN KEY (`instructeur_id`) REFERENCES `Instructeur` (`id`);

--
-- Contraintes pour la table `Reservation`
--
ALTER TABLE `Reservation`
  ADD CONSTRAINT `reservation_ibfk_1` FOREIGN KEY (`eleve_id`) REFERENCES `Eleve` (`id`),
  ADD CONSTRAINT `reservation_ibfk_2` FOREIGN KEY (`cours_id`) REFERENCES `Cours` (`id`);

--
-- Contraintes pour la table `Resultat`
--
ALTER TABLE `Resultat`
  ADD CONSTRAINT `resultat_ibfk_1` FOREIGN KEY (`eleve_id`) REFERENCES `Eleve` (`id`),
  ADD CONSTRAINT `resultat_ibfk_2` FOREIGN KEY (`examen_id`) REFERENCES `Examen` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
