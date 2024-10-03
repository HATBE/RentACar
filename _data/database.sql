-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 03. Okt 2024 um 23:12
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
(1, 'Nissan', 'Skyline R34 GT-R', 1998, 'JDM', 280, 4, 500, 'MANUAL'),
(2, 'Toyota', 'Supra MK4', 1993, 'JDM', 320, 4, 450, 'MANUAL'),
(3, 'Mazda', 'RX-7 FD', 1992, 'JDM', 276, 4, 400, 'MANUAL'),
(4, 'Honda', 'NSX NA1', 1990, 'JDM', 270, 2, 600, 'MANUAL'),
(5, 'Subaru', 'Impreza WRX STi', 1999, 'JDM', 280, 5, 350, 'MANUAL'),
(6, 'Mitsubishi', 'Lancer Evolution VI', 1999, 'JDM', 280, 5, 375, 'MANUAL'),
(7, 'Toyota', 'Camry', 2020, 'Sedan', 203, 5, 80, 'AUTOMATIC'),
(8, 'Honda', 'Civic', 2019, 'Sedan', 158, 5, 75, 'AUTOMATIC'),
(9, 'Ford', 'Focus', 2018, 'Hatchback', 160, 5, 70, 'AUTOMATIC'),
(10, 'Chevrolet', 'Malibu', 2021, 'Sedan', 160, 5, 85, 'AUTOMATIC'),
(11, 'Volkswagen', 'Golf', 2020, 'Hatchback', 147, 5, 80, 'AUTOMATIC'),
(12, 'Hyundai', 'Elantra', 2021, 'Sedan', 147, 5, 70, 'AUTOMATIC'),
(13, 'Kia', 'Sportage', 2022, 'SUV', 187, 5, 100, 'AUTOMATIC'),
(14, 'Nissan', 'Altima', 2021, 'Sedan', 188, 5, 90, 'AUTOMATIC'),
(15, 'Mazda', 'CX-5', 2021, 'SUV', 187, 5, 95, 'AUTOMATIC'),
(16, 'Subaru', 'Forester', 2020, 'SUV', 182, 5, 100, 'AUTOMATIC'),
(17, 'Lamborghini', 'Aventador SVJ', 2021, 'Supercar', 759, 2, 2000, 'AUTOMATIC'),
(18, 'Ferrari', '488 Pista', 2020, 'Supercar', 710, 2, 1800, 'AUTOMATIC'),
(19, 'McLaren', '720S', 2021, 'Supercar', 710, 2, 1900, 'AUTOMATIC'),
(20, 'Porsche', '911 Turbo S', 2021, 'Supercar', 640, 2, 1500, 'AUTOMATIC'),
(21, 'Bugatti', 'Chiron', 2020, 'Supercar', 1500, 2, 5000, 'AUTOMATIC'),
(22, 'Dacia', 'Sandero', 2021, 'Compact', 90, 5, 40, 'MANUAL'),
(23, 'Renault', 'Clio', 2020, 'Compact', 100, 5, 50, 'MANUAL'),
(24, 'Skoda', 'Fabia', 2019, 'Compact', 95, 5, 45, 'MANUAL'),
(25, 'Hyundai', 'i20', 2020, 'Compact', 100, 5, 50, 'MANUAL'),
(26, 'Kia', 'Rio', 2021, 'Compact', 100, 5, 50, 'MANUAL');

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
  MODIFY `Cid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=27;

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
