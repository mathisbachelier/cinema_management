-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le : sam. 25 mai 2024 à 15:42
-- Version du serveur : 10.4.32-MariaDB
-- Version de PHP : 8.0.30

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données : `cinema_management`
--

DELIMITER $$
--
-- Procédures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `archive_old_screenings` ()   BEGIN
    DECLARE cuurrent_date DATE;
    
    SET cuurrent_date = CURDATE();  -- Obtient la date actuelle
    
    UPDATE screening
    SET is_archived = 1
    WHERE start_time < cuurrent_date;  -- Met à jour les séances dont la date est passée
END$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `UpdateScreeningArchivedStatus` ()   BEGIN
    -- Déclaration de variables pour la date actuelle et la date de demain
    DECLARE cuurrent_date DATE;
    DECLARE tomorrow_date DATE;
    
    -- Attribution de la date actuelle et de la date de demain
    SET cuurrent_date = CURDATE();
    SET tomorrow_date = DATE_ADD(cuurrent_date, INTERVAL 1 DAY);
    
    -- Mise à jour de la colonne is_archived pour les séances dont la date est demain
    UPDATE screening
    SET is_archived = 1
    WHERE start_time = tomorrow_date;
END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `client`
--

CREATE TABLE `client` (
  `id` int(11) NOT NULL,
  `firstname` varchar(255) NOT NULL,
  `lastname` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `code_postal` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `client`
--

INSERT INTO `client` (`id`, `firstname`, `lastname`, `email`, `code_postal`) VALUES
(1, 'John', 'Doe', 'johndoe@gmail.com', '06000'),
(2, 'Jane', 'Smith', 'JaneSmith@gmail.com', '06200'),
(3, 'Alice', 'Johnson', 'aliceJohnson@gmail.com', '06300'),
(8, 'mathis', 'bachelier', 'mathis@gmail.com', '06000');

-- --------------------------------------------------------

--
-- Structure de la table `employee`
--

CREATE TABLE `employee` (
  `id` int(11) NOT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `role` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `employee`
--

INSERT INTO `employee` (`id`, `username`, `password`, `role`) VALUES
(1, 'mathis', 'mathis0218', 1),
(2, 'emma', 'emma123', 2),
(3, 'jack', 'jack456', 2),
(18, 'jojo', '0776180dc120b60d4f3e065d47ee3eb22c74585fe39dd66774850583b5c87ee9', 2);

-- --------------------------------------------------------

--
-- Structure de la table `movie`
--

CREATE TABLE `movie` (
  `id` int(11) NOT NULL,
  `title` varchar(255) NOT NULL,
  `director` varchar(255) NOT NULL,
  `description` varchar(255) NOT NULL,
  `duration` int(11) NOT NULL,
  `is_archived` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `movie`
--

INSERT INTO `movie` (`id`, `title`, `director`, `description`, `duration`, `is_archived`) VALUES
(1, 'Inception', 'Christopher Nolan', 'Dom Cobb, voleur de rêves, se voit proposer une mission : implanter une idée.\r\n\r\nL\'équipe s\'aventure dans les labyrinthes du subconscient où danger et illusions se mêlent.\r\n\r\nCobb combat ses démons et la frontière entre rêve et réalité s\'efface.\r\n\r\nL\'ince', 148, 0),
(2, 'The Shawshank Redemption', 'Frank Darabont', 'Andy Dufresne, banquier innocent, est condamné à vie pour meurtre.\r\n\r\nEn prison, il se lie d\'amitié avec Red et trouve l\'espoir dans l\'évasion.\r\n\r\nUn récit poignant sur l\'amitié, la persévérance et la rédemption.\r\n\r\nAvec Tim Robbins et Morgan Freeman.', 142, 0),
(3, 'Pulp Fiction', 'Quentin Tarantino', 'Los Angeles, histoires violentes et entrecroisées de gangsters, boxeurs et truands.\r\n\r\nDialogue percutant, humour noir et situations explosives.\r\n\r\nUn film culte de Quentin Tarantino avec John Travolta, Samuel L. Jackson et Uma Thurman.', 154, 0),
(4, 'blablabla', 'blablabla', 'blablabla', 123, 1);

-- --------------------------------------------------------

--
-- Structure de la table `reservation`
--

CREATE TABLE `reservation` (
  `reservation_id` int(11) NOT NULL,
  `client_email` varchar(255) NOT NULL,
  `screening_id` int(11) NOT NULL,
  `seat_id` int(11) NOT NULL,
  `reservation_time` datetime NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `reservation`
--

INSERT INTO `reservation` (`reservation_id`, `client_email`, `screening_id`, `seat_id`, `reservation_time`) VALUES
(1, 'johndoe@gmail.com', 2, 5, '2024-05-10 16:50:08'),
(2, 'johndoe@gmail.com', 2, 7, '2024-05-10 17:46:55'),
(3, 'johndoe@gmail.com', 2, 17, '2024-05-10 17:54:19'),
(4, 'johndoe@gmail.com', 2, 28, '2024-05-10 18:11:16'),
(5, 'johndoe@gmail.com', 2, 28, '2024-05-10 23:59:50'),
(6, 'johndoe@gmail.com', 17, 15, '2024-05-12 12:02:31'),
(7, 'johndoe@gmail.com', 17, 6, '2024-05-12 12:02:49'),
(9, 'johndoe@gmail.com', 2, 20, '2024-05-12 12:13:24'),
(10, 'johndoe@gmail.com', 2, 30, '2024-05-12 12:14:58'),
(11, 'johndoe@gmail.com', 17, 102, '2024-05-12 12:28:59'),
(12, 'johndoe@gmail.com', 2, 22, '2024-05-12 12:29:17'),
(13, 'mathis@gmail.com', 17, 66, '2024-05-12 12:30:29');

-- --------------------------------------------------------

--
-- Structure de la table `room`
--

CREATE TABLE `room` (
  `room_id` int(11) NOT NULL,
  `room_number` int(11) NOT NULL,
  `capacity` int(11) NOT NULL,
  `is_archived` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `room`
--

INSERT INTO `room` (`room_id`, `room_number`, `capacity`, `is_archived`) VALUES
(1, 101, 50, 0),
(2, 102, 40, 0),
(3, 103, 30, 0),
(8, 110, 50, 0),
(14, 1000, 50, 1),
(17, 201, 50, 0),
(18, 10, 50, 0);

--
-- Déclencheurs `room`
--
DELIMITER $$
CREATE TRIGGER `create_seats_on_room_insert` AFTER INSERT ON `room` FOR EACH ROW BEGIN
    DECLARE i INT DEFAULT 1;
    WHILE i <= NEW.capacity DO
        INSERT INTO `seat` (`room_id`, `seat_number`)
        VALUES (NEW.room_id, i);
        SET i = i + 1;
    END WHILE;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `screening`
--

CREATE TABLE `screening` (
  `id` int(11) NOT NULL,
  `film_id` int(11) NOT NULL,
  `room_id` int(11) NOT NULL,
  `start_time` date NOT NULL,
  `start_hour` time NOT NULL,
  `duration` int(11) NOT NULL,
  `available_seats` int(11) NOT NULL DEFAULT 50,
  `is_reserved` tinyint(1) NOT NULL DEFAULT 0,
  `is_archived` tinyint(11) NOT NULL DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `screening`
--

INSERT INTO `screening` (`id`, `film_id`, `room_id`, `start_time`, `start_hour`, `duration`, `available_seats`, `is_reserved`, `is_archived`) VALUES
(1, 1, 1, '2024-03-20', '00:00:00', 180, 50, 0, 1),
(2, 2, 17, '2024-06-20', '00:00:00', 150, 50, 0, 0),
(3, 3, 3, '2024-03-22', '00:00:00', 170, 50, 0, 1),
(4, 1, 1, '2024-04-18', '00:00:00', 148, 50, 0, 1),
(15, 1, 8, '2024-04-29', '16:35:00', 148, 50, 0, 1),
(17, 3, 18, '2024-06-30', '10:45:00', 154, 50, 0, 0);

--
-- Déclencheurs `screening`
--
DELIMITER $$
CREATE TRIGGER `set_archive_after_screening` BEFORE INSERT ON `screening` FOR EACH ROW BEGIN
    DECLARE end_time DATETIME;
    SET end_time = DATE_ADD(NEW.start_time, INTERVAL NEW.duration MINUTE);
    
    IF NOW() > end_time THEN
        SET NEW.is_archived = 1;
    END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Structure de la table `seat`
--

CREATE TABLE `seat` (
  `seat_id` int(11) NOT NULL,
  `room_id` int(11) NOT NULL,
  `seat_number` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Déchargement des données de la table `seat`
--

INSERT INTO `seat` (`seat_id`, `room_id`, `seat_number`) VALUES
(3, 17, 1),
(4, 17, 2),
(5, 17, 3),
(6, 17, 4),
(7, 17, 5),
(8, 17, 6),
(9, 17, 7),
(10, 17, 8),
(11, 17, 9),
(12, 17, 10),
(13, 17, 11),
(14, 17, 12),
(15, 17, 13),
(16, 17, 14),
(17, 17, 15),
(18, 17, 16),
(19, 17, 17),
(20, 17, 18),
(21, 17, 19),
(22, 17, 20),
(23, 17, 21),
(24, 17, 22),
(25, 17, 23),
(26, 17, 24),
(27, 17, 25),
(28, 17, 26),
(29, 17, 27),
(30, 17, 28),
(31, 17, 29),
(32, 17, 30),
(33, 17, 31),
(34, 17, 32),
(35, 17, 33),
(36, 17, 34),
(37, 17, 35),
(38, 17, 36),
(39, 17, 37),
(40, 17, 38),
(41, 17, 39),
(42, 17, 40),
(43, 17, 41),
(44, 17, 42),
(45, 17, 43),
(46, 17, 44),
(47, 17, 45),
(48, 17, 46),
(49, 17, 47),
(50, 17, 48),
(51, 17, 49),
(52, 17, 50),
(53, 18, 1),
(54, 18, 2),
(55, 18, 3),
(56, 18, 4),
(57, 18, 5),
(58, 18, 6),
(59, 18, 7),
(60, 18, 8),
(61, 18, 9),
(62, 18, 10),
(63, 18, 11),
(64, 18, 12),
(65, 18, 13),
(66, 18, 14),
(67, 18, 15),
(68, 18, 16),
(69, 18, 17),
(70, 18, 18),
(71, 18, 19),
(72, 18, 20),
(73, 18, 21),
(74, 18, 22),
(75, 18, 23),
(76, 18, 24),
(77, 18, 25),
(78, 18, 26),
(79, 18, 27),
(80, 18, 28),
(81, 18, 29),
(82, 18, 30),
(83, 18, 31),
(84, 18, 32),
(85, 18, 33),
(86, 18, 34),
(87, 18, 35),
(88, 18, 36),
(89, 18, 37),
(90, 18, 38),
(91, 18, 39),
(92, 18, 40),
(93, 18, 41),
(94, 18, 42),
(95, 18, 43),
(96, 18, 44),
(97, 18, 45),
(98, 18, 46),
(99, 18, 47),
(100, 18, 48),
(101, 18, 49),
(102, 18, 50);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Index pour la table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `movie`
--
ALTER TABLE `movie`
  ADD PRIMARY KEY (`id`);

--
-- Index pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD PRIMARY KEY (`reservation_id`),
  ADD KEY `screening_id` (`screening_id`),
  ADD KEY `fk_client_email` (`client_email`);

--
-- Index pour la table `room`
--
ALTER TABLE `room`
  ADD PRIMARY KEY (`room_id`);

--
-- Index pour la table `screening`
--
ALTER TABLE `screening`
  ADD PRIMARY KEY (`id`),
  ADD KEY `film_id` (`film_id`,`room_id`),
  ADD KEY `room_id` (`room_id`);

--
-- Index pour la table `seat`
--
ALTER TABLE `seat`
  ADD PRIMARY KEY (`seat_id`),
  ADD KEY `room_id` (`room_id`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `client`
--
ALTER TABLE `client`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT pour la table `employee`
--
ALTER TABLE `employee`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT pour la table `movie`
--
ALTER TABLE `movie`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT pour la table `reservation`
--
ALTER TABLE `reservation`
  MODIFY `reservation_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=14;

--
-- AUTO_INCREMENT pour la table `room`
--
ALTER TABLE `room`
  MODIFY `room_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT pour la table `screening`
--
ALTER TABLE `screening`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT pour la table `seat`
--
ALTER TABLE `seat`
  MODIFY `seat_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=103;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `reservation`
--
ALTER TABLE `reservation`
  ADD CONSTRAINT `fk_client_email` FOREIGN KEY (`client_email`) REFERENCES `client` (`email`),
  ADD CONSTRAINT `reservation_ibfk_2` FOREIGN KEY (`screening_id`) REFERENCES `screening` (`id`);

--
-- Contraintes pour la table `screening`
--
ALTER TABLE `screening`
  ADD CONSTRAINT `screening_ibfk_1` FOREIGN KEY (`room_id`) REFERENCES `room` (`room_id`),
  ADD CONSTRAINT `screening_ibfk_2` FOREIGN KEY (`film_id`) REFERENCES `movie` (`id`);

--
-- Contraintes pour la table `seat`
--
ALTER TABLE `seat`
  ADD CONSTRAINT `seat_ibfk_1` FOREIGN KEY (`room_id`) REFERENCES `room` (`room_id`);

DELIMITER $$
--
-- Évènements
--
CREATE DEFINER=`root`@`localhost` EVENT `UpdateScreeningArchivedEvent` ON SCHEDULE EVERY 1 DAY STARTS '2024-05-04 01:00:04' ON COMPLETION NOT PRESERVE ENABLE DO BEGIN
    CALL archive_old_screenings();

END$$

DELIMITER ;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
