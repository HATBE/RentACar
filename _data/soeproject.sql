-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Erstellungszeit: 18. Nov 2024 um 09:34
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
  `Bid` int(11) NOT NULL,
  `Uid` int(11) NOT NULL,
  `Cid` int(11) NOT NULL,
  `BstartDate` datetime NOT NULL,
  `BendDate` datetime NOT NULL,
  `BcreationDate` datetime NOT NULL,
  `BcalculatedPrice` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `carcategories`
--

CREATE TABLE `carcategories` (
  `CCid` int(11) NOT NULL,
  `CCname` varchar(255) NOT NULL,
  `CCimage` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `carcategories`
--

INSERT INTO `carcategories` (`CCid`, `CCname`, `CCimage`) VALUES
(1, 'JDM', 'jdm.jpg'),
(2, 'Sedan', 'sedan.jpg'),
(3, 'Hatchback', 'hatchback.jpg'),
(4, 'SUV', 'suv.jpg'),
(5, 'Supercar', 'supercar.jpg'),
(6, 'Compact', 'compact.jpg'),
(7, ' Electric', 'electric.jpg'),
(8, 'Hybrid', 'hybrid.jpg');

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `cars`
--

CREATE TABLE `cars` (
  `Cid` int(11) NOT NULL,
  `Cmake` varchar(255) NOT NULL,
  `Cmodel` varchar(255) NOT NULL,
  `CbuildYear` int(4) NOT NULL,
  `Chorsepower` int(11) NOT NULL,
  `Cseatscount` int(11) NOT NULL,
  `CpricePerDay` float NOT NULL,
  `CgearType` enum('AUTOMATIC','MANUAL') NOT NULL,
  `CfuelType` enum('GASOLINE','DIESEL','ELECTRIC','HYBRID') NOT NULL,
  `CCid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `cars`
--

INSERT INTO `cars` (`Cid`, `Cmake`, `Cmodel`, `CbuildYear`, `Chorsepower`, `Cseatscount`, `CpricePerDay`, `CgearType`, `CfuelType`, `CCid`) VALUES
(1, 'Nissan', 'Skyline R34 GT-R', 1998, 280, 4, 500, 'MANUAL', 'GASOLINE', 1),
(2, 'Toyota', 'Supra MK4', 1993, 320, 4, 450, 'MANUAL', 'GASOLINE', 1),
(3, 'Mazda', 'RX-7 FD', 1992, 276, 4, 400, 'MANUAL', 'GASOLINE', 1),
(4, 'Honda', 'NSX NA1', 1990, 270, 2, 600, 'MANUAL', 'GASOLINE', 1),
(5, 'Subaru', 'Impreza WRX STi', 1999, 280, 5, 350, 'MANUAL', 'GASOLINE', 1),
(6, 'Mitsubishi', 'Lancer Evolution VI', 1999, 280, 5, 375, 'MANUAL', 'GASOLINE', 1),
(7, 'Toyota', 'Camry', 2020, 203, 5, 80, 'AUTOMATIC', 'GASOLINE', 2),
(8, 'Honda', 'Civic', 2019, 158, 5, 75, 'AUTOMATIC', 'GASOLINE', 2),
(9, 'Ford', 'Focus', 2018, 160, 5, 70, 'AUTOMATIC', 'GASOLINE', 3),
(10, 'Chevrolet', 'Malibu', 2021, 160, 5, 85, 'AUTOMATIC', 'GASOLINE', 2),
(11, 'Volkswagen', 'Golf', 2020, 147, 5, 80, 'AUTOMATIC', 'GASOLINE', 3),
(12, 'Hyundai', 'Elantra', 2021, 147, 5, 70, 'AUTOMATIC', 'GASOLINE', 2),
(13, 'Kia', 'Sportage', 2022, 187, 5, 100, 'AUTOMATIC', 'GASOLINE', 4),
(14, 'Nissan', 'Altima', 2021, 188, 5, 90, 'AUTOMATIC', 'GASOLINE', 2),
(15, 'Mazda', 'CX-5', 2021, 187, 5, 95, 'AUTOMATIC', 'GASOLINE', 4),
(16, 'Subaru', 'Forester', 2020, 182, 5, 100, 'AUTOMATIC', 'GASOLINE', 4),
(17, 'Lamborghini', 'Aventador SVJ', 2021, 759, 2, 2000, 'AUTOMATIC', 'GASOLINE', 5),
(18, 'Ferrari', '488 Pista', 2020, 710, 2, 1800, 'AUTOMATIC', 'GASOLINE', 5),
(19, 'McLaren', '720S', 2021, 710, 2, 1900, 'AUTOMATIC', 'GASOLINE', 5),
(20, 'Porsche', '911 Turbo S', 2021, 640, 2, 1500, 'AUTOMATIC', 'GASOLINE', 5),
(21, 'Bugatti', 'Chiron', 2020, 1500, 2, 5000, 'AUTOMATIC', 'GASOLINE', 5),
(22, 'Dacia', 'Sandero', 2021, 90, 5, 40, 'MANUAL', 'GASOLINE', 6),
(23, 'Renault', 'Clio', 2020, 100, 5, 50, 'MANUAL', 'GASOLINE', 6),
(24, 'Skoda', 'Fabia', 2019, 95, 5, 45, 'MANUAL', 'GASOLINE', 6),
(25, 'Hyundai', 'i20', 2020, 100, 5, 50, 'MANUAL', 'GASOLINE', 6),
(26, 'Kia', 'Rio', 2021, 100, 5, 50, 'MANUAL', 'GASOLINE', 6),
(27, 'Tesla', 'Model S', 2022, 1020, 5, 150, 'AUTOMATIC', 'ELECTRIC', 7),
(28, 'Ford', 'Mustang Mach-E', 2021, 346, 5, 120, 'AUTOMATIC', 'ELECTRIC', 7),
(29, 'Toyota', 'Prius', 2018, 121, 5, 60, 'AUTOMATIC', 'HYBRID', 8),
(30, 'Chevrolet', 'Bolt EV', 2021, 200, 5, 90, 'AUTOMATIC', 'ELECTRIC', 7),
(31, 'BMW', 'i3', 2019, 170, 4, 80, 'AUTOMATIC', 'ELECTRIC', 7),
(32, 'Nissan', 'Leaf', 2020, 147, 5, 85, 'AUTOMATIC', 'ELECTRIC', 7),
(33, 'Hyundai', 'Ioniq', 2020, 139, 5, 75, 'AUTOMATIC', 'HYBRID', 8),
(34, 'Honda', 'Insight', 2019, 151, 5, 70, 'AUTOMATIC', 'HYBRID', 8),
(35, 'Audi', 'e-tron', 2021, 402, 5, 130, 'AUTOMATIC', 'ELECTRIC', 7),
(36, 'Mercedes-Benz', 'EQC', 2020, 402, 5, 140, 'AUTOMATIC', 'ELECTRIC', 7),
(37, 'BMW', 'X5', 2019, 261, 5, 110, 'AUTOMATIC', 'DIESEL', 4),
(38, 'Audi', 'Q7', 2020, 282, 7, 120, 'AUTOMATIC', 'DIESEL', 4),
(39, 'Honda', 'CR-V', 2021, 190, 5, 85, 'AUTOMATIC', 'DIESEL', 4),
(40, 'Mercedes-Benz', 'GLE', 2021, 325, 5, 140, 'AUTOMATIC', 'DIESEL', 4),
(41, 'Volkswagen', 'Passat', 2020, 174, 5, 65, 'MANUAL', 'DIESEL', 2);

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
  ADD PRIMARY KEY (`Bid`),
  ADD KEY `bookings_cars` (`Cid`),
  ADD KEY `bookings_users` (`Uid`);

--
-- Indizes für die Tabelle `carcategories`
--
ALTER TABLE `carcategories`
  ADD PRIMARY KEY (`CCid`);

--
-- Indizes für die Tabelle `cars`
--
ALTER TABLE `cars`
  ADD PRIMARY KEY (`Cid`),
  ADD KEY `cars_carcategories_fk` (`CCid`);

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
  MODIFY `Bid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT für Tabelle `carcategories`
--
ALTER TABLE `carcategories`
  MODIFY `CCid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT für Tabelle `cars`
--
ALTER TABLE `cars`
  MODIFY `Cid` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=42;

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

--
-- Constraints der Tabelle `cars`
--
ALTER TABLE `cars`
  ADD CONSTRAINT `cars_carcategories_fk` FOREIGN KEY (`CCid`) REFERENCES `carcategories` (`CCid`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
