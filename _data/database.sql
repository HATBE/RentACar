-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 03. Okt 2024 um 22:55
-- Server-Version: 10.4.27-MariaDB
-- PHP-Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `soeproject`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `bookings`
--

CREATE TABLE `bookings` (
  `Rid` int(11) NOT NULL,
  `Uid` int(11) NOT NULL,
  `Cid` int(11) NOT NULL,
  `RstartDate` datetime NOT NULL,
  `RendDate` datetime NOT NULL,
  `RcreationDate` int(11) NOT NULL,
  `RcalculatedPrice` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `cars`
--

CREATE TABLE `cars` (
  `Cid` int(11) NOT NULL,
  `Cmake` varchar(255) NOT NULL,
  `Cmodel` varchar(255) NOT NULL,
  `CbuildYear` int(4) NOT NULL,
  `Ccategory` varchar(255) NOT NULL,
  `Chorsepower` int(11) NOT NULL,
  `Cseatscount` int(11) NOT NULL,
  `CpricePerDay` float NOT NULL,
  `CgearType` enum('AUTOMATIC','MANUAL') NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `cars`
--

INSERT INTO `cars` (`Cid`, `Cmake`, `Cmodel`, `CbuildYear`, `Ccategory`, `Chorsepower`, `Cseatscount`, `CpricePerDay`, `CgearType`) VALUES
(1, 'Nissan', 'Skyline R34 GT-R', 1998, 'JDM', 280, 4, 500, 'MANUAL');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `users`
--

CREATE TABLE `users` (
  `Uid` int(11) NOT NULL,
  `Ufirstname` varchar(255) NOT NULL,
  `Ulastname` varchar(255) NOT NULL,
  `Uemail` varchar(320) NOT NULL,
  `Uphone` varchar(255) NOT NULL,
  `UpasswordHash` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `bookings`
--
ALTER TABLE `bookings`
  ADD PRIMARY KEY (`Rid`),
  ADD KEY `bookings_cars` (`Cid`),
  ADD KEY `bookings_users` (`Uid`);

--
-- Indizes für die Tabelle `cars`
--
ALTER TABLE `cars`
  ADD PRIMARY KEY (`Cid`);

--
-- Indizes für die Tabelle `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`Uid`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `bookings`
--
ALTER TABLE `bookings`
  MODIFY `Rid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `cars`
--
ALTER TABLE `cars`
  MODIFY `Cid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT für Tabelle `users`
--
ALTER TABLE `users`
  MODIFY `Uid` int(11) NOT NULL AUTO_INCREMENT;

--
-- Constraints der exportierten Tabellen
--

--
-- Constraints der Tabelle `bookings`
--
ALTER TABLE `bookings`
  ADD CONSTRAINT `bookings_cars` FOREIGN KEY (`Cid`) REFERENCES `cars` (`Cid`),
  ADD CONSTRAINT `bookings_users` FOREIGN KEY (`Uid`) REFERENCES `users` (`Uid`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
