-- phpMyAdmin SQL Dump
-- version 4.8.5
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Apr 26, 2019 at 09:11 PM
-- Server version: 10.1.38-MariaDB
-- PHP Version: 7.3.2

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bank`
--

-- --------------------------------------------------------

--
-- Table structure for table `transactions`
--

CREATE TABLE `transactions` (
  `Type` enum('Deposit','Withdraw') CHARACTER SET utf8 NOT NULL,
  `Value` int(11) NOT NULL,
  `Date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `transactions`
--

INSERT INTO `transactions` (`Type`, `Value`, `Date`, `id`, `user_id`) VALUES
('Withdraw', 500, '2019-04-26 19:02:40', 42, 1),
('Withdraw', 300, '2019-04-26 19:02:16', 41, 1),
('Withdraw', 300, '2019-04-26 19:01:24', 40, 1),
('Deposit', 300, '2019-04-26 19:00:34', 39, 1),
('Deposit', 300, '2019-04-26 19:00:05', 38, 1),
('Deposit', 1200, '2019-04-26 18:59:26', 37, 1),
('Withdraw', 1200, '2019-04-26 18:59:05', 36, 1),
('Deposit', 10, '2019-04-20 20:31:17', 25, 1),
('Deposit', 1000, '2019-04-26 17:37:18', 26, 1),
('Deposit', 1500, '2019-04-26 17:39:05', 27, 1),
('Deposit', 1500, '2019-04-26 17:39:11', 28, 1),
('Deposit', 1500, '2019-04-26 17:39:18', 29, 1),
('Deposit', 1200, '2019-04-26 18:27:50', 30, 1),
('Deposit', 1200, '2019-04-26 18:28:42', 31, 1),
('Withdraw', 1800, '2019-04-26 18:31:30', 32, 1),
('Withdraw', 1800, '2019-04-26 18:45:37', 33, 1),
('Withdraw', 1800, '2019-04-26 18:45:40', 34, 1),
('Withdraw', 1800, '2019-04-26 18:45:52', 35, 1);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `ID` int(11) UNSIGNED NOT NULL,
  `password` varchar(255) CHARACTER SET utf8 NOT NULL,
  `balance` int(11) DEFAULT '0',
  `dates` date DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`ID`, `password`, `balance`, `dates`) VALUES
(1, 'password', 2220, NULL);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `transactions`
--
ALTER TABLE `transactions`
  ADD PRIMARY KEY (`id`),
  ADD KEY `user_id` (`user_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `transactions`
--
ALTER TABLE `transactions`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=43;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `ID` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
