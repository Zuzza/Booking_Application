-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: localhost:8889
-- Generation Time: Aug 13, 2019 at 03:18 AM
-- Server version: 5.7.25
-- PHP Version: 7.3.1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `db_booking`
--
CREATE DATABASE IF NOT EXISTS `db_booking` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `db_booking`;

-- --------------------------------------------------------

--
-- Table structure for table `appartments`
--

CREATE TABLE `appartments` (
  `address` varchar(30) NOT NULL,
  `postCode` varchar(4) NOT NULL,
  `suburb` varchar(15) NOT NULL,
  `dayPrice` decimal(5,0) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `appartments`
--

INSERT INTO `appartments` (`address`, `postCode`, `suburb`, `dayPrice`) VALUES
('305/554 Elizabeth st', '3000', 'Melbourne', '165'),
('308/25 Batman st', '3003', 'West Melbourne', '120'),
('44 Lygon st', '3053', 'Carlton', '245');

-- --------------------------------------------------------

--
-- Table structure for table `bookings`
--

CREATE TABLE `bookings` (
  `bookingID` int(8) NOT NULL,
  `address` varchar(30) NOT NULL,
  `name` varchar(20) NOT NULL,
  `surname` varchar(25) NOT NULL,
  `phone` varchar(15) NOT NULL,
  `email` varchar(25) NOT NULL,
  `checkin` date NOT NULL,
  `days` int(3) NOT NULL,
  `quoted` decimal(7,0) NOT NULL,
  `source` int(1) DEFAULT NULL,
  `returning` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `bookings`
--

INSERT INTO `bookings` (`bookingID`, `address`, `name`, `surname`, `phone`, `email`, `checkin`, `days`, `quoted`, `source`, `returning`) VALUES
(3, '308/25 Batman st', 'Tom', 'Gorman', '111222333', 'gor@hd.au', '2019-10-16', 1, '120', 1, NULL),
(4, '308/25 Batman st', 'Rachel', 'Woods', '0900744322', 'Rachel.Woods@gmail.com', '2019-12-25', 1, '108', 2, NULL),
(123, '308/25 Batman st', 'Miles', 'Caldwell', '0985345567', 'Miles@mail.com', '2019-11-19', 8, '864', 2, NULL),
(124, '44 Lygon st', 'Alex', 'Thomas', '0765432345', 'Alex.t@mailo.com', '2019-10-06', 2, '490', NULL, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `appartments`
--
ALTER TABLE `appartments`
  ADD PRIMARY KEY (`address`);

--
-- Indexes for table `bookings`
--
ALTER TABLE `bookings`
  ADD PRIMARY KEY (`bookingID`),
  ADD KEY `fk_bookings_appartments` (`address`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bookings`
--
ALTER TABLE `bookings`
  ADD CONSTRAINT `fk_bookings_appartments` FOREIGN KEY (`address`) REFERENCES `appartments` (`address`);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
