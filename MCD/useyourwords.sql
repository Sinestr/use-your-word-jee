-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le :  Dim 01 nov. 2020 à 20:10
-- Version du serveur :  10.4.6-MariaDB
-- Version de PHP :  7.3.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de données :  `useyourwords`
--

-- --------------------------------------------------------

--
-- Structure de la table `constitute`
--

CREATE TABLE `constitute` (
  `GAME_ID` int(10) NOT NULL,
  `MEDIA_ID` int(4) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `game`
--

CREATE TABLE `game` (
  `GAME_ID` int(10) NOT NULL,
  `GAME_NB_PLAYERS` int(2) DEFAULT NULL,
  `GAME_ACCESS_CODE` varchar(6) DEFAULT NULL,
  `GAME_CREATED_AT` date DEFAULT NULL,
  `GAME_STATUS` tinyint(1) NOT NULL,
  `game_team` bit(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `game`
--

INSERT INTO `game` (`GAME_ID`, `GAME_NB_PLAYERS`, `GAME_ACCESS_CODE`, `GAME_CREATED_AT`, `GAME_STATUS`, `game_team`) VALUES
(17, 2, 'AT6FH8', '2020-10-31', 1, b'1');

-- --------------------------------------------------------

--
-- Structure de la table `media`
--

CREATE TABLE `media` (
  `MEDIA_ID` int(4) NOT NULL,
  `MEDIA_PATH` longblob DEFAULT NULL,
  `MEDIA_CONTENT` varchar(2500) DEFAULT NULL,
  `MEDIA_NAME` varchar(255) DEFAULT NULL,
  `MEDIA_TITLE` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `play`
--

CREATE TABLE `play` (
  `PLAY_ID` int(12) NOT NULL,
  `USER_ID` int(8) DEFAULT NULL,
  `GAME_ID` int(10) DEFAULT NULL,
  `PLAY_SCORE` int(8) DEFAULT NULL,
  `PLAY_HOST` tinyint(1) NOT NULL,
  `play_team` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `play`
--

INSERT INTO `play` (`PLAY_ID`, `USER_ID`, `GAME_ID`, `PLAY_SCORE`, `PLAY_HOST`, `play_team`) VALUES
(20, 3, 17, 0, 1, 3),
(21, 2, 17, 0, 0, 2);

-- --------------------------------------------------------

--
-- Structure de la table `spring_session`
--

CREATE TABLE `spring_session` (
  `PRIMARY_ID` char(36) NOT NULL,
  `SESSION_ID` char(36) NOT NULL,
  `CREATION_TIME` bigint(20) NOT NULL,
  `LAST_ACCESS_TIME` bigint(20) NOT NULL,
  `MAX_INACTIVE_INTERVAL` int(11) NOT NULL,
  `EXPIRY_TIME` bigint(20) NOT NULL,
  `PRINCIPAL_NAME` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

--
-- Déchargement des données de la table `spring_session`
--

INSERT INTO `spring_session` (`PRIMARY_ID`, `SESSION_ID`, `CREATION_TIME`, `LAST_ACCESS_TIME`, `MAX_INACTIVE_INTERVAL`, `EXPIRY_TIME`, `PRINCIPAL_NAME`) VALUES
('2a4ddb80-b9ae-4d97-8a11-df7c52dac3bb', '9e48b8a5-237e-4a66-a145-80fc63d7c667', 1604251300580, 1604253857228, 1800, 1604255657228, NULL);

-- --------------------------------------------------------

--
-- Structure de la table `spring_session_attributes`
--

CREATE TABLE `spring_session_attributes` (
  `SESSION_PRIMARY_ID` char(36) NOT NULL,
  `ATTRIBUTE_NAME` varchar(200) NOT NULL,
  `ATTRIBUTE_BYTES` blob NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `USER_ID` int(8) NOT NULL,
  `USER_LOGIN` varchar(100) DEFAULT NULL,
  `USER_PASSWORD` varchar(100) DEFAULT NULL,
  `USER_PSEUDO` varchar(100) DEFAULT NULL,
  `USER_ADMIN` tinyint(1) DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Déchargement des données de la table `user`
--

INSERT INTO `user` (`USER_ID`, `USER_LOGIN`, `USER_PASSWORD`, `USER_PSEUDO`, `USER_ADMIN`) VALUES
(1, 'tangigreffier', 'azer', 'Dopelore', 1),
(2, 'roccat', 'mouse', 'Kova', 0),
(3, 'benoit', 'adven', 'Diablox9', 0);

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `constitute`
--
ALTER TABLE `constitute`
  ADD PRIMARY KEY (`GAME_ID`,`MEDIA_ID`),
  ADD KEY `FKl87bovfmndaswqqcx6k6f9rru` (`MEDIA_ID`);

--
-- Index pour la table `game`
--
ALTER TABLE `game`
  ADD PRIMARY KEY (`GAME_ID`);

--
-- Index pour la table `media`
--
ALTER TABLE `media`
  ADD PRIMARY KEY (`MEDIA_ID`);

--
-- Index pour la table `play`
--
ALTER TABLE `play`
  ADD PRIMARY KEY (`PLAY_ID`),
  ADD KEY `FKh5dyrwenqcpiuqbsl417ioqhs` (`GAME_ID`),
  ADD KEY `FKgemxxun4q0nwpvtv6g5l9tihn` (`USER_ID`);

--
-- Index pour la table `spring_session`
--
ALTER TABLE `spring_session`
  ADD PRIMARY KEY (`PRIMARY_ID`),
  ADD UNIQUE KEY `SPRING_SESSION_IX1` (`SESSION_ID`),
  ADD KEY `SPRING_SESSION_IX2` (`EXPIRY_TIME`),
  ADD KEY `SPRING_SESSION_IX3` (`PRINCIPAL_NAME`);

--
-- Index pour la table `spring_session_attributes`
--
ALTER TABLE `spring_session_attributes`
  ADD PRIMARY KEY (`SESSION_PRIMARY_ID`,`ATTRIBUTE_NAME`);

--
-- Index pour la table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`USER_ID`);

--
-- AUTO_INCREMENT pour les tables déchargées
--

--
-- AUTO_INCREMENT pour la table `game`
--
ALTER TABLE `game`
  MODIFY `GAME_ID` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- AUTO_INCREMENT pour la table `media`
--
ALTER TABLE `media`
  MODIFY `MEDIA_ID` int(4) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=25;

--
-- AUTO_INCREMENT pour la table `play`
--
ALTER TABLE `play`
  MODIFY `PLAY_ID` int(12) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `USER_ID` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `constitute`
--
ALTER TABLE `constitute`
  ADD CONSTRAINT `FK_CONSTITUTE_GAME` FOREIGN KEY (`GAME_ID`) REFERENCES `game` (`GAME_ID`),
  ADD CONSTRAINT `FK_CONSTITUTE_MEDIA` FOREIGN KEY (`MEDIA_ID`) REFERENCES `media` (`MEDIA_ID`),
  ADD CONSTRAINT `FKcof81ccyrp51p8s8qdo4hnlpi` FOREIGN KEY (`GAME_ID`) REFERENCES `game` (`GAME_ID`),
  ADD CONSTRAINT `FKl87bovfmndaswqqcx6k6f9rru` FOREIGN KEY (`MEDIA_ID`) REFERENCES `media` (`MEDIA_ID`);

--
-- Contraintes pour la table `play`
--
ALTER TABLE `play`
  ADD CONSTRAINT `FK_PLAY_GAME` FOREIGN KEY (`GAME_ID`) REFERENCES `game` (`GAME_ID`),
  ADD CONSTRAINT `FK_PLAY_USER` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `FKgemxxun4q0nwpvtv6g5l9tihn` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`),
  ADD CONSTRAINT `FKh5dyrwenqcpiuqbsl417ioqhs` FOREIGN KEY (`GAME_ID`) REFERENCES `game` (`GAME_ID`);

--
-- Contraintes pour la table `spring_session_attributes`
--
ALTER TABLE `spring_session_attributes`
  ADD CONSTRAINT `SPRING_SESSION_ATTRIBUTES_FK` FOREIGN KEY (`SESSION_PRIMARY_ID`) REFERENCES `spring_session` (`PRIMARY_ID`) ON DELETE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
