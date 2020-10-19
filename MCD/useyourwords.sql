-- phpMyAdmin SQL Dump
-- version 4.9.0.1
-- https://www.phpmyadmin.net/
--
-- Hôte : 127.0.0.1
-- Généré le :  jeu. 15 oct. 2020 à 17:55
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
  `GAME_CREATED_AT` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `media`
--

CREATE TABLE `media` (
  `MEDIA_ID` int(4) NOT NULL,
  `MEDIA_TYPE_ID` int(2) DEFAULT NULL,
  `MEDIA_PATH` varchar(500) DEFAULT NULL,
  `MEDIA_CONTENT` varchar(2500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `media_type`
--

CREATE TABLE `media_type` (
  `MEDIA_TYPE_ID` int(2) NOT NULL,
  `MEDIA_TYPE_LIBELLE` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `play`
--

CREATE TABLE `play` (
  `PLAY_ID` int(12) NOT NULL,
  `USER_ID` int(8) DEFAULT NULL,
  `GAME_ID` int(10) DEFAULT NULL,
  `PLAY_SCORE` int(8) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Structure de la table `user`
--

CREATE TABLE `user` (
  `USER_ID` int(8) NOT NULL,
  `USER_LOGIN` varchar(100) DEFAULT NULL,
  `USER_PASSWORD` varchar(100) DEFAULT NULL,
  `USER_PSEUDO` varchar(100) DEFAULT NULL,
  `USER_ADMIN` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Index pour les tables déchargées
--

--
-- Index pour la table `constitute`
--
ALTER TABLE `constitute`
  ADD PRIMARY KEY (`GAME_ID`,`MEDIA_ID`),
  ADD KEY `FK_CONSTITUTE_MEDIA` (`MEDIA_ID`);

--
-- Index pour la table `game`
--
ALTER TABLE `game`
  ADD PRIMARY KEY (`GAME_ID`);

--
-- Index pour la table `media`
--
ALTER TABLE `media`
  ADD PRIMARY KEY (`MEDIA_ID`),
  ADD KEY `FK_MEDIA_MEDIA_TYPE` (`MEDIA_TYPE_ID`);

--
-- Index pour la table `media_type`
--
ALTER TABLE `media_type`
  ADD PRIMARY KEY (`MEDIA_TYPE_ID`);

--
-- Index pour la table `play`
--
ALTER TABLE `play`
  ADD PRIMARY KEY (`PLAY_ID`),
  ADD KEY `FK_PLAY_USER` (`USER_ID`),
  ADD KEY `FK_PLAY_GAME` (`GAME_ID`);

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
  MODIFY `GAME_ID` int(10) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `media`
--
ALTER TABLE `media`
  MODIFY `MEDIA_ID` int(4) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `media_type`
--
ALTER TABLE `media_type`
  MODIFY `MEDIA_TYPE_ID` int(2) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `play`
--
ALTER TABLE `play`
  MODIFY `PLAY_ID` int(12) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT pour la table `user`
--
ALTER TABLE `user`
  MODIFY `USER_ID` int(8) NOT NULL AUTO_INCREMENT;

--
-- Contraintes pour les tables déchargées
--

--
-- Contraintes pour la table `constitute`
--
ALTER TABLE `constitute`
  ADD CONSTRAINT `FK_CONSTITUTE_GAME` FOREIGN KEY (`GAME_ID`) REFERENCES `game` (`GAME_ID`),
  ADD CONSTRAINT `FK_CONSTITUTE_MEDIA` FOREIGN KEY (`MEDIA_ID`) REFERENCES `media` (`MEDIA_ID`);

--
-- Contraintes pour la table `media`
--
ALTER TABLE `media`
  ADD CONSTRAINT `FK_MEDIA_MEDIA_TYPE` FOREIGN KEY (`MEDIA_TYPE_ID`) REFERENCES `media_type` (`MEDIA_TYPE_ID`);

--
-- Contraintes pour la table `play`
--
ALTER TABLE `play`
  ADD CONSTRAINT `FK_PLAY_GAME` FOREIGN KEY (`GAME_ID`) REFERENCES `game` (`GAME_ID`),
  ADD CONSTRAINT `FK_PLAY_USER` FOREIGN KEY (`USER_ID`) REFERENCES `user` (`USER_ID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
