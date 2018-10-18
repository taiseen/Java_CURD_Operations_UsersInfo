-- phpMyAdmin SQL Dump
-- version 4.8.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Oct 18, 2018 at 09:04 PM
-- Server version: 10.1.36-MariaDB
-- PHP Version: 7.2.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `diu_test`
--

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `uID` int(11) NOT NULL,
  `uName` varchar(25) NOT NULL,
  `uEmail` varchar(30) NOT NULL,
  `uUserName` varchar(10) NOT NULL,
  `uPass` varchar(15) NOT NULL,
  `uAge` int(11) DEFAULT NULL,
  `uContact` varchar(15) DEFAULT NULL,
  `uGender` varchar(11) DEFAULT NULL,
  `uHight` varchar(10) DEFAULT NULL,
  `uAddress` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`uID`, `uName`, `uEmail`, `uUserName`, `uPass`, `uAge`, `uContact`, `uGender`, `uHight`, `uAddress`) VALUES
(1, 'seen', 'seen@gmail.com', 'seen', '123', 23, '01712335544', 'Male', '5.6', 'Dhaka'),
(2, 'taiseen azam', 'taiseen@gmail.com', 'taiseen', '1234', 25, '01717416412', 'Male', '5\'5', 'Chapai'),
(3, 'Jon', 'jon@gmail.com', 'jon', 'jon', 25, '01717546215', 'Female', '5\'9', 'Jhalokhati'),
(5, 'hasib', 'example@gmail.com', 'hasib', 'abcd', 21, '01745984512', 'Male', '5\'7', 'Rajshahi'),
(6, 'alex jovan', 'alex@gmail.com', 'seen', '12345', 25, '01717416412', 'Female', '5\'5', 'Nattor');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`uID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `uID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
